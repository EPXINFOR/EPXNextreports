/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ro.nextreports.server.web.security.cas;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;

import ro.nextreports.server.web.themes.ThemesManager;

/**
 * @author Decebal Suiu
 */
public class CasLoginErrorPage extends WebPage {
    
    private static final long serialVersionUID = 1L;

	public CasLoginErrorPage() {
    	super();
    	
    	WebMarkupContainer cssContainer = new WebMarkupContainer("cssPath");
        cssContainer.add(new AttributeModifier("href", ThemesManager.getInstance().getThemeRelativePathCss()));
        add(cssContainer);
    	
        String logoutUrl = CasUtil.getLogoutUrl();
        add(new WebMarkupContainer("casLogin").add(AttributeModifier.replace("href", logoutUrl)));
    }

}
