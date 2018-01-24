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
		int reps = 100;
		
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
		System.out.println("Diagonalization");
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
	}

	//Find the kth power of a matrix
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
	
	//Multiply two matrices together
	public static double[][] mult(double[][] mat1, double[][] mat2) {
		int n = mat1.length;
  		double value = 0;
 		double[] a = new double[n]; double[] b = new double[n];
 		double[][] result = new double[n][n];
  		for (int i = 0; i < n; i++) {
  			for (int j = 0; j < n; j++) {
  				value = 0;
  				a = mat1[i];
  				for (int k = 0; k < n; k++)	b[k] = mat2[k][j];
 				for (int k = 0; k < n; k++)	value += a[k]*b[k];
  				result[i][j] = value;
  			}
  		}
  		return result;
	}

	//Matrix multiplication with diagonalization
	public static double[][] diagPower(double[][] mat, int power) {
		int n = mat.length;
		double[][] result = new double[n][n];
		double[][] temp = new double[n][n];
		double[][] diagonal = new double[n][n];
		double[][] pmatrixTranspose = new double[n][n]; //transpose of the pmatrix
		double[][] pmatrix = new double[n][n];
		double[][] pmatrixInverse = new double[n][n];
		//TODO
		
		double[] eigenvalues = new double[n];
		for(int i = 0; i < n; i++) //eigenvalue = diagonal entries
			eigenvalues[i] = mat[i][i];
		
		for(int i = 0; i < n; i++) { //for each eigenvalue
			for(int j = 0; j < n; j++) { //create (A - lambda I)
				for(int k = 0; k < n; k++) {
					temp[j][k] = mat[j][k];
					if(j == k)
						temp[j][k] -= eigenvalues[i];
				}
			}
			//Solve the homogeneous equation temp x = 0
			pmatrixTranspose[i] = eigen(temp);
			diagonal[i][i] = Math.pow(eigenvalues[i],power);
		}
		
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				pmatrix[i][j] = pmatrixTranspose[j][i];
			}
		}
		pmatrixInverse = invert(pmatrix);
		
		result = mult(pmatrix, diagonal);
		result = mult(result, pmatrixInverse);
		
		return result;
	}
	
	/* Row reduces the matrix and finds the eigenvectors.
	 * This assumes that the matrix is triangular and that all eigenvalues are distinct.
	 */
	public static double[] eigen(double[][] array) {
		int n = array.length;
		double[] eigenvector = new double[n];
		
		for(int i = 0; i < n; i++) {
			if(array[i][i] != 0)
				rowScale(array, i, 1/array[i][i]);
		}
		
		for(int i = n-2; i >= 0; i--) { //Backwards substitution
			for(int j = i+1; j < n; j++) {
				rowReplace(array, j, i, -array[i][j]);
			}
		}

		boolean flag = true;
		for(int i = 0; i < n; i++) {
			if(flag) {
				if(array[i][i] == 0) {
					eigenvector[i] = 1;
					flag = false;
				}
				else {
					for(int j = i+1; j < n; j++) {
						if(array[i][j] != 0) {
							eigenvector[i] = -1*array[i][j];
						}
					}
				}
			}
			else
				eigenvector[i] = 0;
		}

		return eigenvector;
	}
	
	public static void rowScale(double[][] array, int r, double k) { //Multiplies all elements of row r by k
		for (int j = 0; j < array[r].length; j++) {
			array[r][j] *= k;
		}
	}
	
	public static void rowReplace(double[][] array, int i, int j, double k) { //Row j -> Row j + k * Row i
		for (int x = 0; x < array[i].length; x++) {
			array[j][x] += array[i][x] * k;
		}
	}

	//Prints a 2d matrix
	public static void printMatrix(double[][] mat) {
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[i].length; j++) {
				System.out.print(mat[i][j] + "\t");
			}
			System.out.println();
		}
	}
	
	/**
	 * Inverts a matrix.
	 */
	 
	public static double[][] invert(double a[][]) 
	    {
	        int n = a.length;
	        double x[][] = new double[n][n];
	        double b[][] = new double[n][n];
	        int index[] = new int[n];
	        for (int i=0; i<n; ++i) 
	            b[i][i] = 1;
	 
	 // Transform the matrix into an upper triangle
	        gaussian(a, index);
	 
	 // Update the matrix b[i][j] with the ratios stored
	        for (int i=0; i<n-1; ++i)
	            for (int j=i+1; j<n; ++j)
	                for (int k=0; k<n; ++k)
	                    b[index[j]][k]
	                    	    -= a[index[j]][i]*b[index[i]][k];
	 
	 // Perform backward substitutions
	        for (int i=0; i<n; ++i) 
	        {
	            x[n-1][i] = b[index[n-1]][i]/a[index[n-1]][n-1];
	            for (int j=n-2; j>=0; --j) 
	            {
	                x[j][i] = b[index[j]][i];
	                for (int k=j+1; k<n; ++k) 
	                {
	                    x[j][i] -= a[index[j]][k]*x[k][i];
	                }
	                x[j][i] /= a[index[j]][j];
	            }
	        }
	        return x;
	    }
	 
	// Method to carry out the partial-pivoting Gaussian
	// elimination.  Here index[] stores pivoting order.
	 
	    public static void gaussian(double a[][], int index[]) 
	    {
	        int n = index.length;
	        double c[] = new double[n];
	 
	 // Initialize the index
	        for (int i=0; i<n; ++i) 
	            index[i] = i;
	 
	 // Find the rescaling factors, one from each row
	        for (int i=0; i<n; ++i) 
	        {
	            double c1 = 0;
	            for (int j=0; j<n; ++j) 
	            {
	                double c0 = Math.abs(a[i][j]);
	                if (c0 > c1) c1 = c0;
	            }
	            c[i] = c1;
	        }
	 
	 // Search the pivoting element from each column
	        int k = 0;
	        for (int j=0; j<n-1; ++j) 
	        {
	            double pi1 = 0;
	            for (int i=j; i<n; ++i) 
	            {
	                double pi0 = Math.abs(a[index[i]][j]);
	                pi0 /= c[index[i]];
	                if (pi0 > pi1) 
	                {
	                    pi1 = pi0;
	                    k = i;
	                }
	            }
	 
	   // Interchange rows according to the pivoting order
	            int itmp = index[j];
	            index[j] = index[k];
	            index[k] = itmp;
	            for (int i=j+1; i<n; ++i) 	
	            {
	                double pj = a[index[i]][j]/a[index[j]][j];
	 
	 // Record pivoting ratios below the diagonal
	                a[index[i]][j] = pj;
	 
	 // Modify other elements accordingly
	                for (int l=j+1; l<n; ++l)
	                    a[index[i]][l] -= pj*a[index[j]][l];
	            }
	        }
	    }
	
}
