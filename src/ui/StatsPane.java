package ui;


import database.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import models.Refreshable;
import sample.Main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StatsPane extends BorderPane implements Refreshable {

    private static StatsPane instance;

    LineChart<String, Number> lineChart;
    PieChart pieChart;


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
        this.setTop(new Region());

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Completed Tasks");
        lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("The past week");
        lineChart.getData().add(new XYChart.Series<>());
        lineChart.getData().get(0).setName("Days");
        this.setRight(lineChart);

        pieChart = new PieChart();
        pieChart.setTitle("The next month");
        pieChart.setData(FXCollections.observableArrayList());
        this.setLeft(pieChart);

        pieChart.setLegendVisible(false);
        lineChart.setLegendVisible(false);



    }

    @Override
    public void refresh() {
        refreshLineChart();
        refreshPieChart();
    }

    private void refreshPieChart() {
        ObservableList<PieChart.Data> data = pieChart.getData();
        data.clear();
        String start_date   = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String end_date     = LocalDate.now().plusMonths(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String query = "SELECT * FROM `tasks` WHERE date BETWEEN '" + start_date + "' AND '" + end_date + "';";
        int complete_count      = 0;
        int incomplete_count    = 0;
        ResultSet result = Database.getInstance().sqlQuery(query, false);
        try {
            while(result.next()) {
                if (result.getBoolean("completed")) {
                    complete_count++;
                } else {
                    incomplete_count++;
                }
            }
        } catch (SQLException e) { }
        data.add(new PieChart.Data("complete", complete_count));
        data.add(new PieChart.Data("incomplete", incomplete_count));
        //pieChart.setData(data);
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
