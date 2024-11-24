/* Гунько Виктория 
   Задача 2
   1/(1+х)^3
*/

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Scanner;

public class Main {

    // Функция для вычисления суммы ряда с использованием стандартных типов
    public static double calculateStandard(double x, double epsilon) {
        double sum = 0.0;
        double term = 1.0; // Первый член ряда (x^0 / 0!)
        int n = 0;

        while (Math.abs(term) >= epsilon) {
            sum += term;
            n++;
            term *= x / n; // Вычисляем следующий член ряда
        }

        return sum;
    }

    // Функция для вычисления суммы ряда с использованием BigDecimal
    public static BigDecimal calculateBigDecimal(double x, double epsilon) {
        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal term = BigDecimal.ONE; // Первый член ряда (x^0 / 0!)
        int n = 0;
        BigDecimal xBD = BigDecimal.valueOf(x);
        BigDecimal epsilonBD = BigDecimal.valueOf(epsilon);

        while (term.abs().compareTo(epsilonBD) >= 0) {
            sum = sum.add(term);
            n++;
            term = term.multiply(xBD).divide(BigDecimal.valueOf(n), MathContext.DECIMAL128); // Вычисляем следующий член ряда
        }

        return sum;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ввод значений x и epsilon
        System.out.print("Введите значение x (в пределах (-1 < x < 1)): ");
        double x = scanner.nextDouble();

        System.out.print("Введите значение k (натуральное число): ");
        int k = scanner.nextInt();
        double epsilon = Math.pow(10, -(k));
        // Вычисление и вывод результатов
        double resultStandard = calculateStandard(x, epsilon);
        BigDecimal resultBigDecimal = calculateBigDecimal(x, epsilon);

        System.out.printf("Результат с использованием стандартных типов: %.6f%n", resultStandard);
        System.out.printf("Результат с использованием BigDecimal: %.6f%n", resultBigDecimal.setScale(6, BigDecimal.ROUND_HALF_UP));

        // Сравнение результатов
        if (Math.abs(resultStandard - resultBigDecimal.doubleValue()) < epsilon) {
            System.out.println("Результаты совпадают.");
        } else {
            System.out.println("Результаты не совпадают.");
        }

        scanner.close();
    }
}
