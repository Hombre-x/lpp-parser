package com.graphene
package domain.parser

import cats.Eval
import cats.data.{EitherT, StateT}
import domain.evalEither.EvalEither

import com.graphene.domain.token.Token

/**
 * Evolution of the type:
 * 1. String => (String, A)
 * 2. String => Either[String, (String, A)]
 * 3. String => EitherT[Eval, ParseError, (String, A)]
 * 4. StateT[EitherT[Eval, ParseError, *], String, A]
 * 5.
 */

type ParserError = String
type Parser[A] = StateT[EvalEither, String, A]
object Parser:
  def apply[A](sf: String => EvalEither[(String, A)]): Parser[A] =
    StateT[EvalEither, String, A](sf)
    
  
    


