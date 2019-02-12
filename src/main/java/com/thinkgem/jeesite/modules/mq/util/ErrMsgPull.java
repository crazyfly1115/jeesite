package com.thinkgem.jeesite.modules.mq.util;

import java.util.*;

import com.thinkgem.jeesite.common.config.Global;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.impl.consumer.PullResultExt;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;


public class ErrMsgPull extends Thread
{
    private final String serveraddr= Global.getConfig("mq_serveraddr");
    private final String nameserveraddr= Global.getConfig("mq_nameserveraddr");
    private final String task_id="ErrorTopic";//错误信息日志
    private Map<MessageQueue, Long> offsetTable = new HashMap<MessageQueue, Long>();
    private DefaultMQPullConsumer consumer;

    public void run()
    {
        try
        {
            MqTask t = MqServer.map_task.get(task_id);
            consumer = new DefaultMQPullConsumer("pullconsumer"
                    + ContainerInfo.container_ip.replaceAll(".", "")
                    + task_id);
            consumer.setNamesrvAddr(nameserveraddr);//t.getTask_mq_nameserveraddr()
            consumer.start();
            boolean existmqs = false;
            Set<MessageQueue> mqs = null;
//            while (MqServer.map_task.containsKey(task_id))
//            {
                if (!existmqs)
                {
                    try
                    {
                        mqs = consumer.fetchSubscribeMessageQueues(t
                                .getTask_mq_topic());
                        existmqs = true;
                        System.out.println("Message Task[" + t.getTask_id()
                                + "] Started.....");
                    }
                    catch (Exception ee)
                    {
                        ee.printStackTrace();
                        System.out.println("消息队列未找到:" + t.getTask_id());
                    }
                }
                // 判断队列是否存在，存在才开始取

                if (t.getTask_mq_status().equals("ON") && existmqs)
                {
                    for (MessageQueue mq : mqs)
                    {
                        try
                        {
                            long offset = getOffset(t, mq, true);
                            PullResultExt pullResult = (PullResultExt) consumer
                                    .pull(mq, t.getTask_mq_tag(), offset, 1,
                                            4000);
                            updateOffset(mq, pullResult.getNextBeginOffset());
                            switch (pullResult.getPullStatus())
                            {
                                case FOUND:
                                    List<MessageExt> messageExtList = pullResult
                                            .getMsgFoundList();
                                    for (MessageExt m : messageExtList)
                                    {
                                        DataInput.DataInputFromMq(
                                                new String(m.getBody()), t);
                                        t.setTask_mq_lastid(offset);
                                        MqServer.map_task.put(t.getTask_id(), t);
                                    }
                                    break;
                                case NO_MATCHED_MSG:
                                    break;
                                case NO_NEW_MSG:
                                    break;
                                case OFFSET_ILLEGAL:
                                    break;
                                default:
                                    break;
                            }
                            System.out.println("从队列【" + t.getTask_mq_topic()
                                    + "】取脚标为【" + offset + "】的消息");
                        }
                        catch (Exception ee)
                        {
                            System.out.println(ee.getMessage());
                        }
                    }
                /*}*/
                Thread.sleep(t.getTask_mq_eachpulltime());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            consumer.shutdown();
        }
    }

    private long getOffset(MqTask t, MessageQueue mq, boolean fromStore)
            throws MQClientException
    {
        Long al = offsetTable.get(mq);
        if (null == al)
        {
            // long offset = consumer.fetchConsumeOffset(mq, fromStore);
            long offset = t.getTask_mq_lastid();
            return offset;
        }
        return al;
    }

    public void updateOffset(MessageQueue mq, long offset)
            throws MQClientException
    {
        offsetTable.put(mq, offset);
        consumer.updateConsumeOffset(mq, offset);
    }

}
