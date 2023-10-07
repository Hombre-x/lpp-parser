package com.graphene
package core

import parsley.Parsley as Parser
import parsley.character.item

object Parser:

  val char: Parser[Char] = item

end Parser
