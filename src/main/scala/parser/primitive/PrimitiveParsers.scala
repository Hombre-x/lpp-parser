package com.graphene
package parser.primitive

import domain.parser.*
import domain.evalEither.EvalEither.*

object PrimitiveParsers:

  /**
   * Parser that always succeeds with the given value `v`,
   * without consuming any input.
   */
  def result[A](v: A): Parser[A] =
    Parser(inp => later((inp, v)))

  /**
   *  Parser that always fails with the given error message.
   */
  def zero[A](errorMsg: ParserError): Parser[A] =
    Parser(_ => error(errorMsg))

  /**
   * Parser which successfully consumes the first character
   * if the input string is non-empty, and fails otherwise.
   */
  def item: Parser[Char] =
    Parser: inp =>
      if inp.isEmpty then error("item")
      else later((inp.tail, inp.head))

 
end PrimitiveParsers
