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

import org.deepinthink.plasma.broker.server.config.condition.ConditionalOnBrokerConnectorTransport;
import org.deepinthink.plasma.broker.server.connector.BrokerConnectorServer.Transport;
import org.deepinthink.plasma.broker.server.connector.BrokerConnectorServerBootstrap;
import org.deepinthink.plasma.broker.server.connector.BrokerConnectorServerFactory;
import org.deepinthink.plasma.broker.server.connector.tcp.BrokerConnectorTcpServerFactory;
import org.deepinthink.plasma.broker.server.connector.websocket.BrokerConnectorWebSocketServerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = BrokerServerProperties.PREFIX + ".connector", name = "port")
@EnableConfigurationProperties(BrokerServerProperties.class)
public class BrokerConnectorServerConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public BrokerConnectorServerBootstrap brokerConnectorServerBootstrap(
      BrokerConnectorServerFactory factory) {
    return new BrokerConnectorServerBootstrap(factory);
  }

  @Bean
  @ConditionalOnBrokerConnectorTransport(transport = Transport.TCP)
  @ConditionalOnMissingBean
  public BrokerConnectorServerFactory brokerConnectorTcpServerFactory(
      BrokerServerProperties properties) {
    BrokerConnectorTcpServerFactory tcpServerFactory = new BrokerConnectorTcpServerFactory();
    PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
    propertyMapper.from(properties.getConnector()::getPort).to(tcpServerFactory::setPort);
    return tcpServerFactory;
  }

  @Bean
  @ConditionalOnBrokerConnectorTransport(transport = Transport.WEBSOCKET)
  @ConditionalOnMissingBean
  public BrokerConnectorServerFactory brokerConnectorWebSocketServerFactory(
      BrokerServerProperties properties) {
    BrokerConnectorWebSocketServerFactory webSocketServerFactory =
        new BrokerConnectorWebSocketServerFactory();
    PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
    propertyMapper.from(properties.getTransfer()::getPort).to(webSocketServerFactory::setPort);
    return webSocketServerFactory;
  }
}
