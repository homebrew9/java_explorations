// A Java program to count all subsets with given sum.
import java.util.ArrayList;
import java.nio.file.*;
import java.io.*;
import java.util.*;
public class subset_sum_problem {
    // dp[i][j] is going to store true if sum j is
    // possible with array elements from 0 to i.
    static boolean[][] dp;
    static void display(ArrayList<Integer> v) {
        System.out.println(v);
    }

    // A recursive function to print all subsets with the
    // help of dp[][]. Vector p[] stores current subset.
    static void printSubsetsRec(int arr[], int i, int sum, ArrayList<Integer> p) {
        // If we reached end and sum is non-zero. We print
        // p[] only if arr[0] is equal to sun OR dp[0][sum]
        // is true.
        if (i == 0 && sum != 0 && dp[0][sum]) {
            p.add(arr[i]);
            display(p);
            p.clear();
            return;
        }
        // If sum becomes 0
        if (i == 0 && sum == 0) {
            display(p);
            p.clear();
            return;
        }
        // If given sum can be achieved after ignoring
        // current element.
        if (dp[i-1][sum]) {
            // Create a new vector to store path
            ArrayList<Integer> b = new ArrayList<>();
            b.addAll(p);
            printSubsetsRec(arr, i-1, sum, b);
        }
        // If given sum can be achieved after considering
        // current element.
        if (sum >= arr[i] && dp[i-1][sum-arr[i]]) {
            p.add(arr[i]);
            printSubsetsRec(arr, i-1, sum-arr[i], p);
        }
    }

    // Prints all subsets of arr[0..n-1] with sum 0.
    static void printAllSubsets(int arr[], int n, int sum) {
        if (n == 0 || sum < 0) {
            return;
        }
        // Sum 0 can always be achieved with 0 elements
        dp = new boolean[n][sum + 1];
        for (int i=0; i<n; ++i) {
            dp[i][0] = true;
        }
        // Sum arr[0] can be achieved with single element
        if (arr[0] <= sum) {
            dp[0][arr[0]] = true;
        }
        // Fill rest of the entries in dp[][]
        for (int i = 1; i < n; ++i) {
            for (int j = 0; j < sum + 1; ++j) {
                dp[i][j] = (arr[i] <= j) ? (dp[i-1][j] || dp[i-1][j-arr[i]]) : dp[i - 1][j];
            }
        }
        if (dp[n-1][sum] == false) {
            System.out.println("There are no subsets with sum "+ sum);
            return;
        }
        // Now recursively traverse dp[][] to find all
        // paths from dp[n-1][sum]
        ArrayList<Integer> p = new ArrayList<>();
        printSubsetsRec(arr, n-1, sum, p);
    }

    static String readFileAsString(String fileName) throws Exception {
        String data = "";
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
    }

    static int[] convertIntegers(List<Integer> integers) {
        int[] ret = new int[integers.size()];
        for (int i=0; i < ret.length; i++) {
            ret[i] = integers.get(i).intValue();
        }
        return ret;
    }

    //Driver Program to test above functions
    public static void main(String args[]) throws Exception {
        String fileName = "C:\\data\\myfile.txt";
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName))));
        float x;
        int sum = 0;
        int item;
        List<Integer> values = new ArrayList<Integer>();
        boolean isFirstLine = true;
        String line = null;
        String[] numbers = null;
        // Read line by line till end of file
        while ((line = reader.readLine()) != null) {
            x = Float.valueOf(line.trim());
            if (isFirstLine) {
                sum = (int)(x * 100);
                System.out.println("FIRST LINE sum: " + sum);
                isFirstLine = false;
            } else {
                item = (int)(x * 100);
                values.add(item);
                System.out.println("item: " + item);
            }
        }
        int arr[] = convertIntegers(values);
        int n = arr.length;

        //String data = readFileAsString("C:\\data\\myfile.txt");
        //System.out.println(data);
        System.out.println("=======================================");
        //int arr[] = {1, 2, 3, 4, 5};
        //int n = arr.length;
        //int sum = 10;
        printAllSubsets(arr, n, sum);
    }
}

