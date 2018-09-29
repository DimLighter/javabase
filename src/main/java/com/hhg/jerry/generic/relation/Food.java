package com.hhg.jerry.generic.relation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lining on 2018/9/29.
 */
public class Food {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    public void taste(){
        logger.info("no taste");
    }
}
