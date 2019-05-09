package com.aision.iot.platform.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author yim
 * @description websocket配置
 * @date 2019/4/28
 */
@Configuration
public class WebsocketConfig {

        @Bean
        public ServerEndpointExporter serverEndpointExporter() {
            return new ServerEndpointExporter();
        }


}
