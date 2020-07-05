package com.test;

public class ThreadExample {
    static Runtime r; 
    static Thread th1;
    static Thread th2;
	
	public static void main(String[] arg) 
	{
		ThreadExample the=new ThreadExample();
		r = Runtime.getRuntime();
		System.out.println("max memory :" + r.maxMemory());
		System.out.println("max memory :" + r.availableProcessors());
		th1= new ThreadClass("beant");
		th1.start();
		th2= new ThreadClass("soyef");
		th2.start();
	}
	
}

