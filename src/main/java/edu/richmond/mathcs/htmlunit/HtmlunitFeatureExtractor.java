

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

import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.WebClient;
import java.io.*;
import java.util.*;
import java.util.regex.*;

public class HtmlunitFeatureExtractor {
	
	public static void main(String[] args) 	 {
	    try {
		final WebClient webClient = new WebClient();
		final HtmlPage page = webClient.getPage("http://www.google.com");

		String pageContent = page.getWebResponse().getContentAsString();

		Pattern scriptRegexp = 
		    Pattern.compile("[[<script]|[<Script]|[<SCRIPT]]*[[</script]|[</Script]|[</SCRIPT]]");

		Scanner pageScanner = new Scanner(pageContent);

		while (pageScanner.hasNext()) {

		    String currentScript = pageScanner.next(scriptRegexp);
		    System.out.print("\n\n\n" + currentScript + "\n\n\n");

		}


		System.out.println(page.getWebResponse().getContentAsString() );
		webClient.closeAllWindows();
	    }catch (Exception e ){
		
	    }

	}
}
