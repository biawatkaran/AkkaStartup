package com.excelian

import java.io.File


/**
  * Created by KBiawat on 09/02/2015.
 */
object WordCountExecutor extends App{

  import akka.actor.{ActorSystem, Props}
  import akka.pattern.ask
  import akka.util.Timeout
  import scala.concurrent.duration._
  import akka.dispatch.ExecutionContexts._
  import com.excelian.wc.util.{FileReaderActor, ProcessFileMsg}
  import java.util.concurrent.TimeUnit

  override def main (args: Array[String]) {
    implicit val ec = global
    implicit val timeout = Timeout(25, TimeUnit.SECONDS)

    val system = ActorSystem("FileReaderSystem")
    val filePath = getClass.getResource("/test.txt").getFile

    val actor = system.actorOf(Props(new FileReaderActor(filePath)))
    val future = actor ? ProcessFileMsg()

    future.map { result =>
      println("Total number of words " + result)
      system.shutdown()
    }
  }
}