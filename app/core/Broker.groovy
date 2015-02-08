package core

/**
 * Created by k33g_org on 08/02/15.
 */
class Broker {
  String protocol
  String host
  Integer port

  String url () { return "$protocol://$host:$port" }
}
