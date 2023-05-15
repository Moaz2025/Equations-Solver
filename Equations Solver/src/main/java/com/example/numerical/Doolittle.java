package com.example.numerical;

public class Doolittle {
    static String s = "";
    double [][] mat;
    double [] b;
    int n;

    public Doolittle(double[][] mat, double[] b, int n){
        this.mat = mat;
        this.b = b;
        this.n = n;
    }
    public double [] Doolittle_Decomposition()
    {
        double[][] lower = new double[n][n];
        double[][] upper = new double[n][n];

        // Decomposing matrix into Upper and Lower
        // triangular matrix
        for (int i = 0; i < n; i++)
        {
            // Upper Triangular
            for (int k = i; k < n; k++)
            {
                // Summation of L(i, j) * U(j, k)
                double sum = 0;
                for (int j = 0; j < i; j++)
                    sum += (lower[i][j] * upper[j][k]);

                // Evaluating U(i, k)
                upper[i][k] = mat[i][k] - sum;
            }

            // Lower Triangular
            for (int k = i; k < n; k++)
            {
                if (i == k)
                    lower[i][i] = 1; // Diagonal as 1
                else
                {
                    // Summation of L(k, j) * U(j, i)
                    double sum = 0;
                    for (int j = 0; j < i; j++)
                        sum += (lower[k][j] * upper[j][i]);

                    // Evaluating L(k, i)
                    lower[k][i]
                            = (mat[k][i] - sum) / upper[i][i];
                }
            }
        }

        // setw is for displaying nicely
        System.out.println(setw(10) + "Lower Triangular"
                + setw(30) + "Upper Triangular");

        // Displaying the result :
        for (int i = 0; i < n; i++)
        {
            // Lower
            for (int j = 0; j < n; j++)
                System.out.print(setw(3) + String.format("%.5f", lower[i][j])
                        + "\t");
            System.out.print("\t" + "\t");

            // Upper
            for (int j = 0; j < n; j++)
                System.out.print(setw(3) + String.format("%.5f", upper[i][j])
                        + "\t");
            System.out.print("\n");
        }
        Forward l = new Forward(lower,b,n);
        double[] sol1 = l.Forward();
        Backward u = new Backward(upper,sol1,n);
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

