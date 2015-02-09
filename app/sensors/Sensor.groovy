package sensors

import core.Message
import hubs.Hub

/**
 * Created by k33g_org on 08/02/15.
 */
interface Sensor {
  //List<Hub> observers
  Sensor addObserver(Hub hub)
  Sensor notifyObservers(Message message)
  Sensor start (Integer every)

  // this is an observer
  void update (Message message)
}