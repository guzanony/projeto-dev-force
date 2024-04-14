package br.com.DevForce.Coffe.on.Validator;

public class ValidadorCpf {

    public static boolean isValid(String cpf) {
        cpf = cpf.trim().replace(".", "").replace("-", "");

        if (cpf.length() != 11 || !cpf.matches("\\d{11}") || hasAllSameCharacters(cpf)) {
            return false;
        }

        int[] weightsFirstDigit = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] weightsSecondDigit = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

        int firstDigit = calculateDigit(cpf.substring(0, 9), weightsFirstDigit);
        int secondDigit = calculateDigit(cpf.substring(0, 10), weightsSecondDigit);

        return cpf.equals(cpf.substring(0, 9) + firstDigit + secondDigit);
    }

    private static boolean hasAllSameCharacters(String cpf) {
        char firstChar = cpf.charAt(0);
        for (int i = 1; i < cpf.length(); i++) {
            if (cpf.charAt(i) != firstChar) {
                return false;
            }
        }
        return true;
    }

    private static int calculateDigit(String str, int[] weights) {
        int sum = 0;
        for (int i = 0; i < str.length(); i++) {
            sum += Integer.parseInt(str.substring(i, i + 1)) * weights[i];
        }

        sum = 11 - (sum % 11);
        return sum > 9 ? 0 : sum;
    }
}
