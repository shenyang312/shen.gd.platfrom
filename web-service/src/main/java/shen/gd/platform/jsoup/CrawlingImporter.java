/*
 * Zenlayer.com Inc.
 * Copyright (c) 2014-2021 All Rights Reserved.
 */
package shen.gd.platform.jsoup;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.ObjectUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author yangshen
 * @date 2021-03-07 16:01:22
 * @version $ Id: CrawlingImporter.java, v 0.1  yangshen Exp $
 */
public class CrawlingImporter {

    public static void main(String[] args) throws IOException {
        //设置需要下载多少页
        //先爬取476页的内容
        //int page = 476;
        int page = 1;
        int result = 0;
        for (int i = 1; i <= page; i++) {

            result = pachong_page("http://importer.usaypage.com/lamps-category/index-" + i + ".html");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("爬取结束！一共爬取内容为:" + result * page + "条！");
    }

    public static int pachong_page(String url) throws IOException {
        //String url="http://www.ygdy8.net/html/gndy/dyzz/list_23_1.html";
        Document doc = null;
        try {
            doc = Jsoup.connect(url).userAgent("Mozilla").get();//模拟火狐浏览器
        } catch (IOException e) {
            e.printStackTrace();
        }
        //这里根据在网页中分析的类选择器来获取电影列表所在的节点
        Elements div = doc.getElementsByClass("rmainc");
        //查找table标签
        Elements table = div.select("table");
        int result = table.size();
        List<Map<String, Object>> list = Lists.newArrayList();
        for (Element tb : table) {
            //获取所有商铺
            Elements tr = tb.select("tr");
            if (ObjectUtils.isEmpty(tr) || tr.size() < 4) {
                result -= 1;
                break;
            }
            //Name
            String name = tr.get(0).select("a").text();
            //Importer Category
            String importerCategory = tr.get(1).select("a").text();
            //Address
            String address = tr.get(2).select("td").text();
            //Address
            String country = tr.get(3).select("a").text();
            //Tel
            String tel = tr.get(4).select("td").get(0).text();
            //Fax
            String fax = tr.get(4).select("td").get(1).text();
            Map<String, Object> map = Maps.newHashMap();
            map.put("name", name);
            map.put("importerCategory", importerCategory);
            map.put("address", address);
            map.put("country", country);
            map.put("tel", tel);
            map.put("fax", fax);
            System.out.println("1--->>>>>>>>>>>");
            System.out.println("name" + name);
            System.out.print("importerCategory" + importerCategory);
            System.out.print("address" + address);
            System.out.print("country" + country);
            System.out.print("tel" + tel);
            System.out.println("fax" + fax);
            System.out.println("2--->>>>>>>>>>>");
            list.add(map);

        }

        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream("/Users/yangshen/Documents/2222.xlsx");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        writeExcel(list, fileOut);
        return result;

    }

    /**
     * 把内容写入Excel
     * @param list 传入要写的内容，此处以一个List内容为例，先把要写的内容放到一个list中
     * @param outputStream 把输出流怼到要写入的Excel上，准备往里面写数据
     */
    @SuppressWarnings(value = "all")
    public static void writeExcel(List<Map<String, Object>> list, FileOutputStream outputStream) {
        //创建工作簿
        XSSFWorkbook xssfWorkbook = null;
        xssfWorkbook = new XSSFWorkbook();

        //创建工作表
        XSSFSheet xssfSheet;
        xssfSheet = xssfWorkbook.createSheet();

        //创建行
        XSSFRow xssfRow;

        //创建列，即单元格Cell
        XSSFCell xssfCell;
        xssfRow = xssfSheet.createRow(0);
        xssfCell = xssfRow.createCell(0); //创建单元格
        xssfCell.setCellValue("name"); //设置单元格内容
        xssfSheet.setColumnWidth(xssfCell.getColumnIndex(), 250 * 50);
        xssfCell = xssfRow.createCell(1); //创建单元格
        xssfCell.setCellValue("importerCategory"); //设置单元格内容
        xssfSheet.setColumnWidth(xssfCell.getColumnIndex(), 50 * 50);
        xssfCell = xssfRow.createCell(2); //创建单元格
        xssfCell.setCellValue("address"); //设置单元格内容
        xssfSheet.setColumnWidth(xssfCell.getColumnIndex(), 500 * 50);
        xssfCell = xssfRow.createCell(3); //创建单元格
        xssfCell.setCellValue("country"); //设置单元格内容
        xssfSheet.setColumnWidth(xssfCell.getColumnIndex(), 100 * 50);
        xssfCell = xssfRow.createCell(4); //创建单元格
        xssfCell.setCellValue("tel"); //设置单元格内容
        xssfSheet.setColumnWidth(xssfCell.getColumnIndex(), 100 * 50);
        xssfCell = xssfRow.createCell(5); //创建单元格
        xssfCell.setCellValue("fax"); //设置单元格内容
        xssfSheet.setColumnWidth(xssfCell.getColumnIndex(), 100 * 50);

        //把List里面的数据写到excel中
        for (int i = 1; i <= list.size(); i++) {
            //从第一行开始写入
            xssfRow = xssfSheet.createRow(i);
            //创建每个单元格Cell，即列的数据
            Map<String, Object> sub_list = list.get(i - 1);
            xssfCell = xssfRow.createCell(0); //创建单元格
            xssfCell.setCellValue((String) sub_list.get("name")); //设置单元格内容
            xssfCell = xssfRow.createCell(1); //创建单元格
            xssfCell.setCellValue((String) sub_list.get("importerCategory")); //设置单元格内容
            xssfCell = xssfRow.createCell(2); //创建单元格
            xssfCell.setCellValue((String) sub_list.get("address")); //设置单元格内容
            xssfCell = xssfRow.createCell(3); //创建单元格
            xssfCell.setCellValue((String) sub_list.get("country")); //设置单元格内容
            xssfCell = xssfRow.createCell(4); //创建单元格
            xssfCell.setCellValue((String) sub_list.get("tel")); //设置单元格内容
            xssfCell = xssfRow.createCell(5); //创建单元格
            xssfCell.setCellValue((String) sub_list.get("fax")); //设置单元格内容
        }

        //用输出流写到excel
        try {
            xssfWorkbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
