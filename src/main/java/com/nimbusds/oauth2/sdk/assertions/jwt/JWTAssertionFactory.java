package com.nimbusds.oauth2.sdk.assertions.jwt;


import java.security.Provider;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.nimbusds.oauth2.sdk.auth.Secret;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.SignedJWT;


/**
 * Static JWT bearer assertion factory.
 *
 * <p>Related specifications:
 *
 * <ul>
 *     <li>Assertion Framework for OAuth 2.0 Client Authentication and
 *         Authorization Grants (RFC 7521).
 *     <li>JSON Web Token (JWT) Profile for OAuth 2.0 Client Authentication and
 *         Authorization Grants (RFC 7523).
 * </ul>
 */
public class JWTAssertionFactory {


	/**
	 * Returns the supported signature JSON Web Algorithms (JWAs).
	 *
	 * @return The supported JSON Web Algorithms (JWAs).
	 */
	public static Set<JWSAlgorithm> supportedJWAs() {

		Set<JWSAlgorithm> supported = new HashSet<>();
		supported.addAll(JWSAlgorithm.Family.HMAC_SHA);
		supported.addAll(JWSAlgorithm.Family.RSA);
		supported.addAll(JWSAlgorithm.Family.EC);
		return Collections.unmodifiableSet(supported);
	}


	/**
	 * Creates a new HMAC-protected JWT bearer assertion.
	 *
	 * @param details      The JWT bearer assertion details. Must not be
	 *                     {@code null}.
	 * @param jwsAlgorithm The expected HMAC algorithm (HS256, HS384 or
	 *                     HS512) for the JWT assertion. Must be supported
	 *                     and not {@code null}.
	 * @param secret       The secret. Must be at least 256-bits long.
	 *
	 * @return The JWT bearer assertion.
	 *
	 * @throws JOSEException If the client secret is too short, or HMAC
	 *                       computation failed.
	 */
	public static SignedJWT create(final JWTAssertionDetails details,
				       final JWSAlgorithm jwsAlgorithm,
				       final Secret secret)
		throws JOSEException {

		SignedJWT signedJWT = new SignedJWT(new JWSHeader(jwsAlgorithm), details.toJWTClaimsSet());
		signedJWT.sign(new MACSigner(secret.getValueBytes()));
		return signedJWT;
	}


	/**
	 * Creates a new RSA-signed JWT bearer assertion.
	 *
	 * @param details       The JWT bearer assertion details. Must not be
	 *                      be {@code null}.
	 * @param jwsAlgorithm  The expected RSA signature algorithm (RS256,
	 *                      RS384, RS512, PS256, PS384 or PS512) for the
	 *                      JWT assertion. Must be supported and not
	 *                      {@code null}.
	 * @param rsaPrivateKey The RSA private key. Must not be {@code null}.
	 * @param keyID         Optional identifier for the RSA key, to aid key
	 *                      selection on the recipient side. Recommended.
	 *                      {@code null} if not specified.
	 * @param jcaProvider   Optional specific JCA provider, {@code null} to
	 *                      use the default one.
	 *
	 * @return The JWT bearer assertion.
	 *
	 * @throws JOSEException If RSA signing failed.
	 */
	public static SignedJWT create(final JWTAssertionDetails details,
				       final JWSAlgorithm jwsAlgorithm,
				       final RSAPrivateKey rsaPrivateKey,
				       final String keyID,
				       final Provider jcaProvider)
		throws JOSEException {

		SignedJWT signedJWT = new SignedJWT(
			new JWSHeader.Builder(jwsAlgorithm).keyID(keyID).build(),
			details.toJWTClaimsSet());
		RSASSASigner signer = new RSASSASigner(rsaPrivateKey);
		if (jcaProvider != null) {
			signer.getJCAContext().setProvider(jcaProvider);
		}
		signedJWT.sign(signer);
		return signedJWT;
	}


	/**
	 * Creates a new EC-signed JWT bearer assertion.
	 *
	 * @param details      The JWT bearer assertion details. Must not be
	 *                     {@code null}.
	 * @param jwsAlgorithm The expected EC signature algorithm (ES256,
	 *                     ES384 or ES512) for the JWT assertion. Must be
	 *                     supported and not {@code null}.
	 * @param ecPrivateKey The EC private key. Must not be {@code null}.
	 * @param keyID        Optional identifier for the EC key, to aid key
	 *                     selection on the recipient side. Recommended.
	 *                     {@code null} if not specified.
	 * @param jcaProvider  Optional specific JCA provider, {@code null} to
	 *                     use the default one.
	 *
	 * @return The JWT bearer assertion.
	 *
	 * @throws JOSEException If RSA signing failed.
	 */
	public static SignedJWT create(final JWTAssertionDetails details,
				       final JWSAlgorithm jwsAlgorithm,
				       final ECPrivateKey ecPrivateKey,
				       final String keyID,
				       final Provider jcaProvider)
		throws JOSEException {

		SignedJWT signedJWT = new SignedJWT(
			new JWSHeader.Builder(jwsAlgorithm).keyID(keyID).build(),
			details.toJWTClaimsSet());
		ECDSASigner signer = new ECDSASigner(ecPrivateKey);
		if (jcaProvider != null) {
			signer.getJCAContext().setProvider(jcaProvider);
		}
		signedJWT.sign(signer);
		return signedJWT;
	}


	/**
	 * Prevents public instantiation.
	 */
	private JWTAssertionFactory() {}
}
