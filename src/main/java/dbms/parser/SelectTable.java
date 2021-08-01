package dbms.parser;

import dbms.Database;
import dbms.Datasource;
import dbms.Table;
import dbms.metadata.TableMeta;

import java.util.Arrays;
import java.util.Set;
import java.util.regex.Matcher;

public class SelectTable extends Parser {


  public SelectTable(Datasource datasource) {
    super(datasource);
  }

  @Override
  protected String getRegex() {
    return "select *(.*) *from *(.*) *(where *(.*))*";
  }

  @Override
  protected void execute(Matcher matcher, String query) {
    Database currentDatabase = datasource.getCurrentDatabase();
    String tableName = matcher.group(2).trim();
    String conditions [] = new String[2];
    if(tableName.contains("where")) {
      conditions[0] = tableName.substring(tableName.indexOf("where")+5).trim();
      tableName = tableName.substring(0,tableName.indexOf("where")-1);
    }
    Table table = currentDatabase.getTable(tableName);
    TableMeta tableMeta = table.getMetaData();
    Set<String> tableColumns = tableMeta.getColumns();
    System.out.println("**** Printing table :" + tableName + "*****");
    if (matcher.group(1).trim().contains("*") && !matcher.group(2).trim().contains("where")) {
      //select all
      for (String key : tableColumns) {
        System.out.print(key + "  ");
      }
      System.out.println("\n" + table.toDBString().replace("~~", "  "));
    } else if (matcher.group(2).trim().contains("where")) {
      //print with where condition
      String keyValue[] = conditions[0].split("=");
      String key = keyValue[0];
      String value = keyValue[1];
      System.out.println("**** Printing the values for the key&value pair*****"+key +" "+value);
    }
    System.out.println("**** End of printing the table *****");
  }

}
