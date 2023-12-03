package com.graphene
package core.lexer

import core.lexer.Lexer.{lexer, symbol}
import domain.parser.Parser
object ReservedWords:

  val reservedKeywords = Set(
    "procedimiento",
    "fin mientras",
    "nueva_linea",
    "registro",
    "fin caso",
    "fin para",
    "mientras",
    "entonces",
    "caracter",
    "booleano",
    "arreglo",
    "funcion",
    "retorne",
    "escriba",
    "fin si",
    "repita",
    "llamar",
    "cadena",
    "entero",
    "inicio",
    "hasta",
    "para",
    "haga",
    "caso",
    "sino",
    "real",
    "var",
    "lea",
    "mod",
    "div",
    "fin",
    "de",
    "si",
    "o",
    "y"
  )
  
  
  
  
  val reservedSymbols: List[Parser[String]] =
    List(
      "procedimiento",
      "fin mientras",
      "nueva_linea",
      "registro",
      "fin caso",
      "fin para",
      "mientras",
      "entonces",
      "caracter",
      "booleano",
      "arreglo",
      "funcion",
      "retorne",
      "escriba",
      "fin si",
      "repita",
      "llamar",
      "cadena",
      "entero",
      "inicio",
      "hasta",
      "para",
      "haga",
      "caso",
      "sino",
      "real",
      "var",
      "lea",
      "mod",
      "div",
      "fin",
      "de",
      "si",
      "o",
      "y"
    ).map(symbol)
  

end ReservedWords
