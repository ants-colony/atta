package sensors

/**
 * Created by k33g_org on 09/02/15.
 * Unity: %
 */
trait humidity {
  Integer minHumidity = 0
  Integer maxHumidity = 100
  String humidityUnity = "%"


  Integer getHumidityLevel() {
    Random random = new Random()
    return random.nextInt(maxHumidity-minHumidity+1)+maxHumidity
  }
}