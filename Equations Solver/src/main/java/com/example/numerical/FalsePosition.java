package com.example.numerical;

import net.objecthunter.exp4j.Expression;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FalsePosition {
    Expression ex;
    double l,u;
    int MAX_ITER = 50;
    double epsilon = 1e-15;
    int pre;

    public FalsePosition(Expression ex, double l, double u,int MAX_ITER,double epsilon,int pre){
        this.ex = ex;
        this.l = l;
        this.u = u;
        this.MAX_ITER = MAX_ITER;
        this.epsilon = epsilon;
        this.pre = pre;
    }
    static double f(Expression ex,double x){
        ex.setVariable("x",x);
        double res = ex.evaluate();
        return res;
    }
    public String regulaFalsi()
    {
        if (f(ex,l) * f(ex,u) >= 0)
        {
            return ("No solution in this interval");
        }
        // Initialize result
        double r = 0.0,fr;
        int n, side = 0;

        /** starting values at endpoints of interval **/
        double fs = f(ex,l);
        double ft = f(ex,u);
        System.out.println("False-Position");
        for (n = 0; n < MAX_ITER; n++)
        {
            r = (fs * u - ft * l) / (fs - ft);
            System.out.println((n + 1) + ": " + r);
            if (Math.abs(u - l) < epsilon * Math.abs(u + l))
                break;
            fr = f(ex,r);

            if (fr * ft > 0)
            {
                /** fr and ft have same sign, copy r to t **/
                u = r;
                ft = fr;
                if (side == -1)
                    fs /= 2;
                side = -1;
            }
            else if (fs * fr > 0)
            {
                /** fr and fs have same sign, copy r to s **/
                l = r;
                fs = fr;
                if (side == +1)
                    ft /= 2;
                side = +1;
            }
            else
            {
                /** fr * f_ very small (looks like zero) **/
                break;
            }
        }
        BigDecimal formatter = new BigDecimal(r).setScale(pre, RoundingMode.HALF_UP);
        r = formatter.doubleValue();
        return String.valueOf(r);
    }
}
