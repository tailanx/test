package com.yidejia.app.mall.util;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import android.util.Base64;
public class DesUtils {
 public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";
 /**
  * DES�㷨������
  * @param data ������ַ�
  * @param key ����˽Կ�����Ȳ��ܹ�С��8λ
  * @return ���ܺ���ֽ����飬һ����Base64����ʹ��
  * @throws CryptException �쳣
  */
 public static String encode(String key, String data) throws Exception {
  return encode(key, data.getBytes());
 }
 /**
  * DES�㷨������
  * @param data  ������ַ�
  * @param key  ����˽Կ�����Ȳ��ܹ�С��8λ
  * @return ���ܺ���ֽ����飬һ����Base64����ʹ��
  * @throws CryptException
  *             �쳣
  */
 private static String encode(String key, byte[] data) throws Exception {
  try {
   DESKeySpec dks = new DESKeySpec(key.getBytes());
   SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
   // key�ĳ��Ȳ��ܹ�С��8λ�ֽ�
   Key secretKey = keyFactory.generateSecret(dks);
   Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
   IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());
   AlgorithmParameterSpec paramSpec = iv;
   cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
   byte[] bytes = cipher.doFinal(data);
   return Base64.encodeToString(bytes, 0);
  } catch (Exception e) {
   throw new Exception(e);
  }
 }
 /**
  * DES�㷨������
  * @param data  ������ַ�
  * @param key ����˽Կ�����Ȳ��ܹ�С��8λ
  * @return ���ܺ���ֽ�����
  * @throws Exception  �쳣
  */
 private static byte[] decode(String key, byte[] data) throws Exception {
  try {
   DESKeySpec dks = new DESKeySpec(key.getBytes());
   SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
   // key�ĳ��Ȳ��ܹ�С��8λ�ֽ�
   Key secretKey = keyFactory.generateSecret(dks);
   Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
   IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());
   AlgorithmParameterSpec paramSpec = iv;
   cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
   return cipher.doFinal(data);
  } catch (Exception e) {
   throw new Exception(e);
  }
 }
 
 /**
  * ����.
  * @param key
  * @param data
  * @return
  * @throws Exception
  */
	public static String decode(String key, String data) {
  byte[] datas;
  String value = null;
  try {
   if (System.getProperty("os.name") != null
     && (System.getProperty("os.name").equalsIgnoreCase("sunos") || System
       .getProperty("os.name").equalsIgnoreCase("linux"))) {
    datas = decode(key, Base64.decode(data, 0));
   } else {
    datas = decode(key, Base64.decode(data, 0));
   }
   value = new String(datas);
  } catch (Exception e) {
   value = "";
  }
  return value;
 }
}