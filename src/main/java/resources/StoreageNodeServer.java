

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;


/**
 * Created by mengdongqi on 17-09-27.
 */
public class StoreageNodeServer {
  public void initServer(int port, StorageNodeCluster cluster) throws TException {
    KeyValueService.Processor<KeyValueService.Iface> processor = new KeyValueService.Processor<>(new KeyValueHandler(cluster));

    TNonblockingServerSocket trans = new TNonblockingServerSocket(port);
    TThreadedSelectorServer.Args args = new TThreadedSelectorServer.Args(trans);

    args.transportFactory(new TFramedTransport.Factory());
    args.protocolFactory(new TBinaryProtocol.Factory());
    args.processor(processor);
    args.selectorThreads(32);
    args.workerThreads(32);

    TServer server = new TThreadedSelectorServer(args);
    server.serve();
  }
}
