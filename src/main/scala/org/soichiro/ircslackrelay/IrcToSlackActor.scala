package org.soichiro.ircslackrelay

import akka.actor.{Actor, ActorLogging}
import StringModifier._
import com.sorcix.sirc.Channel

/**
 * command of start actor
 */
case class StartIrcToSlackActor()

/**
 * Actor of relay IRC to Slack
 */
class IrcToSlackActor extends Actor with ActorLogging {
  lazy val slackClient = new SlackClient

  override def receive: Receive = {
    case StartIrcToSlackActor =>
      IrcClientProvider.ircClient.connect
      log.info("IrcToSlackActor Started.")
    case m: IrcMessage =>
      log.info(s"Messaged: ${m}")
      slackClient.postMessage(createPostMessage(m), getSlackChannel(m.target), log)
    case n: IrcNotice =>
      log.info(s"Noticed: ${n}")
      slackClient.postMessage(createPostMessage(n, true), getSlackChannel(n.target), log)
    case _ =>
      log.error("Not supported command.")
  }

  private def modifiedName(name: String) = {
    Config.slack.modifier.toLowerCase match {
      case "replace" => replaceLastTwo(name)
      case _ =>  insertUnderScore(name)
    }
  }

  private def createPostMessage(command: IrcCommand, isSandUnderscores: Boolean = false): String = {
    if(isSandUnderscores) {
      sandUnderscores(s":${modifiedName(command.sender.getNick.toLowerCase)}: ${command.message}")
    } else {
      s":${modifiedName(command.sender.getNick.toLowerCase)}: ${command.message}"
    }
  }

  private def getSlackChannel(ircChannel: Channel): String = {
    Config.relays.relayMapIrcToSlack(ircChannel.getName.toLowerCase)
  }
}
