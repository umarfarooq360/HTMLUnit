package edu.richmond.mathcs.htmlunit;

import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.WebClient;
import java.io.*;
import java.util.*;


public class test1{
	
	public static void main(String[] args) 	 {
	    try{
	    final WebClient webClient = new WebClient();
	    final HtmlPage page = webClient.getPage("http://www.google.com");
	    
	    System.out.println(page.getWebResponse().getContentAsString() );
	    webClient.closeAllWindows();
	}catch (Exception e ){

	}
	}
}