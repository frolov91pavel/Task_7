package game;

import java.net.URL;
import java.net.URLClassLoader;

public class PluginClassLoader extends URLClassLoader {
    public PluginClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        // Сначала пытаемся загрузить класс из плагина
        synchronized (getClassLoadingLock(name)) {
            try {
                Class<?> clazz = findClass(name); // Ищем в плагине
                if (resolve) {
                    resolveClass(clazz);
                }
                return clazz;
            } catch (ClassNotFoundException e) {
                // Если не удалось найти в плагине, используем родительский загрузчик
                System.out.println("Class not found: " + name);
                return super.loadClass(name, resolve);
            }
        }
    }
}