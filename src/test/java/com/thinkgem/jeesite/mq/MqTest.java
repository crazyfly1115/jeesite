package com.thinkgem.jeesite.mq;

import com.thinkgem.jeesite.modules.ips.service.MQService;
import org.apache.rocketmq.client.exception.MQClientException;
import org.junit.Test;

public class MqTest {
    @Test
    public  void startMq()  {
        MQService mqService=new MQService();
        try {
            System.out.println("start");
            mqService.getErrMessage();
        } catch (MQClientException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }
}
