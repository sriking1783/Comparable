import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileWriter;
import java.io.BufferedWriter;
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
        System.out.println(args[0]);
        switch(args[0]){
            case "init":
                createFolderAndCommit();
                break;
            case "add":
                break;
            case "commit":
                break;
            case "log":
                break;
            case "global-log":
                break;
            case "find":
                break;
            case "status":
                System.out.println(getCurrentCommit());
                System.out.println(getCurrentTree());
                checkFiles();
                break;
            case "checkout":
                break;
            case "branch":
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

    private static String has_files_changed() {
        return null;
    }

    private static Commit getCurrentCommit() {
        return Commit.getHead();
    }

    private static Tree getCurrentTree() {
        return Commit.getHead().getTree();
    }

    private static void checkFiles() {
        File folder = new File(System.getProperty("user.dir"));
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println("File " + listOfFiles[i].getName());
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
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
        new File(System.getProperty("user.dir")+"/"+".gitlet"+"/refs").mkdir();
        new File(System.getProperty("user.dir")+"/"+".gitlet"+"/refs/remotes").mkdir();
        new File(System.getProperty("user.dir")+"/"+".gitlet"+"/refs/remotes/origin").mkdir();

    }

    private static void saveHead() {
        try {
            File file = new File(System.getProperty("user.dir")+"/"+".gitlet"+"/refs/remotes/origin/master");
            file.createNewFile();
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(Integer.toString(head.getCommitId()));
            bw.close();
        } catch(IOException i)
        {
            i.printStackTrace();
        }
    }

    private static void stageFile(String fileName) {
        File file = new File(System.getProperty("user.dir")+"/"+fileName);
        if(file.exists()) {
            staged_files.put(fileName, 'A');
        }
        else {
            System.out.println("File does not exist");
        }
    }
}
