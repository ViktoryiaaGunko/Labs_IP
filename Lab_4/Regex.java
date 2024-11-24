// Гунько Виктория 

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Regex {

    public static void main(String[] args) {
        // Чтение содержимого из файла input.txt
        String input = readFile("input.txt");
        StringBuilder output = new StringBuilder();

        // Выполнение первого задания: удаление символов внутри круглых скобок
        output.append("1. Удаление символов внутри круглых скобок:\n")
              .append(removeParentheses(input))
              .append("\n\n");

        // Выполнение второго задания: удаление лишних цифр
        output.append("2. Удаление лишних цифр:\n")
              .append(removeExcessDigits(input))
              .append("\n\n");

        // Выполнение третьего задания: удаление незначащих нулей
        output.append("3. Удаление незначащих нулей:\n")
              .append(removeLeadingZeros(input))
              .append("\n");

        // Запись результатов в файл output.txt
        writeFile("output.txt", output.toString());
    }

    // Метод 1: Удаление символов внутри круглых скобок
    private static String removeParentheses(String str) {
        return str.replaceAll("\\([^()]*\\)", ""); // Удаление текста внутри круглых скобок
    }

    // Метод 2: Удаление лишних цифр
    private static String removeExcessDigits(String str) {
        return str.replaceAll("(\\d{2})(\\d+)", "$1"); // Оставляем только первые две цифры
    }

    // Метод 3: Удаление незначащих нулей
    private static String removeLeadingZeros(String str) {
        return str.replaceAll("\\b0+(\\d)", "$1"); // Замена незначащих нулей
    }

    // Метод для чтения содержимого из файла
    private static String readFile(String fileName) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            // Чтение построчно и добавление каждой строки к содержимому
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace(); // Обработка исключений при чтении файла
        }
        return content.toString().trim(); // Возвращаем содержимое без лишних пробелов в конце
    }

    // Метод для записи содержимого в файл
    private static void writeFile(String fileName, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content); // Запись содержимого в файл
        } catch (IOException e) {
            e.printStackTrace(); // Обработка исключений при записи в файл
        }
    }
}