package com.leopold.store.service;

public interface SessionManager {
    String getSessionId();
    String getUserId();
    void setUserId(String userId);
    void invalidateSession();
}