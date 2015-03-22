import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

class TernaryOperatorEval {

    private int lookaheadToken;

    private InputStream in;

    public TernaryOperatorEval(InputStream in) throws IOException 
    {
		this.in = in;
		lookaheadToken = in.read();
    }

    private void consume(int symbol) throws IOException, ParseError 
    {
    	//System.out.print("Consume " + (char)lookaheadToken);
    	//System.out.println();
		if (lookaheadToken != symbol)
		    throw new ParseError();
		lookaheadToken = in.read();
		if(lookaheadToken == ' ')
		{
			consume(' ');
		}
    }
    


    private String Expr(String str) throws IOException, ParseError 
    {
    	//System.out.println("Expr");
    	//System.out.println(((char)lookaheadToken));
    	String str1 = "";
    	String str2 = "";
    	
    	str1 += Term(str1);  	
    	str2 += Expr2(str2);
    	
    	//System.out.println("EXPR = " + str1);
    	//System.out.println("EXPR = " + str2);
    	
    	
    	if (str2.length() >= 1)
		{
			char a = str2.charAt(0);
			if(a == '+' || a == '-' || a == '*' || a == '/')
			{
				str2 = str2.substring(1);
			}
		
			String s = "( " + a +" " + str1 + str2 + " )";
			return s;
		}
    	
    	
    	return (str1 + str2) ;
    }
    
    private String Term(String str) throws IOException, ParseError 
    {
    	//System.out.println("Term");
    	//System.out.println(((char)lookaheadToken));
    	
    	String str1 = "";
    	String str2 = "";
    	
    	str1 += Factor(str1);
    	//System.out.println("After factor sto Term = " + str1);
    	str2 += Term2(str2);
    	//System.out.println("After Term2 sto Term = " + str2);
    	//e += t;
    	
    	if (str2.length() >= 1)
		{
			char a = str2.charAt(0);
			if(a == '*' || a == '/')
			{
				str2 = str2.substring(1);
			}
		
			String s = " ( " + a +" " + str1 + " "+ str2 + " )";
			return s;
		}
    	
    	return (str1 + str2) ;
    }
    
    private String Expr2(String str) throws IOException, ParseError 
    {
    	//System.out.println("Expr2");
    	//System.out.println(((char)lookaheadToken));
    	if(lookaheadToken != '+' && lookaheadToken != '-')
		{
    		//throw new ParseError();
    		return "";
		}
		str += ((char)lookaheadToken);
		//char ch = (char)lookaheadToken;
		consume(lookaheadToken);
		
		String str1 = "";
    	String str2 = "";
		
		str1 += Term(str1);
		//System.out.println("After Term sto Expr2 = " + str1);
		str2 +=Expr2(str2);
		//System.out.println("After Expr2 sto Expr2 = " + str2);
		
		if (str2.length() >= 1)
		{
			char a = str2.charAt(0);
		
			//System.out.println("AAAAA = " + a);
			if(a == '+' || a == '-')
			{
				str2 = str2.substring(1);
			}
		
			String s = str + " ( " + a +" " + str1 + " " + str2 + " )";
			return s;
		}
		return (str + str1 + str2);
    }
    
    private String Factor(String str) throws IOException, ParseError 
    {
    	//System.out.println("Factor");
    	//System.out.println(((char)lookaheadToken));
    	if(lookaheadToken == '\n' || lookaheadToken == -1)
		{
			System.out.println("epistrefo logo " + lookaheadToken);
		    return "";
		}
    	
    	String str1 = "";
    	if(lookaheadToken == '(')
    	{
    		//str1 += ((char)lookaheadToken);
    		consume(lookaheadToken);
    		str1 += Expr(str1);
    		if(lookaheadToken == ')')
    		{
    			//str += ((char)lookaheadToken);
    			consume(lookaheadToken);
    			return str1;
    		}
    		else
    		{
    			System.out.println("Syntax error: expecting )");
    			return "";
    		}
    	}
    	
    	if(lookaheadToken < '0' || lookaheadToken > '9')
		    throw new ParseError();
		//int cond = evalDigit(lookaheadToken);
		//System.out.println("Cond = " + cond);
		str += ((char)lookaheadToken);
		consume(lookaheadToken);
		return str;
    }
    
    private String Term2(String str) throws IOException, ParseError 
    {
    	//System.out.println("Term2");
    	//System.out.println(((char)lookaheadToken));
    	if(lookaheadToken != '*' && lookaheadToken != '/')
		{
    		//throw new ParseError();
    		return "";
		}
		str += ((char)lookaheadToken);
		consume(lookaheadToken);
		
		String str1 = "";
    	String str2 = "";
		
		str1 += Factor(str1);
		str2 += Term2(str2);
		
		if (str2.length() >= 1)
		{
			char a = str2.charAt(0);
		
			//System.out.println("AAAAA = " + a);
			if(a == '*' || a == '/')
			{
				str2 = str2.substring(1);
			}
		
			String s = str + " ( " + a +" " + str1 + " " + str2 + " )";
			return s;
		}
		
		return (str + str1 + str2);
    }


    public String eval() throws IOException, ParseError 
    {
    	String str = "";
		String rv = Expr(str);
		if (lookaheadToken != '\n' && lookaheadToken != -1)
		    throw new ParseError();
		return rv;
    }

    public static void main(String[] args) throws FileNotFoundException 
    {
    	System.out.println(args[0]);
    	String s = new String(args[0]);
    	InputStream in = new FileInputStream(s);
    	try 
    	{
		    TernaryOperatorEval evaluate = new TernaryOperatorEval(in);
		    System.out.println(evaluate.eval());
    	}
    	catch (IOException e) 
    	{
    		System.err.println(e.getMessage());
    	}
    	catch(ParseError err)
    	{
    		System.err.println(err.getMessage());
    	}
    }
}

