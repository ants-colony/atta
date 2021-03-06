package sensors

import core.Message
import hubs.Hub

import java.util.concurrent.ExecutorService

/**
 * Created by k33g_org on 08/02/15.
 */
class VirtualSensor implements Sensor {
  Boolean working = false
  String sensorId = null
  ExecutorService environment

  Closure onMessage

  List<Hub> observers = new LinkedList<Hub>()


  Closure<Message> action

  @Override
  Sensor addObserver(Hub hub) {
    this.observers.add(hub)
    hub.add(this)
    return this
  }

  @Override
  Sensor notifyObservers(Message message) {
    this.observers.each { observer ->
      message.to = observer.id()
      observer.update(message)
    }
  }

  @Override
  Sensor start (Integer every) {
    this.working = true
    this.environment.execute((Runnable){
      while (this.working) {
        Thread.sleep(every)
        this.notifyObservers((Message) this.action(this))
      }
    })

  }

  @Override
  void update(Message message) {
    this.onMessage(this, message)
  }
}



