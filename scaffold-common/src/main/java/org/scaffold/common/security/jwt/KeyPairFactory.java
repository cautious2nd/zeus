/**
 * Author : chiziyue
 * Date : 2021年11月12日 下午2:55:41
 * Title : org.scaffold.common.security.jwt.KeyPairFactory.java
 *
**/
package org.scaffold.common.security.jwt;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.core.io.ClassPathResource;

public class KeyPairFactory {
	private final static Lock LOCK = new ReentrantLock();
	private KeyStore keyStore;
//	private KeyPair keyPair;

	public KeyPair create(String keyPath, String keyAlias, String keyPass) {
		ClassPathResource classPathResource = new ClassPathResource(keyPath);
		char[] pem = keyPass.toCharArray();

		try {
			LOCK.lock();
			if (null == keyStore) {
				keyStore = KeyStore.getInstance("jks");
				keyStore.load(classPathResource.getInputStream(), pem);
			}
			LOCK.unlock();
			RSAPrivateCrtKey rsaPrivateCrtKey = (RSAPrivateCrtKey) keyStore.getKey(keyAlias, pem);
			RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(rsaPrivateCrtKey.getModulus(),
					rsaPrivateCrtKey.getPublicExponent());
			PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(rsaPublicKeySpec);
			return new KeyPair(publicKey, rsaPrivateCrtKey);
//			this.keyPair = new KeyPair(publicKey, rsaPrivateCrtKey);
		} catch (Exception e) {
			throw new IllegalStateException("Cannot load keys from store : " + classPathResource, e);
		}

	}

//	public KeyPair getKeyPair() {
//		return this.keyPair;
//	}

}
