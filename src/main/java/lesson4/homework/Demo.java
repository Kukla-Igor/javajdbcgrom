package lesson4.homework;

import java.util.ArrayList;

public class Demo {
    public static void main(String[] args) throws Exception {

        String[] arr1 = {"txt", "avi"};
        String[] arr2 = {"txt"};

        Storage storage1 = new Storage(1,arr1, "Test", 500);
        Storage storage2 = new Storage(2,arr1, "Test", 5050);
        //Storage storage2 = null;
        File file1 = new File(0, "File1", "txt", 201);
        File file2 = new File(5, "Test", "avi", 200, storage1);
        ArrayList <File> list = new ArrayList<>();
        list.add(file1);
        list.add(file2);
        Controller controller = new Controller();

        //System.out.println(controller.put(storage2, file2));

        //controller.putAll(storage, list);

        //System.out.println(controller.delete(storage1, file2));

        //controller.transferAll(storage1, storage2);

        System.out.println(controller.transferFile(storage1, storage2, 5));
    }
}
