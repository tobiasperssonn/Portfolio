import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.kerberos.KerberosKey;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Scanner;
import java.io.UnsupportedEncodingException;

public class Encryptor {

    public static void encrypt_file(File file) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKey key = generate_Key();
            IvParameterSpec iv = IvGen();
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);

            FileInputStream inputStream = new FileInputStream(file);
            byte[] inputBytes = new byte[(int) file.length()];
            inputStream.read(inputBytes);
            inputStream.close();

            byte[] encryptedBytes = cipher.doFinal(inputBytes);

            FileOutputStream outputStream = new FileOutputStream(file);

            outputStream.write(encryptedBytes);
            outputStream.close();

            System.out.println("File encrypted successfully: " + file.getAbsolutePath());
            System.out.println("Using the secret key: " + key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void decrypt_file(File file, int key) {

    }

    public static SecretKey generate_Key() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        SecretKey key = keyGen.generateKey();
        return key;
    }

    public static IvParameterSpec IvGen() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public static void main(String[] args) {
        String path = "C:/Programmering/Test/Testfiler";
        File dir = new File(path);
        File[] dirList = dir.listFiles(); 

        if(dirList != null) {
            for(File foundFile: dirList) {
                encrypt_file(foundFile);
            }
        } else {
            System.out.println("NO FILES IN DIRECTORY: " + path);
        }

        Scanner keyboard = new Scanner(System.in);

        System.out.println("Press E to exit");
        while (true) {
            if (keyboard.next().equalsIgnoreCase("e")) {
                break;
            }
        }
    }
}
