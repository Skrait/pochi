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

    //注入RedisTemplate用来操作Redis缓存,如添加、删除String的缓存
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
        /**
         * ValidatingSession是确定session是否有效和验证自身是否有效
         * 验证通常是确定上次访问或修改的时间,并确定该时间是否长于允许持续时间
         */
        if (session instanceof ValidatingSession){
            ValidatingSession validatingSession = (ValidatingSession) session;
            if(validatingSession.isValid()){
                redisTemplate.boundValueOps(session.getId()).set(session);
            }else {
                //校验失败,说明未登录或者登录失效,则删除
                redisTemplate.delete(session.getId());
            }
        }else {
            //到这里一般来讲就是SimpleSession类型了,我们一样存进Redis缓存
            redisTemplate.boundValueOps(session.getId()).set(session);
        }
    }

    @Override
    protected void doDelete(Session session) {
        redisTemplate.delete(session.getId());
    }
}

