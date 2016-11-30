
public class PrettyPrint {

	String Tab = "";
	private boolean canSpace = true;
	private boolean isArrayFunc = false;
	private boolean isList = false; 
	
	
	public PrettyPrint(lexeme tree){
		PrettyPrint2(tree);
	}

	
	private void PrettyPrint2(lexeme tree) {

		//System.out.println(" ------------ " + tree.type);
		switch(tree.type){
			case "PROGRAM":
				PrettyPrint2(tree.left);
				PrettyPrint2(tree.right);
				break;
			case "GLUE":
				PrettyPrint2(tree.left);
				PrettyPrint2(tree.right);
				break;
			case "IMPORTS":
				PrettyPrint2(tree.left);
				PrettyPrint2(tree.right);
				break;
			case "MAINFUNC":
				PrettyPrint2(tree.left);
				PrettyPrint2(tree.right);
				break;
			case "UNARY":
				PrettyPrint2(tree.left);
				PrettyPrint2(tree.right);
				break;
			case "EXPRESSION":
				PrettyPrint2(tree.left);
				PrettyPrint2(tree.right);
				break;
			case "OPERATOR":
				PrettyPrint2(tree.left);
				if (tree.right != null && tree.right.type != "EMPTY"){
					PrettyPrint2(tree.right);
				}
				break;
			case "optExpressionList":
				PrettyPrint2(tree.left);
				PrettyPrint2(tree.right);
				break;
			case "ifStatement":
				PrettyPrint2(tree.left);
				PrettyPrint2(tree.right);
				break;
			case "optElse":
				PrettyPrint2(tree.left);
				PrettyPrint2(tree.right);
				break;
			case "ELSE":
				System.out.print("else ");
				break;
			case "whileLoop":
				PrettyPrint2(tree.left);
				PrettyPrint2(tree.right);
				break;
			case "block":
				PrettyPrint2(tree.left);
				PrettyPrint2(tree.right);
				break;
			case "statement":
				PrettyPrint2(tree.left);
				PrettyPrint2(tree.right);
				break;
			case "statements":
				PrettyPrint2(tree.left);
				PrettyPrint2(tree.right);
				break;
			case "nonOptVarDef":
				PrettyPrint2(tree.left);
				PrettyPrint2(tree.right);
				break;
			case "varDef":
				PrettyPrint2(tree.left);
				PrettyPrint2(tree.right);
				break;
			case "optInit":
				PrettyPrint2(tree.left);
				PrettyPrint2(tree.right);
				break;
			case "functionDef":
				PrettyPrint2(tree.left);
				PrettyPrint2(tree.right);
				break;
			case "paramList":
				PrettyPrint2(tree.left);
				PrettyPrint2(tree.right);
				break;
			case "comparators":
				PrettyPrint2(tree.left);
				PrettyPrint2(tree.right);
				break;
			case "optParamList":
				PrettyPrint2(tree.left);
				PrettyPrint2(tree.right);
				break;
			case "forLoop":
				PrettyPrint2(tree.left);
				PrettyPrint2(tree.right);
				break;
			case "classStructure":
				PrettyPrint2(tree.left);
				PrettyPrint2(tree.right);
				break;
			case "functionCalls":
				PrettyPrint2(tree.left);
				PrettyPrint2(tree.right);
				break;
			case "functionCall":
				PrettyPrint2(tree.left);
				PrettyPrint2(tree.right);
				break;
			case "classBlock":
				PrettyPrint2(tree.left);
				PrettyPrint2(tree.right);
				break;
			case "classDef":
				PrettyPrint2(tree.left);
				PrettyPrint2(tree.right);
				break;
			case "classFuncCall":
				PrettyPrint2(tree.left);
				PrettyPrint2(tree.right);
				break;
			case "print":
				PrettyPrint2(tree.left);
				PrettyPrint2(tree.right);
				break;
			case "printList":
				PrettyPrint2(tree.left);
				PrettyPrint2(tree.right);
				break;
			case "increment":
				PrettyPrint2(tree.left);
				PrettyPrint2(tree.right);
				break;
			case "decrement":
				PrettyPrint2(tree.left);
				PrettyPrint2(tree.right);
				break;
			case "conditional":
				PrettyPrint2(tree.left);
				PrettyPrint2(tree.right);
				break;
			case "ifGuts":
				PrettyPrint2(tree.left);
				PrettyPrint2(tree.right);
				break;
			case "EMPTY":
				//System.out.print(tree.display());
				break;
			case "OPEN_BRACE":
				
				if (isList == false){
					System.out.print("{ \n");
					//System.out.print(Tab);
					Tab += "   ";
					
					System.out.print(Tab);
				}
				else{
					System.out.print("{");
				}
				break;
			case "CLOSE_BRACE":
				System.out.print("} \n");
				//lastTab = Tab;
				//Tab = Tab = "\t";
				
				//System.out.println(Tab.length());
				if (Tab.length() >= 3 && isList == false){
					Tab = Tab.substring(3);
				}
				isList = false;
				System.out.print(Tab);
				break;
			case "OPEN_BRACKET":
				System.out.print("[");
				//System.out.print(Tab);
				break;
			case "CLOSE_BRACKET":
				System.out.print("]\n");
				System.out.print(Tab);
				break;
			case "LESSTHAN":
				System.out.print(" < ");
				break;
			case "GREATERTHAN":
				System.out.print(" > ");
				break;
			case "LESSTHANEQUALS":
				System.out.print(" <= ");
				break;
			case "GREATERTHANEQUALS":
				System.out.print(" >= ");
				break;
			case "ASSIGN":
				System.out.print(" = ");
				break;
			case "EQUALS?":
				System.out.print(" equals? ");
				break;
			case "OPEN_PAREN":
				System.out.print("(");
				break;
			case "CLOSE_PAREN":
				canSpace = true;
				System.out.print(")");
				if (isArrayFunc == true){
					System.out.print("\n" + Tab);
				}
				isArrayFunc = false;
				break;
			case "SEMICOLON":
				if(canSpace  == true){
					System.out.print("; \n");
					System.out.print(Tab);
				}else{
					System.out.print("; ");
				}
				
				break;
			case "DECREMENT":
				System.out.print("--");
				break;
			case "INCREMENT":
				System.out.print("++");
				break;
			case "COMMA":
				System.out.print(", ");
				break;
			case "DOT":
				System.out.print(".");
				break;
			case "MOD":
				System.out.print(" % ");
				break;
			case "CARET":
				System.out.print(" ^ ");
				break;
			case "PLUS":
				System.out.print(" + ");
				break;
			case "MINUS":
				System.out.print(" - ");
				break;
			case "TIMES":
				System.out.print(" * ");
				break;
			case "DIVIDES":
				System.out.print(" / ");
				break;
			case "STRING":
				System.out.print(tree.display());
				break;
			case "true":
				System.out.print(tree.display());
				break;
			case "false":
				System.out.print(tree.display());
				break;
			case "INTEGER":
				System.out.print(tree.display());
				break;
			case "DECIMAL":
				System.out.print(tree.display());
				break;
			case "MAIN":
				System.out.print("Main");
				break;
			case "VARIABLE":
				System.out.print(tree.display());
				break;
			case "PRINT":
				System.out.print("print");
				break;
			case "LAMBDA":
				System.out.print("lambda ");
				break;
			case "arrayDef":
				PrettyPrint2(tree.left);
				PrettyPrint2(tree.right);
				//System.out.print("array \n");
				break;
			case "ARRAY":
				System.out.print("array ");
				isList = true;
				break;
			case "setArr":
			//	System.out.print("setArr ");
				PrettyPrint2(tree.left);
				PrettyPrint2(tree.right);
				break;
			case "getArr":
			//	System.out.print("getArr ");
				PrettyPrint2(tree.left);
				PrettyPrint2(tree.right);
				break;
			case "GETARR":
				System.out.print("getArr");
				isArrayFunc = true;
				break;
			case "SETARR":
				System.out.print("setArr");
				isArrayFunc = true;
				break;
			case "OF":
				System.out.print(" of ");
				break;
			case "AND":
				System.out.print(" and ");
				break;
			case "OR":
				System.out.print(" or ");
				break;
			case "IMPORT":
				System.out.print("import ");
				break;
			case "FUNCTION":
				System.out.print("function ");
				break;
			case "WHILE":
				System.out.print("while ");
				break;
			case "IF":
				System.out.print("if ");
				break;
			case "FOR":
				canSpace = false;
				System.out.print("for ");
				break;
			case "CLASS":
				System.out.print("class ");
				break;
			case "lambdaCall":
				PrettyPrint2(tree.left);
				PrettyPrint2(tree.right);
				break;
			default:
				System.out.print(tree.display() + " ");
				break;
				//System.out.print("ENDofINPUT");
		}
	
	}
	
}
