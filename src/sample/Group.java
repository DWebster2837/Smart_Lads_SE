package sample;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

public class Group {
    private String Name;
    private LinkedList<Member> Members;
    private int groupID;
    private static Group[] groups = loadGroups();

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
}

class Member{
    public int userID;
    public String userName;
    public int status;
}
