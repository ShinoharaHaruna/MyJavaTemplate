package cc.icooper.majavatemplate.core.util.token;

public interface TokenWorker {
    /**
     * 生成一个新的Token
     *
     * @return 生成的Token
     */
    String generateToken();

    /**
     * 验证Token是否有效
     *
     * @param token 要验证的Token
     * @return 如果Token有效则返回true，否则返回false
     */
    boolean validateToken(String token);

    /**
     * 移除Token
     *
     * @param token 要移除的Token
     */
    void removeToken(String token);
}