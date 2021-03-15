package sample;

import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.security.MessageDigest;
import java.util.Base64;
import java.io.*;

public class Account implements Serializable{
    private static Account[] accounts = loadAccounts(Paths.get(""));
    private final int userID;
    private final String password;
    private final String username;
    private final String email;

    public Account(int userID, String password, String username, String email) {
        this.userID = userID;
        this.password = hashString(password);
        this.username = username;
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
        return password.equals(hashString(pass));
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
    private Account loadAccount(File file){
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
}
