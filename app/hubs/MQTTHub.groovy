package hubs;

import core.Callbacks;
import core.MQTTDevice
import core.Message
import groovy.json.JsonOutput;
import org.eclipse.paho.client.mqttv3.IMqttToken;

/**
 * Created by k33g_org on 08/02/15.
 */
public class MQTTHub extends MQTTDevice implements Hub {
  /* listen to sensors */
  /* publish to one topic */

  private String publicationTopic


  @Override
	public void update(Message message) {

    println(JsonOutput.toJson(message))

    this.topic(this.publicationTopic)
        .content(JsonOutput.toJson(message))
        .publish(new Callbacks(
          success: { IMqttToken t -> null }, /* TODO: to be completed */
          failure: { IMqttToken t, Throwable e -> null} /* TODO: to be completed */
    ))
	}

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
