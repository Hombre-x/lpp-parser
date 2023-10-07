package com.graphene
package domain.token

type Lexeme   = String
type Program  = String
type Name     = String

case class Position(line: Int, column: Int)

case class Token[A](name: Name, lexeme: A, position: Position)

