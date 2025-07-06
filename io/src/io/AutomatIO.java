package io;

public class AutomatIO {
    public void saveAutomatState(Automat automat) {
        // Implement serialization logic to save the automat state to a file
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("automat.ser"))) {
            oos.writeObject(automat);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Automat loadAutomatState() {
        // Implement deserialization logic to load the automat state from a file
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("automat.ser"))) {
            return (Automat) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}