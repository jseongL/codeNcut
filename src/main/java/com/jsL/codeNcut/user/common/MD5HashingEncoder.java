package com.jsL.codeNcut.user.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5HashingEncoder {
	//md5를 통해 암호화
		public static String encode(String message){
			
			try {
				MessageDigest messageDigest = MessageDigest.getInstance("md5");
				
				byte[] bytes = message.getBytes();
				
				messageDigest.update(bytes);
				
				byte[] digest = messageDigest.digest();
				
				String result = "";
				for(int i = 0; i< digest.length; i++) {
					result += Integer.toHexString(digest[i] & 0xff);
				}
				
				return result;
				
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				return null;
			}
		}
}
