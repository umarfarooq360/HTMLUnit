

package edu.richmond.mathcs.htmlunit;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.cookie.CookieOrigin;
import org.w3c.css.sac.ErrorHandler;



public class DougTest {

    public static void main(String[] arg) {

	System.out.print("DougTest says Hello World!\n");
	System.out.print("Running the demo htmlunit test.\n");
	HtmlunitTester tester = new HtmlunitTester();

	try {
	    tester.homePage();
	} catch (Exception e) {
	    System.out.print("Caught exception in Tester!\n");
	}



    }

}
