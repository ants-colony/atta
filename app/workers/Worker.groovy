package workers
/**
 * Created by k33g_org on 09/02/15.
 */
interface Worker {
  String id()
  Worker start (String subscriptionTopic, Closure whenStarted)
  Worker start (Closure whenStarted)

  String subscriptionTopic()
  Worker subscriptionTopic(String topic)
}