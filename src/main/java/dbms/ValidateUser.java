package dbms;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Array;
import java.util.HashMap;
import java.util.Scanner;

import static dbms.Constants.newLineDelimiter;
import static dbms.Constants.pipeDelimiter;

public class ValidateUser {
  String userName;

  public Boolean performUserValidation() {
    System.out.println("Please enter the username");
    Scanner sc = new Scanner(System.in);
    userName = sc.nextLine();
    System.out.println("Please enter the password");
    String password = sc.nextLine();
    String secAnswerInput;
    HashMap<String, String> credMap = new HashMap<String, String>();
    HashMap<String, String> mfaMap = new HashMap<String, String>();
    File credFile = new File("usercredentials.txt");
    try {
      String credFileContent = new Scanner(credFile).useDelimiter("\\Z").next();
      String[] credFileArray = credFileContent.split(newLineDelimiter);
      for (int i = 0; i < credFileArray.length; i++) {
        String[] userInfo = credFileArray[i].split(pipeDelimiter);
        credMap.put(userInfo[0], userInfo[1]);
        mfaMap.put(userInfo[2], userInfo[3]);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    Boolean isValidUser = validateUser(userName, password, credMap);
    if (isValidUser) {
      System.out.println("For additional reasons, please enter the security question");
      System.out.println("Favorite food");
      secAnswerInput = sc.nextLine();
      Boolean isValidMFA = validateTwoFA(secAnswerInput, mfaMap);
      return isValidMFA;
    } else {
      return false;
    }
  }

  public String getUserName() {
    return userName;
  }


  public static boolean validateTwoFA(String secAnswer, HashMap<String, String> mfaMap) {
    if (mfaMap.get("Favorite food").trim().equals(secAnswer)) {
      System.out.println("Provided correct answer");
      return true;
    } else {
      System.out.println("Incorrect MFA answer");
      return false;
    }

  }

  public static boolean validateUser(String userName, String password, HashMap<String, String> credMap) {
    if (credMap.containsKey(userName)) {
      if (credMap.get(userName).equals(password))
        return true;
      else {
        System.out.println("Password Incorrect");
        return false;
      }
    } else {
      System.out.println("NO User found");
      return false;
    }

  }
}
