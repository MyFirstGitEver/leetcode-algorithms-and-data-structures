package org.example;

class BigIntegerSolution {
    public String multiply(String num1, String num2) {
        if (num2.equals("0") || num1.equals("0")) {
            return "0";
        }

        int padding = 0;
        String result = "0";

        for(int i = num2.length() - 1;i >= 0;i--) {
            String mulResult = mul(num1, num2.charAt(i) - 48, padding);
            result = add(result, mulResult);
            padding++;
        }

        return result;
    }

    public static String mul(String a, int num, int padding) {
        StringBuilder mulResult = new StringBuilder();
        int carry = 0;

        for(int i=a.length()-1;i>=0;i--) {
            int result = (a.charAt(i) - 48) * num + carry;
            carry = result / 10;

            mulResult.append((char) (result % 10 + 48));
        }

        if(carry != 0) {
            mulResult.append(carry);
        }

        return padding(mulResult.reverse().toString(), padding);
    }

    public static String add(String a, String b) {
        int carry = 0;

        int p = a.length() - 1, q = b.length() - 1;
        int maxLength = Math.max(a.length(), b.length());

        StringBuilder builder = new StringBuilder();
        for(int i=0;i<maxLength;i++) {
            int sum;

            sum = digitAt(a, p) + digitAt(b, q) + carry;

            if(sum >= 10) {
                carry = 1;
                sum -= 10;
            }
            else {
                carry = 0;
            }

            p--;
            q--;
            builder.append(sum);
        }

        if(carry != 0) {
            builder.append("1");
        }

        return builder.reverse().toString();
    }

    public static String padding(String num, int padding) {

        return num + "0".repeat(Math.max(0, padding));
    }

    private static int digitAt(String num, int i) {
        if(i < 0) {
            return 0;
        }

        return num.charAt(i) - 48;
    }
}
