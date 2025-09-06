package mayton.oracle

import org.slf4j.LoggerFactory

object OracleSchemaAdvisor {

    var logger = LoggerFactory.getLogger(OracleSchemaAdvisor.getClass)

    def process() = {
      logger.info(":: process")
    }
}
