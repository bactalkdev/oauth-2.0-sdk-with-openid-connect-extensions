package com.nimbusds.oauth2.sdk;


import java.net.URI;

import com.nimbusds.oauth2.sdk.http.HTTPRequest;


/**
 * Request message, serialises to an HTTP request.
 */
public interface Request extends Message {


	/**
	 * Gets the URI of the endpoint (HTTP or HTTPS) for which the request 
	 * is intended.
	 * 
	 * @return The endpoint URI, {@code null} if not specified.
	 */
	public URI getEndpointURI();
	
	
	/**
	 * Returns the matching HTTP request.
	 *
	 * @return The HTTP request.
	 *
	 * @throws SerializeException If the request message couldn't be
	 *                            serialised to an HTTP request.
	 */
	public HTTPRequest toHTTPRequest() 
		throws SerializeException;
}


