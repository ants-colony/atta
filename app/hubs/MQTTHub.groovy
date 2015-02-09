package hubs;

import core.Callbacks;
import core.MQTTDevice
import core.Message
import groovy.json.JsonOutput;
import org.eclipse.paho.client.mqttv3.IMqttToken
import sensors.Sensor;

/**
 * Created by k33g_org on 08/02/15.
 */
public class MQTTHub extends MQTTDevice implements Hub {
  /* listen to sensors */
  /* publish to one topic */
  /* a hub is an observer */

  private String publicationTopic

  public List<Sensor> sensors = new LinkedList<Sensor>()

  @Override
	void update(Message message) {

    //println(JsonOutput.toJson(message))

    this.topic(this.publicationTopic)
        .content(JsonOutput.toJson(message))
        .publish(new Callbacks(
          success: { IMqttToken t -> null }, /* TODO: to be completed */
          failure: { IMqttToken t, Throwable e -> null} /* TODO: to be completed */
    ))
	}

  @Override
  Hub observe(Sensor sensor) {
    sensor.addObserver(this)
    this.sensors.add(sensor)
    return this
  }

  @Override
  Hub add(Sensor sensor) {
    this.sensors.add(sensor)
    return this
  }
/*TODO stop observing*/

  @Override
  String id() {
    return this.id
  }

  @Override
  MQTTHub start (String publicationTopic, Closure whenStarted) { // connect and define publication Topic
    this.publicationTopic = publicationTopic
		this.initialize().activateCallBacks().connect(new Callbacks(
        success:{ IMqttToken token ->
          String hubId = this.id
          println "Hub[$hubId]: connection is OK"
          whenStarted(this)
        },
        failure:{ IMqttToken token, Throwable e ->
          String hubId = this.id
          String err = e.message
          println "Hub[$hubId]: connection is KO ($err)"
        }
		))
    return this
	}

  @Override
  Hub start(Closure whenStarted) {
    return this.start(this.publicationTopic(), whenStarted)
  }

  @Override
  String publicationTopic() {
    return this.publicationTopic
  }

  @Override
  Hub publicationTopic(String topic) {
    this.publicationTopic = topic
    return this
  }
}
