
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Evaluator {

    private lexeme global;
    private Environment e;

    public Evaluator() {
        e = new Environment();
        global = e.create();
    }


    private lexeme eval(lexeme tree, lexeme env) throws Exception {
        if (tree == null) {
            return null;
        }
        //System.out.println("tree type " + tree.type);
        switch (tree.type) {
        
            //self-evaluating
            case "STRING": return tree;
            case "true": return tree;
            case "false": return tree;
            case "SEMICOLON": return tree;
            //case "ID": return e.lookup(tree, env);
            case "VARIABLE": return e.lookup(tree, env);
            case "INTEGER": return tree;
            case "DECIMAL": return tree;
            case "LAMBDA": return tree; // not sure i should do this
            case "EMPTY": return tree;
            case "GLUE": return tree;
            case "OF": return tree;
            case "COMMA": return tree;

            //case "NEGATIVE": return evalNegative(tree, env);
            case "NOT": return evalNot(tree, env);

            //operators
            case "EQUALS?": return evalSimpleOp(tree, env);
            //case "NOTEQUALTO": return evalSimpleOp(tree, env);
            case "GREATERTHANEQUALS": return evalSimpleOp(tree, env);
            case "LESSTHANEQUALS": return evalSimpleOp(tree, env);
            case "GREATERTHAN": return evalSimpleOp(tree, env);
            case "LESSTHAN": return evalSimpleOp(tree, env);
            case "PLUS": return evalSimpleOp(tree, env);
            case "MINUS": return evalSimpleOp(tree, env);
            case "TIMES": return evalSimpleOp(tree, env);
            case "DIVIDES": return evalSimpleOp(tree, env);
            case "MOD": return evalSimpleOp(tree, env);
            case "CARET": return evalSimpleOp(tree, env);
            case "ASSIGN": return tree;
            case "INCREMENT": return evalIncrement(tree, env);
            case "DECREMENT": return evalDecrement(tree, env);
            
            case "IMPORT": return tree;
            case "MAIN": return tree;
            case "OPEN_PAREN": return tree;
            case "CLOSE_PAREN": return tree;
            case "OPEN_BRACE": return tree;
            case "CLOSE_BRACE": return tree;
            case "EXPRESSION": return evalExpression(tree, env);
            case "classDef": return evalClassDef(tree, env); 

            


            //operators - short circuit
            case "AND": return evalShortCircuitOp(tree, env);
            case "OR": return evalShortCircuitOp(tree, env);

            //builtin functions
            case "print": return evalPrint(tree, env);
            //case "printList": return evalPrintList(tree, env);

            //variables and function definitions
            case "PROGRAM": return evalProgram(tree, env);
            case "IMPORTS": return evalImports(tree, env);
            case "MAINFUNC": return evalMain(tree, env);
            //case "OPERATOR": return evalOperator(tree, env);
            case "conditional": return evalConditional(tree, env);
            case "ifGuts": return evalIfGuts(tree, env);
            case "optExpressionList": return evalOptExpressionList(tree, env);
            case "UNARY": return evalUnary(tree, env);
            //case "print": return evalPrint(tree, env);
            case "printList": return evalPrintList(tree, env);
            case "nonOptVarDef": return evalNonOptVarDef(tree, env);
            case "optInit": return evalOptInit(tree, env);
            case "comparators": return evalComparators(tree, env);
            case "optParamList": return evalOptParamList(tree, env);
            case "classStructure": return evalClassStructure(tree, env);
            case "classBlock": return evalClassBlock(tree, env);
            case "classFunctionCall": return evalClassFunctionCall(tree, env);
            case "DEFINITION": return evalDefinition(tree, env);
            case "IDINIT": return evalIDInit(tree, env);
            case "VARDEF": return evalVarDef(tree, env);
            case "functionDef": return evalFunctionDef(tree, env);
            case "functionCall": return evalFunctionCall(tree, env);
            case "functionCalls": return evalFunctionCalls(tree, env);
            case "paramList": return evalArgList(tree, env);
            //case "ARRAYDEF": return evalArrayDef(tree, env);
            //case "ARRACCESS": return evalArrAccess(tree, env);
            //case "PARENEXPR": return evalParenExpr(tree, env);
            case "LAMBDAACC": return evalLambdaCall(tree, env);
            case "block": return evalBlock(tree, env);
            case "DOT": return tree;//evalDot(tree, env);

            //statements
            case "statements": return evalStatements(tree, env);
            case "statement": return evalStatement(tree, env);
            case "ifStatement": return evalIfStatement(tree, env);
            case "optElse": return evalOptElse(tree, env);
            case "whileLoop": return evalWhileLoop(tree, env);
            case "forLoop": return evalForLoop(tree, env);
            case "lambdaCall": return evalFunctionCall(tree,env);
            case "arrayDef": return evalArrayDef(tree, env);
//            case "getArr": 

            	//return evalGetArr(tree, env);
        //    case "setArr": return evalSetArr(tree, env);
            

            default:
                System.err.printf("\nFatal error in Evaluator.java: bad expression %s\n", tree.type);
                System.exit(1);
                return null;
        }
    }


    private lexeme evalProgram(lexeme t, lexeme env) throws Exception {
//        lexeme result = new lexeme("EVALEDPROGRAM");
        while (t != null) {
            eval(t.left, env);
            t = t.right;
        }
        return null;
    }
    
    private lexeme evalImports(lexeme t, lexeme env) throws Exception{
    	//System.out.println("right " + t.right.type);
    	if (t.right != null && t.right.type != "EMPTY"){
    		if (t.right.right.type.equals("SEMICOLON")){
    			lexer myLex = new lexer(t.right.left.stringToken);
    			lexeme token = myLex.lex();
    			
    			// Recognizer and Parser, with Pretty Print
    			RecParse myRex = new RecParse(token, myLex);
    			lexeme parseTree = myRex.parser();
    			PrettyPrint printer = new PrettyPrint(parseTree);
    			System.out.println("------------------- Evaluating ----------------");
    			Evaluator evalu8r = new Evaluator();
    			lexeme b = evalu8r.runEval(parseTree);
    			return eval(parseTree, env);
    		}else{
    			
    		}
    	}else{
    		return null;
    	}
    	//while (t != null){
    	//	eval(t.left, env);
    	//	t = t.right;
    	//}
    	return null;
    }
    
    private lexeme evalExpression(lexeme t, lexeme env) throws Exception{
    	//System.out.println("INSIDE evalExpression " + t.left.display());
    	if (t.left.type == "OPEN_BRACE" ){
    		//System.out.println("array expression here");
    		return null;
    	}else if (t.right != null){
	    	if (t.right.left != null){	
	    		String st = t.right.left.type;
	    		if (st == "ASSIGN"){	
	    			//t.right.left.type == "ASSIGN"
		    		lexeme val = eval(t.right.right, env);
		    		//System.out.println("value lookup: " + eval(t.right.right, env).display() );
		    		e.update(t.left.left, val, env);
		    		//System.out.println(" looked up fo realz " + e.lookup(t.left, env).display());
		    		//if (val != null){
		    		///	System.out.println("value lookup: " + val + " the left is : " + val.display());
		    		//}
		    		return val;
	    		}else if (st == "OPERATOR"){
	    			//System.out.println(" this is my operation " + t.right.left.left.type);
	    			lexeme val = eval(new lexeme(t.right.left.left.type, t.left, t.right.right), env);
	    			//System.out.println("END operation " + val.display());
	    			return val;
	    			// return new lexeme, it will propagate up
	    		}else if (st == "comparators"){
	    			lexeme left = eval(t.left, env);
	    			lexeme right = eval(t.right.right, env);
	    			//System.out.println("left " + left.integer + " right " + right.integer + " type " + t.right.left.left.type);
	    			switch(t.right.left.left.type){
		    			case "LESSTHAN":
		    				if (left.integer < right.integer){
		    					return new lexeme("true", true);
		    				}else{
		    					return new lexeme("false", false);
		    				}
					case "GREATERTHAN":
		    				if (left.integer > right.integer){
		    					return new lexeme("true", true);
		    				}else{
		    					return new lexeme("false", false);
		    				}
					case "GREATERTHANEQUALS":
		    				if (left.integer >= right.integer){
		    					return new lexeme("true", true);
		    				}else{
		    					return new lexeme("false", false);
		    				}
					case "LESSTHANEQUALS":
		    				if (left.integer <= right.integer){
		    					return new lexeme("true", true);
		    				}else{
		    					return new lexeme("false", false);
		    				}
					case "EQUALS?":
		    				if (left.integer == right.integer){
		    					return new lexeme("true", true);
		    				}else{
		    					return new lexeme("false", false);
		    				}
		    		default: return new lexeme("false", false);
		    			}
	    		}
	    		else{
	    			if (t.right == null || t.right.type == "EMPTY"){
	    				lexeme val =  eval(t.left.left, env);
	    				//System.out.println("eval left in expression " + val.display());
	    				return val;
	    			}
	    			else{
	    				while (t != null){
	    		    		eval(t.left, env);
	    		    		t = t.right;
	    		    	}
	    			}
	    		}
	    	}else{
	    		if (t.right == null || t.right.type == "EMPTY"){
    				return eval(t.left, env);
    			}
    			else{
    				while (t != null){
    		    		eval(t.left, env);
    		    		t = t.right;
    		    	}
    			}
	    	}
    	}else{
    		if (t.right == null || t.right.type == "EMPTY"){
				return eval(t.left, env);
			}
			else{
				while (t != null){
		    		eval(t.left, env);
		    		t = t.right;
		    	}
			}
    	}
    	return null;
    }
    
    private lexeme evalIncrement(lexeme t, lexeme env){
    	int val = e.lookup(t.left.left, env).integer + 1;
    	e.update(t.left.left, new lexeme("INTEGER", val), env);
    	return null;
    }
    
    private lexeme evalDecrement(lexeme t, lexeme env){
    	//System.out.println("t.left " + t.left.type + " BEFORE: " + e.lookup(t.left.left, env).integer);
    	int val = e.lookup(t.left.left, env).integer - 1;
    	//System.out.println("After: " + val);
    	e.update(t.left.left, new lexeme("INTEGER", val), env);
    	return null;
    }
    
    private lexeme evalClassDef(lexeme t, lexeme env) throws Exception{
    	while (t != null){
    		eval(t.right, env);
    		t = t.right;
    	}
    	return null;
    }
    
    private lexeme evalMain(lexeme t, lexeme env) throws Exception{
    	while (t != null){
    		eval(t.left, env);
    		t = t.right;
    	}
    	return null;
    }
    
    private lexeme evalOperator(lexeme t, lexeme env) throws Exception{
    	while (t != null){
    		eval(t.left, env);
    		t = t.right;
    	}
    	return null;
    }
    
    private lexeme evalConditional(lexeme t, lexeme env) throws Exception{
    	while (t != null){
    		eval(t.left, env);
    		t = t.right;
    	}
    	return null;
    }
    
    private lexeme evalIfGuts(lexeme t, lexeme env) throws Exception{
    	if (t.right == null || t.right.type == "EMPTY"){
    		return eval(t.left, env);
    	}
    	else if (t.left.type == "NOT"){
    		lexeme val = eval(t.right.right.left, env);
    		if (val.boolToken == true){
    			return new lexeme("false", false);
    		}else{
    			return new lexeme("true", true);
    		}
    	}else{
    		lexeme left = eval(t.left, env);
    		lexeme right = eval(t.right.right, env);
    		String cond = t.right.left.type;
    		if (cond == "AND"){
    			if(left.boolToken == true && right.boolToken == true){
    				return new lexeme("true", true);
    			}else{
    				return new lexeme("false", false);
    			}
    		}else{
    			if(left.boolToken == true || right.boolToken == true){
    				return new lexeme("true", true);
    			}else{
    				return new lexeme("false", false);
    			}
    		}
    		
    	}
    }
    
    private lexeme evalOptExpressionList(lexeme t, lexeme env) throws Exception{
    	while (t != null){
    		t.left = eval(t.left, env);
    		t = t.right;
    	}
    	return null;
    }
    
    private lexeme evalUnary(lexeme t, lexeme env) throws Exception{
    	while (t != null){
    		lexeme temp = eval(t.left, env);
    		if (temp != null){
    			//System.out.println(" Here is a unary: " + temp.display());
    			return temp;
    		}
    		t = t.right;
    	}
    	return null;
    }
    
    //private lexeme evalPrintList(lexeme t, lexeme env){
   // 	while (t != null){
   // 		eval(t.left, env);
   // 		t = t.right;
   // 	}
   // 	return null;
   // }
    
    private lexeme evalNonOptVarDef(lexeme t, lexeme env) throws Exception{
    	e.insert(t.left, new lexeme("EMPTY"), env);
    	if (t.right != null){
	    	if (t.right.left != null){	
	    		if (t.right.left.type == "ASSIGN"){	
	    			//t.right.left.type == "ASSIGN"
		    		lexeme val = eval(t.right.right.left, env);
		    		e.update(t.left.left, val, env);
		    		System.out.println("var val: " + e.lookup(t.left, env));
		    		return val;
	    		}else{
	    			while (t != null){
	    	    		eval(t.left, env);
	    	    		t = t.right;
	    	    	}
	    		}
	    	}else{
	    		while (t != null){
		    		eval(t.left, env);
		    		t = t.right;
		    	}
	    	}
    	}else{
	    	while (t != null){
	    		eval(t.left, env);
	    		t = t.right;
	    	}
    	}
    	return null;
    }
    
    private lexeme evalOptInit(lexeme t, lexeme env) throws Exception{
    	while (t != null){
    		eval(t.left, env);
    		t = t.right;
    	}
    	return null;
    }
    
    private lexeme evalComparators(lexeme t, lexeme env) throws Exception{
    	while (t != null){
    		eval(t.left, env);
    		t = t.right;
    	}
    	return null;
    }
    
    private lexeme evalOptParamList(lexeme t, lexeme env) throws Exception{
    	while (t != null){
    		eval(t.left, env);
    		t = t.right;
    	}
    	return null;
    }
    
    private lexeme evalClassStructure(lexeme t, lexeme env) throws Exception{
    	while (t != null){
    		eval(t.right, env);
    		t = t.right;
    	}
    	return null;
    }
    
    private lexeme evalClassBlock(lexeme t, lexeme env) throws Exception{
    	while (t != null){
    		eval(t.left, env);
    		t = t.right;
    	}
    	return null;
    }
    
    private lexeme evalClassFunctionCall(lexeme t, lexeme env) throws Exception{
    	while (t != null){
    		eval(t.left, env);
    		t = t.right;
    	}
    	return null;
    }
    
    private lexeme evalFunctionCalls(lexeme t, lexeme env) throws Exception{
    	while (t != null){
    		eval(t.left, env);
    		t = t.right;
    	}
    	return null;
    }
    
    private lexeme evalForLoop(lexeme t, lexeme env) throws Exception{
    	while (t != null){
    		eval(t.left, env);
    		t = t.right;
    	}
    	return null;
    }

    private lexeme evalDefinition(lexeme t, lexeme env) throws Exception {
        while (t != null) {
            eval(t.left, env);
            t = t.right;
        }
        return null;
    }


    
    private lexeme evalPrint(lexeme t, lexeme env) throws Exception {
    	//System.out.println("PRINTING");
    	lexeme eargs = evalPrintList(t.right.right.left, env);
        System.out.println();
        //while (eargs != null) {
        //    System.out.print(eargs.left);
       // }
        //System.out.println();
        return null;
    }

    public lexeme evalPrintList(lexeme t, lexeme env) throws Exception {
    	//System.out.println(" tree " + t.left.left.left.display() );
    	lexeme eargs = evalExpression(t.left, env);

        if (eargs != null) {
            //System.out.println("printList " + eargs.type );
        	System.out.print(eargs.printDisplay());
        	if (t.right != null){
        		if ( t.right.right != null){
        			//System.out.print(", ");
        			evalPrintList(t.right.right, env);
        		}
        	}
            
        }

        //System.out.println();
        return null;
    }


    private lexeme evalFunctionDef(lexeme t, lexeme env) {
        lexeme closure = new lexeme("CLOSURE", env, t);
        e.insert(t.right.left, closure, env);
        return closure;
    }
    
    private lexeme evalLambdaDef(lexeme t, lexeme env){
    	lexeme closure = new lexeme("CLOSURE", env, t);
    	return  closure;
    }

    private lexeme evalVarDef(lexeme t, lexeme env) throws Exception {
       // if (t.right.right.type.equals("SEMICOLON")) {
        //    e.insert(t.right.left, new lexeme("EMPTY"), env);
       // }
        //else {
        //    lexeme val = eval(t.right.right.left.left, env);
        //    e.insert(t.right.left, val, env);
        //}
        //return null;
    	e.insert(t.left, new lexeme("EMPTY"), env);
    	
    	if (t.right != null){
	    	if (t.right.left != null){	
		    	if (t.right.left.left != null){
	    			if (t.right.left.left.type == "ASSIGN"){	
		    			//t.right.left.type == "ASSIGN"
			    		lexeme val = eval(t.right.left.right, env);
			    		e.update(t.left.left, val, env);
			    		//System.out.println("var val: " + e.lookup(t.left, env));
			    		return val;
		    		}else{
		    			while (t != null){
		    	    		eval(t.left, env);
		    	    		t = t.right;
		    	    	}
		    		}
		    	}else{
		    		while (t != null){
			    		eval(t.left, env);
			    		t = t.right;
			    	}
		    	}
	    	}else{
	    		while (t != null){
		    		eval(t.left, env);
		    		t = t.right;
		    	}
	    	}
    	}else{
	    	while (t != null){
	    		eval(t.left, env);
	    		t = t.right;
	    	}
    	}
    	return null;

    }
    
    private lexeme evalGetArray(lexeme args){
    	int val = args.right.right.left.integer;
    	ArrayList<lexeme> arr = args.left.arrayToken;
    	//System.out.println("val returned is " + arr.get(val).display());
    	if (arr.get(val).type.equals("INTEGER")){
    		return new lexeme(arr.get(val).type, Integer.parseInt(arr.get(val).display()));
    	}else{
    		lexeme a = new lexeme(arr.get(val).type, arr.get(val).display());
    		return a;
    	}
    }
    
    private lexeme evalSetArray(lexeme args, lexeme env) throws Exception{
    	//System.out.println(" setArr " + args.right.right.right.right.right.type);
    	int index = args.right.right.left.integer;
    	ArrayList<lexeme> arr = args.left.arrayToken;
    	lexeme val = eval(args.right.right.right.right.left, env);
    	arr.set(index, val);
    	//System.out.println("val returned is " + arr.get(val).display());
    	//if (arr.get(val).type.equals("INTEGER")){
    	//	return new lexeme(arr.get(val).type, Integer.parseInt(arr.get(val).display()));
    	//}else{
    	//	lexeme a = new lexeme(arr.get(val).type, arr.get(val).display());
    	//	return a;
    	//}
    	return null;
    }
    

    private lexeme evalFunctionCall(lexeme t, lexeme env) throws Exception {
       //System.out.println("Function type " + t.left.display());
       if (t.left.type == "VARIABLE"){
    	   lexeme args = t.right.right.left;
    	   lexeme eargs = evalArgList(args, env);
    	   //System.out.println("built-in getArr here "+  t.left.display());
    	   // Built ins
    	   if (t.left.display().equals("getArray")){
    		   //System.out.println("built-in getArr here");
    		   return evalGetArray(eargs);
    	   }else if (t.left.display().equals("setArray")){
    		   return evalSetArray(eargs, env);
    	   }
    	   
    	    lexeme closure = e.lookup(t.left, env); 
	           
	        lexeme params = closure.right.right.right.right.left.left;
	        lexeme body = closure.right.right.right.right.right.right;
	        lexeme denv = closure.left;
	        
	        lexeme xenv = e.extend(params, eargs, denv);
	        return eval(body, xenv);
	        //variable that points to this xenv
	        //e.insert(new lexeme("ID", "mug"), xenv, xenv);
        	
        }else{
        	//System.out.println("lambda has been reached " + t.left.type);
        	lexeme closure = evalLambdaDef(t.left, env); 
        	
	        lexeme args = t.right.right.left.left;   
	        lexeme params = closure.right.right.right.left.left;
	        lexeme body = closure.right.right.right.right.right;
	        lexeme denv = closure.left;
	        //System.out.println("lambda has been reached " + closure.right.type);
	        lexeme eargs = evalArgList(args, env);
	        lexeme xenv = e.extend(params, eargs, denv);
	        return eval(body, xenv);
        }
        
    }

    private lexeme evalDot(lexeme t, lexeme env) throws Exception {
        lexeme object = eval(t.left, env);
        return eval(t.right, e.extend(object.left, object.right.left, env));
    }

    private lexeme evalArgList(lexeme t, lexeme env) throws Exception {
        //System.out.println("inside Arg list " + t.type);
    	lexeme result = new lexeme("EVALEDARGLIST");
        lexeme ptr = result;
        while (t != null) {
            lexeme val = eval(t.left, env);
            ptr.left = val;

            t = t.right;
            if(t != null) {
                ptr.right = new lexeme("EVALEDARGLIST");
                ptr = ptr.right;
            }
        }
        return result;
    }
    
    private ArrayList<lexeme> evalArrayList(lexeme t, lexeme env) throws Exception{
    	ArrayList<lexeme> AL = new ArrayList<lexeme>();
    	while (t != null){
    		if (t.left != null && t.left.type != "COMMA" ){
    			AL.add(eval(t.left, env));
    		}
    		t = t.right;
    	}
    	return AL;
    }

    private lexeme evalArrayDef(lexeme t, lexeme env) throws Exception {
       //System.out.println("ArrayDef here " + t.left.type);
       
       if (t.right.right.left.type == "OPEN_BRACE"){
    	    ArrayList<lexeme> array = evalArrayList(t.right.right.right.left, env);
    	    lexeme newArr = new lexeme ("ARRAY", array);
    	   //System.out.println( "array Name is  " + t.right.left.display());
    	   lexeme arrayDeftemp = new lexeme("GLUE", t.right.left, null);
    	   evalVarDef(arrayDeftemp, env);
    	   
    	   e.update(t.right.left, newArr, env);
    	   //System.out.println("testing arr val " + e.lookup(t.right.left, env).display());
    	   
    	   //// THE FOLLOWING IS A PRINTING SCHEME FOR THE ARRAYS, FOR DEBUGGING PURPOSES HERE 
    	    
    	  // System.out.print("{");
    	   //for ( int i = 0; i < array.size(); i++){
    		//   if (array.get(i) != null){
    		//	   System.out.print(array.get(i).display() + ", ");
    		 //  }
    	   //}
    	   //System.out.print("}\n");
    	   
    	   ///////// END PRINTING CODE
    	   //System.out.println(newArr.display());
    	   return newArr;
    	   //return null;
    	   
       }else{ 
    	   return null;
       }
    	//return null;
    }
    
    private lexeme evalGetArr(lexeme t, lexeme env) throws Exception{
    	//System.out.println("getArr var name  " + t.right.right.left.display());
    	lexeme name = e.lookup(t.right.right.left, env);
    	String index = eval(t.right.right.right.right.left, env).display();
    	System.out.println("getArr var is  " + name.arrayToken.get(Integer.parseInt(index)).type);
    	return new lexeme(name.arrayToken.get(Integer.parseInt(index)).type, name.arrayToken.get(Integer.parseInt(index)).display());
    }
    
    private lexeme evalSetArr(lexeme t , lexeme env){
    	System.out.println("setArr here");
    	return null;
    }

    private lexeme evalArrAccess(lexeme t, lexeme env) throws Exception {
        lexeme arr = eval(t.left, env);
        lexeme index = eval(t.right, env);

        lexeme result = arr.arrayToken.get(index.integer);
        return result;
    }

    private lexeme evalIDInit(lexeme t, lexeme env) throws Exception {
        lexeme temp = new lexeme("ASSIGN", t.left, t.right.left);
        //e.insert(t.left, t.right.left, env);
        return eval(temp, env);
    }


    private lexeme evalParenExpr(lexeme t, lexeme env) throws Exception {
        return eval(t.left, env);
    }

    private lexeme evalLambdaCall(lexeme t, lexeme env) throws Exception {
        if(t.left.type.equals("IDFUNCCALL")) {
            lexeme closure = e.lookup(t.left.left, env);
            lexeme eargs = eval(t.right, env);
            lexeme params = closure.right.right.right.right.right.right.left.left.left.left.left;
            lexeme body = closure.right.right.right.right.right.right.left.left.left.left.right.left.left;
            lexeme xenv = e.extend(params, eargs, env);
            return eval(body, xenv);
        }

        else {
            lexeme eargs = eval(t.right, env);
            lexeme params = t.left.left;
            lexeme body = t.left.right;
            if (body == null) {
                lexeme closure = e.lookup(t.left, env);
                params = closure.left;
                body = closure.right;
            }

            lexeme xenv = e.extend(params, eargs, env);
            return eval(body, xenv);
        }
    }

    private lexeme evalBlock(lexeme t, lexeme env) throws Exception {
        lexeme result = null;

        while (t != null) {
            result = eval(t.left, env);
            t = t.right;
        }
        return result;
    }

    private lexeme evalStatements(lexeme t, lexeme env) throws Exception {
        lexeme val = null;
        
        while (t != null ) {
            val = eval(t.left, env);
            t = t.right;
        }
        return val;
    }

    private lexeme evalStatement(lexeme t, lexeme env) throws Exception {
    	//System.out.println("statement type is "  +t.left.type);
    	return eval(t.left, env);
    }

    private lexeme evalWhileLoop(lexeme t, lexeme env) throws Exception {
        while (eval(t.right.right.left, env).boolToken == true){
        	eval(t.right.right.right.right, env);
        }
        return null;
    }

    private lexeme evalIfStatement(lexeme t, lexeme env) throws Exception {
       //System.out.println("bool " + eval(t.right.right.left.left, env));
    	if(eval(t.right.right.left, env).boolToken) {
            return eval(t.right.right.right.right.left, env);
        }
        else {
            return eval(t.right.right.right.right.right, env);
        }
    }

    private lexeme evalOptElse(lexeme t, lexeme env) throws Exception {
        return eval(t.right, env);
    }


    private lexeme evalNot(lexeme t, lexeme env) throws Exception {
        lexeme result = eval(t.right, env);
        result.boolToken = !result.boolToken;
        return result;
    }

    private lexeme evalShortCircuitOp(lexeme t, lexeme env) throws Exception {
        switch (t.type) {
            case "AND":
                return evalAnd(t, env);
            case "OR":
                return evalOr(t, env);
            default:
                return null;
        }
    }

    private lexeme evalAnd(lexeme t, lexeme env) throws Exception {
        if (eval(t.left, env).boolToken && eval(t.right, env).boolToken)
            return new lexeme("BOOLEAN", true);
        else
            return new lexeme("BOOLEAN", false);
    }

    private lexeme evalOr(lexeme t, lexeme env) throws Exception {
        if (eval(t.left, env).boolToken || eval(t.right, env).boolToken)
            return new lexeme("BOOLEAN", true);
        else
            return new lexeme("BOOLEAN", false);
    }

    private lexeme evalSimpleOp(lexeme t, lexeme env) throws Exception {
        switch (t.type) {
            case "PLUS":
                return evalPlus(t, env);
            case "MINUS":
                return evalMinus(t, env);
            case "TIMES":
                return evalTimes(t, env);
            case "DIVIDES":
                return evalDivide(t, env);
            case "CARET":
                return evalExp(t, env);
            case "MOD":
                return evalMod(t, env);
            case "EQUALTO":
                return evalEqualTo(t, env);
            case "NOTEQUALTO":
                return evalNotEqualTo(t, env);
            case "GTHANEQUALTO":
                return evalGthanEqualTo(t, env);
            case "LTHANEQUALTO":
                return evalLthanEqualTo(t, env);
            case "GREATERTHAN":
                return evalGreaterThan(t, env);
            case "LESSTHAN":
                return evalLessThan(t, env);
            default:
                return null;
        }
    }

    private lexeme evalPlus(lexeme t, lexeme env) throws Exception {
        lexeme left = eval(t.left, env);
        lexeme right = eval(t.right, env);
        //System.out.println("inside plus --- " + t.right.display());
        if (left.type.equals("INTEGER") && right.type.equals("INTEGER"))
            return new lexeme("INTEGER", left.integer + right.integer);
        else if (left.type.equals("INTEGER") && right.type.equals("DECIMAL"))
            return new lexeme("DECIMAL", left.integer + right.real);
        else if (left.type.equals("DECIMAL") && right.type.equals("INTEGER"))
            return new lexeme("DECIMAL", left.real + right.integer);
        else
            return new lexeme("DECIMAL", left.real + right.real);
    }

    private lexeme evalMinus(lexeme t, lexeme env) throws Exception {
        lexeme left = eval(t.left, env);
        lexeme right = eval(t.right, env);
        if (left.type.equals("INTEGER") && right.type.equals("INTEGER"))
            return new lexeme("INTEGER", left.integer - right.integer);
        else if (left.type.equals("INTEGER") && right.type.equals("DECIMAL"))
            return new lexeme("DECIMAL", left.integer - right.real);
        else if (left.type.equals("DECIMAL") && right.type.equals("INTEGER"))
            return new lexeme("DECIMAL", left.real - right.integer);
        else
            return new lexeme("DECIMAL", left.real - right.real);
    }

    private lexeme evalTimes(lexeme t, lexeme env) throws Exception {
        lexeme left = eval(t.left, env);
        lexeme right = eval(t.right, env);
        if (left.type.equals("INTEGER") && right.type.equals("INTEGER"))
            return new lexeme("INTEGER", left.integer * right.integer);
        else if (left.type.equals("INTEGER") && right.type.equals("DECIMAL"))
            return new lexeme("DECIMAL", left.integer * right.real);
        else if (left.type.equals("DECIMAL") && right.type.equals("INTEGER"))
            return new lexeme("DECIMAL", left.real * right.integer);
        else
            return new lexeme("DECIMAL", left.real * right.real);
    }

    private lexeme evalDivide(lexeme t, lexeme env) throws Exception {
        lexeme left = eval(t.left, env);
        lexeme right = eval(t.right, env);
        if (left.type.equals("INTEGER") && right.type.equals("INTEGER"))
            return new lexeme("INTEGER", left.integer / right.integer);
        else if (left.type.equals("INTEGER") && right.type.equals("DECIMAL"))
            return new lexeme("DECIMAL", left.integer / right.real);
        else if (left.type.equals("DECIMAL") && right.type.equals("INTEGER"))
            return new lexeme("DECIMAL", left.real / right.integer);
        else
            return new lexeme("DECIMAL", left.real / right.real);
    }

    private lexeme evalExp(lexeme t, lexeme env) throws Exception {
        lexeme left = eval(t.left, env);
        lexeme right = eval(t.right, env);
        if (left.type.equals("INTEGER") && right.type.equals("INTEGER"))
            return new lexeme("DECIMAL", Math.pow((double) left.integer, (double) right.integer));
        else if (left.type.equals("INTEGER") && right.type.equals("DECIMAL"))
            return new lexeme("DECIMAL", Math.pow(left.integer, right.real));
        else if (left.type.equals("DECIMAL") && right.type.equals("INTEGER"))
            return new lexeme("DECIMAL", Math.pow(left.real, right.integer));
        else
            return new lexeme("DECIMAL", Math.pow(left.real, right.real));
    }

    private lexeme evalMod(lexeme t, lexeme env) throws Exception {
        lexeme left = eval(t.left, env);
        lexeme right = eval(t.right, env);
        return new lexeme("INTEGER", left.integer % right.integer);
    }

   // private lexeme evalAssign(lexeme t, lexeme env) {
   //     lexeme value = eval(t.right, env);
    //    e.update(t.left, value, env);
      //  System.out.println("var val: " + e.lookup(t.left, env));
        
       // if (t.left.type.equals("ID")) {
      //     e.update(t.left, value, env);
      // }
      // else if (t.left.type.equals("DOT")) {
      //     lexeme object = eval(t.left.left, env);
      //     e.update(t.left.right, value, object);
       // }

     //   return value;
   // }

    private lexeme evalEqualTo(lexeme t, lexeme env) throws Exception {
        lexeme left = eval(t.left, env);
        lexeme right = eval(t.right, env);
        if (left.type.equals("INTEGER") && right.type.equals("INTEGER"))
            return new lexeme("BOOLEAN", left.integer == right.integer);
        else if (left.type.equals("INTEGER") && right.type.equals("DECIMAL"))
            return new lexeme("BOOLEAN", left.integer == right.real);
        else if (left.type.equals("DECIMAL") && right.type.equals("INTEGER"))
            return new lexeme("BOOLEAN", left.real == right.integer);
        else if (left.type.equals("DECIMAL") && right.type.equals("DECIMAL"))
            return new lexeme("BOOLEAN", left.real == right.real);
        else if (left.type.equals("STRING") && right.type.equals("STRING"))
            return new lexeme("BOOLEAN", left.stringToken.equals(right.stringToken));
        else if (left.type.equals("BOOLEAN") && right.type.equals("BOOLEAN"))
            return new lexeme("BOOLEAN", left.boolToken == right.boolToken);
        else if (left.type.equals("EMPTY") && !right.type.equals("EMPTY"))
            return new lexeme("BOOLEAN", false);
        else if (!left.type.equals("EMPTY") && right.type.equals("EMPTY"))
            return new lexeme("BOOLEAN", false);
        else if (left.type.equals("EMPTY") && right.type.equals("EMPTY"))
            return new lexeme("BOOLEAN", true);
        else {
            System.out.printf("\nFatal error in Evaluator.java: Type mismatch, attempting to compare %s with %s\n", left.type, right.type);
            System.exit(1);
            return null;
        }
    }

    private lexeme evalNotEqualTo(lexeme t, lexeme env) throws Exception {
        lexeme left = eval(t.left, env);
        lexeme right = eval(t.right, env);
        if (left.type.equals("INTEGER") && right.type.equals("INTEGER"))
            return new lexeme("BOOLEAN", left.integer != right.integer);
        else if (left.type.equals("INTEGER") && right.type.equals("DECIMAL"))
            return new lexeme("BOOLEAN", left.integer != right.real);
        else if (left.type.equals("DECIMAL") && right.type.equals("INTEGER"))
            return new lexeme("BOOLEAN", left.real != right.integer);
        else if (left.type.equals("DECIMAL") && right.type.equals("DECIMAL"))
            return new lexeme("BOOLEAN", left.real != right.real);
        else if (left.type.equals("STRING") && right.type.equals("STRING"))
            return new lexeme("BOOLEAN", !left.stringToken.equals(right.stringToken));
        else if (left.type.equals("BOOLEAN") && right.type.equals("BOOLEAN"))
            return new lexeme("BOOLEAN", left.boolToken != right.boolToken);
        else if (left.type.equals("EMPTY") && !right.type.equals("EMPTY"))
            return new lexeme("BOOLEAN", true);
        else if (!left.type.equals("EMPTY") && right.type.equals("EMPTY"))
            return new lexeme("BOOLEAN", true);
        else if (left.type.equals("EMPTY") && right.type.equals("EMPTY"))
            return new lexeme("BOOLEAN", false);
        else {
            System.out.printf("\nFatal error in Evaluator.java: Type mismatch, attempting to compare %s with %s\n", left.type, right.type);
            System.exit(1);
            return null;
        }
    }

    private lexeme evalGthanEqualTo(lexeme t, lexeme env) throws Exception {
        lexeme left = eval(t.left, env);
        lexeme right = eval(t.right, env);
        if (left.type.equals("INTEGER") && right.type.equals("INTEGER"))
            return new lexeme("BOOLEAN", left.integer >= right.integer);
        else if (left.type.equals("INTEGER") && right.type.equals("DECIMAL"))
            return new lexeme("BOOLEAN", left.integer >= right.real);
        else if (left.type.equals("DECIMAL") && right.type.equals("INTEGER"))
            return new lexeme("BOOLEAN", left.real >= right.integer);
        else if (left.type.equals("DECIMAL") && right.type.equals("DECIMAL"))
            return new lexeme("BOOLEAN", left.real >= right.real);
        else {
            System.out.printf("\nFatal error in Evaluator.java: Type mismatch, attempting to compare %s with %s\n", left.type, right.type);
            System.exit(1);
            return null;
        }
    }

    private lexeme evalLthanEqualTo(lexeme t, lexeme env) throws Exception {
        lexeme left = eval(t.left, env);
        lexeme right = eval(t.right, env);
        if (left.type.equals("INTEGER") && right.type.equals("INTEGER"))
            return new lexeme("BOOLEAN", left.integer <= right.integer);
        else if (left.type.equals("INTEGER") && right.type.equals("DECIMAL"))
            return new lexeme("BOOLEAN", left.integer <= right.real);
        else if (left.type.equals("DECIMAL") && right.type.equals("INTEGER"))
            return new lexeme("BOOLEAN", left.real <= right.integer);
        else if (left.type.equals("DECIMAL") && right.type.equals("DECIMAL"))
            return new lexeme("BOOLEAN", left.real <= right.real);
        else {
            System.out.printf("\nFatal error in Evaluator.java: Type mismatch, attempting to compare %s with %s\n", left.type, right.type);
            System.exit(1);
            return null;
        }
    }

    private lexeme evalLessThan(lexeme t, lexeme env) throws Exception {
        lexeme left = eval(t.left, env);
        lexeme right = eval(t.right, env);
        if (left.type.equals("INTEGER") && right.type.equals("INTEGER"))
            return new lexeme("BOOLEAN", left.integer < right.integer);
        else if (left.type.equals("INTEGER") && right.type.equals("DECIMAL"))
            return new lexeme("BOOLEAN", left.integer < right.real);
        else if (left.type.equals("DECIMAL") && right.type.equals("INTEGER"))
            return new lexeme("BOOLEAN", left.real < right.integer);
        else
            return new lexeme("BOOLEAN", left.real < right.real);
    }

    private lexeme evalGreaterThan(lexeme t, lexeme env) throws Exception {
        lexeme left = eval(t.left, env);
        lexeme right = eval(t.right, env);
        if (left.type.equals("INTEGER") && right.type.equals("INTEGER"))
            return new lexeme("BOOLEAN", left.integer > right.integer);
        else if (left.type.equals("INTEGER") && right.type.equals("DECIMAL"))
            return new lexeme("BOOLEAN", left.integer > right.real);
        else if (left.type.equals("DECIMAL") && right.type.equals("INTEGER"))
            return new lexeme("BOOLEAN", left.real > right.integer);
        else
            return new lexeme("BOOLEAN", left.real > right.real);
    }

    public lexeme runEval(lexeme tree) throws Exception {
    
        eval(tree, global);
        return global;
    }

}