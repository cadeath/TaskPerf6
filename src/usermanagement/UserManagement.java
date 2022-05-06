package usermanagement;

import java.io.File; 
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.Scanner;
import java.util.InputMismatchException;


public class UserManagement {
    
    public static String DB_FILE = "records.txt";
    public static String LOGIN_SUCCESS = "Successfully logged in";
    public static String LOGIN_FAIL = "Incorrect username or password";
    
    public static void main(String[] args) {        
        String username = "";
        String password = "";
        
        createDB();
        
        int c = systemPrompt();
        if(c == 3) {
            System.out.println("Closing...");
            System.exit(0);
        }
        
        Scanner inputScan = new Scanner(System.in);        
        if(c == 2) System.out.println("Register===================");        
        if(c == 1) System.out.println("Login======================");
        
        System.out.print("Username: ");
        username = inputScan.next();

        System.out.print("Password: ");
        password = inputScan.next();
        
        if(c == 2) {
            addAccount(username,password);
        }
        
        if(c == 1) {
            if (loginAccount(username,password)) {
                System.out.println(LOGIN_SUCCESS);
            } else {
                System.out.println(LOGIN_FAIL);
            }
        }
        
    }
        
    private static int systemPrompt() {
        System.out.println("=========WELCOME===========");
        System.out.println("1. Login        2. Register");
        System.out.println("3. Exit");
        
        Scanner inputScan = new Scanner(System.in);
        
        while(true) {
            System.out.print("> ");
            
            try {
                int userInput = inputScan.nextInt();
                
                if(userInput <= 3) {
                    return userInput;
                } 
            } catch (InputMismatchException e) {
                System.out.println("Invalid input");
//                e.printStackTrace();
                
                break;
            }
        }
        return 3;
    }
    
    private static void createDB() {
        try {
            File db = new File(DB_FILE);
            if (db.createNewFile()) {
                System.out.println("Database created: " + db.getName());
            }else {
                System.out.println("Database is ready.");
            }
            
        } catch (IOException err) {
            System.out.println("An error occurred.");
            err.printStackTrace();
        }
    }
    
    private static void addAccount(String u, String p) {
        try {
            FileWriter newUser = new FileWriter(DB_FILE,true);
        
            String data = u + ":" + p + "\n";

            newUser.write(data);
            newUser.close();

            System.out.println(u + " is added.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    private static boolean loginAccount(String u, String p) {
        boolean isLogin = false;
        
        try {
            File db = new File(DB_FILE);
            Scanner cur = new Scanner(db);
            
            while(cur.hasNextLine()) {
                String data = cur.nextLine();
                
                String[] userData = data.split(":");
                String recUser = userData[0].trim();
                
                if (recUser.equals(u)) {
                    String recPass = userData[1].trim();
                    
                    if (recPass.equals(p)) {
                        isLogin = true;
                    } else {
//                        System.out.println("[user x] " + recPass + "|" + p);
                    }
                } else {
//                    System.out.println("[pass x] " + recUser + "|" + u);
                }
            }
            
            cur.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            
            System.exit(0);
        }
        
        return isLogin;
    }
    
}
