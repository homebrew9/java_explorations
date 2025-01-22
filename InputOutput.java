/*
A simple java file that reads in characters (from a file,
the user input, the standard input, or whatever) and
just outputs them again, one by one
*/

import java.util.Scanner;
public class InputOutput {
 public static void main (String[] args) {
  Scanner scnr;
  String line;
  System.out.println("\nThis program prompts for a line of text.");
  System.out.println("And prints the characters, one per line.");
  System.out.println("Enter \"quit\" to end.\n");
  // Input
  System.out.print("==> ");
  scnr = new Scanner(System.in);
  line = scnr.nextLine();
  // Loop after first input
  while (! line.equals("quit")) {
   // Output
   for (int i=0; i<line.length(); i++) {
    System.out.println(line.charAt(i));
   }
   // Input again
   System.out.print("==> ");
   scnr = new Scanner(System.in);
   line = scnr.nextLine();
  }
 }
}

