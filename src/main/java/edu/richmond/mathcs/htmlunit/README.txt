Overview of HtmlUnitTest


Class overview:

Main.java:

The program's main entry point. The program begins by calling a static method in
the Utility.java class which retrieves a list of websites from an external
text file. The method can be modified to take a string parameter of the sites
location but at this point it is just hardcoded in. After retrieving the list of
sites it feeds them to a ProcessSite method which records redirects on the page
along with fetching the page, which calls the underlying HTMLUnit methods that
simulate a browser. After the page has finished loading we then print out all
the statistics we have gathered.

There is also a forceJavascriptExecution method that ensures that all the
javascript on the page is attempted to be executed. Using this method
causes a lot of javascript errors due to executing javascript without
other necessary javascript components.

Stats.java

This is where all the statistics about methods and plugins are gathered. When
a page is fetched in Main.java the various simulated methods are called in
HTMLUnit. These methods have been modified to call their respective methods
in Stats.java to record which method has been called.

For example in NativeString.java when a javascript substring method is called I
have added the following code:

Stats.incSubstrCount();
Stats.logSubstrArgs(args);
return js_substring(cx, ScriptRuntime.toString(thisObj), args);

Main.java then calls Stats.printStats() and Stats.outputStats() to output
the stats that have been collected.

ActiveXLog.java

Class that simulates ActiveX objects that are created through javascript. Is
called because we modified the ActiveXObject.java class in
com.gargoylsoftware.htmlunit.javascript.host to map all activex calls
to the ActiveXLog class and more specifically the LogMethod method which then
calls a Stats method.

Utility.java

Contains some utility methods such as retrieving websites from a text file and
setting up the WebClient.

