package cn.nizuge.config;
/**
 * 服务器启动时自动执行
 */


import cn.nizuge.mongo.dao.RSAKeyPair;
import cn.nizuge.mongo.dao.VenkasDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@Order(value = 1)
public class MyApplicationRunner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(MyApplicationRunner.class);

    @Autowired
    GeneralConfig config;
    @Autowired
    RSAKeyPair rsaKeyPair;
    @Autowired
    VenkasDB venkasDB;


    @Override
    public void run(ApplicationArguments arg) throws Exception {
        logger.info("====== mongoDB 初始化 =======");
        venkasDB.initVenkasDB();
        logger.info("====== base64密钥对获取 =======");
        rsaKeyPair.loadBase64KeyPair();
    }

}
