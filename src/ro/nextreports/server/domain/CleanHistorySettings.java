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
package ro.nextreports.server.domain;

import org.jcrom.annotations.JcrProperty;

/**
 * @author Decebal Suiu
 */
public class CleanHistorySettings extends EntityFragment {

	private static final long serialVersionUID = 1L;

	@JcrProperty
	private Integer daysToKeep;

	@JcrProperty
	private String cronExpression;

	@JcrProperty
	private Integer daysToDelete;

	@JcrProperty
	private boolean shrinkDataFolder;

	public CleanHistorySettings() {
		super();
	}

	public Integer getDaysToKeep() {
		return daysToKeep;
	}

	public void setDaysToKeep(Integer daysToKeep) {
		this.daysToKeep = daysToKeep;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public Integer getDaysToDelete() {
		return daysToDelete;
	}

	public void setDaysToDelete(Integer daysToDelete) {
		this.daysToDelete = daysToDelete;
	}

	public boolean isShrinkDataFolder() {
		return shrinkDataFolder;
	}

	public void setShrinkDataFolder(boolean shrinkDataFolder) {
		this.shrinkDataFolder = shrinkDataFolder;
	}

	@Override
	public String toString() {
		return "CleanHistorySettings [daysToKeep=" + daysToKeep + ", cronExpression=" + cronExpression
				+ ", daysToDelete=" + daysToDelete + ", shrinkDataFolder=" + shrinkDataFolder + "]";
	}

}
