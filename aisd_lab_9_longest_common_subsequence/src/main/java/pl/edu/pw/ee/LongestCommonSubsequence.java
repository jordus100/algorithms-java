package pl.edu.pw.ee;

import java.util.ArrayList;
import java.util.List;

class LongestCommonSubsequence {

    private final String firstStr, secondStr;
    private MatrixElem[][] lcsMatrix = null;
    private final List<Integer> lcsPathX = new ArrayList<>();
    private final List<Integer> lcsPathY = new ArrayList<>();
    private final int WIDTH_PER_CHAR = 1;
    private final int MARGIN_FOR_CORNERS = 2;
    private final int CELL_WIDTH_WITHOUT_NUMBER_AND_CORNERS = 4;
    private final int CELL_HEIGHT_WITHOUT_CORNERS = 3;
    private final int CELL_HEIGHT = CELL_HEIGHT_WITHOUT_CORNERS + MARGIN_FOR_CORNERS;
    private int CELL_WIDTH = CELL_WIDTH_WITHOUT_NUMBER_AND_CORNERS + WIDTH_PER_CHAR + MARGIN_FOR_CORNERS;
    private final String COLOR_RESET = "\033[0m";
    private final String COLOR_GREEN = "\033[0;32m";
    private final char CORNER = '+';
    private final char VERTICAL_LINE = '|';
    private final char HORIZONTAL_LINE = '_';
    private final char ARROW_UP = '^';
    private final char ARROW_LEFT = '<';
    private final char ARROW_DIAGONAL = '\\';
    private final char[] specialChars = new char[]{'\r', '\n', '\t', '\b', '\f'};
    private final String[] specialCharsReplacements = new String[]{"\\r", "\\n", "\\t", "\\b", "\\f"};

    private class MatrixElem {
        private short number;
        private Direction direction = null;
    }

    public LongestCommonSubsequence(String firstStr, String secondStr) {
        if (firstStr == null || secondStr == null) {
            throw new IllegalArgumentException("Input strings cannot be null");
        }
        this.firstStr = firstStr;
        this.secondStr = secondStr;
    }

    public String findLCS() {
        lcsMatrix = new MatrixElem[firstStr.length() + 1][secondStr.length() + 1];
        initMatrix();
        calcDirectionsInLCSMatrix();
        return getLCSFromMatrix();
    }

    private void initMatrix() {
        for (int i = 0; i < lcsMatrix.length; i++) {
            for (int n = 0; n < lcsMatrix[0].length; n++) {
                lcsMatrix[i][n] = new MatrixElem();
            }
        }
        for (int i = 0; i < lcsMatrix.length; i++) {
            lcsMatrix[i][0].number = 0;
        }
        for (int i = 0; i < lcsMatrix[0].length; i++) {
            lcsMatrix[0][i].number = 0;
        }
    }

    private void calcDirectionsInLCSMatrix() {
        for (int i = 1; i < lcsMatrix.length; i++) {
            for (int n = 1; n < lcsMatrix[0].length; n++) {
                if (firstStr.charAt(i - 1) == secondStr.charAt(n - 1)) {
                    lcsMatrix[i][n].number = (short) (lcsMatrix[i - 1][n - 1].number + 1);
                    lcsMatrix[i][n].direction = Direction.Diagonal;
                } else {
                    if (lcsMatrix[i][n - 1].number >= lcsMatrix[i - 1][n].number) {
                        lcsMatrix[i][n].number = lcsMatrix[i][n - 1].number;
                        lcsMatrix[i][n].direction = Direction.Up;
                    } else {
                        lcsMatrix[i][n].number = lcsMatrix[i - 1][n].number;
                        lcsMatrix[i][n].direction = Direction.Left;
                    }
                }
            }
        }
    }

    private String getLCSFromMatrix() {
        int currColInd = lcsMatrix.length - 1;
        int currRowInd = lcsMatrix[0].length - 1;
        String subSeq = "";
        while ((currColInd != 0) && (currRowInd != 0)) {
            switch (lcsMatrix[currColInd][currRowInd].direction) {
                case Left:
                    currColInd--;
                    addToLcsPath(currColInd, currRowInd);
                    break;
                case Up:
                    currRowInd--;
                    addToLcsPath(currColInd, currRowInd);
                    break;
                case Diagonal:
                    if (lcsPathX.isEmpty()) {
                        addToLcsPath(currColInd, currRowInd);
                    }
                    subSeq = subSeq.concat(Character.toString(firstStr.charAt(currColInd - 1)));
                    currRowInd--;
                    currColInd--;
                    addToLcsPath(currColInd, currRowInd);
                    break;
            }
        }
        return new StringBuilder(subSeq).reverse().toString();
    }

    private void addToLcsPath(int currColInd, int currRowInd) {
        lcsPathX.add(currColInd);
        lcsPathY.add(currRowInd);
    }

    public void display() {
        if (lcsMatrix == null) {
            findLCS();
        }
        calculateCellSize();
        char[][] consoleOutput = new char[(CELL_WIDTH - 1) * (lcsMatrix.length + 1) + 1][(CELL_HEIGHT - 1) * (lcsMatrix[0].length + 1) + 1];
        generateCellsTo2dCharArray(consoleOutput);
        print2dCharToConsole(consoleOutput);
    }

    private void calculateCellSize() {
        int biggestNum = lcsMatrix[lcsMatrix.length - 1][lcsMatrix[0].length - 1].number;
        if (String.valueOf(biggestNum).length() > 1) {
            CELL_WIDTH = (CELL_WIDTH_WITHOUT_NUMBER_AND_CORNERS + WIDTH_PER_CHAR * String.valueOf(biggestNum).length()) + MARGIN_FOR_CORNERS;
        }
    }

    private void generateCellsTo2dCharArray(char[][] consoleOutput) {
        for (int i = 0; i < lcsMatrix.length - 1; i++) {
            generateCell(String.valueOf(firstStr.charAt(i)), null, i + 2, 0, consoleOutput);
        }
        for (int i = 0; i < lcsMatrix[0].length - 1; i++) {
            generateCell(String.valueOf(secondStr.charAt(i)), null, 0, i + 2, consoleOutput);
        }
        generateCell("", null, 0, 0, consoleOutput);
        generateCell("", null, 0, 1, consoleOutput);
        generateCell("", null, 1, 0, consoleOutput);
        for (int i = 0; i < lcsMatrix.length; i++) {
            for (int n = 0; n < lcsMatrix[0].length; n++) {
                generateCell(String.valueOf(lcsMatrix[i][n].number), null, i + 1, n + 1, consoleOutput);
            }
        }
        for (int i = 0; i < lcsPathX.size(); i++) {
            generateCell("", lcsMatrix[lcsPathX.get(i)][lcsPathY.get(i)].direction,
                    lcsPathX.get(i) + 1, lcsPathY.get(i) + 1, consoleOutput);
        }
    }

    private void print2dCharToConsole(char[][] consoleOutput) {
        char currChar;
        for (int i = 0; i < consoleOutput[0].length; i++) {
            for (int n = 0; n < consoleOutput.length; n++) {
                currChar = consoleOutput[n][i];
                if (currChar != '\u0000') {
                    if (i != 0 && n != 0 && Character.isDigit(currChar)) {
                        System.out.print(COLOR_GREEN);
                        System.out.print(currChar);
                        System.out.print(COLOR_RESET);
                    } else {
                        System.out.print(currChar);
                    }
                } else {
                    System.out.print(' ');
                }
                if (n + 1 == consoleOutput.length) {
                    System.out.print('\n');
                }
            }
        }
    }

    private void generateCell(String caption, Direction directionSign, int colInd, int rowInd, char[][] output) {
        int topYCoord = rowInd == 0 ? 0 : rowInd * (CELL_HEIGHT - 1);
        int leftXCoord = colInd == 0 ? 0 : colInd * (CELL_WIDTH - 1);
        int bottomYCoord = topYCoord + CELL_HEIGHT - 1;
        int rightXCoord = leftXCoord + CELL_WIDTH - 1;
        output[leftXCoord][topYCoord] = CORNER;
        output[rightXCoord][topYCoord] = CORNER;
        output[leftXCoord][bottomYCoord] = CORNER;
        output[rightXCoord][bottomYCoord] = CORNER;
        generateCellBorders(output, topYCoord, leftXCoord, bottomYCoord, rightXCoord);
        caption = escapeSpecialChars(caption);
        int startCapXCoord = leftXCoord + Math.floorDiv(CELL_WIDTH - MARGIN_FOR_CORNERS - caption.length(), 2) + 1;
        int capYCoord = (topYCoord + CELL_HEIGHT / 2);
        if (!(caption.equals(""))) {
            for (int i = startCapXCoord; i < startCapXCoord + caption.length(); i++) {
                output[i][capYCoord] = caption.charAt(i - startCapXCoord);
            }
        }
        generateDirectionSign(caption, directionSign, output, topYCoord, leftXCoord, startCapXCoord, capYCoord);
    }

    private void generateCellBorders(char[][] output, int topYCoord, int leftXCoord, int bottomYCoord, int rightXCoord) {
        for (int n : new int[]{topYCoord, bottomYCoord}) {
            for (int i = leftXCoord + 1; i < rightXCoord; i++) {
                output[i][n] = HORIZONTAL_LINE;
            }
        }
        for (int n : new int[]{leftXCoord, rightXCoord}) {
            for (int i = topYCoord + 1; i < bottomYCoord; i++) {
                output[n][i] = VERTICAL_LINE;
            }
        }
    }

    private String escapeSpecialChars(String caption) {
        if (!caption.equals("")) {
            char capChar = caption.charAt(0);
            for (int j = 0; j < specialChars.length; j++) {
                if (capChar == specialChars[j]) {
                    caption = specialCharsReplacements[j];
                }
            }
        }
        return caption;
    }

    private void generateDirectionSign(String caption, Direction directionSign, char[][] output, int topYCoord, int leftXCoord, int startCapXCoord, int capYCoord) {
        if (directionSign != null) {
            switch (directionSign) {
                case Up:
                    int xCoord = startCapXCoord + Math.floorDiv(caption.length(), 2);
                    output[xCoord][topYCoord + 1] = ARROW_UP;
                    break;
                case Left:
                    output[leftXCoord + 1][capYCoord] = ARROW_LEFT;
                    break;
                case Diagonal:
                    output[leftXCoord + 1][topYCoord + 1] = ARROW_DIAGONAL;
            }
        }
    }

}
