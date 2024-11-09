package cc.icooper.majavatemplate.core.util.crypto;

public interface Crypto {
    String encrypt(String plainText);

    String decrypt(String cipherText);

    Boolean validate(String plainText, String cipherText);
}
