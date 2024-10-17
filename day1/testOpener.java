package day1;

import java.util.Scanner;

import librerias.FileOpener;

public class testOpener {

    public static void main(String[] args) {

        Scanner file = FileOpener.opener(1, 1);

        System.out.println(file.nextLine());
    }

    public void test(){
        System.out.println(this.getClass().getCanonicalName());
    }
    
}
