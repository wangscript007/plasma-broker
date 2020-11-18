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

import org.deepinthink.plasma.broker.server.transfer.DefaultTransferServerFactory;
import org.deepinthink.plasma.broker.server.transfer.TransferServerFactory;
import org.deepinthink.plasma.broker.server.transfer.context.TransferServerBootstrap;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = BrokerServerProperties.PREFIX + ".transfer", name = "port")
@EnableConfigurationProperties(BrokerServerProperties.class)
public class BrokerTransferServerConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public TransferServerBootstrap brokerTransferServerBootstrap(TransferServerFactory factory) {
    return new TransferServerBootstrap(factory);
  }

  @Bean
  @ConditionalOnMissingBean
  public TransferServerFactory brokerTransferServerFactory(BrokerServerProperties properties) {
    DefaultTransferServerFactory defaultServerFactory = new DefaultTransferServerFactory();
    PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
    propertyMapper.from(properties.getTransfer()::getPort).to(defaultServerFactory::setPort);
    return defaultServerFactory;
  }
}
