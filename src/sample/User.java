package sample;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class User {
    private final int userID;
    private Group[] groups;
    private Exercises exercises;
    private Diet diet;
    private Goals goals;
    private Account account;

    public User(int userID, Account account) {
        this.userID = userID;
        this.groups = new Group[]{};
        this.exercises = new Exercises();
        this.diet = new Diet();
        this.goals = new Goals();
        this.account = account;
    }

    public int getUserID() {
        return userID;
    }

    public Group[] getGroups() {
        return groups;
    }

    public Exercises getExercises() {
        return exercises;
    }

    public Diet getDiet() {
        return diet;
    }


    public Goals getGoals() {
        return goals;
    }

    public Account getAccount() {
        return account;
    }

    public void writeUser(File file){
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
}
