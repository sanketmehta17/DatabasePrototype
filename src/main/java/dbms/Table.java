package dbms;

import dbms.metadata.TableMeta;

import java.io.File;
import java.util.LinkedList;

import static dbms.Constants.databasesFolder;

public class Table {
    private File file;
    private LinkedList<Row> rows;
    private TableMeta metaData;

    public void create(TableMeta tableMeta) {
        File tablesFolder = FileOperator.getOrCreateFolder(databasesFolder + "/" + tableMeta.getDbName() +  "/tables");
        this.file = FileOperator.getOrCreateFile(tablesFolder.getPath()+ "/"+ tableMeta.getName() + ".txt");
        this.rows = new LinkedList<>();
        this.metaData = tableMeta;
    }

    public void addRow(Row row) {
        rows.addLast(row);
    }

    public void delete() {
        this.file.delete();
    }

}
