package com.thinkgem.jeesite.durid;
       /*
        *@Author zhangsy
        *@Description  $
        *@Date $ $
        *@Param $
        *@return $
        **/

import com.thinkgem.jeesite.modules.ips.entity.Database;
import com.thinkgem.jeesite.modules.ips.service.DuridService;
import org.junit.Test;

public class TestDuridService {
    @Test
    public  void  Test() throws Exception {
        DuridService duridService=new DuridService();
        duridService.getTables(new Database());
    }
}
