package sample.routing.server;

import org.deepinthink.plasma.broker.client.EnableBrokerClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBrokerClient
@SpringBootApplication
public class RoutingServerApplication {
  public static void main(String[] args) {
    SpringApplication.run(RoutingServerApplication.class, args);
  }
}
