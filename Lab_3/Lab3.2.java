// Гунько Виктория 

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Main {
    private String search; //переменнаядля хранения слова, которое будем искать

    //конструктор 
    public Main(String search) {
        //приводим к нижнему регистру
        this.search = search.toLowerCase(); 
    }

    // функция для подсчета вхождений
    public int count(String text) {
        
        //переменная для хранения вхождений слова
        //StringTokenizer разбивает строку text на токены 
        StringTokenizer token = new StringTokenizer(text, " ,.-—!?:;\"'\n\r\t");
        int count = 0;

        while (token.hasMoreTokens()) {
            //переходим на следующе слово и приводим к нижнему регистру
            String word = token.nextToken().toLowerCase(); 
            if (word.equals(search))//слово совпадает
            { count++;
                
            }
        }
        return count;
    }

    public static void main(String[] args) {
        String in = "input.txt";
        //StringBuilder для накопления текста из файла (добавления строки)
        StringBuilder text = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(in))) {
            String first = br.readLine(); 
            //объект для хранения строки
            Main word = new Main(first.trim()); 

            String line;
            //считываем остальные строки из файла и добавляет их в text, разделяя пробелом
            while ((line = br.readLine()) != null) {
                text.append(line).append(" "); 
            }

            String Text = text.toString();
            int n = word.count(Text);
            System.out.println("Слово \"" + first + "\" встречается " + n + " раз(а) в тексте.");
        } 
        //Если во время чтения файла возникает ошибка, программа выведет стек вызовов исключения
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}