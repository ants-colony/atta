package sensors

import core.Message
import hubs.Hub

/**
 * Created by k33g_org on 08/02/15.
 */
trait observable {
  List<Hub> observers = new LinkedList<Hub>()

  Sensor addObserver(Hub hub) {
    this.observers.add(hub)
    return (Sensor) this
  }

  def notifyObservers(Message message) {
    this.observers.each { observer ->
      message.to = observer.id()
      observer.update(message)
    }
  }
}