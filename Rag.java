import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author noah
 */
public class Rag<Vertex> {
    /*
     HashMap<ProcessId, node> HashMap
     <ResourceId , node
     */

    private static Node start;
    ArrayList<Node> deadlockNodes = new ArrayList<Node>();

    public void setStartValue(Node n) {
        start = n;
    }

    public boolean didCauseCycle(Node node) {
           // deadlockNodes.add(node);
        
        /*
         System.out.println(node.value);
         System.out.println(node.edgeValue);
         System.out.println(node.type);
         */
            //System.out.println("Here" + node.edgeValue);
            
            if (node.edgeValue == null) {
                //no deadlock detected
                //deadlockNodes.clear();
                //System.out.println("ran");
                return false;
            } else if (node.edgeValue != null) {
                didCauseCycle(node.edgeValue);
            }else if (node.edgeValue.equals(start)) {
                //deadlock detected

                return true;
            }
            return false;


    }

    public void printDeadlockNodes() {
        List<Node> listProcesses = new ArrayList<Node>();
        List<Node> listResources = new ArrayList<Node>();

        for (Node n : deadlockNodes) {

            if (n.type.equals(Node.NodeType.PROCESS)) {
                listProcesses.add(n);

            }
            if (n.type.equals(Node.NodeType.RESOURCE)) {
                listResources.add(n);
            }
        }
        System.out.print("DEADLOCK DETECTED: Processes ");
        for (Node n : listProcesses) {
            System.out.print(n.value + ", ");
        }
        System.out.print("and Resourceses ");
        for (Node n : listResources) {
            System.out.print(n.value + ", ");
        }

        System.out.print("are found in the cycle.");

    }

    public void printDeadlockNodes2() {

    }

    public void removeEdge(Node n) {
        n.edgeValue = null;
    }

    public Node getNode(int value, List<Rag.Node> nodeList, String type) {
        if (type == "Process") {
            for (Node n : nodeList) {
                if (n.value == value && n.type.equals(Node.NodeType.PROCESS)) {
                    return n;
                }
            }
        } else if (type == "Resource") {
            for (Node n : nodeList) {
                if (n.value == value && n.type.equals(Node.NodeType.RESOURCE)) {
                    return n;
                }
            }
        }
        return null;
    }

    public static class Node {

        public enum NodeType {

            RESOURCE, PROCESS
        }

        public NodeType type;
        public int value;
        public Node edgeValue;

        public Node(int value, boolean isProcess) {
            this.type = NodeType.PROCESS;
            this.value = value;
            this.edgeValue = null;
        }

        public Node(int value) {
            this.type = NodeType.RESOURCE;
            this.value = value;
            this.edgeValue = null;
        }

    }

}
