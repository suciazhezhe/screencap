package com.gzTeleader.screencap.webSocket;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.gzTeleader.screencap.utils.FileUtils;

@ServerEndpoint(value = "/ws/{type}")
@Component
public class WebSocket {
    private static final Logger logger = LoggerFactory.getLogger(WebSocket.class);

//    private static SessionStorage sessionStorage = SessionStorage.getInstance();

//    private static Map<String, String> sessionGroup = new ConcurrentHashMap<>(16);
    
    private static  List<Session> sessionList = new ArrayList<Session>();
    
//    private static  Session masterSession = null;

    /**
     * 连接建立成功调用的方法
     * @param session
     * @param type
     * @param wsGroupId
     * @throws Exception
     */
    @OnOpen
    public void onOpen(@PathParam("type") String type, Session session) {
    	sessionList.add(session);
        logger.info("websocket连接成功 sessionId:" + session.getId());
        logger.info("websocket连接成功 type:" + type);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam("type") String type, Session session) {
    	sessionList.remove(session);
        logger.info("websocket连接关闭成功 sessionId:" + session.getId());
        logger.info(type);
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message, @PathParam("type") String type,Session s) {
        try {
            logger.info(message);
            for(Session session : sessionList) {
                if (session.isOpen()) {
                    session.getBasicRemote().sendText(s.getId()+":"+message);
                    logger.info("已发送》》》》》" + message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @OnMessage
    public void onMessage(byte[] arrayBuffer, @PathParam("type") String type,Session s) {
        try {
        	logger.info(type);
        	FileUtils.saveByte(arrayBuffer, "C:\\Users\\mj\\Desktop\\test\\temporary\\"+(new Date()).getTime());
            ByteBuffer byteBuffer =ByteBuffer.wrap(arrayBuffer);
            for(Session session : sessionList) {
                if (session.isOpen()) {
					session.getBasicRemote().sendBinary(byteBuffer );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.info("websocket连接失败 sessionId:" + session.getId());
    }
}
