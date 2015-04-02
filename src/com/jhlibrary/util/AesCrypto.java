package com.jhlibrary.util;

import java.util.ResourceBundle;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;


/**
 * @author jong-hyun.jeong
 *  AES 128 UTIL
 *  encrypt : 암호화
 *  decrypt : 복호화
 */
public class AesCrypto {
	
	private static final String CRYPTO_NAME = "AES";
	private static final String TRANSFORM = "AES/ECB/PKCS7Padding";
	
	public static String encrypt(String plainText) throws Exception {
		
		ResourceBundle resource = ResourceBundle.getBundle("assets.auth-config");
		String key = resource.getString("crypt.key");
		KeyGenerator kgen = KeyGenerator.getInstance(CRYPTO_NAME);
		kgen.init(128);
		byte[] raw = key.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, CRYPTO_NAME);
		Cipher cipher = Cipher.getInstance(TRANSFORM);

		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(plainText.getBytes());
		return asHex(encrypted);
	}

	public static String decrypt(String cipherText) throws Exception {
		
		ResourceBundle resource = ResourceBundle.getBundle("assets.auth-config");
		String key = resource.getString("crypt.key");
		
		KeyGenerator kgen = KeyGenerator.getInstance(CRYPTO_NAME);
		kgen.init(128);

		byte[] raw = key.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, CRYPTO_NAME);
		Cipher cipher = Cipher.getInstance(TRANSFORM);

		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] original = cipher.doFinal(fromString(cipherText));
		String originalString = new String(original);
		return originalString;
	}

	private static String asHex(byte buf[]) {
		StringBuffer strbuf = new StringBuffer(buf.length * 2);
		int i;

		for (i = 0; i < buf.length; i++) {
			if (((int) buf[i] & 0xff) < 0x10)
				strbuf.append("0");

			strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
		}
		return strbuf.toString();
	}

	private static byte[] fromString(String hex) {
		int len = hex.length();
		byte[] buf = new byte[((len + 1) / 2)];

		int i = 0, j = 0;
		if ((len % 2) == 1)
			buf[j++] = (byte) fromDigit(hex.charAt(i++));

		while (i < len) {
			buf[j++] = (byte) ((fromDigit(hex.charAt(i++)) << 4) | fromDigit(hex
					.charAt(i++)));
		}
		return buf;
	}

	private static int fromDigit(char ch) {
		if (ch >= '0' && ch <= '9')
			return ch - '0';
		if (ch >= 'A' && ch <= 'F')
			return ch - 'A' + 10;
		if (ch >= 'a' && ch <= 'f')
			return ch - 'a' + 10;

		throw new IllegalArgumentException("invalid hex digit '" + ch + "'");
	}
}
