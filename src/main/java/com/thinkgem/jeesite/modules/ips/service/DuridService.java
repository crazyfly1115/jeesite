package com.thinkgem.jeesite.modules.ips.service;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.thinkgem.jeesite.common.persistence.BaseEntity;
import com.thinkgem.jeesite.common.persistence.interceptor.SQLHelper;
import com.thinkgem.jeesite.common.utils.Reflections;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.gen.dao.GenDataBaseDictDao;
import com.thinkgem.jeesite.modules.gen.dao.GenTableDao;
import com.thinkgem.jeesite.modules.gen.entity.GenTable;
import com.thinkgem.jeesite.modules.ips.entity.Database;
import com.thinkgem.jeesite.modules.sys.entity.Log;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * 爬虫服务任务管关联Service
 * @author zhangsy
 * @version 2019-01-03
 *  尚渝网络
 */
@Service
@Transactional(readOnly = true)
public class DuridService{
    private Logger log=Logger.getLogger(DuridService.class);
    public  void getConn() throws Exception {
        Properties properties=new Properties();
        properties.setProperty("url","jdbc:mysql://localhost:3306/ips?useUnicode=true&characterEncoding=utf-8");
        properties.setProperty("username","root");
        properties.setProperty("password","zhangsy");
        properties.setProperty("initialSize","1");
        properties.setProperty("minIdle","3");
        properties.setProperty("maxActive","3");
        DataSource dataSource=DruidDataSourceFactory.createDataSource(properties);

        SqlSessionFactoryBean sqlSessionFactoryBean=new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage("com.thinkgem.jeesite");
        sqlSessionFactoryBean.setTypeAliasesSuperType(BaseEntity.class);
        Resource res = new ClassPathResource("mappings/modules/gen/GenDataBaseDictDao.xml");
        Resource[] resources=new Resource[]{res};
        sqlSessionFactoryBean.setMapperLocations(resources);

        Resource conf = new ClassPathResource("mybatis-config.xml");
        sqlSessionFactoryBean.setConfigLocation(conf);
        SqlSessionFactory sqlSessionFactory= sqlSessionFactoryBean.getObject();
        sqlSessionFactory.openSession().getMapper(GenDataBaseDictDao.class).findTableList(new GenTable());


    }
}