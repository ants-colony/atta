package sensors

import core.Message

import java.util.concurrent.ExecutorService

/**
 * Created by k33g_org on 08/02/15.
 */
class VirtualSensor implements Sensor, observable {
  Boolean working = false
  String sensorId = null
  ExecutorService environment

  Closure<Message> action

  @Override
  void start (Integer every) {
    this.working = true
    this.environment.execute((Runnable){
      while (this.working) {
        Thread.sleep(every)
        this.notifyObservers((Message) this.action(this))
      }
    })

  }
}



