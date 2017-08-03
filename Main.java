import java.util.Scanner;
import java.util.Random;

import java.util.NoSuchElementException;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        dll A = new dll();
        dll B = new dll();

        Scanner sam = null ;
        int onum = 0       ;
        float b, c, d, e   ;

        try{
            sam = new Scanner(new File( "/Users/kieranderfus/IdeaProjects/PA2-KMeansClustering/src/lab2_data.txt" )) ;
        }
        catch (FileNotFoundException oops) {
            System.out.println( "file not found." ) ;
            return ;
        }

        boolean loop ;
        loop = true  ;

        do {

            try {
                onum = sam.nextInt() ;
            }
            catch ( InputMismatchException badI )  {

            }
            catch ( NoSuchElementException at_eof ) {
                loop = false ;
            }

            if ( loop ) {
                b = sam.nextFloat() ;
                c = sam.nextFloat() ;
                d = sam.nextFloat() ;
                e = sam.nextFloat() ;

                listcell temp = new listcell();

                temp.obs_num = onum;
                temp.sep_length = b;
                temp.sep_width = c;
                temp.pet_length = d;
                temp.pet_width = e;


                int n;
                Random rand = new Random();

                n = rand.nextInt(2);

                if (n == 0) {
                    A.append(temp);

                }
                if (n == 1) {
                    B.append(temp);
                }
            }

        } while (loop) ;

        boolean change;

        do {

            change = false;

            listcell A_node = A.head;

            while (A_node != null) {


                float[] meanA = ComputeMean(A);
                float[] meanB = ComputeMean(B);

                double EuclideanA = Euclidean(A_node, meanA);

                double EuclideanB = Euclidean(A_node, meanB);

                if (EuclideanB < EuclideanA) {
                    listcell temp1 = A_node;
                    listcell temp2 = A_node;

                    A.delete(A_node);
                    B.append(temp1);

                    A_node = temp2.getNext();

                    change = true;

                } else {
                    A_node = A_node.getNext();
                }
            }


            listcell B_node = B.head;

            while (B_node != null) {

                float[] meanA = ComputeMean(A);
                float[] meanB = ComputeMean(B);

                double EuclideanA = Euclidean(B_node, meanA);

                double EuclideanB = Euclidean(B_node, meanB);

                if (EuclideanA < EuclideanB) {
                    listcell temp1 = B_node;
                    listcell temp2 = B_node;

                    B.delete(B_node);
                    A.append(temp1);

                    B_node = temp2.getNext();
                    change = true;

                } else {
                    B_node = B_node.getNext();
                }
            }

        } while (change);

        write_a_text_file("A.txt", A);
        write_a_text_file("B.txt", B);

    }


    private static float[] ComputeMean(dll x) {

        float[] mean_array = new float[4];
        listcell helper = x.head;
        int j = x.size;

        for (int i = 1; i <= x.size; i++) {

            mean_array[0] += helper.sep_length; // sums up all entries
            mean_array[1] += helper.sep_width;
            mean_array[2] += helper.pet_length;
            mean_array[3] += helper.pet_width;

            if (helper.getNext() != null) helper = helper.getNext();

        }

        for (int i = 0; i < 4; i++) { // divides sum of all entries by # of entries
            mean_array[i] /= j;
        }

        return mean_array;
    }


    private static double Euclidean (listcell x, float [] myarray) {

        double sum_distance1 = ((myarray[0] - x.sep_length) * (myarray[0] - x.sep_length))
                + ((myarray[1] - x.sep_width) * (myarray[1] - x.sep_width))
                + ((myarray[2] - x.pet_length) * (myarray[2] - x.pet_length))
                + ((myarray[3] - x.pet_width) * (myarray[3] - x.pet_width));


        return Math.sqrt(sum_distance1);

    }

    public static void write_a_text_file(String fname, dll x) {

        File outfile = null;
        BufferedWriter fwriter = null;

        global_constants G = new global_constants();

        try {
            outfile = new File(fname);
            fwriter = new BufferedWriter(new FileWriter(outfile));
        } catch (Exception Hate_Java_IO) {
            Hate_Java_IO.printStackTrace();
            return;
        }

        try {

            listcell temp = x.head;
            while (temp != null) {
                fwriter.write(temp.obs_num + " " + temp.sep_length + " " + temp.sep_width + " "
                + temp.pet_length + " " + temp.pet_width);
                fwriter.newLine();
                temp = temp.getNext();
            }
            fwriter.write("\n");
        } catch (Exception Hate_Java_IO) {
            Hate_Java_IO.printStackTrace();
            return;
        }

        try {
            fwriter.close();
        } catch (Exception Hate_Java_IO) {
            Hate_Java_IO.printStackTrace();
        }
    }
}
