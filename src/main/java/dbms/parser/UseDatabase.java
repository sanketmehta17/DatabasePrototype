package dbms.parser;

import dbms.Datasource;
import java.util.regex.Matcher;

public class UseDatabase extends Parser{
  public UseDatabase(Datasource datasource) {
    super(datasource);
  }

  @Override
  protected String getRegex() {
    return "use *(.*) ";
  }

  @Override
  protected void execute(Matcher matcher, String query) {
    String databaseName = matcher.group(1).trim();
    datasource.setCurrentDatabase(databaseName);
  }
}
