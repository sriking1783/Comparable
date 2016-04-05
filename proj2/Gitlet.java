import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.*;
import java.util.List;
import java.util.HashSet;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.Iterator;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
public class Gitlet {
    private static HashMap<String, Character> staged_files;
    private static Commit head;
    public static void main(String[] args) {
        staged_files = new HashMap<String, Character>();
        head = null;
        if(args.length == 0) {
            System.out.println("Enter any argument");
            return ;
          }
        instantiateHead();
        switch(args[0]){
            case "init":
                createFolderAndCommit();
                break;
            case "add":
                createFileObjects(args);
                break;
            case "commit":
                createCommit(args[1]);
            case "log":
                System.out.println(showLog());
            case "global-log":
                break;
            case "find":
                break;
            case "status":
                listBranches();
                checkFiles();
                break;
            case "checkout":
                if(checkIfBranch(args[1])) {
                    flipBranch(args[1]);
                    instantiateFiles(args[1]);
                }
                break;
            case "branch":
                createBranch(args[1]);
                break;
            case "rm-branch":
                break;
            case "reset":
                break;
            case "merge":
                break;
            case "rebase":
                break;
            case "i-rebase":
                break;
        }
    }

    private static boolean checkIfBranch(String branch_name) {
        String[] branches = getBranches();
        return Arrays.asList(branches).contains(branch_name);
    }
    private static void listBranches() {
        System.out.println("=== Branches ===");
        String branch = currentBranch();
        String[] branches = getBranches();
        for(String branch_name : branches) {
            if(branch_name.equals(branch)){
              System.out.print("*");
            }
            System.out.println(branch_name);
        }

    }

    private static String[] getBranches() {
      File folder = new File(System.getProperty("user.dir")+"/"+".gitlet"+"/refs/remotes/origin/");
      File[] listOfFiles = folder.listFiles();
      String[] branch_names = new String[listOfFiles.length];
      for(int i = 0; i< listOfFiles.length; i++) {
          branch_names[i] = listOfFiles[i].getName();
      }
      return branch_names;
    }

    private static String currentBranch() {
        try {
            File file = new File(System.getProperty("user.dir")+"/.gitlet/HEAD");
            if(file.exists()) {
                String refs = readFile(System.getProperty("user.dir")+"/.gitlet/HEAD");
                return refs.substring(refs.lastIndexOf("/") + 1, refs.length());
              }
          }catch(IOException e) {
              e.printStackTrace();
          }
          return null;
    }

    private static void flipBranch(String branch_name) {
        try {
            File head_file = new File(System.getProperty("user.dir")+"/"+".gitlet/HEAD");
            FileOutputStream fooStream1 = new FileOutputStream(head_file, false);
            byte[] myBytes1 = ("/refs/remotes/origin/"+branch_name).getBytes();
            fooStream1.write(myBytes1);
            fooStream1.close();
         } catch(IOException e) {
             e.printStackTrace();
         }
    }

    private static void createBranch(String branch_name) {
       try {
            File file = new File(System.getProperty("user.dir")+"/"+".gitlet"+"/refs/remotes/origin/"+branch_name);
            FileOutputStream fooStream = new FileOutputStream(file, false);
            byte[] myBytes = head.getCommitId().getBytes();
            fooStream.write(myBytes);
            fooStream.close();
            flipBranch(branch_name);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static void instantiateHead() {
        File f = new File(System.getProperty("user.dir")+"/.gitlet/refs/remotes/origin/"+currentBranch());
        try {
            if(f.exists()) {
                String commit = readFile(System.getProperty("user.dir")+"/.gitlet/refs/remotes/origin/"+currentBranch());
                head = Commit.getCommit(commit);

            }
        } catch(IOException ie) {
            ie.printStackTrace();
        }
    }

    private static void instantiateFiles(String branch_name) {
        //ignoreFiles
        Commit commit = null;
        File dir = new File(System.getProperty("user.dir")+"/");
        for(File file: dir.listFiles()){
            if(ignoreFiles(file.getName()) && (file.isFile())) {
                file.delete();
            }
        }
        try {
            String commitid = readFile(System.getProperty("user.dir")+"/.gitlet/refs/remotes/origin/"+branch_name);
            System.out.println("Reading from commit : " + commitid);
            commit = Commit.getCommit(commitid);
        }    catch(IOException i)
            {
                i.printStackTrace();
            }

        HashMap<String, String> file_locations = commit.getTree().getFiles();
        Iterator it = file_locations.entrySet().iterator();
        String file_content ;
        File file;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            try {
                file_content = Tree.deserializeFile(System.getProperty("user.dir")+"/"+".gitlet/objects/"+pair.getValue());
                file = new File(System.getProperty("user.dir")+"/"+pair.getKey());

                FileOutputStream fooStream = new FileOutputStream(file, false);
                byte[] myBytes = file_content.getBytes();
                fooStream.write(myBytes);
                fooStream.close();

            }
            catch(IOException i)
            {
                i.printStackTrace();
            }
        }
    }

    private static String showLog() {
        try {
            String commit = readFile(System.getProperty("user.dir")+"/.gitlet/refs/remotes/origin/"+currentBranch());
            Commit temp = Commit.getCommit(commit);
            String log = "";
            while(temp != null) {
                log = log + temp.commitSummary();
                temp = temp.getPrevious();
            }
            return log;
          } catch(IOException ie) {
              ie.printStackTrace();
          }

          return null;
    }

    private static void createCommit(String message) {
        HashMap<String, String> files = new HashMap<String, String>();
        Set<String> staged_files = new HashSet<String>();
        staged_files = stagedFiles();
        File folder = new File(System.getProperty("user.dir")+"/"+".gitlet"+"/objects/staged");
        File[] listOfFiles = folder.listFiles();
        Commit commit = null;

        try {
            for(String staged_file : staged_files) {
                files.put(staged_file, readFile(staged_file));
            }
        } catch(IOException i)
        {
            i.printStackTrace();
        }
        for(File file : listOfFiles) {
            file.delete();
        }
        commit = new Commit(message, head, currentBranch() ,files);
        System.out.println("New Commit : "+commit.getCommitId());
        commit.serializeCommit(System.getProperty("user.dir")+"/"+".gitlet"+"/objects/"+commit.getCommitId());
        writeHead(commit);
    }
    private static Commit getCurrentCommit() {
        return Commit.getHead(currentBranch());
    }

    private static Tree getCurrentTree() {
        return Commit.getHead(currentBranch()).getTree();
    }

    private static HashMap<String, String> getFiles() {
        return Commit.getHead(currentBranch()).getTree().getFiles();
    }

    private static Set<String> stagedFiles() {
        Set<String> files = new HashSet<String>();
        try
        {
            File folder = new File(System.getProperty("user.dir")+"/"+".gitlet"+"/objects/staged");
            File[] listOfFiles = folder.listFiles();
            if(listOfFiles == null || listOfFiles.length == 0) {
                return files;
            }
            FileInputStream fis = new FileInputStream(listOfFiles[0]);
            ObjectInputStream ois = new ObjectInputStream(fis);
            files = (Set<String>) ois.readObject();
            ois.close();
            fis.close();
         }catch(IOException ioe){
             ioe.printStackTrace();
          }
          catch(ClassNotFoundException c){
             c.printStackTrace();
          }
        return files;
    }

    private static void createFileObjects(String[] args) {
        String file_content;
        Set<String> files = new HashSet<String>();
        String file_names = "";
        for(int i = 1; i < args.length; i++) {
            files.add(args[i]);
            file_names = file_names+", ";
        }
        try{
            // Serialize data object to a file
            String file_name = new ShaHash().cryptMessage(file_names);
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(System.getProperty("user.dir")+"/"+".gitlet"+"/objects/staged/"+file_name));
            out.writeObject(files);
            out.close();

            // Serialize data object to a byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream() ;
            out = new ObjectOutputStream(bos) ;
            out.writeObject(files);
            out.close();

            // Get the bytes of the serialized object
            byte[] buf = bos.toByteArray();
        } catch (IOException e) {
              e.printStackTrace();
        }
    }

    private static void checkFiles() {
        File folder = new File(System.getProperty("user.dir"));
        Set<String> unstaged_files = new HashSet<String>();
        Set<String> new_files = new HashSet<String>();
        Set<String> staged_files = new HashSet<String>();
        File[] listOfFiles = folder.listFiles();
        unstaged_files = unstagedFiles();
        new_files = newFiles();
        staged_files = stagedFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile() && (ignoreFiles(listOfFiles[i].getName()))) {

                if(unstaged_files.contains(listOfFiles[i].getName())) {
                    System.out.println("=== UNSTAGED ===");
                    System.out.println("File " + listOfFiles[i].getName());
                }
                else if(staged_files.contains(listOfFiles[i].getName())) {
                    System.out.println("=== Staged Files ===");
                    System.out.println("File " + listOfFiles[i].getName());
                }
                else if(new_files.contains(listOfFiles[i].getName())) {
                    System.out.println("=== UN ADDED ===");
                    System.out.println("File " + listOfFiles[i].getName());
                }

            } /*else if (listOfFiles[i].isDirectory() && (ignoreFiles(listOfFiles[i].getName()))) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }*/
        }
    }

    /*private static Set<String> checkExistingFiles() {
        Set<String> new_files = new HashSet<String>();
        File folder_objects = new File(System.getProperty("user.dir")+"/"+".gitlet/objects/");
        File[] listOfFilesObjects = folder_objects.listFiles();
        for (int i = 0; i < listOfFilesObjects.length; i++) {
            System.out.println(listOfFilesObjects[i].getName());
        }

        File folder = new File(System.getProperty("user.dir")+"/");
        File[] listOfFiles = folder.listFiles();
        try {
            String commit = readFile(System.getProperty("user.dir")+"/.gitlet/refs/remotes/origin/master");
            head = Commit.getCommit(commit);

            HashMap<String, String> file_locations = head.getTree().getFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                if(!listOfFiles[i].isDirectory() && ignoreFiles(listOfFiles[i].getName())) {
                    if(file_locations != null && !file_locations.containsKey(listOfFiles[i].getName())) {
                        System.out.println(listOfFiles[i].getName());
                    }
                }
            }
          } catch(IOException ie) {
                ie.printStackTrace();
          }
        return new_files;
    }*/

    private static Set<String> newFiles() {
        //checkExistingFiles();
        Set<String> new_files = new HashSet<String>();
        HashMap<String, String> file_locations = getFiles();
        File folder = new File(System.getProperty("user.dir"));
        File[] listOfFiles = folder.listFiles();
        if(file_locations == null || file_locations.isEmpty()) {
            for (int i = 0; i < listOfFiles.length; i++) {
                new_files.add(listOfFiles[i].getName());
            }
            return new_files;
        }
        Iterator it = file_locations.entrySet().iterator();

        for (int i = 0; i < listOfFiles.length; i++) {
            if(!file_locations.containsKey(listOfFiles[i].getName())) {
                new_files.add(listOfFiles[i].getName());
            }
        }

        return new_files;
    }

    private static Set<String> unstagedFiles() {
        //HashMap<String, String> file_locations = getFiles();
        Set<String> unstaged_files = new HashSet<String>();
        Commit temp;
        try {
            String commit = readFile(System.getProperty("user.dir")+"/.gitlet/refs/remotes/origin/"+currentBranch());
            temp = Commit.getCommit(commit);
            HashMap<String, String> file_locations = head.getTree().getFiles();
            if(file_locations == null || file_locations.isEmpty()) {
                return unstaged_files;
            }
            Iterator it = file_locations.entrySet().iterator();
            String file_content, current_file_content;
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                try {
                        file_content = Tree.deserializeFile(System.getProperty("user.dir")+"/"+".gitlet/objects/"+pair.getValue());
                        current_file_content = readFile(System.getProperty("user.dir")+"/"+pair.getKey());
                        if(!current_file_content.equals(file_content)){
                            if(file_locations != null ) {
                                unstaged_files.add(pair.getKey().toString());
                            }
                        }
                }
                catch(IOException i)
                {
                    i.printStackTrace();
                }
            }
          }
          catch(IOException i)
          {
                i.printStackTrace();
          }
        return unstaged_files;
    }

    public static String readFile(String filePath) throws IOException  {
        byte[] encoded = Files.readAllBytes(Paths.get(filePath));
        return new String(encoded, StandardCharsets.UTF_8);
    }

    private static void createFolderAndCommit() {
        File dir = new File(System.getProperty("user.dir")+"/"+".gitlet");
        if(!dir.exists() && head == null) {
            dir.mkdir();
            createFolders();
            head = new Commit();
            head.serializeCommit(System.getProperty("user.dir")+"/"+".gitlet"+"/objects/"+head.getCommitId());
            saveHead();
        }
        else {
            System.out.println("A gitlet version control system already exists in the current directory.");
        }
    }

    private static void createFolders() {
        File dir = new File(System.getProperty("user.dir")+"/"+".gitlet");
        new File(System.getProperty("user.dir")+"/"+".gitlet"+"/objects").mkdir();
        new File(System.getProperty("user.dir")+"/"+".gitlet"+"/objects/staged").mkdir();
        new File(System.getProperty("user.dir")+"/"+".gitlet"+"/refs").mkdir();
        new File(System.getProperty("user.dir")+"/"+".gitlet"+"/refs/remotes").mkdir();
        new File(System.getProperty("user.dir")+"/"+".gitlet"+"/refs/remotes/origin").mkdir();

    }

    private static void writeHead(Commit commit) {
        try {
            File file = new File(System.getProperty("user.dir")+"/"+".gitlet"+"/refs/remotes/origin/"+currentBranch());
            System.out.println("Writing to file "+file.getName());
            FileOutputStream fooStream = new FileOutputStream(file, false);
            byte[] myBytes = commit.getCommitId().getBytes();
            fooStream.write(myBytes);
            fooStream.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveHead() {
        try {
            File file = new File(System.getProperty("user.dir")+"/"+".gitlet"+"/refs/remotes/origin/master");
            File head_file = new File(System.getProperty("user.dir")+"/"+".gitlet"+"/HEAD");
            file.createNewFile();
            head_file.createNewFile();
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(head.getCommitId());
            bw.close();

            FileWriter fw1 = new FileWriter(head_file.getAbsoluteFile());
            BufferedWriter bw1 = new BufferedWriter(fw1);
            bw1.write("refs/remotes/origin/master");
            bw1.close();
        } catch(IOException i)
        {
            i.printStackTrace();
        }
    }

    private static boolean ignoreFiles(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        return (!extension.equals("java") && !extension.equals("class") && !(extension.equals("gitlet")));
    }
}
