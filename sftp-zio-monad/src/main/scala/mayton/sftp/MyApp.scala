package mayton.sftp

import zio.*
import zio.Console.*

import java.io.IOException

object MyApp extends ZIOAppDefault {

  def run: ZIO[Any, IOException, Unit] = myAppLogic

  val myAppLogic =
    for {
      _    <- printLine("Hello! What is your name?")
      name <- readLine
      _    <- printLine(s"Hello, ${name}, welcome to ZIO!")
    } yield ()
}