package dbms;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static dbms.Constants.mandatoryFiles;
import static dbms.Constants.mandatoryFolders;

public class FileOperator {

    public static void createInitialFilesFolders() {
        for(String mandatoryFolder : mandatoryFolders) {
            getOrCreateFolder(mandatoryFolder);
        }
        for (String mandatoryFile: mandatoryFiles) {
            getOrCreateFile(mandatoryFile);
        }

    }

    public static File getOrCreateFolder(String path) {
        File file = new File(path);
        if(!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static File getOrCreateFile(String path) {
        File file = new File(path);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.exit(1);
            }
        }
        return file;
    }

    public static void deleteFolderOrFile(File file) {
        file.delete();
    }

    public static void appendToFile(String path, String row) {
        try{
            FileWriter fileWriter = new FileWriter(path, true);
            fileWriter.append(row);
            fileWriter.append("\n");
            fileWriter.close();
        } catch (Exception e){
            System.out.println("Append Error");
        }
    }
    private FileOperator() {}
}
