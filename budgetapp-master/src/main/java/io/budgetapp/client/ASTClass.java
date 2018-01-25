package io.budgetapp.client;

public class ASTClass {
    public static void instrum(String a, String b)
    {
        System.out.println("the statement of type:"+a+ " is at: " +b+ "");
    }
    public static void instrum(String a, String b, String c,String d)
    {
        System.out.println("the statement:"+ c +" of type:"+a+ " is at: " +d+ " with parent:"+b + "");
    }
}
