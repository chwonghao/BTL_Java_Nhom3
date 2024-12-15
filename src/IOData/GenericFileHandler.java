/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IOData;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author haong
 * @param <T>
 */
public class GenericFileHandler<T extends Serializable> {
    private String fileName;

    public GenericFileHandler(String fileName) {
        this.fileName = fileName;
    }
    
    public void writeObjectToFile(T object) {
        List<T> existingObjects = readFromFile();

        // Check for duplicates
        if (contains(existingObjects, object)) {
            System.out.println("Lỗi: Đã tìm thấy mục trùng lặp. Bỏ qua ghi.");
            return;
        }

        // Add the new object to the existing list
        existingObjects.add(object);

        // Write updated list to file
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(existingObjects);
            // System.out.println("Đối tượng đã được ghi vào tệp thành công.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToFile(List<T> objects) {
        List<T> existingObjects = readFromFile();

        // Check for duplicates
        for (T obj : objects) {
            if (contains(existingObjects, obj)) {
                // System.out.println("Lỗi: Đã tìm thấy mục trùng lặp. Bỏ qua ghi.");
                return;
            }
        }

        // Append new objects to the existing list
        existingObjects.addAll(objects);

        // Write updated list to file
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(existingObjects);
            // System.out.println("Dữ liệu đã được ghi vào tệp thành công.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteObject(T object) {
        List<T> existingObjects = readFromFile();
        boolean removed = existingObjects.remove(object);

        if (removed) {
            // Write updated list to file
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
                oos.writeObject(existingObjects);
                // System.out.println("Đã xóa đối tượng thành công.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Lỗi: Không tìm thấy đối tượng.");
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<T> readFromFile() {
        List<T> objects = new ArrayList<>();
        File file = new File(fileName);

        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                objects = (List<T>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Tệp không tồn tại.");
        }

        return objects;
    }

    private boolean contains(List<T> list, T obj) {
        return list.contains(obj);
    }
}
       
