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

diagram : 'D' '(' diagramParam ')' NL initializer+;

diagramParam : (name | signature);

initializer: NL* '\t' formula NL* | NL* '\t' vars NL* | NL* '\t' globalConditions NL* | NL* '\t' renaming NL*;

vars : 'Vars' NL variable+;

variable : '\t'+ KIND TYPE BRACKETS? ID NL?;

globalConditions : 'GlobalConditions' NL ('\t'+ condition NL)+;

renaming : 'Renaming' NL renamer+;

renamer : '\t'+ (TYPE | 'bool') ID OP condition NL?; 

formula : 'F' '(' (mod INDICATOR_DELIM)? pre INDICATOR_DELIM post ')' NL refinement;

pre : 'pre:' condition;

post : 'post:' condition;

mod : 'mod:' condition;

intm : 'intm:' condition;

condition : '(' condition ')' | '(' condition ')' OP condition | quantor | identifier | identifier condition | OP condition | condition ',' condition; 

quantor : QT ID ';' condition;

keyword : '\\' ID '(' identifier ')';

identifier : ID | keyword | (TYPE | ID | keyword) ('[' condition ']' | '[*]');

refinement : '\t'+ refinementRule; 

refinementRule : statement | composition | selection | repetition | returnS | originalS | skipS | methodCallS | block | mlexpr;

statement : (identifier | javaReturn | identifier'()' | identifier '(' condition ')' | identifier statement | identifier OP assigner | identifier OP)? ';' NL?;

javaReturn : 'return' assigner;

assigner : (identifier | '(' assigner ')' | identifier EMPTY_BRACKETS | identifier assigner | NEW identifier | identifier OP assigner);

composition : 'C' '(' (mod INDICATOR_DELIM)? intm ')' NL refinement refinement;

selection : 'S' '(' (mod INDICATOR_DELIM)? guards ')' NL refinement+;

guards : guard (INDICATOR_DELIM guard)*;

repetition : 'L' '(' (mod INDICATOR_DELIM)? inv INDICATOR_DELIM guard INDICATOR_DELIM var ')' NL refinement;

inv : 'inv:' condition;

guard : 'guard:' condition;

var : 'var:' condition;

returnS : 'R:' statement;

originalS : 'O:' statement;

skipS : 'skip' NL?;

methodCallS : 'M:' statement;

block : 'B' '(' (mod INDICATOR_DELIM)? name INDICATOR_DELIM pre INDICATOR_DELIM post ')' NL mlexpr ;

name : 'name:' ID;

signature : 'sig:' VISIBILITY ('void' | TYPE BRACKETS?) ID (EMPTY_BRACKETS | '(' methodParameter (',' methodParameter)* ')');

methodParameter : TYPE BRACKETS? ID;

mlexpr : '{' NL? ('\t'+ statement)* '\t'+ '}' NL?;

// Lexer rules

WS : [ \r]+ -> skip;

NL: '\n';

INDICATOR_DELIM : ';';

KIND : 'LOCAL' | 'RETURN' | 'PARAM' | 'PUBLIC';

VISIBILITY : 'public' | 'private' | 'protected';

TYPE : 'boolean' | 'char' | 'short' | 'int' | 'long' | 'String';

BRACKETS : [\[\]]+;

EMPTY_BRACKETS : '()';

NEW : 'new ';

OP : [=+\-*/%<>&|!. ]+;

QT : '\\forall int ' | '\\exists int ';

ID : [a-zA-Z0-9]+;

