/*
 * Copyright (c) 2002-2014 Gargoyle Software Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gargoylesoftware.htmlunit.javascript.host.css;

import static com.gargoylesoftware.htmlunit.BrowserVersionFeatures.JS_CSSRULELIST_DONT_ENUM_ITEM;
import static com.gargoylesoftware.htmlunit.javascript.configuration.BrowserName.FF;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.htmlunit.corejs.javascript.Scriptable;

import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxClass;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxFunction;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxGetter;
import com.gargoylesoftware.htmlunit.javascript.configuration.WebBrowser;

/**
 * A JavaScript object for a CSSRuleList.
 *
 * @version $Revision: 9330 $
 * @author Ahmed Ashour
 */
@JsxClass
public class CSSRuleList extends SimpleScriptable {

    private final CSSStyleSheet stylesheet_;
    private final org.w3c.dom.css.CSSRuleList rules_;

    /**
     * Creates a new instance. JavaScript objects must have a default constructor.
     */
    @Deprecated
    public CSSRuleList() {
        stylesheet_ = null;
        rules_ = null;
    }

    /**
     * Creates a new instance.
     * @param stylesheet the stylesheet
     */
    public CSSRuleList(final CSSStyleSheet stylesheet) {
        stylesheet_ = stylesheet;
        rules_ = stylesheet.getWrappedSheet().getCssRules();
        setParentScope(stylesheet.getParentScope());
        setPrototype(getPrototype(getClass()));
    }

    /**
     * Returns the length of this list.
     * @return the length of this list.
     */
    @JsxGetter
    public int getLength() {
        if (rules_ != null) {
            return rules_.getLength();
        }
        return 0;
    }

    /**
     * Returns the item in the given index.
     * @param index the index
     * @return the item in the given index
     */
    @JsxFunction(@WebBrowser(FF))
    public Object item(final int index) {
        return null;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Object[] getIds() {
        final List<String> idList = new ArrayList<String>();

        final int length = getLength();
        if (getBrowserVersion().hasFeature(JS_CSSRULELIST_DONT_ENUM_ITEM)) {
            idList.add("length");

            for (int i = 0; i < length; i++) {
                idList.add(Integer.toString(i));
            }
        }
        else {
            for (int i = 0; i < length; i++) {
                idList.add(Integer.toString(i));
            }

            idList.add("length");
            idList.add("item");
        }
        return idList.toArray();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean has(final int index, final Scriptable start) {
        return index >= 0 && index < getLength();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public boolean has(final String name, final Scriptable start) {
        if ("length".equals(name) || "item".equals(name)) {
            return true;
        }
        try {
            return has(Integer.parseInt(name), start);
        }
        catch (final Exception e) {
            //ignore
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object get(final int index, final Scriptable start) {
        if (index < 0 || getLength() <= index) {
            return NOT_FOUND;
        }
        return CSSRule.create(stylesheet_, rules_.item(index));
    }

}
