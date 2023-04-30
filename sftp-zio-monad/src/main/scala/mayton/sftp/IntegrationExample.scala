package mayton.sftp

import zio.*
import zio.Console.*
import zio.Unsafe

object IntegrationExample {

  val runtime = Runtime.default

  Unsafe.unsafe { implicit unsafe =>
    runtime.unsafe.run(ZIO.attempt(println("Hello World!"))).getOrThrowFiberFailure()
  }
}
