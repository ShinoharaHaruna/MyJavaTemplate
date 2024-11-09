package cc.icooper.majavatemplate.core.util.token.impl;

import cc.icooper.majavatemplate.core.util.token.TokenStore;
import cc.icooper.majavatemplate.core.util.token.TokenWorker;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenWorker implements TokenWorker {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    TokenStore tokenStore = TokenStore.getInstance();
    @Value("${secret.jwt}")
    private String SECRET;

    @Override
    public String generateToken() {
        try {
            Map<String, Object> header = new HashMap<>();
            header.put("alg", "HS256");
            header.put("typ", "JWT");

            Map<String, Object> payload = new HashMap<>();
            payload.put("sub", "user");
            payload.put("iat", System.currentTimeMillis() / 1000);
            payload.put("exp", (System.currentTimeMillis() + 3600000) / 1000); // 1 hour expiration

            String headerJson = objectMapper.writeValueAsString(header);
            String payloadJson = objectMapper.writeValueAsString(payload);

            String encodedHeader = Base64.getUrlEncoder().withoutPadding().encodeToString(headerJson.getBytes());
            String encodedPayload = Base64.getUrlEncoder().withoutPadding().encodeToString(payloadJson.getBytes());

            String signature = createSignature(encodedHeader + "." + encodedPayload);

            String token = encodedHeader + "." + encodedPayload + "." + signature;

            tokenStore.add(token);
            return token;
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate token", e);
        }
    }

    @Override
    public boolean validateToken(String token) {
        if (!tokenStore.contains(token)) {
            return false;
        }

        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                return false;
            }

            String header = new String(Base64.getUrlDecoder().decode(parts[0]));
            String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
            String signature = parts[2];

            String expectedSignature = createSignature(parts[0] + "." + parts[1]);

            if (!expectedSignature.equals(signature)) {
                return false;
            }

            Map<String, Object> payloadMap = objectMapper.readValue(payload, Map.class);
            long exp = (Integer) payloadMap.get("exp");
            return exp > (System.currentTimeMillis() / 1000);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void removeToken(String token) {
        tokenStore.remove(token);
    }

    private String createSignature(String data) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(SECRET.getBytes(), "HmacSHA256");
        mac.init(secretKey);
        byte[] signatureBytes = mac.doFinal(data.getBytes());
        return Base64.getUrlEncoder().withoutPadding().encodeToString(signatureBytes);
    }
}
