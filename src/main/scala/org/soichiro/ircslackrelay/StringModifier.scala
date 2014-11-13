package org.soichiro.ircslackrelay

/**
 * Size zero space insert 
 */
object StringModifier {

  def applyAtMinimumSize(string: String, func: (String=>String), size:Int = 1) ={
    if (string.size > 1) {
      func(string)
    } else {
      string
    }
  }

  /**
   * insert size zero space to string
   * @param string
   * @return
   */
  def insertSpace(string: String):String = {
    if (string.size > 1) {
      string.head + "\u200B" + insertSpace(string.tail)
    } else {
      string
    }
  }

  /**
   * insert underscore to string
   * @param string
   * @return
   */
  def insertUnderScore(string: String):String = {
    if (string.size > 1) {
      string.head + "_" + string.tail
    } else {
      string
    }
  }

  def replaceLastTwo(string: String, re:String = "..$", replacer:String ="**"):String = {
    applyAtMinimumSize(string, s => (re.r.replaceAllIn(s, replacer)), 2)
  }

  /**
   * Sand underscores
   * @param string
   * @return
   */
  def sandUnderscores(string: String): String = {
    s"_ ${string} _"
  }
}
