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
	cat -n error1.mylang
error1x:
	dpl error1.mylang 
error2:	# missing semicolon after statement
	cat -n error2.mylang
error2x:
	dpl error2.mylang
error3: # no main functionm
	cat -n error3.mylang
error3x:
	dpl error3.mylang
comments:
	cat -n comments.mylang
commentsx:
	dpl comments.mylang
intsandstrings:
	cat -n intsandstrings.mylang
intsandstringsx:
	dpl intsandstrings.mylang
dynamic:
	cat -n dynamic.mylang
dynamicx:
	dpl dynamic.mylang
arrays:
	cat -n arrays.mylang
arraysx:
	dpl arrays.mylang
conditionals:
	cat -n conditionals.mylang
conditionalsx:
	dpl conditionals.mylang
recursion:
	cat -n recursion.mylang
recursionx:
	dpl recursion.mylang
iteration:
	cat -n iteration.mylang
iterationx:
	dpl iteration.mylang
print:
	cat -n print.mylang
printx:
	dpl print.mylang
operators:
	cat -n operators.mylang
operatorsx:
	dpl operators.mylang
functions:
	cat -n firstclass.mylang
functionsx:
	dpl firstclass.mylang
dictionary:
	# not implemented
dictionaryx:   
 	# not implemented

problem:
	# not implemented
problemx:
	# not implemented
lambda:
	cat -n lambda.mylang
lambdax:
	dpl lambda.mylang

