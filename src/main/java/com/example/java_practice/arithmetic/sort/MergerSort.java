package com.example.java_practice.arithmetic.sort;

import java.util.ArrayList;
import java.util.List;

/**
 * 归并排序
 */
public class MergerSort {

    public static <T extends Comparable<? super T>> void sort(List<T> list) {
        System.out.println("原始集合:"+list);
        //将集合对半分为两个集合，对每个子集合进行排序，接着将排序好的子集合合并起来
        List<T> data = sort_part(list, 0, list.size() - 1);
        System.out.println("排序后集合"+data);
    }

    //将集合对半拆分2个
    private static <T extends Comparable<? super T>> List<T> sort_part(List<T> list, int begin, int end) {
        //递归的终止条件
        if (list.size() <= 1) {
            return list;
        }
        //找到中间下标,拆分两个集合
        int mid = (begin + end) / 2;
        List<T> listA;
        List<T> listB;
        if (mid == 0) {
            listA = list.subList(0,1);
            listB = list.subList(1,2);
        } else {
            listA = list.subList(begin, mid);
            listB = list.subList(mid, end+1);
        }
        //分别排序两个子集合
        List<T> sortA = sort_part(listA, 0, listA.size() - 1);
        List<T> sortB = sort_part(listB, 0, listB.size() - 1);
        //合并两个有序集合
        return merge(sortA,sortB);
    }


    //合并两个有序集合
    private static <T extends Comparable<? super T>> List<T> merge(List<T> listA, List<T> listB) {
        List<T> data = new ArrayList<>();

        int i = 0;
        int j = 0;

        //比较这两个元素 A[i]和 A[j]，如果 A[i]<=A[j]，我们就把 A[i]放入到临时数组 tmp，并且 i 后移一位，否则将 A[j]放入到数组 tmp，j 后移一位
        //直到其中一个子数组中的所有数据都放入临时数组中，再把另一个数组中的数据依次加入到临时数组的末尾
        while (!(i >= listA.size() || j >= listB.size())) {
            T t1 = listA.get(i);
            Comparable c1 = (Comparable) t1;
            T t2 = listB.get(j);
            Comparable c2 = (Comparable) t2;
            if (c1.compareTo(c2) < 0) {
                data.add(t1);
                i++;
            } else {
                data.add(t2);
                j++;
            }
        }

        //将剩余的数据放入集合
        if (i < listA.size()) {
            List<T> subList = listA.subList(i, listA.size());
            data.addAll(subList);
        }
        if (j < listB.size()) {
            List<T> subList = listB.subList(j, listB.size());
            data.addAll(subList);
        }

        return data;
    }
}
