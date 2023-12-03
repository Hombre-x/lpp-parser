package com.graphene
package core.lexer

import core.lexer.Lexer.symbol

object Operators:
  
  val operators: Set[String] =
    Set(
      "<-",
      "<>",
      "<=",
      ">=",
      "<" ,
      ">" ,
      "." ,
      "," ,
      ":" ,
      "]" ,
      "[" ,
      ")" ,
      "(" ,
      "+" ,
      "-" ,
      "*" ,
      "/",
      "^",
      "=",
    )
    
  val operatorParsers = List(
    "<-",
    "<>",
    "<=",
    ">=",
    "<",
    ">",
    ".",
    ",",
    ":",
    "]",
    "[",
    ")",
    "(",
    "+",
    "-",
    "*",
    "/",
    "^",
    "="
  ).map(symbol)
  
  val operatorMap: Map[String, String] = Map(
    "<-" -> "tkn_assign",
    "<>" -> "tkn_neq",
    "<=" -> "tkn_leq",
    ">=" -> "tkn_geq",
    "<"  -> "tkn_less",
    ">"  -> "tkn_greater",
    "."  -> "tkn_period",
    ","  -> "tkn_comma",
    ":"  -> "tkn_colon",
    "]"  -> "tkn_closing_bra",
    "["  -> "tkn_opening_bra",
    ")"  -> "tkn_closing_par",
    "("  -> "tkn_opening_par",
    "+"  -> "tkn_plus",
    "-"  -> "tkn_minus",
    "*"  -> "tkn_times",
    "/"  -> "tkn_div",
    "^"  -> "tkn_power",
    "="  -> "tkn_equal"
  ).map( (k, v) => (s"\"$k\"", s"\"$v\"") )
  
  val inverseOperatorMap: Map[String, String] = operatorMap.map(_.swap)
 
end Operators
