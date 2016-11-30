JCC = javac
JFLAGS = -g

default: lexeme.class lexer.class Environment.class Evaluator.class Main.class PrettyPrint.class RecParse.class

lexeme.class: lexeme.java
	$(JCC) $(JFLAGS) lexeme.java

lexer.class: lexer.java
	$(JCC) $(JFLAGS) lexer.java

Environment.class: Environment.java
	$(JCC) $(JFLAGS) Environment.java

Evaluator.class: Evaluator.java
	$(JCC) $(JFLAGS) Evaluator.java

Main.class: Main.java
	$(JCC) $(JFLAGS) Main.java

PrettyPrint.class: PrettyPrint.java
	$(JCC) $(JFLAGS) PrettyPrint.java

RecParse.class: RecParse.java
	$(JCC) $(JFLAGS) RecParse.java


# To start over from scratch, type 'make clean'.  
# Removes all .class files, so that the next make rebuilds them
#
clean: 
	$(RM) *.class

error1: # missing parens in function def
	cat error1.mylang
error1x:
	mylang error1.mylang 
error2:	# missing semicolon after statement
	cat error2.mylang
error2x:
	mylang error2.mylang
error3: # no main functionm
	cat error3.mylang
error3x:
	mylang error3.mylang
comments:
	cat comments.mylang
commentsx:
	mylang comments.mylang
intsandstrings:
	cat intsandstrings.mylang
intsandstringsx:
	mylang intsandstrings.mylang
dynamic:
	cat dynamic.mylang
dynamicx:
	mylang dynamic.mylang
arrays:
	cat arrays.mylang
arraysx:
	mylang arrays.mylang
conditionals:
	cat conditionals.mylang
conditionalsx:
	mylang conditionals.mylang
recursion:
	cat recursion.mylang
recursionx:
	mylang recursion.mylang
iteration:
	cat iteration.mylang
iterationx:
	mylang iteration.mylang
print:
	cat print.mylang
printx:
	mylang print.mylang
operators:
	cat operators.mylang
operatorsx:
	mylang operators.mylang
functions:
	cat firstclass.mylang
functionsx:
	mylang firstclass.mylang
dictionary:
	# not implemented
dictionaryx:   
 	# not implemented

calculator:
	# not implemented
calculatorx:
	# not implemented
lambda:
	cat lambda.mylang
lambdax:
	mylang lambda.mylang
