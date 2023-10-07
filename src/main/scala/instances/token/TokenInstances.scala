package com.graphene
package instances.token

import cats.Show
import cats.syntax.eq.*
import cats.syntax.show.*
import domain.token.{Position, Token}

given TokenShow[A : Show]: Show[Token[A]] = Show.show:
  case Token(name, lexeme, Position(row, column)) if name === lexeme.show =>
    s"<$name,$row,$column>"

  case Token(name, lexeme, Position(row, column)) =>
    s"<$name,${lexeme.show},$row,$column>"