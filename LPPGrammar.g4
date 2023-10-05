grammar LPPGrammar ;

// Initial set of rules
program : initializations principal initializations EOF ; 

initializations : initialization initializations | ;
initialization  : declaration 
                | procedure ;

// Reserved words
PROCEDURE : 'procedimiento' ;
ENTONCES : 'entonces' ;
ESCRIBA : 'escriba' ;
LEA : 'lea' ;
CADENA: 'cadena' ;
SI : 'si' ;
SINO : 'sino' ;
MIENTRAS : 'mientras' ;
HAGA : 'haga' ;
REPITA : 'repita' ;
HASTA : 'hasta' ;

// Tokens:
ID : 'var1' | 'var2' | 'var3' 
   | 'procedimiento1' | 'procedimiento2'
   | 'funcion1' | 'funcion2' ;

STRING : '"Hola mundo"' ;

// Operators
TKN_ASSIGN : '<-' ;
TKN_COMMA : ',' ;


TKN_INTEGER : [0-9]+ ;
WS : [ \t\r\n]+ -> skip ;

// =================================

// Declarations
declarations : declaration declarations | ;
declaration : type ids ;

// ids : id | id TKN_COMMA ids ; 
ids   : ID ids_p ; 
ids_p : TKN_COMMA ids | ;

// Types
type   : 'entero' | 'real' | 'booleano' | 'caracter' | cadena ;
cadena : CADENA '[' TKN_INTEGER ']' ;


// params : param | param TKN_COMMA params ;
params   : param params_p ;
params_p : TKN_COMMA params | ;
param    : type ID ;

// Procedures 
procedures : procedure procedures | ;

// procedure  : PROCEDURE ID '(' declarations ')' declarations principal 
//            | PROCEDURE ID declarations principal ;
procedure : PROCEDURE ID procedure_p ;
procedure_p : '(' params ')' declarations statements 
            | declarations statements ;

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
       | ciclo_mientras 
       | ciclo_repita ;

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
assignment : ID TKN_ASSIGN STRING ;


// If statement 
condition : '2 < 3' ; 

// if_statement : SI condition 'entonces' actions 'fin si' 
//              | SI condition 'entonces' actions SINO actions 'fin si' ;

if_statement   : SI condition ENTONCES actions if_statement_p ;
if_statement_p : 'fin si' | SINO actions 'fin si' ;

// Ciclo mientras
ciclo_mientras : MIENTRAS condition HAGA actions 'fin mientras' ;

// Ciclo repita
ciclo_repita : REPITA actions HASTA condition ;