import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
    public static final String USER_SIGN = "X";
    public static final String USER_SIGN_SECOND = "O";
    public static final String AI_SIGN = "O";
    public static final String NOT_SIGH = "*";
    public static int aiLevel = 0;
    public static final int DIMENSION = 3;
    public static String[][] field = new String[DIMENSION][DIMENSION];


    public static void main(String[] args) {
        mainMenu();

    }

    public static void modeTwoPlayers() {
        int count = 0;
        initFied();
        while (true) {
            printField();
            userShot(USER_SIGN, 1);
            count++;
            printField();
            if (checkWin(USER_SIGN)) {
                System.out.println("USER 1 WIN!!");
                printField();
                break;
            }
            userShot(USER_SIGN_SECOND, 2);
            count++;
            if (checkWin(USER_SIGN_SECOND)) {
                System.out.println("USER 2 WIN!!");
                printField();
                break;
            }
            if (count == Math.pow(DIMENSION, 2)) {
                printField();
                break;
            }
        }
    }

    public static void modeAgainstAl() {
        int count = 0;
        initFied();
        while (true) {
            printField();
            userShot(USER_SIGN, 0);
            count++;
            if (checkWin(USER_SIGN)) {
                System.out.println("USER WIN!!");
                printField();
                break;
            }
            aiShot();
            count++;
            if (checkWin(AI_SIGN)) {
                System.out.println("AI WIN!!");
                printField();
                break;
            }
            if (count == Math.pow(DIMENSION, 2)) {
                printField();
                break;
            }
        }

    }

    public static void initFied() {

        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                field[i][j] = NOT_SIGH;
            }
        }


    }

    public static void printField() {
        for (int i = 0; i < DIMENSION; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < DIMENSION; i++) {
            System.out.print((i + 1) + " ");

            for (int j = 0; j < DIMENSION; j++) {
                System.out.print(field[i][j] + " ");
            }
            System.out.println();
        }
    }


    public static void aiLevel() {
        System.out.println("Выберите сложность компьютер");
        System.out.println("1 Простой");
        System.out.println("2 Продвинутый");
        System.out.println("3 Сложный");
        System.out.println("4 Вернуться в предыдущие меню");
        System.out.println("5 Выход");
        int i = 0;

        Scanner sc = new Scanner(System.in);
        i = sc.nextInt();

        switch (i) {
            case 1: {
                aiLevel = 0;
                modeAgainstAl();
                break;
            }
            case 2: {
                aiLevel = 1;
                modeAgainstAl();
                break;
            }
            case 3: {
                aiLevel = 2;
                modeAgainstAl();
                break;
            }
            case 4: {
                mainMenu();
                break;
            }
            case 5: {
                System.exit(0);
                break;
            }
            default: {
                System.out.println("Было введенно не правильное значение!");
            }
        }
    }



    public static void mainMenu() {
        System.out.print("Выбирете режим игры");
        System.out.print("1. Игра против компьютера");
        System.out.print("2. 2 игрока");
        System.out.print("3 Выход");
        int i = 0;
        try {
            Scanner sc = new Scanner(System.in);
            i = sc.nextInt();
        } catch (ArrayIndexOutOfBoundsException ex) {
        }
        switch (i) {
            case 1: {
                aiLevel();
                break;
            }
            case 2: {
                modeTwoPlayers();
                break;
            }
            case 3: {
                System.exit(0);
                break;
            }

            default: {
                System.out.println("Было введено неверное значение!");
            }
        }
    }

    public static void userShot(String sigh, int i) {
        int x = -1;
        int y = -1;
        do {
            if (i == 0) {
                System.out.println("Введите кординаты x y(1 -" + DIMENSION + "):");
            } else {
                System.out.println("Игрок" + i + ".Введите координаті х у(1-" + DIMENSION + ");");
            }
            try {
                Scanner sc = new Scanner(System.in);
                x = Integer.parseInt(sc.next()) - 1;
                y = Integer.parseInt(sc.next()) - 1;
            } catch (ArrayIndexOutOfBoundsException ex) {
                System.out.println("Вводим только числа");
            }
        }
        while (isCellBusy(x, y));

        field[x][y] = sigh;
    }

    public static void aiShot() {
        int x = -1;
        int y = -1;
        boolean al_win = false;
        boolean user_win = false;
        // находим выиграшный ход
        if (aiLevel == 2) {
            for (int i = 0; i < DIMENSION; i++) {
                for (int j = 0; j < DIMENSION; j++) {
                    if (!isCellBusy(i, j)) {
                        field[i][j] = AI_SIGN;
                        if (checkWin(AI_SIGN)) {
                            x = i;
                            y = j;
                            al_win = true;
                        }
                        field[i][j] = NOT_SIGH;
                    }

                }

            }
        }
        //блокировка хода пользователя,если компьюет видет что пользователь побеждает на следующем ходу
        if (aiLevel > 0) {
            if (!al_win) {
                for (int i = 0; i < DIMENSION; i++) {
                    for (int j = 0; j < DIMENSION; j++) {
                        if (!isCellBusy(i, j)) {
                            field[i][j] = USER_SIGN;
                            if (checkWin(USER_SIGN)) {
                                x = i;
                                y = j;
                                user_win = true;
                            }
                            field[i][j] = NOT_SIGH;
                        }

                    }

                }
            }
        }
            if (!al_win && !user_win) {

                do {
                    try {
                        Random rnd = new Random();
                        x = rnd.nextInt(DIMENSION);
                        y = rnd.nextInt(DIMENSION);
                    }catch (ArrayIndexOutOfBoundsException ex){

                    }
                }
                while (isCellBusy(x, y));
            }
            field[x][y] = AI_SIGN;
            System.out.println("x = " + x + "| y = " + y + "| ai_win = " + al_win + "| user_win = " + user_win);
        }





    public static boolean isCellBusy(int x, int y) {
        if (x < 0 || y < 0 || x > DIMENSION - 1 || y > DIMENSION - 1) {
            return false;
        }
        return field[x][y] != NOT_SIGH;
    }

    public static boolean chekLine(int start_x, int start_y, int dx, int dy, String sign) {
        for (int i = 0; i < DIMENSION; i++) {
            if (field[start_x + i * dx][start_y + i * dy] != sign)
                return false;

        }
        return true;

    }

    public static boolean checkWin(String sign) {

        for (int i = 0; i < DIMENSION; i++) {
            //проверяем строки
            if (chekLine(i, 0, 0, 1, sign))
                return true;
            if (chekLine(0, i, 1, 0, sign))
                return true;
        }
        // проверяем диагонали
        if (chekLine(0, 0, 1, 1, sign))
            return true;
        if (chekLine(0, DIMENSION-1, 1,-1, sign))
            return true;
        return false;
    }


    public static boolean checkWin2(String sign) {
        {
            //проверка по строкам
            for (int i = 0; i < DIMENSION; i++) {
                if (field[i][0] == sign && field[i][1] == sign && field[i][2] == sign) {
                    return true;
                }
            }
            //проверка по столбцам
            for (int j = 0; j < DIMENSION; j++) {
                if (field[0][j] == sign && field[1][j] == sign && field[2][j] == sign) {
                    return true;
                }
                //проверка по диагонали

                if (field[0][0] == sign && field[1][1] == sign && field[2][2] == sign) {
                    return true;

                }
                if (field[0][2] == sign && field[1][1] == sign && field[2][0] == sign)
                    return true;
            }
            return false;

        }
    }
}
