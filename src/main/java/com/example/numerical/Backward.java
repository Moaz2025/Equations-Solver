package com.example.numerical;

public class Backward {
    double [][] Umat;
    double [] b;
    int n;
    public Backward(double[][] Umat, double[] b, int n){
        this.Umat = Umat;
        this.b = b;
        this.n = n;
    }
    public double[] Backward(){

        double[] sol2 = new double[n];

        for (int r = n - 1; r >= 0; r--) {
            double val = 0;
            for (int c = n - 1; c > r; c--) {
                val = val + sol2[c] * Umat[r][c];
            }
            val = b[r] - val;
            sol2[r] = val / Umat[r][r];
        }
        return sol2;
    }
}
