# lexmeme
My Programming Language

Matt Williams
CS 403
Designer Programming Language


This language was built using Java.

RUNNING THE PROGRAM

To run the program, type dpl filename.
For example,
	dpl MyLanguageCodeExample

This will run the program on the file MyLangugeCodeExample.
You can also use the make file included to show different functions.
Here are the commands for the make file: (colons not part of commands)
	make clean : removes all the .class files
	make error1: displays the error1 file
	make error1x: executes the error1 file
	make error2:
	make error2x:
	make error3:
	make error3x:
	make comments:
	make commmentsx:
	make intsandstrings:
	make intsandstringsx:
	make dynamic:
	make dynamicx:
	make arrays:
	make arraysx:
	make conditionals:
	make conditionalsx:
	make recursion:
	make recursionx:
	make iteration:
	make iterationx:
	make print:
	make printx:
	make operators:
	make operatorsx:
	make functions:
	make functionsx:
	make dictionary:
	make dictionaryx:
	make calculator:
	make calculatorx:
	make lambda:
	make lambdax:



Each file must have a main function. All code must be inside of the main function to work, expect for any import statements, which are to be placed at the very top of the file.

Comments are created using #, which comments out anything that comes after # on that line.

Numbers and Strings
	This language supports several different types of literals, including decimals, integers, and strings. Strings must be wrapped with quotes. Decimals and integers are differntiated by the decimal point that decimals contain.

	There are many different operations that can be done in this language. Here are a list of the operators:
		1) (Plus) : +
		2) (Minus) : -
		3) (Multiply) : *
		4) (Divide) : /
		5) (Caret / Exponentiation) : ^
		6) (Modulus) : %

	When performing comparison operations, the following operators can be used:
		1) (Less than) : <
		2) (Greater than) : >
		3) (Less than equals) : <=
		4) (Greater than equals) : >=
		5) (Equals) : equals?

	There are also several other keywords that can bew used with operators
		1) (AND) : and
		2) (OR) : or
		3) (NOT) : not

Definitions:
	To define a variable, you can either provide a simple initialization, or perform assignment directly. Both cases require a semicolon to end the statement. This language is dynamically typed so variables do not need to be declared as a "string", or "integer", this is not necessary.

	As and example, if we had a variable called x we could define it in the following two ways:
		1) x;
		2) x = 15;

	To define a function, the definition must include the tag "function" before the definition begins. Functions can take any number of arguments, which includes not having any at all. The function is wrapped with "{" "}" braces, and all function code must be within these braces.

	For example,
		function testFunction(){}

	To define a class, use the same method as with function, excpet use the "class" tag instead. There are also several ways to define a class in this language. This language is object oriented,
	so you can define empty classes, or classes as other types.

	For example,
		1) class testClass{
			# code here
		 	}
		 2) class classObject of testClass{
		 	# code here
		 	}

	To define an array, use the same method as above, expect use the "array" tag instead,
	There are also several ways to define arrays. Here are some examples:
		1) array testArray{ 1, "hi", true}
		2) array testArray[5]
	The first method above defines an array and sets the contents of the array to the list {1, "hi", true}
	The second methods creates an empty array with a size of 5.

	There are also several built in functions for use with arrays.

	Get an item from the array:
		getArr(array, index)

	Set the value of an item in the array:
		setArr(array, index, value)


	This language also supports anonymous funtions. They are defined just like regular functions, except they use the "lambda" tag. Here is an example.
		lambda (x) {print(x)}
