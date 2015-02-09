package sensors

/**
 * Created by k33g_org on 08/02/15.
 */
trait temperature {

  Integer minTemperature = -100
  Integer maxTemperature = 100
  String temperatureUnity = "°C"

  Integer getTemperature() {
    Random random = new Random()
    return random.nextInt(maxTemperature-minTemperature+1)+maxTemperature
  }

}