package com.demxs.tdm.common.file.fast.pool;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool.Config;
import org.csource.fastdfs.*;

import java.io.File;
import java.io.IOException;

/**
 * 连接池实现类
 * @Project 	: fastdfs.pool
 * @Program Name: com.fast.pool.FastdfsPool.java
 * @ClassName	: FastdfsPool 
 * @Author 		: caozhifei 
 * @CreateDate  : 2014年8月18日 下午1:10:48
 */
public class FastdfsPool extends PoolAdapter {
	private static String confPath = "fdfs_client.conf";
	public FastdfsPool(Config poolConfig, PoolableObjectFactory factory) {
		super(poolConfig, factory);
	}

	public FastdfsPool(Config poolConfig, String confPath) {
		super(poolConfig, new FastdfsClientFactory());
		this.confPath = confPath;
	}

	private static class FastdfsClientFactory extends BasePoolableObjectFactory {
		@Override
		public Object makeObject() throws Exception {
			String classPath = new File(getClass().getResource("/").getFile())
					.getCanonicalPath();
			String configFilePath = classPath + File.separator
					+ confPath;
			ClientGlobal.init(configFilePath);
			TrackerClient tracker = new TrackerClient();
			TrackerServer trackerServer = tracker.getConnection();
			StorageServer storageServer = null;
			StorageClient client = new StorageClient(trackerServer,
					storageServer);
			return client;
		}

		@Override
		public void destroyObject(Object obj) throws Exception {
			if ((obj != null) && ((obj instanceof StorageClient))) {
				StorageClient storageClient = (StorageClient) obj;
				TrackerServer trackerServer = storageClient.getTrackerServer();
				StorageServer storageServer = storageClient.getStorageServer();
				trackerServer.close();
				storageServer.close();
			}
		}

		@Override
		public boolean validateObject(Object obj) {
			StorageClient storageClient = (StorageClient) obj;
			try {
				return ProtoCommon.activeTest(storageClient.trackerServer
						.getSocket());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}
	}
}
