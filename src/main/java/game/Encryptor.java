package game;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class Encryptor {
    private final String encryptionKey;

    public Encryptor(String encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    public void encryptClassFile(File inputFile, File outputFile) throws IOException {
        byte[] classBytes = Files.readAllBytes(inputFile.toPath());
        byte[] encryptedBytes = encrypt(classBytes);
        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            fos.write(encryptedBytes);
        }
    }

    private byte[] encrypt(byte[] data) {
        byte[] keyBytes = encryptionKey.getBytes();
        byte[] encrypted = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            encrypted[i] = (byte) (data[i] ^ keyBytes[i % keyBytes.length]);
        }
        return encrypted;
    }
}