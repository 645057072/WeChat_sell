package com.hlx.sell.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@Slf4j
@ServerEndpoint("/webSocket")
public class WebSocket {

    private Session session;

    private static CopyOnWriteArraySet<WebSocket> websocketset=new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session){
        this.session=session;

        websocketset.add(this);
        log.info("[wesocket信息]建立连接：总数={}",websocketset.size());
    }
    @OnClose
    public void onClose(Session session){
        this.session=session;
        websocketset.add(this);
        log.info("[wesocket信息]断开连接:总数={}",websocketset.size());
    }
    @OnMessage
    public void onMessage(String message){
        log.info("[websocket信息]收到客户端发来的消息，message={}",message);
    }

   public void senndMessage(String message){
        for (WebSocket webSocket:websocketset){
            log.info("[wesocket信息]广播信息，message={}",message);
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
   }


}
