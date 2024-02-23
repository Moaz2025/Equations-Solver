package com.example.numerical;

public class Cholesky {
    static String s = "";
    double [][] matrix;
    double [] b;
    int n;
    public Cholesky(double[][] matrix,double[] b, int n){
        this.matrix = matrix;
        this.b = b;
        this.n = n;
    }
    public double [] Cholesky_Decomposition()
    {
        double[][] lower = new double[n][n];

        // Decomposing a matrix
        // into Lower Triangular
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                double sum = 0;

                // summation for diagonals
                if (j == i) {
                    for (int k = 0; k < j; k++)
                        sum += Math.pow(lower[j][k],
                                2);
                    lower[j][j] = Math.sqrt(
                            matrix[j][j] - sum);
                }

                else {

                    // Evaluating L(i, j)
                    // using L(j, j)
                    for (int k = 0; k < j; k++)
                        sum += (lower[i][k] * lower[j][k]);
                    lower[i][j] = (matrix[i][j] - sum)
                            / lower[j][j];
                }
            }
        }
        double [][] lowertranspose = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                lowertranspose[j][i] = lower[i][j];
            }
        }

        // Displaying Lower
        // Triangular and its Transpose
        System.out.println(setw(10) + "Lower Triangular" + setw(30) + "Transpose");
        for (int i = 0; i < n; i++) {

            // Lower Triangular
            for (int j = 0; j < n; j++)
                System.out.print(setw(3) + String.format("%.5f", lower[i][j]) + "\t");
            System.out.print("\t" + "\t");

            // Transpose of
            // Lower Triangular

            for (int j = 0; j < n; j++)
                System.out.print(setw(3) + String.format("%.5f", lowertranspose[i][j]) + "\t");
            System.out.println();
        }
        Forward l = new Forward(lower,b,n);
        double[] sol1 = l.Forward();
        Backward u = new Backward(lowertranspose,sol1,n);
        double[] sol2 = u.Backward();

        return sol2;
    }
    static String setw(int noOfSpace)
    {
        s = "";
        for (int i = 0; i < noOfSpace; i++)
            s += " ";
        return s;
    }
}
