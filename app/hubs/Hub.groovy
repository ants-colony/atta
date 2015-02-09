package hubs

import core.Message
import sensors.Sensor

/**
 * Created by k33g_org on 08/02/15.
 */
interface Hub {
  // this is an observer
  void update (Message message)

  //List<Sensor> sensors

  Hub observe (Sensor sensor)
  Hub add (Sensor sensor)

  String id()
  Hub start (String publicationTopic, Closure whenStarted)
  Hub start (Closure whenStarted)

  String publicationTopic()
  Hub publicationTopic(String topic)

  Hub notifySensors(Message message)
  Hub notifySensor(String id, Message message)

}