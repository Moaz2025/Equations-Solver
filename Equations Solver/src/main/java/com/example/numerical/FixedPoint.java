package com.example.numerical;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class FixedPoint {
    String exp;
    double intial;
    int numberofiterations = 50;
    double tolerance = 1e-15;
    int pre;

    public FixedPoint(String exp,double intial,int numberofiterations,double tolerance,int pre){
        this.exp = exp;
        this.intial = intial;
        this.numberofiterations = numberofiterations;
        this.tolerance = tolerance;
        this.pre = pre;
    }
    double gx(String exp,double x)
    {
        Map<String, Double> vars = new HashMap<String, Double>();
        vars.put("x", x);
        ExpressionBuilder eb = new ExpressionBuilder(exp);
        eb.variables("x");
        Expression ex = eb.build();
        ex.setVariables(vars);
        double result = ex.evaluate();
        return result;
    }
    public String FixedPoint(){
        int counter =0;
        double p=0;
        double p0= intial;
        System.out.println("Fixed point");
        while(counter<numberofiterations){
            p=gx(exp,p0);
            System.out.println((counter + 1) + ": " + p);
            if(Math.abs((p-p0)/p)<tolerance){
                break;
            }
            p0=p;
            counter++;

        }
        BigDecimal formatter = new BigDecimal(p).setScale(pre, RoundingMode.HALF_UP);
        p = formatter.doubleValue();
        return String.valueOf(p);
    }
}
