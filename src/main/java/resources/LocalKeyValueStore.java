

import org.apache.thrift.TException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by mengdongqi on 17-09-26.
 */
public class LocalKeyValueStore implements KeyValueStore {
  Map<String,String> storageNode = new ConcurrentHashMap<>();
  @Override
  public void put(String key, String value) throws TException {
    storageNode.put(key, value);
  }

  @Override
  public String get(String key) throws TException {
    return storageNode.getOrDefault(key, "");
  }
}
