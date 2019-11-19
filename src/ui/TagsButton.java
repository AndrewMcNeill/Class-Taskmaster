package ui;

import database.Database;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import sample.Main;

import javax.xml.crypto.Data;
import java.sql.SQLException;

public class TagsButton {

    MenuButton tagsButton;
    private MenuItem item;
    private TextField newTag;

    TagsButton() {
        // create all previous stored tags on creation of new tag button
        Database.getInstance().grabTags(Main.tagList);

        tagsButton = new MenuButton("Tags");
        tagsButton.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            updateTagList();
        });

        newTag = new TextField();
        newTag.setPromptText("+ New Tag");
        newTag.setOnAction(e->{
            if (!Main.tagList.containsKey(newTag.getText())){                       // if the tag doesnt exist in the map
                if (!newTag.getText().equals("")) {                                 // if the tag is not empty
                    item = new MenuItem(newTag.getText());                          // create a new menu item with the text from the newTag Field
                    item.setOnAction(f->{tagsButton.setText(item.getText());});     // Create a listener, set the button text to the text from the tag item
                    Main.tagList.put(newTag.getText(), item);                       // put this tag item into the map
                    tagsButton.getItems().add(item);                                // add the item to the menu list
                    tagsButton.setText(item.getText());                             // set the text to the new tag by default
                    Database.getInstance().insertTag(item.getText());
                    newTag.clear();                                                 // clear the field
                }
            }
        });

        CustomMenuItem addTag = new CustomMenuItem(newTag, false);
        tagsButton.getItems().add(addTag);
    }

    void clearTagButton() {
        tagsButton.setText("Tags");
    }

    // recreate the list on click
    private void updateTagList() {
        tagsButton.getItems().clear();
        tagsButton.getItems().add(new CustomMenuItem(newTag, false));
        for (MenuItem item : Main.tagList.values()){
            item.setOnAction(f->{tagsButton.setText(item.getText());});
            tagsButton.getItems().add(item);
        }
    }
}
