/**
 * Author : chiziyue
 * Date : 2021年11月15日 上午11:41:17
 * Title : org.scaffold.common.security.jwt.JksKeyPairFactory.java
 *
**/
package org.scaffold.common.security.jwt;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.core.io.ClassPathResource;

public class JksKeyPairFactory implements IKeyPairFactory {
	private final static Lock LOCK = new ReentrantLock();
	private KeyStore keyStore;
	private KeyPair keyPair;

	@Override
	public KeyPair createKeyPair(String keyPath, String keyAlias, String keyPass) {

		char[] pem = keyPass.toCharArray();

		try {
			LOCK.lock();
			if (null == keyStore) {
				try (InputStream inputStream = new ClassPathResource(keyPath).getInputStream()) {
					keyStore = KeyStore.getInstance("jks");
					keyStore.load(inputStream, pem);
				}
			}
			LOCK.unlock();
			RSAPrivateCrtKey rsaPrivateCrtKey = (RSAPrivateCrtKey) keyStore.getKey(keyAlias, pem);
			RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(rsaPrivateCrtKey.getModulus(),
					rsaPrivateCrtKey.getPublicExponent());
			PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(rsaPublicKeySpec);
			this.keyPair = new KeyPair(publicKey, rsaPrivateCrtKey);
//			return new KeyPair(publicKey, rsaPrivateCrtKey);
			return this.keyPair;
		} catch (Exception e) {
			throw new IllegalStateException("Cannot load keys from store : " + keyPath, e);
		}

	}

	@Override
	public KeyPair getKeyPair() {
		return this.keyPair;
	}

}
