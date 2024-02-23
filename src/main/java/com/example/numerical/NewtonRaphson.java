package com.example.numerical;

import net.objecthunter.exp4j.Expression;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NewtonRaphson {
    Expression ex;
    double x;
    int MAX_ITER = 50;
    double epsilon = 1e-15;
    int pre;

    public NewtonRaphson(Expression ex, double x,int MAX_ITER,double epsilon,int pre){
        this.ex = ex;
        this.x = x;
        this.MAX_ITER = MAX_ITER;
        this.epsilon = epsilon;
        this.pre = pre;
    }
    static double f(Expression ex,double x){
        ex.setVariable("x",x);
        double res = ex.evaluate();
        return res;
    }

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
    public String NewtonRaphson()
    {
        int i = 1;
        double h = f(ex,x) / derivative(ex,x);
        System.out.println("Newton-Raphson");
        while (Math.abs(h) >= epsilon && i <= MAX_ITER)
        {
            h = f(ex,x) / derivative(ex,x);

            // x(i+1) = x(i) - f(x) / f'(x)
            x = x - h;
            System.out.println(i + ": " + x);
            i++;
        }
        BigDecimal formatter = new BigDecimal(x).setScale(pre, RoundingMode.HALF_UP);
        x = formatter.doubleValue();
        return String.valueOf(x);
    }

}
