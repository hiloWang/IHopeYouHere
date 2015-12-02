package com.hilo.util;

import android.annotation.SuppressLint;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

@SuppressLint("TrulyRandom")
public class AESUtils {
    /**
     * 加密
     *
     * @param stringToEncode
     * 需要加密的内容
     * @param password
     * 加密密码
     * @return
     */
    // 向量
    public static String IV = "0001020304050607";
    // 密匙
    public static String Key = "P)(%&#*~<>diuej8ApU!Wm,#@:3TuoiQ";
    // 要加密的内容
    private static String stringToEncode = "4565";

    public static void test() {
        String encodeCBC = encodeCBC(stringToEncode, Key, IV);
        String encodeECB = encodeECB(stringToEncode, Key);
        String encode = encode(stringToEncode, Key, null);
        System.out.println("加密前：" + stringToEncode + " \n加密后：" + encodeCBC);
        System.out.println("加密前：" + stringToEncode + " \n加密后：" + encodeECB);
        System.out.println("加密前：" + stringToEncode + " \n加密后：" + encode);
        try {
            String decryptCBC = DecryptCBC(encodeCBC, Key, IV);
            String decryptECB = DecryptECB(encodeECB, Key);
            String decrypt = Decrypt(encode, Key, null);
            System.out.println("解密后CBC：" + decryptCBC);
            System.out.println("解密后ECB：" + decryptECB);
            System.out.println("解密后ECB：" + decrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ECB 解密
     *
     * @param stringToEncode 要解密的内容
     * @param Key            密匙
     * @return 解密后的内容
     * @throws Exception
     */
    @SuppressLint("TrulyRandom")
    public static String DecryptECB(String stringToEncode, String Key)
            throws Exception {
        try {
            // 判断Key是否正确
            if (Key == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            // if (sKey.length() != 16) {
            // System.out.print("Key长度不是16位");
            // return null;
            // }
            byte[] raw = Key.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// "算法/模式/补码方式"
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = Base64.decode(stringToEncode, 0);// 先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original, "utf-8");
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    /**
     * CBC 解密
     *
     * @param stringToEncode 要解密的内容
     * @param Key            密匙
     * @param IV             向量
     * @return 解密后的内容
     * @throws Exception
     */
    public static String DecryptCBC(String stringToEncode, String Key, String IV)
            throws Exception {
        try {
            // 判断Key是否正确
            if (Key == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            // if (sKey.length() != 16) {
            // System.out.print("Key长度不是16位");
            // return null;
            // }
            byte[] decode = Base64.decode(stringToEncode, 0);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes());
            SecretKeySpec skeySpec = getKey(Key);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParameterSpec);
            byte[] ret = cipher.doFinal(decode);
            return new String(ret, "utf-8");
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    /**
     * CBC 加密
     *
     * @param stringToEncode 要加密的内容
     * @param Key            密匙
     * @param IV             向量
     * @return 加密后的内容
     * @throws NullPointerException
     */
    @SuppressLint("TrulyRandom")
    public static String encodeCBC(String stringToEncode, String Key, String IV)
            throws NullPointerException {
        try {
            SecretKeySpec skeySpec = getKey(Key);
            byte[] clearText = stringToEncode.getBytes("UTF8");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParameterSpec);

            String encrypedValue = Base64.encodeToString(
                    cipher.doFinal(clearText), Base64.DEFAULT);
            return encrypedValue;

        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * ECB 加密
     *
     * @param stringToEncode 要加密的内容
     * @param Key            密匙
     * @return 加密后的内容
     * @throws NullPointerException
     */
    public static String encodeECB(String stringToEncode, String Key)
            throws NullPointerException {
        try {
            SecretKeySpec skeySpec = getKey(Key);
            byte[] clearText = stringToEncode.getBytes("utf-8");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            String encrypedValue = Base64.encodeToString(
                    cipher.doFinal(clearText), Base64.DEFAULT);
            return encrypedValue;
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * ECB CBC 通用加密
     *
     * @param stringToEncode 要加密的内容
     * @param Key            密匙
     * @param IV             CBC需要传递的向量，ECB传递null
     * @return 加密后的内容
     * @throws NullPointerException
     */
    public static String encode(String stringToEncode, String Key, String IV)
            throws NullPointerException {
        try {
            SecretKeySpec skeySpec = getKey(Key);
            byte[] clearText = stringToEncode.getBytes("utf-8");
            Cipher cipher;
            if (null != IV) {
                IvParameterSpec ivParameterSpec = new IvParameterSpec(
                        IV.getBytes());
                cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
                cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParameterSpec);
            } else {
                cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
                cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            }
            String encrypedValue = Base64.encodeToString(
                    cipher.doFinal(clearText), Base64.DEFAULT);
            return encrypedValue;
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * ECB CBC 通用解密
     *
     * @param stringToEncode 要解密的内容
     * @param Key            密匙
     * @param IV             向量：CBC需要传递，ECB传递null
     * @return 解密后的内容
     * @throws Exception
     */
    public static String Decrypt(String stringToEncode, String Key, String IV)
            throws Exception {
        try {
            // 判断Key是否正确
            if (Key == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            // if (sKey.length() != 16) {
            // System.out.print("Key长度不是16位");
            // return null;
            // }
            Cipher cipher;
            byte[] encrypted1 = Base64.decode(stringToEncode, 0);// 先用base64解密
            if (null != IV) {
                IvParameterSpec ivParameterSpec = new IvParameterSpec(
                        IV.getBytes());
                SecretKeySpec skeySpec = getKey(Key);
                cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParameterSpec);
            } else {
                byte[] raw = Key.getBytes("utf-8");
                SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
                cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// "算法/模式/补码方式"
                cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            }
            byte[] ret = cipher.doFinal(encrypted1);
            return new String(ret, "utf-8");
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    /**
     * 将密匙转SecretKeySpec
     *
     * @param Key 密匙
     * @return SecretKeySpec对象
     * @throws UnsupportedEncodingException
     */
    private static SecretKeySpec getKey(String Key)
            throws UnsupportedEncodingException {
        int keyLength = 256;
        byte[] keyBytes = new byte[keyLength / 8];
        Arrays.fill(keyBytes, (byte) 0x0);
        byte[] passwordBytes = Key.getBytes("UTF-8");
        int length = passwordBytes.length < keyBytes.length ? passwordBytes.length
                : keyBytes.length;
        System.arraycopy(passwordBytes, 0, keyBytes, 0, length);
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
        return key;
    }

    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname))
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes()));
            else
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes(charsetname)));
        } catch (Exception exception) {
        }
        return resultString;
    }

    private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
}