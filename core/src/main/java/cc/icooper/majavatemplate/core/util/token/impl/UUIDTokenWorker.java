package cc.icooper.majavatemplate.core.util.token.impl;

import cc.icooper.majavatemplate.core.util.token.TokenStore;
import cc.icooper.majavatemplate.core.util.token.TokenWorker;

import java.util.UUID;

public class UUIDTokenWorker implements TokenWorker {
    TokenStore tokenStore = TokenStore.getInstance();

    @Override
    public String generateToken() {
        // 生成一个唯一的Token
        String token = UUID.randomUUID().toString();
        // 将Token存储在tokenStore中
        tokenStore.add(token);
        return token;
    }

    @Override
    public boolean validateToken(String token) {
        // 检查Token是否存在于tokenStore中
        return tokenStore.contains(token);
    }

    @Override
    public void removeToken(String token) {
        // 从tokenStore中移除Token
        tokenStore.remove(token);
    }
}
