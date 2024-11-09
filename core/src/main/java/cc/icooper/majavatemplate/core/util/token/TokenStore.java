package cc.icooper.majavatemplate.core.util.token;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class TokenStore {
    private static final Set<String> tokens = ConcurrentHashMap.newKeySet();
    private static volatile TokenStore instance = null;

    private TokenStore() {
        // 私有构造函数，防止外部实例化
    }

    public static TokenStore getInstance() {
        if (instance == null) {
            synchronized (TokenStore.class) {
                if (instance == null) {
                    instance = new TokenStore();
                }
            }
        }
        return instance;
    }

    public void add(String token) {
        tokens.add(token);
    }

    public boolean contains(String token) {
        return tokens.contains(token);
    }

    public void remove(String token) {
        tokens.remove(token);
    }
}
