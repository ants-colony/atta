package core

import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttToken

/**
 * Created by k33g_org on 08/02/15.
 */
class Callbacks implements IMqttActionListener {

  Closure success
  Closure failure

  @Override
  void onSuccess(IMqttToken asyncActionToken) {
    success(asyncActionToken)
  }

  @Override
  void onFailure(IMqttToken asyncActionToken, Throwable exception) {
    failure(asyncActionToken, exception)
  }
}
