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

import org.deepinthink.plasma.broker.server.config.condition.ConditionalOnConnectorTransport;
import org.deepinthink.plasma.broker.server.connector.ConnectorServer.Transport;
import org.deepinthink.plasma.broker.server.connector.ConnectorServerFactory;
import org.deepinthink.plasma.broker.server.connector.context.ConnectorServerBootstrap;
import org.deepinthink.plasma.broker.server.connector.tcp.TcpConnectorServerFactory;
import org.deepinthink.plasma.broker.server.connector.websocket.WebSocketConnectorServerFactory;
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
  public ConnectorServerBootstrap connectorServerBootstrap(ConnectorServerFactory factory) {
    return new ConnectorServerBootstrap(factory);
  }

  @Bean
  @ConditionalOnConnectorTransport(transport = Transport.TCP)
  @ConditionalOnMissingBean
  public ConnectorServerFactory tcpConnectorServerFactory(BrokerServerProperties properties) {
    TcpConnectorServerFactory factory = new TcpConnectorServerFactory();
    PropertyMapper mapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
    mapper.from(properties.getConnector()::getPort).to(factory::setPort);
    return factory;
  }

  @Bean
  @ConditionalOnConnectorTransport(transport = Transport.WEBSOCKET)
  @ConditionalOnMissingBean
  public ConnectorServerFactory webSocketConnectorServerFactory(BrokerServerProperties properties) {
    WebSocketConnectorServerFactory factory = new WebSocketConnectorServerFactory();
    PropertyMapper mapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
    mapper.from(properties.getTransfer()::getPort).to(factory::setPort);
    return factory;
  }
}
