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
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

import com.lumius.auth_microservice.dto.TokenRequest;
import com.lumius.auth_microservice.dto.TokenResponse;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

public class JWTServiceImpl implements JWTService {
	
	@Value("${server.public}")
	private String publicKeyEncoded;
	
	@Value("${server.private}")
	private String privateKeyEncoded;
	
	private KeyPair generateRsaKey() {
		
		try {
			
			// Setup KeyPairGenerator with RSA algorithm
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
			byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyEncoded);
			byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyEncoded);
			
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

		try {
			
			// TODO make the keyID something relevant
			String keyId = "ServerKeyID";
			
			// Load RSA keyPair into memory
			KeyPair keyPair = loadRsaKey();
			PublicKey publicKey = keyPair.getPublic();
			PrivateKey privateKey = keyPair.getPrivate();
			
			// Create JWTClaimSet
			Date issueTime = new Date();
			Date expiryTime = new Date(System.currentTimeMillis() + 3600000);
			
			JWTClaimsSet claims = new JWTClaimsSet.Builder()
					.subject(userId)
					.claim("scope", scope)
					.issueTime(issueTime)
					.expirationTime(expiryTime)
					.build();
			
			// Create SignedJWT
			SignedJWT token = new SignedJWT(
					new JWSHeader.Builder(JWSAlgorithm.RS256)
						.keyID(keyId)
						.build(),
					claims);
					
			
			// Sign
			token.sign(new RSASSASigner(privateKey));
			
			// Serialize
			String accessToken = token.serialize();
			
			// Return TokenResponse		
			return new TokenResponse(accessToken, "Bearer", "3600", scope);
			
		} catch (Exception e) {
			throw new RuntimeException("Error creating token", e);
		}
		
	}

}
