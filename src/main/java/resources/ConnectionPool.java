

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by mengdongqi on 17-09-26.
 */
public class ConnectionPool {
  private int QEQUEST_NUMBER = 32;
  private int port;
  private String host;
  private LinkedBlockingQueue<KeyValueService.Client> linkedBlockingQueue = new LinkedBlockingQueue<>(QEQUEST_NUMBER);

  ConnectionPool(String host, int port) {
    this.host = host;
    this.port = port;
  }
  private void initServicePool(String host, int port){
    for (int i = 0; i < QEQUEST_NUMBER; i++) {
      TSocket sock = new TSocket(host, port);
      TTransport transport = new TFramedTransport(sock);
      TProtocol protocol = new TBinaryProtocol(transport);
      KeyValueService.Client client = new KeyValueService.Client(protocol);
      try{
        transport.open();
      }catch (TTransportException tte){
        tte.printStackTrace();
      }
      try{
        linkedBlockingQueue.put(client);
      }catch (InterruptedException ie){
        ie.printStackTrace();
      }

    }
  }

  public KeyValueService.Client  getClient(){
    synchronized (this.getClass()){
      if (linkedBlockingQueue.size() == 0) {
        initServicePool(host,port);
      }
    }
    try{
      return linkedBlockingQueue.take();
    }catch (InterruptedException ie){
      ie.printStackTrace();
    }
    return null;
  }
  public void clientPutBack(KeyValueService.Client client){
    try{
      linkedBlockingQueue.put(client);
    }catch (InterruptedException ie){
      ie.printStackTrace();
    }

  }
}
