package zjdf.zhaogongzuo.utils;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.tencent.weibo.sdk.android.component.sso.tools.Base64;
/**
 * MD5操作运算
 * @author Eilin.Yang
 * @since 2013-6-26
 * @version v2.0
 */
public class MD5Utils extends StringUtils{

	private static byte[] iv = {1,2,3,4,5,6,7,8};
	/**
	 * MD5加码32位
	 * @param inStr
	 * @return 32位
	 */
	public static String MD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		if (null==inStr) {
			return inStr;
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];

		byte[] md5Bytes = md5.digest(byteArray);

		StringBuffer hexValue = new StringBuffer();

		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}

		return hexValue.toString();
	}
	
	/**
	 * MD5加码16位
	 * @param inStr 字符
	 * @param isfront 是否前16位,true:前16位，false：后16位
	 * @return
	 */
	public static String MD5_16(String inStr,boolean isfront) {
		String encrypt=MD5(inStr);
		if (null==encrypt||encrypt.length()==0) {
			return null;
		}
		if (!isfront) {
			return encrypt.substring(16);
		}
		return encrypt.substring(0, 16);
	}

	/**
	 * 可逆的加密算法
	 * @param inStr
	 * @param key 密钥
	 * @return
	 */
	public static String encoding(String inStr,char key) {
		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ key);
		}
		return new String(a);
	}

	/**
	 * 加密后解密 
	 * @param inStr
	 * @param key 密钥
	 * @return
	 */
	public static String decoding(String inStr,char key) {
		return encoding(inStr, key);
	}
	

	/**
	 * 
	 * @param encryptString
	 * @param encryptKey 必须8位
	 * @return
	 * @throws Exception
	 */
	public static String encryptDES(String encryptString, String encryptKey) throws Exception {
		IvParameterSpec zeroIv = new IvParameterSpec(iv);
		SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
		byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
	 
		return Base64.encode(encryptedData);
	}
	
	/**
	 * 
	 * @param decryptString
	 * @param decryptKey 必须8位，跟encryptKey 一样
	 * @return
	 * @throws Exception
	 */
	public static String decryptDES(String decryptString, String decryptKey) throws Exception {
		byte[] byteMi = Base64.decode(decryptString);
		IvParameterSpec zeroIv = new IvParameterSpec(iv);
		SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
		byte decryptedData[] = cipher.doFinal(byteMi);
	 
		return new String(decryptedData);
	}
}
