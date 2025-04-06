package com.library.Util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class QRCodeUtil {
    public static void generateQRCode(String data, String filePath, int size) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, size, size);
        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
        ImageIO.write(image, "PNG", new File(filePath));
    }

    public static String readQRCode(File file) {
        // Implementation for reading QR code from a file can be added here
        return null; // Placeholder for future implementation
    }
}