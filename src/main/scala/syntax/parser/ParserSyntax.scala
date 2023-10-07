package com.graphene
package syntax.parser

import domain.parser.*

extension [A](parser: Parser[A])
  def parse(input: String): Either[ParserError, (String, A)] =
    parser.run(input).value.value
