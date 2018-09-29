package com.hhg.jerry.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lining on 2018/9/29.
 */
public class LogTest {
    private static Logger logger = LoggerFactory.getLogger(LogTest.class);
    public static void main(String[] args){
        logger.debug("main debug with param {}", "hello,world");
        logger.info("main info with param {}", "hello,world");
        logger.warn("main warn with param {}", "hello,world");
        logger.error("main error with param {}", "hello,world");
        new Thread(){
            public void run() {
                logger.debug("inner thread debug with param {}", "hello,world");
                logger.info("inner thread info with param {}", "hello,world");
                logger.warn("inner thread warn with param {}", "hello,world");
                logger.error("inner thread error with param {}", "hello,world");
            }
        }.start();
    }
}
