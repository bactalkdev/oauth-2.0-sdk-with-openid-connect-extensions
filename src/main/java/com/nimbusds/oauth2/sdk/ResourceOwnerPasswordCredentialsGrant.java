package com.nimbusds.oauth2.sdk;


import java.util.LinkedHashMap;
import java.util.Map;

import net.jcip.annotations.Immutable;

import com.nimbusds.oauth2.sdk.auth.Secret;


/**
 * Resource owner password credentials grant. Used in access token requests
 * with the resource owner's username and password. This class is immutable.
 *
 * <p>Related specifications:
 *
 * <ul>
 *     <li>OAuth 2.0 (RFC 6749), section 4.3.2.
 * </ul>
 *
 * @author Vladimir Dzhuvinov
 */
@Immutable
public class ResourceOwnerPasswordCredentialsGrant extends AuthorizationGrant {


	/**
	 * The associated grant type.
	 */
	public static final GrantType GRANT_TYPE = GrantType.PASSWORD;


	/**
	 * The username.
	 */
	private final String username;


	/**
	 * The password.
	 */
	private final Secret password;


	/**
	 * The requested scope.
	 */
	private final Scope scope;


	/**
	 * Creates a new resource owner password credentials grant.
	 *
	 * @param username The resource owner's username. Must not be
	 *                 {@code null}.
	 * @param password The resource owner's password. Must not be
	 *                 {@code null}.
	 * @param scope    The requested scope, {@code null} if not
	 *                 specified.
	 */
	public ResourceOwnerPasswordCredentialsGrant(final String username,
						     final Secret password,
						     final Scope scope) {

		super(GRANT_TYPE);

		if (username == null)
			throw new IllegalArgumentException("The username must not be null");

		this.username = username;

		if (password == null)
			throw new IllegalArgumentException("The password must not be null");

		this.password = password;

		this.scope = scope;
	}


	/**
	 * Gets the resource owner's username.
	 *
	 * @return The username.
	 */
	public String getUsername() {

		return username;
	}


	/**
	 * Gets the resource owner's password.
	 *
	 * @return The password.
	 */
	public Secret getPassword() {

		return password;
	}


	/**
	 * Gets the requested scope.
	 *
	 * @return The requested scope.
	 */
	public Scope getScope() {

		return scope;
	}


	@Override
	public Map<String,String> toParameters() {

		Map<String,String> params = new LinkedHashMap<String,String>();

		params.put("grant_type", GRANT_TYPE.getValue());

		params.put("username", username);
		params.put("password", password.getValue());

		if (scope != null)
			params.put("scope", scope.toString());

		return params;
	}


	/**
	 * Parses a resource owner password credentials grant from the
	 * specified parameters.
	 *
	 * <p>Example:
	 *
	 * <pre>
	 * grant_type=password
	 * username=johndoe
	 * password=A3ddj3w
	 * </pre>
	 *
	 * @param params The parameters.
	 *
	 * @return The resource owner password credentials grant.
	 *
	 * @throws ParseException If parsing failed.
	 */
	public static ResourceOwnerPasswordCredentialsGrant parse(final Map<String,String> params)
		throws ParseException {

		// Parse grant type
		String grantTypeString = params.get("grant_type");

		if (grantTypeString == null)
			throw new ParseException("Missing \"grant_type\" parameter", OAuth2Error.INVALID_REQUEST);

		GrantType grantType = new GrantType(grantTypeString);

		if (! grantType.equals(GRANT_TYPE))
			throw new ParseException("The \"grant_type\" must be " + GRANT_TYPE, OAuth2Error.INVALID_GRANT);

		// Parse the username
		String username = params.get("username");

		if (username == null || username.trim().isEmpty())
			throw new ParseException("Missing or empty \"username\" parameter", OAuth2Error.INVALID_REQUEST);

		// Parse the password
		String passwordString = params.get("password");

		if (passwordString == null || passwordString.trim().isEmpty())
			throw new ParseException("Missing or empty \"password\" parameter", OAuth2Error.INVALID_REQUEST);

		Secret password = new Secret(passwordString);

		// Parse optional scope
		String scopeValue = params.get("scope");

		Scope scope = null;

		if (scopeValue != null)
			scope = Scope.parse(scopeValue);

		return new ResourceOwnerPasswordCredentialsGrant(username, password, scope);
	}
}