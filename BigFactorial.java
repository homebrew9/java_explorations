import java.math.BigInteger;
import java.lang.*;
import java.io.*;

class BigFactorial {
  public static void main(String args[]) {
    BigInteger sum = BigInteger.ONE;
    for(int i=1; i<=(new Integer(args[0])).intValue(); i++)
      sum=sum.multiply(BigInteger.valueOf(i));
    System.out.println("the factorial is "+sum);
  }
}
