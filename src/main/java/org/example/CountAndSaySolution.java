package org.example;

import java.util.ArrayList;
import java.util.List;

record Say(char c, int count) {
}

class CountAndSaySolution {
    public String countAndSay(int n) {
        if(n == 1) {
            return "1";
        }

        String last = countAndSay(n - 1);

        int len = last.length(), count = 0;
        char currentChar = last.charAt(0);

        List<Say> says = new ArrayList<>();

        for(int i=0;i<len;i++) {
            char c = last.charAt(i);

            if(currentChar != c) {
                says.add(new Say(currentChar, count));
                currentChar = c;
                count = 0;
            }

            count++;
        }

        says.add(new Say(currentChar, count));

        StringBuilder builder = new StringBuilder();

        for(Say say : says) {
            builder.append(say.count());
            builder.append(say.c());
        }

        return builder.toString();
    }

    void driver() {
        System.out.println(new CountAndSaySolution().countAndSay(5));
    }
}
