package com.graphene
package core.parser

import parsley.combinator.*
import core.lexer.Lexer.{epsilon, given}
import core.lexer.Types.arrayDimension
import core.lexer.Lexemes.{identifier, literals}

import Expressions.*
import Initializations.identifiers
import domain.parser.Parser
object Actions:
  
  lazy val statements =
    ("inicio" *> actions <* "fin") map (actions => s"inicio $actions fin")

  lazy val actions: Parser[String] = many(action) map ( _.mkString("\n") )
  
  lazy val action =
    read
    <|> write
    <|> assign
    <|> call
    <|> ifStatement
    <|> switchStatement
    <|> whileLoop
    <|> repeatLoop
    <|> forLoop

  
  // Read
  lazy val read = "lea" *> identifiers map (ids => s"lea $ids")
  
  // Write
  lazy val write = "escriba" *> functionParams map (params => s"escriba $params")
  
  // Assign
  lazy val assign = ((variableAccessNoFunc <* "<-") <~> expr) map ((v, e) => s"$v <- $e")
  lazy val variableAccessNoFunc = (identifier <~> variableAccessNoFuncP) map ( (id, pp) => s"$id$pp" )
  lazy val variableAccessNoFuncP =
    (("." *> identifier) map (id => s".$id"))
    <|> arrayAccesses
    <|> epsilon
  
  lazy val call  = ("llamar" *> callP) map (p => s"llamar $p")
  lazy val callP =
    (identifier <~> ( "(" *> functionParamsZero <* ")" ) map ( (id, p) => s"$id($p)") )
    <|> ("nueva_linea" #> "nueva_linea")
    
  // If statement
  lazy val ifStatement =
    for
      e <- "si" *> expr
      a <- "entonces" *> actions
      f <- ifStatementP
    yield
      s"""si $e entonces
         |$a$f
         |""".stripMargin
      
  lazy val ifStatementP =
    (("sino" *> actions <* "fin si") map (a => s"sino \n$a \nfin si"))
    <|> ("fin si" #> "fin si")
    
  // Switch statement
  lazy val switchStatement =
    for
      id <- "caso" *> identifier
      va <- switchValues
      sp <- switchStatementP
    yield
      s"""caso $id
         |$va
         |$sp
         |""".stripMargin
      
  lazy val switchStatementP =
    (("sino" *> ":" *> actions <* "fin caso") map (a => s"sino: \n$a \nfin caso"))
    <|> ("fin caso" #> "fin caso")
    
  lazy val switchValues = some(switchValue) map (_.mkString("\n"))
  lazy val switchValue = ((values <* ":") <~> actions) map ( (v, a) => s"$v: $a")
  lazy val values = sepBy1(literals, ",") map (_.mkString(","))
  
  
  // Loops
  lazy val whileLoop =
    ("mientras" *> expr) <~> ("haga" *> actions <* "fin mientras") map ((e, a) => s"mientras $e hacer \n$a \nfin mientras")
  
  lazy val repeatLoop =
    ("repita" *> actions) <~> ("hasta" *> expr) map ((a,e) => s"repita \n$a \nhasta $e")
    
  lazy val forLoop: Parser[String] =
    for
      tup <- ( ("para" *> identifier) <~> ( "<-" *> expr ) ) map ( (id, e) => s"para $id <- $e" )
      ha <- "hasta" *> expr
      a <- "haga" *> actions <* "fin para"
    yield
      s"""$tup hasta $ha
          |$a
          |fin para""".stripMargin
      

  
end Actions
