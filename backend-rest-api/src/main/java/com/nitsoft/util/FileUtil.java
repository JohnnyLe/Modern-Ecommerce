
package com.nitsoft.util;

import com.nitsoft.ecommerce.tracelogged.EventLogManager;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;

/**
 */
public class FileUtil {
    
    public static final int BUFFER_SIZE = 100 * 1024;

    private String parentName = null;
    private File dir = null;

    /**
     * 
     * @param parentName
     * @param dir 
     */
    FileUtil(String parentName, File dir) {
        this.parentName = parentName;
        this.dir = dir;
    }

    /**
     * Delete file
     * @param filePath 
     */
    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }

    public static void deleteDirectory2(String path) {
        File dir = new File(path);
        if (dir.exists()) {
            //
            File[] files = dir.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteFile(files[i].getPath());
            }
            // 
            dir.delete();
        }
    }

    public static void deleteDirectory(String path) {
        if (path != null) {
            File dir = new File(path);
            if (dir.exists()) {
                // 
                File[] files = dir.listFiles();
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isFile()) {
                        deleteFile(files[i].getPath());
                    } else {
                        deleteDirectory(files[i].getPath());
                    }
                }
                // 
                dir.delete();
            }
        }
    }

    public static void copyFile(String in, String out) throws Exception {
        FileChannel ic = new FileInputStream(in).getChannel();
        FileChannel oc = new FileOutputStream(out).getChannel();
        oc.transferFrom(ic, 0, ic.size());
        ic.close();
        oc.close();
    }

    public static void copyFolder(String in, String out) throws Exception {

        File newdir = new File(out);
        if (!newdir.exists()) {
            newdir.mkdir();
        }

        File orgdir = new File(in);

        File[] files = orgdir.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {

                copyFile(files[i].getPath(), newdir.getPath() + "\\" + files[i].getName());
            }
        }
    }

    public static String[] getLines(String filePath) throws Exception {
        BufferedReader input = new BufferedReader(new FileReader(filePath));
        ArrayList list = new ArrayList(5000);
        String line = null;
        while ((line = input.readLine()) != null) {
            list.add(line);
        }
        String[] lines = new String[list.size()];
        list.toArray(lines);
        input.close();
        return lines;
    }

    public static void zip(String path) throws Exception {
        int point = path.lastIndexOf(".");
        zip(path, path.substring(0, point));
    }

    public static void zip(String path, String zipFileName) throws Exception {

        File file = new File(path);
        File zipFile = new File(zipFileName + ".zip");
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));


        if (file.isFile()) {
            addTargetFile(zos, file, file.getName());

        } else {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                int point = zipFile.getName().lastIndexOf(".");
                folderZip(fileList[i].getPath(), fileList[i].getName(), zos, zipFile.getName().substring(0, point));
            }
        }
        zos.close();

    }

    private static void folderZip(String path, String fileName, ZipOutputStream zos, String zipFileName) throws Exception {

        File file = new File(path);


        if (file.isFile()) {
            addTargetFile(zos, file, fileName);
        } else {
            int point = file.getPath().lastIndexOf(zipFileName);
            String zipfolderPath = file.getPath().substring(point + zipFileName.length());
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                folderZip(fileList[i].getPath(), zipfolderPath + "\\" + fileList[i].getName(), zos, zipFileName);
            }
        }
    }

    protected Vector pathNames() {
        String[] list = dir.list();
        File[] files = new File[list.length];
        Vector vec = new Vector();
        for (int i = 0; i < list.length; i++) {

            String pathName = parentName
                    + File.separator + list[i];
            files[i] = new File(pathName);
            if (files[i].isFile()) {
                vec.addElement(files[i].getPath());
            }
        }

        return vec;
    }

    public static void addTargetFile(ZipOutputStream zos, File file, String fileName) throws Exception {
        int EOF = -1;
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));

        ZipEntry target = new ZipEntry(fileName);
        zos.putNextEntry(target);


        byte buf[] = new byte[1024];
        int count;
        while ((count = bis.read(buf, 0, 1024)) != EOF) {
            zos.write(buf, 0, count);
        }
        bis.close();
        zos.closeEntry();
    }
    
    
    
   public static String getPrefix(String fileName) {
        if (fileName == null) {
            return null;
        }
        int point = fileName.lastIndexOf(".");
        if (point != -1) {
            return fileName.substring(0, point);
        }
        return fileName;
    } 
   
    public static String getFileSizeString( int fileSize) {        
        int Mb= fileSize/1024/1024;
        if(Mb>0) 
          return String.valueOf((fileSize/1024/1024))+"MB";
        else
          return String.valueOf((fileSize/1024))+"KB";
    }
    
    /**
     *
     * @param response
     * @param filePath
     * @param fileName
     * @param fileType
     * @throws IOException
     * @throws ServletException
     */
    public static void downloadTeamRecieptFromServer(HttpServletResponse response, String RealPath, Date createDate) {
        EventLogManager.getInstance().info("downloadFromLocalServer path=" + RealPath);
        try {
        java.io.File initialFile = new java.io.File(RealPath);
        InputStream inputStream = new FileInputStream(initialFile);
        // Do Download
        
            if (inputStream != null) {
                int contentLength = inputStream.available();
                response.setContentLength(contentLength);
                response.addHeader("Content-Length", Long.toString(contentLength));
                //read from the file; write to the ServletOutputStream
                String ext = FilenameUtils.getExtension(RealPath);
                
                response.setContentType(MimeTypeUtils.getMineType(ext)); // Fixing bug: Lack extention on IE9
                response.setHeader("Content-Type", MimeTypeUtils.getMineType(ext));
                
//                String baseName = (ext.equals(fileType)) ? FilenameUtils.getBaseName(fileName) : fileName;

                response.setHeader("Content-Disposition", "attachment; filename=\"receipt_" + createDate.toString() + "." + ext + "\""); // Fix bug: Lack file name on Firefox
                ServletOutputStream out = response.getOutputStream();
                byte[] buffer = new byte[1024];
                try {
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) >= 0) {
                        out.write(buffer, 0, bytesRead);
                    }
                } catch (Exception e) {
                    System.err.println(e);
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                }
            }
        } catch (Exception ex) {
            EventLogManager.getInstance().info("doDownload file error" + ex.getMessage());
        }
    }
    
    /**
     * Append new input stream into specific file
     *
     * @param in
     * @param destFile
     */
    public static void appendFile(InputStream in, File destFile) {
        OutputStream out = null;
        try {
            if (destFile.exists()) {
                out = new BufferedOutputStream(new FileOutputStream(destFile, true), BUFFER_SIZE);
            } else {
                out = new BufferedOutputStream(new FileOutputStream(destFile), BUFFER_SIZE);
            }
            in = new BufferedInputStream(in, BUFFER_SIZE);

            int len = 0;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (IOException e) {
            EventLogManager.getInstance().error(e.getMessage());
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
                if (null != out) {
                    out.close();
                }
            } catch (IOException e) {
                EventLogManager.getInstance().error(e.getMessage());
            }
        }
    }
}