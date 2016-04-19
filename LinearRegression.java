/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linearregression;

import Jama.Matrix;
import java.util.Random;


/**
 *
 * @author TaKuma
 */
public class LinearRegression {

    /*
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        final int SIZE = 100;
        final int dim = 3;
        double[][] points  = new double[SIZE][dim];
        double[][] points2  = new double[1000][dim];
        Random rGenerator = new Random();
      
        double[][] oWeights = new double[4][1000];
        double[][] results = new double[4][1000];
        
        for(int z = 0; z < 1000; z++) { //Repeat this experiment 1000 times
     
            //Generate Dataset
            for(int i = 0; i < SIZE; i++){
                for(int j = 0; j < dim - 1;j++){
                    points[i][j] = rGenerator.nextFloat() * 2 - 1;
                }
            }  


            // picks 2 random points from dataset and make a line
            int index1 = rGenerator.nextInt(SIZE);
            int index2 = rGenerator.nextInt(SIZE);
            double x1 = points[index1][0];
            double y1 = points[index1][1];
            double x2 = points[index2][0]; 
            double y2 = points[index2][1];
            double gradient = (y2 - y1)/(x2 - x1); //gradient

            //Equatiion of line 
            double c = (-gradient*x1)+y1;
        /*  System.out.println("y = " + gradient+"x" + " + " + c +"\n");        
            System.out.println("w0 = "+ c);
            System.out.println("w1 = "+ gradient);
            System.out.println("w2 = -1"); */
            oWeights[0][z] = c;
            oWeights[1][z] = gradient;
            oWeights[2][z] = -1;


            //Mapping the points 1/-1
            double map;
            for(int i = 0; i < SIZE; i++){
                map = gradient*points[i][0] - points[i][1] + c;
                if (map > 0)
                    points[i][2] = +1;
                else
                    points[i][2] = -1;
            }

            // Fill the X 
            double[][] X = new double[SIZE][dim];   
            for (int i = 0; i < SIZE; i++){
                for(int j = 0; j < 3; j++){
                    if ( j == 0)
                        X[i][j] = 1;
                    else
                        X[i][j] = points[i][j-1];
                }
            } 

            // Fill the Y
            double[][] Y = new double[SIZE][1];
            for (int i = 0; i < SIZE; i++){
                Y[i][0] = points[i][2];
            }
            
            //Puts X and Y into Matrix Form
            Matrix yMatrix = new Matrix(Y);
            Matrix xMatrix = new Matrix(X);

            //Do matrix operations 
            //xMatrix.print(dim, dim);
            Matrix xTranspose = xMatrix.transpose();//X^T
            //xTranspose.print(dim, dim);
            Matrix XtXInverse = xTranspose.times(xMatrix).inverse(); //(X^TX)^-1
            //XtXInverse.print(dim, dim);
            Matrix pInverse = XtXInverse.times(xTranspose); // X(pseudo-inverse) = (X^TX)^-1X^T
           // pInverse.print(dim, dim);
            Matrix wLin = pInverse.times(yMatrix);
           // wLin.print(dim, 15);   // Weights of Liner Regression
            //Store the weights
            results[0][z] = wLin.get(0,0);
            results[1][z] = wLin.get(1,0);
            results[2][z] = wLin.get(2,0);

            //Find the Ein
            int error = 0;
            for(int i = 0; i < SIZE; i++){
                double map2 = wLin.get(1, 0)*points[i][0] + wLin.get(2, 0)*points[i][1] + wLin.get(0, 0)*1;
                double temp;
                if (map2 > 0 )
                    temp = 1;
                else
                    temp = -1;
                if (points[i][2] != temp)
                    error++;
            }
            //Stores the Ein Error
            results[3][z] = (double) error/SIZE;
            //System.out.println("Ein = " + results[3][0]);   
            
        }
        
        //Computes the Average Ein Erorr
        double sum = 0;
        for(int i = 0; i < 1000; i++){
            sum = sum + results[3][i];
        }
        System.out.println("Average Ein = " + (double)sum/1000);
        
        
        //Performs the experiment 1000 times
        for (int z = 0; z < 1000; z++){ 
            //Generates a New data Set
            for(int i = 0; i < 1000; i++){
                    for(int j = 0; j < dim - 1;j++){
                        points2[i][j] = rGenerator.nextFloat() * 2 - 1;
                    }
            }
            //Classifies Data Set based on Orignal Weights
            //Mapping the points 1/-1
            double map;
            for(int i = 0; i < 1000; i++){
                map = oWeights[1][z]*points2[i][0] - points2[i][1] + oWeights[0][z];
                if (map > 0)
                    points2[i][2] = +1;
                else
                    points2[i][2] = -1;
            }
            //Estimates the Eout of Data Set
            int error = 0;
            for(int i = 0; i < 1000; i++){
                double map2 = results[1][z]*points2[i][0] + results[2][z]*points2[i][1] + results[0][z]*1;
                double temp;
                if (map2 > 0 )
                    temp = 1;
                else
                    temp = -1;
                if (points2[i][2] != temp)
                    error++;
            }
            
            oWeights[3][z] = (double)error/1000;
            //System.out.println("Average Eout = " + (double)error/1000);
        }
        
        //Computes the Average Eout Erorr
        double sum2 = 0;
        for(int i = 0; i < 1000; i++){
            sum2 = sum2 + oWeights[3][i];
        }
        System.out.println("Average Eout = " + (double)sum2/1000);
    }
}
