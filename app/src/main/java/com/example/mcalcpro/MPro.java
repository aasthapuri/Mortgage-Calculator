package com.example.mcalcpro;

public class MPro {
    public  double Principle;
    public double amortization;
    public double interest;
    public static final int AMORT_MIN=20;
    public static final int AMORT_MAX=50;
    public static final int INTEREST_MAX=50;
    public static final double EPSILON=0.001;

    public MPro(){
        Principle = 0;
        amortization= AMORT_MIN;
        interest=0;


    }
    public void setPrinciple(String p) throws Exception {
        if(Principle >0){
            throw new Exception("P is not numeric and nor positive");
        }
    }
    public void setAmortization(String a) throws Exception {
        if (amortization>AMORT_MAX){
            throw new Exception("not in range");
        }
    }
    public void setInterest(String i) throws Exception {
        if (interest>INTEREST_MAX){
            throw new Exception("not in range");
        }

    }
    public void main(String[] args) throws Exception {

        MPro mp = new MPro();
        mp.setPrinciple("4000000");
        mp.setAmortization("20");
        mp.setInterest("5");
        System.out.println(mp.computePayment("%.,2f"));
        System.out.println(mp.outstandingAfter(2,"%,16.0f"));

    }
    public String computePayment(String fmt){
        int z = 12;
        double r = (interest / 100) / z;
        double n = amortization *z;
        double monthlyPayment = (Principle * r) / (1 - 1 / Math.pow((1 + r), n));
        String result = String.format(fmt, monthlyPayment);
        return result;
    }
    public String outstandingAfter(int years,String fmt){
        interest = (interest) / 1200;
        amortization = years * 12;
        double a = ( Principle ) * interest;
        double b = (1 - (Math.pow((1 + interest), (-(amortization)))));
        double c = a/b;

        return String.format(fmt,c);
    }
}