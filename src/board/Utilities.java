package board;

public class Utilities {
    private static boolean[] FirstColumn = ColumnMaker(0);
    private static boolean[] SecondColumn = ColumnMaker(1);
    private static boolean[] SeventhColumn = ColumnMaker(6);
    private static boolean[] EigthColumn = ColumnMaker(7);
    private static boolean[] SecondRow = RowMaker(1);
    private static boolean[] SeventhRow = RowMaker(6);

    private static boolean[] RowMaker(int row) {
        boolean[] array = new boolean[64];
        for (int i = 0; i < 8; i++) {
            array[(row*8) + i] = true;
        }
        return array;

    }

    public static boolean[] ColumnMaker(int column) {
        boolean array[] = new boolean[64];
        for (int i = 0; i < 8; i++) {
            array[column+(8*i)] = true;
        }
        return array;
    }
    public static boolean isFirstColumn(int coord) {
        return FirstColumn[coord];
    }

    public static boolean isSecondColumn(int coord) {
        return  SecondColumn[coord];
    }
    public static boolean isSeventhColumn(int coord) {
        return  SeventhColumn[coord];
    }
    public static boolean isEigthColumn(int coord) {
        return  EigthColumn[coord];
    }

    public static boolean isSecondRow(int coord) { return SecondRow[coord]; }
    public static boolean isSeventhRow(int coord) { return SeventhRow[coord]; }
}
