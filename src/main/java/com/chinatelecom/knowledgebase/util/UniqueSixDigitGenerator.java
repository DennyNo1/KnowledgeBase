package com.chinatelecom.knowledgebase.util;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

public class UniqueSixDigitGenerator {
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Set<Integer> generatedNumbers = new HashSet<>();

    public static int generateUniqueSixDigitNumber() {
        while (true) {
            int number = secureRandom.nextInt(900000) + 100000;
            if (generatedNumbers.add(number)) { // add()方法返回true表示集合中没有此元素，成功添加
                return number;
            }
        }
    }
}
