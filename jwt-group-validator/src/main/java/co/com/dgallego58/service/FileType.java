package co.com.dgallego58.service;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum FileType {

    JPG("jpg", "FFD8FF"),
    PDF("pdf", "255044462D");

    private final String type;
    private final String hexSignature;

    FileType(String type, String hexSignature) {
        this.type = type;
        this.hexSignature = hexSignature;
    }

    public static FileType fromExtension(String ext) {
        return Arrays.stream(FileType.values())
                .filter(fileType -> fileType.getType().equalsIgnoreCase(ext))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Extension not found"));
    }

    public String getType() {
        return type;
    }

    public String getHexSignature() {
        return hexSignature;
    }

    public int bytesToCheck() {
        return hexSignature.trim()
                .replace(" ", "")
                .length() / 2;
    }
}
