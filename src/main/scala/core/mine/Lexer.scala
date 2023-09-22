package com.graphene
package core.mine

import domain.token.Position

object Lexer:
  
  def trackWhitespace(init: Position, string: String): Position =
    string.foldLeft(init): (pos, current) =>
      current match
        case '\n' => Position(pos.line + 1, 0)
        case '\t' => Position(pos.line, pos.column + 4)
        case ' '  => Position(pos.line, pos.column + 1)
        case _    => Position(pos.line, pos.column + 1)
        
        
end Lexer
