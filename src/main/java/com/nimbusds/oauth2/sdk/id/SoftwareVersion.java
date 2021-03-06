package com.nimbusds.oauth2.sdk.id;


import net.jcip.annotations.Immutable;


/**
 * Version identifier for an OAuth 2.0 client software.
 *
 * <p>Related specifications:
 *
 * <ul>
 *     <li>OAuth 2.0 Dynamic Client Registration Protocol (RFC 7591), section
 *         2.
 * </ul>
 */
@Immutable
public final class SoftwareVersion extends Identifier {


	/**
	 * Creates a new OAuth 2.0 client software version identifier with the
	 * specified value.
	 *
	 * @param value The software version identifier value. Must not be
	 *              {@code null} or empty string.
	 */
	public SoftwareVersion(final String value) {

		super(value);
	}


	@Override
	public boolean equals(final Object object) {

		return object instanceof SoftwareVersion &&
		       this.toString().equals(object.toString());
	}
}
