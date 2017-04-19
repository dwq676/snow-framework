package com.zoe.snow.util;

import org.apache.commons.codec.binary.Hex;
import com.zoe.snow.log.Logger;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 对称加密、非对称加密类，包括DES、3DES、MD5
 * @author dwq
 */
public final class Security {
    private static final String MD5 = "MD5";
    private static final String DESEDE = "DESede";
    private static final String aesKey = "SNOWaEsToCrypto";
    private static final String DES_ALGORITHM = "DES";
    protected static String desedeKey = "snow-desedKey";
    protected static Map<StringBuilder, SecretKey> secretKeys = new ConcurrentHashMap<>();

    /**
     * 提取MD5消息摘要。
     *
     * @param text String 源字符串。
     * @return String MD5消息摘要；如果提取失败将返回null。
     */
    public static String md5(String text) {
        return text == null ? null : Hex.encodeHexString(digest(MD5, text.getBytes(), null));
    }

    /**
     * 提取MD5消息摘要。
     *
     * @param text       String 源字符串。
     * @param time       计算次数。
     * @param secretKeys 加密密钥集。
     * @return String MD5消息摘要；如果提取失败，或计算次数小于1将返回null。
     */
    public static String md5(String text, int time, String[] secretKeys) {
        if (text == null || time < 1)
            return null;

        byte[] bytes = text.getBytes();
        byte[][] keys = getSecretKeys(secretKeys);
        for (int i = 0; i < time; i++)
            bytes = digest(MD5, bytes, keys[i % keys.length]);

        return Hex.encodeHexString(bytes);
    }

    protected static byte[][] getSecretKeys(String[] secretKeys) {
        if (secretKeys == null || secretKeys.length == 0)
            return new byte[][]{null};

        byte[][] array = new byte[secretKeys.length][];
        for (int i = 0; i < secretKeys.length; i++)
            array[i] = secretKeys[i].getBytes();

        return array;
    }

    protected static byte[] digest(String algorithm, byte[] input, byte[] key) {
        if (input == null)
            return null;

        try {
            return MessageDigest.getInstance(algorithm).digest(merge(input, key));
        } catch (NoSuchAlgorithmException e) {
            if (Logger.isDebugEnable())
                Logger.warn(e, "取消息摘要[{}]时发生异常！", algorithm);
            return null;
        }
    }

    protected static byte[] merge(byte[] input, byte[] key) {
        if (key == null || key.length == 0)
            return input;

        byte[] array = new byte[input.length + key.length];
        for (int i = 0; i < input.length; i++)
            array[i] = input[i];
        for (int j = 0; j < key.length; j++)
            array[input.length + j] = key[j];

        return array;
    }

    /**
     * 使用默认密钥进行3DES算法进行加密。
     *
     * @param input 要加密的数据。
     * @return 加密后的数据。
     */
    public static byte[] encrypt3des(String input) {
        return encrypt3des(desedeKey, input);
    }

    /**
     * 使用3DES算法进行加密。
     *
     * @param key   密钥。密钥长度必须为24个字节。
     * @param input 要加密的数据。
     * @return 加密后的数据。
     */
    public static byte[] encrypt3des(String key, String input) {
        if (Validator.isEmpty(key) || Validator.isEmpty(input))
            return null;

        return encrypt3des(key.getBytes(), input.getBytes());
    }

    /**
     * 使用3DES算法进行加密。
     *
     * @param key   密钥。密钥长度必须为24个字节。
     * @param input 要加密的数据。
     * @return 加密后的数据。
     */
    public static byte[] encrypt3des(byte[] key, byte[] input) {
        return crypt3des(key, input, Cipher.ENCRYPT_MODE);
    }

    /**
     * 使用默认密钥进行3DES算法进行解密。
     *
     * @param input 要解密的数据。
     * @return 解密后的数据。
     */
    public static byte[] decrypt3des(String input) {
        return decrypt3des(desedeKey, input);
    }

    /**
     * 使用3DES算法进行解密。
     *
     * @param key   密钥。密钥长度必须为24个字节。
     * @param input 要解密的数据。
     * @return 解密后的数据。
     */
    public static byte[] decrypt3des(String key, String input) {
        if (Validator.isEmpty(key) || Validator.isEmpty(input))
            return null;

        return decrypt3des(key.getBytes(), input.getBytes());
    }

    /**
     * 使用3DES算法进行解密。
     *
     * @param key   密钥。密钥长度必须为24个字节。
     * @param input 要解密的数据。
     * @return 解密后的数据。
     */
    public static byte[] decrypt3des(byte[] key, byte[] input) {
        return crypt3des(key, input, Cipher.DECRYPT_MODE);
    }

    protected static byte[] crypt3des(byte[] key, byte[] input, int mode) {
        if (Validator.isEmpty(key) || Validator.isEmpty(input))
            return null;

        try {
            Cipher cipher = Cipher.getInstance(DESEDE);
            cipher.init(mode, getDesedeSecretKey(key, DESEDE));

            return cipher.doFinal(input);
        } catch (Exception e) {
            if (Logger.isDebugEnable())
                Logger.warn(e, "使用密钥[{}]进行3DES加/解密[{}]时发生异常！", new String(key), new String(input));
            return null;
        }
    }

    protected static SecretKey getDesedeSecretKey(byte[] key, String algorithm) {
        if (key.length != 24) {
            if (Logger.isDebugEnable())
                Logger.warn(null, "密钥[{}]长度[{}]必须是24个字节！", new String(key), key.length);
            return null;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(algorithm);
        for (byte by : key)
            sb.append(by);

        SecretKey secretKey = secretKeys.get(sb);
        if (secretKey == null) {
            secretKey = new SecretKeySpec(key, algorithm);
            secretKeys.put(sb, secretKey);
        }

        return secretKey;
    }

    /**
     * DES加密
     *
     * @param plainData
     * @return
     * @throws Exception
     */
    public static String AESEncode(String plainData) {

        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(DES_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, generateKey(aesKey));
            // 为了防止解密时报javax.crypto.IllegalBlockSizeException: Input length must be multiple of 8 when decrypting with padded cipher异常，
            // 不能把加密后的字节数组直接转换成字符串
            byte[] buf = cipher.doFinal(plainData.getBytes());

            return Base64Utils.encode(buf);

        } catch (Exception e) {
            return plainData;
        }

    }

    /**
     * DES解密
     *
     * @param secretData
     * @return
     * @throws Exception
     */
    public static String AESDecode(String secretData) {

        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(DES_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, generateKey(aesKey));
            byte[] buf = cipher.doFinal(Base64Utils.decode(secretData.toCharArray()));

            return new String(buf);

        } catch (Exception e) {
            return secretData;
        }
    }


    /**
     * 获得秘密密钥
     *
     * @param secretKey
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static SecretKey generateKey(String secretKey) throws NoSuchAlgorithmException {
        /*SecureRandom secureRandom = new SecureRandom(secretKey.getBytes());

        // 为我们选择的DES算法生成一个KeyGenerator对象
        KeyGenerator kg = null;
        try {
            kg = KeyGenerator.getInstance(DES_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
        }
        kg.init(secureRandom);
        //kg.init(56, secureRandom);

        // 生成密钥
        return kg.generateKey();*/
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES_ALGORITHM);
            DESKeySpec keySpec = new DESKeySpec(secretKey.getBytes());
            keyFactory.generateSecret(keySpec);
            return keyFactory.generateSecret(keySpec);
        } catch (Exception e) {

        }
        return null;
    }

    /*public static void main(String[] a) throws Exception {
        String input = "cy11Xlbrmzyh:604:301:1353064296";

        String result = Security.AESEncode(input);
        System.out.println(result);

        System.out.println(Security.AESDecode(result));

    }*/


    public static void main(String[] args) {
        try {

            System.out.println(Security.md5("DN:clound.apple.qtlcdn.com"));

            System.out.println("#####开始加密#####");
            System.out.println("root:" + Security.AESEncode("root"));
            System.out.println("192.168.2.39 :" + Security.AESEncode("192.168.2.39"));
            System.out.println("zoesoft@39:" + Security.AESEncode("zoesoft@39"));
            System.out.println("10.0.2.54:" + Security.AESEncode("10.0.2.54"));
            System.out.println("test:" + Security.AESEncode("test"));
            System.out.println("192.168.110.136:" + Security.AESEncode("192.168.110.136"));

            System.out.println("####开始解密");
            System.out.println("root:" + Security.AESDecode("wI+pPLaAN2ARcg5J9JFVKQ=="));
            System.out.println("root:" + Security.AESDecode("root"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
