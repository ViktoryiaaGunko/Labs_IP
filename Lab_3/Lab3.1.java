// Гунько Виктория

import java.util.StringTokenizer;
import java.util.HashSet;
import java.util.Scanner;

public class Lab3.1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите строку: ");
        String input = scanner.nextLine();
        String result = find(input);
        System.out.println("Слова с наибольшим количеством уникальных символов: " + result);
        scanner.close();
    }

    //функция для поиска слов с наибольшим количеством уникальных символов и выводом результа
    public static String find(String input) {
        
        //StringTokenizer для разбивки строки на слова
        StringTokenizer token = new StringTokenizer(input, " ,;:.!?-");
        
        //создаём пустую строку - объект класса StringBuilder
        StringBuilder max_words = new StringBuilder();
        int max = 0;

        //работаем пока есть слова с помощью метода hasMoreTokens()
        while (token.hasMoreTokens()) {
            //извлекаем следующее слово и сохраняем в новой строке word
            String word = token.nextToken();
            int n = count(word);

            if (n > max) {
                max = n;
                //очищаем StringBuilder max_words
                max_words.setLength(0); 
                //добавляем текущее слово
                max_words.append(word);
            } 
            else if (n == max) {
                if (max_words.length() > 0) {
                    // добавляем пробел перед новым словом
                    max_words.append(" "); 
                }
                //добавляем текущее слово
                max_words.append(word);
            }
        }
        //создаём строку со словами, которые были добавлены в max_words в процессе
        return max_words.toString();
    }

    //функция для подсчёта количества уникальных символов
    private static int count(String word) {
        //объкт для хранения уникальных символов
        //если символ уже есть в наборе, он не будет добавлен повторно
        HashSet<Character> chars = new HashSet<>();
        // преобразуем строку в массив символов
        for (char c : word.toCharArray()) {
            chars.add(c);
        }
        return chars.size();
    }
}