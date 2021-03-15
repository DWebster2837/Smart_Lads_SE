package sample;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

public class Account {
    private static Account[] accounts;
    private int userID;
    private String password;
    private String username;
    private String email;

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
}
