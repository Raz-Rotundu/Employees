package com.lumius.auth_microservice.service;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import com.lumius.auth_microservice.dto.TokenRequest;
import com.lumius.auth_microservice.dto.TokenResponse;

public class JWTServiceImpl implements JWTService {
	
	private KeyPair generateRsaKey() {
		
		try {
			
			// Setup KeyPairGenerator with RSA algorith
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");	
			
			// Generate RSA keypair
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			
			
			// Encode both keys to Base64 strings
			String publicKey = Base64.getEncoder().encodeToString(
					keyPair.getPublic()
						.getEncoded());
			
			String privateKey = Base64.getEncoder().encodeToString(
					keyPair.getPrivate()
						.getEncoded());
			
			// TODO Output somewhere productive
			System.out.println(publicKey);
			System.out.println(privateKey);
			
			// return the initial keypair
			return keyPair;
			
			
		} catch (NoSuchAlgorithmException ex) {
			throw new RuntimeException("Algorithm problem:", ex);
		}	
	}

	private KeyPair loadRsaKey() {
		
		try {
			
			// TODO create config file to contain service's private and public keys	
			byte[] publicKeyBytes = Base64.getDecoder().decode("ChangeMePublic");
			byte[] privateKeyBytes = Base64.getDecoder().decode("ChangeMePrivate");
			
			//Create spec?
			X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
			PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
			
			// Call keyFactory
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			
			
			//Create Keypair
			PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
			PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
			
			KeyPair pair = new KeyPair(publicKey, privateKey);
			
			// Return Keypair
			return pair;
			
			
			
		} catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
			throw new RuntimeException("Algorithm problem:", ex);
		} 
		
	}
	
	
	@Override
	public TokenResponse getJWTToken(TokenRequest request, String scope, String userId) {

		// TODO make the keyID something relevant
		String keyId = "ServerKeyID";
		
		// Load RSA keyPair into memory
		
		// Create JWTClaimSet
		
		// Create SignedJWT
		
		// Sign
		
		// Serialize
		
		// Return TokenResponse
		
		
		return null;
	}

}
