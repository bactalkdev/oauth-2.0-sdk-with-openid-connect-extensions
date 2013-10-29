package com.nimbusds.openid.connect.sdk.rp;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWSAlgorithm;

import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.client.ClientMetadata;
import com.nimbusds.oauth2.sdk.util.JSONObjectUtils;

import com.nimbusds.openid.connect.sdk.SubjectType;
import com.nimbusds.openid.connect.sdk.claims.ACR;


/**
 * OpenID Connect client metadata.
 *
 * <p>Related specifications:
 *
 * <ul>
 *     <li>OpenID Connect Dynamic Client Registration 1.0, section 2.
 *     <li>OAuth 2.0 Dynamic Client Registration Protocol 
 *         (draft-ietf-oauth-dyn-reg-14), section 2.
 * </ul>
 *
 * @author Vladimir Dzhuvinov
 */
public class OIDCClientMetadata extends ClientMetadata {
	
	
	/**
	 * The client application type.
	 */
	private ApplicationType applicationType;


	/**
	 * The subject identifier type for responses to this client.
	 */
	private SubjectType subjectType;


	/**
	 * Sector identifier URI.
	 */
	private URL sectorIDURI;
	
	
	/**
	 * Pre-registered OpenID Connect request URIs.
	 */
	private Set<URL> requestObjectURIs;


	/**
	 * The JSON Web Signature (JWS) algorithm required for the OpenID 
	 * Connect request objects sent by this client.
	 */
	private JWSAlgorithm requestObjectJWSAlg;


	/**
	 * The JSON Web Signature (JWS) algorithm required for
	 * {@code private_key_jwt} and {@code client_secret_jwt}
	 * authentication at the Token endpoint.
	 */
	private JWSAlgorithm authJWSAlg;


	/**
	 * The JSON Web Signature (JWS) algorithm required for the ID Tokens
	 * issued to this client.
	 */
	private JWSAlgorithm idTokenJWSAlg;


	/**
	 * The JSON Web Encryption (JWE) algorithm required for the ID Tokens
	 * issued to this client.
	 */
	private JWEAlgorithm idTokenJWEAlg;


	/**
	 * The encryption method (JWE enc) required for the ID Tokens issued to
	 * this client.
	 */
	private EncryptionMethod idTokenJWEEnc;


	/**
	 * The JSON Web Signature (JWS) algorithm required for the UserInfo
	 * responses to this client.
	 */
	private JWSAlgorithm userInfoJWSAlg;


	/**
	 * The JSON Web Encryption (JWE) algorithm required for the UserInfo
	 * responses to this client.
	 */
	private JWEAlgorithm userInfoJWEAlg;


	/**
	 * The encryption method (JWE enc) required for the UserInfo responses
	 * to this client.
	 */
	private EncryptionMethod userInfoJWEEnc;


	/**
	 * The default max authentication age, in seconds. If not specified 0.
	 */
	private int defaultMaxAge;


	/**
	 * If {@code true} the {@code auth_time} claim in the ID Token is
	 * required by default.
	 */
	private boolean requiresAuthTime;


	/**
	 * The default Authentication Context Class Reference (ACR) values, by
	 * order of preference.
	 */
	private List<ACR> defaultACRs;


	/**
	 * Authorisation server initiated login HTTPS URL.
	 */
	private URL initiateLoginURI;


	/**
	 * Logout redirect URL.
	 */
	private URL postLogoutRedirectURI;


	/** 
	 * Creates a new OpenID Connect client metadata instance.
	 */
	public OIDCClientMetadata() {

		super();
	}
	
	
	/**
	 * Creates a new OpenID Connect client metadata instance from the
	 * specified base OAuth 2.0 client metadata.
	 * 
	 * @param metadata The base OAuth 2.0 client metadata. Must not be
	 *                 {@code null}.
	 */
	public OIDCClientMetadata(final ClientMetadata metadata) {
		
		super(metadata);
	}
	
	
	/**
	 * Gets the client application type. Corresponds to the
	 * {@code application_type} client metadata field.
	 *
	 * @return The client application type, {@code null} if not specified.
	 */
	public ApplicationType getApplicationType() {

		return applicationType;
	}


	/**
	 * Sets the client application type. Corresponds to the
	 * {@code application_type} client metadata field.
	 *
	 * @param applicationType The client application type, {@code null} if
	 *                        not specified.
	 */
	public void setApplicationType(final ApplicationType applicationType) {

		this.applicationType = applicationType;
	}


	/**
	 * Gets the subject identifier type for responses to this client. 
	 * Corresponds to the {@code subject_type} client metadata field.
	 *
	 * @return The subject identifier type, {@code null} if not specified.
	 */
	public SubjectType getSubjectType() {

		return subjectType;
	}


	/**
	 * Sets the subject identifier type for responses to this client. 
	 * Corresponds to the {@code subject_type} client metadata field.
	 *
	 * @param subjectType The subject identifier type, {@code null} if not 
	 *                    specified.
	 */
	public void setSubjectType(final SubjectType subjectType) {

		this.subjectType = subjectType;
	}


	/**
	 * Gets the sector identifier URI. Corresponds to the 
	 * {@code sector_identifier_uri} client metadata field.
	 *
	 * @return The sector identifier URI, {@code null} if not specified.
	 */
	public URL getSectorIDURI() {

		return sectorIDURI;
	}


	/**
	 * Sets the sector identifier URI. Corresponds to the 
	 * {@code sector_identifier_uri} client metadata field.
	 *
	 * @param sectorIDURI The sector identifier URI, {@code null} if not 
	 *                    specified.
	 */
	public void setSectorIDURI(final URL sectorIDURI) {

		this.sectorIDURI = sectorIDURI;
	}
	
	
	/**
	 * Gets the pre-registered OpenID Connect request object URIs.
	 * Corresponds to the {@code request_uris} client metadata field.
	 * 
	 * @return The request object URIs, {@code null} if not specified.
	 */
	public Set<URL> getRequestObjectURIs() {
		
		return requestObjectURIs;
	}
	
	
	/**
	 * Sets the pre-registered OpenID Connect request object URIs.
	 * Corresponds to the {@code request_uris} client metadata field.
	 *
	 * @param requestObjectURIs The request object URIs, {@code null} if not
	 * specified.
	 */
	public void setRequestObjectURIs(final Set<URL> requestObjectURIs) {

		this.requestObjectURIs = requestObjectURIs;
	}


	/**
	 * Gets the JSON Web Signature (JWS) algorithm required for the OpenID 
	 * Connect request objects sent by this client. Corresponds to the 
	 * {@code request_object_signing_alg} client metadata field.
	 *
	 * @return The JWS algorithm, {@code null} if not specified.
	 */
	public JWSAlgorithm getRequestObjectJWSAlg() {

		return requestObjectJWSAlg;
	}


	/**
	 * Sets the JSON Web Signature (JWS) algorithm required for the OpenID 
	 * Connect request objects sent by this client. Corresponds to the 
	 * {@code request_object_signing_alg} client metadata field.
	 *
	 * @param requestObjectJWSAlg The JWS algorithm, {@code null} if not 
	 *                            specified.
	 */
	public void setRequestObjectJWSAlg(final JWSAlgorithm requestObjectJWSAlg) {

		this.requestObjectJWSAlg = requestObjectJWSAlg;
	}


	/**
	 * Gets the JSON Web Signature (JWS) algorithm required for
	 * {@code private_key_jwt} and {@code client_secret_jwt}
	 * authentication at the Token endpoint. Corresponds to the
	 * {@code token_endpoint_auth_signing_alg} client metadata field.
	 *
	 * @return The JWS algorithm, {@code null} if not specified.
	 */
	public JWSAlgorithm getTokenEndpointAuthJWSAlg() {

		return authJWSAlg;
	}


	/**
	 * Sets the JSON Web Signature (JWS) algorithm required for
	 * {@code private_key_jwt} and {@code client_secret_jwt}
	 * authentication at the Token endpoint. Corresponds to the
	 * {@code token_endpoint_auth_signing_alg} client metadata field.
	 *
	 * @param authJWSAlg The JWS algorithm, {@code null} if not specified.
	 */
	public void setTokenEndpointAuthJWSAlg(final JWSAlgorithm authJWSAlg) {

		this.authJWSAlg = authJWSAlg;
	}


	/**
	 * Gets the JSON Web Signature (JWS) algorithm required for the ID 
	 * Tokens issued to this client. Corresponds to the 
	 * {@code id_token_signed_response_alg} client metadata field.
	 *
	 * @return The JWS algorithm, {@code null} if not specified.
	 */
	public JWSAlgorithm getIDTokenJWSAlg() {

		return idTokenJWSAlg;
	}


	/**
	 * Sets the JSON Web Signature (JWS) algorithm required for the ID 
	 * Tokens issued to this client. Corresponds to the 
	 * {@code id_token_signed_response_alg} client metadata field.
	 *
	 * @param idTokenJWSAlg The JWS algorithm, {@code null} if not 
	 *                      specified.
	 */
	public void setIDTokenJWSAlg(final JWSAlgorithm idTokenJWSAlg) {

		this.idTokenJWSAlg = idTokenJWSAlg;
	}


	/**
	 * Gets the JSON Web Encryption (JWE) algorithm required for the ID 
	 * Tokens issued to this client. Corresponds to the 
	 * {@code id_token_encrypted_response_alg} client metadata field.
	 *
	 * @return The JWE algorithm, {@code null} if not specified.
	 */
	public JWEAlgorithm getIDTokenJWEAlg() {

		return idTokenJWEAlg;
	}


	/**
	 * Sets the JSON Web Encryption (JWE) algorithm required for the ID 
	 * Tokens issued to this client. Corresponds to the 
	 * {@code id_token_encrypted_response_alg} client metadata field.
	 *
	 * @param idTokenJWEAlg The JWE algorithm, {@code null} if not 
	 *                      specified.
	 */
	public void setIDTokenJWEAlg(final JWEAlgorithm idTokenJWEAlg) {

		this.idTokenJWEAlg = idTokenJWEAlg;
	}


	/**
	 * Gets the encryption method (JWE enc) required for the ID Tokens 
	 * issued to this client. Corresponds to the 
	 * {@code id_token_encrypted_response_enc} client metadata field.
	 *
	 * @return The JWE encryption method, {@code null} if not specified.
	 */
	public EncryptionMethod getIDTokenJWEEnc() {

		return idTokenJWEEnc;
	}


	/**
	 * Sets the encryption method (JWE enc) required for the ID Tokens 
	 * issued to this client. Corresponds to the 
	 * {@code id_token_encrypted_response_enc} client metadata field.
	 *
	 * @param idTokenJWEEnc The JWE encryption method, {@code null} if not 
	 *                      specified.
	 */
	public void setIDTokenJWEEnc(final EncryptionMethod idTokenJWEEnc) {

		this.idTokenJWEEnc = idTokenJWEEnc;
	}


	/**
	 * Gets the JSON Web Signature (JWS) algorithm required for the 
	 * UserInfo responses to this client. Corresponds to the 
	 * {@code userinfo_signed_response_alg} client metadata field.
	 *
	 * @return The JWS algorithm, {@code null} if not specified.
	 */
	public JWSAlgorithm getUserInfoJWSAlg() {

		return userInfoJWSAlg;
	}


	/**
	 * Sets the JSON Web Signature (JWS) algorithm required for the 
	 * UserInfo responses to this client. Corresponds to the
	 * {@code userinfo_signed_response_alg} client metadata field.
	 *
	 * @param userInfoJWSAlg The JWS algorithm, {@code null} if not 
	 *                       specified.
	 */
	public void setUserInfoJWSAlg(final JWSAlgorithm userInfoJWSAlg) {

		this.userInfoJWSAlg = userInfoJWSAlg;
	}


	/**
	 * Gets the JSON Web Encryption (JWE) algorithm required for the 
	 * UserInfo responses to this client. Corresponds to the 
	 * {@code userinfo_encrypted_response_alg} client metadata field.
	 *
	 * @return The JWE algorithm, {@code null} if not specified.
	 */
	public JWEAlgorithm getUserInfoJWEAlg() {

		return userInfoJWEAlg;
	}


	/**
	 * Sets the JSON Web Encryption (JWE) algorithm required for the 
	 * UserInfo responses to this client. Corresponds to the 
	 * {@code userinfo_encrypted_response_alg} client metadata field.
	 *
	 * @param userInfoJWEAlg The JWE algorithm, {@code null} if not
	 *                       specified.
	 */
	public void setUserInfoJWEAlg(final JWEAlgorithm userInfoJWEAlg) {

		this.userInfoJWEAlg = userInfoJWEAlg;
	}


	/**
	 * Gets the encryption method (JWE enc) required for the UserInfo 
	 * responses to this client. Corresponds to the 
	 * {@code userinfo_encrypted_response_enc} client metadata field.
	 *
	 * @return The JWE encryption method, {@code null} if not specified.
	 */
	public EncryptionMethod getUserInfoJWEEnc() {

		return userInfoJWEEnc;
	}


	/**
	 * Sets the encryption method (JWE enc) required for the UserInfo 
	 * responses to this client. Corresponds to the 
	 * {@code userinfo_encrypted_response_enc} client metadata field.
	 *
	 * @param userInfoJWEEnc The JWE encryption method, {@code null} if not 
	 *                       specified.
	 */
	public void setUserInfoJWEEnc(final EncryptionMethod userInfoJWEEnc) {

		this.userInfoJWEEnc = userInfoJWEEnc;
	}


	/**
	 * Gets the default maximum authentication age. Corresponds to the 
	 * {@code default_max_age} client metadata field.
	 *
	 * @return The default max authentication age, in seconds. If not
	 *         specified 0.
	 */
	public int getDefaultMaxAge() {

		return defaultMaxAge;
	}


	/**
	 * Sets the default maximum authentication age. Corresponds to the 
	 * {@code default_max_age} client metadata field.
	 *
	 * @param defaultMaxAge The default max authentication age, in seconds.
	 *                      If not specified 0.
	 */
	public void setDefaultMaxAge(final int defaultMaxAge) {

		this.defaultMaxAge = defaultMaxAge;
	}


	/**
	 * Gets the default requirement for the {@code auth_time} claim in the
	 * ID Token. Corresponds to the {@code require_auth_time} client 
	 * metadata field.
	 *
	 * @return If {@code true} the {@code auth_Time} claim in the ID Token 
	 *         is required by default.
	 */
	public boolean requiresAuthTime() {

		return requiresAuthTime;
	}


	/**
	 * Sets the default requirement for the {@code auth_time} claim in the
	 * ID Token. Corresponds to the {@code require_auth_time} client 
	 * metadata field.
	 *
	 * @param requiresAuthTime If {@code true} the {@code auth_Time} claim 
	 *                         in the ID Token is required by default.
	 */
	public void requiresAuthTime(final boolean requiresAuthTime) {

		this.requiresAuthTime = requiresAuthTime;
	}


	/**
	 * Gets the default Authentication Context Class Reference (ACR) 
	 * values. Corresponds to the {@code default_acr_values} client 
	 * metadata field.
	 *
	 * @return The default ACR values, by order of preference, 
	 *         {@code null} if not specified.
	 */
	public List<ACR> getDefaultACRs() {

		return defaultACRs;
	}


	/**
	 * Sets the default Authentication Context Class Reference (ACR)
	 * values. Corresponds to the {@code default_acr_values} client 
	 * metadata field.
	 *
	 * @param defaultACRs The default ACRs, by order of preference, 
	 *                    {@code null} if not specified.
	 */
	public void setDefaultACRs(final List<ACR> defaultACRs) {

		this.defaultACRs = defaultACRs;
	}


	/**
	 * Gets the HTTPS URI that the authorisation server can call to
	 * initiate a login at the client. Corresponds to the 
	 * {@code initiate_login_uri} client metadata field.
	 *
	 * @return The login URI, {@code null} if not specified.
	 */
	public URL getInitiateLoginURI() {

		return initiateLoginURI;
	}


	/**
	 * Sets the HTTPS URI that the authorisation server can call to
	 * initiate a login at the client. Corresponds to the 
	 * {@code initiate_login_uri} client metadata field.
	 *
	 * @param loginURI The login URI, {@code null} if not specified.
	 */
	public void setInitiateLoginURI(final URL loginURI) {

		this.initiateLoginURI = loginURI;
	}


	/**
	 * Gets the post logout redirect URI. Corresponds to the 
	 * {@code post_logout_redirect_uri} client metadata field.
	 *
	 * @return The logout URI, {@code null} if not specified.
	 */
	public URL getPostLogoutRedirectURI() {

		return postLogoutRedirectURI;
	}


	/**
	 * Sets the post logout redirect URI. Corresponds to the 
	 * {@code post_logout_redirect_uri} client metadata field.
	 *
	 * @param logoutURI The logout URI, {@code null} if not specified.
	 */
	public void setPostLogoutRedirectURI(final URL logoutURI) {

		this.postLogoutRedirectURI = logoutURI;
	}
	
	
	/**
	 * Applies the client metadata defaults where no values have been
	 * specified.
	 * 
	 * <ul>
	 *     <li>The response types default to {@code ["code"]}.
	 *     <li>The grant types default to {@code "authorization_code".}
	 *     <li>The client authentication method defaults to 
	 *         "client_secret_basic".
	 *     <li>The ID token JWS algorithm defaults to "RS256".
	 * </ul>
	 */
	@Override
	public void applyDefaults() {
		
		super.applyDefaults();
		
		if (idTokenJWSAlg == null) {
			idTokenJWSAlg = JWSAlgorithm.RS256;
		}
	}


	@Override
	public JSONObject toJSONObject() {

		JSONObject o = super.toJSONObject(false);

		o.putAll(getCustomFields());
		
		if (applicationType != null)
			o.put("application_type", applicationType.toString());


		if (subjectType != null)
			o.put("subject_type", subjectType.toString());


		if (sectorIDURI != null)
			o.put("sector_identifier_uri", sectorIDURI.toString());
		
		
		if (requestObjectURIs != null) {
			
			JSONArray uriList = new JSONArray();
			
			for (URL uri: requestObjectURIs)
				uriList.add(uri.toString());
			
			o.put("request_uris", uriList);
		}


		if (requestObjectJWSAlg != null)
			o.put("request_object_signing_alg", requestObjectJWSAlg.getName());

		if (authJWSAlg != null)
			o.put("token_endpoint_auth_signing_alg", authJWSAlg.getName());


		if (idTokenJWSAlg != null)
			o.put("id_token_signed_response_alg", idTokenJWSAlg.getName());


		if (idTokenJWEAlg != null)
			o.put("id_token_encrypted_response_alg", idTokenJWEAlg.getName());


		if (idTokenJWEEnc != null)
			o.put("id_token_encrypted_response_enc", idTokenJWEEnc.getName());


		if (userInfoJWSAlg != null)
			o.put("userinfo_signed_response_alg", userInfoJWSAlg.getName());


		if (userInfoJWEAlg != null)
			o.put("userinfo_encrypted_response_alg", userInfoJWEAlg.getName());


		if (userInfoJWEEnc != null)
			o.put("userinfo_encrypted_response_enc", userInfoJWEEnc.getName());


		if (defaultMaxAge > 0)
			o.put("default_max_age", defaultMaxAge);


		o.put("require_auth_time", requiresAuthTime);


		if (defaultACRs != null) {

			JSONArray acrList = new JSONArray();

			for (ACR acr: defaultACRs)
				acrList.add(acr);

			o.put("default_acr_values", acrList);
		}


		if (initiateLoginURI != null)
			o.put("initiate_login_uri", initiateLoginURI.toString());


		if (postLogoutRedirectURI != null)
			o.put("post_logout_redirect_uri", postLogoutRedirectURI.toString());

		return o;
	}


	/**
	 * Parses an OpenID Connect client metadata instance from the specified
	 * JSON object.
	 *
	 * @param jsonObject The JSON object to parse. Must not be 
	 *                   {@code null}.
	 *
	 * @return The OpenID Connect client metadata.
	 *
	 * @throws ParseException If the JSON object couldn't be parsed to an
	 *                        OpenID Connect client metadata instance.
	 */
	public static OIDCClientMetadata parse(final JSONObject jsonObject)
		throws ParseException {

		ClientMetadata baseMetadata = ClientMetadata.parse(jsonObject);
		
		OIDCClientMetadata metadata = new OIDCClientMetadata(baseMetadata);

		// Parse the OIDC-specific fields from the custom OAuth 2.0 dyn
		// reg fields

		JSONObject oidcFields = baseMetadata.getCustomFields();
		
		if (oidcFields.containsKey("application_type")) {
			metadata.setApplicationType(JSONObjectUtils.getEnum(jsonObject, 
				                                          "application_type", 
				                                          ApplicationType.class));

			oidcFields.remove("application_type");
		}
		
		if (jsonObject.containsKey("subject_type")) {
			metadata.setSubjectType(JSONObjectUtils.getEnum(jsonObject, "subject_type", SubjectType.class));
			oidcFields.remove("subject_type");
		}

		if (jsonObject.containsKey("sector_identifier_uri")) {
			metadata.setSectorIDURI(JSONObjectUtils.getURL(jsonObject, "sector_identifier_uri"));
			oidcFields.remove("sector_identifier_uri");
		}

		if (jsonObject.containsKey("request_uris")) {
			
			Set<URL> requestURIs = new LinkedHashSet<URL>();
			
			for (String uriString: JSONObjectUtils.getStringArray(jsonObject, "request_uris")) {
				
				try {
					requestURIs.add(new URL(uriString));
					
				} catch (MalformedURLException e) {
					
					throw new ParseException("Invalid \"request_uris\" parameter");
				}
			}
			
			metadata.setRequestObjectURIs(requestURIs);
			oidcFields.remove("request_uris");
		}
		
		if (jsonObject.containsKey("request_object_signing_alg")) {
			metadata.setRequestObjectJWSAlg(new JWSAlgorithm(
				JSONObjectUtils.getString(jsonObject, "request_object_signing_alg")));

			oidcFields.remove("request_object_signing_alg");
		}

		if (jsonObject.containsKey("token_endpoint_auth_signing_alg")) {
			metadata.setTokenEndpointAuthJWSAlg(new JWSAlgorithm(
				JSONObjectUtils.getString(jsonObject, "token_endpoint_auth_signing_alg")));

			oidcFields.remove("token_endpoint_auth_signing_alg");
		}

		if (jsonObject.containsKey("id_token_signed_response_alg")) {
			metadata.setIDTokenJWSAlg(new JWSAlgorithm(
				JSONObjectUtils.getString(jsonObject, "id_token_signed_response_alg")));

			oidcFields.remove("id_token_signed_response_alg");
		}

		if (jsonObject.containsKey("id_token_encrypted_response_alg")) {
			metadata.setIDTokenJWEAlg(new JWEAlgorithm(
				JSONObjectUtils.getString(jsonObject, "id_token_encrypted_response_alg")));

			oidcFields.remove("id_token_encrypted_response_alg");
		}

		if (jsonObject.containsKey("id_token_encrypted_response_enc")) {
			metadata.setIDTokenJWEEnc(new EncryptionMethod(
				JSONObjectUtils.getString(jsonObject, "id_token_encrypted_response_enc")));

			oidcFields.remove("id_token_encrypted_response_enc");
		}

		if (jsonObject.containsKey("userinfo_signed_response_alg")) {
			metadata.setUserInfoJWSAlg(new JWSAlgorithm(
				JSONObjectUtils.getString(jsonObject, "userinfo_signed_response_alg")));

			oidcFields.remove("userinfo_signed_response_alg");
		}

		if (jsonObject.containsKey("userinfo_encrypted_response_alg")) {
			metadata.setUserInfoJWEAlg(new JWEAlgorithm(
				JSONObjectUtils.getString(jsonObject, "userinfo_encrypted_response_alg")));

			oidcFields.remove("userinfo_encrypted_response_alg");
		}

		if (jsonObject.containsKey("userinfo_encrypted_response_enc")) {
			metadata.setUserInfoJWEEnc(new EncryptionMethod(
				JSONObjectUtils.getString(jsonObject, "userinfo_encrypted_response_enc")));

			oidcFields.remove("userinfo_encrypted_response_enc");
		}

		if (jsonObject.containsKey("default_max_age")) {
			metadata.setDefaultMaxAge(JSONObjectUtils.getInt(jsonObject, "default_max_age"));
			oidcFields.remove("default_max_age");
		}

		if (jsonObject.containsKey("require_auth_time")) {
			metadata.requiresAuthTime(JSONObjectUtils.getBoolean(jsonObject, "require_auth_time"));
			oidcFields.remove("require_auth_time");
		}

		if (jsonObject.containsKey("default_acr_values")) {

			List<ACR> acrValues = new LinkedList<ACR>();

			for (String acrString: JSONObjectUtils.getStringArray(jsonObject, "default_acr_values"))
				acrValues.add(new ACR(acrString));

			metadata.setDefaultACRs(acrValues);

			oidcFields.remove("default_acr_values");
		}

		if (jsonObject.containsKey("initiate_login_uri")) {
			metadata.setInitiateLoginURI(JSONObjectUtils.getURL(jsonObject, "initiate_login_uri"));
			oidcFields.remove("initiate_login_uri");
		}

		if (jsonObject.containsKey("post_logout_redirect_uri")) {
			metadata.setPostLogoutRedirectURI(JSONObjectUtils.getURL(jsonObject, "post_logout_redirect_uri"));
			oidcFields.remove("post_logout_redirect_uri");
		}

		// The remaining fields are custom
		metadata.setCustomFields(oidcFields);

		return metadata;
	}
}
