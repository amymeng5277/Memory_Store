
import org.apache.thrift.TException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mengdongqi on 17-09-20.
 */
public class StorageNodeCluster implements KeyValueStore {
  private Map<Integer,String> hostmap = new HashMap<>();
  private Map<Integer,Integer> portMap = new HashMap<>();
  public Map<Integer, KeyValueStore> storeMap = new HashMap<>();
  private int localNodeNum;
  StorageNodeCluster(int localNodeNum ,Map<Integer, String> hostmap, Map<Integer, Integer> portMap){
    this.localNodeNum = localNodeNum;
    this.hostmap = hostmap;
    this.portMap = portMap;
  }
  public int getLocalNodeNum(){
    return this.localNodeNum;
  }
  public int getTotalServers() {
    return this.hostmap.size();
  }

  public String getServerHost(int index) {
    return this.hostmap.get(index);
  }

  public int getServerPort(int index) {
    return this.portMap.get(index);
  }
  public void initSevice(){
    for(int i=0;i<getTotalServers();i++){
      if(i==localNodeNum)
        storeMap.put(i,new LocalKeyValueStore());
      else
        storeMap.put(i,new RemoteKeyValueStore(hostmap.get(i),portMap.get(i)));
    }
  }

  @Override
  public String get(String key) throws TException {
    Integer nodeNumber = Math.abs(key.hashCode())%getTotalServers();
    return storeMap.get(nodeNumber).get(key);
  }

  @Override
  public void put(String key, String value) throws TException {
    Integer nodeNumber = Math.abs(key.hashCode())%getTotalServers();
    storeMap.get(nodeNumber).put(key,value);
  }


}
