import core.Broker
import core.MQTTHelper
import core.Message
import hubs.MQTTHub
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import sensors.VirtualSensor
import sensors.temperature

import groovy.json.JsonOutput;


import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
/**
 * Created by k33g_org on 08/02/15.
 */

ExecutorService env = Executors.newCachedThreadPool()
MqttConnectOptions connectOptions = MQTTHelper.getConnectOptions()
Broker broker = new Broker(protocol:"tcp", host:"localhost", port:1883)

class TemperatureSensor extends VirtualSensor implements temperature {

  Closure<Message> action = { TemperatureSensor self ->
    Message msg = new Message(
        from: this.sensorId
    )
    msg.add([
        "temperature":this.getTemperature(),
        "unity": this.temperatureUnity,
        "time": new Date()
    ])

    println(JsonOutput.toJson(msg))

    return msg

  }
}


MQTTHub hub001 = new MQTTHub(
    id: "hub001",
    broker: broker,
    connectOptions: connectOptions

).publicationTopic("sensors").start({ MQTTHub self ->


  self.observe(
      new TemperatureSensor(sensorId: self.id() + "|000", environment: env).start(5000)
  )

  new TemperatureSensor(sensorId: self.id() + "|001", environment: env).addObserver(self).start(5000)

  new TemperatureSensor(sensorId: self.id() + "|002",environment: env).addObserver(self).start(5000)

  100.times { i ->

    new TemperatureSensor(
        sensorId: self.id() + "|$i",
        environment: env
    ).addObserver(self).start(5000)
  }

})

MQTTHub hub002 = new MQTTHub(
    id: "hub002",
    broker: broker,
    connectOptions: connectOptions
).publicationTopic("sensors").start({ MQTTHub self ->

  new TemperatureSensor(sensorId: self.id() + "|001", environment: env).addObserver(self).start(5000)

  new TemperatureSensor(sensorId: self.id() + "|002",environment: env).addObserver(self).start(5000)

  100.times { i ->

    new TemperatureSensor(
        sensorId: self.id() + "|$i",
        environment: env
    ).addObserver(self).start(5000)
  }
})

MQTTHub hub003 = new MQTTHub(
    id: "hub003",
    broker: broker,
    connectOptions: connectOptions
).publicationTopic("sensors").start({ MQTTHub self ->

  100.times { i ->

    new TemperatureSensor(
        sensorId: self.id() + "|$i",
        environment: env
    ).addObserver(self).start(5000)
  }

})