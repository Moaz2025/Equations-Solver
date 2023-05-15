package com.example.numerical;

public class GaussJordan {
    static String s = "";
    double [][] Arr;
    double [] B;

    public GaussJordan(double[][] Arr, double[] B){
        this.Arr = Arr;
        this.B = B;
    }
    public double[] GaussJordan()
    {

        for(int i=0;i<B.length;i++)
        {
            //finding the pivot
            double pivot=Math.abs(Arr[i][i]);
            int pivotIndx=i;
            for(int j=i+1;j<B.length;j++)
            {
                if(Math.abs(Arr[j][i])>pivot)
                {
                    pivot=Math.abs(Arr[j][i]);
                    pivotIndx=j;
                }
            }

            // change rows in coef array

            double[] temp=Arr[i];
            Arr[i]=Arr[pivotIndx];
            Arr[pivotIndx]=temp;

            //change rows in B Matrix
            double temp2=B[i];
            B[i]=B[pivotIndx];
            B[pivotIndx]=temp2;

            //forward Elimination
            for(int k=i+1;k<B.length;k++)
            {
                if(Arr[i][i]==0) {}
                double factor=Arr[k][i]/Arr[i][i];
                B[k]=B[k]-factor*B[i];

                for(int c=i;c<B.length;c++)
                {
                    Arr[k][c]=Arr[k][c]-factor*Arr[i][c];
                }
            }
            //Backward Elimination
            for(int k=i-1;k>=0;k--)
            {
                if(Arr[i][i]==0) { }
                double factor=Arr[k][i]/Arr[i][i];
                B[k]=B[k]-factor*B[i];

                for(int c=0;c<B.length;c++)
                {
                    Arr[k][c]=Arr[k][c]-factor*Arr[i][c];
                }
            }
        }
        //result
        for(int i=0;i<B.length;i++)
        {
            B[i]=B[i]/Arr[i][i];
            System.out.println(B[i]);
        }
        return B;
    }

}
