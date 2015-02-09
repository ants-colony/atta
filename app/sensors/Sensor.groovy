package sensors

import core.Message
import hubs.Hub

/**
 * Created by k33g_org on 08/02/15.
 */
interface Sensor {
  //List<Hub> observers
  Sensor addObserver(Hub hub)
  void notifyObservers(Message message)
  void start (Integer every)
}