package sample;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

public class Group implements Serializable{
    private String Name;
    private LinkedList<Member> Members;
    private int groupID;
    private static Group[] groups = loadGroups();

    public Group(String name) {
        Name = name;
        int id = 0;
        boolean taken = true;
        while(taken){
            id++;
            int finalId = id;
            taken = Arrays.stream(groups).anyMatch(x->x.groupID == finalId);
        }
        groupID = id;
        Members = new LinkedList<>();

    }
    /*    public static User registerUser(String username, String email, String password){
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
    }*/

    public String getName(){
        return Name;
    }

    public LinkedList<Member> getMembers() {
        return Members;
    }

    public int getGroupId() {
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

    private void saveGroup(){
        /*public void saveAccount(){
            File file = new File("accounts/" + userID + ".ser");
            writeAccount(file);
        }*/
        File file = new File("groups/" + groupID + ".ser");
        writeGroup(file);
    }
    private void writeGroup(File file){
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
    public static void saveGroups(){
        for(Group g : groups){
            g.saveGroup();
        }
    }

    private static Group[] loadGroups(){
        File[] files;
        try {
            Path folderPath = Paths.get("groups");
            Stream<Path> stream = Files.list(folderPath).filter(Files::isRegularFile);
            PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**.ser");
            //stream.filter((f) -> f.toString().toLowerCase().endsWith(".ser"));
            //technically either of these work; im assuming the thingy for the purpose is better.
            Stream<File> matches = stream.filter(matcher::matches).map(Path::toFile);
            files = matches.toArray(File[]::new);
            stream.close();
        }
        catch(Exception e){ throw new RuntimeException(e);}
        Group[] arr = new Group[files.length];
        for(int i = 0; i < files.length; i++){
            File f = files[i];
            arr[i] = loadGroup(f);
        }
        return arr;
    }

    public static Group joinGroup(int _groupID){
        //get group with the specified id
        Group join = Arrays.stream(groups).filter(x->x.groupID == _groupID).findFirst().orElse(null);
        //if it exists
        if(join != null){
            //join the group
            User.curUser.addGroup(join);
            join.addMember(new Member(User.curUser.getUserID(), User.curUser.getAccount().getUsername()));
            saveGroups();
            return join;
        }
        else{
            //else return null
            return null;
        }
    }
    public static void addGroup(Group newGroup){
        ArrayList<Group> newList = new ArrayList<>(Arrays.asList(groups));
        newList.add(newGroup);
        groups = newList.toArray(new Group[0]);
        saveGroups();
    }
    public static Group getGroupByName(String name){
        return Arrays.stream(groups).filter(x->x.Name.equals(name)).findFirst().orElse(null);
    }
    public static ArrayList<Group> getGroupsbyIDs(ArrayList<Integer> ids){
        ArrayList<Group> out = new ArrayList<>(){};
        for(Integer i : ids){
            Group g = Arrays.stream(groups).filter(x->x.groupID == i).findFirst().orElse(null);
            if(g != null){
                out.add(g);
            }
        }
        return out;
    }
}

class Member implements Serializable{
    public int userID;
    public String userName;
    //public int status;

    public Member(int userID, String userName) {
        this.userID = userID;
        this.userName = userName;
    }
}
