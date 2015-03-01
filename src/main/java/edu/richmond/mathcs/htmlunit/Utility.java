/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java;

import com.gargoylesoftware.htmlunit.AlertHandler;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.ScriptPreProcessor;
import com.gargoylesoftware.htmlunit.WebWindowListener;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptErrorListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Omar Farooq
 */
public class Utility {

    public static WebClient GetWebClient(BrowserVersion bv, boolean redirect, boolean enableActiveX, WebWindowListener listen, ScriptPreProcessor processor,
            AlertHandler alert, JavaScriptErrorListener errorListener)
    {
        WebClient wc = new WebClient(bv);
        //ProxyConfig pc = new ProxyConfig("68.167.132.242",1814);
        //wc.setProxyConfig(pc);
        wc.addWebWindowListener(listen);
        wc.setRedirectEnabled(redirect);
        wc.setActiveXNative(enableActiveX);
        wc.setJavaScriptErrorListener(errorListener);
        wc.setScriptPreProcessor(processor);
        wc.setAlertHandler(alert);
        wc.setThrowExceptionOnScriptError(false);
        wc.setThrowExceptionOnFailingStatusCode(false);
        return wc;
    }

    public static boolean isPrintableChar( char c ) {
    Character.UnicodeBlock block = Character.UnicodeBlock.of( c );
    return (!Character.isISOControl(c)) &&
            c != KeyEvent.CHAR_UNDEFINED &&
            block != null &&
            block != Character.UnicodeBlock.SPECIALS;
}

    public static List<String> getSites()
    {
        List<String> siteList = new ArrayList();
        try {
            FileInputStream fs = new FileInputStream("sites.txt");
            DataInputStream ds = new DataInputStream(fs);
            BufferedReader br = new BufferedReader(new InputStreamReader(ds));
            String line;

            while((line = br.readLine()) != null)
            {
                siteList.add(line);
            }
            ds.close();
        } catch (Exception ex) {
            Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return siteList;
    }



}
