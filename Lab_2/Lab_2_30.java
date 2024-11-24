// Гунько Виктория 
// задача 30

import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ввод размерности матрицы
        System.out.print("Введите количество строк матрицы: ");
        int rows = scanner.nextInt();
        System.out.print("Введите количество столбцов матрицы: ");
        int cols = scanner.nextInt();

        // Ввод элементов матрицы
        Vector<Vector<Integer>> matrix = new Vector<>(rows);
        System.out.println("Введите элементы матрицы: ");
        for (int i = 0; i < rows; i++) {
            Vector<Integer> row = new Vector<>(cols);
            for (int j = 0; j < cols; j++) {
                row.add(scanner.nextInt());
            }
            matrix.add(row);
        }

        // Вывод исходной матрицы
        System.out.println("Исходная матрица:");
        printMatrix(matrix);

        // Упорядочивание столбцов по наименьшим элементам
        Integer[] minElements = new Integer[cols];
        for (int j = 0; j < cols; j++) {
            minElements[j] = findMinInColumn(matrix, j);
        }

        // Упорядочиваем индексы столбцов по наименьшим элементам
        Integer[] indices = new Integer[cols];
        for (int j = 0; j < cols; j++) {
            indices[j] = j;
        }

        Arrays.sort(indices, (a, b) -> minElements[a].compareTo(minElements[b]));

        // Создаем новую отсортированную матрицу
        Vector<Vector<Integer>> sortedMatrix = new Vector<>(rows);
        for (int i = 0; i < rows; i++) {
            Vector<Integer> newRow = new Vector<>(cols);
            for (int index : indices) {
                newRow.add(matrix.get(i).get(index));
            }
            sortedMatrix.add(newRow);
        }

        // Вывод отсортированной матрицы
        System.out.println("Матрица после сортировки столбцов:");
        printMatrix(sortedMatrix);

        scanner.close();
    }

    // Метод для нахождения минимального элемента в столбце
    private static int findMinInColumn(Vector<Vector<Integer>> matrix, int col) {
        int min = Integer.MAX_VALUE;
        for (Vector<Integer> row : matrix) {
            min = Math.min(min, row.get(col));
        }
        return min;
    }

    // Метод для вывода матрицы
    private static void printMatrix(Vector<Vector<Integer>> matrix) {
        for (Vector<Integer> row : matrix) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}
