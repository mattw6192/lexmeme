import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//BufferedReader reader = new BufferedReader(new FileReader("/Users/Matt/scam/CS403/Language/MyLanguageCodeExample"));
		//System.out.println(reader.readLine());
		scanner(args[0]);
		
	}
	
	public static void scanner(String file) throws Exception{
		lexer myLex = new lexer(file);
		lexeme token = myLex.lex();
		
		// Recognizer and Parser, with Pretty Print
		RecParse myRex = new RecParse(token, myLex);
		lexeme parseTree = myRex.parser();
		PrettyPrint printer = new PrettyPrint(parseTree);
		System.out.println("------------------- Evaluating ----------------");
		Evaluator evalu8r = new Evaluator();
		lexeme b = evalu8r.runEval(parseTree);
		
		//lexeme a = new lexeme("INTEGER", 5);
		//lexeme b = new lexeme("INTEGER", 5); 
		
		//Environment TOPenv = new Environment();
		//lexeme env = TOPenv.create();
		//TOPenv.insert(a, b, env);
		//lexeme c = TOPenv.lookup(b, env);
		//System.out.println(c.display());
		
		
		
		//System.out.println(b.right.left.right.left.right.type);
		//System.out.println();
		//recEval(b);
		
		
		//System.out.println(myRex.parser().display());
	
		
		// SCANNER HERE
		//while (token.type != "ENDofINPUT" && token.display() != "unknown"){
		//	System.out.println(myLex.lineNumber + ": " + token.display() + " " + myLex.charInt);
		//	token = myLex.lex();
		//}
		
		
		
		
		//PARSER STUFF HERE
		
		//public Lexeme parse(String f) throws Exception {
	     //   l = new Lexer(f);
	      //  currentLexeme = l.lex();
	       // output = program();
	        //match("END_OF_INPUT");
	        //return output;
	    //}
		
	}

	public static void recEval(lexeme tree){
		if(tree != null){
			if (tree.type == "VARIABLE" || tree.type == "INTEGER" || tree.type == "STRING"){
				System.out.println(tree.type + " is " + tree.display());
			}
			recEval(tree.left);
			recEval(tree.right);
		}
	}
	

}
