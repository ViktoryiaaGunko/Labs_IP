// Гунько Виктория 5 группа
// задача 16

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ввод размерности матрицы
        System.out.print("Введите количество строк матрицы: ");
        int rows = scanner.nextInt();
        System.out.print("Введите количество столбцов матрицы: ");
        int cols = scanner.nextInt();

        // Проверка на квадратность матрицы
        if (rows != cols) {
            System.out.println("Ошибка: матрица должна быть квадратной.");
            return;
        }

        // Ввод элементов матрицы
        int[][] matrix = new int[rows][cols];
        System.out.println("Введите элементы матрицы: ");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }

        // Вывод матрицы
        System.out.println("Введенная матрица:");
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }

        // Инициализация переменной для хранения минимума сумм модулей
        int minSum = Integer.MAX_VALUE;

        // Вычисление сумм модулей элементов диагоналей, параллельных побочной диагонали
        for (int d = 1 - cols; d < rows; d++) {
            // Сумма модулей для текущей диагонали
            int sum = 0; 
            for (int i = 0; i < rows; i++) {
                // Вычисляем j для данной диагонали
                int j = cols - 1 - (i + d); 
                if (j >= 0 && j < cols) {
                    sum += Math.abs(matrix[i][j]);
                }
            }
            // Обновляем минимум
            minSum = Math.min(minSum, sum); 
        }

        // Вывод результата
        System.out.println("Минимум среди сумм модулей элементов диагоналей: " + minSum);

        scanner.close();
    }
}