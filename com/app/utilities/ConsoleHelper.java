package com.app.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class ConsoleHelper {

    public static final int INTEGER = 0;
    public static final int FLOAT = 1;
    public static final int DOUBLE = 2;
    public static final int STRING = 3;

    private BufferedReader bufferedReader;
    private PrintWriter printWriter;

    public ConsoleHelper() {
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        printWriter = new PrintWriter(System.out);
    }

    public Object read(String prompt, int type) {
        if (prompt != null)
            print(prompt);
        String input = null;
        try {
            input = bufferedReader.readLine();

            try {
                if (type == INTEGER)
                    return Integer.parseInt(input);
                else if (type == FLOAT)
                    return Float.parseFloat(input);
                else if (type == DOUBLE)
                    return Double.parseDouble(input);
            } catch (NumberFormatException exception) {
                println(ConsoleConstants.ILLEGAL_INPUT);
                return read(prompt, type);
            }
        } catch (IOException e) {
            return read(prompt, type);
        }
        return input;
    }

    public int readInRange(String prompt, int from, int to) {
        if (prompt != null)
            println(prompt);
        String input = null;
        int output = -1;
        try {
            input = bufferedReader.readLine();

            try {
                output = Integer.parseInt(input);

                if (output < from || output > to)
                    return readInRange(prompt, from, to);
            } catch (NumberFormatException exception) {
                println(ConsoleConstants.ILLEGAL_INPUT);
                return readInRange(prompt, from, to);
            }
        } catch (IOException e) {
            return readInRange(prompt, from, to);
        }
        return output;
    }

    public void println(Object message) {
        printWriter.println(message);
        printWriter.flush();
    }

    public void print(Object message) {
        printWriter.format("%s", message);
        printWriter.flush();
    }
}
