package ui;


import database.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import models.Refreshable;
import sample.Main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StatsPane extends BorderPane implements Refreshable {

    private static StatsPane instance;

    LineChart<String, Number> lineChart;


    public static StatsPane getInstance() {
        if (StatsPane.instance == null)
            StatsPane.instance = new StatsPane();
        return StatsPane.instance;
    }

    private StatsPane() {
        Button backButton = new Button("Back to tasks");
        backButton.setOnMouseClicked(e->{
            Main.changeScene(MainPane.getInstance());
        });
        this.setBottom(backButton);

        final CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Day");
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Completed Tasks");
        lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.getData().add(new XYChart.Series<>());
        this.setRight(lineChart);




    }

    @Override
    public void refresh() {
        refreshLineChart();
        refreshPieChart();
    }

    private void refreshPieChart() {

    }

    private void refreshLineChart() {
        XYChart.Series series = lineChart.getData().get(0);
        series.getData().clear();
        for (int i = 6; i >= 0; i--) {
            String date = LocalDate.now().minusDays(i).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String query = "SELECT * FROM `tasks` WHERE date = '" + date + "' AND completed = true;";
            ResultSet result = Database.getInstance().sqlQuery(query, false);
            int count = 0;
            try {
                for (;result.next(); count++);
            } catch (SQLException e) { }
            String name = i == 0 ? "today" : i == 1 ? "yesterday" : i + " days ago";
            series.getData().add(new XYChart.Data(name, count));
        }
    }
}
