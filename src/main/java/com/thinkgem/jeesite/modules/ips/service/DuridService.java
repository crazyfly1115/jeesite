package com.thinkgem.jeesite.modules.ips.service;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.thinkgem.jeesite.common.persistence.BaseEntity;
import com.thinkgem.jeesite.common.persistence.interceptor.SQLHelper;
import com.thinkgem.jeesite.common.utils.AssertUtil;
import com.thinkgem.jeesite.common.utils.Reflections;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.gen.dao.GenDataBaseDictDao;
import com.thinkgem.jeesite.modules.gen.dao.GenTableDao;
import com.thinkgem.jeesite.modules.gen.entity.GenTable;
import com.thinkgem.jeesite.modules.gen.entity.GenTableColumn;
import com.thinkgem.jeesite.modules.ips.entity.Database;
import com.thinkgem.jeesite.modules.sys.entity.Log;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.List;
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

    //get tables
    public  List<GenTable>  getTables(Database database){
        SqlSession sqlSession=getSqlSessionFactory(database).openSession();
        List<GenTable> list=sqlSession.getMapper(GenDataBaseDictDao.class).findTableList(new GenTable());
        sqlSession.close();
        return list;

    }
    //get columns
    public List<GenTableColumn> getColumnByTable(Database database,String tableName){
        AssertUtil.notNull(tableName,"表不能为空");

        GenTable genTable=new GenTable();
        genTable.setName(tableName);

        SqlSession sqlSession=getSqlSessionFactory(database).openSession();
        List<GenTableColumn> list=sqlSession.getMapper(GenDataBaseDictDao.class).findTableColumnList(genTable);
        sqlSession.close();
        return list;
    }
    /*
     * @Author zhangsy
     * @Description  DuridService
     * @Date 23:51 2019/1/4
     * @Param [database]
     * @return org.apache.ibatis.session.SqlSessionFactory
     * @Company 重庆尚渝网络科技
     * @version v1000
     **/
    public SqlSessionFactory getSqlSessionFactory(Database database){
        Properties properties=new Properties();

        AssertUtil.notNull(database.getDatabaseUrl(),"数据库连接不能为空");
        AssertUtil.notNull(database.getLoginUser(),"数据库用户名不能为空");
        AssertUtil.notNull(database.getLoginPsw(),"数据库密码不能为空");


        properties.setProperty("url",database.getDatabaseUrl());//"jdbc:mysql://localhost:3306/ips?useUnicode=true&characterEncoding=utf-8"
        properties.setProperty("username",database.getLoginUser());
        properties.setProperty("password",database.getLoginPsw());
        properties.setProperty("initialSize","1");
        properties.setProperty("minIdle","1");
        properties.setProperty("maxActive","1");
        DataSource dataSource= null;
        try {
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException("创建连接失败,请检查URL,USERNAME,PASSWD");
        }

        SqlSessionFactoryBean sqlSessionFactoryBean=new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage("com.thinkgem.jeesite");
        sqlSessionFactoryBean.setTypeAliasesSuperType(BaseEntity.class);
        Resource res = new ClassPathResource("mappings/modules/gen/GenDataBaseDictDao.xml");
        Resource[] resources=new Resource[]{res};
        sqlSessionFactoryBean.setMapperLocations(resources);

        Resource conf = new ClassPathResource("mybatis-config.xml");
        sqlSessionFactoryBean.setConfigLocation(conf);
        SqlSessionFactory sqlSessionFactory= null;
        try {
            sqlSessionFactory = sqlSessionFactoryBean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException("创建连接失败,请检查URL,USERNAME,PASSWD");
        }
        return  sqlSessionFactory;
    }
}