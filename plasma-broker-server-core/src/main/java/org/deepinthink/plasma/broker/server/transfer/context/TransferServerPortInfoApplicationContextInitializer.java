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

package org.deepinthink.plasma.broker.server.transfer.context;

import java.util.HashMap;
import java.util.Map;
import org.deepinthink.plasma.broker.server.connector.context.TransferServerInitializedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

public class TransferServerPortInfoApplicationContextInitializer
    implements ApplicationContextInitializer<ConfigurableApplicationContext>,
        ApplicationListener<TransferServerInitializedEvent> {
  private static final String PROPERTY_NAME = "local.plasma.broker.transfer.server.port";
  private ConfigurableApplicationContext applicationContext;

  @Override
  public void initialize(ConfigurableApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
    applicationContext.addApplicationListener(this);
  }

  @Override
  public void onApplicationEvent(TransferServerInitializedEvent event) {
    if (event.getServer().address() != null) {
      setPortProperty(this.applicationContext, event.getServer().address().getPort());
    }
  }

  private void setPortProperty(ApplicationContext context, int port) {
    if (context instanceof ConfigurableApplicationContext) {
      setPortProperty(((ConfigurableApplicationContext) context).getEnvironment(), port);
    }
    if (context.getParent() != null) {
      setPortProperty(context.getParent(), port);
    }
  }

  private void setPortProperty(ConfigurableEnvironment environment, int port) {
    MutablePropertySources sources = environment.getPropertySources();
    PropertySource<?> source = sources.get("server.ports");
    if (source == null) {
      source = new MapPropertySource("server.ports", new HashMap<>());
      sources.addFirst(source);
    }
    setPortProperty(port, source);
  }

  @SuppressWarnings("unchecked")
  private void setPortProperty(int port, PropertySource<?> source) {
    ((Map<String, Object>) source.getSource()).put(PROPERTY_NAME, port);
  }
}
