package com.DarkKeks.drm.test;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Random;

public class EncryptionTest {
    public static void main(String[] args) throws Exception {
        new EncryptionTest("kekekeke").decryptTest();
    }

    private final Random rand = new SecureRandom();
    private String message;

    public EncryptionTest(String message) {
        this.message = message;
    }

    public void run() throws Exception {
        String password = "MyAwesomePass";
        byte[] ciph = getEncryptedMessage(message, password);
        System.out.println("String: " + message);
        System.out.println("Password: " + password);
        System.out.println("Encrypted: " + Arrays.toString(ciph));
        String deciph = getDecryptedMessage(ciph, "MyAwesomePass");
        System.out.println("Decrypted: " + deciph);
    }

    public void decryptTest() throws GeneralSecurityException, UnsupportedEncodingException {
        byte[] msg = new byte[]{4, -80, -95, 16, 113, -5, 57, -76,
                                75, 1, 95, 89, 56, -35, 52, -105,
                                35, -38, -76, 125, -98, -115, 67, -115,
                                -14, 37, -54, -15, 53, 83, -47, -86,
                                -23, 88, 100, 15, -65, -43, 30, 21};
        String pass = "MyAwesomePass";
        System.out.println(getDecryptedMessage(msg, pass));
    }

    private byte[] getEncryptedMessage(String message, String password) throws Exception {
        byte[] salt = new byte[8];
        rand.nextBytes(salt);
        SecretKey key = getKey(password.toCharArray(), salt);

        return concatenateByteArrays(salt, encrypt(key, message));
    }

    private String getDecryptedMessage(byte[] message, String password) throws GeneralSecurityException, UnsupportedEncodingException {
        byte[] salt = Arrays.copyOfRange(message, 0, 8);
        byte[] iv = Arrays.copyOfRange(message, 8, 24);
        message = Arrays.copyOfRange(message, 24, message.length);
        SecretKey key = getKey(password.toCharArray(), salt);

        return decrypt(key, iv, message);
    }

    public SecretKey getKey(char[] password, byte[] salt) throws GeneralSecurityException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password, salt, 16384, 256);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");

        return secret;
    }

    public byte[] encrypt(SecretKey key, String msg) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        AlgorithmParameters params = cipher.getParameters();
        byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
        byte[] ciphertext = cipher.doFinal(msg.getBytes("UTF-8"));

        return concatenateByteArrays(iv, ciphertext);
    }

    public String decrypt(SecretKey key, byte[] iv, byte[] msg) throws GeneralSecurityException, UnsupportedEncodingException {

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));

        return new String(cipher.doFinal(msg));
    }

    private static byte[] concatenateByteArrays(byte[] a, byte[] b) throws Exception {
        byte[] result;
        try{
            ByteArrayOutputStream str = new ByteArrayOutputStream();
            str.write(a);
            str.write(b);

            result = str.toByteArray();
        } catch (Exception e) {
            throw new Exception("Some weird shit happened at Security.concatenateByteArrays");
        }
        return result;
    }
}
