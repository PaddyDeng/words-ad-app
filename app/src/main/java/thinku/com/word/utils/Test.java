package thinku.com.word.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/2/5.
 */

public class Test {
    public static void main(String[] args) {
        Map<String,String> map =new HashMap<>();
        map.put("1","1");
        if(null==map.get("0")){
            System.out.println("空");
        }
    }
}
