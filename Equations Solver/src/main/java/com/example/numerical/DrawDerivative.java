package com.example.numerical;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class DrawDerivative extends Application {
    private double l,u,y0,y1;
    private Expression function;
    private String exp;
    DrawDerivative(){}
    public static double hValue(double x) {
        return Math.max(Math.abs(x / 1000.0), 0.0001);
    }
    public static double derivative(Expression expression, double x) {
        double h = hValue(x);
        expression.setVariable("x",x);
        double lower = expression.evaluate();
        expression.setVariable("x",x+h);
        double higher = expression.evaluate();
        expression.setVariable("x",x);
        return (higher-lower)/(h);
    }
    DrawDerivative(double l,double u,String exp){
        this.l = l;
        this.u = u;
        ExpressionBuilder builder = new ExpressionBuilder(exp);
        builder.variables("x");
        function = builder.build();
        function.setVariable("x",l);
        y0 = function.evaluate() - 20;
        function.setVariable("x",u);
        y1 = function.evaluate() + 20;
        function.setVariable("x",l);
    }
    @Override
    public void start(Stage stage) {
        //Defining the x axis
        NumberAxis xAxis = new NumberAxis(l-2, u+2, 1);
        xAxis.setLabel("X");
        //Defining the y axis
        NumberAxis yAxis = new NumberAxis(-100, 100, 1);
        yAxis.setLabel(exp);
        //Creating the line chart
        LineChart linechart = new LineChart(xAxis, yAxis);

        //Prepare XYChart.Series objects by setting data
        XYChart.Series series = new XYChart.Series();
        series.setName("Function drawing");
        double start = Math.min(l,u);
        double end = Math.max(l,u);
        while (start<end){
            function.setVariable("x",start);
            double value = derivative(function,start);
            series.getData().add(new XYChart.Data(start,value));
            start += 0.01;
        }
//        series.getData().add(new XYChart.Data(1970, 15));
//        series.getData().add(new XYChart.Data(1980, 30));
//        series.getData().add(new XYChart.Data(1990, 60));
//        series.getData().add(new XYChart.Data(2000, 120));
//        series.getData().add(new XYChart.Data(2013, 240));
//        series.getData().add(new XYChart.Data(2014, 300));

        //Setting the data to Line chart
        linechart.getData().add(series);


        //Creating a Group object
        Group root = new Group(linechart);

        //Creating a scene object
        Scene scene = new Scene(root, 600, 600);

        //Setting title to the Stage
        stage.setTitle("Line Chart");

        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();
    }
    public static void main(String args[]){
        launch(args);
    }
}
