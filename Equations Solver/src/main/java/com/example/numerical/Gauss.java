package com.example.numerical;

import java.util.ArrayList;

public class Gauss {
    double [][] coffs;
    double [] b;
    boolean scaling;

    public Gauss(double[][] coffs,double[] b,boolean scaling){
        this.coffs = coffs;
        this.b = b;
        this.scaling = scaling;
    }
    public double[] GuessElimination() {
        // turning from arrays to arraylist
        //---------------------------------

        ArrayList<ArrayList<Double>> equations = new ArrayList<ArrayList<Double>>();
        for(int k = 0; k < coffs.length ; k++)
        {

            ArrayList<Double> equationnumberk = new ArrayList<>();
            for(int j = 0; j < coffs[k].length ; j++){
                equationnumberk.add(coffs[k][j]);

            }
            equations.add(equationnumberk);


        }
        //----------------------------------------


        //turning the matrix into upper traingle matrix
        //-----------------------------------------------------------------------------
        for (int k = 0; k < equations.size() - 1; k++)
        {
            ArrayList<ArrayList<Double>> scaledcoof = new ArrayList<ArrayList<Double>>();
            //preparing the scaled coof if needed
            //-----------------------------------
            if (scaling == true)
            {
                for (int i = k; i < equations.size(); i++)
                {
                    double maxofcoff = Math.abs(equations.get(i).get(k));
                    for (int j = k; j < equations.size(); j++)
                    {
                        if (Math.abs(equations.get(i).get(j)) > maxofcoff)
                            maxofcoff = equations.get(i).get(j);

                    }

                    ArrayList<Double> Array = new ArrayList<>();
                    for (int j = k; j < equations.size(); j++)
                    {
                        if(maxofcoff==0)
                            Array.add(equations.get(i).get(j));
                        else
                            Array.add(equations.get(i).get(j)/maxofcoff);

                    }
                    scaledcoof.add(Array);

                }
            }
            //-------------------------------------
            //parial pivoting
            //-------------------------------------
            ArrayList<Double> temp = new ArrayList<Double>();
            double temp2;
            int maxindex = k;
            if(scaling==true)
            {
                for (int i = k; i < equations.size(); i++)
                {
                    if(Math.abs(scaledcoof.get(i-k).get(k-k))>Math.abs(scaledcoof.get(maxindex-k).get(k-k)))
                        maxindex=i;
                }
            }
            else
            {   for (int i = k; i < equations.size(); i++)
            {
                if(Math.abs(equations.get(i).get(k))>Math.abs(equations.get(maxindex).get(k)))
                    maxindex=i;
            }
            }
            temp = equations.get(k);
            equations.set(k,equations.get(maxindex));
            equations.set(maxindex,temp);
            temp2 = b[k];
            b[k]=b[maxindex];
            b[maxindex]=temp2;
            if(equations.get(k).get(k)==0)
                continue;

            //continue quess elimination
            //----------------------------------
            for(int i =k+1;i<equations.size();i++)
            {
                double factor = equations.get(i).get(k)/equations.get(k).get(k);
                for(int j = k;j<equations.size();j++)
                    equations.get(i).set(j,equations.get(i).get(j)-factor*equations.get(k).get(j));
                b[i]=b[i]-factor*b[k];
            }
            //----------------------------------
        }
        //------------------------------------------------------------------------
        //backward substitution
        double[] result = new double[equations.size()];
        result[equations.size()-1]=b[equations.size()-1]/equations.get(equations.size()-1).get(equations.size()-1);
        for(int i =equations.size()-2;i>=0;i--)
        {
            double sum =0;
            for(int j =i+1;j<equations.size();j++)
                sum = sum + equations.get(i).get(j)*result[j];
            result[i]=(b[i]-sum)/equations.get(i).get(i);
        }
        return result;



    }
}
