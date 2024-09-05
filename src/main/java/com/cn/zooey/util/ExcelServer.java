package com.cn.zooey.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author Fengzl
 * @date 2024/7/29 16:40
 * @desc
 **/
public class ExcelServer {


    public static void main(String[] args) throws IOException {
        // generateData();
        // readData();
        zipFile();
    }


    public static void generateData() throws IOException {
        String fileName = "/data/test/zl_name5.xlsx";
        File file = new File(fileName);
        if (!file.exists()) {
            boolean newFile = file.createNewFile();
            System.out.println("创建文件" + newFile);
        }

        List<List<String>> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            List<String> list1 = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                list1.add("姓名" + i + j);
            }
            list.add(list1);
        }

        ExcelWriter excelWriter = EasyExcel.write(fileName, List.class).build();
        WriteSheet sheet = EasyExcel.writerSheet().sheetName("Sheet1").build();
        excelWriter.write(list, sheet);
        excelWriter.finish();

    }


    public static void readData() throws IOException {
        String fileName = "/data/test/zl_name.xlsx";

        ExcelReader reader = EasyExcel.read(fileName, new DataListener()).headRowNumber(0).build();
        ReadSheet sheet = EasyExcel.readSheet().sheetNo(0).sheetName("Sheet1").build();
        reader.read(sheet);
        reader.finish();
    }


    private static void zipFile( ) {
        Set<String> fileSet = new HashSet<>();
        fileSet.add("zl_name.xlsx");
        fileSet.add("zl_name2.xlsx");
        fileSet.add("zl_name3.xlsx");
        fileSet.add("zl_name4.xlsx");
        fileSet.add("zl_name5.xlsx");

        File zipFile = new File("/data/test/zl_name_000.zip");
        try (ZipOutputStream zipOut = new ZipOutputStream(Files.newOutputStream(zipFile.toPath()));
             ) {

            // 遍历每个txt文件，逐个添加到zip中
            for (String srcFile : fileSet) {
                try (BufferedInputStream in = new BufferedInputStream(Files.newInputStream(Paths.get("/data/test/" + srcFile)))) {
                    zipOut.putNextEntry(new ZipEntry(srcFile));
                    // 读取txt文件内容并写入zip文件
                    byte[] buffer = new byte[2048];
                    int length;
                    while ((length = in.read(buffer)) >= 0) {
                        zipOut.write(buffer, 0, length);
                    }
                    zipOut.closeEntry();
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


}


class DataListener implements ReadListener<Map<String, String>> {
    private static final int BATCH_NUMBER = 100;
    private int total;
    private int rowNum;

    private List<Map<String, String>> list = new ArrayList<>();

    @Override
    public void invoke(Map<String, String> data, AnalysisContext analysisContext) {
        total = analysisContext.readSheetHolder().getApproximateTotalRowNumber();
        list.add(data);
        // System.out.println(JSONObject.toJSONString(data));
        rowNum++;
        if (list.size() >= BATCH_NUMBER) {
            System.out.println("保存" + list.size() + " 共:" + rowNum + " / " + total);
            list = new ArrayList<>();
        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        if (!list.isEmpty()) {
            System.out.println("最后一次保存" + list.size() + " 共:" + rowNum + " / " + total);
        }
        System.out.println("读取完成 " + list.size() + "共" + rowNum + " / " + total);

    }



}
