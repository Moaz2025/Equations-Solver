package com.example.numerical;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class Secant {

    String s;
    double x1,x2;
    double epsilon = 1e-15;
    int MAX_ITERATIONS = 50;
    int pre;

    public Secant(String s, double x1, double x2, double epsilon, int MAX_ITERATIONS,int pre) {
        this.s = s;
        this.x1 = x1;
        this.x2 = x2;
        this.epsilon = epsilon;
        this.MAX_ITERATIONS = MAX_ITERATIONS;
        this.pre = pre;
    }

    public String solve(){

        int iterations = 0;
        double x3;

        System.out.println("Secant");
        while (true){
            double y1 = Evaluate(s,x1);
            double y2 = Evaluate(s,x2);

            if(y1 - y2 == 0) return "Error";
            x3 = x2 - (y2*(x1-x2))/(y1-y2);
            double error = Math.abs((x3-x2)/x3);

            System.out.println("X_" + iterations + " = " + x3);
            System.out.println("error = " + error+"\n");

            if (iterations >= MAX_ITERATIONS || error <= epsilon)break;

            x1=x2;
            x2=x3;
            iterations++;
        }
        BigDecimal formatter = new BigDecimal(x3).setScale(pre, RoundingMode.HALF_UP);
        x3 = formatter.doubleValue();
        return String.valueOf(x3);

    }

    public static double Evaluate(String s,double val){

        Map<String, Double> vars = new HashMap<String, Double>();
        vars.put("x",val);
        ExpressionBuilder eb = new ExpressionBuilder(s);
        eb.variables("x");
        Expression ex = eb.build();
        ex.setVariables(vars);
        double res = ex.evaluate();
        return res;
    }
}
