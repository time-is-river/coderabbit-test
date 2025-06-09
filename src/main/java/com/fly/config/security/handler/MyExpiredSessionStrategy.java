package com.fly.config.security.handler;

import com.fly.response.ResponseUtils;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * 用户被挤下线处理
 * @author
 * @date 2021/5/18 10:39
 */
@Component
public class MyExpiredSessionStrategy implements SessionInformationExpiredStrategy {
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        ResponseUtils.responseJson(event.getResponse(), ResponseUtils.response(408, "被迫下线", null));
    }
}
