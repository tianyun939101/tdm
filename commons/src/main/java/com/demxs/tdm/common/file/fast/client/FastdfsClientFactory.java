package com.demxs.tdm.common.file.fast.client;

import com.demxs.tdm.common.file.fast.pool.StorageClient;
import org.apache.commons.pool.BasePoolableObjectFactory;
import org.csource.fastdfs.*;

import java.io.File;
import java.io.IOException;

/**
 * 继承抽象工厂类，创建fastdfs客户端对象
 * User: wuliepeng
 * Date: 2017-07-31
 * Time: 上午11:43
 */
public class FastdfsClientFactory extends BasePoolableObjectFactory {
	/**
	 * fastDfs配置文件路径
	 */
	private String confPath = "fdfs_client.conf";
	/**
	 * 创建fastDfs客户端对象
	 */
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
	/**
	 * 销毁fastDfs客户端对象
	 */
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
	/**
	 * 检查fastDfs客户端对象连接是否正常
	 */
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