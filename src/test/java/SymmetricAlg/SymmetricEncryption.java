package SymmetricAlg;

import ffffffff0x.beryenigma.App.Controller.Encryption.Coding.BaseEncoding.Coding_Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.Security;

/**
 * @author: RyuZUSUNC
 * @create: 2021/12/7 19:44
 **/
public class SymmetricEncryption {
    public static void main(String[] args) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        // 原文:
        String message = "Hello, world!";
        System.out.println("Message: " + message);
        // 256位密钥 = 32 bytes Key:
        byte[] key = "1234567890abcdef1234567890abcdef".getBytes("UTF-8");
        // 16位 iv = 4 bytes Key:
        byte[] iv = "testtesttesttest".getBytes("UTF-8");
        // 加密:
        byte[] data = message.getBytes("UTF-8");
        byte[] encrypted = encrypt(key, iv, data, "AES/CBC/PKCS7Padding");
        System.out.println("Encrypted: " + Coding_Base64.encodeToString(encrypted));
        // 解密:
        byte[] decrypted = decrypt(key, iv, encrypted, "AES/CBC/PKCS7Padding");
        System.out.println("Decrypted: " + new String(decrypted, "UTF-8"));
    }

    // 加密:
    public static byte[] encrypt(byte[] key, byte[] iv, byte[] input, String cipherInstance) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(cipherInstance);
        SecretKeySpec keySpec = new SecretKeySpec(key, cipherInstance.split("/")[0]);
        if (iv == null) {
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        }else {
            IvParameterSpec ivps = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivps);
        }
        return cipher.doFinal(input);
    }

    // 解密:
    public static byte[] decrypt(byte[] key, byte[] iv, byte[] input, String cipherInstance) throws GeneralSecurityException {
        // 解密:
        Cipher cipher = Cipher.getInstance(cipherInstance);
        SecretKeySpec keySpec = new SecretKeySpec(key, cipherInstance.split("/")[0]);
        if (iv == null) {
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        }else {
            IvParameterSpec ivps = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivps);
        }
        return cipher.doFinal(input);
    }

    public static byte[] join(byte[] bs1, byte[] bs2) {
        byte[] r = new byte[bs1.length + bs2.length];
        System.arraycopy(bs1, 0, r, 0, bs1.length);
        System.arraycopy(bs2, 0, r, bs1.length, bs2.length);
        return r;
    }
}
