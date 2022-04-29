package org.scaffold.mybatis.multiDataSource.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：zjd
 * @date ：Created By 2022/4/18 17:12
 * @description：${description}
 * @modified By：
 * @version: $version$
 */
public class CollectionCommmonUtil {

    private static final int batchLimit = 1000;

    /**
     *
     * @param list 需要拆分的集合
     * @param splitSize 按多少数量拆集合 传null值默认1000
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> splitBySize(List<T> list,
                                                Integer splitSize) {
        if (list == null || list.size() < 1) {
            return new ArrayList<>();
        }

        int size = (splitSize == null ? batchLimit : splitSize);

        List<List<T>> returnList = new ArrayList<>();

        List<T> midList = new ArrayList<T>();

        for (int i = 0; i < list.size(); i++) {
            midList.add(list.get(i));

            if ((midList.size()) % size == 0 || i == (list.size() - 1)) {
                returnList.add( new ArrayList<T>(midList));
                midList.clear();
            }
        }

        return returnList;
    }
}
