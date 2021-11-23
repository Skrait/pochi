package com.jg.pochi.shiro;

import com.jg.pochi.constant.CoreConstant;
import com.jg.pochi.utils.StringUtils;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.UUID;

/**
 * Author Peekaboo
 *
 * @Date 2021/11/23 13:54
 */
@Configuration
public class TokenWebSessionManager extends DefaultWebSessionManager {

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        //从请求头获取Token
        String token = WebUtils.toHttp(request).getHeader(CoreConstant.TOKEN_HEADER);
        if(StringUtils.isNoneBlank(token)){
            return token;
        }
        return UUID.randomUUID().toString();
    }
}
