package sample;

import java.io.*;

public class User implements Serializable {
    private final int userID;
    private Group[] groups;
    private Exercises exercises;
    private Diet diet;
    private Goals goals;
    private Account account;
    private double height_cm, weight_kg;
    private String firstname, lastname;
    public static User curUser;

    public User(int userID, Account account) {
        this.userID = userID;
        this.groups = new Group[]{};
        this.exercises = new Exercises();
        this.diet = new Diet();
        this.goals = new Goals();
        this.account = account;
    }

    public double getHeight_cm() {
        return height_cm;
    }

    public void setHeight_cm(double height_cm) {
        this.height_cm = height_cm;
    }

    public double getWeight_kg() {
        return weight_kg;
    }

    public void setWeight_kg(double weight_kg) {
        this.weight_kg = weight_kg;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    private static User loadUser(File file){
        try{
            FileInputStream finstr = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(finstr);
            User out = (User)in.readObject();
            in.close();
            return out;
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    public static User loadUser(int userID){
        File file = new File("users/" + userID + ".ser");
        return loadUser(file);
    }
}

