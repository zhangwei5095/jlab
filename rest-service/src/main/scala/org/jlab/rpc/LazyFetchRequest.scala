package org.jlab.rpc

import akka.actor.{Actor, ActorSystem, Props}
import org.jlab.model.{Start, Message}

/**
 * Created by scorpiovn on 1/14/15.
 */
object LazyFetchRequest extends App {
  implicit val system = ActorSystem("LocalSystem")
  val requestActor = system.actorOf(Props[LazyRequestActor], name = "RequestActor") // the local actor
  requestActor ! Start
}

class LazyRequestActor extends Actor {
  // create the remote actor
  val remote = context.actorSelection("akka.tcp://simple-rpc@127.0.0.1:5150/user/LazyFetchActor")
  var counter = 0

  def receive = {
    case Start =>
      remote ! Message("Hello from the LocalActor")
    case Message(msg) =>
      println(s"LocalActor received message: '$msg'")
      if (counter < 5) {
        sender ! Message("Hello back to you")
        counter += 1
      }
  }
}