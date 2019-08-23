package com.thinkgem.jeesite.modules.ips.entity.mq;

/**
 * Copyright 2019 bejson.com
 */

/**
 * Auto-generated: 2019-04-15 10:28:25
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class QueueStatInfoList {

    private String brokerName;
    private int queueId;
    private int brokerOffset;
    private int consumerOffset;
    private String clientInfo;
    private int lastTimestamp;
    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }
    public String getBrokerName() {
        return brokerName;
    }

    public void setQueueId(int queueId) {
        this.queueId = queueId;
    }
    public int getQueueId() {
        return queueId;
    }

    public void setBrokerOffset(int brokerOffset) {
        this.brokerOffset = brokerOffset;
    }
    public int getBrokerOffset() {
        return brokerOffset;
    }

    public void setConsumerOffset(int consumerOffset) {
        this.consumerOffset = consumerOffset;
    }
    public int getConsumerOffset() {
        return consumerOffset;
    }

    public void setClientInfo(String clientInfo) {
        this.clientInfo = clientInfo;
    }
    public String getClientInfo() {
        return clientInfo;
    }

    public void setLastTimestamp(int lastTimestamp) {
        this.lastTimestamp = lastTimestamp;
    }
    public int getLastTimestamp() {
        return lastTimestamp;
    }

}