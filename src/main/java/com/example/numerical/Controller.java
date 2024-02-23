package com.example.numerical;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.stage.Stage;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class Controller implements Initializable {

    @FXML
    private ChoiceBox<String> ChooseFunction;

    @FXML
    private ChoiceBox<String> ChooseFunction2;

    @FXML
    private TextArea EquationsInput;

    @FXML
    private TextField FreeInput;

    @FXML
    private TextArea Input_of_b;

    @FXML
    private TextField Lower;

    @FXML
    private TextField NoOfEquations;

    @FXML
    private TextField Output;

    @FXML
    private TextField Output2;

    @FXML
    private CheckBox Scaling;

    @FXML
    private Button Solve;

    @FXML
    private Button Solve2;

    @FXML
    private TextField Upper;

    @FXML
    private TextField epsilon;

    @FXML
    private TextField epsilon2;

    @FXML
    private TextArea initialValues;

    @FXML
    private TextField noOfIterations;

    @FXML
    private TextField noOfIterations2;

    @FXML
    private TextField precision;

    @FXML
    private TextField precision2;

    @FXML
    private TextField runTime;

    @FXML
    private TextField runTime2;

    @FXML
    void SolveEquations(MouseEvent event) {
        Output.clear();
        long startTime = System.currentTimeMillis();
        int n =Integer.valueOf(NoOfEquations.getText());
        String[] rows = EquationsInput.getText().split("\n");
        String [][] matrixS = new String[n][n];
        for (int i = 0; i < n; i++) {
            String[] columns = rows[i].split(" ");
            for (int j = 0; j < n; j++) {
                matrixS[i][j] = columns[j];
            }
        }
        double [][] matrixD = new double[n][n];
        for(int i = 0;i < n;i++){
            for(int j = 0;j < n;j++){
                matrixD[i][j] = Double.valueOf(matrixS[i][j]);
            }
        }
        String[] bS = Input_of_b.getText().split("\n");
        double [] bD = new double[n];
        for(int i = 0;i < n;i++){
            bD[i] = Double.valueOf(bS[i]);
        }
        String func = ChooseFunction.getSelectionModel().getSelectedItem();
        double [] sol = new double[n];

        if(func == "LU_Dolittle"){
            Doolittle lu = new Doolittle(matrixD,bD,n);
            sol = lu.Doolittle_Decomposition();
        }

        else if(func == "LU_Crout"){
            Crout lu = new Crout(matrixD, bD, n);
            sol = lu.Crout_Decomposition();
        }

        else if(func == "LU_Cholesky"){
            boolean sym = true;
            double [][] tran = new double[n][n];
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    tran[i][j] = matrixD[j][i];
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    if (matrixD[i][j] != tran[i][j])
                        sym = false;
            if(sym) {
                boolean positive = true;
                Cholesky lu = new Cholesky(matrixD, bD, n);
                sol = lu.Cholesky_Decomposition();
                for (int i = 0; i < n; i++)
                    for (int j = 0; j < n; j++)
                        if(Double.toString(sol[j]) == "NaN")
                            positive = false;
                if(positive){
                    int pre;
                    if(precision.getText().isEmpty())
                        pre = 5;
                    else
                        pre = Integer.valueOf(precision.getText());
                    for(int i = 0;i < n;i++){
                        BigDecimal formatter = new BigDecimal(sol[i]).setScale(pre, RoundingMode.HALF_UP);
                        sol[i] = formatter.doubleValue();
                        Output.setText(Arrays.toString(sol));
                    }
                }
                else
                    Output.setText("Must have positive eigenvalues");
            }
            else
                Output.setText("Must be symmetric");
        }

        else if(func == "Jacobi"){
            String[] init = initialValues.getText().split("\n");
            double [] initD = new double[n];
            for(int i = 0;i < n;i++){
                initD[i] = Double.valueOf(init[i]);
            }
            double eps = Double.valueOf(epsilon.getText());
            int it = Integer.valueOf(noOfIterations.getText());
            Jacobi jac = new Jacobi(matrixD,bD,initD,it,eps);
            sol = jac.solve();
        }

        else if(func == "GaussSeidel"){
            String[] init = initialValues.getText().split("\n");
            double [] initD = new double[n];
            for(int i = 0;i < n;i++){
                initD[i] = Double.valueOf(init[i]);
            }
            JaussSeidel sed = new JaussSeidel();
            sed.setMatrix(matrixD);
            sed.setConstants(bD);
            double eps = Double.valueOf(epsilon.getText());
            sed.setEpsilon(eps);
            int it = Integer.valueOf(noOfIterations.getText());
            sed.setIterations(it);
            int pre;
            if(precision.getText().isEmpty())
                pre = 5;
            else
                pre = Integer.valueOf(precision.getText());
            sed.setPrecision(pre);
            sed.setInitial(initD);
            sol = sed.solve();
        }

        else if(func == "GaussJordan"){
            GaussJordan arr = new GaussJordan(matrixD, bD);
            sol = arr.GaussJordan();
        }

        else if(func == "GaussElimination"){
            boolean scale = Scaling.isSelected();
            Gauss arr = new Gauss(matrixD, bD,scale);
            sol = arr.GuessElimination();
        }

        int pre;
        if(precision.getText().isEmpty())
            pre = 5;
        else
            pre = Integer.valueOf(precision.getText());
        for(int i = 0;i < n;i++){
            BigDecimal formatter = new BigDecimal(sol[i]).setScale(pre, RoundingMode.HALF_UP);
            sol[i] = formatter.doubleValue();
        }
        if(func != "LU_Cholesky")
            Output.setText(Arrays.toString(sol));
        long endTime = System.currentTimeMillis();
        runTime.setText(String.valueOf(endTime - startTime) + " ms");
    }

    @FXML
    void SolveFunction(MouseEvent event) {
        Output2.clear();
        long startTime = System.currentTimeMillis();
        String func2 = ChooseFunction2.getSelectionModel().getSelectedItem();

        if(func2 == "Bisection"){
            String exp = FreeInput.getText();
            double l = Double.valueOf(Lower.getText());
            double u = Double.valueOf(Upper.getText());
            double eps = Double.valueOf(epsilon2.getText());
            int pre;
            if(precision2.getText().isEmpty())
                pre = 5;
            else
                pre = Integer.valueOf(precision2.getText());
            Bisection Bi = new Bisection(l,u,exp,eps,pre);
            String sol = Bi.solve();
            Output2.setText(sol);
            long endTime = System.currentTimeMillis();
            runTime2.setText(String.valueOf(endTime - startTime) + " ms");
            Drawing drawing = new Drawing(l,u,exp);
            Stage temp = new Stage();
            drawing.start(temp);
        }

        else if(func2 == "False-Position"){
            String exp = FreeInput.getText();
            ExpressionBuilder eb = new ExpressionBuilder(FreeInput.getText());
            eb.variables("x");
            Expression ex = eb.build();
            double l = Double.valueOf(Lower.getText());
            double u = Double.valueOf(Upper.getText());
            int it = Integer.valueOf(noOfIterations2.getText());
            double eps = Double.valueOf(epsilon2.getText());
            int pre;
            if(precision2.getText().isEmpty())
                pre = 5;
            else
                pre = Integer.valueOf(precision2.getText());
            FalsePosition f = new FalsePosition(ex,l,u,it,eps,pre);
            String sol = f.regulaFalsi();
            Output2.setText(sol);
            long endTime = System.currentTimeMillis();
            runTime2.setText(String.valueOf(endTime - startTime) + " ms");
            Drawing drawing = new Drawing(l,u,exp);
            Stage temp = new Stage();
            drawing.start(temp);
        }

        else if(func2 == "Fixed point"){
            String exp = FreeInput.getText();
            double x = Double.valueOf(Lower.getText());
            int it = Integer.valueOf(noOfIterations2.getText());
            double eps = Double.valueOf(epsilon2.getText());
            int pre;
            if(precision2.getText().isEmpty())
                pre = 5;
            else
                pre = Integer.valueOf(precision2.getText());
            FixedPoint fx = new FixedPoint(exp,x,it,eps,pre);
            String sol = fx.FixedPoint();
            Output2.setText(sol);
            long endTime = System.currentTimeMillis();
            runTime2.setText(String.valueOf(endTime - startTime) + " ms");
            Drawing drawing = new Drawing(x-20,x+20,exp);
            Stage temp = new Stage();
            drawing.start(temp);
        }

        else if(func2 == "Newton-Raphson"){
            String exp = FreeInput.getText();
            ExpressionBuilder eb = new ExpressionBuilder(FreeInput.getText());
            eb.variables("x");
            Expression ex = eb.build();
            double x = Double.valueOf(Lower.getText());
            int it = Integer.valueOf(noOfIterations2.getText());
            double eps = Double.valueOf(epsilon2.getText());
            int pre;
            if(precision2.getText().isEmpty())
                pre = 5;
            else
                pre = Integer.valueOf(precision2.getText());
            NewtonRaphson n = new NewtonRaphson(ex,x,it,eps,pre);
            String sol = n.NewtonRaphson();
            Output2.setText(sol);
            long endTime = System.currentTimeMillis();
            runTime2.setText(String.valueOf(endTime - startTime) + " ms");
            DrawDerivative drawing = new DrawDerivative(x-10,x+10,exp);
            Stage temp = new Stage();
            drawing.start(temp);
        }

        else if(func2 == "Secant"){
            String exp = FreeInput.getText();
            double l = Double.valueOf(Lower.getText());
            double u = Double.valueOf(Upper.getText());
            int it = Integer.valueOf(noOfIterations2.getText());
            double eps = Double.valueOf(epsilon2.getText());
            int pre;
            if(precision2.getText().isEmpty())
                pre = 5;
            else
                pre = Integer.valueOf(precision2.getText());
            Secant s = new Secant(exp,l,u,eps,it,pre);
            String sol = s.solve();
            Output2.setText(sol);
            long endTime = System.currentTimeMillis();
            runTime2.setText(String.valueOf(endTime - startTime) + " ms");
            DrawDerivative drawing = new DrawDerivative(l,u,exp);
            Stage temp = new Stage();
            drawing.start(temp);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ChooseFunction.getItems().addAll("GaussElimination","GaussJordan",
                "LU_Dolittle","LU_Crout","LU_Cholesky","GaussSeidel","Jacobi");

        ChooseFunction2.getItems().addAll("Bisection","False-Position",
                "Fixed point","Newton-Raphson","Secant");
    }

}

