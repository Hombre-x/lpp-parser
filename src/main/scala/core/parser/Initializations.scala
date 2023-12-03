package com.graphene
package core.parser

import parsley.combinator.*
import core.lexer.Lexemes.identifier
import core.lexer.Types.{functionType, typeP}
import core.lexer.Lexer.{epsilon, given}
import domain.parser.Parser
import syntax.parser.?

import Actions.{actions, statements, switchStatement}
import Expressions.expr


object Initializations:
  
  lazy val initializations: Parser[String] =
    for
      rs <- registers
      dl <- declarations
      pf <- many(procedure <|> function) map (_.mkString("\n"))
    yield
      s"""$rs
         |$dl
         |$pf
         |""".stripMargin
  
  
  lazy val declarations: Parser[String] = many(declaration) map (_.mkString("\n"))
    
  lazy val declaration: Parser[String] = (typeP <~> identifiers) map ((t, ids) => s"$t $ids")
  
  lazy val identifiers: Parser[String] = sepBy1(identifier, ",") map (_.mkString(","))
  
  // Procedures:
  private lazy val procedures = many(procedure) map (_.mkString("\n"))
  lazy val procedure: Parser[String] =
    for
      id <- "procedimiento" *> identifier
      r  <- procedureP
    yield
      s"""procedimiento $id$r
         |""".stripMargin
    
  lazy val procedureP =
    ((( "(" *> params <* ")" ) <~> procedureBody ) map ((p, b) => s"($p) \n$b") )
      <|> procedureBody
  
  lazy val procedureBody = (declarations <~> statements) map ((s, d) => s"$d \n$s")
      
      
  // Functions:
  private lazy val functions = many(function) map (_.mkString("\n"))
  private lazy val function = (("funcion" *> identifier) <~> funcionP) map ((id, b) => s"funcion $id $b")
  private lazy val funcionP =
    ((("(" *> params <* ")") <~> functionPP) map ((p, b) => s"($p) \n$b"))
    <|> functionReturn
    
  private lazy val functionPP =
    ((declarations <~> statements) map ((d, s) => s"$d \n$s"))
    <|> functionReturn
    
  private lazy val functionReturn =
    for
      t <- ":" *> functionType
      d <- declarations
      b <- functionBody
    yield
      s""":$t
         |$d
         |$b
         |""".stripMargin
      
    
  private lazy val functionBody = (("inicio" *> actions) <~> functionBodyP) map ((a, b) => s"inicio\n$a\n$b")
  private lazy val functionBodyP =
    (("retorne" *> expr <* "fin") map (r => s"retorne $r\nfin"))
    <|> ("fin" #> "fin")
      
  private lazy val registers = many(register) map (_.mkString("\n"))
  private lazy val register =
    for
      rt <- "registro" *> identifier
      at <- registerAttributes <* "fin registro"
    yield
      s"""registro $rt
         |$at
         |fin registro
         |""".stripMargin
      
  private lazy val registerAttributes = many(registerAttribute) map (_.mkString("\n"))
  private lazy val registerAttribute = (typeP <~> identifiers) map ((t, ids) => s"$t $ids")
  
  lazy val params: Parser[String] = sepBy1(paramVar, ",") map (_.mkString(","))
  lazy val paramVar: Parser[String] = (("var" <|> epsilon) <~> param) map ((v, p) => s"$v $p")
  lazy val param: Parser[String] =
    for
      t <- typeP
      i <- identifier
    yield
      s"$t $i"
  
  

   
 
end Initializations
