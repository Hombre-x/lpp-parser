package com.graphene
package utils

import cats.effect.Sync
import cats.effect.std.Console
import cats.Show
import cats.syntax.all.*

extension [F[_] : Console : Sync, A](fa: F[A])
  def peek: F[A] =
    for
      a <- fa
      t <- Sync[F].delay(Thread.currentThread.getName)
      _ <- Console[F].println(s"[$t] $a")
    yield a
