
import org.apache.thrift.TException;

/**
 * Created by mengdongqi on 17-09-26.
 */
public interface KeyValueStore {
  void put(String key,String value)throws TException;
  String get(String key)throws TException;
}
