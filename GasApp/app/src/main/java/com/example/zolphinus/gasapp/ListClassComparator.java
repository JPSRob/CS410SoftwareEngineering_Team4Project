package com.example.zolphinus.gasapp;

import java.util.Comparator;
import java.util.Map;

/**
 * Created by blong824 on StackOverflow
 * @
 * http://stackoverflow.com/questions/5155952/sorting-a-list-of-mapstring-string
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
