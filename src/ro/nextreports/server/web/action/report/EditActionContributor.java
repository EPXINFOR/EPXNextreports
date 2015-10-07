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
package ro.nextreports.server.web.action.report;

import javax.annotation.Resource;

import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.model.StringResourceModel;

import ro.nextreports.server.domain.DataSource;
import ro.nextreports.server.domain.Entity;
import ro.nextreports.server.domain.Report;
import ro.nextreports.server.report.ReportConstants;
import ro.nextreports.server.service.SecurityService;
import ro.nextreports.server.util.PermissionUtil;
import ro.nextreports.server.util.ServerUtil;
import ro.nextreports.server.web.core.action.ActionContext;
import ro.nextreports.server.web.core.action.SingleActionContributor;


public class EditActionContributor extends SingleActionContributor {

	public static final String ID = EditActionContributor.class.getName();

    @Resource
    private SecurityService securityService;

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    public boolean support(Entity entity) {
        if ((entity instanceof Report) && ((Report) entity).getType().equals(ReportConstants.NEXT)) {
            try {
                if (securityService.hasPermissionsById(ServerUtil.getUsername(), PermissionUtil.getWrite(), entity.getId())) {
                	DataSource ds = ((Report)entity).getDataSource();
                	if (securityService.hasPermissionsById(ServerUtil.getUsername(), PermissionUtil.getRead(), ds.getId())) {
                		return true;
                	}
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public String getActionImage() {
        // TODO bootstrap
        return "images/edit_report.png";
//        return "pencil";
    }

    public String getActionName() {
    	return new StringResourceModel("ActionContributor.Edit.name", null).getString();
    }

    public String getId() {
    	return ID;
    }

    public AbstractLink getLink(ActionContext context) {
        return new EditActionLink(context);
    }

}
