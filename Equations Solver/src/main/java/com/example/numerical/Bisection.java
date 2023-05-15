package com.example.numerical;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Bisection {
    private double l,u,r,epsilon;
    private String input;
    private Expression function;
    private int iterations;
    private int pre;

    public Bisection(double l, double u, String input,double epsilon,int pre) {
        this.l = l;
        this.u = u;
        this.input = input;
        this.epsilon = epsilon;
        this.pre = pre;
        ExpressionBuilder builder = new ExpressionBuilder(input);
        builder.variables("x");
        function = builder.build();
        calculateIterations();
    }
    private void calculateIterations(){
        double length = Math.abs(l-u);
        double factor = Math.abs(length/epsilon);
        iterations = (int)(Math.log(factor)/Math.log(2.0) + 1);
    }
    public void setIterations(int iterations) {
        this.iterations = iterations;
    }
    public String solve(){
        System.out.println("Method: Bisection & Equation: " + input);
        function.setVariable("x",l);
        double lower = function.evaluate();
        function.setVariable("x",u);
        double upper = function.evaluate();
        if((lower*upper) >= 0){
            System.out.println("Error:root out of interval");
            return "Error:root out of interval";
        }
        double middle;
        System.out.println("Iteration\tXl\tXu\tXr\tf(Xr)");
        for(int i=0;i<iterations;i++){
            r = (l+u)/2;
            function.setVariable("x",l);
            lower = function.evaluate();
            function.setVariable("x",u);
            upper = function.evaluate();
            function.setVariable("x",r);
            middle = function.evaluate();
            if(middle == 0){
                return Double.toString(r);
            }
            if((middle*upper)>0){
                u = r;
            }else if((middle*lower)>0){
                l = r;
            }
            System.out.println(i +"\t"+ Double.toString(l)+"\t" + Double.toString(u)+"\t" +Double.toString(r)+"\t" + Double.toString(middle));
        }
        BigDecimal formatter = new BigDecimal(r).setScale(pre, RoundingMode.HALF_UP);
        r = formatter.doubleValue();
        return Double.toString(r);
    }
}

