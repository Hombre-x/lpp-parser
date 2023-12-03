package com.graphene
package syntax.parser

import parsley.combinator.option
import domain.parser.Parser

import scala.annotation.targetName

extension [A](pa: Parser[A]) 
  @targetName("opt")
  def ? : Parser[String] = option(pa) map:
    case Some(a) => s"$a "
    case None => ""
    
    
