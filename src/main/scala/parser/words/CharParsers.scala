package com.graphene
package parser.words

import cats.syntax.all.*
import cats.Eval

import parser.primitive.PrimitiveParsers.*
import domain.parser.*

object CharParsers:
  def sat(predicate: Char => Boolean): Parser[Char] =
    item >>= ( x =>
      if predicate(x) then result(x)
      else zero(s"The char \"$x\" doesn't satisfy the predicate"))

  def digit: Parser[Char] = sat(_.isDigit)
  def lower: Parser[Char] = sat(_.isLower)
  def upper: Parser[Char] = sat(_.isUpper)
  def whiteSpace: Parser[Char] = sat(_.isWhitespace)
  def char(c: Char): Parser[Char] = sat(_ === c)
  def letter: Parser[Char] = lower <+> upper
  def alphanum: Parser[Char] = letter <+> digit
  def word: Parser[String] = 

    val neWord =
      letter `>>=`: x =>
        word `>>=`: xs =>
          result (s"$x$xs") <+> zero("Can't parse word")

    neWord <+> result("")

  end word


end CharParsers