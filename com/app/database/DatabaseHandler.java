package com.app.database;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler<T> {

    private File file;

    public DatabaseHandler(String input) throws IOException {
        File file = new File(input);
        file.createNewFile();
        this.file = file;
    }

    public void write(List<T> input) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
             ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream)) {
            outputStream.writeObject(input);
        } catch (IOException e) {
        }
    }

    public List<T> read() throws IOException, ClassNotFoundException {
        try (FileInputStream fileInputStream = new FileInputStream(file);
             ObjectInputStream inputStream = new ObjectInputStream(fileInputStream)) {
            return (List<T>) inputStream.readObject();
        } catch (IOException e) {

        }
        return new ArrayList<>();
    }
}
