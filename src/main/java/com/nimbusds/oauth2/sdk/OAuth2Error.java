package com.nimbusds.oauth2.sdk;


import com.nimbusds.oauth2.sdk.http.HTTPResponse;


/**
 * Standard OAuth 2.0 authorisation and token endpoint errors.
 *
 * <p>The set HTTP status code is ignored for authorisation errors passed by
 * HTTP redirection. Errors that are only used by at the authorisation endpoint
 * are supplied with a matching HTTP status code in case they are used in a
 * different context.
 */
public final class OAuth2Error {


	// Common OAuth 2.0 authorisation errors
	
	/**
	 * The request is missing a required parameter, includes an invalid 
	 * parameter, or is otherwise malformed.
	 */
	public static final ErrorObject INVALID_REQUEST = 
		new ErrorObject("invalid_request", "Invalid request", HTTPResponse.SC_BAD_REQUEST);
	
	
	/**
	 * The client is not authorised to request an authorisation code using 
	 * this method.
	 */
	public static final ErrorObject UNAUTHORIZED_CLIENT =
		new ErrorObject("unauthorized_client", "Unauthorized client", HTTPResponse.SC_BAD_REQUEST);
	
	
	/**
	 * The resource owner or authorisation server denied the request.
	 */
	public static final ErrorObject ACCESS_DENIED =
		new ErrorObject("access_denied", "Access denied by resource owner or authorization server", HTTPResponse.SC_FORBIDDEN);
	
	
	/**
	 * The authorisation server does not support obtaining an authorisation 
	 * code using this method.
	 */
	public static final ErrorObject UNSUPPORTED_RESPONSE_TYPE =
		new ErrorObject("unsupported_response_type", "Unsupported response type", HTTPResponse.SC_BAD_REQUEST);
	
	
	/**
	 * The requested scope is invalid, unknown, or malformed.
	 */
	public static final ErrorObject INVALID_SCOPE =
		new ErrorObject("invalid_scope", "Invalid, unknown or malformed scope", HTTPResponse.SC_BAD_REQUEST);
	
	
	/**
	 * The authorisation server encountered an unexpected condition which 
	 * prevented it from fulfilling the request.
	 */
	public static final ErrorObject SERVER_ERROR =
		new ErrorObject("server_error", "Unexpected server error", HTTPResponse.SC_SERVER_ERROR);
	
	
	/**
	 * The authorisation server is currently unable to handle the request 
	 * due to a temporary overloading or maintenance of the server.
	 */
	public static final ErrorObject TEMPORARILY_UNAVAILABLE =
		new ErrorObject("temporarily_unavailable", "The authorization server is temporarily unavailable", HTTPResponse.SC_SERVICE_UNAVAILABLE);
	
	
	// Token, Base OAuth 2.0 authorisation errors, section 5.2
	
	/**
	 * Client authentication failed (e.g. unknown client, no client 
	 * authentication included, or unsupported authentication method).
	 */
	public static final ErrorObject INVALID_CLIENT =
		new ErrorObject("invalid_client", "Client authentication failed", HTTPResponse.SC_UNAUTHORIZED);
	
	
	/**
	 * The provided authorisation grant (e.g. authorisation code, resource 
	 * owner credentials) or refresh token is invalid, expired, revoked, 
	 * does not match the redirection URI used in the authorization request,
	 * or was issued to another client.
	 */
	public static final ErrorObject INVALID_GRANT =
		new ErrorObject("invalid_grant", "Invalid grant", HTTPResponse.SC_BAD_REQUEST);
	
	
	/**
	 * The authorisation grant type is not supported by the authorisation 
	 * server.
	 */
	public static final ErrorObject UNSUPPORTED_GRANT_TYPE =
		new ErrorObject("unsupported_grant_type", "Unsupported grant type", HTTPResponse.SC_BAD_REQUEST);

	
	/**
	 * Prevents public instantiation.
	 */
	private OAuth2Error() { }
}