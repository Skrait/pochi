package com.jg.pochi.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * Author Peekaboo
 * 重写存取sessionId的方法
 * @Date 2021/11/23 15:46
 */
@Component
public class SessionDaoConfig extends EnterpriseCacheSessionDAO {

    @Resource
    private RedisTemplate<Serializable, Session> redisTemplate;

    @Override
    protected Serializable doCreate(Session session) {
        // 获取SessionId
        Serializable SessionId = this.generateSessionId(session);
        SimpleSession simpleSession = (SimpleSession) session;
        simpleSession.setId(SessionId);
        return simpleSession;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        //从redis中读取sessionId(即通过BoundValueOperations从Redis中获取缓存值)
        return redisTemplate.boundValueOps(sessionId).get();
    }

    @Override
    protected void doUpdate(Session session) {
        if (session instanceof ValidatingSession)
        super.doUpdate(session);
    }

    @Override
    protected void doDelete(Session session) {
        super.doDelete(session);
    }
}

