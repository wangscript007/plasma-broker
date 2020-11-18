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

package org.deepinthink.plasma.broker.server.connector.websocket;

import org.deepinthink.plasma.broker.server.connector.ConfigurableConnectorServerFactory;
import org.deepinthink.plasma.broker.server.connector.ConnectorServer;
import org.deepinthink.plasma.broker.server.connector.ConnectorServerFactory;

public class WebSocketConnectorServerFactory
    implements ConnectorServerFactory, ConfigurableConnectorServerFactory {

  private int port;

  @Override
  public ConnectorServer createServer() {
    return new WebSocketConnectorServer();
  }

  @Override
  public void setPort(int port) {
    this.port = port;
  }
}
