package com.tongbanjie.baymax.utils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by sidawei on 16/4/2.
 */
public class NewArrayList{

    public static NewArrayList newIt(){
        return new NewArrayList();
    }

    public ArrayList toArrayList(){
        return instances;
    }

    private ArrayList instances;

    private NewArrayList(){
        this.instances = new ArrayList();
    }

    public NewArrayList add(Object o) {
        instances.add(o);
        return this;
    }

    public NewArrayList addAll(Collection collection) {
        instances.addAll(collection);
        return this;
    }
}
