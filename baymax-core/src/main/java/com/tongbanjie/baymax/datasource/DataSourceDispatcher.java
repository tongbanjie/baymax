package com.tongbanjie.baymax.datasource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

/**
 * DataSource分发
 */
public class DataSourceDispatcher {
	private Set<DataSourceGroup>    dataSourceGroupSet  = new HashSet<DataSourceGroup>();
	private Map<String, DataSource> dataSources         = new HashMap<String, DataSource>();
	private Set<DataSource>         dataSourceSet       = new HashSet<DataSource>();
	private DataSource              defaultDataSource   = null;

	public Map<String, DataSource> getDataSources() {
		return dataSources;
	}

	public void init() throws Exception {
		if (dataSourceGroupSet == null || dataSourceGroupSet.size() == 0) {
			return;
		}
		for (DataSourceGroup group : dataSourceGroupSet) {
			if(group.getIdentity() == null || group.getIdentity().trim().length() == 0){
				throw new RuntimeException("identity must not be empty!");
			}
			if(group.getTargetDataSource() == null){
				throw new RuntimeException("targetDataSource must not be null!");
			}
			DataSource dataSourceToUse = group.getTargetDataSource();
			DataSource ds = dataSourceToUse;
			dataSources.put(group.getIdentity(), ds);
			dataSourceSet.add(ds);
			if(defaultDataSource == null){
				// TODO default need be setting in config
				defaultDataSource = group.getTargetDataSource();
			}
		}
	}
	
	public DataSource getDefaultDataSource(){
		return this.defaultDataSource;
	}
	
	public DataSource getDataSourceByName(String parttionName){
		return dataSources.get(parttionName);
	}

	public Set<DataSource> getDataSourceSet() {
		return dataSourceSet;
	}

	public Set<DataSourceGroup> getDataSourceGroupSet() {
		return dataSourceGroupSet;
	}

	public void setDataSourceGroupSet(Set<DataSourceGroup> dataSourceGroupSet) {
		this.dataSourceGroupSet = dataSourceGroupSet;
	}

	public void setDataSources(Map<String, DataSource> dataSources) {
		this.dataSources = dataSources;
	}

	public void setDataSourceSet(Set<DataSource> dataSourceSet) {
		this.dataSourceSet = dataSourceSet;
	}

	public void setDefaultDataSource(DataSource defaultDataSource) {
		this.defaultDataSource = defaultDataSource;
	}
}
