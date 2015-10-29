import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author noah
 */
public class Deadlock {

    static Rag r = new Rag();

    private static ArrayList<Rag.Node> nodeList = new ArrayList<Rag.Node>();

    public static void main(String[] args) throws FileNotFoundException {

        //final String fileName = args[0];
        try {
            BufferedReader br = new BufferedReader(new FileReader("/Users/noah/Desktop/4401/PA1/Programming Assignment 2/src/programming/assignment/pkg2/input1.txt"));
            String line = null;

            while ((line = br.readLine()) != null) {

                String tokens[] = line.split(" ");

                int process = Integer.parseInt(tokens[0]);
                String status = tokens[1];
                int resource = Integer.parseInt(tokens[2]);

                Rag.Node nProcess = r.getNode(process, nodeList, "Process");
                Rag.Node nResource = r.getNode(resource, nodeList, "Resource");

                if (status.equals("N")) {

                    if (nProcess == null && nResource == null) {
                        Rag.Node processNode = new Rag.Node(process, true);
                        Rag.Node resourceNode = new Rag.Node(resource);
                        resourceNode.edgeValue = processNode;
                        nodeList.add(processNode);
                        nodeList.add(resourceNode);
                        System.out.println("Process " + process + " needs resource " + resource + "- " + "Resource " + resource + " is allocated to process " + process + ".");
                        for (Rag.Node n : nodeList) {
                            boolean isDeadlock = r.didCauseCycle(n);
                            if (isDeadlock) {
                                //deadlock
                                System.out.println("NodeValue: " + n.value);
                                System.out.println("Deadlock");
                                r.printDeadlockNodes();
                            }
                        }

                    }
                    if (nProcess == null && nResource != null) {
                        Rag.Node processNode = new Rag.Node(process, true);
                        if (nResource.edgeValue == null) {
                            System.out.println("3");
                            nResource.edgeValue = processNode;
                            System.out.println("Process " + process + " needs resource " + resource + "- " + "Resource " + resource + " is allocated to process " + process + ".");

                        }
                        if (nResource.edgeValue != null) {
                            System.out.println("4");
                            processNode.edgeValue = nResource;
                            System.out.println("Process " + process + " needs resource " + resource + "- " + "Process " + process + " must wait");
                        }
                        nodeList.add(processNode);
                        for (Rag.Node n : nodeList) {
                            boolean isDeadlock = r.didCauseCycle(n);
                            if (isDeadlock) {
                                //deadlock
                                System.out.println("Deadlock");
                                r.printDeadlockNodes();
                            }
                        }

                    }
                    if (nProcess != null && nResource == null) {
                        Rag.Node resourceNode = new Rag.Node(resource);
                        if (nProcess.edgeValue == null) {
                            resourceNode.edgeValue = nProcess;
                            System.out.println("1");
                            System.out.println("Process " + process + " needs resource " + resource + "- " + "Process " + process + " must wait");
                        }
                        if (nProcess.edgeValue != null) {
                            System.out.println("2");
                            nProcess.edgeValue = nProcess;
                            System.out.println("Process " + process + " needs resource " + resource + "- " + "Process " + process + " must wait");
                        }
                        for (Rag.Node n : nodeList) {
                            boolean isDeadlock = r.didCauseCycle(n);
                            if (isDeadlock) {
                                //deadlock
                                System.out.println("Deadlock");
                                r.printDeadlockNodes();
                            }
                        }

                    }

                    if (nProcess != null && nResource != null) {
                        Rag.Node resourceNode = new Rag.Node(resource);
                        if (nProcess.edgeValue == null) {
                            resourceNode.edgeValue = nProcess;
                            System.out.println("Process " + process + " needs resource " + resource + "- " + "Process " + process + " must wait");
                        }
                        if (nProcess.edgeValue != null) {
                            nProcess.edgeValue = nProcess;
                            System.out.println("Process " + process + " needs resource " + resource + "- " + "Process " + process + " must wait");
                        }
                        for (Rag.Node n : nodeList) {
                            boolean isDeadlock = r.didCauseCycle(n);
                            if (isDeadlock) {
                                System.out.println("Deadlock");
                                r.printDeadlockNodes();
                            }
                        }

                    }

                } else if (status.equals("R")) {
                    //nResource.edgeValue = null;
                    nProcess.edgeValue = null;
                    nResource.edgeValue = nProcess;
                    System.out.println("Process " + process + "releases " + resource + "-" + "Process " + resource + " is now free.");

                }

            }
            br.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println(ex.getMessage());
        }

    }

}
