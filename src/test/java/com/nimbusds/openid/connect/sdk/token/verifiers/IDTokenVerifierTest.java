package com.nimbusds.openid.connect.sdk.token.verifiers;


import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.proc.BadJWEException;
import com.nimbusds.jose.proc.BadJWSException;
import com.nimbusds.jose.util.Base64URL;
import com.nimbusds.jose.util.ByteUtils;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.PlainJWT;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.proc.BadJWTException;
import com.nimbusds.oauth2.sdk.auth.Secret;
import com.nimbusds.oauth2.sdk.id.Audience;
import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.oauth2.sdk.id.Issuer;
import com.nimbusds.oauth2.sdk.id.Subject;
import com.nimbusds.oauth2.sdk.jose.jwk.ImmutableJWKSet;
import com.nimbusds.oauth2.sdk.jose.jwk.JWEDecryptionKeySelector;
import com.nimbusds.oauth2.sdk.jose.jwk.JWSVerificationKeySelector;
import com.nimbusds.openid.connect.sdk.Nonce;
import com.nimbusds.openid.connect.sdk.claims.IDTokenClaimsSet;
import junit.framework.TestCase;


/**
 * Tests the ID token verifier.
 */
public class IDTokenVerifierTest extends TestCase {


	public void testConstant() {

		assertEquals(60, IDTokenVerifier.DEFAULT_MAX_CLOCK_SKEW);
	}


	public void testVerifyPlain()
		throws Exception {

		Issuer iss = new Issuer("https://c2id.com");
		ClientID clientID = new ClientID("123");
		Date now = new Date();

		JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
				.issuer(iss.getValue())
				.subject("alice")
				.audience(clientID.getValue())
				.expirationTime(new Date(now.getTime() + 10*60*1000L))
				.issueTime(now)
				.build();

		PlainJWT idToken = new PlainJWT(claimsSet);

		IDTokenVerifier idTokenVerifier = new IDTokenVerifier(iss, clientID);
		assertEquals(iss, idTokenVerifier.getExpectedIssuer());
		assertEquals(clientID, idTokenVerifier.getClientID());
		assertNull(idTokenVerifier.getJWSKeySelector());
		assertNull(idTokenVerifier.getJWEKeySelector());

		IDTokenClaimsSet idTokenClaimsSet = idTokenVerifier.verify(idToken, null);
		assertEquals(iss, idTokenClaimsSet.getIssuer());
		assertEquals(new Subject("alice"), idTokenClaimsSet.getSubject());
		assertTrue(idTokenClaimsSet.getAudience().contains(new Audience("123")));
		assertNotNull(idTokenClaimsSet.getExpirationTime());
		assertNotNull(idTokenClaimsSet.getIssueTime());
	}


	public void testVerifyPlainExpired()
		throws Exception {

		Issuer iss = new Issuer("https://c2id.com");
		ClientID clientID = new ClientID("123");
		Date now = new Date();

		JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
				.issuer(iss.getValue())
				.subject("alice")
				.audience(clientID.getValue())
				.expirationTime(new Date(now.getTime() - 5*60*1000L))
				.issueTime(new Date(now.getTime() - 10*60*1000L))
				.build();

		PlainJWT idToken = new PlainJWT(claimsSet);

		IDTokenVerifier idTokenVerifier = new IDTokenVerifier(iss, clientID);

		try {
			idTokenVerifier.verify(idToken, null);
			fail();
		} catch (BadJWTException e) {
			assertEquals("Expired JWT", e.getMessage());
		}
	}


	public void testVerifySigned()
		throws Exception {

		KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
		gen.initialize(1024);
		KeyPair keyPair = gen.generateKeyPair();
		RSAKey rsaJWK = new RSAKey.Builder((RSAPublicKey)keyPair.getPublic())
				.privateKey((RSAPrivateKey)keyPair.getPrivate())
				.keyID("1")
				.keyUse(KeyUse.SIGNATURE)
				.build();
		JWKSet jwkSet = new JWKSet(rsaJWK);

		Issuer iss = new Issuer("https://c2id.com");
		ClientID clientID = new ClientID("123");
		Date now = new Date();

		JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
				.issuer(iss.getValue())
				.subject("alice")
				.audience(clientID.getValue())
				.expirationTime(new Date(now.getTime() + 10*60*1000L))
				.issueTime(now)
				.build();

		SignedJWT idToken = new SignedJWT(new JWSHeader(JWSAlgorithm.RS256), claimsSet);
		idToken.sign(new RSASSASigner(rsaJWK));

		IDTokenVerifier idTokenVerifier = new IDTokenVerifier(iss, clientID, JWSAlgorithm.RS256, jwkSet);
		assertNotNull(idTokenVerifier.getJWSKeySelector());
		assertNull(idTokenVerifier.getJWEKeySelector());

		IDTokenClaimsSet idTokenClaimsSet = idTokenVerifier.verify(idToken, null);
		assertEquals(iss, idTokenClaimsSet.getIssuer());
		assertEquals(new Subject("alice"), idTokenClaimsSet.getSubject());
		assertTrue(idTokenClaimsSet.getAudience().contains(new Audience("123")));
		assertNotNull(idTokenClaimsSet.getExpirationTime());
		assertNotNull(idTokenClaimsSet.getIssueTime());
	}


	public void testVerifyBadSigned()
		throws Exception {

		KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
		gen.initialize(1024);
		KeyPair keyPair = gen.generateKeyPair();
		RSAKey rsaJWK = new RSAKey.Builder((RSAPublicKey)keyPair.getPublic())
				.privateKey((RSAPrivateKey)keyPair.getPrivate())
				.keyID("1")
				.keyUse(KeyUse.SIGNATURE)
				.build();
		JWKSet jwkSet = new JWKSet(rsaJWK);

		Issuer iss = new Issuer("https://c2id.com");
		ClientID clientID = new ClientID("123");
		Date now = new Date();

		JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
				.issuer(iss.getValue())
				.subject("alice")
				.audience(clientID.getValue())
				.expirationTime(new Date(now.getTime() + 10*60*1000L))
				.issueTime(now)
				.build();

		SignedJWT idToken = new SignedJWT(new JWSHeader(JWSAlgorithm.RS256), claimsSet);
		idToken.sign(new RSASSASigner(rsaJWK));
		idToken = new SignedJWT(idToken.getHeader().toBase64URL(), idToken.getPayload().toBase64URL(), Base64URL.encode("bad-sig"));

		IDTokenVerifier idTokenVerifier = new IDTokenVerifier(iss, clientID, JWSAlgorithm.RS256, jwkSet);

		try {
			idTokenVerifier.verify(idToken, null);
			fail();
		} catch (BadJWSException e) {
			assertEquals("Signed JWT rejected: Invalid signature", e.getMessage());
		}
	}


	public void testVerifySignedWithNonce()
		throws Exception {

		KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
		gen.initialize(1024);
		KeyPair keyPair = gen.generateKeyPair();
		RSAKey rsaJWK = new RSAKey.Builder((RSAPublicKey)keyPair.getPublic())
				.privateKey((RSAPrivateKey)keyPair.getPrivate())
				.keyID("1")
				.keyUse(KeyUse.SIGNATURE)
				.build();
		JWKSet jwkSet = new JWKSet(rsaJWK);

		Issuer iss = new Issuer("https://c2id.com");
		ClientID clientID = new ClientID("123");
		Date now = new Date();

		JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
				.issuer(iss.getValue())
				.subject("alice")
				.audience(clientID.getValue())
				.expirationTime(new Date(now.getTime() + 10*60*1000L))
				.issueTime(now)
				.claim("nonce", "xyz")
				.build();

		SignedJWT idToken = new SignedJWT(new JWSHeader(JWSAlgorithm.RS256), claimsSet);
		idToken.sign(new RSASSASigner(rsaJWK));

		IDTokenVerifier idTokenVerifier = new IDTokenVerifier(iss, clientID, JWSAlgorithm.RS256, jwkSet);
		assertNotNull(idTokenVerifier.getJWSKeySelector());
		assertNull(idTokenVerifier.getJWEKeySelector());

		IDTokenClaimsSet idTokenClaimsSet = idTokenVerifier.verify(idToken, new Nonce("xyz"));
		assertEquals(iss, idTokenClaimsSet.getIssuer());
		assertEquals(new Subject("alice"), idTokenClaimsSet.getSubject());
		assertTrue(idTokenClaimsSet.getAudience().contains(new Audience("123")));
		assertNotNull(idTokenClaimsSet.getExpirationTime());
		assertNotNull(idTokenClaimsSet.getIssueTime());
		assertEquals(new Nonce("xyz"), idTokenClaimsSet.getNonce());
	}


	public void testVerifyHmacWithNonce()
		throws Exception {

		Secret clientSecret = new Secret(ByteUtils.byteLength(256));

		Issuer iss = new Issuer("https://c2id.com");
		ClientID clientID = new ClientID("123");
		Date now = new Date();

		JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
				.issuer(iss.getValue())
				.subject("alice")
				.audience(clientID.getValue())
				.expirationTime(new Date(now.getTime() + 10*60*1000L))
				.issueTime(now)
				.claim("nonce", "xyz")
				.build();

		SignedJWT idToken = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
		idToken.sign(new MACSigner(clientSecret.getValueBytes()));

		IDTokenVerifier idTokenVerifier = new IDTokenVerifier(iss, clientID, JWSAlgorithm.HS256, clientSecret);
		assertNotNull(idTokenVerifier.getJWSKeySelector());
		assertNull(idTokenVerifier.getJWEKeySelector());

		IDTokenClaimsSet idTokenClaimsSet = idTokenVerifier.verify(idToken, new Nonce("xyz"));
		assertEquals(iss, idTokenClaimsSet.getIssuer());
		assertEquals(new Subject("alice"), idTokenClaimsSet.getSubject());
		assertTrue(idTokenClaimsSet.getAudience().contains(new Audience("123")));
		assertNotNull(idTokenClaimsSet.getExpirationTime());
		assertNotNull(idTokenClaimsSet.getIssueTime());
		assertEquals(new Nonce("xyz"), idTokenClaimsSet.getNonce());
	}


	public void testVerifyBadHmac()
		throws Exception {

		Secret clientSecret = new Secret(ByteUtils.byteLength(256));

		Issuer iss = new Issuer("https://c2id.com");
		ClientID clientID = new ClientID("123");
		Date now = new Date();

		JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
				.issuer(iss.getValue())
				.subject("alice")
				.audience(clientID.getValue())
				.expirationTime(new Date(now.getTime() + 10*60*1000L))
				.issueTime(now)
				.build();

		SignedJWT idToken = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
		idToken.sign(new MACSigner(new Secret(ByteUtils.byteLength(256)).getValueBytes()));

		IDTokenVerifier idTokenVerifier = new IDTokenVerifier(iss, clientID, JWSAlgorithm.HS256, clientSecret);

		try {
			idTokenVerifier.verify(idToken, null);
			fail();
		} catch (BadJWSException e) {
			assertEquals("Signed JWT rejected: Invalid signature", e.getMessage());
		}
	}


	public void testVerifyNested()
		throws Exception {

		KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
		gen.initialize(1024);

		// Generate OP key
		KeyPair keyPair = gen.generateKeyPair();
		RSAKey opJWK = new RSAKey.Builder((RSAPublicKey)keyPair.getPublic())
				.privateKey((RSAPrivateKey)keyPair.getPrivate())
				.keyID("s1")
				.keyUse(KeyUse.SIGNATURE)
				.build();
		final JWKSet opJWKSet = new JWKSet(opJWK);

		// Generate RP key
		keyPair = gen.generateKeyPair();
		RSAKey rpJWK = new RSAKey.Builder((RSAPublicKey)keyPair.getPublic())
				.privateKey((RSAPrivateKey)keyPair.getPrivate())
				.keyID("e1")
				.keyUse(KeyUse.ENCRYPTION)
				.build();
		final JWKSet rpJWKSet = new JWKSet(rpJWK);

		Issuer iss = new Issuer("https://c2id.com");
		ClientID clientID = new ClientID("123");
		Date now = new Date();

		JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
				.issuer(iss.getValue())
				.subject("alice")
				.audience(clientID.getValue())
				.expirationTime(new Date(now.getTime() + 10*60*1000L))
				.issueTime(now)
				.build();

		SignedJWT idToken = new SignedJWT(new JWSHeader.Builder(JWSAlgorithm.RS256).keyID("s1").build(), claimsSet);
		idToken.sign(new RSASSASigner(opJWK));

		JWEObject jweObject = new JWEObject(new JWEHeader.Builder(JWEAlgorithm.RSA1_5, EncryptionMethod.A128CBC_HS256).keyID("e1").contentType("JWT").build(), new Payload(idToken));
		jweObject.encrypt(new RSAEncrypter(rpJWK));

		String idTokenString = jweObject.serialize();

		IDTokenVerifier verifier = new IDTokenVerifier(iss, clientID,
				new JWSVerificationKeySelector(
						iss,
						JWSAlgorithm.RS256,
						new ImmutableJWKSet(iss, opJWKSet)),
				new JWEDecryptionKeySelector(
						clientID,
						JWEAlgorithm.RSA1_5,
						EncryptionMethod.A128CBC_HS256,
						new ImmutableJWKSet(clientID, rpJWKSet)));

		assertEquals(iss, verifier.getExpectedIssuer());
		assertEquals(clientID, verifier.getClientID());
		assertNotNull(verifier.getJWSKeySelector());
		assertNotNull(verifier.getJWEKeySelector());

		IDTokenClaimsSet idTokenClaimsSet = verifier.verify(JWTParser.parse(idTokenString), null);

		assertEquals(iss, idTokenClaimsSet.getIssuer());
		assertEquals(new Subject("alice"), idTokenClaimsSet.getSubject());
		assertTrue(idTokenClaimsSet.getAudience().contains(new Audience("123")));
		assertNotNull(idTokenClaimsSet.getExpirationTime());
		assertNotNull(idTokenClaimsSet.getIssueTime());
	}


	public void testBadEncryption()
		throws Exception {

		KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
		gen.initialize(1024);

		// Generate OP key
		KeyPair keyPair = gen.generateKeyPair();
		RSAKey opJWK = new RSAKey.Builder((RSAPublicKey)keyPair.getPublic())
				.privateKey((RSAPrivateKey)keyPair.getPrivate())
				.keyID("s1")
				.keyUse(KeyUse.SIGNATURE)
				.build();
		final JWKSet opJWKSet = new JWKSet(opJWK);

		// Generate RP key
		keyPair = gen.generateKeyPair();
		RSAKey rpJWK = new RSAKey.Builder((RSAPublicKey)keyPair.getPublic())
				.privateKey((RSAPrivateKey)keyPair.getPrivate())
				.keyID("e1")
				.keyUse(KeyUse.ENCRYPTION)
				.build();
		final JWKSet rpJWKSet = new JWKSet(rpJWK);


		// Generate bad encryption key
		keyPair = gen.generateKeyPair();
		RSAKey badJWK = new RSAKey.Builder((RSAPublicKey)keyPair.getPublic())
				.privateKey((RSAPrivateKey)keyPair.getPrivate())
				.keyID("e1")
				.keyUse(KeyUse.ENCRYPTION)
				.build();
		final JWKSet badJWKSet = new JWKSet(rpJWK);

		Issuer iss = new Issuer("https://c2id.com");
		ClientID clientID = new ClientID("123");
		Date now = new Date();

		JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
				.issuer(iss.getValue())
				.subject("alice")
				.audience(clientID.getValue())
				.expirationTime(new Date(now.getTime() + 10*60*1000L))
				.issueTime(now)
				.build();

		SignedJWT idToken = new SignedJWT(new JWSHeader.Builder(JWSAlgorithm.RS256).keyID("s1").build(), claimsSet);
		idToken.sign(new RSASSASigner(opJWK));

		JWEObject jweObject = new JWEObject(new JWEHeader.Builder(JWEAlgorithm.RSA1_5, EncryptionMethod.A128CBC_HS256).keyID("e1").contentType("JWT").build(), new Payload(idToken));
		jweObject.encrypt(new RSAEncrypter(badJWK));

		String idTokenString = jweObject.serialize();

		IDTokenVerifier verifier = new IDTokenVerifier(iss, clientID,
				new JWSVerificationKeySelector(
						iss,
						JWSAlgorithm.RS256,
						new ImmutableJWKSet(iss, opJWKSet)),
				new JWEDecryptionKeySelector(
						clientID,
						JWEAlgorithm.RSA1_5,
						EncryptionMethod.A128CBC_HS256,
						new ImmutableJWKSet(clientID, rpJWKSet)));

		try {
			verifier.verify(JWTParser.parse(idTokenString), null);
			fail();
		} catch (BadJWEException e) {
			assertEquals("Encrypted JWT rejected: Given final block not properly padded", e.getMessage());
		}
	}
}
