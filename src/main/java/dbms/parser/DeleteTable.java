package dbms.parser;

import dbms.*;
import java.util.regex.Matcher;

import static java.util.Objects.isNull;

public class DeleteTable extends Parser{
  public DeleteTable(Datasource datasource) {
    super(datasource);
  }

  @Override
  protected String getRegex() {
    return "delete from +(.+)";
  }

  private void checkTableExist(Table table) throws Exception {
    if (isNull(table)) {
      throw new Exception("Invalid table");
    }
  }

  @Override
  protected void execute(Matcher matcher, String query) {
    try {
      Database currentDatabase = datasource.getCurrentDatabase();
      String tableName = matcher.group(1).trim();
      Table table = currentDatabase.getTable(tableName);

      checkTableExist(table);
      table.deleteContent();

    } catch (Exception ex) {
      System.out.println("Invalid query for delete");
    }
  }
}
