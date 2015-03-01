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
package com.gargoylesoftware.htmlunit.javascript.host.html;

import static com.gargoylesoftware.htmlunit.BrowserVersionFeatures.BUTTON_EMPTY_TYPE_BUTTON;
import static com.gargoylesoftware.htmlunit.BrowserVersionFeatures.JS_BUTTON_SET_TYPE_THROWS_EXCEPTION;
import static com.gargoylesoftware.htmlunit.javascript.configuration.BrowserName.CHROME;
import static com.gargoylesoftware.htmlunit.javascript.configuration.BrowserName.FF;

import java.io.IOException;
import java.util.Locale;

import net.sourceforge.htmlunit.corejs.javascript.Context;

import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxClass;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxFunction;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxGetter;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxSetter;
import com.gargoylesoftware.htmlunit.javascript.configuration.WebBrowser;
import com.gargoylesoftware.htmlunit.javascript.host.FormField;

/**
 * The JavaScript object that represents a {@link HtmlButton} (&lt;button type=...&gt;).
 *
 * @version $Revision: 9449 $
 * @author <a href="mailto:mbowler@GargoyleSoftware.com">Mike Bowler</a>
 * @author Marc Guillemot
 * @author Ahmed Ashour
 * @author Ronald Brill
 * @author Frank Danek
 */
@JsxClass(domClass = HtmlButton.class)
public class HTMLButtonElement extends FormField {

    /**
     * Sets the value of the attribute "type".
     * <p>Note that there is no GUI change in the shape of the button,
     * so we don't treat it like {@link HTMLInputElement#setType(String)}.</p>
     * @param newType the new type to set
     */
    @JsxSetter
    public void setType(final String newType) {
        if (getBrowserVersion().hasFeature(JS_BUTTON_SET_TYPE_THROWS_EXCEPTION)) {
            throw Context.reportRuntimeError("Object doesn't support this action");
        }
        getDomNodeOrDie().setAttribute("type", newType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @JsxGetter
    public String getType() {
        String type = ((HtmlButton) getDomNodeOrDie()).getTypeAttribute();
        if (null != type) {
            type = type.toLowerCase(Locale.ENGLISH);
        }
        if ("reset".equals(type)) {
            return "reset";
        }
        if ("submit".equals(type)) {
            return "submit";
        }
        if ("button".equals(type)) {
            return "button";
        }
        if (getBrowserVersion().hasFeature(BUTTON_EMPTY_TYPE_BUTTON)) {
            return "button";
        }
        return "submit";
    }

    /**
     * {@inheritDoc} Overridden to modify browser configurations.
     */
    @Override
    @JsxFunction({ @WebBrowser(CHROME), @WebBrowser(FF) })
    public void click() throws IOException {
        super.click();
    }
}
