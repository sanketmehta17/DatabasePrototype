package dbms;

import dbms.metadata.TableMeta;

import java.io.File;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import static dbms.Constants.databasesFolder;
import static dbms.Constants.newLineDelimiter;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;

public class Table {
    private File file;
    private LinkedList<Row> rows;
    private TableMeta metaData;

    public Table(TableMeta tableMeta) {
        File tablesFolder = FileOperator.getOrCreateFolder(databasesFolder + "/" + tableMeta.getDbName() +  "/tables");
        this.file = FileOperator.getOrCreateFile(tablesFolder.getPath()+ "/"+ tableMeta.getName() + ".txt");
        this.metaData = tableMeta;
    }

    public void create() {
        this.rows = new LinkedList<>();
    }

    public TableMeta getMetaData() {
        return metaData;
    }

    public void addRow(Row row) {
        rows.addLast(row);
        FileOperator.appendToFile(file.getPath(), row.toDBString());
    }

    public void populate() {
        this.fromString(String.join(newLineDelimiter, FileOperator.getFileContents(file.getPath())));
    }

    public void delete() {
        this.file.delete();
    }

    public void deleteContent(){
        try {
            PrintWriter printWriter = new PrintWriter(this.file);
            printWriter.print("");
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String toDBString() {
        final List<String> collect = rows.stream().map(Row::toDBString).collect(toList());
        return String.join(newLineDelimiter, collect);
    }

    public void fromString(String string) {
        rows = stream(string.split(newLineDelimiter)).map(s -> new Row().fromString(s))
                .collect(toCollection(LinkedList::new));
    }

}
