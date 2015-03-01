/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author OmarFarooq
 */
public class Stats {

    private static boolean active = false;

    private static int substr_count = 0;
    private static int substr_memory = 0;
    private static int concat_memory = 0;

    private static int eval_count = 0;
    public static int concat_count = 0;
    private static int from_char_code_count = 0;
    private static int char_at_count = 0;
    private static int uneval_count = 0;
    private static int unescape_count = 0;
    private static int encode_uri_count = 0;
    private static int decode_uri_count = 0;
    private static int doc_create_element_count = 0;
    private static int document_write_count = 0;
    private static int setTimeout_count = 0;
    private static int activeXInstants = 0;
    private static int site_num =1;

    private static List<String> evalArguments = new ArrayList<String>();
    private static List<String> getRequests = new ArrayList<String>();
    private static Map<String,String> activeXDict = new HashMap<String,String>();
    private static List<String> pluginNames = new ArrayList<String>();


    public static void printStats() {
        System.out.println("=== BEGIN STAT PRINTOUT ===");
        System.out.println("# of substr: " + substr_count);
        System.out.println("substr total length: " + substr_memory);
        System.out.println("# of eval: " + eval_count);
        System.out.println("concat total length: " + concat_memory);
        System.out.println("# of concat: " + concat_count);
        System.out.println("# of fromCharCode: " + from_char_code_count);
        System.out.println("# of charAt " + char_at_count);
        System.out.println("# of uneval " + uneval_count);
        System.out.println("# of unescape " + unescape_count);
        System.out.println("# of encodeURI " + encode_uri_count);
        System.out.println("# of decodeURI " + decode_uri_count);
        System.out.println("# of document.createElement " + doc_create_element_count);
        System.out.println("# of document.write " + document_write_count);
        System.out.println("=== END STAT PRINTOUT ===");

        System.out.println("=== BEGIN EVAL LENGTH PRINTOUT ===");
        for(String s : evalArguments)
        {
            System.out.println("eval() was called w/ argument of length: " + s.length());
        }
        System.out.println("=== END EVAL LENGTH PRINTOUT ===");
        System.out.println("=== BEGIN GET REQUEST PRINTOUT ===");
        for(String s : getRequests)
        {
            System.out.println("GetRequest on url: " + s);
        }
        System.out.println("=== END GET REQUEST PRINTOUT ===");




    }

    public static void outputStats(String siteName, int numRed, int dif)
    {
        try
        {
            File file = new File("data.arff");
            if(!file.exists())
            {
                file.createNewFile();
                FileWriter fw = new FileWriter("data.arff");
                fw.write("@RELATION classify\n\n");
                fw.write("@ATTRIBUTE x1 NUMERIC\n");
                fw.write("@ATTRIBUTE x2 NUMERIC\n");
                fw.write("@ATTRIBUTE x3 NUMERIC\n");
                fw.write("@ATTRIBUTE x4 NUMERIC\n");
                fw.write("@ATTRIBUTE x5 NUMERIC\n");
                fw.write("@ATTRIBUTE class {benign, malicious}\n\n");
                fw.write("@DATA");
                
                fw.close();
            }

            FileWriter fw = new FileWriter("data.arff",true);
            BufferedWriter out = new BufferedWriter(fw);

            int f1 = numRed;
            int f2 = dif;
            
            int denom = 1;
            if ((document_write_count+ eval_count)!=0)
            {
                denom = document_write_count+ eval_count;
            }
                
            double f3 = (substr_count + from_char_code_count)/denom;
            int f4 = eval_count + document_write_count+ doc_create_element_count;
            double f5;

            int evalArgSum = 0;
            double size = evalArguments.size();
            for(int i = 0; i<size; i++)
            {
                evalArgSum+=evalArguments.get(i).length();
            }
            if(size!=0)
            {
                f5 = evalArgSum/size;
            }
            else
            {
                f5=0;
            }

            out.append("\n" + f1 + "," + f2 + "," + f3 + "," + f4 + "," + f5);

            out.close();
            fw.close();

            /*
            fw = new FileWriter("site" + site_num + "_evalArgs.txt");
            out = new BufferedWriter(fw);
            out.write("eval argument\n");
            for(int i = 0;i<evalArguments.size();i++)
            {
                out.append(evalArguments.get(i) + '\n');
            }
            out.close();
            fw.close();




            fw = new FileWriter("site" + site_num + "_gets.txt");
            out = new BufferedWriter(fw);
            out.write("gets\n");
            for(int i = 0;i<getRequests.size();i++)
            {
                out.append(getRequests.get(i) + '\n');
            }
            out.close();
            fw.close();


            fw = new FileWriter("plugins.txt",true);
            out = new BufferedWriter(fw);
            for(String pluginName : pluginNames)
            {
                out.append(siteName + "," + pluginName + '\n');
            }
            out.close();
            fw.close();

            fw = new FileWriter("activex.txt",true);
            out = new BufferedWriter(fw);
            for(Map.Entry<String,String> entry : activeXDict.entrySet())
            {
                out.append(siteName + "," + entry.getKey() + "," + entry.getValue() + '\n');
            }

            out.close();
            fw.close();
            getRequests.clear();

            site_num++;
             *
             */
             
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    public static void recordGetRequest(String request)
    {
        if(active)
        {
            if(!request.equals("about:blank") || !getRequests.contains(request))
                getRequests.add(request);
        }
    }

    public static void logPluginName(String pluginName)
    {
        if(active)
        {
        if(!pluginNames.contains(pluginName))
            pluginNames.add(pluginName);
        }
    }

    public static void setStatus(boolean status) {
            active = status;
       
    }

    public static void logActiveXArgs(String activeXName, String argument)
    {
        if(active)
        {

            activeXDict.put(activeXName, argument);
        }
    }


    // Wrote this method separate from one below because substring and substr handle 
    // a second parameter (index) differently (TBA)
    public static void logSubstrArgs(Object[] args, Object theString)
    {
        if(active)
        {
            //for(Object arg : args)
            //{
                //substr_memory += arg.toString().length();

            // Does not handle case when arguments are not valid (e.g. negative, greater than length of string)
            // Note: if first arg is greater than length of string, substring starts from second arg
            if (args.length == 2)
            {
                substr_memory += Double.parseDouble(args[1].toString()) ;
            }
            else
            {
                substr_memory += theString.toString().length()-Double.parseDouble(args[0].toString())+1;
            }
        }
    }

    public static void logSubstringArgs(Object[] args, Object theString)
    {
        if(active)
        {
            //for(Object arg : args)
            //{
                //substr_memory += arg.toString().length();

            // Does not handle case when arguments are not valid (e.g. negative, greater than length of string) (TBA)
            if (args.length == 2)
            {
                substr_memory += Double.parseDouble(args[1].toString())-Double.parseDouble(args[0].toString());
            }
            else
            {
                substr_memory += theString.toString().length()-Double.parseDouble(args[0].toString())+1;
            }

        }
    }

    public static void logEvalArgument(Object s)
    {
        if(active)
        evalArguments.add(s.toString());
    }

    // Adds lengths of the two strings being concatenated to the concat_memory variable (TBA)
    public static void logConcatArgs(Object[] args, Object theString)
    {
        if(active)
        {
            int numArgs = args.length;
            //concat_memory = 0;
            for (int i=0; i<numArgs; i++)
            {
                concat_memory += args[i].toString().length();
            }
             
        }
    }

    public static void incConcatMemory(int a){
        concat_memory += a;

    }

    public static void incDocWriteCount()
    {
        if(active)
        {
            document_write_count++;
        }
    }

    public static void incUnevalCount() {
        if (active) {
            uneval_count++;
        }
    }

    public static void incUnescapeCount() {
        if (active) {
            unescape_count++;
        }
    }

    public static void incDecodeURICount() {
        if (active) {
            decode_uri_count++;
        }
    }

    public static void incEncodeURICount() {
        if (active) {
            encode_uri_count++;
        }
    }

    public static void incSubstrCount() {
        if (active) {
            substr_count++;
        }
    }

    public static void incEvalCount() {
        if (active) {
            eval_count++;
        }
    }

    public static void incConcatCount() {
        if (active) {
            concat_count++;
        }
    }

    public static void incFromCharCode() {
        if (active) {
            from_char_code_count++;
        }
    }

    public static void incCharAt() {
        if (active) {
            char_at_count++;
        }
    }

    public static void incDocCreateElement()
    {
        if(active) {
            doc_create_element_count++;
        }
    }

    public static void incSetTimeout()
    {
        if(active)
        {
            setTimeout_count++;
        }
    }
    /*
    public static void incActiveX(){
        activeXInstant++;
    }
*/
    public static void clearAll()
    {
        

    substr_count = 0;
    substr_memory = 0;

    concat_memory = 0;

    eval_count = 0;
    concat_count = 0;
    from_char_code_count = 0;
    char_at_count = 0;
    uneval_count = 0;
    unescape_count = 0;
    encode_uri_count = 0;
    decode_uri_count = 0;
    doc_create_element_count = 0;
    document_write_count = 0;
    setTimeout_count = 0;



    evalArguments = new ArrayList<String>();
    getRequests = new ArrayList<String>();
    activeXDict = new HashMap<String,String>();
    pluginNames = new ArrayList<String>();
    }
}
