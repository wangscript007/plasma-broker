/*
 * Copyright 2020-present Tingyan Shen. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.deepinthink.plasma.broker.server.config;

import org.deepinthink.plasma.broker.server.connector.BrokerConnectorServer.Transport;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = BrokerServerProperties.PREFIX)
public class BrokerServerProperties {
  public static final String PREFIX = "plasma.broker.server";

  private final Connector connector = new Connector();
  private final Transfer transfer = new Transfer();

  public Connector getConnector() {
    return connector;
  }

  public Transfer getTransfer() {
    return transfer;
  }

  public static class Connector {
    private int port;
    private Transport transport;

    public int getPort() {
      return port;
    }

    public void setPort(int port) {
      this.port = port;
    }

    public Transport getTransport() {
      return transport;
    }

    public void setTransport(Transport transport) {
      this.transport = transport;
    }
  }

  public static class Transfer {
    private int port;

    public int getPort() {
      return port;
    }

    public void setPort(int port) {
      this.port = port;
    }
  }
}
