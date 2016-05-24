package lock;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Benchmark {

	private static final int THREAD_NUM = 10; 
	private static final int SESSION_TIMEOUT = 10000;
	private static final String CONNECTION_STRING = "192.168.146.139:2181";
	private static final String GROUP_PATH = "/disLocks";
	private static final Logger LOG = LoggerFactory.getLogger(Benchmark.class);
	
	public static void main(String[] args) {
		Benchmark benchmark = new Benchmark();
		benchmark.test();
       
    }
	
	public void test() {
		final CountDownLatch threadSemaphore = new CountDownLatch(THREAD_NUM);
		 for(int i=0; i < THREAD_NUM; i++){
            final int threadId = i+1;
            new Thread(){
                @Override
                public void run() {
                    try{
                        DistributedLock dc = new DistributedLock(threadId);
                        dc.createConnection(CONNECTION_STRING, SESSION_TIMEOUT);
                        //GROUP_PATH不存在的话，由一个线程创建即可；
                        synchronized (threadSemaphore){
                            dc.createPath(GROUP_PATH, "该节点由线程" + threadId + "创建", true);
                        }
                        dc.getLock();
                        threadSemaphore.countDown();
                    } catch (Exception e){
                        LOG.error("【第"+threadId+"个线程】 抛出的异常：");
                        e.printStackTrace();
                    }
                }
            }.start();
        }
        try {
            threadSemaphore.await();
            LOG.info("所有线程运行结束!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	}
}
