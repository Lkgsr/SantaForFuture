package santa.tools;

public class MatrixOperations {

    public int[][] Multiply(int[][] a, int[][] b) {
        if (a.length != b[0].length) {
            throw new IllegalArgumentException("This this.rows" + a.length + " != " + b[0].length + "b.columns");
        }
        int[][] dot = new int[a.length][b[0].length];
        //b = T(b);
        int sum = 0;
        for (int index_row_a = 0; index_row_a < a.length; index_row_a++) {
            for (int index_row_b = 0; index_row_b < b[0].length; index_row_b++) {
                sum = 0;
                for (int index_column = 0; index_column < a[0].length; index_column++) {
                    sum += a[index_row_a][index_column] * b[index_row_b][index_column];
                }
                dot[index_row_a][index_row_b] = sum;

            }
        }

        return dot;
    }

    public double[][] T(double[][] a) {
        double[][] a_t = new double[a[0].length][a.length];
        for (int index_y = 0; index_y < a.length; index_y++) {
            for (int index_x = 0; index_x < a[0].length; index_x++) {
                a_t[index_x][index_y] = a[index_y][index_x];
            }
        }
        return a_t;
    }

    public int spur(int[][] a) {
        int sum = 0;
        for (int i = 0; i < a.length; i++) {
            for (int ii = 0; ii < a[0].length; ii++) {
                if (i == ii) {
                    sum += a[i][ii];
                }
            }
        }
        return sum;
    }

    public double[][] remove_row(double[][] a, int row) {
        double[][] mat = new double[a.length - 1][a[0].length];
        int new_index = 0;
        for (int i = 0; i < mat.length; i++) {
            if (i == row) {
                new_index += 1;
            }
            mat[i] = a[new_index];
            new_index += 1;
        }
        return mat;
    }

    public double[][] subArray(double[][] a, int row, int column) {
        double[][] sub_array = remove_row(T(remove_row(a, row)), column);
        return T(sub_array);

    }

    /*public int determinant(int[][] a){
        if(a.length != a[0].length){
            throw new IllegalArgumentException("Must be a quadratic matrix");
        }
        if (a.length == 2){
            return a[0][0]*a[1][1] - a[0][1]*a[1][0];
        }
        double sum = 0;
            for (int columns = 0; columns<a.length; columns++){
                if(a[0][columns]!=0){
                    sum += Math.pow(-1, 0 + columns) * a[0][columns] * determinant(subArray(a, 0, columns));
                }
            }
            return (int) sum;
    } */

}
