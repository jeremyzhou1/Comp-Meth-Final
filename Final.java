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
		ArrayList<Integer> vectorSizes = new ArrayList<Integer>();
		ArrayList<Integer> powers = new ArrayList<Integer>();
		
		int numPowers = reader.nextInt();
		for (int k = 0; k < numPowers; k++) {
			powers.add(reader.nextInt());
		}
		int numMatrices = reader.nextInt();
		for (int k = 0; k < numMatrices; k++) {
			int n = reader.nextInt();
			vectorSizes.add(n);
			double[][] inputMat = new double[n][n];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					inputMat[i][j] = reader.nextInt();
				}
			}
			data.add(inputMat);
		}
		reader.close();

		//Calculations
		long start; long end;
		ArrayList<double[][]> answers1 = new ArrayList<double[][]>();
		ArrayList<double[][]> answers2 = new ArrayList<double[][]>();
		
		//Normal
		System.out.println("Brute Force Powers");
		for (int i = 0; i < vectorSizes.size(); i++){
			for (int j = 0; j < powers.size(); j++) {
				start = System.nanoTime();
				answers1.add(power(data.get(i), powers.get(j)));
				end = System.nanoTime();
				System.out.println("size " + vectorSizes.get(i) + ", power " + powers.get(j) + ": " + (end-start));
			}
			System.out.println();
		}
		System.out.println();
		
		//Diagonilization
		System.out.println("Powers with Diagonlization");
		for (int i = 0; i < vectorSizes.size(); i++){
			for (int j = 0; j < powers.size(); j++) {
				start = System.nanoTime();
				answers1.add(diagPower(data.get(i), powers.get(j)));
				end = System.nanoTime() - start;
				System.out.println("size " + vectorSizes.get(i) + ", power " + powers.get(j) + ": " + (end-start));
			}
			System.out.println();
		}
		System.out.println();
		
		//Check if diagmult() works
		for (int i = 0; i < 3; i++) {
			if (answers1.equals(answers2))	System.out.println("Works");
		}
	}
	
	//Prints a 2d matrix (for testing purposes)
	public static void printMatrix(double[][] mat) {
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[i].length; j++) {
				System.out.print(mat[i][j] + " ");
			}
			System.out.println();
		}
	}

	//Normal Matrix Multiplication
	public static double[][] power(double[][] mat, int power) {
		int n = mat.length;
		double[][] result = new double[n][n];
		
		double value = 0;
		double[] a = new double[n]; double[] b = new double[n];
		for (int p = 0; p < power; p++) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					a = mat[i];
					for (int k = 0; k < n; k++)	b[k] = mat[k][j];
					
					value = 0;
					for (int k = 0; k < n; k++)	value += a[k]*b[k];
					result[i][j] = value;
				}
			}
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
