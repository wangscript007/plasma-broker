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

package org.deepinthink.plasma.broker.server.config.condition;

import java.util.Map;
import org.deepinthink.plasma.broker.server.config.BrokerServerProperties;
import org.deepinthink.plasma.broker.server.connector.BrokerConnectorServer.Transport;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

final class OnBrokerConnectorTransportCondition extends SpringBootCondition {

  @Override
  public ConditionOutcome getMatchOutcome(
      ConditionContext context, AnnotatedTypeMetadata metadata) {
    Map<String, Object> attributes =
        metadata.getAnnotationAttributes(ConditionalOnBrokerConnectorTransport.class.getName());
    Transport transport = (Transport) attributes.get("transport");
    return getMatchOutcome(context.getEnvironment(), transport);
  }

  private ConditionOutcome getMatchOutcome(Environment environment, Transport transport) {
    String name = transport.name();
    ConditionMessage.Builder builder =
        ConditionMessage.forCondition(ConditionalOnBrokerConnectorTransport.class);
    String transportKey = BrokerServerProperties.PREFIX + ".connector.transport";
    return environment.containsProperty(transportKey)
            && environment.getProperty(transportKey).equalsIgnoreCase(name)
        ? ConditionOutcome.match(builder.foundExactly(name))
        : ConditionOutcome.noMatch(builder.didNotFind(name).atAll());
  }
}
