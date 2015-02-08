package core
import org.eclipse.paho.client.mqttv3.MqttAsyncClient
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence

/**
 * Created by k33g_org on 08/02/15.
 */
class MQTTDevice {
  String id
  Broker broker
  Integer qos
  MqttConnectOptions connectOptions
  Closure whenMessageArrived = { topic, message -> null }
  Closure whenDeliveryComplete = { token -> null }
  Closure whenConnectionLost = { error -> null }

  private MqttAsyncClient client
  private String content
  private String topic

  MQTTDevice content (String content) {
    this.content = content
    return this
  }

  String content () {
    return this.content
  }

  MQTTDevice topic (String topic) {
    this.topic = topic
    return this
  }

  String topic () {
    return this.topic
  }

  private MqttAsyncClient getMqttClient () {
    MemoryPersistence persistence = new MemoryPersistence()
    return new MqttAsyncClient(this.broker.url(), this.id, persistence)
  }

  MQTTDevice initialize (id=null) {
    this.qos = this.qos ?: 0
    this.id = this.id ?: MqttClient.generateClientId()
    this.client = this.getMqttClient()
    return this
  }

  MQTTDevice activateCallBacks () {
    this.client.setCallback(new MQTTCallback(
        whenConnectionLost: this.whenConnectionLost,
        whenMessageArrived: this.whenMessageArrived,
        whenDeliveryComplete: this.whenDeliveryComplete
    ))
    return this
  }

  MQTTDevice activateCallBacks (MQTTCallback mqttCallback) {
    this.client.setCallback(mqttCallback)
    return this
  }

  MQTTDevice syncConnect () {
    String url = this.broker.url
    println("[$id]:Connecting to broker: $url")
    this.client.connect(this.connectOptions)
    println("[$id] is connected")
    return this

  }

  MQTTDevice connect (Callbacks actionListenerCbk) {
    String url = this.broker.url()
    println("[$id]:Connecting to broker: $url")
    this.client.connect(this.connectOptions, null,actionListenerCbk)
    return this
  }

  MQTTDevice syncSubscribe (String topicFilter) {
    this.client.subscribe(topicFilter, this.qos as int)
    return this
  }

  MQTTDevice subscribe (String topicFilter, Callbacks actionListenerCbk) {
    this.client.subscribe(topicFilter, this.qos as int, null as Object, actionListenerCbk)
    return this
  }

  MQTTDevice syncUnsubscribe (String topicFilter) {
    this.client.unsubscribe(topicFilter)
    return this
  }

  MQTTDevice unsubscribe (String topicFilter, Callbacks actionListenerCbk) {
    this.client.unsubscribe(topicFilter, null as Object, actionListenerCbk)
    return this
  }

  MQTTDevice syncPublish () {
    MqttMessage message  = new MqttMessage(this.content().getBytes())
    message.setQos(this.qos)
    this.client.publish(this.topic(), message)
    return this
  }

  MQTTDevice publish (Callbacks actionListenerCbk) {
    MqttMessage message  = new MqttMessage(this.content().getBytes())
    message.setQos(this.qos)
    this.client.publish(this.topic(), message, null as Object, actionListenerCbk)
    return this
  }

  MQTTDevice syncDisconnect () {
    this.client.disconnect()
    return this
  }

  MQTTDevice disconnect (Callbacks actionListenerCbk) {
    this.client.disconnect(null as Object, actionListenerCbk)
    return this
  }


}
