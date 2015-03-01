package main.java;

import com.gargoylesoftware.htmlunit.AlertHandler;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.JavaScriptPage;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.PluginConfiguration;
import com.gargoylesoftware.htmlunit.ScriptException;
import com.gargoylesoftware.htmlunit.ScriptPreProcessor;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.TextPage;
import com.gargoylesoftware.htmlunit.UnexpectedPage;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebWindowEvent;
import com.gargoylesoftware.htmlunit.WebWindowListener;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlLink;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlScript;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptErrorListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.net.URI;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Omar Farooq
 *
 * http://www.xiaonei.com
 * http://goo.gl/CRmEu
 * http://padrejonacir.sites.uol.com.br
 * bfivuzop.cn
 * http://pastehtml.com/view/1ejsxhj.html
 * http://pastehtml.com/view/1ejs7ns.html
 * http://www.americanbusinessmedia.com/abm/Default.asp
 * http://pastehtml.com/view/avpa2kn9e.html
 *
 * Malicious sites:
 * http://grupoarcoiris.org/
 * suplementate.com
 * http://pro-fm.net
 * http://quake2012.ru/in.php?a=QQkFBwQHBAEABQQMEkcJBQcEBwQCBgwNBA==
 * http://keod.co.kr
 */
public class Main
{
    static List<String> redirectSiteList = new ArrayList<String>();
    static List<String> javaScriptList = new ArrayList<String>();
    static boolean done = false;
    private static WebClient webClient;
    static int redirects = 0;

    public static void main(String[] args) throws IOException
    {
        Stats.setStatus(true);

        //List<String> siteList = Utility.getSites();
        List<String> siteList = new ArrayList<String>();

        if(args.length>0){
            addUrls(siteList, args[0]);
        }
        //test site
        siteList.add("https://www.google.com");

        for(String site : siteList)
        {
            redirects =0;
            int firefoxRed = getFirefoxRedirects(site);
            Stats.clearAll();
            ProcessSite(site);
            int numRed = redirectSiteList.size();
            Stats.printStats();

            Stats.outputStats(site, redirects, Math.abs(redirects-firefoxRed));
        }
    }

    private static int getFirefoxRedirects(String theSite)
    {
        WebWindowListener IElistener = new WebWindowListener()
        {
            String previousDomain = "";

            public void webWindowOpened(WebWindowEvent event)
            {
                throw new UnsupportedOperationException("Not supported yet.");
            }
            public void webWindowContentChanged(WebWindowEvent event)
            {
                String newURL = webClient.getCurrentWindow().getEnclosedPage().getUrl().toString();
                int domainBreaker = newURL.indexOf('/', 8);
                String newDomain = newURL.substring(0,domainBreaker);
                
                if (!newDomain.equals(previousDomain))
                {
                    previousDomain = newDomain;
                    redirects++;
                }
            }

            public void webWindowClosed(WebWindowEvent event)
            {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };

        webClient = Utility.GetWebClient(BrowserVersion.FIREFOX_3_6  , true, true, IElistener, processor, alert, errorListener);
        redirectSiteList.clear();
        try
        {
            webClient.getPage(theSite);
           
        } 
        catch (IOException ex)
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (FailingHttpStatusCodeException ex)
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        return redirects;
    }

    private static void ProcessSite(String site) throws IOException
    {
        redirectSiteList.clear();
        URI url = URI.create(site);
        redirectSiteList.add(url.toString());

        
        webClient = Utility.GetWebClient(BrowserVersion.INTERNET_EXPLORER_8 , true, true, listen, processor, alert, errorListener);

        webClient.setRedirectEnabled(false);

        done = true;
        

        try
        {
            forceJavascriptExecution(webClient);
        } 
        catch (Exception ex)
        {
        }
    }

    private static void forceJavascriptExecution(WebClient webClient)
    {
        HtmlPage page = null;
        for (String redirectSite : redirectSiteList)
        {
            try
            {
                
                page = webClient.getPage(redirectSite);

            } 
            catch (Exception ex)
            {
                System.out.println("Unable to get redirect site " + redirectSite + " due to " + ex.getMessage());
                continue;
            }
            /*
            
            
            
            Iterable<HtmlElement> hElements = null;
            DomNodeList<HtmlElement> element = null;
            try
            {

                element= page.getElementsByTagName("script");
                for (int i = 0; i < element.size(); i++){
                    HtmlElement script = element.get(i);
                    for (int j = 0; j < script.getChildNodes().size(); j++){
                        DomNodeList<DomNode> childNode = script.getChildNodes();
                        page.executeJavaScript(childNode.get(j).asXml()); 
                    }
                }
            } 
            catch (Exception a)
            {
                System.out.println(a.getMessage());
                return;
            }

            for (HtmlElement e : hElements)
            {
                if (e instanceof HtmlScript)
                {
                    HtmlScript scriptE = (HtmlScript) e;
                    if (scriptE.getSrcAttribute().equals(""))
                    {
                        ScriptResult s;
                        try
                        {
                            //page.adoptNode(scriptE);
                            //page.executeJavaScript(scriptE.getTextContent());

                            javaScriptList.add(scriptE.getTextContent());
                            //s = page.executeJavaScript(scriptE.getTextContent());
                            System.out.println("The following javascript has been executed:");
                            System.out.println(scriptE.getTextContent());
                        } 
                        catch (Exception ex)
                        {
                            System.out.println("Unable to execute the following javascript:");
                            System.out.println(scriptE.getTextContent());
                        }
                    }
                    
                    else
                    {
                        try
                        {
                            String srcAttribute = scriptE.getSrcAttribute();

                            URI u = URI.create(redirectSite);
                            u = u.resolve(srcAttribute);
                            srcAttribute = u.toString();
                            Object objectPage = webClient.getPage(srcAttribute);
                            //redirectSiteList.remove(srcAttribute);

                            if (objectPage instanceof JavaScriptPage)
                            {
                                JavaScriptPage scriptPage = (JavaScriptPage) objectPage;
                                //javaScriptList.add("Javascript at: " + scriptE.getSrcAttribute() + "\n\n" + scriptPage.getWebResponse().getContentAsString());
                                //page.adoptNode(scriptPage);
                                
                                //javaScriptList.add(scriptPage.getWebResponse().getContentAsString());
                                System.out.println("Attempting to implement the following javascript:");
                                System.out.println(scriptE.getSrcAttribute());
                                System.out.println(scriptPage.getWebResponse().getContentAsString());
                                page.executeJavaScript(scriptPage.getWebResponse().getContentAsString());
                                //ScriptResult s = page.executeJavaScript(scriptPage.getWebResponse().getContentAsString());
                            } 
                            else if (objectPage instanceof HtmlPage)
                            {
                                HtmlPage scriptPage = (HtmlPage) objectPage;
                                //javaScriptList.add("Javascript at: " + scriptE.getSrcAttribute() + "\n\n" + scriptPage.getWebResponse().getContentAsString());
                                //javaScriptList.add(scriptPage.getWebResponse().getContentAsString());
                                //page.adoptNode(scriptPage);
                                page.executeJavaScript(scriptPage.getWebResponse().getContentAsString());
                                System.out.println("Attempting to implement the following javascript:");
                                System.out.println(scriptE.getSrcAttribute());
                                 System.out.println(scriptPage.getWebResponse().getContentAsString());
                                //ScriptResult s = page.executeJavaScript(scriptPage.getWebResponse().getContentAsString());
                            }

                        } 
                        catch (Exception ex)
                        {
                            System.out.println("Unable to get script," + ex.getMessage());
                        }
                    }
                 }
            }
            

            System.out.println("=======BEGIN REDIRECT PRINT OUT=======");
            
            for (int i = 0; i < redirectSiteList.size(); i++)
            {
                System.out.println(redirectSiteList.get(i));
            }

            System.out.println("=======END REDIRECT PRINT OUT=======");

            //System.out.println("=======END SCRIPT PRINT OUT=======");
            //System.out.println(page.getWebResponse().getContentAsString());
            webClient.closeAllWindows();


            CheckForShellcode();
            for (String s : javaScriptList)
            {
                try
                {
                    //FileWriter fw = new FileWriter("js.txt");
                    //  fw.write(s);
                    //fw.close();
                } 
                catch (Exception e)
                {
                }

                //webClient.setJavaScriptEnabled(true);
                ScriptResult sResult = page.executeJavaScript(s);
                //webClient.setJavaScriptEnabled(false);
                //FunctionFinder f = new FunctionFinder(s, redirectSite);
                //f.find();
            }
            javaScriptList.clear();
             * 
             */
           
             
             
        }

    }

    private static void CheckForShellcode()
    {
        
        for (String s : javaScriptList)
        {
            

            for (int i = 0; i < s.length(); i++)
            {
                if (!Utility.isPrintableChar(s.charAt(i)))
                {
                    char nonp = s.charAt(i);
                    System.out.println("found a non printable char");
                }
            }
        }
    }

    /*
     * Meant to check for major differences between visits
     */
    private static boolean[] isCloaked(Object[] args){

        //assuming the original is done through firefox, run through in IE8
        webClient = Utility.GetWebClient(BrowserVersion.INTERNET_EXPLORER_8 , true, true, listen, processor, alert, errorListener);

        HtmlPage page = null;
        for(int c= 0; c<args.length; c++){
            try{
                page = webClient.getPage((String) args[c]);

                //compare them here.
                

            }catch(Exception e){
                System.out.println("error with " + args[c] + ": "+ e.getMessage());
                continue;
            }
        }

        //make it compile
        return new boolean[]{false};
    }

    public static void addUrls(List list, String filepath){

        try{
            Scanner sc = new Scanner(new File(filepath));

            while(sc.hasNext())
                list.add(sc.next());

        }catch(Exception e){

        }

    }

    /**
     * Detect ratio of string declarations/evals/substrings/etc to
     * uses.
     * This is undoubtedly buggy and incomplete as of 7/6/2012 so
     * it needs work still -md
     * @param thePage the webPage in question
     */
    private static double stringUsesRatio(HtmlPage thePage){

        String stringName = null;
        HashMap<String, Integer> hash = new HashMap();
        int invocations = 0;
        int uses = 0;

        //is there a better way than this to get the content as a string?
        String theWebPage = thePage.toString();

        try{
            Scanner sc = new Scanner(theWebPage);

            while(sc.hasNext()){
                stringName = sc.next();
                //find variables
                boolean isVar = false;

                if(isVar){
                    if(hash.containsKey(stringName)){

                        //it's already there. replace the current val
                        //with an incremented one
                        hash.put(stringName, hash.get(stringName)+1);
                        uses++;

                    }else{
                        //it is a new var. declare it and add it
                        invocations++;
                        hash.put(stringName, 0);
                        
                    }

                }
            }

        }catch(Exception e){

        }

        return (double) invocations/uses;

    }


    public static void checkActiveX(String url){
        //we need to get the IE page in order to do this properly
        //so we plan to only take the url string and then
        //get the page as if we were using IE8

        webClient = Utility.GetWebClient(BrowserVersion.INTERNET_EXPLORER_8 , true, true, listen, processor, alert, errorListener);

        HtmlPage thePage = null;
        try{
            thePage = webClient.getPage(url);


        }catch(Exception e){

        }

    }

    static WebWindowListener listen = new WebWindowListener()
    {
        public void webWindowOpened(WebWindowEvent event)
        {
        }

        public void webWindowContentChanged(WebWindowEvent event)
        {
            String newURL = webClient.getCurrentWindow().getEnclosedPage().getUrl().toString();
            if (!redirectSiteList.contains(newURL) && !done)
            {
                redirectSiteList.add(newURL);
            }
        }

        public void webWindowClosed(WebWindowEvent event)
        {
        }
    };

    static ScriptPreProcessor processor = new ScriptPreProcessor()
    {
        public String preProcess(HtmlPage htmlPage, String sourceCode, String sourceName, int lineNumber, HtmlElement htmlElement)
        {
            return sourceCode;
        }
    };

    static AlertHandler alert = new AlertHandler()
    {
        public void handleAlert(Page page, String message)
        {
            System.out.println("Alert came in with message " + message);
        }
    };

    static JavaScriptErrorListener errorListener = new JavaScriptErrorListener()
    {
        public void scriptException(HtmlPage hp, ScriptException se)
        {
        }

        public void timeoutError(HtmlPage hp, long l, long l1)
        {
        }

        public void malformedScriptURL(HtmlPage hp, String string, MalformedURLException murle)
        {
        }

        public void loadScriptError(HtmlPage hp, URL url, Exception excptn)
        {
        }
    };
}
