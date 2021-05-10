package sample;

import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.io.*;
import java.util.stream.*;

public class Account implements Serializable{
    private static Account[] accounts = loadAccounts();
    private final int userID;
    private final String password;
    private final String username;
    private String email;

    public Account(int userID, String password, String username, String email) {
        this.userID = userID;
        this.password = hashString(password);
        this.username = username;
        this.email = email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    //public Member getMember(){
    //    return new Member();
    //}
    //Member not implemented yet

    private static String hashString(String input){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashed = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashed);
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public boolean checkPassword(String pass){
        String hash = hashString(pass);
        return password.equals(hash);
    }

    private void writeAccount(File file){
        try{
            FileOutputStream foutstr = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(foutstr);
            out.writeObject(this);
            out.flush();
            out.close();
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public void saveAccount(){
        File file = new File("accounts/" + userID + ".ser");
        writeAccount(file);
    }

    private static Account loadAccount(File file){
        try{
            FileInputStream finstr = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(finstr);
            Account out = (Account)in.readObject();
            in.close();
            return out;
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    public static Account loadAccount(int userID){
        File file = new File("accounts/" + userID + ".ser");
        return loadAccount(file);
    }

    private static Account[] loadAccounts(){
        File[] files;
        try {
            Path folderPath = Paths.get("accounts");
            Stream<Path> stream = Files.list(folderPath).filter(Files::isRegularFile);
            PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**.ser");
            //stream.filter((f) -> f.toString().toLowerCase().endsWith(".txt"));
            //technically either of these work; im assuming the thingy for the purpose is better.
            Stream<File> matches = stream.filter(matcher::matches).map(Path::toFile);
            files = matches.toArray(File[]::new);
            stream.close();
        }
        catch(Exception e){ throw new RuntimeException(e);}
        Account[] accarr = new Account[files.length];
        for(int i = 0; i < files.length; i++){
            File f = files[i];
            accarr[i] = loadAccount(f);
        }
        return accarr;
    }

    public static boolean attemptLogin(String username, String password){
        //return null on fail
        Account attempt = Arrays.stream(accounts).filter(x->x.username.equals(username)).findFirst().orElse(null);
        if(attempt == null){
            return false;
        }
        if(attempt.checkPassword(password)){
            User.curUser = User.loadUser(attempt.userID);
            return true;
        }
        else{
            return false;
        }
    }

    public static User registerUser(String username, String email, String password){
        //guarantee unique id
        int id = 0;
        boolean taken = true;
        while(taken){
            id++;
            int finalId = id;
            taken = Arrays.stream(accounts).anyMatch(x->x.userID == finalId);
        }

        Account newAcc = new Account(id, password, username, email);
        User newUser = new User(id, newAcc);
        newAcc.writeAccount(new File("accounts/" + id + ".ser"));
        newUser.saveUser();
        User.curUser = newUser;
        return newUser;
    }
}
