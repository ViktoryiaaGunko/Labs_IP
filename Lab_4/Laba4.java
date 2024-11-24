/* Гунько Виктория 2 курс 5 группа
Лабораторная работа 4.2
Разработать консольное приложение на Java.
Входные данные:
Входной файл input1.html содержит текст, написанный на языке HTML.
В тесте находятся теги. В одной строке может быть несколько тегов. Теги находятся в угловых скобках, 
каждому открывающему тегу ставится в соответствие закрывающий тег. Например, пара тегов<b></b>.
Между тегами находится текст, причем теги не разрывают текст. Например, при поиске слова hello комбинация
h<b><i>el</i>l</b>o должна быть найдена.
Гарантируется,что страница HTML является корректной, т.е. все символы "<" и ">" используются только в тегах, 
все теги записаны корректно.
Входной файл input2.in содержит список фрагментов текста, которые нужно найти в первом файле, записанных 
через разделители (точка с запятой). Может быть несколько строк.
Программа должна игнорировать различие между строчными и прописными буквами и для тегов и для искомого контекста. 
Выходные данные:
1. В выходной файл output1.out вывести список всех тегов в порядке возрастания количества символов тега.
2. В выходной файл output2.out вывести номера строк (нумерация с 0) первого файла, в которых был найден
искомый контекст в первый раз или -1 , если не был найден.
3. В выходной файл output3.out - список фрагментов второго файла, которые НЕ были найдены в первом файле.
*/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Laba4 {

    public static void main(String[] args) {
        try {
            // Чтение HTML файла
            StringBuilder htmlContent = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader("input1.html"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    htmlContent.append(line).append("\n");
                }
            }

            // Чтение фрагментов
            List<String> fragments = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader("input2.in"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    fragments.addAll(Arrays.asList(line.split(";")));
                }
            }

            // 1. Поиск тегов
            Set<String> tags = new TreeSet<>(Comparator.comparingInt(String::length));
            Matcher matcher = Pattern.compile("<[^>]+>").matcher(htmlContent);
            while (matcher.find()) {
                tags.add(matcher.group().toLowerCase());
            }

            // Запись тегов в output1.out
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("output1.out"))) {
                for (String tag : tags) {
                    writer.write(tag);
                    writer.newLine();
                }
            }

            // 2. Поиск строк с фрагментами
            List<Integer> foundLines = new ArrayList<>();
            String[] lines = htmlContent.toString().split("\n");
            for (String fragment : fragments) {
                boolean found = false;
                for (int i = 0; i < lines.length; i++) {
                    if (containsFragment(lines[i], fragment)) {
                        foundLines.add(i);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    foundLines.add(-1);
                }
            }

            // Запись найденных строк в output2.out
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("output2.out"))) {
                for (Integer lineNum : foundLines) {
                    writer.write(lineNum.toString());
                    writer.newLine();
                }
            }

            // 3. Поиск не найденных фрагментов
            List<String> notFound = new ArrayList<>();
            for (String fragment : fragments) {
                if (!foundLines.contains(fragments.indexOf(fragment))) {
                    notFound.add(fragment);
                }
            }

            // Запись не найденных фрагментов в output3.out
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("output3.out"))) {
                for (String missing : notFound) {
                    writer.write(missing);
                    writer.newLine();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean containsFragment(String line, String fragment) {
        String lowerLine = line.toLowerCase().replaceAll("<[^>]+>", ""); // Удаляем теги
        return lowerLine.contains(fragment.toLowerCase());
    }
}