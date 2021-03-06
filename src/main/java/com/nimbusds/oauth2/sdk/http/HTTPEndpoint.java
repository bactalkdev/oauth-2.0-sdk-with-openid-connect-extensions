package com.nimbusds.oauth2.sdk.http;


/**
 * HTTP endpoint.
 */
public interface HTTPEndpoint {


	/**
	 * Processes an HTTP request.
	 *
	 * @param httpRequest The HTTP request to process. Must not be
	 *                    {@code null}.
	 *
	 * @return The HTTP response.
	 */
	HTTPResponse process(final HTTPRequest httpRequest);
}