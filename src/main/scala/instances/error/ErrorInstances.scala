package com.graphene
package instances.error

import parsley.errors.DefaultErrorBuilder
import utils.{combineInfoWithLines, disjunct}

import domain.parser.Parser
import core.lexer.Lexer.lexer
import core.lexer.Operators.operatorParsers
import core.lexer.ReservedWords.reservedSymbols

import parsley.Parsley
import parsley.errors.tokenextractors.LexToken
import parsley.errors.combinator.ErrorMethods

abstract class MyErrorBuilder extends DefaultErrorBuilder with LexToken
given CustomErrorBuilder: MyErrorBuilder with
  
  override val endOfInput: String = "final de archivo"

  override def vanillaError(
    unexpected: Option[String],
    expected: Option[String],
    reasons: Seq[String],
    lines: Seq[String]
  ): Seq[String] = combineInfoWithLines(Seq.concat(unexpected, expected, reasons), lines)

  override def format(pos:  String, source: Option[String], lines: Seq[String]): String =
    val newLines = lines.take(2).mkString("; ")
    s"$pos Error sintactico: ${newLines}"
  
  override def unexpected(item: Option[String]): Option[String] = item map (i => s"se encontro: \"$i\"")
  override def expected(alts: Option[String]): Option[String] =
    alts map (a => s"se esperaba: ${a.replaceAll("keyword ", "")}.")
  override def pos(line: Int, col: Int): String = s"<$line:$col>"
  override def combineExpectedItems(alts: Set[String]): Option[String] =
    disjunct(alts.toList.filter(_.nonEmpty).map(item => s"\"$item\""), true)

  override def raw(item:  String): String = item

  override def tokens: Seq[Parser[String]] =
    val identifier: Parser[String] = lexer.nonlexeme.names.identifier.label("id")
    val integer: Parser[String]    = lexer.nonlexeme.numeric.integer.number.label("entero").map(_.toString)
    val real: Parser[String]       = lexer.nonlexeme.numeric.real.float.label("real").map(_.toString)
    val string: Parser[String]     = lexer.nonlexeme.text.rawString.fullUtf16.label("cadena_de_caracteres")
    val character: Parser[String]  = lexer.nonlexeme.text.character.fullUtf16.label("caracter_simple").map(_.toString)
    val boolean: Parser[String] =
      lexer.nonlexeme.symbol.apply("vardadero", "verdadero") #> "verdadero"
        <|> lexer.nonlexeme.symbol.apply("falso", "falso") #> "falso"

    val lexemes = List(identifier, real, integer, string, character, boolean)

    operatorParsers ::: reservedSymbols ::: lexemes

  end tokens
  
