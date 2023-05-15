package com.example.numerical;

public class Crout {
    double [][] A;
    double [] b;
    int n;
    public Crout(double[][] A, double[] b, int n){
        this.A = A;
        this.b = b;
        this.n = n;
    }
    public double [] Crout_Decomposition(){
        double[][] L;
        double[][] U;
        L=matrizDiagonal(0,n);
        U=matrizDiagonal(1,n);
        for(int k=0;k<n;++k){
            double suma=0;
            for(int p=0;p<k;++p){
                suma+=L[k][p]*U[p][k];
            }
            L[k][k]=A[k][k]-suma;
            for(int i=k+1;i<n;++i){
                double suma2=0;
                for(int p=0;p<k;++p){
                    suma2+=L[i][p]*U[p][k];
                }
                L[i][k]=(A[i][k]-suma2)/U[k][k];
            }
            for(int j=k+1;j<n;++j){
                double suma3=0;
                for(int p=0;p<k;++p){
                    suma3+=L[k][p]*U[p][j];
                }
                U[k][j]=(A[k][j]-suma3)/L[k][k];
            }
        }
        printMatrix(L,n);
        System.out.println();
        printMatrix(U,n);
        Forward l = new Forward(L,b,n);
        double[] sol1 = l.Forward();
        Backward u = new Backward(U,sol1,n);
        double[] sol2 = u.Backward();

        return sol2;
    }

    static double[][] matrizDiagonal(int dig,int n){
        double[][] A = new double[n][n];
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                if(i==j){
                    A[i][j]=1;
                }else{
                    A[i][j]=0;
                }
            }
        }
        return A;
    }
    public static void printMatrix(double [][] A,int n){
        for(int i = 0; i < n; ++i){
            System.out.println();
            for(int j = 0; j < n; ++j){
                System.out.print("   "+String.format("%.5f",A[i][j]));
            }
        }
        System.out.println();
    }
}

