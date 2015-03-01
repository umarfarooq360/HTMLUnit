/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java;

import org.mozilla.javascript.Scriptable;


/**
 *
 * @author cl6md
 */
public class ActiveXLog{

    private String activeXName;

    public ActiveXLog()
    {
        
    }

    public void jsConstructor()
    {
        System.out.println("jsConstructor");
    }

    public void LogMethod()
    {
        System.out.println("Empty call to logmethod. " + activeXName);
    }

    public void LogMethod(Object... strings)
    {
        for(Object s : strings)
        {
            System.out.println("LogMethod called on activeXObject " + activeXName + " with argument: " + (String)s);
            Stats.logActiveXArgs(activeXName, (String)s);
        }
    }

    public void setActiveXName(String axn)
    {
        activeXName = axn;
    }


}
