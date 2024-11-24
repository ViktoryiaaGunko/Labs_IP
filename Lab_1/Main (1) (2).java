/* Гунько Виктория 
   Задача 2
   1/(1+х)^3
*/

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите значение x (-1 < x < 1): ");
        double x = scanner.nextDouble();
        
        System.out.print("Введите натуральное число k: ");
        int k = scanner.nextInt();

        if (x <= -1 || x >= 1) {
            System.out.println("Ошибка: x должно быть в диапазоне (-1, 1).");
            return;
        }
        
        if (k <= 0) {
            System.out.println("Ошибка: k должно быть натуральным числом.");
            return;
        }

        double a = Math.pow(10, -k);
        double sum = 0.0;
        double term;
        int n = 0;

        do {
            // Вычисляем текущее слагаемое
            term = ((n + 2) * (n + 1)) / 2.0 * Math.pow(-1, n) * Math.pow(x, n);
            sum += term;
            n++;
        } while (Math.abs(term) >= a);

        // Вывод результата с тремя знаками после запятой
        System.out.printf("Первое число: %.3f%n", sum);
        
        double second = 1 / Math.pow(1 + x, 3);
        System.out.printf("Второе число: %.3f%n", second);
    }
}
