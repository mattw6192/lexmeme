import java.io.IOException;

public class RecParse {

	lexeme CurrentLexeme;
	lexer myLex;
	lexeme finalLex;
	
	public RecParse(lexeme current, lexer lex) {
		// TODO Auto-generated constructor stub
		CurrentLexeme = current;
		myLex = lex;
		if (CurrentLexeme.type == "ENDofINPUT"){
			//System.out.println("done");
			System.exit(0);
		}
		
	}

	public void recognizer() throws IOException{
		program();
	}
	
	public lexeme parser() throws Exception {
       finalLex = program();
       match("ENDofINPUT", "PARSER");
       return finalLex;
    }
	
	public boolean check(String type){
		return CurrentLexeme.type.equals(type);
	}
	
	public lexeme advance() throws IOException{
		lexeme saveLex = CurrentLexeme;
		CurrentLexeme = myLex.lex();
		return saveLex;
	}
	
	public lexeme match(String type, String funcStr) throws IOException{
		matchNoAdvance(type, funcStr);
		//System.out.println(CurrentLexeme.display() + " --- " + CurrentLexeme.type);
		//System.out.println(" here: " + type + " :lineNum: " + myLex.lineNumber + " :function: " + funcStr);
		//System.out.println(myLex.lineNumber + ": " + CurrentLexeme.display());
		return advance();
	}

	public void matchNoAdvance(String type, String funcStr){
		if  (!check(type)){
			//System.out.println(CurrentLexeme.display());
			System.out.println(" syntax error! Expected: " + type + " on line: " + myLex.lineNumber + " in function " + funcStr);
			//System.out.printf("syntax error! on line: " + myLex.lineNumber);
			System.exit(0);
		}
	}
	
	
	
	/****************************************
	 *        Pending functions
	 ****************************************/
	
	public boolean importsPending(){
		return check("IMPORT");
	}
	
	public boolean mainPending(){
		return check("FUNCTION");
	}
	
	public boolean blockPending(){
		return check("OPEN_BRACE");
	}
	
	public boolean expressionPending(){
		return unaryPending() || check("NOT") || check("OPEN_BRACE");
	}
	
	public boolean lambdaDefPending(){
		return check("LAMBDA");
	}
	
	public boolean lambdaCallPending(){
		return lambdaDefPending();
	}
	
	public boolean arrayDefPending(){
		return check("ARRAY");
	}
	public boolean getArrPending(){
		return check("GETARR");
	}
	
	public boolean setArrPending(){
		return check("SETARR");
	}
	
	public boolean unaryPending(){
		return check("INTEGER") || check("DECIMAL") || check("VARIABLE")  || check("OPEN_PAREN") || check("MINUS") || check("STRING") || classFuncCallPending();
	}
	
	public boolean optExpressionListPending(){
		return expressionPending();
	}
	
	public boolean operatorPending(){
		return check("PLUS") || check("MINUS") || check("TIMES") || check("DIVIDES") || check("MOD") || check("CARET") || check("INCREMENT") || check("DECREMENT");
	}
	
	public boolean comparatorsPending(){
		return check("EQUALS?") || check("LESSTHANEQUALS") || check("GREATERTHANEQUALS") || check("LESSTHAN") || check("GREATERTHAN");
	}
	
	public boolean optElsePending(){
		return check("ELSE");
	}
	
	public boolean ifStatementPending(){
		return check("IF");
	}
	
	public boolean statementsPending(){
		return statementPending();
	}
	
	public boolean whileLoopPending(){
		return check("WHILE");
	}
	
	public boolean forLoopPending(){
		return check("FOR");
	}
	
	public boolean classStructurePending(){
		return check("CLASS");
	}
	
	public boolean statementPending(){
		return expressionPending() || varDefPending() || arrayDefPending()  || ifStatementPending() || whileLoopPending() || forLoopPending() || printPending() || functionDefPending() || functionCallsPending() || classDefPending() || classFuncCallPending() || classBlockPending();
	}
	
	public boolean classFuncCallPending(){
		return check("VARIABLE");
	}
	
	public boolean functionDefPending(){
		///System.out.println("defpending");
		return check("FUNCTION");
	}
	
	public boolean optInitPending(){
		return check("ASSIGN");
	}
	
	public boolean optParamListPending(){
		return paramListPending();
	}
	
	public boolean paramListPending(){
		return expressionPending();
	}
	
	public boolean nonOptVarDefPending(){
		return  unaryPending() || check("VARIABLE") ;
	}
	
	public boolean varDefPending(){
		return check("VARIABLE");
	}
	
	public boolean classBlockPending(){
		return check("OPEN_BRACE");
	}
	
	public boolean functionCallPending(){
		return check("VARIABLE") || lambdaCallPending();
	}
	
	public boolean functionCallsPending(){
		return functionCallPending();
	}
	
	public boolean printListPending(){
		return expressionPending();
	}
	
	public boolean classDefPending(){
		return check("CLASS");
	}
	
	public boolean printPending(){
		return check("PRINT");
	}
	
	public boolean incrementPending(){
		return check("VARIABLE");
	}
	
	public boolean decrementPending(){
		return check("VARIABLE");
	}
	
	public boolean conditionalPending(){
		return check("AND") || check("OR");
	}
	
	/***************************************
	 * END PENDING FUNCTIONS
	 * BEGIN MAIN FUNCTIONS
	 * @throws IOException 
	 ***************************************/
	
	public lexeme program() throws IOException{
		lexeme left = imports();
		lexeme right = main();
		return new lexeme("PROGRAM", left, right);
	}
	
	public lexeme imports() throws IOException{
		if (check("IMPORT")){
			lexeme a = match("IMPORT", "IMPORTS");
			lexeme b = match("STRING", "IMPORTS");
			lexeme c = match("SEMICOLON", "IMPORTS");
			lexeme d = new lexeme("EMPTY");
			if (check("imports")){
				d = imports();
				return new lexeme("IMPORTS", a, new lexeme("GLUE", b, new lexeme("GLUE", c, d)));
			}
			return new lexeme("IMPORTS", a, new lexeme("GLUE", b, c));
		}
		else{
			return new lexeme("IMPORTS", new lexeme("EMPTY"), new lexeme("EMPTY"));
			//System.out.println("Empty");
		}
	}
	
	public lexeme main() throws IOException{
		lexeme a = match("MAIN", "MAIN");
		lexeme b = match("OPEN_PAREN", "MAIN");
		lexeme c = match("CLOSE_PAREN", "MAIN");
		lexeme d = block();
		
		return new lexeme("MAINFUNC", a, new lexeme("GLUE", b, new lexeme("GLUE", c, d)));
	}
	
	public lexeme unary() throws IOException{
		//System.out.println(" ----- " + CurrentLexeme.type);
		if (check("INTEGER")){
			return new lexeme("UNARY", match("INTEGER", "UNARY"), new lexeme("EMPTY"));
		}
		else if (check("OPEN_BRACKET")){
			//System.out.println(" --Array Begin--- " + CurrentLexeme.type);
			lexeme a = match("OPEN_BRACKET", "UNARY");
			lexeme b = optExpressionList();
			lexeme c = match("CLOSE_BRACKET", "UNARY");
			return new lexeme("UNARY", a, new lexeme("GLUE", b, c));
		}
		else if (check("DECIMAL")){
			return new lexeme("UNARY", match("DECIMAL", "UNARY"), new lexeme("EMPTY"));
		}
		else if(check("VARIABLE")){
			lexeme a = match("VARIABLE", "UNARY");
			if (check("OPEN_PAREN")){
				lexeme b = match("OPEN_PAREN", "UNARY");
				lexeme c = optExpressionList();
				lexeme d = match("CLOSE_PAREN", "UNARY");
				//System.out.println("------------------------------------------------------------------------------");
				return new lexeme("functionCall", a, new lexeme("GLUE", b, new lexeme("GLUE", c, d)));
			}
			else if (check("DOT")){
				lexeme b = match("DOT", "unary");
				return new lexeme("UNARY", a, new lexeme("GLUE", b, functionCall()));
			}
			return new lexeme("UNARY", a, new lexeme("EMPTY"));
		}
		else if (check("STRING")){
			return new lexeme("UNARY", match("STRING", "UNARY"), new lexeme("EMPTY"));
		}
		else if (check("OPEN_PAREN")){
			lexeme a = match("OPEN_PAREN", "UNARY");
			lexeme b = expression();
			return new lexeme("UNARY", a, new lexeme("GLUE", b, match("CLOSE_PAREN", "UNARY")));
		}
		else if (lambdaDefPending()){
			return new lexeme("UNARY", lambdaDef(), new lexeme("EMPTY"));
		}
		else{
			//match("MINUS", "UNARY");
			//unary();
			return new lexeme("UNARY", match("MINUS", "UNARY"), unary());
		}
	}
	
	public lexeme lambdaDef() throws IOException{
		
		lexeme a = match("LAMBDA", "LAMBDADEF");
		lexeme b = match("OPEN_PAREN", "LAMBDADEF");
		lexeme c = optParamList();
		lexeme d = match("CLOSE_PAREN", "LAMBDADEF");
		lexeme e = block();
		return new lexeme("UNARY", a , new lexeme("GLUE", b , new lexeme("GLUE", c, new lexeme("GLUE", d, e))));
	}
	
	public lexeme lambdaCall() throws IOException{
		//System.out.println("We have reached a lambda call");
		lexeme a = lambdaDef();
		lexeme b = match("OPEN_PAREN", "LAMBDACALL");
		lexeme c = optParamList();
		lexeme d = match("CLOSE_PAREN", "LAMBDACALL");
		lexeme e = match("SEMICOLON", "LAMBDACALL");
		return new lexeme("lambdaCall", a, new lexeme("GLUE", b, new lexeme("GLUE", c, new lexeme("GLUE", d, e))));
	}
	
	public lexeme expression() throws IOException{
		if (unaryPending()){
			lexeme a = unary();
			if(operatorPending()){
				lexeme b = operator();
				return new lexeme("EXPRESSION", a, new lexeme("GLUE", b, expression()));
			}
			else if (comparatorsPending()){
				lexeme b = comparators();
				return new lexeme("EXPRESSION", a, new lexeme("GLUE", b, expression()));
			}
			else if (check("ASSIGN")){
				lexeme b = match("ASSIGN", "expression");
				return new lexeme("EXPRESSION", a, new lexeme("GLUE", b, expression()));
			}
			return new lexeme("EXPRESSION", a, new lexeme("EMPTY"));
		}
		else if (check("NOT")){
			return new lexeme("EXPRESSION", match("NOT", "EXPRESSION"), expression());
		}
		else if (check("OPEN_BRACE")){
			lexeme a = match("OPEN_BRACE", "EXPRESSION");
			lexeme b = printList();
			lexeme c = match("CLOSE_BRACE", "EXPRESSION");
			return new lexeme("EXPRESSION", a, new lexeme("GLUE", b,c));
		}
		else{
			if(check("CLOSE_BRACE")){
				return new lexeme("EXPRESSION", match("CLOSE_BRACE", "expression"), new lexeme("EMPTY"));
			}
			return new lexeme("EXPRESSION", new lexeme("EMPTY"), new lexeme("EMPTY"));
		}
		
		
		
		
	}
	
	public lexeme operator() throws IOException{
		if(check("PLUS")){
			return new lexeme("OPERATOR", match("PLUS", "OPERATOR"), new lexeme("EMPTY"));
		}
		else if (check("MINUS")){
			return new lexeme("OPERATOR", match("MINUS", "OPERATOR"), new lexeme("EMPTY"));
		}
		else if (check("TIMES")){
			return new lexeme("OPERATOR", match("TIMES", "OPERATOR"), new lexeme("EMPTY"));
		}
		else if (check("DIVIDES")){
			return new lexeme("OPERATOR", match("DIVIDES", "OPERATOR"), new lexeme("EMPTY"));
		}
		else if (check("MOD")){
			return new lexeme("OPERATOR", match("MOD", "operator"), new lexeme("EMPTY"));
		}
		else if (check("CARET")){
			return new lexeme("OPERATOR", match("CARET", "operator"), new lexeme("EMPTY"));
		}
		else if (check("INCREMENT")){
			return new lexeme("OPERATOR", match("INCREMENT", "operator"), new lexeme("EMPTY"));
		}
		else{
			return new lexeme("OPERATOR", match("DECREMENT", "operator"), new lexeme("EMPTY"));
		}
	}
	
	public lexeme optExpressionList() throws IOException{
		if (expressionPending()){
			lexeme a = expression();
			lexeme b = new lexeme("EMPTY");
			if (check("COMMA")){
				lexeme c = match("COMMA", "optExpressionList");
				return new lexeme("optExpressionList", a, new lexeme("GLUE", c, optExpressionList()));
			}
			return new lexeme("optExpressionList", a, b);
		}
		else{
			return new lexeme("optExpressionList", new lexeme("EMPTY"), new lexeme("EMPTY"));
			//System.out.println("EMPTY");
		}
	}
	
	public lexeme ifStatement() throws IOException{
		lexeme a = match("IF", "ifStatement");
		lexeme b = match("OPEN_PAREN", "ifStatement");
		lexeme c = ifGuts();
		lexeme d = match("CLOSE_PAREN", "ifStatement");
		lexeme e = block();
		lexeme f = optElse();
		return new lexeme("ifStatement", a, new lexeme("GLUE", b, new lexeme("GLUE", c, new lexeme("GLUE", d, new lexeme("GLUE", e, f)))));
	}
	
	public lexeme optElse() throws IOException{
		if (check("ELSE")){
			lexeme a = match("ELSE", "optElse");
			if (blockPending()){
				return new lexeme("optElse", a, block());
			}
			else{
				return new lexeme("optElse", a, ifStatement());
			}
		}
		else{
			return new lexeme("optElse", new lexeme("EMPTY"), new lexeme("EMPTY"));
			//System.out.println("EMPTY");
		}
	}
	
	public lexeme whileLoop() throws IOException{
		lexeme a = match("WHILE", "whileLoop");
		lexeme b = match("OPEN_PAREN", "whileLoop");
		lexeme c = expression();
		lexeme d = match("CLOSE_PAREN", "whileLoop");
		lexeme e = block();
		return new lexeme("whileLoop", a, new lexeme("GLUE", b, new lexeme("GLUE", c, new lexeme("GLUE", d, e))));
		
	}
	
	public lexeme block() throws IOException{
		lexeme a = match("OPEN_BRACE", "block");
		lexeme b = statements();
		return new lexeme("block", a, new lexeme("GLUE", b, match("CLOSE_BRACE", "block")));
	}
	
	public lexeme statement() throws IOException{
		//System.out.println("Statement here --- " + CurrentLexeme.type);
		if (expressionPending()){
			lexeme a = expression();
			if (check("SEMICOLON")){
				return new lexeme("statement", a, match("SEMICOLON", "statement"));
			}
			return new lexeme("statement", a, new lexeme("EMPTY"));
			
		}
		else if (lambdaDefPending()){
			//System.out.print("lambda Def here");
			return new lexeme("statement", lambdaCall(), new lexeme("EMPTY"));
		}
		else if (ifStatementPending()){
			return new lexeme("statement", ifStatement(), new lexeme("EMPTY"));
		}
		else if (whileLoopPending()){
			return new lexeme("statement", whileLoop(), new lexeme("EMPTY"));
		}
		else if (forLoopPending()){
			return new lexeme("statement", forLoop(), new lexeme("EMPTY"));
		}
		else if (printPending()){
			return new lexeme("statement", print(), new lexeme("EMPTY"));
		}
		else if (functionDefPending()){
			return new lexeme("statement", functionDef(), new lexeme("EMPTY"));
		}
		else if (varDefPending()){
			return new lexeme("varDef", varDef(), new lexeme("EMPTY"));
		}
		else if (arrayDefPending()){
			return arrayDef();
		}
	//	else if (getArrPending()){
	//		return getArr();
	//	}
	//	else if (setArrPending()){
	//		return setArr();
	//	}
		else if (functionCallsPending()){
			return new lexeme("statement", functionCalls(), new lexeme("EMPTY"));
		}
		else if (classDefPending()){
			return new lexeme("statement", classDef(), new lexeme("EMPTY"));
		}
		else if (classFuncCallPending()){
			return new lexeme("statement", classFuncCall(), new lexeme("EMPTY"));
		}
		else{
			//System.out.println(CurrentLexeme.type);
			return new lexeme("statement", classBlock(), new lexeme("EMPTY"));
		}
	}
	
	public lexeme statements() throws IOException{
		//System.out.println("--- STATEMENTS BEGIN ___ ");
		lexeme a = statement();
		//System.out.println("--- STATEMENTS END ___ ");
		if (statementsPending()){
			return new lexeme("statements", a, statements());
		}
		return new lexeme("statements", a, new lexeme("EMPTY"));
	}
	
	public lexeme nonOptVarDef() throws IOException{
		if (unaryPending()){
			return new lexeme("nonOptVarDef", unary(), new lexeme("EMPTY"));
		}
		else{
			lexeme a = match("VARIABLE", "nonOptVarDef");
			lexeme b = match("ASSIGN", "nonOptVarDef");
			lexeme c = expression();
			lexeme d = match("SEMICOLON", "nonOptVarDef");
			return new lexeme("nonOptVarDef", a, new lexeme("GLUE", b, new lexeme("GLUE", c, d)));
		}
	}
	
	public lexeme varDef() throws IOException{
		lexeme a = match("VARIABLE", "varDef");
		lexeme b = optInit();
		return new lexeme("varDef", a, new lexeme ("GLUE", b, match("SEMICOLON", "varDef")));
	}
	
	public lexeme optInit() throws IOException{
		if (check("ASSIGN")){
			return new lexeme("optInit", match("ASSIGN", "optInit"), expression());
		}
		else{
			return new lexeme("optInit", new lexeme("EMPTY"), new lexeme("EMPTY"));
		}
	}
	
	public lexeme functionDef() throws IOException{
		lexeme a = match("FUNCTION", "functionDef");
		lexeme b = match("VARIABLE", "functionDef");
		lexeme c = match("OPEN_PAREN", "functionDef");
		lexeme d = optParamList();
		lexeme e = match("CLOSE_PAREN", "functionDef");
		lexeme f = block();
		return new lexeme("functionDef", a, new lexeme("GLUE", b, new lexeme("GLUE", c, new lexeme("GLUE", d, new lexeme("GLUE", e, f)))));
	}
	
	public lexeme paramList() throws IOException{
		lexeme a = expression();
		if(check("COMMA")){
			lexeme b = match("COMMA", "paramList");
			return new lexeme("paramList", a, new lexeme("GLUE", b, paramList()));
		}
		return new lexeme("paramList", a, new lexeme("EMPTY"));
	}
	
	public lexeme comparators() throws IOException{
		if(check("EQUALS?")){
			return new lexeme("comparators", match("EQUALS?", "comparators"), new lexeme("EMPTY"));
		}
		else if (check("LESSTHANEQUALS")){
			return new lexeme("comparators", match("LESSTHANEQUALS", "comparators"), new lexeme("EMPTY"));
		}
		else if (check("GREATERTHANEQUALS")){
			return new lexeme("comparators", match("GREATERTHANEQUALS", "comparators"), new lexeme("EMPTY"));
		}
		else if (check("LESSTHAN")){
			return new lexeme("comparators", match("LESSTHAN", "comparators"), new lexeme("EMPTY"));
		}
		else{
			return new lexeme("comparators", match("GREATERTHAN", "comparators"), new lexeme("EMPTY"));
		}
	}
	
	public lexeme optParamList() throws IOException{
		if (paramListPending()){
			return new lexeme("optParamList", paramList(), new lexeme("EMPTY"));
		}
		else{
			return new lexeme("optParamList", new lexeme("EMPTY"), new lexeme("EMPTY"));
		}
	}
	
	public lexeme forLoop() throws IOException{
		lexeme a = match("FOR", "forLoop");
		lexeme b = match("OPEN_PAREN", "forLoop");
		lexeme c = nonOptVarDef();
		lexeme d = match("SEMICOLON", "forLoop");
		lexeme e = expression();
		lexeme f = match("SEMICOLON", "forLoop");
		lexeme g = expression();
		lexeme h = match("CLOSE_PAREN", "forLoop");
		lexeme i = block();
		return new lexeme("forLoop", a, new lexeme("GLUE", b, new lexeme("GLUE", c, new lexeme("GLUE", d, new lexeme("GLUE", e, new lexeme("GLUE", f, new lexeme("GLUE", g, new lexeme("GLUE", h, i))))))));
	}
	
	public lexeme classStructure() throws IOException{
		lexeme a = match("CLASS", "classStructure");
		lexeme b = match("VARIABLE", "classStructure");
		return new lexeme("classStructure", a, new lexeme("GLUE", b, classBlock()));
	}
	
	public lexeme functionCalls() throws IOException{
		lexeme a = functionCall();
		if (functionCallsPending()){
			return new lexeme("functionCalls", a, functionCalls());
		}
		return new lexeme("functionCalls", a, new lexeme("EMPTY"));
	}
	
	public lexeme functionCall() throws IOException{
		if (check("VARIABLE")){
			lexeme a = match("VARIABLE", "functionCall");
			lexeme b = match("OPEN_PAREN", "functionCall");
			lexeme c = optParamList();
			lexeme d = match("CLOSE_PAREN", "functionCall");
			return new lexeme("functionCall", a, new lexeme("GLUE", b, new lexeme("GLUE", c, new lexeme("GLUE", d, match("SEMICOLON", "functionCall")))));
		}else{
			return lambdaCall();
		}
	}
	
	public lexeme classBlock() throws IOException{
		lexeme a = match("OPEN_BRACE", "classBlock");
		if (check("CLOSE_BRACE")){
			return new lexeme("classBlock", a, match("CLOSE_BRACE", "classBlock"));
		}else{
			lexeme b = statements();
			return new lexeme("classBlock", a, new lexeme("GLUE", b, match("CLOSE_BRACE", "classBlock")));
		}
		
		
	}
	
	public lexeme classDef() throws IOException{
		lexeme a = match("CLASS", "classDef");
		lexeme b = match("VARIABLE", "classDef");
		if (check("OF")){
			lexeme c = match("OF", "classDef");
			lexeme d = match("VARIABLE", "classDef");
			if (check("SEMICOLON")){
				lexeme e = match("SEMICOLON", "classDef");
				return new lexeme("classDef", a, new lexeme("GLUE", b, new lexeme("GLUE", c, new lexeme("GLUE", d, e))));
			}else{
				lexeme e = classBlock();
				return new lexeme("classDef", a, new lexeme("GLUE", b, new lexeme("GLUE", c, new lexeme("GLUE", d, e))));
			}
			
		}
		else{
			if (check("SEMICOLON")){
				return new lexeme("classDef", a, new lexeme("GLUE", b, match("SEMICOLON", "classDef")));
			}else{
				return new lexeme("classDef", a, new lexeme("GLUE", b, classBlock()));
			}
		}
		
		
	}
	
	public lexeme classFuncCall() throws IOException{
		lexeme a = match("VARIABLE", "classFunctionCall");
		lexeme b = match("DOT", "classFunctionCall");
		return new lexeme("classFuncCall", a, new lexeme("GLUE", b, functionCall()));
		
	}
	
	public lexeme print() throws IOException{
		lexeme a = match("PRINT", "print");
		lexeme b = match("OPEN_PAREN", "print");
		lexeme c = printList();
		lexeme d = match("CLOSE_PAREN", "print");
		lexeme e = match("SEMICOLON", "print");
		return new lexeme("print", a, new lexeme("GLUE", b, new lexeme("GLUE", c, new lexeme("GLUE", d, e))));
	}
	
	public lexeme printList() throws IOException{
		if (expressionPending()){
			lexeme a = expression();
			if (check("COMMA")){
				lexeme b = match("COMMA", "printList");
				return new lexeme("printList", a, new lexeme("GLUE", b, printList()));
			}
			return new lexeme("printList", a, new lexeme("EMPTY"));
		}
		else{
			return new lexeme("printList", new lexeme("EMPTY"), new lexeme("EMPTY"));
		}
	}
	
	public lexeme increment() throws IOException{
		lexeme a = match("VARIABLE", "increment");
		lexeme b = match("PLUS", "increment");
		lexeme c = match("PLUS", "increment");
		if (check("SEMICOLON")){
			lexeme d = match("SEMICOLON", "increment");
			return new lexeme("increment", a, new lexeme("GLUE", b, new lexeme("GLUE", c, d)));
		}
		return new lexeme("increment", a, new lexeme("GLUE", b, c));
	}
	
	public lexeme decrement() throws IOException{
		lexeme a = match("VARIABLE", "decrement");
		lexeme b = match("MINUS", "decrement");
		lexeme c = match("MINUS", "decrement");
		if (check("SEMICOLON")){
			lexeme d = match("SEMICOLON", "decrement");
			return new lexeme("decrement", a, new lexeme("GLUE", b, new lexeme("GLUE", c, d)));
		}
		return new lexeme("decrement", a, new lexeme("GLUE", b, c));
	}
	
	public lexeme conditional() throws IOException{
		if (check("AND")){
			return new lexeme("conditional", match("AND", "conditional"), new lexeme("EMPTY"));
		}
		else{
			return new lexeme("conditional", match("OR", "conditional"), new lexeme("EMPTY"));
		}
	}
	
	public lexeme ifGuts() throws IOException{
		if (expressionPending()){
			lexeme a = expression();
			if(conditionalPending()){
				lexeme b = conditional();
				return new lexeme("ifGuts", a, new lexeme("GLUE", b, ifGuts()));
			}
			return new lexeme("ifGuts", a, new lexeme("EMPTY"));
		}
		else if (check("true")){
			return new lexeme("ifGuts", match("true", "ifGuts"), new lexeme("EMPTY"));
		}
		else if (check("false")){
			return new lexeme("ifGuts", match("false", "ifGuts"), new lexeme("EMPTY"));
		}
		else{
			lexeme a = match("NOT", "ifGuts");
			lexeme b = match("OPEN_PAREN", "ifGuts");
			lexeme c = ifGuts();
			lexeme d = match("CLOSE_PAREN", "ifGuts");
			return new lexeme("ifGuts", a, new lexeme("GLUE", b, new lexeme("GLUE", c, d)));
		}
	}
	
/*	public lexeme getArr() throws IOException{
		lexeme a = match("GETARR", "GETARR");
		lexeme b = match("OPEN_PAREN", "GETARR");
		lexeme c = match("VARIABLE", "GETARR");
		lexeme d = match("COMMA", "GETARR");
		lexeme e = expression();
		lexeme f = match("CLOSE_PAREN", "GETARR");
		return new lexeme("getArr", a, new lexeme("GLUE", b, new lexeme("GLUE", c, new lexeme("GLUE", d, new lexeme("GLUE", e, f)))));
	}
	
	public lexeme setArr() throws IOException{
		lexeme a = match("SETARR", "SETARR");
		lexeme b = match("OPEN_PAREN", "SETARR");
		lexeme c = match("VARIABLE", "SETARR");
		lexeme d = match("COMMA", "SETARR");
		lexeme e = expression();
		lexeme f = match("COMMA", "SETARR");
		lexeme g = expression();
		lexeme h = match("CLOSE_PAREN", "SETARR");
		return new lexeme("setArr", a, new lexeme("GLUE", b, new lexeme("GLUE", c, new lexeme("GLUE", d, new lexeme("GLUE", e, new lexeme("GLUE", f, new lexeme("GLUE", g, h)))))));
	}
	*/
	public lexeme arrayDef() throws IOException{
		//System.out.println(" Creating Array Def");
		lexeme a = match("ARRAY", "ARRAYDEF");
		lexeme b = match("VARIABLE", "ARRAYDEF");
		//System.out.println(CurrentLexeme.type);
		if (check("OPEN_BRACE")){
			lexeme c = match("OPEN_BRACE", "ARRAYDEF");
			lexeme d = printList();
			//System.out.println(CurrentLexeme.type);
			lexeme e = match("CLOSE_BRACE", "ARRAYDEF");
			return new lexeme("arrayDef", a, new lexeme("GLUE", b, new lexeme("GLUE", c, new lexeme("GLUE", d, e))));
		}
		else{
			lexeme c = match("OPEN_BRACKET", "ARRAYDEF");
			lexeme d = expression();
			lexeme e = match("CLOSE_BRACKET", "ARRAYDEF");
			return new lexeme("arrayDef", a, new lexeme("GLUE", b, new lexeme("GLUE", c, new lexeme("GLUE", d, e))));
		}
	}
	
}
