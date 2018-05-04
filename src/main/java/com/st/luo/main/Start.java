package com.st.luo.main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by bobo on 2018/5/4.
 *
 * @email ruantianbo@163.com
 */
public class Start {

    private static final String LOUBASEURL = "https://m.weibo.cn/api/container/getIndex?uid=1640571365&luicode=10000011&lfid=100103type%3D1%26q%3D%E7%BD%97%E6%B0%B8%E6%B5%A9&featurecode=20000320&type=uid&value=1640571365&containerid=1076031640571365&page=";

    public static void main(String[] args) throws Exception{
        Start start = new Start();
        start.run();
    }

    public void run() {
        Integer count = 0;
        ExecutorService service = Executors.newCachedThreadPool();
        try {
            List<Future<Integer>> futureList = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                futureList.add(service.submit(new Fetch(LOUBASEURL + i)));
            }
            for (Future<Integer> fItem : futureList) {
                while (!fItem.isDone()) {
                    Thread.sleep(1000);
                }
                count = count + fItem.get();
            }
            System.out.println("Total count:"+count);
        }catch (Exception ex){
            System.out.println(ex.getMessage()+","+ex.getCause());
        }finally {
            if(service != null) {
                service.shutdown();
            }
        }
    }

    class Fetch implements Callable<Integer>{
        String url;
        public Fetch(String url){
            this.url = url;
        }
        @Override
        public Integer call() throws Exception {
            System.out.println("Fetching-->\t"+url);
            return 1;
        }
    }

}
