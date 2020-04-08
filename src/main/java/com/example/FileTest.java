package com.example;

import java.io.IOException;

/**
 * DOC转PDF
 *
 */
public class FileTest {
    public static void main(String[] args) throws IOException, InterruptedException {

        String command = "D:/LibreOffice/program/soffice --headless --invisible --convert-to pdf E:/file/oa3/aa1218.doc --outdir E:/file/oa3/ ";
        System.out.println(command);
        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();

    }
}
