import java.util.Random;
import java.util.Scanner;

public class lesson4 {

    private static final char DOT_EMPTY = '.';
    private static final char DOT_HUMAN = 'X';
    private static final char DOT_COMP = 'O';
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Random RANDOM = new Random();
    private static char[][] field;
    private static int fieldSizeY;
    private static int fieldSizeX;

    public static void initialize() {
        fieldSizeY = 3;
        fieldSizeX = 3;

        field = new char[fieldSizeY][fieldSizeX];
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                field[y][x] = DOT_EMPTY;
            }
        }
    }

    // Отрисовка игрового поля
    public static void printField() {
        System.out.print("+");
        for (int i = 0; i < fieldSizeY * 2 + 1; i++) {
            if (i % 2 == 0) {
                System.out.print("-");
            } else {
                System.out.print(i / 2 + 1);
            }
        }
        System.out.println();

        for (int i = 0; i < fieldSizeY; i++) {
            System.out.print(i + 1 + "|");
            for (int j = 0; j < fieldSizeY; j++) {
                System.out.print(field[i][j] + "|");
            }
            System.out.println();
        }
    }

    public static void humanTurn() {
        int x;
        int y;
        do {
            System.out.println("Введите координаты поля через пробел от 1 до " + fieldSizeX);
            x = SCANNER.nextInt() - 1;
            y = SCANNER.nextInt() - 1;
        } while (!isCellValid(x, y) || !isCellEmpty(x, y));
        field[y][x] = DOT_HUMAN;
    }

    public static boolean isCellEmpty(int x, int y) {
        return field[y][x] == DOT_EMPTY;
    }

    public static boolean isCellValid(int x, int y) {
        return x >= 0 && x <= fieldSizeX && y >= 0 && y <= fieldSizeY;
    }

    public static void compTurn() {
        int x, y;
        do {
            x = RANDOM.nextInt(fieldSizeX);
            y = RANDOM.nextInt(fieldSizeY);
        } while (!isCellEmpty(x, y));
        field[y][x] = DOT_COMP;
    }

    public static boolean checkDraw() {
        for (int y = 0; y < fieldSizeX; y++) {
            for (int x = 0; x < fieldSizeY; x++) {
                if (isCellEmpty(x, y))
                    return false;
            }
        }
        return true;
    }

    public static boolean checkWin(char c) {
        // Проверка по трем горизонталям
        if (field[0][0] == c && field[0][1] == c && field[0][2] == c) return true;
        if (field[1][0] == c && field[1][1] == c && field[1][2] == c) return true;
        if (field[2][0] == c && field[2][1] == c && field[2][2] == c) return true;

        // Проверка по трем вертикалям
        if (field[0][0] == c && field[1][0] == c && field[2][0] == c) return true;
        if (field[0][1] == c && field[1][1] == c && field[2][1] == c) return true;
        if (field[0][2] == c && field[1][2] == c && field[2][2] == c) return true;

        //Проверка по диагонали
        if (field[0][0] == c && field[1][1] == c && field[2][2] == c) return true;
        if (field[0][2] == c && field[1][1] == c && field[2][0] == c) return true;

        return false;
    }

    public static boolean gameCheck(char dot, String s) {
        if (checkWin(dot)) {
            System.out.println(s);
            return true;
        } if(checkDraw()) {
            System.out.println("Ничья");
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        boolean f = true;

        while (true) {
            initialize();
            printField();
            while (f) {
                humanTurn();
                printField();
                if (gameCheck(DOT_HUMAN, "Вы победили!"))
                    break;
                compTurn();
                printField();
                if (gameCheck(DOT_COMP, "Компьютер победил!"))
                    break;
            }
            System.out.println("Если вы хотите сыграть еще раз введите Y");
            if (!SCANNER.next().equalsIgnoreCase("y"))
                break;
        }

    }
}
