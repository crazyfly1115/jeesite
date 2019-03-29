package com.thinkgem.jeesite.modules.ips.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Lazy(false)
public class TaskService {
    @Autowired
    private  ReptileTaskService reptileTaskService;
    @Scheduled(cron = "0/30 * * * * ?")
    public  void saveTaskData(){
        System.out.println(1);
    }
}
