package dbms;

import dbms.logger.QueryLog;
import dbms.parser.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class Transaction {

  private Datasource datasource;

  private List<Parser> parserList;

  private Logger logger;


  public Transaction(Datasource datasource, Logger logger, List<Parser> parserList) {
    this.datasource = datasource;
    this.parserList = parserList;
    this.logger = logger;
  }

  public void startTransaction(){
    System.out.println("You are in transaction operation");
    System.out.println(">> TRANSACTION HAS BEGUN");
    System.out.println(">> Please enter your query");
    Scanner scanner= new Scanner(System.in);
    String query = "";
    List<String> queries = new ArrayList<>();
    while (true){
      query = scanner.nextLine();
      if (query.equalsIgnoreCase("commit")){
        doParseAndExecuteTransaction(queries);
        break;
      }
      if (query.equalsIgnoreCase("rollback")){
        break;
      }
      queries.add(query);
      System.out.println("Enter next query OR commit OR rollback: ");
    }

  }

  private void doParseAndExecuteTransaction(List<String> queries){

    for (String inputQuery : queries) {
      logger.info(new QueryLog(inputQuery, Timestamp.from(Instant.now())).toString());
      Boolean executed = false;
      for (int i = 0; i < parserList.size(); i++) {
        Parser parser = parserList.get(i);
        Boolean isValidQueryExecute = parser.isValid(inputQuery);
        if (isValidQueryExecute) {
          executed = true;
          break;
        }
      }
      if (!executed) System.out.println("Invalid query");
    }
  }

}
