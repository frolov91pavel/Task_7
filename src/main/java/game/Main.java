package game;

import java.io.File;

public class Main {
    public static void main(String[] args) {

        /// Задание №1 (Необязательно)
        try {
            // Путь к папке с плагинами
            PluginManager pluginManager = new PluginManager("src/main/resources/plugins");

            // Загружаем и выполняем плагин
            Plugin plugin = pluginManager.load("ExamplePlugin", "game.ExamplePlugin");
            plugin.doUseful();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Задание 1 заверешено \n");

        /// Задание 2 (Обязательно)

        try {
            // Шифрование файла
            String encryptionKey = "2024";
            File originalFile = new File("target/classes/game/MyClass.class");
            File encryptedFile = new File("src/main/resources/encrypted/MyClass.class");
            Encryptor encryptor = new Encryptor(encryptionKey);
            encryptor.encryptClassFile(originalFile, encryptedFile);
            System.out.println("Class file encrypted successfully.");

            // Загрузка и выполнение класса
            File rootDir = new File("src/main/resources/encrypted");
            EncryptedClassLoader classLoader = new EncryptedClassLoader(encryptionKey, rootDir, Main.class.getClassLoader());
            Class<?> loadedClass = classLoader.loadClass("game.MyClass");
            Object instance = loadedClass.getDeclaredConstructor().newInstance();
            loadedClass.getMethod("sayHello").invoke(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Задание 2 заверешено \n");

    }
}