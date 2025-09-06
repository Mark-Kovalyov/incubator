package mayton.chess

import akka.actor.{Actor, Props}

import akka.event.Logging

case object Ping
case object Pong

object Event {
  case object Start
}

class QueenGeneratorActor extends Actor{

  val logger = Logging(context.system, this)

  override def receive : Receive = {
    case Event.Start =>
      val detector = context.actorOf(Props.empty, "detector")
      for(i <- 0 to 100) {
        detector ! i
      }
      logger.info("Start received")
  }

}
