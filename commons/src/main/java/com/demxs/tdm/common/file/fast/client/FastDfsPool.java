package com.demxs.tdm.common.file.fast.client;

import com.demxs.tdm.common.file.fast.pool.PoolAdapter;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool.Config;

public class FastDfsPool extends PoolAdapter {

	public FastDfsPool(Config poolConfig, PoolableObjectFactory factory) {
		super(poolConfig, factory);
		// TODO Auto-generated constructor stub
	}

}
