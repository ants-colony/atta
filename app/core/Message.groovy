package core

/**
 * Created by k33g_org on 08/02/15.
 */
class Message {
  String from
  String to
  List<Object> contents = new LinkedList<Object>()

  def add (Object content) {
    this.contents.push(content)
  }

  def empty () {
    this.contents.clear()
  }

}
