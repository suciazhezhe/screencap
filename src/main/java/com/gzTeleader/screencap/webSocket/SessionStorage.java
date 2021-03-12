package com.gzTeleader.screencap.webSocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

public class SessionStorage {
    private Map<String, Map<String, Session>> sessionStore = new ConcurrentHashMap<>();

    private static volatile SessionStorage storage;

    private static final Logger logger = LoggerFactory.getLogger(SessionStorage.class);

    private SessionStorage() {
    }

    public static synchronized SessionStorage getInstance() {
        if(storage == null) {
            synchronized(SessionStorage.class) {
                if(storage == null) {
                    storage = new SessionStorage();
                }
            }
        }
        return storage;
    }

    /**
     * 将ws连接会话分组保存
     * @author  feiyang
     * @param session
     * @param wsGroupId
     * @param type
     * @return  void
     * @date    2019/9/19
     * @throws
     */
    public void putSession(Session session, String wsGroupId, String type) {
        if(session != null) {
            Map<String, Session> sessionGroup = this.sessionStore.get(wsGroupId);
            if (sessionGroup == null) {
               sessionGroup = new ConcurrentHashMap<>(16);
               this.sessionStore.put(wsGroupId, sessionGroup);
            }
            sessionGroup.put(type, session);
        }
    }

    /**
     * ws连接关闭时删除会话
     * @author  feiyang
     * @param wsGroupId
     * @param type
     * @return  void
     * @date    2019/9/19
     * @throws
     */
    public void removeSession(String wsGroupId, String type) {
        Map<String, Session> sessionGroup = sessionStore.get(wsGroupId);
        sessionGroup.remove(type);
        if (sessionGroup.isEmpty()) {
            this.sessionStore.remove(wsGroupId);
        }
    }

    /**
     * 获取指定ws连接会话
     * @author  feiyang
     * @param wsGroupId
     * @param type
     * @return  javax.websocket.Session
     * @date    2019/9/19
     * @throws
     */
    public Session getSession(String wsGroupId, String type) {
        Map<String, Session> sessionGroup = this.sessionStore.get(wsGroupId);
        return sessionGroup.get(type);
    }

    /**
     * 获取指定ws连接会话组全部session
     * @author  feiyang
     * @param wsGroupId
     * @return  java.util.List<javax.websocket.Session>
     * @date    2019/9/19
     * @throws
     */
    public List<Session> getSessions(String wsGroupId) {
        Session[] sessions = new Session[]{};
        Map<String, Session> sessionGroup = this.sessionStore.get(wsGroupId);
        return Arrays.asList(sessionGroup.values().toArray(sessions));
    }

    /**
     * 获取全部的会话
     * @author  feiyang
     * @param
     * @return  java.util.List<javax.websocket.Session>
     * @date    2019/9/19
     * @throws
     */
    public List<Session> getAllSession() {
        List<Session> sessionList = new ArrayList<>();
        for (Map<String, Session> sessionGroup : sessionStore.values()) {
            Session[] sessions = new Session[]{};
            sessionList.addAll(Arrays.asList(sessionGroup.values().toArray(sessions)));
        }
        return sessionList;
    }

    /**
     * 针对单个会话发送文本消息
     * @author  feiyang
     * @param wsGroupId
     * @param type
     * @param text
     * @return  void
     * @date    2019/9/19
     * @throws
     */
    @Async
    public void sendTextSingle(String wsGroupId, String type, String text){
        try {
            Session session = getSession(wsGroupId, type);
            if (session.isOpen()) {
                session.getBasicRemote().sendText(text);
                logger.info("已发送》》》》》" + text);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 针对会话组发送文本信息
     * @author  feiyang
     * @param wsGroupId
     * @param text
     * @return  void
     * @date    2019/9/19
     * @throws
     */
    @Async
    public void  sendTextGroup(String wsGroupId, String text) {
        try {
            List<Session> sessions = getSessions(wsGroupId);
                for(Session session : sessions) {
                    if (session.isOpen()) {
                        session.getBasicRemote().sendText(text);
                        logger.info("已发送》》》》》" + text);
                    }
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 针对全部会话发送消息
     * @author  feiyang
     * @param text
     * @return  void
     * @date    2019/9/19
     * @throws
     */
    public void sendTextAll(String text) {
        try {
            List<Session> sessions = getAllSession();
            for(Session session : sessions) {
                if (session.isOpen()) {
                    session.getBasicRemote().sendText(text);
                    logger.info("已发送》》》》》" + text);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
