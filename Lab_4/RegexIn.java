// Гунько Виктория 

import java.util.Scanner;
import java.util.regex.Pattern;

public class RegexIn {

    // Регулярное выражение для проверки MAC-адреса
    private static final String MAC_REGEX = "^([0-9a-fA-F]{2}(:|[-])?){5}([0-9a-fA-F]{2})$";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите строку для проверки на MAC-адрес: ");
        String input = scanner.nextLine();

        if (isValidMacAddress(input)) {
            System.out.println("Строка является правильным MAC-адресом.");
        } else {
            System.out.println("Строка не является правильным MAC-адресом.");
        }

        scanner.close();
    }

    // Метод для проверки, является ли строка правильным MAC-адресом
    private static boolean isValidMacAddress(String mac) {
        // Проверка строки с использованием регулярного выражения
        return Pattern.matches(MAC_REGEX, mac);
    }
}