package dbms;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class Constants {
    public static final String runDirectoryBase = "src/applicationData";
    public static final String logFile = "src/db.log";
    public static final String sqlDump = "src/dump.sql";
    public static final String databasesFolder = runDirectoryBase + "/databases";
    public static final String metaDataFolder = runDirectoryBase+ "/metaData";
    public static final String databaseMetaDataFile = metaDataFolder + "/databaseMeta.txt";
    public static final String tablesMetaDataFile = metaDataFolder + "/tableMeta.txt";
    public static final List<String> mandatoryFolders = Arrays.asList(databasesFolder, metaDataFolder);
    public static final List<String> mandatoryFiles = Arrays.asList(databaseMetaDataFile, tablesMetaDataFile);

    public static final String delimiter = "~~";
    public static final String secondaryDelimiter = ",";
    public static final String ternaryDelimiter = "|";
    public static final String newLineDelimiter = "\n";
    public static final String pipeDelimiter="\\|";
    public static final String escapeSequence = "\\";

}
