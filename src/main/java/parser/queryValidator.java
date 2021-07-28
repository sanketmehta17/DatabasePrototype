package parser;

import dbms.Datasource;
import dbms.metadata.DatabaseMeta;
import dbms.metadata.TableMeta;

import java.sql.Struct;

public class queryValidator {

  dbms.Datasource datasource;
  String[] split;

  public queryValidator(){
    datasource = new Datasource();
  }

  public Boolean validateQuery(String query){

    split = query.split(" ");
    //for use and create query
    if (split[0].equalsIgnoreCase("use")){
      return validateUse(query);
    }else if (split[0].equalsIgnoreCase("create")){
      return validateCreate(query);
    }
    return false;

  }

  private Boolean validateCreate(String query) {
    return true;
  }

  private Boolean validateUse(String query) {
    if (query.matches("[a-zA-z]+\s[a-zA-Z0-9]+;$")){
      if (validateMetadata(query.replaceAll(";", " "))){
        return true;
      }else {
        System.out.println("Database does not exist!");
        return false;
      }
    }else {
      System.out.println("There is syntax error in your query. Make sure you use correct syntax!");
      return false;
    }
  }

  private Boolean validateMetadata(String query) {

    split = query.split(" ");
    return datasource.getDatabase(split[1]);

  }

}
