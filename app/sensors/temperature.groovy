package sensors

/**
 * Created by k33g_org on 08/02/15.
 */
trait temperature {
  //private Integer currentTemperature = 0

  private Integer min = -100
  private Integer max = 100

  def min(Integer value) {
    min = value
    return this
  }

  def max(Integer value) {
    max = value
    return this
  }

  def Integer getTemperature() {
    Random random = new Random()
    return random.nextInt(max-min+1)+max
  }

}