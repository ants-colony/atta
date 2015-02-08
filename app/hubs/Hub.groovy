package hubs

import core.Message

/**
 * Created by k33g_org on 08/02/15.
 */
interface Hub {
  // this is an observer
  void update (Message message)
  String id()
  Hub start (String publicationTopic, Closure whenStarted)
  Hub start (Closure whenStarted)

  String publicationTopic()
  Hub publicationTopic(String topic)

}