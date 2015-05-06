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
package ro.nextreports.server.web.dashboard.drilldown;

import java.util.Map;

import ro.nextreports.server.domain.Chart;
import ro.nextreports.server.domain.DrillEntityContext;
import ro.nextreports.server.report.next.NextUtil;
import ro.nextreports.server.service.StorageService;
import ro.nextreports.server.util.ChartUtil;
import ro.nextreports.server.web.dashboard.EntityWidget;
import ro.nextreports.server.web.dashboard.WidgetView;
import ro.nextreports.server.web.dashboard.model.WidgetModel;


public class DrillDownWidget extends EntityWidget {

	private static final long serialVersionUID = 1L;
		
	public static final String DEFAULT_TITLE = "Drill-down";
    public static final String CHART_TYPE = "chartType";    
    
    private DrillDownWidgetView ddView;
	
	public DrillDownWidget() {
		title = DEFAULT_TITLE;
    }	
	
	public WidgetView createView(String viewId, boolean zoom) {		
        if (entity == null) {
            return new WidgetView(viewId, new WidgetModel(getId()), false); // dynamic
        }
        
        ddView = new DrillDownWidgetView(viewId, new WidgetModel(getId()), zoom); // dynamic
        return ddView;
	}
	
	public WidgetView createView(String viewId, String width, String height) {
		if (entity == null) {
            return new WidgetView(viewId, new WidgetModel(getId()), false); // dynamic
        }
        
        ddView = new DrillDownWidgetView(viewId, new WidgetModel(getId()), false, width, height); // dynamic
        return ddView;
	}	
	
	public WidgetView createView(String viewId, boolean zoom, Map<String, Object> urlQueryParameters) {
		if (entity == null) {
			return new WidgetView(viewId, new WidgetModel(getId()), false); // dynamic
		}

		ddView = new DrillDownWidgetView(viewId, new WidgetModel(getId()), zoom, urlQueryParameters); // dynamic
		return ddView;
	}
	
	public WidgetView createView(String viewId, String width, String height, Map<String,Object> urlQueryParameters) {
		if (entity == null) {
            return new WidgetView(viewId, new WidgetModel(getId()), false); // dynamic
        }
        
        ddView = new DrillDownWidgetView(viewId, new WidgetModel(getId()), false, width, height, urlQueryParameters); // dynamic
        return ddView;
	}
  
    public boolean saveToExcel() {
        return true;
    }

    @Override
    public void afterCreate(StorageService storageService) {        
        super.afterCreate(storageService);
        
		if (entity instanceof Chart) {
			if (getChartType() == null) {
				if ((entity == null) || (((Chart) entity).getContent() == null)) {
					return;
				}
				ro.nextreports.engine.chart.Chart c = NextUtil.getChart(((Chart) entity).getContent());
				if (c == null) {
					return;
				}

				String chartType = ChartUtil.getChartType(c.getType().getType());
				setChartType(chartType);
			}
		}
    }

    public void setChartType(String chartType) {
        settings.put(CHART_TYPE, chartType);
    }

    public String getChartType() {
        return settings.get(CHART_TYPE);
    }
           
    public DrillEntityContext getDrillEntityContext() {
    	if (ddView == null) {
    		return null;
    	}
		return ddView.getDrillEntityContext();
	}	

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DrillDownWidget[");
		buffer.append("id = ").append(getId());
		buffer.append(" title = ").append(getTitle());
		buffer.append(" entityId = ").append(getInternalSettings().get(ENTITY_ID));
		buffer.append("]");
		
		return buffer.toString();
	}
		
}
