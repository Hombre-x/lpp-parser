package com.graphene
package core.parser

import parsley.combinator.*
import parsley.expr.chain.*
import core.lexer.Lexer.*
import core.lexer.Lexemes.*
import core.lexer.Lexer.given
import domain.parser.Parser

import parsley.Parsley


object Expressions:
  
  
  lazy val expr: Parser[String] = logicalOr
  
  private lazy val logicalOr: Parser[String] = left1(logicalAnd, "o" #> ( (l, r) => s"$l o $r") )
  
  private lazy val logicalAnd: Parser[String] = left1(equality, "y" #> ( (l, r) => s"$l y $r"))
  
  private lazy val equality: Parser[String] = left1(comparison, equalityOps)
  private lazy val equalityOps: Parsley[(String, String) => String] =
    ("=" #> ((l: String, r: String) => s"$l = $r"))
      <|> ("<>" #> ((l: String, r: String) => s"$l <> $r"))
    
  private lazy val comparison: Parser[String] = left1(addition, comparisonOps)
  private lazy val comparisonOps: Parser[(String, String) => String] =
    ("<=" #> ((l: String, r: String) => s"$l <= $r"))
      <|> (">=" #> ((l: String, r: String) => s"$l >= $r"))
      <|> ("<" #> ((l: String, r: String) => s"$l < $r"))
      <|> (">" #> ((l: String, r: String) => s"$l > $r"))
    
  private lazy val addition: Parser[String] = left1(multiplication, additionOps)
  private lazy val additionOps: Parser[(String, String) => String] =
    ("+" #> ((l: String, r: String) => s"$l + $r"))
      <|> ("-" #> ((l: String, r: String) => s"$l - $r"))

  private lazy val multiplication: Parser[String] = left1(exponentiation, multiplicationOps)
  private lazy val multiplicationOps: Parser[(String, String) => String] =
    ("*" #> ((l: String, r: String) => s"$l * $r"))
      <|> ("/" #> ((l: String, r: String) => s"$l / $r"))
      <|> ("div" #> ((l: String, r: String) => s"$l div $r"))
      <|> ("mod" #> ((l: String, r: String) => s"$l mod $r"))

  private lazy val exponentiation: Parser[String] = right1(unary, ("^" #> ((l, r) => s"$l ^ $r")))

  private lazy val unary: Parser[String] =
    ( (unaryOps <~> primary) map { case (op, p) => s"$op $p" } )
      <|> primary
  private lazy val unaryOps: Parser[String] = "-" #> "-"

  private lazy val primary: Parser[String] =
    real
    <|> integer
    <|> boolean
    <|> string
    <|> character
    <|> (( "(" *> expr <* ")" ) map (e => s"($e)"))
    <|> variableAccess

  lazy val variableAccess: Parser[String] = (identifier <~> variableAccessP) map ( (id, pp) => s"$id$pp" )
  private lazy val variableAccessP: Parser[String] =
    (( "(" *> functionParamsZero <* ")" ) map (p => s"($p)") )
    <|> ("." *> identifier map (id => s".$id"))
    <|> arrayAccesses
    <|> epsilon
  

  lazy val arrayDimensionExpr: Parser[String] = sepBy1(expr, ",") map (_.mkString(","))
  
  lazy val arrayAccesses: Parser[String] = many(arrayAccess) map (_.mkString(""))
  lazy val arrayAccess: Parser[String]   = "[" *> arrayDimensionExpr <* "]" map (e => s"[$e]")
  
  
  lazy val functionParams: Parser[String] = sepBy1(expr, ",") map (_.mkString(","))
  lazy val functionParamsZero: Parser[String] = sepBy(expr, ",") map (_.mkString(","))
  
  
end Expressions


