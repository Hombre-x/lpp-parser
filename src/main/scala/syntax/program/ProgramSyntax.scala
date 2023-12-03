package com.graphene
package syntax.program

import domain.token.Program

extension (program: Program)
  def tlc: Program = program.toLowerCase
