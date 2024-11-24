/* Гунько Виктория 2 курс 5 группа
Лабораторная работа 5
Задача:
Входные данные:
Входной файл input содержит данные в формате CSV.
Каждая запись в файле расположена на новой строке.
Разделителем между полями одной записи является символ точка с запятой (';').
Если значения какого-то поля записи нет, то разделить все равно присутствует.
Обязательными для заполнения являются только те поля, по которым строятся запросы для выборки данных.

Формат входных данных:
Имеется список компаний.
Каждый элемент списка содержит следующие поля:
   Наименование (name)
    Краткое наименование (shortTitle)
    Дата актуализации (dateUpdate)
    Адрес (address)
    Дата основания (dateFoundation)
    Численность сотрудников (countEmployees)
    Аудитор (auditor)
    Телефон (phone)
    Email (email)
    Отрасль (branch)
    Вид деятельности (activity)
    Адрес страницы в Интернет (internetAddress/link)

Выходные данные
1. Прочитать данные из входного файла.
2. Выбрать данные по запросу.
3. Записать полученные данные в два файла (в XML-формате и JSON).

Запросы
1. Найти компанию по краткому наименованию.
2. Выбрать компании по отрасли.
3. Выбрать компании по виду деятельности.
4. Выбрать компании по дате основания в определенном промежутке (с и по).
5. Выбрать компании по численности сотрудников в определенном промежутке (с и по).

Примечания
1. Ваша программа должна игнорировать различие между строчными и прописными буквами.
2. Необходимо вести статистику работы приложения в файле logfile.txt. Данные должны накапливаться.
Формат данных: дата и время запуска пиложения; текст запроса; количество н
*/

import java.io.*;
import java.text.*;
import java.util.*;
import java.util.stream.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.Scanner;

class Company {
    String name;
    String shortTitle;
    String dateUpdate;
    String address;
    String dateFoundation;
    String countEmployees;
    String auditor;
    String phone;
    String email;
    String branch;
    String activity;
    String internetAddress;

    /*Конструктор для создания экземпляров класса Company на основе данных из CSV файла.
    Этим мы обеспечиваем корректное инициализацию всех полей объекта, даже если некоторые данные отсутствуют.  */
    public Company(String[] parts) {
        this.name = parts.length > 0 ? parts[0].trim() : null;
        this.shortTitle = parts.length > 1 ? parts[1].trim() : null;
        this.dateUpdate = parts.length > 2 ? parts[2].trim() : null;
        this.address = parts.length > 3 ? parts[3].trim() : null;
        this.dateFoundation = parts.length > 4 ? parts[4].trim() : null;
        this.countEmployees = parts.length > 5 ? parts[5].trim() : null;
        this.auditor = parts.length > 6 ? parts[6].trim() : null;
        this.phone = parts.length > 7 ? parts[7].trim() : null;
        this.email = parts.length > 8 ? parts[8].trim() : null;
        this.branch = parts.length > 9 ? parts[9].trim() : null;
        this.activity = parts.length > 10 ? parts[10].trim() : null;
        this.internetAddress = parts.length > 11 ? parts[11].trim() : null;
    }
    // Геттеры для всех полей
    public String getName() {
        return name;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public String getDateUpdate() {
        return dateUpdate;
    }

    public String getAddress() {
        return address;
    }

    public String getDateFoundation() {
        return dateFoundation;
    }

    public String getCountEmployees() {
        return countEmployees;
    }

    public String getAuditor() {
        return auditor;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getBranch() {
        return branch;
    }

    public String getActivity() {
        return activity;
    }

    public String getInternetAddress() {
        return internetAddress;
    }
}

public class CompanyDataProcessor {
    private List<Company> companies = new ArrayList<>(); //данные о загруженных компаниях
    private static final String LOG_FILE = "logfile.txt"; //Константа для имени файла журнала

    public static void main(String[] args) {
        CompanyDataProcessor processor = new CompanyDataProcessor();
        //loadData обрабатывает файл и добавляет каждую компанию в список companies
        processor.loadData("input.csv");
        //вызов записывает сообщение в журнал, что приложение было запущено
        processor.log("Приложение запущено.");

        // Обработка запросов из консоли
        processor.processQueries();

        processor.saveToXML("output.xml");
        processor.saveToJSON("output.json");
    }

    //загрузка данных о компаниях из файла в список объектов
    public void loadData(String F) {
        //BufferedReader обеспечивает эффективное считывание текста из файла построчно
        try (BufferedReader reader = new BufferedReader(new FileReader(F))) {
            // хранения текущей строки
            String line;
            while ((line = reader.readLine()) != null) {
                //Каждая считанная строка разбивается на массив строк parts
                String[] parts = line.split(";");
                companies.add(new Company(parts));
            }
            log("Данные успешно загружены.");
        } catch (IOException e) {
            log("Ошибка при загрузке данных: " + e.getMessage());
        }
    }

    //метод для ввода запросов
    public void processQueries() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            /*Запросы
            1. Найти компанию по краткому наименованию.
            2. Выбрать компании по отрасли.
            3. Выбрать компании по виду деятельности.
            4. Выбрать компании по дате основания в определенном промежутке (с и по).
            5. Выбрать компании по численности сотрудников в определенном промежутке (с и по). */
            System.out.println("Введите запрос (1 - по краткому наименованию, 2 - по отрасли, 3 - по виду деятельности, 4 - по дате основания, 5 - по численности сотрудников, 6 - для выхода) : ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("6")) {
                break;
            }

            switch (input.toLowerCase()) {
                case "1":
                    System.out.print("Введите сокращенное название: ");
                    String shortTitle = scanner.nextLine();
                    findByShortTitle(shortTitle);
                    break;
                case "2":
                    System.out.print("Введите отрасль: ");
                    String branch = scanner.nextLine();
                    findByBranch(branch);
                    break;
                case "3":
                    System.out.print("Введите вид деятельности: ");
                    String activity = scanner.nextLine();
                    findByActivity(activity);
                    break;
                case "4":
                    System.out.print("Введите начальную дату (yyyy-MM-dd): ");
                    String startDate = scanner.nextLine();
                    System.out.print("Введите конечную дату (yyyy-MM-dd): ");
                    String endDate = scanner.nextLine();
                    findByFoundationDateRange(startDate, endDate);
                    break;
                case "5":
                    System.out.print("Введите минимальное количество сотрудников: ");
                    int min = Integer.parseInt(scanner.nextLine());
                    System.out.print("Введите максимальное количество сотрудников: ");
                    int max = Integer.parseInt(scanner.nextLine());
                    findByEmployeeCountRange(min, max);
                    break;
                default:
                    System.out.println("Некорректный запрос. ");
            }
        }
        scanner.close();
    }

    // поиск компаний по сокращенному названию
    public void findByShortTitle(String shortTitle) {
        // Используем стрим для фильтрации списка компаний
        List<Company> result = companies.stream()
                // Фильтруем компании, у которых короткое название не null и совпадает с заданным (игнорируя регистр)
                .filter(c -> c.shortTitle != null && c.shortTitle.equalsIgnoreCase(shortTitle))
                // Собираем отфильтрованные компании в новый список
                .collect(Collectors.toList());

        // Записываем в журнал информацию о выполненном запросе и количестве найденных компаний
        log("Запрос по короткому названию: " + shortTitle + ", найден: " + result.size());
        // Выводим названия всех найденных компаний в консоль
        result.forEach(c -> System.out.println(c.name));
    }

    // поиск компаний по ветви
    public void findByBranch(String branch) {
        // Используем стрим для фильтрации списка компаний
        List<Company> result = companies.stream()
                // Фильтруем компании, у которых ветвь не null и совпадает с заданной (игнорируя регистр)
                .filter(c -> c.branch != null && c.branch.equalsIgnoreCase(branch))
                // Собираем отфильтрованные компании в новый список
                .collect(Collectors.toList());

        // Записываем в журнал информацию о выполненном запросе и количестве найденных компаний
        log("Запрос по ветвям: " + branch + ", найден: " + result.size());
        // Выводим названия всех найденных компаний в консоль
        result.forEach(c -> System.out.println(c.name));
    }

    // поиск компаний по виду деятельности
    public void findByActivity(String activity) {
        // Используем стрим для фильтрации списка компаний
        List<Company> result = companies.stream()
                // Фильтруем компании, у которых вид деятельности не null и совпадает с заданным (игнорируя регистр)
                .filter(c -> c.activity != null && c.activity.equalsIgnoreCase(activity))
                // Собираем отфильтрованные компании в новый список
                .collect(Collectors.toList());

        // Записываем в журнал информацию о выполненном запросе и количестве найденных компаний
        log("Запрос по виду деятельности: " + activity + ", найден: " + result.size());

        // Выводим названия всех найденных компаний в консоль
        result.forEach(c -> System.out.println(c.name));
    }
    // Метод для поиска компаний по диапазону дат основания
    public void findByFoundationDateRange(String startDate, String endDate) {
        // Используем стрим для фильтрации списка компаний
        List<Company> result = companies.stream()
                // Фильтруем компании, проверяя, что дата основания не null и находится в заданном диапазоне
                .filter(c -> {
                    try {
                        return c.dateFoundation != null &&
                                isDateInRange(c.dateFoundation, startDate, endDate);
                    } catch (ParseException e) {
                        return false; // Если возникает ошибка парсинга, исключаем компанию из результатов
                    }
                })
                // Собираем отфильтрованные компании в новый список
                .collect(Collectors.toList());

        // Записываем в журнал информацию о выполненном запросе и количестве найденных компаний
        log("Запрос по диапазону дат основания: " + startDate + " до " + endDate + ", найдено: " + result.size());

        // Выводим названия всех найденных компаний в консоль
        result.forEach(c -> System.out.println("Найдена компания: " + c.name)); // Вывод на русском
    }

    // Метод для поиска компаний по диапазону количества сотрудников
    public void findByEmployeeCountRange(int min, int max) {
        // Используем стрим для фильтрации списка компаний
        List<Company> result = companies.stream()
                // Фильтруем компании, проверяя, что количество сотрудников находится в заданном диапазоне
                .filter(c -> {
                    try {
                        int count = c.countEmployees != null ? Integer.parseInt(c.countEmployees) : 0;
                        return count >= min && count <= max; // Проверяем, попадает ли количество в диапазон
                    } catch (NumberFormatException e) {
                        return false; // Если возникает ошибка при парсинге числа, исключаем компанию из результатов
                    }
                })
                // Собираем отфильтрованные компании в новый список
                .collect(Collectors.toList());

        // Записываем в журнал информацию о выполненном запросе и количестве найденных компаний
        log("Запрос по диапазону количества сотрудников: " + min + " до " + max + ", найдено: " + result.size());

        // Выводим названия всех найденных компаний в консоль
        result.forEach(c -> System.out.println("Найдена компания: " + c.name)); // Вывод на русском
    }

    // Метод для сохранения данных в формате XML
    public void saveToXML(String filename) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            Element rootElement = doc.createElement("Companies");
            doc.appendChild(rootElement);

            // Проходим по всем компаниям и создаем XML-элементы
            for (Company company : companies) {
                Element companyElement = doc.createElement("Company");
                rootElement.appendChild(companyElement);

                // Создаем элементы для каждого поля компании
                createElement(doc, companyElement, "Name", company.name);
                createElement(doc, companyElement, "ShortTitle", company.shortTitle);
                createElement(doc, companyElement, "DateUpdate", company.dateUpdate);
                createElement(doc, companyElement, "Address", company.address);
                createElement(doc, companyElement, "DateFoundation", company.dateFoundation);
                createElement(doc, companyElement, "CountEmployees", company.countEmployees);
                createElement(doc, companyElement, "Auditor", company.auditor);
                createElement(doc, companyElement, "Phone", company.phone);
                createElement(doc, companyElement, "Email", company.email);
                createElement(doc, companyElement, "Branch", company.branch);
                createElement(doc, companyElement, "Activity", company.activity);
                createElement(doc, companyElement, "InternetAddress", company.internetAddress);
            }

            // Преобразуем документ в XML-файл
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filename));
            transformer.transform(source, result);

            // Записываем в журнал информацию о сохранении
            log("Данные сохранены в формате XML: " + filename);
        } catch (Exception e) {
            // Записываем сообщение об ошибке в журнал
            log("Ошибка при сохранении в XML: " + e.getMessage());
        }
    }

    // Метод для создания элемента в XML-документе
    private void createElement(Document doc, Element parent, String name, String value) {
        if (value != null) {
            Element element = doc.createElement(name);
            element.appendChild(doc.createTextNode(value));
            parent.appendChild(element);
        }
    }

    // Метод для сохранения данных в формате JSON
    public void saveToJSON(String filename) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Сохраняем список компаний в JSON-файл
            objectMapper.writeValue(new File(filename), companies);
            log("Данные сохранены в формате JSON: " + filename); // Записываем в журнал информацию о сохранении
        } catch (IOException e) {
            // Записываем сообщение об ошибке в журнал
            log("Ошибка при сохранении в JSON: " + e.getMessage());
        }
    }

    // Метод для записи логов
    private void log(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            writer.write(timestamp + " - " + message);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл журнала: " + e.getMessage());
        }
    }

    // Метод для проверки, находится ли дата в заданном диапазоне
    private boolean isDateInRange(String dateToCheck, String startDate, String endDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(dateToCheck);
        Date start = sdf.parse(startDate);
        Date end = sdf.parse(endDate);
        return !(date.before(start) || date.after(end)); // Проверяем, находится ли дата в диапазоне
    }
}