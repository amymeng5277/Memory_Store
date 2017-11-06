

import org.apache.thrift.TException;

/**
 * Created by mengdongqi on 17-09-26.
 */
public class RemoteKeyValueStore implements KeyValueStore {
  private ConnectionPool connectionPool;

  RemoteKeyValueStore(String host,int port){
    connectionPool = new ConnectionPool(host,port);
  }
  @Override
  public void put(String key, String value) throws TException{
    KeyValueService.Client client = connectionPool.getClient();
    client.put(key,value);
  }

  @Override
  public String get(String key) throws TException {
    KeyValueService.Client client = connectionPool.getClient();
    return client.get(key);
  }
}
