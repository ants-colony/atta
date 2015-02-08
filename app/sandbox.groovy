import core.Broker
import core.Callbacks
import core.MQTTDevice
import core.MQTTHelper
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Created by k33g_org on 08/02/15.
 */


ExecutorService env = Executors.newCachedThreadPool()
MqttConnectOptions connectOptions = MQTTHelper.getConnectOptions()
Broker broker = new Broker(protocol:"tcp", host:"localhost", port:1883)

100.times { i ->
  Thread.sleep(10)

  env.execute((Runnable){
    MQTTDevice virtualDevice = new MQTTDevice(
        broker: broker,
        connectOptions: connectOptions
    ).initialize("VirtualDevice_$i").activateCallBacks()

    virtualDevice.connect(new Callbacks(
        success: { IMqttToken token ->
          println "VirtualDevice_$i: connection is OK"

          virtualDevice.topic("ping").content("pong").publish(new Callbacks(
              success: { IMqttToken t -> },
              failure: { IMqttToken t, Throwable e -> }
          ))

        },
        failure: { IMqttToken token, Throwable e ->
          println "VirtualDevice_$i: connection is KO"
          println e.message
        }
    ))
  })




}

MQTTDevice bobDevice = new MQTTDevice(
    broker: new Broker(protocol:"tcp", host:"localhost", port:1883),
    connectOptions: MQTTHelper.getConnectOptions(),
    id: "BobMorane",
    whenMessageArrived: { String topic, MqttMessage message ->
      println "you've got a message on topic: $topic: $message"
    }
)

MQTTDevice johnDoe = new MQTTDevice(
    broker: new Broker(protocol:"tcp", host:"localhost", port:1883),
    connectOptions: MQTTHelper.getConnectOptions(),
    whenMessageArrived: { String topic, MqttMessage message ->
      println "you've got a message on topic: $topic: $message"
    }
)

bobDevice.initialize().activateCallBacks().connect(new Callbacks(
    success: { IMqttToken token ->
      println bobDevice.id + ": connection is ok"
      bobDevice.syncSubscribe("hello")
    },
    failure: { IMqttToken token, Throwable e ->
      println bobDevice.id + ": connection is ko"
      println e.message
    }
))

johnDoe.initialize().activateCallBacks().connect(new Callbacks(
    success: { IMqttToken token ->
      println johnDoe.id + ": connection is ok"

      Thread.sleep(3000)

      johnDoe.topic("hello").content("Hi! It's JohnDoe").syncPublish()
    },
    failure: { IMqttToken token, Throwable e ->
      println johnDoe.id + ": connection is ko"
      println e.message
    }
))