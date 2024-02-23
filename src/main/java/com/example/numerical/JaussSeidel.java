package com.example.numerical;

public class JaussSeidel {
    private double[] output;

    private double[][] matrix;
    private double[] constants;


    public void setMatrix(double[][] matrix) {
        this.matrix = matrix;
    }
    public double[][] getMatrix() {
        return matrix;
    }
    public double[] getConstants() {
        return constants;
    }

    public void setConstants(double[] constants) {
        this.constants = constants;
    }

    private int iterations = 15;
    private double[] initial;
    private int precision = 5;
    double epsilon = 1e-15;
    public int getPrecision() {
        return precision;
    }
    public void setPrecision(int precision) {
        this.precision = precision;
    }
    public double[] getOutput() {
        return output;
    }
    public void setOutput(double[] output) {
        this.output = output;
    }
    public int getIterations() {
        return iterations;
    }
    public void setIterations(int iterations) {
        this.iterations = iterations;
    }
    public double[] getInitial() {
        return initial;
    }
    public void setInitial(double[] initial) {
        this.initial = initial;
    }

    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
    }

    public void printOutput()
    {
        if(this.output == null) {
            return;
        }
        for(int i=0;i<output.length;i++) {
            System.out.println(output[i]);
        }
    }
    public double[] solve(){
        output = initial.clone();
        boolean epsilonAchieved = true;
        for(int i=0;i<iterations;i++) {
            epsilonAchieved = true;
            for(int j=0;j<output.length;j++) {
                double sum = 0;
                for(int k=0;k<output.length;k++) {
                    sum+= matrix[j][k] * output[k];
                }
                double temp = (constants[j] - sum + output[j]*matrix[j][j])/matrix[j][j];
                System.out.println("Relative error = " + (temp-output[j]/temp));
                epsilonAchieved = epsilonAchieved & (Math.abs((temp-output[j]/temp))<epsilon);
                output[j] = temp;
                System.out.println(String.format("%.2f", output[j]));
            }
            if (epsilonAchieved){
                System.out.println("Achieve Epsilon " + epsilon);
                break;
            }
        }
        return output;
    }
}