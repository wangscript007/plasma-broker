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

package org.deepinthink.plasma.broker.server.connector.context;

import java.util.Objects;
import org.deepinthink.plasma.broker.server.connector.ConnectorServer;
import org.deepinthink.plasma.broker.server.connector.ConnectorServerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.SmartLifecycle;

public class ConnectorServerBootstrap implements ApplicationEventPublisherAware, SmartLifecycle {
  private final ConnectorServer connectorServer;
  private ApplicationEventPublisher eventPublisher;

  public ConnectorServerBootstrap(ConnectorServerFactory factory) {
    this.connectorServer = Objects.requireNonNull(factory.createServer());
  }

  @Override
  public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
    this.eventPublisher = eventPublisher;
  }

  @Override
  public void start() {
    this.connectorServer.start();
    this.eventPublisher.publishEvent(new TransferServerInitializedEvent(this.connectorServer));
  }

  @Override
  public void stop() {
    this.connectorServer.stop();
  }

  @Override
  public boolean isRunning() {
    return this.connectorServer.address() != null;
  }
}
