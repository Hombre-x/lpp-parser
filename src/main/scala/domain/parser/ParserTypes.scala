package com.graphene
package domain.parser

import parsley.Parsley

/**
 * Evolution of the type:
 * 1. String => (String, A)
 * 2. String => Either[String, (String, A)]
 * 3. String => EitherT[Eval, ParseError, (String, A)]
 * 4. StateT[EitherT[Eval, ParseError, *], String, A]
 * 5.
 */

type Parser[A] = Parsley[A]




    
  
    


