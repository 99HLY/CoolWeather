package com.example.admin.myapplication;

public class A {
   public static int count=0;
   public static void main(String args[]){
       (new Thread(){
           @Override
           public void run(){
               System.out.println("hello world1");
               while(true){
                   try {
                       Thread.sleep(1000);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
                   count++;
                   System.out.println("又过去一辆车");
               }
           }
       }).start();

       (new Thread(){
           @Override
           public void run(){
               while (true){
                   try {
                       Thread.sleep(1000);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
                   if(count!=0) {
                       System.out.println("过去的车辆数=>" + count);
                       count = 0;
                   }
               }

           }
       }).start();
   }
   synchronized static void printCar() {
       if (count!=0) {
           System.out.println("过去的车辆数=>" + count);
           try {
               Thread.sleep(1000);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           count = 0;
       }
   }
   synchronized static void countCar(){
       count++;
       System.out.println("又过去一辆车");
   }
}
