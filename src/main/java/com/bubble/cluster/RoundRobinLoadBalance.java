package com.bubble.cluster;

import java.util.List;

import com.github.zkclient.ZkClient;

/**
 * @author hxpwangyi@163.com
 * @date 2013-2-11
 */
public class RoundRobinLoadBalance implements LoadBlance {
	
	@Override
	public String select(String zkServer) {
		ZkClient zkClient = new ZkClient(zkServer);
		List<String> serverList = zkClient.getChildren(Constant.root);
		int round=0;
		if(!zkClient.exists(Constant.round)){
			zkClient.createPersistent(Constant.round);
			zkClient.writeData(Constant.round, 0);
		}else{
			round=(Integer)zkClient.readData(Constant.round);
			zkClient.writeData(Constant.round, ++round);
		}
		zkClient.close();
		if (serverList != null && serverList.size() > 0) {
			return serverList.get(round % serverList.size());
		} else {
			return null;
		}

	}

}
