package com.hhg.jerry.generic;

import com.hhg.jerry.generic.relation.Food;
import com.hhg.jerry.generic.relation.Fruit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by lining on 2018/9/29.
 */
public class MyList<T> implements Iterable<T>{
    private ArrayList<T> arrayList = new ArrayList<T>();
    public boolean add(T t){
        return arrayList.add(t);
    }

    public T get(int index){
        return arrayList.get(index);
    }

    public <K extends Fruit> void taste(MyList<K> myList){
        for(Fruit f : myList){
            f.taste();
        }
    }

    public <M ,N> void genericMethod(M m, N n){
        System.out.println(m.getClass().getSimpleName());
        System.out.println(n.getClass().getSimpleName());
    }

    public <K> K returnGeneric(Object p){
        return (K)p;
    }

    public T next() {
        return arrayList.iterator().next();
    }

    public Iterator<T> iterator() {
        return arrayList.iterator();
    }
}
