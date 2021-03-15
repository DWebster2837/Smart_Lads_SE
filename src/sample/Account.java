package sample;

import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.security.MessageDigest;
import java.util.Base64;
import java.io.*;
import java.util.stream.*;

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
    private static Account[] loadAccounts(Path folderPath){
        File[] files;
        try {
            Stream<Path> stream = Files.list(folderPath).filter(Files::isRegularFile);
            PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob: *.txt");
            //stream.filter((f) -> f.toString().toLowerCase().endsWith(".txt"));
            //technically either of these work; im assuming the thingy for the purpose is better.
            files = stream.filter(matcher::matches).map(Path::toFile).toArray(File[]::new);
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

    public static User registerUser(int id, String username, String email, String password){
        Account newAcc = new Account(id, password, username, email);
        User newUser = new User(id, newAcc);
        newAcc.writeAccount(new File("blahdyblah/" + id + ".txt"));
        newUser.writeUser(new File("goingyyoingy/" + id + ".txt"));
        //todo: change the filepaths to actual filepaths
        //todo: change user write function to not take a filepath; other places will need to save the user.
        return newUser;
    }
}
