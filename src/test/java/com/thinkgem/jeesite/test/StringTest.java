package com.thinkgem.jeesite.test;

import com.thinkgem.jeesite.common.utils.StringUtils;
import org.junit.Test;

public class StringTest {
    @Test
    public  void  test(){
        boolean s=StringUtils.strIsRigthTable("1");
        System.out.println(s);
    }
}
