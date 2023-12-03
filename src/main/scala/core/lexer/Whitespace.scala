package com.graphene
package core.lexer

import domain.parser.Parser

import parsley.Parsley
import parsley.Parsley.atomic
import parsley.character.{endOfLine, item, string, whitespace}
import parsley.combinator.{manyUntil, skipMany}
import parsley.errors.combinator.ErrorMethods
import core.lexer.Lexer.symbol

object Whitespace:
  
  private val lineComment      = symbol("//") *> manyUntil(item, endOfLine)
  private val multilineComment = symbol("/*") *> manyUntil(item, string("*/"))
  private val comment = lineComment <|> multilineComment
  def skipWhitespace: Parser[Unit] = skipMany(whitespace <|> comment).hide
  
  
 
end Whitespace
