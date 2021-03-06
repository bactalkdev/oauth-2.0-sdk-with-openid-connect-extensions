package com.nimbusds.oauth2.sdk;


import java.util.LinkedHashMap;
import java.util.Map;

import com.nimbusds.jose.util.Base64URL;
import net.jcip.annotations.Immutable;


/**
 * SAML 2.0 bearer grant. Used in access token requests with a SAML 2.0 bearer
 * assertion.
 *
 * <p>Related specifications:
 *
 * <ul>
 *     <li>Assertion Framework for OAuth 2.0 Client Authentication and
 *         Authorization Grants (RFC 7521), section 4.1.
 *     <li>SAML 2.0 Profile for OAuth 2.0 Client Authentication and
 *         Authorization Grants (RFC 7522), section-2.1.
 * </ul>
 */
@Immutable
public class SAML2BearerGrant extends AssertionGrant {


	/**
	 * The grant type.
	 */
	public static final GrantType GRANT_TYPE = GrantType.SAML2_BEARER;


	/**
	 * Cached {@code unsupported_grant_type} exception.
	 */
	private static final ParseException UNSUPPORTED_GRANT_TYPE_EXCEPTION
			= new ParseException("The \"grant_type\" must be " + GRANT_TYPE, OAuth2Error.UNSUPPORTED_GRANT_TYPE);


	/**
	 * The SAML 2.0 assertion.
	 */
	private final Base64URL assertion;


	/**
	 * Creates a new SAML 2.0 bearer assertion grant.
	 *
	 * @param assertion The SAML 2.0 bearer assertion. Must not be
	 *                  {@code null}.
	 */
	public SAML2BearerGrant(final Base64URL assertion) {

		super(GRANT_TYPE);

		if (assertion == null)
			throw new IllegalArgumentException("The SAML 2.0 bearer assertion must not be null");

		this.assertion = assertion;
	}


	/**
	 * Gets the SAML 2.0 bearer assertion.
	 *
	 * @return The SAML 2.0 bearer assertion.
	 */
	public Base64URL getSAML2Assertion() {

		return assertion;
	}


	@Override
	public String getAssertion() {

		return assertion.toString();
	}


	@Override
	public Map<String,String> toParameters() {

		Map<String,String> params = new LinkedHashMap<>();
		params.put("grant_type", GRANT_TYPE.getValue());
		params.put("assertion", assertion.toString());
		return params;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		SAML2BearerGrant that = (SAML2BearerGrant) o;

		return assertion.equals(that.assertion);

	}


	@Override
	public int hashCode() {
		return assertion.hashCode();
	}


	/**
	 * Parses a SAML 2.0 bearer grant from the specified parameters.
	 *
	 * <p>Example:
	 *
	 * <pre>
	 * grant_type=urn%3Aietf%3Aparams%3Aoauth%3Agrant-type%3Asaml2-
	 * bearer&assertion=PEFzc2VydGlvbiBJc3N1ZUluc3RhbnQ9IjIwMTEtMDU
	 * [...omitted for brevity...]aG5TdGF0ZW1lbnQ-PC9Bc3NlcnRpb24-
	 * </pre>
	 *
	 * @param params The parameters.
	 *
	 * @return The SAML 2.0 bearer grant.
	 *
	 * @throws ParseException If parsing failed.
	 */
	public static SAML2BearerGrant parse(final Map<String,String> params)
		throws ParseException {

		// Parse grant type
		String grantTypeString = params.get("grant_type");

		if (grantTypeString == null)
			throw MISSING_GRANT_TYPE_PARAM_EXCEPTION;

		if (! GrantType.parse(grantTypeString).equals(GRANT_TYPE))
			throw UNSUPPORTED_GRANT_TYPE_EXCEPTION;

		// Parse JWT assertion
		String assertionString = params.get("assertion");

		if (assertionString == null || assertionString.trim().isEmpty())
			throw MISSING_ASSERTION_PARAM_EXCEPTION;

		return new SAML2BearerGrant(new Base64URL(assertionString));
	}
}
