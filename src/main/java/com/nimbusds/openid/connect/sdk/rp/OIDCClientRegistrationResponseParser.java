package com.nimbusds.openid.connect.sdk.rp;


import net.jcip.annotations.Immutable;

import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.client.ClientRegistrationErrorResponse;
import com.nimbusds.oauth2.sdk.client.ClientRegistrationResponse;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;


/**
 * Parser of OpenID Connect client registration response messages.
 *
 * <p>Related specifications:
 *
 * <ul>
 *     <li>OpenID Connect Dynamic Client Registration 1.0, section 3.2 and 3.3.
 *     <li>OAuth 2.0 Dynamic Client Registration Protocol (RFC 7591), sections
 *         2 and 3.2.
 * </ul>
 */
@Immutable
public class OIDCClientRegistrationResponseParser {
	
	
	/**
	 * Parses an OpenID Connect client registration response from the 
	 * specified HTTP response.
	 *
	 * @param httpResponse The HTTP response. Must not be {@code null}.
	 *
	 * @return The OpenID Connect client registration response.
	 *
	 * @throws ParseException If the HTTP response couldn't be parsed to an
	 *                        OpenID Connect client registration response.
	 */
	public static ClientRegistrationResponse parse(final HTTPResponse httpResponse)
		throws ParseException {

		final int statusCode = httpResponse.getStatusCode();

		if (statusCode == HTTPResponse.SC_OK || statusCode == HTTPResponse.SC_CREATED) {
			return OIDCClientInformationResponse.parse(httpResponse);
		} else {
			return ClientRegistrationErrorResponse.parse(httpResponse);
		}
	}
	
	
	/**
	 * Prevents public instantiation.
	 */
	private OIDCClientRegistrationResponseParser() { }
}
