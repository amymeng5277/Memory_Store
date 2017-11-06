

import org.apache.thrift.TException;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengdongqi on 17-09-26.
 */
public class KeyValueHandler implements KeyValueService.Iface {
  StorageNodeCluster cluster;

  KeyValueHandler(StorageNodeCluster cluster) {
    this.cluster = cluster;
  }

  @Override
  public List<String> multiGet(List<String> keys) throws TException {
    List<String> values = new ArrayList<>();
    for (int i = 0; i < keys.size(); i++) {
      String value = get(keys.get(i));
      values.add(value);
    }
    return values;
  }

  @Override
  public void multiPut(List<String> keys, List<String> values) throws TException {
    if(keys.size()!=values.size())
      throw new IllegalArgument("The keys should have same dimension with values");
    for(int i=0;i<keys.size();i++){
      put(keys.get(i),values.get(i));
    }
  }
  @Override
  public String get(String key)  {
    if (key == null)
      try {
        throw new IllegalArgument("The key can not be null");
      } catch (IllegalArgument illegalArgument) {
        illegalArgument.printStackTrace();
      }
    try {
      return cluster.get(key);
    } catch (TException e) {
      e.printStackTrace();
    }
    return "";
  }
  @Override
  public void put(String key,String value){
    if(key == null) {
      try {
        throw new IllegalArgument("The key can not be null");
      } catch (IllegalArgument illegalArgument) {
        illegalArgument.printStackTrace();
      }
    }
    try {
      cluster.put(key,value);
    } catch (TException e) {
      e.printStackTrace();
    }
  }
}
