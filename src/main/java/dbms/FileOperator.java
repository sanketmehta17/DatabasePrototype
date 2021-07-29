package dbms;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static dbms.Constants.*;

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

    public static void updateFile(String filePath, String oldData, String updatedString) {
        try {
            Path path = Paths.get(filePath);
            List<String> lines = Files.readAllLines(path);
            int index=0;
            for (String line: lines) {
                if(line.equals(oldData)){
                    break;
                }
                index++;
            }
            lines.set(index, updatedString);
            Files.write(path, lines);
        } catch (IOException e) {
            System.out.println("Update error");
        }

    }


    public static List<String> getFileContents(String filePath) {
        List<String> contents = new ArrayList<>();
        try {
            Path path = Paths.get(filePath);
            contents = Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println("contentLoading Error");
        }
        return contents;
    }

    public static void addToFile(String filePath, List<String> lines) {
        try {
            Path path = Paths.get(filePath);
            Files.write(path, lines);
        } catch (IOException e) {
            System.out.println("add to file failed");
        }
    }
    private FileOperator() {}
}
