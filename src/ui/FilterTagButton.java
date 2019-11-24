package ui;

import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import sample.Main;

import java.util.logging.Filter;

public class FilterTagButton extends VBox {

    public FilterTagButton(){
        updateTagList();
        this.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            updateTagList();
        });
    }
    void updateTagList() {
        this.getChildren().clear();
        for (String tag : Main.tagList){
            //System.out.println(tag);
            FilterButton b = new FilterButton(tag,
                    "taskid in " +
                            "(SELECT taskid FROM `tagstaskrelational` WHERE tagid = " +
                            "(SELECT tagid FROM `tags` WHERE tagname = '"+tag+"'))");
            this.getChildren().addAll(b);
        }
    }

}
