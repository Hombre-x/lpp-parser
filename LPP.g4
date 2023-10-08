grammar LPP ;

// Initial set of rules
program : initializations principal initializations EOF ; 

initializations : initialization initializations | ;
initialization  : declaration 
                | procedure 
                | function ;

// Reserved words
PROCEDURE : 'procedimiento' ;
FUNCTION : 'funcion' ;
REGISTER : 'registro' ;
ENTONCES : 'entonces' ;
ESCRIBA : 'escriba' ;
RETURN : 'retorne' ;
LEA : 'lea' ;
CADENA: 'cadena' ;
SI : 'si' ;
SINO : 'sino' ;
MIENTRAS : 'mientras' ;
HAGA : 'haga' ;
REPITA : 'repita' ;
HASTA : 'hasta' ;
PARA : 'para' ;
CASO : 'caso' ;
VAR : 'var' ;

// Tokens:
ID : 'var1' | 'var2' | 'var3' 
   | 'procedimiento1' | 'procedimiento2'
   | 'funcion1' | 'funcion2' 
   | 'registro1_t' | 'registro2_t' ;

NUMBER  : INTEGER | REAL ;
STRING  : '"Hola mundo"' ;
BOOLEAN : 'verdadero' | 'falso' ;



// Operators
TKN_ASSIGN : '<-' ;
TKN_COMMA : ',' ;
TKN_COLON : ':' ;

INTEGER : [0-9]+ ;
REAL    : [0-9]+ '.' [0-9]+ ;
WS : [ \t\r\n]+ -> skip ;

// =================================

// Declarations
declarations : declaration declarations | ;
declaration : type ids 
            | register ;

// ids : id | id TKN_COMMA ids ; 
ids   : ID ids_p ; 
ids_p : TKN_COMMA ids | ;

// Types
type   : 'entero' | 'real' | 'booleano' | 'caracter' | cadena ;
cadena : CADENA '[' INTEGER ']' ;


// params : param | param TKN_COMMA params ;
params   : param params_p ;
params_p : TKN_COMMA params | ;
param    : type ID 
         | VAR type ID ;

// Procedures 
procedures : procedure procedures | ;

// procedure  : PROCEDURE ID '(' params ')' declarations principal 
//            | PROCEDURE ID declarations principal ;
procedure   : PROCEDURE ID procedure_p ;
procedure_p : '(' params ')' declarations statements 
            | declarations statements ;


// Functions
functions : function functions | ;
// function  : FUNCTION ID '(' params ')' TKN_COLON type declarations function_body
//           | FUNCTION ID TKN_COLON type declarations function_body 
//           | FUNCTION ID '(' params ')' declarations statements ;

function   : FUNCTION ID function_p ;
// function_p : '(' params ')' TKN_COLON type declarations function_body
//            | '(' params ')' declarations statements 
//            | TKN_COLON type declarations function_body ;
function_p : '(' params ')' function_p2
           | TKN_COLON type declarations function_body ;  

function_p2 : declarations statements
            | TKN_COLON type declarations function_body ;

         


function_body : 'inicio' actions RETURN expr 'fin' ;

// =================================

//  Princial algorithm
principal  : statements ;

statements : 'inicio' actions 'fin' ;

// Define the actions
actions : action actions | ;

// Action
action : escriba 
       | lea 
       | assignment 
       | if_statement 
       | switch_statement
       | ciclo_mientras 
       | ciclo_repita 
       | ciclo_para ;

// Escriba action   
escriba: ESCRIBA escriba_params ; // escriba action

// escriba_params : escriba_param | escriba_param TKN_COMMA escriba_params ;
escriba_params : escriba_param escriba_params_p ;
escriba_params_p : TKN_COMMA escriba_params | ;
escriba_param : STRING
              | ID ;
              
// Lea action
lea : LEA ids ; 

// Assignment
assignment : ID TKN_ASSIGN expr ;


// If statement 

// if_statement : SI condition 'entonces' actions 'fin si' 
//              | SI condition 'entonces' actions SINO actions 'fin si' ;

if_statement   : SI expr ENTONCES actions if_statement_p ;
if_statement_p : 'fin si' | SINO actions 'fin si' ;

// Switch statements

// switch_statement : CASO ID switch_values 'fin caso' 
//                  | CASO ID switch_values SINO actions 'fin caso' ;

switch_statement   : CASO ID switch_values switch_statement_p ;
switch_statement_p : 'fin caso' | SINO TKN_COLON actions 'fin caso' ;

switch_values     : switch_value switch_values | ;
switch_value      : values TKN_COLON actions ;
// values            : VALUE | VALUE TKN_COMMA values ;
values            : value values_p ;
values_p          : TKN_COMMA values | ;
value             : NUMBER | STRING | BOOLEAN ;// TODO: Breaks STRING recognition in <escriba> 

// Ciclo mientras
ciclo_mientras : MIENTRAS expr HAGA actions 'fin mientras' ;

// Ciclo repita
ciclo_repita : REPITA actions HASTA expr ;

// Ciclo para 
ciclo_para : PARA ID TKN_ASSIGN expr HASTA expr HAGA actions 'fin para' ;


// =================================

// Registers
register : REGISTER register_type register_attributes 'fin registro' ;
register_attributes : register_attribute register_attributes | ;
register_attribute : type ID ;
register_type : ID ; 


// Expressions

// expr : logical_or ;
// logical_or : logical_or 'o' logical_and | logical_and ;
// logical_and : logical_and 'y' equality | equality ;
// equality : equality equality_ops comparison | comparison ;
// equality_ops : '=' | '<>' ;
// comparison : comparison comparison_ops addition | addition ;
// comparison_ops : '<' | '>' | '<=' | '>=' ;
// addition : addition addops multiplication | multiplication ;
// addops : '+' | '-' ;
// multiplication : multiplication mulops exponentiation | exponentiation ;
// mulops : '*' | '/' | 'div' | 'mod' ;
// exponentiation   : unary '^' exponentiation | unary ;
// unary : '-' primary | primary ;
// unary_ops : '-' | '!' ;
// primary : ID | NUMBER | '(' expr ')' ;

expr             : logical_or ;

logical_or       : logical_and logical_or_p ;
logical_or_p     : 'o' logical_and logical_or_p | ;

logical_and      : equality logical_and_p ;
logical_and_p    : 'y' equality logical_and_p | ;

equality         : comparison equality_p ;
equality_p       : equality_ops comparison equality_p | ;
equality_ops     : '=' | '<>' ;

comparison       : addition comparison_p ;
comparison_p     : comparison_ops addition comparison_p | ;
comparison_ops   : '<' | '>' | '<=' | '>=' ;

addition         : multiplication addition_p ;
addition_p       : add_ops multiplication addition_p | ;
add_ops          : '+' | '-' ;

multiplication   : exponentiation multiplication_p ;
multiplication_p : mul_ops exponentiation multiplication_p | ;
mul_ops          : '*' | '/' | 'div' | 'mod' ;

exponentiation   : unary exponentiation_p ;
exponentiation_p : '^' exponentiation | ;

unary            : unary_ops primary | primary ;
unary_ops        : '-' | '!' ;

primary          : ID | value | '(' expr ')' ; 