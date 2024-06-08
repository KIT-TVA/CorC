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

diagram : 'D' '(' name ')' NL initializer+;

initializer: NL* '\t' formula NL* | NL* '\t' vars NL* | NL* '\t' globalConditions NL* | NL* '\t' renaming NL*;

vars : 'Vars' NL variable+;

variable : '\t'+ KIND TYPE BRACKETS? ID NL?;

globalConditions : 'GlobalConditions' NL ('\t'+ condition NL)+;

renaming : 'Renaming' NL renamer+;

renamer : '\t'+ ID OP condition NL?; 

formula : 'F' '(' pre ',' post ')' NL refinement;

pre : 'pre:' condition;

post : 'post:' condition;

intm : 'intm:' condition;

condition : '(' condition ')' | '(' condition ')' OP condition | quantor | identifier | identifier condition | OP condition | condition ',' condition; 

quantor : QT ID ';' condition;

keyword : '\\' ID '(' identifier ')';

identifier : ID | keyword | (TYPE | ID | keyword) '[' condition ']';

refinement : '\t'+ refinementRule; 

refinementRule : statement | composition | selection | repetition | returnS | originalS | skipS | methodCallS | block | mlexpr;

statement : (identifier | javaReturn | identifier'()' | identifier '(' condition ')' | identifier statement | identifier OP assigner | identifier OP)? ';' NL?;

javaReturn : 'return' assigner;

assigner : (identifier | '(' assigner ')' | identifier'()' | identifier assigner | NEW identifier | identifier OP assigner);

composition : 'C' '(' intm ')' NL refinement refinement;

selection : 'S' '(' guards ')' NL refinement+;

guards : guard (',' guard)*;

repetition : 'L' '(' inv ',' guard ',' var ')' NL refinement;

inv : 'inv:' condition;

guard : 'guard:' condition;

var : 'var:' condition;

returnS : 'R:' statement;

originalS : 'O:' statement;

skipS : 'skip' NL?;

methodCallS : 'M:' statement;

block : 'B' '(' name ',' pre ',' post ')' NL mlexpr ;

name : 'name:' ID;

mlexpr : '{' NL? ('\t'+ statement)* '\t'+ '}' NL?;

// Lexer rules

WS : [ \r]+ -> skip;

NL: '\n';

KIND : 'LOCAL' | 'RETURN' | 'PARAM' | 'PUBLIC';

TYPE : 'boolean' | 'char' | 'short' | 'int' | 'long' | 'String';

BRACKETS : [\[\]]+;

NEW : 'new ';

OP : [=+\-*/%<>&|!. ]+;

QT : '\\forall int ' | '\\exists int ';

ID : [a-zA-Z0-9]+;

