package com.fly;

/**
 * @author
 * @date 2021/6/7 17:49
 * 【题目】
 *     本题程序的功能，找出10~100之间所有可以表示为两个平方数之和的奇数（如，奇数13可表示为平方式4和平方数9之和）。
 *     采用的算法是：遍历10~100之间的所有奇数，对于每个数判断其是否可以表示为两个平方数之和
 *     （即对于任意小于该奇数x的数i，如果i和x-i均为技术，则说明其可以表示为两个平方数之和）。
 *     最后将满足条件的所有数以形如“13=4+9”的形式输出。
 */
public class Myf1 {
    public static void main(String args[]) {
        int m;
        for (int j = 11; j <= 100; j += 2) {
            m = result(j);
            if ( m!= 0) System.out.println(j + "=" + m + "+" + (j - m));
        }
    }
    static int result(int x)
    {
        int k;
        for(int i=1;i<x-1;i++)
        {
            k=x-i;
            if(isPfh(i)&&isPfh(k))
                return i;
        }
        return 0;
    }
    static boolean isPfh(int y)
    {
        boolean flag =false;
        for(int i=0;i<=Math.sqrt(y);i++)
        {
            if(i*i==y)
            {
                flag=true;
                break;
            }
        }
        return flag;
    }



}
