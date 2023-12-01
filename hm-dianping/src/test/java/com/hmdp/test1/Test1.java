package com.hmdp.test1;

import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Data
public class Test1 {
    private Integer numInteger = 128;
    private Long numLong = -128L;
    @Test
    public void method2() {
        Test1 t = new Test1();
        Test1 t2 = new Test1();
        if(numInteger == t.numInteger) {
            System.out.println("ok");
        } else {
            System.out.println("no");
        }
        if(numInteger == t2.numInteger) {
            System.out.println("ok");
        } else {
            System.out.println("no");
        }
        if(t.numInteger == t2.numInteger) {
            System.out.println("ok");
        } else {
            System.out.println("no");
        }
        if(numLong == t.numLong) {
            System.out.println("ok");
        } else {
            System.out.println("no");
        }
        if(numLong == t2.numLong) {
            System.out.println("ok");
        } else {
            System.out.println("no");
        }
        if(t.numLong == t2.numLong) {
            System.out.println("ok");
        } else {
            System.out.println("no");
        }
    }

    @Test
    public void method3() {
        String s = UUID.randomUUID().toString().replace("-", "");
        System.out.println(s);
    }
}
