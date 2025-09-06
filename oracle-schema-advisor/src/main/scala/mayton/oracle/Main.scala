package mayton.oracle {

  import java.lang.System.err

  import org.apache.log4j.PropertyConfigurator
  import org.slf4j.{Logger, LoggerFactory}

  /**
    * Main
    *
    * 6-Feb-2017 - (mayton) Initial commit.
    * 26-Jun-2018 - (mayton)
    *
    * TODO: Fix: Exception: sbt.TrapExitSecurityException thrown from the
    *            UncaughtExceptionHandler in thread "run-main-0"
    */
  object Main extends App {

    def time() : Long = {
      System.currentTimeMillis
    }

    var logger = LoggerFactory.getLogger(Main.getClass)

    PropertyConfigurator.configure("log4j.properties")

    args.length match {

      case 0 => {
        err.println(s"Usage: java -jar OracleSchemaAdvisor-X.XX [jdbc-shortcut-string]\n")
        err.println(s"Where:\n")
        err.println(s"  jdbc-shortcut-string is ::= login/pwd@host:[port]/SERVICE\n\n")
        System.exit(0)
      }

      case 1 => {
        logger.info("::p1")
        val begin = time()
        OracleSchemaAdvisor.process
        val res = (time() - begin) / 1000
        err.println(s"Elapsed time : $res sec\n")
        logger.info("::p2")
        System.exit(0)
      }

      case _ => {
        println("Unknown command line arguments!")
        System.exit(0)
      }

    }
  }

}



