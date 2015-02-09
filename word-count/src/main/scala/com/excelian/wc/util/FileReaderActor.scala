package com.excelian.wc.util

import akka.actor.{Actor, ActorRef, Props}

/**
  * Created by KBiawat on 06/02/2015.
 */

case class ProcessFileMsg()

class FileReaderActor(fileName : String) extends  Actor{

  private var running = false
  private var fileSender : Option[ActorRef] = None
  private var totalLine = 0
  private var result = 0
  private var linesProcessed = 0

  def receive = {

    case ProcessFileMsg => {
      if(running){
        println("Warning : duplicate start message")
      } else {
        running = true
        fileSender = Some(sender)
        import scala.io.Source._
        fromFile(fileName).getLines().foreach { (line : String) =>
          context.actorOf(Props[LineReaderActor]) ! ProcessLine(line)
          totalLine += 1
        }
      }
    }

    case Words(words) => {
      result += words
      linesProcessed += 1

      if(linesProcessed == totalLine) {
        fileSender.map(_ ! result)
      }
    }

    case _ => println("message not recognized")

  }

}
