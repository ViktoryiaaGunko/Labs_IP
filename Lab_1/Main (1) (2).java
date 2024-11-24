/* Гунько Виктория 
   Задача 2
   1/(1+х)^3
*/

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Введите значение x (-1 < x < 1): ");
            BigDecimal x = scanner.nextBigDecimal();

            System.out.print("Введите натуральное число k: ");
            int k = scanner.nextInt();

            if (x.compareTo(BigDecimal.valueOf(-1)) <= 0 || x.compareTo(BigDecimal.valueOf(1)) >= 0) {
                System.out.println("Ошибка: x должно быть в диапазоне (-1, 1).");
                return;
            }

            if (k <= 0) {
                System.out.println("Ошибка: k должно быть натуральным числом.");
                return;
            }

            BigDecimal a = BigDecimal.TEN.pow(-k);
            BigDecimal sum = BigDecimal.ZERO;
            BigDecimal term;
            int n = 0;

            do {
                // Вычисляем текущее слагаемое
                BigInteger nPlus2 = BigInteger.valueOf(n + 2);
                BigInteger nPlus1 = BigInteger.valueOf(n + 1);
                
                // Убираем умножение на (-1)^n
                BigDecimal numerator = new BigDecimal(nPlus2.multiply(nPlus1)).multiply(x.pow(n));
                BigDecimal denominator = BigDecimal.valueOf(2);

                // Проверка на деление на ноль
                if (denominator.compareTo(BigDecimal.ZERO) == 0) {
                    System.out.println("Ошибка: деление на ноль.");
                    return;
                }

                term = numerator.divide(denominator, MathContext.DECIMAL128);
                sum = sum.add(term);
                n++;
            } while (term.abs().compareTo(a) >= 0);

            // Вывод результата с тремя знаками после запятой
            System.out.printf("Результат: %.3f%n", sum.setScale(3, BigDecimal.ROUND_HALF_UP));

            // Исправлено: расчёт точного значения
            BigDecimal second = BigDecimal.ONE.divide((BigDecimal.ONE.add(x)).pow(3), MathContext.DECIMAL128);
            System.out.printf("Результат (точное значение): %.3f%n", second.setScale(3, BigDecimal.ROUND_HALF_UP));

        } catch (InputMismatchException e) {
            System.out.println("Ошибка: введены некорректные данные. Пожалуйста, введите числа.");
        } catch (ArithmeticException e) {
            System.out.println("Ошибка: возникла арифметическая ошибка при вычислении.");
        } catch (Exception e) {
            System.out.println("Произошла непредвиденная ошибка: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
