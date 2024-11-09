package cc.icooper.majavatemplate.core.util.crypto.impl;

import cc.icooper.majavatemplate.core.util.crypto.Crypto;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCrypt implements Crypto {
    private final BCryptPasswordEncoder passwordEncoder;

    public BCrypt() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public String encrypt(String plainText) {
        return passwordEncoder.encode(plainText);
    }

    @Override
    public String decrypt(String cipherText) {
        throw new UnsupportedOperationException("BCrypt does not support decryption.");
    }

    @Override
    public Boolean validate(String plainText, String cipherText) {
        return passwordEncoder.matches(plainText, cipherText);
    }
}