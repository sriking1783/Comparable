import java.util.ArrayList;
import java.util.HashMap;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.File;
public class Commit implements java.io.Serializable {
    private Commit previous;
    private String message;
    private int commit_id;
    private String branch_name;
    private Tree tree;

    public Commit() {
        this("initial commit", null, "master", null);
    }

    public Commit(String message, Commit previous, String branchName, HashMap<String, String> file_contents) {
        this.message = message;
        this.previous = previous;
        this.commit_id = Math.abs(this.hashCode());
        this.branch_name = branchName;
        this.tree = new Tree();
        this.tree.serializeTree(System.getProperty("user.dir")+"/"+".gitlet"+"/objects/"+this.tree.getTreeId());
        this.tree.serializeFiles(System.getProperty("user.dir")+"/"+".gitlet"+"/objects", file_contents);
    }

   public int getCommitId() {
       return this.commit_id;
   }

   public Tree getTree() {
       return this.tree;
   }

   public static Commit getHead() {
       String filePathString = System.getProperty("user.dir")+"/"+".gitlet"+"/refs/remotes/origin/master";
       BufferedReader br = null;
       try {
           String sCurrentLine;

           br = new BufferedReader(new FileReader(filePathString));
           while ((sCurrentLine = br.readLine()) != null) {
               return getCommit(Integer.parseInt(sCurrentLine));
           }
       } catch (IOException e) {
              e.printStackTrace();
       }
       return null;
   }

   public static Commit getCommit(int commit_id) {
       String filePathString = System.getProperty("user.dir")+"/"+".gitlet"+"/objects/"+Integer.toString(commit_id);
       File f = new File(filePathString);
       if(f.exists() && !f.isDirectory()) {
           return deserializeCommit(filePathString);
       }
       return null;
   }

   public static Commit deserializeCommit(String file_path) {
       try {
           FileInputStream fileIn = new FileInputStream(file_path);
           ObjectInputStream ois = new ObjectInputStream(fileIn);
           Commit commit = (Commit) ois.readObject();
           ois.close();
           fileIn.close();
           return commit;
       }
       catch(Exception ex) {
           ex.printStackTrace();
       }
       return null;
   }

   public void serializeCommit(String file_path) {
        try {
            FileOutputStream fout = new FileOutputStream(file_path);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(this);
            oos.close();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }

}
