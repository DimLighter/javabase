package com.hhg.jerry.generic;

import com.hhg.jerry.generic.relation.Apple;
import com.hhg.jerry.generic.relation.Food;
import com.hhg.jerry.generic.relation.Fruit;
import com.hhg.jerry.generic.relation.Orange;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lining on 2018/9/29.
 */
public class GenericTest {

    @Test
    public void genericErase() {
        MyList<Integer> intMyList = new MyList<Integer>();
        MyList<String> stringMyList = new MyList<String>();
        Assert.assertTrue(intMyList.getClass() == stringMyList.getClass());
    }

    @Test
    public void foodTaste() {
        List<Fruit> fruits = new ArrayList<Fruit>();
        Helper.produce(fruits);
        Helper.foodTasteGenericMethod(fruits);
        Helper.foodTasteCommonMethod(fruits);
    }

    @Test
    public void myListTasteTest() {
        MyList<Fruit> fruits = new MyList<Fruit>();
        fruits.add(new Apple());
        fruits.taste(fruits);
    }

    @Test
    public void genericMethodTest() {
        MyList<Integer> myList = new MyList<Integer>();
        myList.genericMethod(new String("abc"), new Long(5L));
    }

    @Test
    public void returnGenericTest() {
        MyList<Integer> myList = new MyList<Integer>();
        Long longVal = myList.returnGeneric(5L);
        String strVal = myList.returnGeneric("hi");
        System.out.println();
    }

    private Map<String, Integer> map;

    @Test
    public void getGenericTypeInfo() throws Exception {
        Field field = GenericTest.class.getDeclaredField("map");
        Type type = field.getGenericType();
        if(type instanceof ParameterizedType){
            ParameterizedType parameterizedType = (ParameterizedType)type;
            for(Type t : parameterizedType.getActualTypeArguments()){
                if(t instanceof Class)
                    System.out.println(((Class<?>)t).getName());
            }
            System.out.println("raw type:" + ((Class<?>)parameterizedType.getRawType()).getName());
        }
    }
}
