Program for CS 317 Automata Proj. 1
By: 
Tam Nguyen
t.nguyen098@yahoo.com

Program purpose:
Takes in post-fixed regular expression and converted to an NFA (shown as transitions)

Three ways of running,
1. java Main <postfix expression>

2. a.) Have a input.txt (in the same director) that contains multiple postfix expresion (separated by new line (\n))
   b.) run "java Main"
   
3. a.) run "java Main"
   b.) type the a postfix expression

   
Example of an input and output

java Main "a*b|"
------------------------------
Expression: a*b|

(q6, E) -> q3
(q6, E) -> q4
(q1, a) -> q2
(q2, E) -> q3
(q3, E) -> q1
(q3, E) -> q7
(q4, b) -> q5
(q5, E) -> q7

Starting state: q6
Final state: q7


Input symbols include: a, b, c, d, e, |, &, * and E (for ε)
NOTHING ELSE