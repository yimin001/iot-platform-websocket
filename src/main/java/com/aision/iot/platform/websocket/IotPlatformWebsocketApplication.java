package com.aision.iot.platform.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={"com.aision.iot.platform.parent.**","com.aision.iot.platform.websocket"})
@ServletComponentScan
public class IotPlatformWebsocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(IotPlatformWebsocketApplication.class, args);
    }

}
