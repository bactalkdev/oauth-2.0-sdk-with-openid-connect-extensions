package com.nimbusds.openid.connect.messages;


import com.nimbusds.openid.connect.ParseException;


/**
 * Enumeration of the OAuth 2.0 grant types used by OpenID Connect.
 *
 * <p>See draft-ietf-oauth-v2-26
 *
 * @author Vladimir Dzhuvinov
 * @version 0.9 (2012-05-11)
 */
public enum GrantType {

	
	/**
	 * Authorisation code.
	 */
	AUTHORIZATION_CODE,
	
	
	/**
	 * Refresh token.
	 */
	REFRESH_TOKEN;
	
	
	/**
	 * Returns the canonical string representation of this grant type.
	 *
	 * @return The canonical string representation.
	 */
	public String toString() {
	
		return super.toString().toLowerCase();
	}
	
	
	/**
	 * Parses a grant type.
	 *
	 * @param s A canonical string representation of a grant type. Must not
	 *          be {@code null}.
	 *
	 * @return The parsed grant type.
	 *
	 * @throws ParseException If the string doesn't match a grant type.
	 */
	public static GrantType parse(final String s)
		throws ParseException {
	
		if (s == null || s.trim().isEmpty())
			throw new ParseException("Couldn't parse grant type: Null or empty string");
		
		if (s.equals("authorization_code"))
			return AUTHORIZATION_CODE;
			
		else if (s.equals("refresh_token"))
			return REFRESH_TOKEN;
			
		else
			throw new ParseException("Couldn't parse grant type: Unexpected grant type: " + s);
	}
}