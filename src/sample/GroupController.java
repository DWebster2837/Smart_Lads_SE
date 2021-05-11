package sample;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class GroupController implements Initializable {

    public Button sendInviteButton;
    public TextField createGroupName;
    public Label joinError;
    public TextField joinCode;
    @FXML TextField groupID;
    @FXML ComboBox groupName;

    private static HashMap<String, Group> groupMap = new HashMap<String, Group>() {};
    public void goBack(ActionEvent actionEvent) {
        try {
            Main.changeStage(Main.class.getResource("fxml/Dashboard.fxml"), 670d, 452d);
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public void joinById(ActionEvent actionEvent) {
        int id = 0;
        try {
            id = Integer.parseInt(groupID.getText());
        }
        catch(NumberFormatException e){
            joinError.setText("Please enter a valid number");
            return;
        }

        if(Group.joinGroup(id) == null){
            joinError.setText("Group does not exist");
        }
        else{
            joinError.setText("");
            updateComboBox();
        }
    }

    public void sendGroupInvite(ActionEvent actionEvent) {
        String name = groupName.getValue().toString();
        Group g = Group.getGroupByName(name);
        if(g != null) {
            joinCode.setText(Integer.valueOf(g.getGroupId()).toString());
        }
        else{
            joinCode.setText("Group invalid");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateComboBox();
    }
    private void updateComboBox(){
        ArrayList<Integer> groupIDs = User.curUser.getGroups();
        ArrayList<Group> groups = Group.getGroupsbyIDs(groupIDs);
        ArrayList<String> groupNames = new ArrayList<>(){};
        for(Group g : groups){
            groupMap.put(g.getName(), g);
            groupNames.add(g.getName());
        }
        groupName.setItems(FXCollections.observableArrayList(groupNames));
    }

    public void createGroupClicked(ActionEvent actionEvent) {
        //make group
        Group newGroup = new Group(createGroupName.getText());
        //add it to groups
        Group.addGroup(newGroup);
        //join it
        Group.joinGroup(newGroup.getGroupId());
        updateComboBox();
    }
}
