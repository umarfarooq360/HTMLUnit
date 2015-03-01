

package edu.richmond.mathcs.htmlunit;

import java.lang.AssertionError;

public class Assert {
    
    public static void assertEquals(String a, String b) {
	if (!a.equals(b)) {
	    throw new AssertionError();
	}
    }

    public static void assertTrue(boolean input) {
	if (!input) {
	    throw new AssertionError();
	}
    }
}
