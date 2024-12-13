package game;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

public class EncryptedClassLoader extends ClassLoader {
    private final String encryptionKey;
    private final File rootDir;

    public EncryptedClassLoader(String encryptionKey, File rootDir, ClassLoader parent) {
        super(parent);
        this.encryptionKey = encryptionKey;
        this.rootDir = rootDir;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File classFile = new File(rootDir, name.replace('.', File.separatorChar) + ".class");
        if (!classFile.exists()) {
            throw new ClassNotFoundException("Class file not found: " + classFile.getAbsolutePath());
        }

        try (FileInputStream fis = new FileInputStream(classFile)) {
            byte[] encryptedBytes = fis.readAllBytes();
            byte[] decryptedBytes = decrypt(encryptedBytes);
            return defineClass(name, decryptedBytes, 0, decryptedBytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException("Failed to load class: " + name, e);
        }
    }

    private byte[] decrypt(byte[] data) {
        byte[] keyBytes = encryptionKey.getBytes();
        byte[] decrypted = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            decrypted[i] = (byte) (data[i] ^ keyBytes[i % keyBytes.length]);
        }
        return decrypted;
    }
}