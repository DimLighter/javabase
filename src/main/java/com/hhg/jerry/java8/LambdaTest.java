package com.hhg.jerry.java8;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

/**
 * Created by lining on 2018/10/17.
 */
public class LambdaTest {
    private static Logger logger = LoggerFactory.getLogger(LambdaTest.class);
    private static final String NO_LAMBDA = "[No lambda]";
    private static final String WITH_LAMBDA = "[With lambda]";
    public static void main(String[] args){

        List<String> listString = Arrays.asList( "l", "a", "m","b", "d", "a");
        System.out.print("no lambda:");
        for(String s : listString){
            System.out.print(s);
        }
        System.out.println();
        System.out.print("lambda:");
        listString.forEach(e -> System.out.print( e ) );
        System.out.println();

        logger.info("a count in listString : {}", listString.stream().filter(s -> s.equals("a")).count());

        // 类似Runnable 只有一个方法（这种接口称函数接口@FunctionalInterface） 原来用匿名内部类 lambda只需匿名方法
        new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info(NO_LAMBDA);
            }
        }).start();

        new Thread(()-> {
            logger.info(WITH_LAMBDA);
        }).start();

        System.out.println(functionInterface1((x)-> "hi " + x));
        functionInterface2(()-> logger.info("ops")); //compile error when more than 1 method in the interface, 但如果用default关键字 则是可行的

        List<Person> personList = new ArrayList<>(
                Arrays.asList(new Person("John",11,Person.Sex.MALE),
                        new Person("Robbin",22,Person.Sex.MALE),
                        new Person("Sarah",23,Person.Sex.FEMALE),
                        new Person("Amanda",23,Person.Sex.FEMALE))) ;

        //将年龄大于12的Person名字和性别拼接起来再用“，”隔开，结果用“[”“]”括起来
        logger.info(personList.parallelStream()
                .filter(person -> person.getAge() > 12)
                .map(person -> person.getName() + "-" + person.getAge())
                .collect(Collectors.joining(",","[","]")));

        //对性别为男的Person的年龄求和
        logger.info("total age of male: {}", personList.stream()
                .filter(p-> p.getGender() == Person.Sex.MALE)
                .mapToInt(a->a.getAge()).sum());

        //对所有实例的年龄求和
        logger.info(personList.stream()
                .map(p->p.getAge())
                .reduce((x,y)->x+y).get() + "");

        int initNumber = 1;
        IntFunction<Integer> plus =val -> val +3;
        logger.info("IntFunction result : {}" , compute(initNumber, plus));
        logger.info("Function andThen result : {}" , (doubleOp(3, val -> val + 2, val -> val + 3)));

        Function<Person,Integer> getAge = p -> p.getAge();
        Function<Integer,Boolean> isAdult = a -> a>=18;
        logger.info("Function compose result : {}" , isAdult.compose(getAge).apply(new Person("test",19,Person.Sex.MALE)));
    }

    public static String functionInterface1(MyInter1 myInter1){
        return myInter1.dummy1("James");
    }

    public static void functionInterface2(MyInter2 myInter2){
        myInter2.dummy1();
        myInter2.dummy2();
        MyInter2.dummy3();
    }

    static Integer compute(int value, IntFunction<Integer> function){
        return  function.apply(value);
    }
    static Integer doubleOp(int value, Function<Integer, Integer> function1, Function<Integer, Integer> function2){
        return  function1.andThen(function2).apply(value);
    }
}

interface MyInter1{
    String dummy1(String name);
    boolean equals(Object object);
}

interface MyInter2{
    void dummy1();
    default void dummy2(){
        System.out.println("default method for dummy2");
    }
    static void dummy3(){
        System.out.println("static method for dummy3");
    }
}
