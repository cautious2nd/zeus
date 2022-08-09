package org.scaffold.mybatis.multiDataSource.constant;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DBConstant {

    public static final String MASTER = "master";
    public static final String SLAVE1 = "slave1";
    public static final String SLAVE2 = "slave2";
    public static final String SLAVE3 = "slave3";
    public static final String SLAVE4 = "slave4";

    public static final List<String> dBConstants= Arrays.asList(MASTER,SLAVE1
            ,SLAVE2,SLAVE3,SLAVE4);
}
