package com.fly;

/**
 * @author
 * @date 2021/6/7 17:49
 * ����Ŀ��
 *     �������Ĺ��ܣ��ҳ�10~100֮�����п��Ա�ʾΪ����ƽ����֮�͵��������磬����13�ɱ�ʾΪƽ��ʽ4��ƽ����9֮�ͣ���
 *     ���õ��㷨�ǣ�����10~100֮�����������������ÿ�����ж����Ƿ���Ա�ʾΪ����ƽ����֮��
 *     ������������С�ڸ�����x����i�����i��x-i��Ϊ��������˵������Ա�ʾΪ����ƽ����֮�ͣ���
 *     ������������������������硰13=4+9������ʽ�����
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
