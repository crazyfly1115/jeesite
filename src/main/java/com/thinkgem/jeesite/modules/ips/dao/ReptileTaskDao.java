package com.thinkgem.jeesite.modules.ips.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ips.entity.ReptileTask;
import com.thinkgem.jeesite.modules.ips.entity.RootTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 爬虫任务DAO接口
 * @author zhangsy
 * @version 2019-01-04
 * 尚渝网络
 */
@MyBatisDao
public interface ReptileTaskDao extends CrudDao<ReptileTask> {
    Map getTask(@Param("id")String id);
    List<String> getKeyWords();
    //更新剩余时间
    public  void updateComplateTime(ReptileTask reptileTask);





    //以下是统计接口试用数据
    public Integer zcjl();//总采集量
    public  Integer jrcjl();//今日采集量
    public Integer zrws();//总任务数量
    public  Integer zxzrws();//执行中任务数


    public List jrsjxq();//今日数据详情

    public List jrrwssxx();//今日任务实时信息

    public  List jrsjtop();//

    public List rwbl();
}