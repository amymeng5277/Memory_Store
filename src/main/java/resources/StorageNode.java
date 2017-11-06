
import org.apache.log4j.BasicConfigurator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.logging.Logger;


/**
 * Created by mengdongqi on 17-09-26.
 */
public class StorageNode {
  static Logger log;

  public static void main(String [] args) throws Exception {
    if (args.length != 2) {
      System.err.println("Usage: java StorageNode config_file node_num");
      System.exit(-1);
    }

    // initialize log4j
    BasicConfigurator.configure();
    log = Logger.getLogger(StorageNode.class.getName());

    BufferedReader br = new BufferedReader(new FileReader(args[0]));
    HashMap<Integer, String> hosts = new HashMap<Integer, String>();
    HashMap<Integer, Integer> ports  = new HashMap<Integer, Integer>();
    String line;
    int i = 0;
    while ((line = br.readLine()) != null) {
      String[] parts = line.split(" ");
      hosts.put(i, parts[0]);
      ports.put(i, Integer.parseInt(parts[1]));
      i++;
    }
    int myNum = Integer.parseInt(args[1]);
    log.info("Launching storage node " + myNum + ", " + hosts.get(myNum) + ":" + ports.get(myNum));

    // Launch a Thrift server here
    //throw new Error("This code needs more work!");
    StoreageNodeServer server = new StoreageNodeServer();
    StorageNodeCluster cluster = new StorageNodeCluster(myNum, hosts, ports);
    server.initServer(ports.get(myNum),cluster);
  }
}
