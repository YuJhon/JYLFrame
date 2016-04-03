package com.jyl.datasource.util;

import javax.sql.DataSource;

import com.jyl.datasource.RoutingDataSource;

public class JYLDynamicDataSource extends RoutingDataSource
{

    @Override
    protected Object determineCurrentLookupKey()
    {
        String index = JYLDBContextHolder.getDataSourceIndex();
        return index;
    }

    @Override
    public void addDataSource(String key, DataSource ds)
    {
        if (null == key || "".equals(key))
        {
            return;
        }

        if (null != ds)
        {
            super.getTargetDataSources().put(key, ds);
            super.getResolvedDataSources().put(key, ds);
        }
    }
}
