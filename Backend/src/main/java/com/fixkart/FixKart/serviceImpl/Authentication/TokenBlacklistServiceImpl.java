package com.fixkart.FixKart.serviceImpl.Authentication;

import com.fixkart.FixKart.service.Authtentication.TokenBlacklistService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TokenBlacklistServiceImpl implements TokenBlacklistService {

    private final Set<String> blacklist = new HashSet<>();

    @Override
    public void blacklistToken(String token) {
        blacklist.add(token);
    }

    @Override
    public boolean isTokenBlacklisted(String token) {
        return blacklist.contains(token);
    }
}
