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

import java.util.List;

import org.jcrom.annotations.JcrChildNode;
import org.jcrom.annotations.JcrProperty;

/**
 * @author Decebal Suiu
 */
public class DataSource extends Entity {

	private static final long serialVersionUID = 1L;
	
	private static final int DEFAULT_MIN_POOL_SIZE = 0;
	private static final int DEFAULT_MAX_POOL_SIZE = 20;
	private static final int DEFAULT_IDLE_TIME = 300;
	private static final int DEFAULT_INCREMENT = 5;
	
	public static final String JNDI_VENDOR = "JNDI";

	@JcrProperty
    private String vendor;

    @JcrProperty
    private String driver;

    @JcrProperty
    private String url;

    @JcrProperty
    private String username;

    @JcrProperty
    private String password;
    
    @JcrChildNode
    private List<KeyValue> properties;
    
    @JcrProperty
    private Integer minPoolSize;
    
    @JcrProperty
    private Integer maxPoolSize;
    
    @JcrProperty
    private Integer incrementPoolCon;
    
    @JcrProperty
    private Integer idleTimePoolCon;

    public DataSource() {
    	super();
    }

    public DataSource(String name, String path) {
    	super(name, path);
    }

    public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
   
	public List<KeyValue> getProperties() {
		return properties;
	}

	public void setProperties(List<KeyValue> properties) {
		this.properties = properties;
	}
	
	public Integer getMinPoolSize() {
		if (minPoolSize == null) {
			return DEFAULT_MIN_POOL_SIZE;
		}
		return minPoolSize;
	}

	public void setMinPoolSize(Integer minPoolSize) {
		if ((minPoolSize == null) || (minPoolSize < 0)) {
			this.minPoolSize = DEFAULT_MIN_POOL_SIZE;
		} else {
			this.minPoolSize = minPoolSize;
		}
	}

	public Integer getMaxPoolSize() {
		if (maxPoolSize == null) {
			return DEFAULT_MAX_POOL_SIZE;
		}
		return maxPoolSize;
	}

	public void setMaxPoolSize(Integer maxPoolSize) {
		if ((maxPoolSize == null) || (maxPoolSize < 1)) {
			this.maxPoolSize = DEFAULT_MAX_POOL_SIZE;
		} else {
			this.maxPoolSize = maxPoolSize;
		}
	}		

	public Integer getIncrementPoolCon() {
		if ((incrementPoolCon == null) || (incrementPoolCon < 1)) {
			return DEFAULT_INCREMENT;
		}
		return incrementPoolCon;
	}

	public void setIncrementPoolCon(Integer incrementPoolCon) {
		this.incrementPoolCon = incrementPoolCon;
	}

	public Integer getIdleTimePoolCon() {
		if ((idleTimePoolCon == null) || (idleTimePoolCon < 1)) {
			return DEFAULT_IDLE_TIME;
		}
		return idleTimePoolCon;
	}

	public void setIdleTimePoolCon(Integer idleTimePoolCon) {
		this.idleTimePoolCon = idleTimePoolCon;
	}

	@Override
    public boolean allowPermissions() {
        return true;
    }        

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[vendor=").append(vendor).
           append("\r\ndriver=").append(driver).
           append("\r\nurl=").append(url).
           append("\r\nproperties=").append(properties).
           append("\r\nminPoolSize=").append(minPoolSize).
           append("\r\nmaxPoolSize=").append(maxPoolSize).
           append("\r\n]");
        return sb.toString();
    }

}
