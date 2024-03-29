package br.com.DevForce.Coffe.on.Validator;

public class ValidadorCpf {

    public static boolean isValid(String cpf) {
        cpf = cpf.trim().replace(".", "").replace("-", "");

        if (cpf.length() != 11 || !cpf.matches("\\d{11}")) {
            return false;
        }

        for (int j = 0; j < 10; j++) {
            if (padLeft(Integer.toString(j), 11).equals(cpf)) {
                return false;
            }
        }

        int[] weights = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += Integer.parseInt(cpf.substring(i, i + 1)) * weights[i + 1];
        }

        int remainder = sum % 11;
        if (remainder < 2) {
            if (Integer.parseInt(cpf.substring(9, 10)) != 0) {
                return false;
            }
        } else {
            if (Integer.parseInt(cpf.substring(9, 10)) != 11 - remainder) {
                return false;
            }
        }

        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += Integer.parseInt(cpf.substring(i, i + 1)) * weights[i];
        }

        remainder = sum % 11;
        if (remainder < 2) {
            return Integer.parseInt(cpf.substring(10)) == 0;
        } else {
            return Integer.parseInt(cpf.substring(10)) == 11 - remainder;
        }
    }

    private static String padLeft(String text, int length) {
        return String.format("%" + length + "s", text).replace(' ', '0');
    }
}