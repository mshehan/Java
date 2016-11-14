/*-------------------------------------------------------------------
// Name: Matthew Shehan
// Name: Matthew Shehan
// CruzID: 1499521
// Pa3
// Sparse.java
// ----------------
// this is the main program. the program takes in an
// input file and reads the text on the file. After
// reading in the file, creates a matrix with entries
// writes matrix operations onto a separate output file
--------------------------------------------------------------------*/

import java.io.*;
import java.util.Scanner;
class Sparse {
    public static void main(String args[]) throws FileNotFoundException {
        // make
        if(args.length != 2) {
            System.err.println(
                "Error: You must put in exactly two arguments");
            System.exit(1);
        }

        File inFile = new File(args[0]);
        File outFile = new File(args[1]);
        Scanner in = new Scanner(inFile);
        int n = 0;
        int[] entry = new int[2];
        Matrix A = null;
        Matrix B = null;
        // checks until file reachs end of line
        String info = in.nextLine();

        String[] infoItem = info.split("\\s");
        n = Integer.parseInt(infoItem[0],10);
        entry[0] = Integer.parseInt(infoItem[1],10);
        entry[1] = Integer.parseInt(infoItem[2],10);


        A = new Matrix(n);
        B = new Matrix(n);
        int row, col = 0;
        double val = 0.0;

        for(int i = 0; i < entry[0]; i++){
          row = in.nextInt();
          col = in.nextInt();
          val = in.nextDouble();
          A.changeEntry(row,col,val);
        }

        for(int i = 0; i < entry[1]; i++){
          row = in.nextInt();
          col = in.nextInt();
          val = in.nextDouble();
          B.changeEntry(row,col,val);
        }
        // closes the input file
        in.close();

        PrintWriter out = new PrintWriter(outFile);
        out.printf("A has %d non-zero entries\n%s\n",
            A.getNNZ(), A);
        out.printf("B has %d non-zero entries\n%s\n",
            B.getNNZ(), B);
        out.printf("(1.5)*A=\n%s\n", A.scalarMult(1.5));
        out.printf("A+B =\n%s\n", A.add(B));
        out.printf("A+A =\n%s\n", A.add(A));
        out.printf("B-A =\n%s\n", B.sub(A));
        out.printf("A-A =\n%s\n", A.sub(A));
        out.printf("Transpose(A) =\n%s\n", A.transpose());
        out.printf("A*B =\n%s\n", A.mult(B));
        out.printf("B*B =\n%s\n", B.mult(B));

        out.close();
    }
}
