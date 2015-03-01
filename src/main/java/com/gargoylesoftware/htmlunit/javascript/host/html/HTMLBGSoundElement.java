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

import static com.gargoylesoftware.htmlunit.BrowserVersionFeatures.JS_BGSOUND_AS_UNKNOWN;

import com.gargoylesoftware.htmlunit.html.HtmlBackgroundSound;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxClass;

/**
 * The JavaScript object "HTMLBGSoundElement".
 *
 * @version $Revision: 9425 $
 * @author Ahmed Ashour
 */
@JsxClass(domClass = HtmlBackgroundSound.class)
public class HTMLBGSoundElement extends HTMLElement {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getClassName() {
        if (getWindow().getWebWindow() != null) {
            if (getBrowserVersion().hasFeature(JS_BGSOUND_AS_UNKNOWN)) {
                return "HTMLUnknownElement";
            }
        }
        return super.getClassName();
    }

    /**
     * {@inheritDoc}
     */
    protected boolean isEndTagForbidden() {
        return true;
    }

}
