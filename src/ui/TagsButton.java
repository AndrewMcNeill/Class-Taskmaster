package ui;

import database.Database;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import sample.Main;

public class TagsButton {

    MenuButton tagsButton;
    private CustomMenuItem item;
    private CustomMenuItem addTag;
    private TextField newTag;
    private Button deleteTag = new Button("X");
    public boolean ready = false;

    TagsButton() {

        // create all previous stored tags on creation of new tag button



        tagsButton = new MenuButton("Tags");
        tagsButton.setId("TagsButton");
        tagsButton.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            updateTagList();
        });

        newTag = new TextField();
        newTag.setId("TagsInputField");
        newTag.setPromptText("+ New Tag");
        newTag.setOnAction(e->{
            if (!Main.tagList.contains(newTag.getText())){
                if (!newTag.getText().equals("")) {
                    tagsButton.getItems().add(makeNewTagButton(newTag.getText()));
                    Main.tagList.add(newTag.getText());
                    Database.getInstance().insertTag(newTag.getText());
                    tagsButton.setText(newTag.getText()); //set the buttons text *after* the tag has been created in the DB
                    newTag.clear();
                    FilterPane.getInstance().tagFilters.updateTagList();
                }
            }
        });

        addTag = new CustomMenuItem(newTag, false);
        addTag.setId("TagInputWrapper");
        tagsButton.getItems().add(addTag);
    }

    void clearTagButton() {
        tagsButton.setText("Tags");
    }

    // recreate the list on click
    public void updateTagList() {
        tagsButton.getItems().clear();
        //add the text box for making new tags
        tagsButton.getItems().add(addTag);
        for (String tag : Main.tagList){
            //System.out.println(tag);
            tagsButton.getItems().add(makeNewTagButton(tag));
        }
    }

    public CustomMenuItem makeNewTagButton(String tagName) {
        Label label = new Label(tagName);
        label.setOnMouseClicked(f->{tagsButton.setText(label.getText());});     // Create a listener, set the button text to the text from the tag item
        HBox hbox = new HBox();
        if (tagName.equals("No Tag!")) {
            hbox.getChildren().addAll(label);
        } else {
            hbox.getChildren().addAll(new DeleteButton(label), label);
        }
        hbox.setSpacing(20);
        CustomMenuItem item = new CustomMenuItem(hbox);
        item.setId("Tag");
        return item;
    }
    class DeleteButton extends Label {

        Label selfLabel;
        DeleteButton(Label selfLabel){
            this.selfLabel = selfLabel;
            this.setId("DeleteTagsButton");
            this.setOnMouseClicked(e->{
                Main.tagList.remove(selfLabel.getText());
                tagsButton.setText("No Tag!");
                updateTagList();
                FilterPane.getInstance().tagFilters.updateTagList();
                Database.getInstance().removeTag(selfLabel.getText());
            });
        }
    }

}


