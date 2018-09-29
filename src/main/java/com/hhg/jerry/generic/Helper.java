package com.hhg.jerry.generic;

import com.hhg.jerry.generic.relation.Apple;
import com.hhg.jerry.generic.relation.Food;
import com.hhg.jerry.generic.relation.Fruit;
import com.hhg.jerry.generic.relation.Orange;

import java.util.List;

/**
 * Created by lining on 2018/9/29.
 */
public class Helper {
    public static <T extends Food> void foodTasteGenericMethod(List<T> foods){
        for(Food f : foods){
            f.taste();
        }
//        foods.add(new Object()); up bound can't add anything
    }

    public static void foodTasteCommonMethod(List<? extends Fruit> fruits){
        for(Fruit f : fruits){
            f.taste();
        }
    }

    public static void produce(List<? super Fruit> fruit){
//        fruit.add(new Food()); can't add class which is Fruit's parent
        fruit.add(new Fruit());
        fruit.add(new Apple());
        fruit.add(new Orange());
//        Fruit f = fruit.get(0); the list type is Fruit's super, can't assign to fruit for any element in the array
    }

}
