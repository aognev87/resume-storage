package ru.aognev.webapp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aognev on 07.09.2016.
 */
public class MainFile {
    public static void main(String[] args) {
        File file = new File(".");

        List<String> fileList = getFileList(file);
        for (String s : fileList) {
            System.out.println(s);
        }

        List<String> filledFileList = new ArrayList<>();
        fillFileList(filledFileList, file);

        System.out.println(fileList.size() + ", " + filledFileList.size());
        System.out.println(fileList.equals(filledFileList));
    }

    private static List<String> getFileList(File file) {
        List<String> fileList = new ArrayList<>();

        for (File f : file.listFiles()) {
            if (f.isDirectory()) {
                fileList.addAll(getFileList(f));
            } else {
                fileList.add(f.getPath());
            }
        }

        return fileList;
    }

    private static void fillFileList(List<String> fileList,  File file) {
        for (File f : file.listFiles()) {
            if (f.isDirectory()) {
                fillFileList(fileList, f);
            } else {
                fileList.add(f.getPath());
            }
        }
    }
}
