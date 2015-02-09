package com.excelian.wc.util

import akka.actor.Actor


/**
 * Created by KBiawat on 06/02/2015.
 */


case class ProcessLine(string: String)
case class Words(words : Integer)

class LineReaderActor extends Actor{
  def receive = {
    case ProcessLine (string) => {
      val words = string.split(" ").length
      sender ! Words(words)
    }

    case _ => println("Error: unknown message")
  }

}
