package com.nimbusds.oauth2.sdk;


import junit.framework.TestCase;


/**
 * Tests the scope token class.
 */
public class ScopeValueTest extends TestCase {


	public void testMinimalConstructor() {

		Scope.Value t = new Scope.Value("read");

		assertEquals("read", t.getValue());

		assertNull(t.getRequirement());
	}


	public void testFullConstructor() {

		Scope.Value t = new Scope.Value("write", Scope.Value.Requirement.OPTIONAL);

		assertEquals("write", t.getValue());

		assertEquals(Scope.Value.Requirement.OPTIONAL, t.getRequirement());
	}


	public void testEquality() {

		Scope.Value t1 = new Scope.Value("read");
		Scope.Value t2 = new Scope.Value("read");

		assertTrue(t1.equals(t2));
	}
}
