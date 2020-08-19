package ca.dataedu.spark.core

trait Base {

  import org.apache.log4j.{Level, Logger}

  val rootLogger: Logger = Logger.getRootLogger
  rootLogger.setLevel(Level.ERROR)

  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.spark-project").setLevel(Level.WARN)
  Logger.getLogger("org.apache.kafka").setLevel(Level.WARN)

}
