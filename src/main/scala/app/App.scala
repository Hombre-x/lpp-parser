package com.graphene
package app

import core.lexer.Lexer.lexer.fully
import core.parser.Program.program
import syntax.program.tlc

import instances.error.given

object App:

  def parseProgram(input: String): java.lang.String =
    val parsingResult = fully(program.void #> "El analisis sintactico ha finalizado exitosamente.") parse input.tlc
    
    parsingResult.fold(identity, identity).replaceAll("keyword ", "")
    
    

