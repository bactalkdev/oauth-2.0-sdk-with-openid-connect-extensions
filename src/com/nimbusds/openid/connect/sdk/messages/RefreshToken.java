package com.nimbusds.openid.connect.sdk.messages;


import net.jcip.annotations.Immutable;


/**
 * OAuth 2.0 refresh token. This class is immutable.
 *
 * <p>Related specifications:
 *
 * <ul>
 *     <li>draft-ietf-oauth-v2-26, section 1.5.
 * </ul>
 *
 * @author Vladimir Dzhuvinov
 * @version $version$ (2012-11-13)
 */
@Immutable
public class RefreshToken {


	/**
	 * The refresh token value.
	 */
	private final String value;
	
	
	/**
	 * Creates a new refresh token.
	 *
	 * @param value The refresh token value. Must not be {@code null} or 
	 *              empty string.
	 *
	 * @throws IllegalArgumentException If the refresh token value is 
	 *                                  {@code null} or empty string.
	 */
	public RefreshToken(final String value) {
	
		if (value == null || value.trim().isEmpty())
			throw new IllegalArgumentException("The refresh token value must not be null or empty string");
		
		this.value = value;
	}
	
	
	/**
	 * Gets the value of this refresh token.
	 *
	 * @return The value.
	 */
	public String getValue() {
	
		return value;
	}
	
	
	/**
	 * Gets the string representation of this refresh token.
	 *
	 * <p>See {@link #getValue}.
	 *
	 * @return The refresh token value.
	 */
	public String toString() {
	
		return value;
	}
}