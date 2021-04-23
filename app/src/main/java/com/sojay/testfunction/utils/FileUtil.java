package com.sojay.testfunction.utils;

import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    //############################################################################################//

    /**
     * 创建文件
     */
    public static void createFile(String filePath) {
        if (TextUtils.isEmpty(filePath))
            return;

        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建文件夹
     *
     * @param dir
     */
    public static void createFileDir(String dir) {
        if (TextUtils.isEmpty(dir))
            return;

        File file = new File(dir);
        if (!file.exists()) {
            if (file.isFile())
                file.getParentFile().mkdirs();
            else
                file.mkdirs();
        }
    }

    //############################################################################################//

    /**
     * 删除多个文件
     *
     * @param fileList 要删除的文件的文件
     */
    public static void deleteFile(List<String> fileList) {
        for (String file : fileList) {
            deleteFile(file);
        }
    }

    /**
     * 删除某个文件夹下除忽略文件之外的其余文件
     * @param dirPath 文件夹路径
     * @param ignoreFiles 忽略的文件列表
     */
    public static void deleteOtherFile(String dirPath, List<String> ignoreFiles) {
        if (TextUtils.isEmpty(dirPath))
            return;

        if (ignoreFiles == null || ignoreFiles.isEmpty()) {
            deleteDirFiles(dirPath);
        } else {
            File dirFile = new File(dirPath);

            if (!dirFile.exists() || dirFile.isFile()) {
                createFileDir(dirPath);
            } else {
                File[] files = dirFile.listFiles();
                for (int i = files.length - 1; i >= 0; i--) {
                    File f = files[i];
                    if (f.isDirectory()) {
                        deleteOtherFile(f.getPath(), ignoreFiles);
                    } else {
                        if (!ignoreFiles.contains(f.getPath()))
                            f.delete();
                    }
                }
            }
        }
    }

    /**
     * 删除单个文件
     *
     * @param filePath 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String filePath) {

        if (null == filePath || TextUtils.isEmpty(filePath))
            return false;

        File file = new File(filePath);
        if (file.exists()) {
            // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
            if (file.isFile()) {
                if (file.delete())
                    return true;
            } else {
                File[] files = file.listFiles();
                // 如果文件路径所对应的是一个文件夹，里面没有内容则直接删除
                if (files != null && files.length == 0) {
                    file.delete();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 删除文件夹下的所有文件
     * @param dirPath 文件夹路径
     */
    public static void deleteDirFiles(String dirPath) {
        if (TextUtils.isEmpty(dirPath))
            return;

        File dirFile = new File(dirPath);

        if (!dirFile.exists() || dirFile.isFile()) {
            createFileDir(dirPath);
        } else {
            File[] files = dirFile.listFiles();
            for (int i = files.length - 1; i >= 0; i--) {
                File f = files[i];
                if (f.isDirectory()) {
                    deleteDirFiles(f.getPath());
                } else {
                    files[i].delete();
                }
            }
        }
    }

    //############################################################################################//

    /**
     * 获取文件大小
     *
     * @param filePath
     * @return
     */
    public static int getFileSize(String filePath) {
        if (null == filePath || TextUtils.isEmpty(filePath))
            return 0;

        try {
            File file = new File(filePath);
            if (file.exists()) {
                FileInputStream fis = null;
                fis = new FileInputStream(file);
                return fis.available();

            } else {
                return 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

     /**
     * 获取文件大小
     *
     * @param file
     * @return
     */
    public static int getFileSize(File file) {
        try {
            if (file.exists()) {
                FileInputStream fis = null;
                fis = new FileInputStream(file);
                return fis.available();
            } else {
                return 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //############################################################################################//

    /**
     * 获取指定文件夹下所有文件名称
     */
    public static List<File> getDirFiles(String dirPath) {
        List<File> list = new ArrayList<>();

        if (null != dirPath && !TextUtils.isEmpty(dirPath)) {
            File file = new File(dirPath);
            if (file.exists() && file.isDirectory()) {
                File[] listFiles = file.listFiles();
                for (File f : listFiles) {
                    if (f.isFile()) {
                        list.add(f);
                    } else {
                        list.addAll(getDirFiles(f.getPath()));
                        list.add(f);
                    }
                }
            }
        }

        return list;
    }

    /**
     * 获取指定文件夹下所有文件名称
     */
    public static List<String> getDirFileName(String dirPath) {
        List<String> list = new ArrayList<>();

        if (null != dirPath && !TextUtils.isEmpty(dirPath)) {
            File file = new File(dirPath);
            if (file.exists() && file.isDirectory()) {
                File[] listFiles = file.listFiles();
                for (File f : listFiles) {
                    if (f.isFile()) {
                        list.add(f.getPath());
                    } else {
                        list.addAll(getDirFileName(f.getPath()));
                        list.add(f.getPath());
                    }
                }
            }
        }
        return list;
    }

    //############################################################################################//

    /**
     * 文件是否存在
     *
     * @param filePath
     * @return
     */
    public static boolean exists(String filePath) {
        if (null == filePath || TextUtils.isEmpty(filePath))
            return false;

        File file = new File(filePath);
        return file.exists() && file.isFile();
    }

    /**
     * 检测某文件夹下某文件是否存在
     * @param dirPath 文件夹
     * @param fileName 文件
     */
    public static boolean exists(String dirPath, String fileName) {

        if (TextUtils.isEmpty(dirPath) || TextUtils.isEmpty(fileName))
            return false;

        List<File> dirFiles = getDirFiles(dirPath);

        for (File file: dirFiles) {
            if (file.getName().contains(fileName))
                return true;
        }

        return false;
    }

    //############################################################################################//


}
