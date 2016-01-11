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
package ro.nextreports.server.web.core;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.PackageResourceReference;

import ro.nextreports.server.web.NextServerSession;
import ro.nextreports.server.web.common.misc.AjaxBusyIndicator;
import ro.nextreports.server.web.themes.ThemesManager;

/**
 * @author Decebal Suiu
 */
public abstract class BasePage extends SecurePage {

	private static final long serialVersionUID = 1L;

	protected HeaderPanel headerPanel;
	protected FooterPanel footerPanel;
	protected ModalWindow dialog;
	protected WebMarkupContainer cssContainer;

    public BasePage() {
		this(new PageParameters());
	}

	public BasePage(PageParameters parameters) {
		super(parameters);
		
		cssContainer = new WebMarkupContainer("cssPath");       
        cssContainer.add(new AttributeModifier("href", ThemesManager.getInstance().getThemeRelativePathCss()));
        add(cssContainer);

		headerPanel = new HeaderPanel("headerPanel");
		headerPanel.setRenderBodyOnly(true);
		add(headerPanel);

		footerPanel = new FooterPanel("footerPanel");
		footerPanel.setRenderBodyOnly(true);
		add(footerPanel);

        add(new AjaxBusyIndicator());

        dialog = new ModalWindow("dialog");
        add(dialog);
    }

	public ModalWindow getDialog() {
		return dialog;
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);

        // add nextserver.js
		new NextServerJavaScriptContributor().renderHead(response);

        // add busy-indicator.js
		response.render(JavaScriptHeaderItem.forReference(new PackageResourceReference(BasePage.class, "busy-indicator.js")));

        if (isInternetExplorer()) {        	
            response.render(JavaScriptHeaderItem.forUrl("js/jquery.pngFix.js"));
            response.render(JavaScriptHeaderItem.forUrl("js/pngFix.js"));
        }
	}

	private boolean isInternetExplorer() {
        return NextServerSession.get().getClientInfo().getProperties().isBrowserInternetExplorer();
	}

}
