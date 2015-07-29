package com.packtpub.dynamodb.cookbook.addressbook.services;


import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import com.amazonaws.services.dynamodbv2.datamodeling.encryption.providers.EncryptionMaterialsProvider;
import com.amazonaws.services.dynamodbv2.datamodeling.encryption.providers.SymmetricStaticProvider;

public class EncryptorProvider {
	static SecureRandom rnd = new SecureRandom();

	private static SecretKey generateEncryptionKey() {

		KeyGenerator aesGen = null;
		try {
			aesGen = KeyGenerator.getInstance("AES");
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		aesGen.init(128, rnd);
		return aesGen.generateKey();

	}

	private static SecretKey generateSigningKey() {

		KeyGenerator macGen = null;
		try {
			macGen = KeyGenerator.getInstance("HmacSHA256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		macGen.init(256, rnd);
		return macGen.generateKey();

	}

	public static EncryptionMaterialsProvider getProvider() {
		SecretKey encKey = null;
		SecretKey signingKey = null;

		encKey = generateEncryptionKey();
		signingKey = generateSigningKey();
		return new SymmetricStaticProvider(encKey, signingKey);

	}

}
