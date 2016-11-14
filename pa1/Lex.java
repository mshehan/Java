/********************************************************************
// Name: Matthew Shehan
// Name: Matthew Shehan
// CruzID: 1499521
// Pa1
// Lex.java
// ----------------
// this is the main program. the program takes in an
// input file and reads the text on the file. After
// reading in the file, using the insertion sort
// algorithm, the file is written onto the output
// file lexicographically sorted.
*********************************************************************/

import java.io.*;
import java.util.Scanner;
class Lex {
    public static void main(String args[]) throws FileNotFoundException {
        // make
        if(args.length != 2) {
            System.err.println("Error: You must put in exactly two arguments");
            System.exit(1);
        }


        String fileContents = "";
        File inFile = new File(args[0]);
        File outFile = new File(args[1]);
        List sortedList = new List();
        Scanner in = new Scanner(inFile);

        // checks until file reachs end of line
        while(in.hasNext()) {
            fileContents += in.nextLine() + " ";
        }
        // closes the input file
        in.close();
        // splits the file contents on whitespace
        String[] fileItems = fileContents.split("\\s");

        if(fileItems.length > 1) {
            for(int i = 0, size = fileItems.length; i < size; i++) {
                int j = i+1;
                if(sortedList.length() == 0) {
                    sortedList.append(i);
                    sortedList.moveBack();
                }
                if(j < fileItems.length){
                    //while cursor still on list and s1 comes after s2
                    while( (sortedList.index() > -1) &&
                    (fileItems[sortedList.get()].compareTo(fileItems[j]) > 0) ) {
                        sortedList.movePrev();
                    }
                    // moved off the list must reached front
                    if(sortedList.index() == -1) {
                        sortedList.prepend(j);
                    } else { //still on list
                        sortedList.insertAfter(j);
                    }
                    //reset the cursor
                    sortedList.moveBack();
                }
            }
        }
        PrintWriter out = new PrintWriter(outFile);
        sortedList.moveFront();

        while(sortedList.index() > -1) {
            out.write(fileItems[sortedList.get()] + "\n");
            sortedList.moveNext();
        }
        out.close();
    }
}
