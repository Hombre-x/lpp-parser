package com.graphene
package app

import cats.effect.{IO, IOApp}

import parsley.{Failure, Success}
import core.Parser.*

object App extends IOApp.Simple:
  
  def run: IO[Unit] =
    
    val matching = char.parse("") match
      case Failure(msg) => msg
    
    for
      _   <- IO.println("Starting parsing")
      res <- IO( matching )
      _   <- IO.println(res)
      
    yield ()
