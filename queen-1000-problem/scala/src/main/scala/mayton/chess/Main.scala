package mayton.chess

import akka.actor.{ActorSystem, Props}

import scala.io.StdIn

object Main {

  def main(args: Array[String]): Unit = {

    println(":: Start")

    val system = ActorSystem("testSystem")

    val generator = system.actorOf(Props[QueenGeneratorActor], "generator")

    val detector  = system.actorOf(Props[QueenDetector], "detector")

    println(s"[2] :: gen = $generator")

    generator ! Event.Start

    println(">>> Press ENTER to exit <<<")

    try StdIn.readLine()

    finally system.terminate()

    println(":: End")


  }

}