package com.aision.iot.platform.websocket.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author yim
 * @description websocket消息推送
 * @date 2019/4/28
 */
@ServerEndpoint("/websocket/{sid}")
@Component
public class WebsocketUtils {
    private static Logger log = LoggerFactory.getLogger(WebsocketUtils.class);

    /**
     * 当前在线连接数
     */
    private static int onlineCount = 0;
    /**
     * 存放每个客户端对应的MyWebSocket对象
     */
    private static CopyOnWriteArraySet<WebsocketUtils> webSocketSet = new CopyOnWriteArraySet<>();
    /**
     * 客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    /**
     * 接收sid
     */
    private String sid = "";

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        this.session = session;
        //加入set中
        webSocketSet.add(this);
        //在线数加1
        addOnlineCount();
        log.info("有新窗口开始监听:{},当前连接数为", sid, getOnlineCount());
        this.sid = sid;
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        //从set中删除
        webSocketSet.remove(this);
        //连接数减1
        subOnlineCount();
        log.info("有一连接关闭！当前在线连接数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 客户端的连接
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来自窗口" + sid + "的信息:" + message);
        //群发消息
        for (WebsocketUtils item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 连接错误
     * @param session 客户端的连接
     * @param error 错误信息
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }


    /**
     * 服务器主动推送
     * @param message 消息内容
     * @throws IOException 异常
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 发送消息至指定的连接上，不指定连接则群发
     * @param message 信息内容
     * @param sid 连接
     */
    public static void sendInfo(String message, String sid) {
        log.info("推送消息到窗口:{}，推送内容:", sid, message);
        for (WebsocketUtils item : webSocketSet) {
            try {
                //这里可以设定只推送给这个sid的，为null则全部推送
                if (sid == null) {
                    item.sendMessage(message);
                } else if (item.sid.equals(sid)) {
                    item.sendMessage(message);
                }
            } catch (IOException e) {
                continue;
            }
        }
    }

    /**
     * 获取连接数
     * @return
     */
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    /**
     * 增加连接计数
     */
    public static synchronized void addOnlineCount() {
        WebsocketUtils.onlineCount++;
    }

    /**
     * 减少连接计数
     */
    public static synchronized void subOnlineCount() {
        WebsocketUtils.onlineCount--;
    }


}
