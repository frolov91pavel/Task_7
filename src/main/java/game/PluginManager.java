package game;

import java.io.File;
import java.net.URL;

public class PluginManager {
    private final String pluginRootDirectory;

    public PluginManager(String pluginRootDirectory) {
        this.pluginRootDirectory = pluginRootDirectory;
    }

    public Plugin load(String pluginName, String pluginClassName) throws Exception {
        // Формируем путь к директории плагина
        File pluginDir = new File(pluginRootDirectory);
        if (!pluginDir.exists() || !pluginDir.isDirectory()) {
            throw new IllegalArgumentException("Plugin directory does not exist: " + pluginDir.getAbsolutePath());
        }

        // Создаём URL для загрузки плагина
        URL[] pluginUrls = {pluginDir.toURI().toURL()};
        ClassLoader parentClassLoader = this.getClass().getClassLoader();

        // Создаём наш кастомный ClassLoader
        try (PluginClassLoader classLoader = new PluginClassLoader(pluginUrls, parentClassLoader)) {
            // Загружаем класс из плагина
            Class<?> pluginClass = classLoader.loadClass(pluginClassName);
            return (Plugin) pluginClass.getDeclaredConstructor().newInstance();
        }
    }
}