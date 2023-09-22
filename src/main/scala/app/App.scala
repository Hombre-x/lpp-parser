package com.graphene
package app

import cats.effect.{IO, IOApp}

object App extends IOApp.Simple:
  
  def run: IO[Unit] =
    
    for
      _   <- IO.println("Starting parsing")
      res <- IO.unit
      _   <- IO.println(res)
      
    yield ()
