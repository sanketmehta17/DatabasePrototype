package dbms;


public class Main {
    public static void main(String args[]) {
        ValidateUser user = new ValidateUser();
        Boolean isUserValidUser = user.performUserValidation();
        if(isUserValidUser) {
            System.out.println(">>>You are signed in as:"+user.getUserName());
        } else {
            System.out.println(">> Error in login <<");
        }
    }
}
