javachip
========

Generic Lexer and Parser written in Java.

Currently it is able to convert a string that represents an equation, for example "1 + 2 - 5", into a list of tokens 
which can be presented something like:
(NUMBER 1)
(BINARYOP +)
(NUMBER 2)
(BINARYOP -)
(NUMBER 5)
