package mayton.chess

import akka.actor.{Actor, ActorLogging}
import akka.event.Logging

class QueenDetector extends Actor with ActorLogging {


  override def receive: Receive = {
    case "x" => log.info("QueenDetector::Received")
  }



}
