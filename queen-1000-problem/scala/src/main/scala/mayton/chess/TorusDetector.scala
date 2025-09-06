package mayton.chess

import akka.actor.{Actor, ActorLogging}
import akka.event.Logging

class TorusDetector extends Actor with ActorLogging {

  val api : QueenApi = new QueenApi

  override def preStart(): Unit = {
    log.info("::preStart()")
  }

  override def receive: Receive = {
    case "x" =>
      if (api.isTorusSolution(null)) {
        log.info(":: ")
      }
      null
  }


}
