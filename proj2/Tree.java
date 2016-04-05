import java.util.HashMap;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.Iterator;
import java.util.Map;
import java.io.File;
import java.sql.Timestamp;
import java.util.Date;
public class Tree implements java.io.Serializable {
    private HashMap<String, String> file_locations;
    private String tree_id;

    public Tree() {
        file_locations = new HashMap<String, String>();
        this.tree_id = new ShaHash().cryptMessage(new Timestamp(new Date().getTime()).toString()+this.toString());
    }

    public Tree(HashMap<String, String> files) {
        file_locations = new HashMap<String, String>();
        this.tree_id = new ShaHash().cryptMessage(new Timestamp(new Date().getTime()).toString()+this.toString());
        setFileLocations(files);
    }

    public void setFileLocations(HashMap<String, String> files) {
          serializeFiles(System.getProperty("user.dir")+"/"+".gitlet"+"/objects", files);
    }

    public static Tree getTree(String tree_id) {
       String filePathString = System.getProperty("user.dir")+"/"+".gitlet"+"/objects/"+tree_id;
       File f = new File(filePathString);
       if(f.exists() && !f.isDirectory()) {
           return deserializeTree(filePathString);
       }
       return null;
    }

    public HashMap<String, String> getFiles() {
        Tree tree = Tree.getTree(this.tree_id);
        return Tree.getTree(this.tree_id).file_locations;
    }

    public void serializeTree(String file_path) {
         try {
             FileOutputStream fileOut =
                        new FileOutputStream(file_path);
             ObjectOutputStream out = new ObjectOutputStream(fileOut);
             out.writeObject(this);
             out.close();
             fileOut.close();
         } catch(IOException i)
         {
             i.printStackTrace();
         }
    }

    public static Tree deserializeTree(String file_path) {
        try {
            FileInputStream fileIn = new FileInputStream(file_path);
            ObjectInputStream ois = new ObjectInputStream(fileIn);
            Tree tree = (Tree) ois.readObject();
            ois.close();
            fileIn.close();

            return (Tree)tree;
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public void serializeFiles(String file_path, HashMap<String, String> file_contents) {
        Iterator it = file_contents.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            try {
                String location = new ShaHash().cryptMessage(pair.getValue().toString());
                FileOutputStream fileOut =
                            new FileOutputStream(file_path+"/"+location);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(pair.getValue());
                this.file_locations.put(pair.getKey().toString(), location);
                out.close();
                fileOut.close();
                it.remove();
            } catch(IOException i)
            {
                i.printStackTrace();
            }
        }

    }

    public static String  deserializeFile(String file_path) {
      String content = null;
      try {
          FileInputStream fileIn = new FileInputStream(file_path);
          ObjectInputStream ois = new ObjectInputStream(fileIn);
          content = (String) ois.readObject();
          ois.close();
          fileIn.close();
          return content;
        } catch(IOException i)
        {
            i.printStackTrace();
        }
        catch(ClassNotFoundException i)
        {
            i.printStackTrace();
        }

        return content;
    }

    public String getTreeId() {
        return this.tree_id;
    }

    /*@Override
    public String toString() {
        if(file_locations != null)
            return String.valueOf(Math.abs(file_locations.hashCode()));
        return null;
    }*/
}
