import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    static String fileName = Paths.get("").toAbsolutePath().toString() + "\\";
    static ArrayList<Student> arrayList = new ArrayList<>();
    static Scanner in = new Scanner(System.in);


    public static void main(String[] args) throws IOException, ClassNotFoundException {


        switch (loadOrCreate()) {
            case 1:

                File folder = new File(Paths.get("").toAbsolutePath().toString());
                File[] listOfFiles = folder.listFiles((dir1, name) -> name.endsWith(".txt"));
                if (listOfFiles.length > 1) {
                    System.out.println("какой из файлов загрузить?");
                    for (File f : listOfFiles) {
                        System.out.println(f.getName());
                    }
                    System.out.println("Введите название");
                }

                fileName += in.nextLine();

                try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileName))) {
                    arrayList = (ArrayList<Student>) objectInputStream.readObject();
                    System.out.println("Файл успешно загружен");
                    break;
                } catch (FileNotFoundException e) {
                    System.out.println("файл не обнаружен");
                    return;
                }

            case 2:
                createFile();
                System.out.println(fileName);
                break;
        }

        while (true) {
            String menu =
                    "a. Добавьте нового ученика\n" +
                            "b. Удалите ученика\n" +
                            "c. Обновите оценку ученика\n" +
                            "d. Просмотр оценок всех учащихся\n" +
                            "e. Просмотр оценок конкретного учащегося\n" +
                            "f. Сохранить и закрыть";

            System.out.println(menu);

            int score;
            String name;

            String operation = "";

            while (!Pattern.matches("[a-f]", (operation = in.nextLine()))) {
                System.out.println("Введите букву в диапазоне: \"a - f\"");
            }

            Student student = new Student();

            switch (operation) {
                case "a":
                    System.out.println("Имя?");
                    name = in.nextLine();
                    student.setName(name);
                    arrayList.add(student);
                    break;
                case "b":
                    System.out.println("Кого удалить?");
                    name = in.nextLine();
                    arrayList.remove(searchStudent(name, arrayList));
                    break;
                case "c":
                    System.out.println("Чью оценку обновляем?");
                    name = in.nextLine();
                    student = searchStudent(name, arrayList);
                    System.out.println("Оценка?");
                    score = in.nextInt();
                    try {
                        student.newScore(score);
                    } catch (IllegalArgumentException e) {
                        e.getMessage();
                    }
                    break;
                case "d":

                    for (Student s : arrayList) {
                        System.out.println(s);
                    }

                    break;
                case "e":
                    System.out.println("Чьи оценки смотрим?");
                    name = in.nextLine();
                    student = searchStudent(name, arrayList);
                    System.out.println(student);
                    break;
                case "f":
                    try (FileOutputStream fileOutputStream = new FileOutputStream(fileName);
                         ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
                        objectOutputStream.writeObject(arrayList);
                        return;
                    } catch (FileNotFoundException e) {
                        System.out.println("File not found : " + e.getMessage());
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        System.out.println("Error while writing data : " + e.getMessage());
                        throw new RuntimeException(e);
                    }

                default:
                    System.out.println("default");
            }
        }
    }

    static int loadOrCreate() {
        String startMenu = "1. Загрузить файл\n" +
                "2. Создать новый файл\n" +
                "Введите 1 или 2:";
        System.out.println(startMenu);

        int x;

        while ((x = in.nextInt()) < 1 || x > 2) {
            System.out.println("Введите 1 или 2:");
        }

        in.nextLine();
        return x;
    }


    static Student searchStudent(String name, ArrayList<Student> arrayList) {

        Iterator<Student> iterator = arrayList.iterator();

        while (iterator.hasNext()) {
            Student nextStudent = iterator.next();
            if (nextStudent.getName().equals(name)) {
                return nextStudent;
            }
        }
        return null;
    }

    static void createFile() throws IOException {
        System.out.println("Введите название файла: ");
        fileName += in.nextLine();
        File file = Files.createFile(Paths.get(fileName)).toFile();
        if (file.exists()) {
            System.out.println("Файл успешно создан");
        } else {
            System.out.println("файл не создан");
        }
    }

}
