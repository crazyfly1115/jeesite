package com.thinkgem.jeesite.common.utils;

import org.springframework.util.Assert;
/**
 * @Author zhangsy
 * @Description  AssertUtil 继承Assert
 * @Date 23:11 2019/1/4
 * @Param 
 * @return 
 * @Company 重庆尚渝网络科技
 * @version v1000
 **/
public class AssertUtil  extends Assert {
    /**
     * @Author zhangsy
     * @Description  AssertUtil 判断为空,直接断言
     * @Date 23:16 2019/1/4
     * @Param [o, errmsg]
     * @return void
     * @Company 重庆尚渝网络科技
     * @version v1000
     **/
    public  void notEmpty(Object o,String errmsg){
        notNull(o);
        if(o instanceof String){
            if(StringUtils.isBlank(o.toString()))
                throw new IllegalArgumentException(errmsg);
        }
    }
    public  void notEmpty(Object o){
        String errmsg="该参数不能为空";
        notNull(o,errmsg);
        if(o instanceof String){
            if(StringUtils.isBlank(o.toString()))
                throw new IllegalArgumentException(errmsg);
        }
    }
}
