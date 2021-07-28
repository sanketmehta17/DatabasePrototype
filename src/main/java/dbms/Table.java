package dbms;

import dbms.metadata.TableMeta;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class Table {
    private String name;

    private File file;
    private LinkedList<Row> rows;
    private TableMeta metaData;

    public void create(String name, Database database) throws IOException {
        this.name = name;
        File tablesFolder = FileOperator.getOrCreateFolder(database.getFolder().getPath() + "/tables");
        this.file = FileOperator.getOrCreateFile(tablesFolder.getPath()+ "/"+ name + ".txt");
        this.rows = new LinkedList<>();
        this.metaData = new TableMeta(name, database.getMetaData());
    }

    public void addRow(Row row) {
        rows.addLast(row);
    }

    public void delete() {
        this.file.delete();
    }



}
