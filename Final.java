import java.util.*;
import java.io.*;
public class Final {
	public static void main(String[] args) {
		double[][] test1 = null;
		double[][] test2 = null;
		
		long time = System.currentTimeMillis();
		double[][] result1 = mult(test1, test2);
		long time1 = System.currentTimeMillis() - time;
		printMatrix(result1);
		
		time = System.currentTimeMillis();
		double[][] result2 = diagMult(test1, test2);
		long time2 = System.currentTimeMillis() - time;
		printMatrix(result2);
		
		System.out.println("Normal Multiplication Time: " + time1);
		System.out.println("Diagonilization Multiplication Time:" + time2);
	}
	
	public static double[][] mult(double[][] mat1, double[][] mat2) {
		//TODO
		return null;
	}
	
	public static double[][] diagMult(double[][] mat1, double[][] mat2) {
		//TODO
		return null;
	}
	
	public static void printMatrix(double[][] mat) {
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[i].length; j++) {
				System.out.print(mat[i][j]);
			}
			System.out.println();
		}
	}
}