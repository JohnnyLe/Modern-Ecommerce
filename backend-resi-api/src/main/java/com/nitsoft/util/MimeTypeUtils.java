/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nitsoft.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 *
 * @author vkloan
 */
public class MimeTypeUtils {

    private static final Map<String, String> mimeTypeToExtensionMap;

    static String DEFAULT_TYPE = "application/octet-stream";

    static {
        mimeTypeToExtensionMap = new HashMap<String, String>();
        //PDF
        // mimeTypeToExtensionMap.put("application/pdf", "pdf");
        // Word
//        mimeTypeToExtensionMap.put("application/vnd.openxmlformats-officedocument.wordprocessingml.document", "docx");
//        // Excel
//        mimeTypeToExtensionMap.put("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "xlsx");
//        // Power point
//        mimeTypeToExtensionMap.put("application/vnd.openxmlformats-officedocument.presentationml.presentation", "pptx");

        try {
              InputStream is = MimeTypeUtils.class.getResourceAsStream("/META-INF/mimetypes.default");
              if (is != null) {
                  try {
                      loadStream(is);
                  } finally {
                      is.close();
                  }
              }
          } catch (IOException e) {
              // ignore
              System.err.println( e.getMessage() );
          }
    }

    private static void loadStream(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = reader.readLine()) != null) {
            addMimeTypes(line);
        }

//        Properties properties = new Properties();
//
//        properties.load(is);
//
//        for (String key : properties.stringPropertyNames()) {
//            String value = properties.getProperty(key);
//            mimeTypeToExtensionMap.put(key, value);
//        }
    }

    public static synchronized void addMimeTypes(String mime_types) {
         int hashPos = mime_types.indexOf('#');
         if (hashPos != -1) {
             mime_types = mime_types.substring(0, hashPos);
         }
         StringTokenizer tok = new StringTokenizer(mime_types);
         if (!tok.hasMoreTokens()) {
             return;
         }
         String contentType = tok.nextToken();
         while (tok.hasMoreTokens()) {
             String fileType = tok.nextToken();
             mimeTypeToExtensionMap.put(fileType, contentType);
         }
     }

    public static String getContentType( String filename ) {
        int index = filename.lastIndexOf('.');
         if (index == -1 || index == filename.length()-1) {
             return DEFAULT_TYPE;
         }
         String fileType = filename.substring(index + 1);
         String contentType = (String) mimeTypeToExtensionMap.get(fileType.toLowerCase());
         return contentType == null ? DEFAULT_TYPE : contentType;
    }


    public static String getExtension(String mimeType) {
        return mimeTypeToExtensionMap.get(mimeType).toLowerCase();
    }

    public static String getMineType(String ext) {
        String mine = "application/octet-stream";
        for (Entry<String, String> en : mimeTypeToExtensionMap.entrySet()) {
            if (ext.equals(en.getValue())) {
                mine = en.getKey();
            }
        }
        return mine;
    }
}
