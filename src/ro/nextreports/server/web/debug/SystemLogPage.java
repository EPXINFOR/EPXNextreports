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
package ro.nextreports.server.web.debug;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.FileAppender;
import org.apache.log4j.LogManager;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.markup.head.PriorityHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.settings.IJavaScriptLibrarySettings;

import ro.nextreports.server.web.NextServerApplication;
import ro.nextreports.server.web.themes.ThemesManager;

/**
 * @author Decebal Suiu
 */
public class SystemLogPage extends WebPage {

	private static final long serialVersionUID = 1L;
	
	public SystemLogPage() {
		super();
		
		WebMarkupContainer cssContainer = new WebMarkupContainer("cssPath");
        cssContainer.add(new AttributeModifier("href", ThemesManager.getInstance().getThemeRelativePathCss()));
        add(cssContainer);
		
		FileAppender appender = (FileAppender) LogManager.getRootLogger().getAppender("FILE");
		File logFile = new File(appender.getFile());
		
		String content;
		try {
			content = FileUtils.readFileToString(logFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			content = e.toString();
		}
		
		add(new Label("size", FileUtils.byteCountToDisplaySize(logFile.length())));
		add(new Label("lastModified", new Date(logFile.lastModified()).toString()));
		add(new MultiLineLabel("log", content));
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		
        IJavaScriptLibrarySettings settings = NextServerApplication.get().getJavaScriptLibrarySettings();
        response.render(new PriorityHeaderItem(JavaScriptHeaderItem.forReference(settings.getJQueryReference())));
		
		response.render(OnLoadHeaderItem.forScript("$(\"#log\").scrollTop($(\"#log\").prop(\"scrollHeight\"));"));
	}
	
}
