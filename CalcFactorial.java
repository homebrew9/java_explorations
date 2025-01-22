import java.math.BigInteger;
import java.lang.*;
import java.io.*;
public class CalcFactorial {
  public static String calc_fact (int bigNum) {
    BigInteger sum = BigInteger.ONE;
    for(int i=1;i<=bigNum;i++)
      sum=sum.multiply(BigInteger.valueOf(i));
    return (String.valueOf(sum));
  }
}
