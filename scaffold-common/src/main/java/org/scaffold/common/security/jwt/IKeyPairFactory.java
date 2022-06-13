/**
 * Author : chiziyue
 * Date : 2021年11月15日 上午11:12:20
 * Title : org.scaffold.common.security.jwt.IKeyPairFactory.java
 *
**/
package org.scaffold.common.security.jwt;

import java.security.KeyPair;

public interface IKeyPairFactory {
	KeyPair createKeyPair(String keyPath, String keyAlias, String keyPass);

	KeyPair getKeyPair();
}
