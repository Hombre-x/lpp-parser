package com.graphene
package core.lexer

import parsley.errors.combinator.ErrorMethods
import core.lexer.Lexer.lexer
import domain.parser.Parser

import parsley.Parsley

object Lexemes:

  val identifier: Parser[String] = lexer.lexeme.names.identifier.label("id")
  val integer: Parser[String]    = lexer.lexeme.numeric.integer.number.label("valor_entero").map(_.toString)
  val real: Parser[String]       = lexer.lexeme.numeric.real.float.label("valor_real").map(_.toString)
  val string: Parser[String]     = lexer.lexeme.text.rawString.fullUtf16.label("cadena_de_caracteres")
  val character: Parser[String]    = lexer.lexeme.text.character.fullUtf16.label("caracter_simple").map(_.toString)
  val boolean: Parser[String]   =
    lexer.lexeme.symbol.apply("vardadero", "verdadero") #> "verdadero"
      <|> lexer.lexeme.symbol.apply("falso", "falso") #> "falso"
    

  val literals: Parsley[String] = identifier <|> real <|> integer <|> string <|> character <|> boolean