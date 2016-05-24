package com.bubble.cluster;

import java.util.List;

import com.github.zkclient.ZkClient;

/**
 * @author hxpwangyi@163.com
 * @date 2013-2-11
 * 
 */
public class ZookeeperConnStatistic {
	public static void incrementConn(String zkServer,String appServer){
		ZkClient zkClient = new ZkClient(zkServer);
		List<String> serverList = zkClient.getChildren(Constant.root);
		for(int i=0;i<serverList.size();i++){
			String server=serverList.get(i);
			if(server.equals(appServer)){
				if(zkClient.readData(Constant.root+"/"+appServer)==null){
					zkClient.writeData(Constant.root+"/"+appServer, 1);	
				}else{
					int conn=zkClient.readData(Constant.root+"/"+appServer);
					zkClient.writeData(Constant.root+"/"+appServer, ++conn);
				}
				break;
			}
		}
		zkClient.close();
	}
	
	public static int getNodeConn(String zkServer,String appServer){
		ZkClient zkClient = new ZkClient(zkServer);
		List<String> serverList = zkClient.getChildren(Constant.root);
		for(int i=0;i<serverList.size();i++){
			String server=serverList.get(i);
			if(server.equals(appServer)){
				int conn=zkClient.readData(Constant.root+"/"+appServer);
				zkClient.close();
				return conn;
			}
		}
		zkClient.close();
		return 0;
	}
}
   
