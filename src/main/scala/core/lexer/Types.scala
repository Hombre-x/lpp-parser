package com.graphene
package core.lexer

import parsley.combinator.sepBy1
import core.lexer.Lexemes.{identifier, integer}
import core.lexer.Lexer.given
import domain.parser.Parser

object Types:

  
  private val stringType: Parser[String] =
    ("cadena" *> "[" *> integer <* "]") map (int => s"cadena[$int]")
    
  private lazy val arrayType: Parser[String] =
    (("arreglo" *> "[" *> arrayDimension <* "]" <* "de") <~> typeP) map ((int, t) => s"arreglo[$int] de $t")

  lazy val arrayDimension: Parser[String] = sepBy1(integer, ",") map (_.mkString(","))
  
  
  lazy val typeP: Parser[String] =
    ("booleano" #> ("booleano"))
      <|> ("caracter" #> "caracter")
      <|> ("entero" map (_ => "entero"))
      <|> ("real" map (_ => "real"))
      <|> stringType
      <|> arrayType
      <|> identifier
    
  lazy val functionType: Parser[String] =
    ("booleano" #> "booleano")
      <|> ("caracter" map (_ => "caracter"))
      <|> ("entero" map (_ => "entero"))
      <|> ("real" map (_ => "real"))
      <|> stringType
      <|> identifier
  
end Types





  
  

    
    
