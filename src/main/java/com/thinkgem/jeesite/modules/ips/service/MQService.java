package com.thinkgem.jeesite.modules.ips.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ips.dao.ServiceErrDao;
import com.thinkgem.jeesite.modules.ips.entity.ServiceErr;
import com.thinkgem.jeesite.modules.sys.entity.User;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MQService {



    private final String serveraddr= Global.getConfig("mq_serveraddr");
    private final String nameserveraddr= Global.getConfig("mq_nameserveraddr");
    private static String ErrMq="errMq";
    private final String topic="ErrorTopic";//错误信息日志
    protected static Logger logger = LoggerFactory.getLogger(MQService.class);

   private  ServiceErrService serviceErrService =null;



    //获取错误消息

    public void getErrMessage() throws MQClientException {
        //设置消费者组
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ErrorTopic");

        consumer.setVipChannelEnabled(false);
        consumer.setNamesrvAddr(nameserveraddr);
        //设置消费者端消息拉取策略，表示从哪里开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        //设置消费者拉取消息的策略，*表示消费该topic下的所有消息，也可以指定tag进行消息过滤
        consumer.subscribe("ErrorTopic", "*");

        //消费者端启动消息监听，一旦生产者发送消息被监听到，就打印消息，和rabbitmq中的handlerDelivery类似
        consumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt messageExt : msgs) {
                    String topic = messageExt.getTopic();
                    String tag = messageExt.getTags();
                    Long date=messageExt.getBornTimestamp();
                    String host= messageExt.getBornHostString();

                    String msg = new String(messageExt.getBody());
                    logger.debug("消费响应：msgId : " + messageExt.getMsgId() + ",  msgBody : " + msg + ", tag:" + tag + ", topic:" + topic+ ", date:" + date+ ", host:" + host);
                    try{
                        if(StringUtils.isNotBlank(msg)){
                           Map<String,String> map = new Gson().fromJson(msg, new TypeToken<Map<String, String>>() { }.getType());

                            ServiceErr serviceErr=new ServiceErr();

                            serviceErr.setTaskId(map.get("task_id"));
                            serviceErr.setErrMsg(map.get("error_msg"));
                            serviceErr.setErrType(map.get("error_type"));
                            serviceErr.setBornTime(new Date(date));
                            serviceErr.setHostIp(host);
                            serviceErr.setCreateBy(new User("1"));
                            serviceErr.setUpdateBy(new User("1"));
                            serviceErrService=SpringContextHolder.getBean(ServiceErrService.class);
                            serviceErrService.save(serviceErr);

                        }

                    }catch (Exception e){
                        e.printStackTrace();
                        logger.error("存储错误日志错误",e);
                    }
                }

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        //调用start()方法启动consumer
        consumer.start();
        System.out.println("启动完成");

    }

}
