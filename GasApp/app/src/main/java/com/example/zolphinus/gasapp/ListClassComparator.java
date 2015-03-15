package com.example.zolphinus.gasapp;

import java.util.Comparator;
import java.util.Map;

/**
 * Created by zolphinus on 3/14/2015.
 */
class ListMapComparator implements Comparator {

    String compareValue;

    ListMapComparator(String newCompareKey){
        compareValue = newCompareKey;
    }

    public int compare(Object obj1, Object obj2) {
        Map<String, String> test1 = (Map<String, String>) obj1;
        Map<String, String> test2 = (Map<String, String>) obj2;
        return test1.get(compareValue).compareTo(test2.get(compareValue));
    }
}
