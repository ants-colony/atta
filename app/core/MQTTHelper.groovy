package core

import org.eclipse.paho.client.mqttv3.MqttConnectOptions

/**
 * Created by k33g_org on 08/02/15.
 * http://www.eclipse.org/paho/files/javadoc/org/eclipse/paho/client/mqttv3/MqttConnectOptions.html
 */
class MQTTHelper {

  static MqttConnectOptions getConnectOptions (Boolean cleanSession=true) {
    MqttConnectOptions connOpts = new MqttConnectOptions()
    connOpts.setCleanSession(cleanSession)
    return connOpts
  }

}
