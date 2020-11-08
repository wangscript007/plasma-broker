package sample.broker.server;

import org.deepinthink.plasma.broker.server.EnableBrokerServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBrokerServer
@SpringBootApplication
public class BrokerServerApplication {
  public static void main(String[] args) {
    SpringApplication.run(BrokerServerApplication.class, args);
  }
}
