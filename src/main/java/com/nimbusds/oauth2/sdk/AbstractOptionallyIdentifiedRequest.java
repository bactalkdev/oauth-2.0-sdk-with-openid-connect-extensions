package com.nimbusds.oauth2.sdk;


import java.net.URI;

import com.nimbusds.oauth2.sdk.auth.ClientAuthentication;
import com.nimbusds.oauth2.sdk.id.ClientID;


/**
 * Abstract request with optional client authentication or client
 * identification.
 *
 * <p>Client authentication methods:
 *
 * <ul>
 *     <li>{@link com.nimbusds.oauth2.sdk.auth.ClientSecretBasic client_secret_basic}
 *     <li>{@link com.nimbusds.oauth2.sdk.auth.ClientSecretPost client_secret_post}
 *     <li>{@link com.nimbusds.oauth2.sdk.auth.ClientSecretJWT client_secret_jwt}
 *     <li>{@link com.nimbusds.oauth2.sdk.auth.PrivateKeyJWT private_key_jwt}
 * </ul>
 *
 * <p>Client identification methods:
 *
 * <ul>
 *     <li>Top level {@code client_id} parameter.
 * </ul>
 */
public abstract class AbstractOptionallyIdentifiedRequest extends AbstractOptionallyAuthenticatedRequest {


	/**
	 * The client identifier, {@code null} if not specified.
	 */
	private final ClientID clientID;
	


	/**
	 * Creates a new abstract request with optional client authentication.
	 *
	 * @param uri        The URI of the endpoint (HTTP or HTTPS) for which
	 *                   the request is intended, {@code null} if not
	 *                   specified (if, for example, the
	 *                   {@link #toHTTPRequest()} method will not be used).
	 * @param clientAuth The client authentication, {@code null} if none.
	 */
	public AbstractOptionallyIdentifiedRequest(final URI uri,
						   final ClientAuthentication clientAuth) {

		super(uri, clientAuth);
		clientID = null;
	}


	/**
	 * Creates a new abstract request with optional client identification.
	 *
	 * @param uri      The URI of the endpoint (HTTP or HTTPS) for which
	 *                 the request is intended, {@code null} if not
	 *                 specified (if, for example, the
	 *                 {@link #toHTTPRequest()} method will not be used).
	 * @param clientID The client identifier, {@code null} if not
	 *                 specified.
	 */
	public AbstractOptionallyIdentifiedRequest(final URI uri,
						   final ClientID clientID) {

		super(uri, null);
		this.clientID = clientID;
	}


	/**
	 * Gets the client identifier (for a request from a public client or a
	 * request without explicit client authentication).
	 *
	 * @see #getClientAuthentication()
	 *
	 * @return The client identifier, {@code null} if not specified.
	 */
	public ClientID getClientID() {

		return clientID;
	}
}
