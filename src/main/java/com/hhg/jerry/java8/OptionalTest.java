package com.hhg.jerry.java8;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Created by lining on 2018/10/17.
 */
public class OptionalTest {
    private static Logger logger = LoggerFactory.getLogger(OptionalTest.class);

    public static void main(String[] args){
        //construct optional
        Optional<String> optionalStr = Optional.of("en-hen");
//        Optional<String> optionalNullStr = Optional.of(null); throw npe

        Optional<String> optionalNull = Optional.ofNullable(null); //not throw any ex
        Optional<String> optionalNullableButNotNull = Optional.ofNullable("not null");

        //check is not null
        logger.info("isPresent optionStr : {}", optionalStr.isPresent());
        logger.info("isPresent optionalNull : {}", optionalNull.isPresent());

        //存在就消费之,Consumer
        optionalStr.ifPresent((x)-> logger.info("consumer the input : {}", x));
        optionalStr.ifPresent(logger::debug);

        //orElse 相当于get(string key, string default) 若不存在 则返回默认值
        logger.info("I have value : {}", optionalStr.orElse("else is a default value"));
        logger.info("I have a default value : {}", optionalNull.orElse("else is a default value"));

        //orElseGet method ---->>> return value != null ? value : other.get(); 给一个supplier
        logger.info(optionalStr.orElseGet(()->"supplier provided..."));
        logger.info(optionalNull.orElseGet(()->"supplier provided..."));

        Optional<String> filterLengthGt10 = optionalStr.filter(s -> s.length()>10);
        Optional<String> filterLengthGt2 = optionalStr.filter(s -> s.length()>2);
        logger.info("filterLengthGt10 is present : {}", filterLengthGt10.isPresent());
        logger.info("filterLengthGt2 is present : {}", filterLengthGt2.isPresent());
        logger.info("filterLengthGt2.equals(optionalStr) : {}", filterLengthGt2.equals(optionalStr));
    }
}
