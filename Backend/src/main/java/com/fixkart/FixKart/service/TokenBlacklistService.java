package com.fixkart.FixKart.service;

public interface TokenBlacklistService {
    void blacklistToken(String token);
    boolean isTokenBlacklisted(String token);
}
