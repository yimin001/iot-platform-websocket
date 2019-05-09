package com.aision.iot.platform.websocket.api;

import com.aision.iot.platform.websocket.websocket.WebsocketUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yim
 * @description websocket发送消息API接口
 * @date 2019/5/7
 */
@RestController
@RequestMapping("/websocketApi")
public class WebsocketApi {

    private static Logger logger = LoggerFactory.getLogger(WebsocketApi.class);

    /**
     * 发送websocket消息
     * @param msg 消息内容
     * @param target 连接目标
     */
    @GetMapping("/sendMessage")
    public void sendMessage(String msg, String target){
        logger.info("发送websocket消息：{}, 发送到{}的websocket连接", msg, target);
        WebsocketUtils.sendInfo(msg,target);
    }
}
