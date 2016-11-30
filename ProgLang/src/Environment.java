
public class Environment {

	public Environment(){
		
	}
	
	public lexeme cons(String type, lexeme carValue, lexeme cdrValue){
		return new lexeme("CONS", carValue, cdrValue);
	}
	
	public lexeme car(lexeme item){
		return item.left;
	}
	
	public lexeme cdr(lexeme item){
		return item.right;
	}
	
	public lexeme cadr(lexeme item){
		return item.right.left;
	}
	
	public lexeme setCar(lexeme item, lexeme value){
		item.left = value;
		return item.left;
	}
	
	public boolean sameVariable(lexeme a, lexeme b){
		//System.out.println("type " + b.type);
		return a.stringToken.equals(b.stringToken);
	}
	
	
	
	public lexeme  create(){
		return extend(null, null, null);
	}
	
	public lexeme lookup(lexeme variable, lexeme env){
    while (env != null){
        lexeme vars = car(env);
        lexeme vals = cadr(env);
        if(vars.type.equals("paramList")) {
        	while (vars != null && vars.type != "EMPTY"){
                if (sameVariable(variable, car(car(car(vars))))){
                	return car(vals);
                }
                vars = cdr(vars);
                vals = cdr(vals);
            }
        }
        else {
	        while (vars != null && vars.type != "EMPTY"){
	            if (sameVariable(variable, car(vars))){
	                	return car(vals);
	            }
	            vars = cdr(vars);
	            vals = cdr(vals);
	        }
        }
        env = cdr(cdr(env));
     }

   
    System.out.printf("Variable %s is undefined\n", variable.stringToken);
    System.exit(0);
    return null;
    }
	
	public lexeme insert(lexeme variable, lexeme value, lexeme env){
	    //System.out.println("env types " + env);
		setCar(env, new lexeme("GLUE", variable, car(env)));
	    setCar(cdr(env), new lexeme("GLUE", value, cadr(env)));
	    return value;
    }
	
	public lexeme extend(lexeme variables, lexeme values, lexeme env){
		return new lexeme("ENV", variables, new lexeme("VALUES", values, env));
		//return new lexeme("ENV", new lexeme("GLUE", variables, values), env);
		//return cons("ENV", cons("GLUE", variables, values), env);
    }
	
	public lexeme update(lexeme variable, lexeme value, lexeme env){
		//System.out.println("var is " + variable);
		lexeme oldE = env;
		while (env != null){
			lexeme vars = car(env);
			lexeme vals = cadr(env);
			
			while(vars != null && vars.type != "EMPTY"){
				//System.out.println("variable is " + variable.type + " vars " + car(vars).type);
				if (sameVariable(variable, car(vars))){
					return setCar(vals, value);
				}
				vars = cdr(vars);
				vals = cdr(vals);
			}
			env = cdr(cdr(env));
		}
		
		//System.out.printf("Variable %s is undefined\n", variable.stringToken);
		return insert(variable, value, oldE);
	}
	
}
