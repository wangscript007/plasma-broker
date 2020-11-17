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

package org.deepinthink.plasma.broker.server.transfer;

import java.util.Objects;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.SmartLifecycle;

public class BrokerTransferServerBootstrap
    implements ApplicationEventPublisherAware, SmartLifecycle {

  private ApplicationEventPublisher appEventPublisher;

  private final BrokerTransferServer transferServer;

  public BrokerTransferServerBootstrap(BrokerTransferServerFactory factory) {
    this.transferServer = Objects.requireNonNull(factory.createServer());
  }

  @Override
  public void setApplicationEventPublisher(ApplicationEventPublisher appEventPublisher) {
    this.appEventPublisher = appEventPublisher;
  }

  @Override
  public void start() {
    this.transferServer.start();
    this.appEventPublisher.publishEvent(
        new BrokerTransferServerInitializedEvent(this.transferServer));
  }

  @Override
  public void stop() {
    this.transferServer.stop();
  }

  @Override
  public boolean isRunning() {
    return this.transferServer.address() != null;
  }
}
