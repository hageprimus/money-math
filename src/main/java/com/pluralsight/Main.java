package com.pluralsight;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.floor;

public class Main {
    public static void main(String[] args) {
        float testFloat = 0;
        double testDouble = 0;
        BigDecimal testBigDecimal = BigDecimal.ZERO;

        for (var i = 0; i < 10; i++) {
            testFloat += 0.1F;
            testDouble += 0.1;
            testBigDecimal = testBigDecimal.add(BigDecimal.valueOf(0.1));
            //System.out.println("Float: " + testFloat);
            //System.out.println("Double: " + testDouble);
            //System.out.println("BigDecimal: " + testBigDecimal + "\n");
        }

        testFloat = 0;
        testDouble = 0;
        testBigDecimal = BigDecimal.ZERO;

        for (var i = 0; i < 1000; i++) {
            testFloat += 19.99F;
            testDouble += 19.99;
            testBigDecimal = testBigDecimal.add(BigDecimal.valueOf(19.99));
            //System.out.println("Float: " + testFloat);
            //System.out.println("Double: " + testDouble);
            //System.out.println("BigDecimal: " + testBigDecimal + "\n");
        }

        String value1 = "15.25";
        String value2 = "10.67";
        String[] value1Split = value1.split("\\.");
        String[] value2Split = value2.split("\\.");
        int[] array1 = {Integer.parseInt(value1Split[0]), Integer.parseInt(value1Split[1])};
        int[] array2 = {Integer.parseInt(value2Split[0]), Integer.parseInt(value2Split[1])};
        int[] result = decimalArrayCompute(array1, array2, add);
        System.out.println(Double.parseDouble(value1) + Double.parseDouble(value2));
        System.out.println(result[0] + "." + result[1]);
    }

    interface DecimalArrayMath {
        int[] arrayCompute(int[] array1, int[] array2);
    }

    private static int[] decimalArrayCompute(int[] array1, int[] array2, DecimalArrayMath op) {
        return op.arrayCompute(array1, array2);
    }

    static DecimalArrayMath add = (array1, array2) -> {
        int wholePart = array1[0] + array2[0], fracPart;

        int oldFracPart1 = array1[1];
        int oldFracPart2 = array2[1];
        int length1 = array1[1] != 0 ? (int) Math.log10(array1[1]) + 1 : 1;
        int length2 = array2[1] != 0 ? (int) Math.log10(array2[1]) + 1 : 1;
        int longer = length1;
        if (length1 > length2) {
            oldFracPart2 = (int) (oldFracPart2 * Math.pow(10, length1 - length2));
        } else if (length2 > length1) {
            longer = length2;
            oldFracPart1 = (int) (oldFracPart1 * Math.pow(10, length2 - length1));

        }
        int newResult = oldFracPart1 + oldFracPart2;
        int newResultLength = newResult != 0 ? (int) Math.log10(newResult) + 1 : 1;
        int rollover = 0;
        if (newResultLength > longer) {
            rollover = (int) (newResult - (newResult % (Math.pow(10, newResultLength - 1))));
            newResult -= rollover;
            rollover /= (int) (Math.pow(10, newResultLength - 1));
        }
        wholePart += rollover;
        fracPart = newResult;
        return new int[]{wholePart, fracPart};
    };
}
