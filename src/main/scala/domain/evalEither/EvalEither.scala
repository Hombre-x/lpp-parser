package com.graphene
package domain.evalEither

import cats.data.EitherT
import cats.Eval

import domain.parser.ParserError
import domain.token.Token

type EvalEither[A] = EitherT[Eval, ParserError, A]
object EvalEither:

  def now[A](value: A): EvalEither[A] =
    EitherT(Eval.now(Right(value)))

  def later[A](value: A): EvalEither[A] =
    EitherT(Eval.later(Right(value)))

  def error[A](error: ParserError): EvalEither[A] =
    EitherT.fromEither[Eval](Left(error))