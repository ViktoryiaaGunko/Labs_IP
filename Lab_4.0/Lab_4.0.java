// Гунько Виктория 

import java.util.ArrayList; 
import java.util.Comparator; 
import java.util.Iterator;   
import java.util.List;      
import java.util.Scanner;   
import java.io.File;        
import java.io.FileNotFoundException; 

// Основной класс Contact
class Contact implements Comparable<Contact>, Iterable<String> {
    // Поля
    private String name;          
    private String mobilePhone;   
    private String workPhone;     
    private String homePhone;     
    private String email;         

    // Конструктор
    public Contact(String name, String mobilePhone) {
        this.name = name;                
        this.mobilePhone = mobilePhone; 
    }

    // Конструктор, инициализирующий объект из строки
    public Contact(String contactString) {
        String[] parts = contactString.split(","); // Разделяем строку по запятой
        if (parts.length >= 2) { // Минимум два поля: имя и мобильный телефон
            this.name = parts[0].trim(); 
            this.mobilePhone = parts[1].trim(); 
            this.workPhone = parts.length > 2 ? parts[2].trim() : null; 
            this.homePhone = parts.length > 3 ? parts[3].trim() : null; 
            this.email = parts.length > 4 ? parts[4].trim() : null; 
        }
    }

    // Методы доступа к полям (геттеры)
    public String getName() { return name; }                 
    public String getMobilePhone() { return mobilePhone; }   
    public String getWorkPhone() { return workPhone; }      
    public String getHomePhone() { return homePhone; }       
    public String getEmail() { return email; }           

    // Метод для отображения объекта в виде строки
    public String toString() {
        return "Contact{name='" + name + "', mobilePhone='" + mobilePhone + "', workPhone='" + workPhone +
                "', homePhone='" + homePhone + "', email='" + email + "'}"; // Возвращаем строку с информацией о контакте
    }

    // Реализация интерфейса Comparable (по умолчанию сортировка по имени)
    public int compareTo(Contact other) {
        // Сравниваем контакты по имени
        return this.name.compareTo(other.name);
    }

    // Реализация интерфейса Iterable (позволяет перебор полей)
    public Iterator<String> iterator() {
        // Создаем список полей
        List<String> fields = List.of(name, mobilePhone, workPhone, homePhone, email); 
        return fields.iterator(); // Возвращаем итератор для перебора полей
    }

    // Статические компараторы для сортировки по разным полям
    public static Comparator<Contact> compareByName() {
        // Сравнение по имени
        return Comparator.comparing(Contact::getName); 
    }

    public static Comparator<Contact> compareByMobilePhone() {
        // Сравнение по мобильному телефону
        return Comparator.comparing(Contact::getMobilePhone); // Сравнение по мобильному телефону
    }

    public static Comparator<Contact> compareByEmail() {
        // Сравнение по электронной почте
        return Comparator.comparing(Contact::getEmail, Comparator.nullsLast(String::compareTo)); 
    }
}

// Консольное приложение для тестирования
public class Main {
    public static void main(String[] args) {
        List<Contact> contacts = new ArrayList<>(); // Создаем список для хранения контактов

        // Чтение данных из файла
        // Открываем файл для чтения
        try (Scanner fileScanner = new Scanner(new File("contacts.txt"))) { 
            // Пока есть строки в файле
            while (fileScanner.hasNextLine()) { 
                // Читаем строку
                String line = fileScanner.nextLine(); 
                // Создаем контакт из строки и добавляем в список
                contacts.add(new Contact(line)); 
            }
        } catch (FileNotFoundException e) { //если файл не найден
            System.out.println("Файл не найден: " + e.getMessage()); 
            return; // Завершаем выполнение программы
        }

        // Запрос выбора поля для сортировки
        Scanner scanner = new Scanner(System.in); 
        // Запрашиваем выбор
        System.out.println("Выберите поле для сортировки (name, mobilePhone, email): "); 
        // Считываем выбор 
        String sortField = scanner.nextLine(); 

        // Сортировка списка в зависимости от выбора пользователя
        switch (sortField) {
            case "name":
                contacts.sort(Contact.compareByName()); // Сортируем по имени
                break;
            case "mobilePhone":
                contacts.sort(Contact.compareByMobilePhone()); // Сортируем по мобильному телефону
                break;
            case "email":
                contacts.sort(Contact.compareByEmail()); // Сортируем по электронной почте
                break;
            default:
                System.out.println("Неверный выбор. Сортировка по имени по умолчанию."); // Сообщаем о неверном выборе
                contacts.sort(Contact.compareByName()); // Сортируем по имени по умолчанию
                break;
        }

        // Вывод отсортированного списка
        System.out.println("\nСписок контактов:"); // Заголовок для списка
        contacts.forEach(System.out::println); // Выводим каждый контакт в списке
    }
}