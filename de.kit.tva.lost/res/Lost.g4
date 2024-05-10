/*
 /*
 * LOST is the DSL that the textual editor of CorC is based upon. 
 * 
 * Documentation: ...
 */
grammar Lost;

// Parser rules

program : root EOF;

root : diagram;

diagram : 'D(' name ')' NL initializer+;

initializer: NL* formula NL* | NL* vars NL* | NL* globalConditions NL* | NL* renaming NL*;

vars : '\t' 'Vars' NL variable+;

variable : '\t'+ KIND TYPE ID ';' NL?;

globalConditions : '\t' 'GlobalConditions' NL ('\t'+ condition NL)+;

renaming : '\t' 'Renaming' NL renamer+;

renamer : '\t'+ ID OP condition NL?; 

formula : '\t' 'F' '(' pre ',' post ')' NL refinement;

pre : 'pre:' condition;

post : 'post:' condition;

intm : 'intm:' condition;

condition : '(' condition ')' | '(' condition ')' OP condition | quantor | ID | ID condition | OP condition; 

quantor : ID '(' ID ID ';' condition ')';

refinement : '\t'+ refinementRule; 

refinementRule : statement | composition | selection | repetition | returnS | originalS | skipS | methodCallS | block | mlexpr;

statement : (ID | javaReturn | '(' statement ')' | ID'()' | ID statement | ID OP assigner) ';' NL?;

javaReturn : ID assigner;

assigner : (ID | '(' assigner ')' | ID'()' | ID assigner | ID OP assigner);

composition : 'C(' intm ')' NL refinement refinement;

selection : 'S(' guards ')' NL refinement+;

guards : 'guard:' condition (',' 'guard:' condition)*;

repetition : 'L(' inv ',' guard ',' var ')' NL refinement;

inv : 'inv:' condition;

guard : 'guard:' condition;

var : 'var:' ID;

returnS : 'R:' statement;

originalS : 'O:' statement;

skipS : 'skip' NL?;

methodCallS : 'M:' statement;

block : 'B(' name ',' pre ',' post ')' NL mlexpr ;

name : 'name:' ID;

mlexpr : '{' NL? statement* '}' NL?;

// Lexer rules

WS : [ \r]+ -> skip;

NL: '\n';

KIND : 'LOCAL' | 'RETURN' | 'PARAM' | 'PUBLIC';

TYPE : 'boolean' | 'char' | 'short' | 'int' | 'long' | 'String';

OP : [=+\-*/%<>.]+;

ID : [a-zA-Z0-9\[\]]+;

