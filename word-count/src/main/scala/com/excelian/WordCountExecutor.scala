package com.excelian

/**
  * Created by KBiawat on 09/02/2015.
 */
object WordCountExecutorextends extends App{

  import akka.actor.{ActorSystem, Props}
  import akka.pattern.ask
  import akka.util.Timeout
  import scala.concurrent.duration._
  import akka.dispatch.ExecutionContexts._
  import com.excelian.wc.util.{FileReaderActor, ProcessFileMsg}

  implicit val ec = global

  override def main (args: Array[String]) {
    val system = ActorSystem("System")
    val actor = system.actorOf(Props(new FileReaderActor(args(0))))
    implicit val timeout = Timeout(25)

    val future = actor ? ProcessFileMsg()

    future.map { result =>
      println("Total number of words " + result)
      system.shutdown()
    }
  }
}