package com.graphene
package utils

import core.lexer.Operators.{inverseOperatorMap, operatorMap}
import core.lexer.Lexer.{lexer, given}


private def junct(init: List[String], last: String, delim: String, junction: String, oxfordComma: Boolean): String = {
  init.mkString(start = "", sep = delim, end = if (oxfordComma) s"$delim$junction$last" else s" $junction$last")
}
def junct(elems: List[String], junction: String, oxfordComma: Boolean): Option[String] =

  val sortedElems =
    elems
      .map(tkn => operatorMap.getOrElse(tkn, tkn))
      .sorted(using Ordering[String].reverse)
      .map(tkn => inverseOperatorMap.getOrElse(tkn, tkn))
    
  sortedElems match
    case Nil => None
    case List(alt) => Some(alt)
    case List(alt1, alt2) => Some(s"$alt2 $junction $alt1")
    case alt::alts => Some(junct(alts.reverse, alt, delim = ", ", junction = junction, oxfordComma = true))

def disjunct(elems: List[String], oxfordComma: Boolean): Option[String] = junct(elems, junction = "", oxfordComma)

def combineInfoWithLines(info: Seq[String], lines: Seq[String]): Seq[String] =
  if info.isEmpty then "Error desconocido" +: lines
  else info ++: lines



  