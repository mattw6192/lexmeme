import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;

public class lexer {

	
	//BufferedReader Input;
	char ch;
	int charInt;
	int lineNumber;
	PushbackReader Input;
	
	public lexer(String filename) throws FileNotFoundException{
		//System.out.println(filename);
		lineNumber = 1;
		Input = new PushbackReader(new BufferedReader(new FileReader(filename)), 2);
	}

	
	public void skipWhiteSpace() throws IOException{
		while (ch == '\n' || ch == ' ' || ch == '\t'){
			if (ch == '\n'){
				lineNumber += 1;
			}
			charInt = Input.read();
			ch = (char) charInt;
			
			if (ch == '#'){
				lineNumber += 1;
				while ((char) charInt != '\n'){
					charInt = Input.read();
				}
				charInt = Input.read();
				ch = (char) charInt;
				skipWhiteSpace();
			}
			
		}
		
	}
	
	public lexeme lex() throws IOException{
		charInt = Input.read();
		ch = (char) charInt;
		skipWhiteSpace();
		//ch =  firstChar;
		
		if (ch == -1) {
			Input.close();
			return new lexeme("ENDofINPUT");
		}
		
		switch(ch){
		 // single character tokens
		case '(': return new lexeme("OPEN_PAREN");
		case ')': return new lexeme("CLOSE_PAREN");
		case ',': return new lexeme("COMMA");
		case '+': 
			ch = (char) Input.read();
			if (ch == '+'){
				return new lexeme("INCREMENT");
			}else{
				Input.unread(ch);
				return new lexeme("PLUS");
			}
			//return new lexeme("PLUS");
		case '*': return new lexeme("TIMES");
		case '-': 
			ch = (char) Input.read();
			if (ch == '-'){
				return new lexeme("DECREMENT");
			}else{
				Input.unread(ch);
				return new lexeme("MINUS");
			}
			//return new lexeme("MINUS");
		case '/': return new lexeme("DIVIDES");
		case '<': 
			ch = (char) Input.read();
			if (ch == '='){
				return new lexeme("LESSTHANEQUALS");
			}else{
				Input.unread(ch);
				return new lexeme("LESSTHAN");
			}
			
			//return new lexeme("LESSTHAN");
		case '>': 
			ch = (char) Input.read();
			if (ch == '='){
				return new lexeme("GREATERTHANEQUALS");
			}else{
				Input.unread(ch);
				return new lexeme("GREATERTHAN");
			}
		case '=': return new lexeme("ASSIGN");
		case ';': return new lexeme("SEMICOLON");
		case '{': return new lexeme("OPEN_BRACE");
		case '}': return new lexeme("CLOSE_BRACE");
		case '^': return new lexeme("CARET");
		case '%': return new lexeme("MOD");
		case '.': return new lexeme("DOT");
		case '[': return new lexeme("OPEN_BRACKET");
		case ']': return new lexeme("CLOSE_BRACKET");
		default:
			if (Character.isDigit(ch)){	
				return lexNumber();
			}
			else if(Character.isLetter(ch)){
				return lexVariableOrKeyword();
			}
			else if (ch == '\"'){
				return lexString();
			}
			else {
				return new lexeme("ENDofINPUT", ch);
			}
	}

	}


	public lexeme lexVariableOrKeyword() throws IOException{
		String chString = "" + ch;
		boolean hasPlus = false;
		boolean hasMinus = false;
		ch = (char) Input.read();

		
		while (Character.isLetter(ch) || Character.isDigit(ch)  || ch == '_' || ch == '!' || ch == '?'){
			if (ch == '+'){
				if (hasPlus == true){
					//create lex for chString (variable)
					// create lex for ++ (increment)
					hasPlus = false;
					Input.unread(ch);
					Input.unread(ch);
					//System.out.println("------------------------------------------------- " + chString);
					return new lexeme("VARIABLE", chString);
				}
				else{
					hasPlus = true;
				}
			}
			else{
				hasPlus = false;
			}
			
			if (ch == '-'){
				if (hasMinus == true){
					//create lex for chString (variable)
					// create lex for ++ (increment)
					hasMinus = false;
					Input.unread(ch);
					Input.unread(ch);
					//System.out.println("------------------------------------------------- " + chString);
					return new lexeme("VARIABLE", chString);
				}
				else{
					hasMinus = true;
				}
			}
			else{
				hasMinus = false;
			}
			
			chString = chString + ch;
			ch = (char) Input.read();
		}
		
		Input.unread(ch);
		
		// token holds either a variable or a keyword, so figure it out
		//System.out.println("CHECKING FOR KEYWORDS " +  token ); 
		if (chString.equals("if")){
			return new lexeme("IF");
		}
		else if (chString.equals("else")){
			return new lexeme("ELSE");
		}
		else if (chString.equals("lambda")){
			return new lexeme("LAMBDA");
		}
		else if (chString.equals("array")){
			return new lexeme("ARRAY");
		}
		else if (chString.equalsIgnoreCase("getArr")){
			return new lexeme("GETARR");
		}
		else if (chString.equals("setArr")){
			return new lexeme("SETARR");
		}
		else if (chString.equals("while")){
			return new lexeme("WHILE");
		}
		else if (chString.equals("import")){
			//System.out.println("made an import");
			return new lexeme("IMPORT");
		}
		else if (chString.equals("function")){
			return new lexeme("FUNCTION");
		}
		else if (chString.equals("main") || chString.equals("Main") || chString.equals("MAIN")){
			return new lexeme("MAIN");
		}
		else if (chString.equals("for")){
			return new lexeme("FOR");
		}
		else if (chString.equals("class")){
			return new lexeme("CLASS");
		}
		else if (chString.equals("OF") || chString.equals("of")){
			return new lexeme("OF");
		}
		else if (chString.equals("print")){
			return new lexeme("PRINT");
		}
		else if (chString.equals("true")){
			return new lexeme("true");
		}
		else if (chString.equals("false")){
			return new lexeme("false");
		}
		else if (chString.equals("equals?")){
			return new lexeme("EQUALS?");
		}
		else if (chString.equals("not")){
			return new lexeme("NOT");
		}
		else if (chString.equals("and")){
			return new lexeme("AND");
		}
		else if (chString.equals("or")){
			return new lexeme("OR");
		}
		// ... more keyword testing here
		else { // must be a variable!
			return new lexeme("VARIABLE", chString);
		}
		
	}
	
	private lexeme lexString() throws IOException {
		String chString = "" + ch;
		ch = (char) Input.read();
		
		while (ch != '\"'){
			if (ch == '\\'){
				ch = (char) Input.read();
			}
			chString = chString + ch;
			ch = (char) Input.read();
		}
		
		chString = chString + ch;
		return new lexeme("STRING", chString);
		
	}



	private lexeme lexNumber() throws IOException {
		boolean isDecimal = false;
		String chString = "" + ch;
		ch = (char) Input.read();
		
		while (Character.isDigit(ch) || ch == '.'){
			if (ch == '.'){
				isDecimal = true;
			}
			chString = chString + ch;
			ch = (char) Input.read();
		}
		
		Input.unread(ch);
		if (isDecimal == true){
			return new lexeme("DECIMAL", Double.parseDouble(chString));
		}
		else{
			return new lexeme("INTEGER", Integer.parseInt(chString));
		}
	}
}
