package com.graphene
package core.parser

import parsley.combinator.eof
import parsley.errors.combinator.ErrorMethods
import Initializations.initializations
import Actions.{actions, statements}
import core.lexer.Lexer.given

import domain.parser.Parser
object Program:
  
  def program: Parser[String] =
    for
      i1 <- initializations
      a  <- statements <* eof
    yield
      s"""$i1
         |$a
         |""".stripMargin
      
  val testString =
    """inicio
      |	caso var1
      |		1+1,2,3: escriba 2+2
      |		4: escriba 3+4
      |	fin caso
      |fin
      |""".stripMargin
      

      
 
end Program
