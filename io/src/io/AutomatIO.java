package io;

import java.io.*;

public class AutomatIO {
    public void saveAutomat(Object automat, String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(automat);
        }
    }

    public Object loadAutomat(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return in.readObject();
        }
    }
}
