package sample;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.*;
public class Group {
    private String Name;
    private LinkedList<Member> Members;
    private int groupID;

    public Group(String name, int groupID) {
        Name = name;
        this.groupID = groupID;
        Members = new LinkedList<>();
    }

    public String getName(){
        return Name;
    }

    public LinkedList<Member> getMembers() {
        return Members;
    }

    public int getGroupID() {
        return groupID;
    }

    void addMember(Member newMember){
        Members.add(newMember);
    }

    public Member findMember(String Username){
        return Members.stream().filter(x->x.userName.equals(Username)).findFirst().orElse(null);
    }
    public Member findMember(int userID){

        return Members.stream().filter(x->x.userID == userID).findFirst().orElse(null);
    }


    private static Group loadGroup(File file){
        try{
            FileInputStream finstr = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(finstr);
            Group out = (Group)in.readObject();
            in.close();
            return out;
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }
/*    private static Account loadAccount(File file){
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
    }*/
}

class Member{
    public int userID;
    public String userName;
    public int status;
}
