package com.leopold.store.service.impl;

import com.leopold.store.service.SessionManager;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Service
public class SessionManagerImpl implements SessionManager {

    private HttpSession getSession() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true); // create if it does not exist
    }

    @Override
    public String getSessionId() {
        return getSession().getId();
    }

    @Override
    public String getUserId() {
        return (String) getSession().getAttribute("userId");
    }

    @Override
    public void setUserId(String userId) {
        getSession().setAttribute("userId", userId);
    }

    @Override
    public void invalidateSession() {
        getSession().invalidate();
    }
}