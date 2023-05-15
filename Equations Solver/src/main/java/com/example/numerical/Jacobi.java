package com.example.numerical;

import java.util.Arrays;

public class Jacobi {
    double[][] M;
    double[]b;
    double[]X;
    double epsilon = 1e-15;
    int MAX_ITERATIONS = 100;
    int precision = 5;

    public Jacobi(double[][] m,double[]b,double[]X,int mAX_ITERATIONS,double epsilon) {
        this.M = m;
        this.b = b;
        this.X = X;
        MAX_ITERATIONS = mAX_ITERATIONS;
        this.epsilon = epsilon;
    }


    public double[]solve()
    {
        int iterations = 0;
        int n = M.length;
        double[] error = new double[n];

        double [] pre_x = X.clone();

        while (true) {
            for (int i = 0; i < n; i++) {
                double sum = b[i];

                for (int j = 0; j < n; j++)
                    if (j != i)
                        sum -= M[i][j] * pre_x[j];


                X[i] = 1/M[i][i] * sum;
            }

            System.out.print("X_" + iterations + " = {");
            for (int i = 0; i < n; i++)
                System.out.printf("%15.17f ", X[i]);
            System.out.println("}");
            double e ;

            System.out.print("e_" + iterations + " = {");
            for (int i = 0; i < n; i++) {
                error[i] = Math.abs((X[i]-pre_x[i])/X[i]);

                System.out.printf("%15.17f ",error[i]);
            }
            System.out.println("}\n");

            iterations++;
            if (iterations == 1) continue;

            boolean stop = true;
            for (int i = 0; i < n && stop; i++)
                if (Math.abs((X[i]-pre_x[i])/X[i]) > epsilon)
                    stop = false;



            if (stop || iterations == MAX_ITERATIONS) break;
            pre_x = (double[])X.clone();
        }


        return X;
    }
}
