package com.yidejia.app.mall.util;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import android.util.Base64;


//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;

import android.util.Log;

public class DESUtil {
	
	Key key;  
	   
    public DESUtil() {  
   
    }  
   
    public DESUtil(String str) {  
       setKey(str); // ����ܳ�  
    }  
   
    public Key getKey() {  
       return key ;  
    }  
   
    public void setKey(Key key) {  
       this . key = key;  
    }  
   
    /** 
      * ��ݲ������ KEY 
      */  
    public void setKey(String strKey) {  
       try {  
           KeyGenerator _generator = KeyGenerator.getInstance ( "DES" );  
           _generator.init( new SecureRandom(strKey.getBytes()));  
           this . key = _generator.generateKey();  
           _generator = null ;  
       } catch (Exception e) {  
           throw new RuntimeException(  
                  "Error initializing SqlMap class. Cause: " + e);  
       }  
    }  
   
    /** 
      * ���� String �������� ,String ������� 
      */  
    public String encryptStr(String strMing) {  
       byte [] byteMi = null ;  
       byte [] byteMing = null ;  
       String strMi = null; 
//       BASE64Encoder base64en = new BASE64Encoder();  
       try {  
           byteMing = strMing.getBytes( "UTF8" );  
           
           byteMi = this.encryptByte(byteMing);  
//           strMi = Base64.encodeToString(strMing.getBytes(), Base64.DEFAULT);
//           strMi = base64en.encode(byteMi);  
           strMi = new String(byteMi, "UTF8");
  
       } catch (Exception e) {  
           throw new RuntimeException(  
                  "Error initializing SqlMap class. Cause: " + e);  
           
       } finally {  
//           base64en = null ;  
           byteMing = null ;  
           byteMi = null ;  
       }  
       return strMi;  
    }  
   
    /** 
      * ���� �� String �������� ,String ������� 
      * 
      * @param strMi 
      * @return 
      */  
    public String decryptStr(String strMi) {  
//       BASE64Decoder base64De = new BASE64Decoder();  
       byte [] byteMing = null ;  
//       byte [] byteMi = null ;  
       String strMing = null ;  
       try {  
    	   byteMing = Base64.decode(strMi.getBytes(), Base64.DEFAULT);
//           byteMi = base64De.decodeBuffer(strMi);  
//           byteMing = this.decryptByte(strMi.getBytes());  
//           strMing = new String(byteMing, "UTF8" );  
//           Log.i("info", byteMing+"  byteMing1");
       } catch (Exception e) {  
           throw new RuntimeException(  
                  "Error initializing SqlMap class. Cause: " + e);  
       } finally {  
//           base64De = null ;  
           byteMing = null ;  
//           byteMi = null ;  
       }  
       Log.i("info", strMing+"  byteMing");
       return strMing;  
    } 
    
    /** 
     * ������ byte[] �������� ,byte[] ������� 
     * 
     * @param byteS 
     * @return 
     */  
   private byte [] encryptByte( byte [] byteS) {  
      byte [] byteFina = null ;  
      Cipher cipher;  
      try {  
          cipher = Cipher.getInstance ( "DES" );  
          cipher.init(Cipher. ENCRYPT_MODE , key );  
          byteFina = cipher.doFinal(byteS);  
      } catch (Exception e) {  
          throw new RuntimeException(  
                 "Error initializing SqlMap class. Cause: " + e);  
      } finally {  
          cipher = null ;  
      }  
      return byteFina;  
   }  
  
   /** 
     * ������ byte[] �������� , �� byte[] ������� 
     * 
     * @param byteD 
     * @return 
     */  
   private byte [] decryptByte( byte [] byteD) {  
      Cipher cipher;  
      byte [] byteFina = null ;  
      try {  
          cipher = Cipher.getInstance ( "DES" );  
          cipher.init(Cipher. DECRYPT_MODE , key );  
          byteFina = cipher.doFinal(byteD);  
      } catch (Exception e) {  
//          throw new RuntimeException(  
//                 "Error initializing SqlMap class. Cause: " + e);  
      } finally {  
          cipher = null ;  
      }  
      return byteFina;  
   }  
}
