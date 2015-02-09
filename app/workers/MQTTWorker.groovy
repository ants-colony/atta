package workers

import core.Callbacks
import core.MQTTDevice
import org.eclipse.paho.client.mqttv3.IMqttToken

/**
 * Created by k33g_org on 09/02/15.
 */
class MQTTWorker extends MQTTDevice implements Worker {
  //Subscribe to one topic (or several?)
  //do something: ie persists in database

  private String subscriptionTopic

  @Override
  String id() {
    return null
  }

  @Override
  MQTTWorker start(String subscriptionTopic, Closure whenStarted) {
    this.subscriptionTopic = subscriptionTopic
    this.initialize().activateCallBacks().connect(new Callbacks(
        success:{ IMqttToken token ->
          String workerId = this.id
          println "Worker[$workerId]: connection is OK"

          this.syncSubscribe(this.subscriptionTopic)
          whenStarted(this)

          /*
          this.subscribe(this.subscriptionTopic, new Callbacks(
              success: { IMqttToken tkn ->
                  return null
              },
              failure:{ IMqttToken tkn, Throwable e ->
                return null
              }
          ))
          */

        },
        failure:{ IMqttToken token, Throwable e ->
          String workerId = this.id
          String err = e.message
          println "Worker[$workerId]: connection is KO ($err)"
        }
    ))
    return this
  }

  @Override
  Worker start(Closure whenStarted) {
    return this.start(this.subscriptionTopic(), whenStarted)
  }

  @Override
  String subscriptionTopic() {
    return this.subscriptionTopic
  }

  @Override
  Worker subscriptionTopic(String topic) {
    this.subscriptionTopic = topic
    return this
  }
}
