package com.fly;

/**
 * @author
 * @date 2021/6/8 8:29
 */
public class Myf2 {
    public static void main(String[] args) {
        PhoneCard pc=new PhoneCard(12345,123,30);
        pc.performConnection(12345,123);//МоїХ1
        pc.performDial();
        System.out.println(pc.getBalance());

    }
}
class PhoneCard
{
    private int password;
    long cardNumber;
    double balance;
    boolean connected;

    public PhoneCard(long cn, int pw, double bal)
    {
        cardNumber=cn;
        password=pw;
        balance=bal;
        connected = false; //МоїХ2
    }
    boolean performConnection(long cn, int pw)
    {
        if(this.cardNumber == cn && this.password == pw)//МоїХ3
        {
            connected=true;
        }
		else
        {
            connected=false;
        }
        return connected;// МоїХ4
    }
    double getBalance()
    {
        if(connected) return balance;
        else return -1;
    }
    void performDial()
    {
        if(connected)
            balance-=0.5;//МоїХ5
    }
}

