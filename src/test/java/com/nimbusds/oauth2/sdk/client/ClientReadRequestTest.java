package com.nimbusds.oauth2.sdk.client;


import java.net.URI;
import java.net.URL;

import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.token.BearerTokenError;
import junit.framework.TestCase;

import com.nimbusds.oauth2.sdk.http.HTTPRequest;
import com.nimbusds.oauth2.sdk.token.BearerAccessToken;


/**
 * Tests the client read request.
 */
public class ClientReadRequestTest extends TestCase {


	public void testCycle()
		throws Exception {

		URI uri = new URI("https://c2id.com/client-reg/123");
		BearerAccessToken accessToken = new BearerAccessToken();

		ClientReadRequest request = new ClientReadRequest(uri, accessToken);

		assertEquals(uri, request.getEndpointURI());
		assertEquals(accessToken, request.getAccessToken());

		HTTPRequest httpRequest = request.toHTTPRequest();

		assertEquals(HTTPRequest.Method.GET, httpRequest.getMethod());
		assertEquals(uri, httpRequest.getURL().toURI());
		assertEquals(accessToken.toAuthorizationHeader(), httpRequest.getAuthorization());

		request = ClientReadRequest.parse(httpRequest);

		assertEquals(uri.toString(), request.getEndpointURI().toString());
		assertEquals(accessToken.getValue(), request.getAccessToken().getValue());
	}


	public void testParseWithMissingAuthorizationHeader()
		throws Exception {

		HTTPRequest httpRequest = new HTTPRequest(HTTPRequest.Method.GET, new URL("https://c2id.com/client-reg/123"));

		try {
			ClientReadRequest.parse(httpRequest);

			fail();

		} catch (ParseException e) {

			assertTrue(e.getErrorObject() instanceof BearerTokenError);

			BearerTokenError bte = (BearerTokenError)e.getErrorObject();

			assertEquals(401, bte.getHTTPStatusCode());
			assertNull(bte.getCode());
			assertEquals("Bearer", bte.toWWWAuthenticateHeader());
		}
	}
}
