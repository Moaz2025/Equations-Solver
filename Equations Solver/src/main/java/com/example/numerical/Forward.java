package com.example.numerical;

public class Forward {
    double [][] Lmat;
    double [] b;
    int n;

    public Forward(double[][] Lmat, double[] b, int n){
        this.Lmat = Lmat;
        this.b = b;
        this.n = n;
    }
    public double[] Forward(){

        double[] sol1 = new double[n];

        for (int r = 0; r < n; r++) {
            double val = 0;
            for (int c = 0; c < r; c++) {
                val = val + sol1[c] * Lmat[r][c];
            }
            val = b[r] - val;
            sol1[r] = val / Lmat[r][r];
        }
        return sol1;
    }
}
