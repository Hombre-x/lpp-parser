package com.graphene
package domain.parser.expr

sealed trait Expr

case class ExprOp(op: String, left: String, right: String)


