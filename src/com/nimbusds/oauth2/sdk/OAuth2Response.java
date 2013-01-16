package com.nimbusds.oauth2.sdk;


import com.nimbusds.oauth2.sdk.http.HTTPResponse;


/**
 * OAuth 2.0 response message.
 *
 * @author Vladimir Dzhuvinov
 * @version $version$ (2013-01-14)
 */
public interface OAuth2Response extends OAuth2Message {

	
	/**
	 * Returns the matching HTTP response.
	 *
	 * @return The HTTP response.
	 *
	 * @throws SerializeException If the OAuth 2.0 response message
	 *                            couldn't be serialised to an HTTP 
	 *                            response.
	 */
	public HTTPResponse toHTTPResponse() 
		throws SerializeException;
}

