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
package ro.nextreports.server.util;

import ro.nextreports.server.StorageConstants;
import ro.nextreports.server.domain.UserWidgetParameters;
import ro.nextreports.server.service.DashboardService;
import ro.nextreports.server.web.dashboard.AbstractWidget;
import ro.nextreports.server.web.dashboard.Widget;
import ro.nextreports.server.web.dashboard.chart.ChartWidget;
import ro.nextreports.server.web.dashboard.table.TableWidget;

public class WidgetUtil {
	
	public static int getRefreshTime(DashboardService dashboardService, Widget widget) {
		int refreshTime = 0;
		if (widget == null) {
			return refreshTime;
		}
		refreshTime = widget.getRefreshTime();
		UserWidgetParameters wp = dashboardService.getUserWidgetParameters(widget.getId());
		if (wp != null) {
			refreshTime = Integer.parseInt(wp.getSettings().get(ChartWidget.REFRESH_TIME));
		}
        return refreshTime;
	}
	
	public static String getChartType(DashboardService dashboardService, ChartWidget chartWidget) {
		String chartType = null;
        UserWidgetParameters wp = dashboardService.getUserWidgetParameters(chartWidget.getId());
        if (wp != null) {
        	// take chart type from user widget parameters if any
        	chartType = wp.getSettings().get(ChartWidget.CHART_TYPE);
        }
        if (chartType == null) {
        	chartType = chartWidget.getChartType();
        }
        return chartType;
	}
	
	public static int getTimeout(DashboardService dashboardService, Widget widget) {
		int timeout = 600;
		if (widget == null) {
			return timeout;
		}
		timeout = widget.getTimeout();
    	UserWidgetParameters wp = dashboardService.getUserWidgetParameters(widget.getId());
        if (wp != null) {
        	timeout = Integer.parseInt(wp.getSettings().get(AbstractWidget.TIMEOUT));
        }
        return timeout;
	}
	
	public static int getRowsPerPage(DashboardService dashboardService, Widget widget) {
		int rowsPerPage = TableWidget.DEFAULT_ROWS_PER_PAGE;
		if (widget == null) {
			return rowsPerPage;
		}
		rowsPerPage = widget.getRowsPerPage();
		UserWidgetParameters wp = dashboardService.getUserWidgetParameters(widget.getId());
		if (wp != null) {
			rowsPerPage = Integer.parseInt(wp.getSettings().get(TableWidget.ROWS_PER_PAGE));
		}
        return rowsPerPage;
	}
	
	public static boolean isEnableFilter(DashboardService dashboardService, Widget widget) {
		if (widget == null) {
			return false;
		}
		boolean enableFilter = widget.isEnableFilter();
		UserWidgetParameters wp = dashboardService.getUserWidgetParameters(widget.getId());
		if (wp != null) {
			enableFilter = Boolean.parseBoolean(wp.getSettings().get(TableWidget.ENABLE_FILTER));
		}
        return enableFilter;
	}
	
	public static String getUserWidgetParametersPath(String user) {
		return StorageConstants.USERS_DATA_ROOT + "/" + user + StorageConstants.USERS_WIDGET_STATES_PATH;
	}

}
