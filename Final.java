import java.util.*;
import java.io.*;
public class Final {
	public static void main(String[] args) throws IOException{

		//Input
		Scanner reader = new Scanner(new File("final.in"));
		int n = reader.nextInt();
		double[][] test1 = new double[n][n];
		double[][] test2 = new double[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				test1[i][j] = reader.nextInt();
			}
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				test2[i][j] = reader.nextInt();
			}
		}
		reader.close();

		//Normal Matrix Multiplication
		long time = System.nanoTime();
		double[][] result1 = mult(test1, test2);
		long time1 = System.nanoTime() - time;
		printMatrix(result1);

		//Matrix multiplication with diagonalization
		time = System.nanoTime();
		double[][] result2 = diagMult(test1, test2);
		long time2 = System.nanoTime() - time;
		printMatrix(result2);

		//Output
		System.out.println("Normal Multiplication Time: " + time1);
		System.out.println("Diagonilization Multiplication Time:" + time2);
	}

	//Normal Matrix Multiplication
	public static double[][] mult(double[][] mat1, double[][] mat2) {
		int n = mat1.length;
		double value = 0;
		double[] a, double[] b;
		double[][] result = new double[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				value = 0;
				a = mat1[i];
				for (int k = 0; k < n; k++)	b[k] = mat2[k][j];
				for (int k = 0; i < n; k++)	value += a[k]*b[k];
				result[i][j] = value;
			}
		}
		return result;
	}

	//Matrix multiplication with diagonalization
	public static double[][] diagMult(double[][] mat1, double[][] mat2) {
		int n = mat1.length;
		double[][] result = new double[n][n];
		\\TODO
		return result;
	}

	//Prints a 2d matrix
	public static void printMatrix(double[][] mat) {
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[i].length; j++) {
				System.out.print(mat[i][j]);
			}
			System.out.println();
		}
	}
}
