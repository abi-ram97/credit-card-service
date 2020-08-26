package com.example.creditcardservice.util;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

/**
 * Encryptor for sensitive fields
 * @author javadevopsmc06
 *
 */
@Component
public class Encryptor {
	private static final String AES = "AES";
	private static final String SECRET = "secret-key-12345";
	
	private final Key key;
	private final Cipher cipher;
	
	public Encryptor() throws NoSuchAlgorithmException, NoSuchPaddingException {
		key = new SecretKeySpec(SECRET.getBytes(), AES);
        cipher = Cipher.getInstance(AES);
	}
	
	public String encryptColumn(String value) {
		try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return Base64.getEncoder().encodeToString(cipher.doFinal(value.getBytes()));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
	}
	
	public String decryptColumn(String dbData) {
		try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(Base64.getDecoder().decode(dbData)));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
	}

}
