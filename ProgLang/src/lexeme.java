import java.util.ArrayList;

public class lexeme {
	//String token;
	String type = "EMPTY";
	lexeme left;
	lexeme right;
	
	String stringToken = "EMPTY";
	int integer;
	double real;
	char charToken;
	boolean boolToken;
	ArrayList<lexeme> arrayToken;
	lexeme[] array;
	
	public String display() {
		String finalx = "";
		
		if (type.equals("INTEGER")){
			return finalx + integer;
		}
		else if (type.equals("ARRAY")){
			//System.out.println("displaying an Array");
			ArrayList<String> newArr = new ArrayList<String>();
			
	    	   for ( int i = 0; i < arrayToken.size(); i++){
	    		   if (arrayToken.get(i) != null){
	    			   newArr.add(arrayToken.get(i).display());
	    		   }
	    	   }
			
			return finalx + newArr.toString();
		}
		else if (type.equals("UNARY")){
			return left.display();
		}
		else if (type.equalsIgnoreCase("DECIMAL")){
			return finalx + real;
		}
		else if (type.equalsIgnoreCase("true") || type.equalsIgnoreCase("false")){
			return finalx + boolToken;
		}
		else if (type.equalsIgnoreCase("STRING")){
			return finalx + stringToken;
		}
		else if (type.equalsIgnoreCase("VARIABLE") ){
			return finalx + stringToken;
		}
		else if (type.equalsIgnoreCase("UNKNOWN")){
			//System.out.println("LOSERS");
			//type = "unknown";
			return "unknown";
		}
		else{
			return type;
		}
		
	}
	public String printDisplay(){
String finalx = "";
		
		if (type.equals("INTEGER")){
			return finalx + integer;
		}
		else if (type.equals("ARRAY")){
			ArrayList<String> newArr = new ArrayList<String>();
			
	    	   for ( int i = 0; i < arrayToken.size(); i++){
	    		   if (arrayToken.get(i) != null){
	    			   newArr.add(arrayToken.get(i).display());
	    		   }
	    	   }
			
			return finalx + newArr.toString();
		}
		else if (type.equals("UNARY")){
			return left.display();
		}
		else if (type.equalsIgnoreCase("DECIMAL")){
			return finalx + real;
		}
		else if (type.equalsIgnoreCase("true") || type.equalsIgnoreCase("false")){
			return finalx + boolToken;
		}
		else if (type.equalsIgnoreCase("STRING")){
			return stringToken.substring(1, stringToken.length() - 1);
		}
		else if (type.equalsIgnoreCase("VARIABLE") ){
			return finalx + stringToken;
		}
		else if (type.equalsIgnoreCase("UNKNOWN")){
			//System.out.println("LOSERS");
			//type = "unknown";
			return "unknown";
		}
		else{
			return type;
		}
		
	}
	
	public lexeme(){}
	
	public lexeme(String name){
		type = name;
	}
	
	public lexeme(String name, lexeme x, lexeme y){
		type = name;
		left = x;
		right = y;
	}
	
	public lexeme(String name, lexeme[] arr){
		type = name;
		array = arr;
	}
	
	public lexeme(String name, String newToken){
		type = name;
		stringToken = newToken;
	}
	
	public lexeme(String name, int newToken){
		type = name;
		integer = newToken;
	}
	
	public lexeme(String name, ArrayList<lexeme> array){
		type = name;
		arrayToken = array;
	}
	
	public lexeme(String name, double x){
		type = name;
		real = x;
	}
	
	public lexeme(String name, char x){
		type = name;
		charToken = x;
	}
	
	public lexeme(String name, boolean x){
		type = name;
		boolToken = x;
	}

}
