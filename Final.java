/*
 * Computational Methods Final Project
 * Kevin A. Zhou
 * Jeremy Zhou
 * January 2018
 */
import java.util.*;
import java.io.*;
public class Final {
	
	public static void main(String[] args) throws IOException{

		//Input. We assume that the input matrix is triangular and diagonalizable.
		Scanner reader = new Scanner(new File("final.in"));
		
		ArrayList<double[][]> data = new ArrayList<double[][]>();
		ArrayList<Integer> matrixSizes = new ArrayList<Integer>();
		ArrayList<Integer> powers = new ArrayList<Integer>();
		
		int numPowers = reader.nextInt();
		for (int k = 0; k < numPowers; k++)
			powers.add(reader.nextInt());
		
		int numMatrices = reader.nextInt();
		for (int k = 0; k < numMatrices; k++) {
			int n = reader.nextInt();
			matrixSizes.add(n);
			double[][] inputMat = new double[n][n];
			for (int i = 0; i < n; i++)	
				for (int j = 0; j < n; j++)	
					inputMat[i][j] = reader.nextInt();
			data.add(inputMat);
		}
		reader.close();

		//Calculations
		long start; long end; long time;
		ArrayList<double[][]> answers1 = new ArrayList<double[][]>();
		ArrayList<double[][]> answers2 = new ArrayList<double[][]>();
		int reps = 1000;
		
		//Normal
		System.out.println("Brute Force");
		for (int i = 0; i < matrixSizes.size(); i++){
			for (int j = 0; j < powers.size(); j++) {
				start = System.nanoTime();
				for (int k = 0; k < reps; k++)	answers1.add(power(data.get(i), powers.get(j)));
				end = System.nanoTime();
				
				time = (end-start)/reps;
				System.out.println("size " + matrixSizes.get(i) + ", power " + powers.get(j) + ": " + time);
			}
			System.out.println();
		}
		System.out.println();
		
		//Diagonalization
		System.out.println("Diagonlization");
		for (int i = 0; i < matrixSizes.size(); i++){
			for (int j = 0; j < powers.size(); j++) {
				start = System.nanoTime();
				for (int k = 0; k < reps; k++)	answers2.add(diagPower(data.get(i), powers.get(j)));
				end = System.nanoTime();
				time = (end-start)/reps;
				System.out.println("size " + matrixSizes.get(i) + ", power " + powers.get(j) + ": " + time);
			}
			System.out.println();
		}
		
		//Check if diagmult() works
		for (int i = 0; i < 3; i++)
			if (answers1.equals(answers2))	System.out.println("Works");
	}
	
	//Prints a 2d matrix (for testing purposes)
	public static void printMatrix(double[][] mat) {
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[i].length; j++)
				System.out.print(mat[i][j] + " ");
			System.out.println();
		}
	}

	//Normal Matrix Multiplication
	public static double[][] power(double[][] mat, int power) {
		int n = mat.length;
		double[][] result = mat;
		double[][] tempResult = new double[n][n];
		
		double value = 0;
		double[] a = new double[n]; double[] b = new double[n];
		for (int p = 0; p < power-1; p++) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					a = mat[i];
					for (int k = 0; k < n; k++)	b[k] = result[k][j];
					
					value = 0;
					for (int k = 0; k < n; k++)	value += a[k]*b[k];
					tempResult[i][j] = value;
				}
			}
			result = tempResult;
		}
		
		return result;
	}

	//Matrix multiplication with diagonalization
	public static double[][] diagPower(double[][] mat, int power) {
		int n = mat.length;
		double[][] result = new double[n][n];
		//TODO
		return result;
	}
}
