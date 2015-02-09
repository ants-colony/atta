import core.Broker
import core.MQTTHelper
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage
import workers.MQTTWorker

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Created by k33g_org on 09/02/15.
 */


ExecutorService env = Executors.newCachedThreadPool()
MqttConnectOptions connectOptions = MQTTHelper.getConnectOptions()
Broker broker = new Broker(protocol:"tcp", host:"localhost", port:1883)


new MQTTWorker(
    id:"IamTheWorker",
    broker: broker,
    connectOptions: connectOptions,
    whenMessageArrived: { String topic, MqttMessage message ->
      println "you've got a message on topic: $topic: $message"

      /* put something in database */

      /* dejsonize message*/

    }
).subscriptionTopic("sensors").start({ MQTTWorker ->
  println("started ...")
})