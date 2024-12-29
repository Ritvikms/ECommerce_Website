package com.ecommerce.sportscenter.test;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestLombok {
    private String field1;
    private int field2;

    public static void main(String[] args) {
        // Use Lombok's builder to create an instance
        TestLombok test = TestLombok.builder()
                .field1("Hello Lombok")
                .field2(42)
                .build();

        // Print the fields to verify getter and builder work
        System.out.println("Field1: " + test.getField1());
        System.out.println("Field2: " + test.getField2());
    }
}
