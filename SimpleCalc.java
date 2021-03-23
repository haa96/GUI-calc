import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class SimpleCalc {

	JFrame window;  // the main window which contains everything
	Container content ;
	JButton[] digits = new JButton[12]; 
	JButton[] ops = new JButton[4];
	JTextField expression;
	JButton equals;
	JTextField result;

	public SimpleCalc()
	{
		window = new JFrame( "Simple Calc");
		content = window.getContentPane();
		content.setLayout(new GridLayout(2,1)); // 2 row, 1 col
		ButtonListener listener = new ButtonListener();
		
		// top panel holds expression field, equals sign and result field  
		// [4+3/2-(5/3.5)+3]  =   [3.456]
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1,3)); // 1 row, 3 col
		
		expression = new JTextField();
		expression.setFont(new Font("verdana", Font.BOLD, 16));
		expression.setText("");
		
		equals = new JButton("=");
		equals.setFont(new Font("verdana", Font.BOLD, 20 ));
		equals.addActionListener( listener ); 
		
		result = new JTextField();
		result.setFont(new Font("verdana", Font.BOLD, 16));
		result.setText("");
		
		topPanel.add(expression);
		topPanel.add(equals);
		topPanel.add(result);
						
		// bottom panel holds the digit buttons in the left sub panel and the operators in the right sub panel
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1,2)); // 1 row, 2 col
	
		JPanel  digitsPanel = new JPanel();
		digitsPanel.setLayout(new GridLayout(4,3));	
		
		for (int i=0 ; i<10 ; i++ )
		{
			digits[i] = new JButton( ""+i );
			digitsPanel.add( digits[i] );
			digits[i].addActionListener( listener ); 
		}
		digits[10] = new JButton( "C" );
		digitsPanel.add( digits[10] );
		digits[10].addActionListener( listener ); 

		digits[11] = new JButton( "CE" );
		digitsPanel.add( digits[11] );
		digits[11].addActionListener( listener ); 		
	
		JPanel opsPanel = new JPanel();
		opsPanel.setLayout(new GridLayout(4,1));
		String[] opCodes = { "+", "-", "*", "/" };
		for (int i=0 ; i<4 ; i++ )
		{
			ops[i] = new JButton( opCodes[i] );
			opsPanel.add( ops[i] );
			ops[i].addActionListener( listener ); 
		}
		bottomPanel.add( digitsPanel );
		bottomPanel.add( opsPanel );
		
		content.add( topPanel );
		content.add( bottomPanel );
	
		window.setSize( 640,480);
		window.setVisible( true );
	}

	class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			Component whichButton = (Component) e.getSource();
			// how to test for which button?
			// this is why our widgets are 'global' class members
			// so we can refer to them in here
			
			for (int i=0 ; i<10 ; i++ )
				if (whichButton == digits[i])
					expression.setText( expression.getText() + i );
			if (whichButton == digits[10])// C
			{
				expression.setText("");
				result.setText("");
			}
			if (whichButton == digits[11])// CE
			{
				result.setText("");
				String exp = expression.getText();
				if (exp.length() > 0)
				{
					exp = exp.substring(0, exp.length()-1);
					expression.setText(exp);
				}
				else
					expression.setText("");
			}
			if (whichButton == ops[0])
				expression.setText(expression.getText() + "+");
			if (whichButton == ops[1])
				expression.setText(expression.getText() + "-");
			if (whichButton == ops[2])
				expression.setText(expression.getText() + "*");
			if (whichButton == ops[3])
				expression.setText(expression.getText() + "/");
					
			 if (whichButton == equals)
				result.setText( evaluate() );
		}

	public String evaluate()
	{
		if ( !isValid( expression.getText() )) return "INVALID"; // WRITE A ISVALID method
				
		String expr= expression.getText(); // replace with any expression to test
		ArrayList<String> operators = new ArrayList<String>();
		ArrayList<String> operands = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer( expr,"+-*/", true );
		while (st.hasMoreTokens())
		{
			String token = st.nextToken();
			if ("+-/*".contains(token))
				operators.add(token);
			else
				operands.add(token);
		}
		for (int i = 0; i < operators.size(); i++)
		{
			if (operands.size() == operators.size()+1)
			{
				String op = operators.get(i);
				double x = Double.parseDouble(operands.get(i));
				double y = Double.parseDouble(operands.get(i+1));
				String res;
				if (op.equals("*"))
				{
					res = String.valueOf(x * y);
					operands.set(i, res);
					operands.remove(i+1);
					operators.remove(i);
					i--;
					return res;
				}
				else if (op.equals("/"))
				{	
					res = String.valueOf(x / y);
					operands.set(i, res);
					operands.remove(i+1);
					operators.remove(i);
					i--;
					return res;
				}
			}
		}	
		for (int i = 0; i < operators.size(); i++)
		{
			if (operands.size() == operators.size()+1)
			{
				String op = operators.get(i);
				double x = Double.parseDouble(operands.get(i));
				double y = Double.parseDouble(operands.get(i+1));
				String res;
				if (op.equals("+"))	
				{
					res = String.valueOf(x + y);
					operands.set(i, res);
					operands.remove(i+1);
					operators.remove(i);
					i--;
					return res;
				}
				else if (op.equals("-"))
				{
					res = String.valueOf(x - y);
					operands.set(i, res);
					operands.remove(i+1);
					operators.remove(i);
					i--;
					return res;
				}
			}
							
		}
		return ""+operands.get(0);
	}
	public boolean isValid( String expr )
	{	
		//String valids = "0123456789+-*/.";
		//for (int i=0;i<expr.length() ; ++i)
		//	if(!valids.contains(expr.charAt(i))) 
		//		return false;
               // does expr start or end with an operator ?
		String ops = "+-/*";
		for (int i=0;i<ops.length() ; ++i )
		{
			if (expr.charAt(0) == ops.charAt(i)) return false;
			if (expr.charAt(ops.length()-1) == ops.charAt(i)) return false;
		}
		return true;
	}
	
	}
	public static void main (String [] args)
	{
		new SimpleCalc();
	}
}