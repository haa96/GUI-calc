import java.util.*;
import java.io.*;

public class Tokenize
{
	public static void main( String[] args)
	{
		// Stolen directly from stackoverflow with just a few mods :)

		String expr="4+5-12/3.5-5.4*3.14"; // replace with any expression to test
		System.out.println( "expr: " + expr );
		ArrayList<String> operatorList = new ArrayList<String>();
		ArrayList<Double> operandList = new ArrayList<Double>();
		// StringTokenizer is like an infile and calling .hasNext()
		StringTokenizer st = new StringTokenizer( expr,"+-*/", true );
		while (st.hasMoreTokens())
		{
			String token = st.nextToken();
			if ("+-/*".contains(token))
				operatorList.add(token);
			else
				operandList.add( Double.parseDouble( token) );
    		}
 		System.out.println("Operators:" + operatorList);
		System.out.println("Operands:" + operandList);
	}
}

