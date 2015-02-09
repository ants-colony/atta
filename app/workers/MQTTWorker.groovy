package workers

/**
 * Created by k33g_org on 09/02/15.
 */
class MQTTWorker implements Worker {
  //Subscribe to one topic (or several?)
  //do something: ie persists in database
  @Override
  String id() {
    return null
  }

  @Override
  Worker start(String subscriptionTopic, Closure whenStarted) {
    return null
  }

  @Override
  Worker start(Closure whenStarted) {
    return null
  }

  @Override
  String subscriptionTopic() {
    return null
  }

  @Override
  Worker subscriptionTopic(String topic) {
    return null
  }
}
