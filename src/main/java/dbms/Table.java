package dbms;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class Table implements DatasourceEntity{
    private String name;
    private File file;
    private LinkedList<Row> rows;

    @Override
    public void create(String name, String basePath) throws IOException {
        this.name = name;
        this.file = new File(basePath + "/tables/" +name+".txt");
        this.file.createNewFile();
        this.rows = new LinkedList<>();
    }

    public void addRow(Row row) {
        rows.addLast(row);
    }

    @Override
    public void delete() {
        this.file.delete();
    }
}
