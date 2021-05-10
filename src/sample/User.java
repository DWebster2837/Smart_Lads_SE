package sample;

import java.io.*;

public class User implements Serializable {
    private final int userID;
    private Group[] groups;
    private Exercises exercises;
    private Diet diet;
    private Goals goals;
    private Account account;
    public static User curUser;

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

    private void writeUser(File file){
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

    public void saveUser(){
        File file = new File("users/" + userID + ".ser");
        writeUser(file);
    }
}

