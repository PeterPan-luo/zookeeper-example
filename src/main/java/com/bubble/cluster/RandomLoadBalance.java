package com.bubble.cluster;

import java.util.List;
import java.util.Random;

import com.github.zkclient.ZkClient;

/**
 * @author hxpwangyi@163.com
 * @date 2013-2-11
 */
public class RandomLoadBalance implements LoadBlance {

	@Override
	public String select(String zkServer) {
	    ZkClient zkClient = new ZkClient(zkServer);
		List<String> serverList = zkClient.getChildren(Constant.root);
		zkClient.close();
		Random r=new Random();
		if(serverList.size()>=1){
			String server=serverList.get(r.nextInt(serverList.size()));
			return server;
		}else{
			return null;
		}
		
	}

}
