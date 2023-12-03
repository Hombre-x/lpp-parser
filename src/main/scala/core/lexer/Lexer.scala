package com.graphene
package core.lexer

import cats.syntax.eq.*
import parsley.character.{item, string}
import parsley.Parsley.{atomic, pure}
import parsley.*
import parsley.token.descriptions.*
import parsley.token.descriptions.numeric.{ExponentDesc, PlusSignPresence}
import parsley.token.predicate.Basic
import domain.parser.Parser

import Whitespace.skipWhitespace
import ReservedWords.reservedKeywords
import Operators.operators
import parsley.token.Lexer


object Lexer:

  private val lppDesc = LexicalDesc(
    nameDesc = NameDesc.plain.copy(
      identifierStart = Basic(char => char.isLetter || char === '_'),
      identifierLetter = Basic(char => char.isLetterOrDigit || char === '_')
    ),
    symbolDesc = SymbolDesc.plain.copy(
      hardKeywords = reservedKeywords,
      hardOperators = operators,
      caseSensitive = false
    ),
    numericDesc = numeric.NumericDesc.plain.copy(
      positiveSign = PlusSignPresence.Illegal,
      integerNumbersCanBeHexadecimal = false,
      integerNumbersCanBeOctal = false,
      hexadecimalLeads = Set.empty,
      octalLeads = Set.empty,
      binaryLeads = Set.empty,
      decimalExponentDesc = ExponentDesc.NoExponents,
      hexadecimalExponentDesc = ExponentDesc.NoExponents,
      octalExponentDesc = ExponentDesc.NoExponents,
      binaryExponentDesc = ExponentDesc.NoExponents
    ),
    
    textDesc = text.TextDesc.plain.copy(
      escapeSequences = text.EscapeDesc.plain.copy(
        escBegin = '~',
        literals = Set()
      )
    ),
    spaceDesc = SpaceDesc.plain.copy(
      commentStart = "/*",
      commentEnd   = "*/",
      commentLine  = "//"
    )
  )
  
  
  val lexer = new parsley.token.Lexer(lppDesc)
  def symbol(s: String): Parser[String] = atomic(string(s))
  def epsilon: Parser[String] = atomic(pure(""))
  def lexeme(p: Parser[String]): Parser[String] = p <* skipWhitespace
  given anonymousSymbol: Conversion[String, Parser[Unit]] = lexer.lexeme.symbol.implicits.implicitSymbol

end Lexer
