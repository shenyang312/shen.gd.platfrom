package shen.gd.platform.service;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Maps;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class JsoupQQ {
    public static void main(String[] str) throws InterruptedException {
//        List<Integer> num
//        List list = jsoup("http://hs.bianmachaxun.com/");
//        List list = jsoupFour("http://hs.bianmachaxun.com/");
        String s = "101, 102, 103, 104, 105, 106, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 301, 302, 303, 304, 305, 306, 307, 308, 401, 402, 403, 404, 405, 406, 407, 408, 409, 410, 501, 502, 504, 505, 506, 507, 508, 510, 511, 601, 602, 603, 604, 701, 702, 703, 704, 705, 706, 707, 708, 709, 710, 711, 712, 713, 714, 801, 802, 803, 804, 805, 806, 807, 808, 809, 810, 811, 812, 813, 814, 901, 902, 903, 904, 905, 906, 907, 908, 909, 910, 1001, 1002, 1003, 1004, 1005, 1006, 1007, 1008, 1101, 1102, 1103, 1104, 1105, 1106, 1107, 1108, 1109, 1201, 1202, 1203, 1204, 1205, 1206, 1207, 1208, 1209, 1210, 1211, 1212, 1213, 1214, 1301, 1302, 1401, 1404, 1501, 1502, 1503, 1504, 1505, 1506, 1507, 1508, 1509, 1510, 1511, 1512, 1513, 1514, 1515, 1516, 1517, 1518, 1520, 1521, 1522, 1601, 1602, 1603, 1604, 1605, 1701, 1702, 1703, 1704, 1801, 1802, 1803, 1804, 1805, 1806, 1901, 1902, 1903, 1904, 1905, 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008, 2009, 2101, 2102, 2103, 2104, 2105, 2106, 2201, 2202, 2203, 2204, 2205, 2206, 2207, 2208, 2209, 2301, 2302, 2303, 2304, 2305, 2306, 2307, 2308, 2309, 2401, 2402, 2403, 2501, 2502, 2503, 2504, 2505, 2506, 2507, 2508, 2509, 2510, 2511, 2512, 2514, 2515, 2516, 2517, 2518, 2519, 2520, 2521, 2522, 2523, 2524, 2525, 2526, 2528, 2529, 2530, 2601, 2602, 2603, 2604, 2605, 2606, 2607, 2608, 2609, 2610, 2611, 2612, 2613, 2614, 2615, 2616, 2617, 2618, 2619, 2620, 2621, 2701, 2702, 2703, 2704, 2705, 2706, 2707, 2708, 2709, 2710, 2711, 2712, 2713, 2714, 2715, 2716, 2801, 2802, 2803, 2804, 2805, 2806, 2807, 2808, 2809, 2810, 2811, 2812, 2813, 2814, 2815, 2816, 2817, 2818, 2819, 2820, 2821, 2822, 2823, 2824, 2825, 2826, 2827, 2828, 2829, 2830, 2831, 2832, 2833, 2834, 2835, 2836, 2837, 2839, 2840, 2841, 2842, 2843, 2844, 2845, 2846, 2847, 2849, 2850, 2852, 2853, 2901, 2902, 2903, 2904, 2905, 2906, 2907, 2908, 2909, 2910, 2911, 2912, 2913, 2914, 2915, 2916, 2917, 2918, 2919, 2920, 2921, 2922, 2923, 2924, 2925, 2926, 2927, 2928, 2929, 2930, 2931, 2932, 2933, 2934, 2935, 2936, 2937, 2938, 2939, 2940, 2941, 2942, 3001, 3002, 3003, 3004, 3005, 3006, 3101, 3102, 3103, 3104, 3105, 3201, 3202, 3203, 3204, 3205, 3206, 3207, 3208, 3209, 3210, 3211, 3212, 3213, 3214, 3215, 3301, 3302, 3303, 3304, 3305, 3306, 3307, 3401, 3402, 3403, 3404, 3405, 3406, 3407, 3501, 3502, 3503, 3504, 3505, 3506, 3507, 3601, 3602, 3603, 3604, 3605, 3606, 3701, 3702, 3703, 3704, 3705, 3706, 3707, 3801, 3802, 3803, 3804, 3805, 3806, 3807, 3808, 3809, 3810, 3811, 3812, 3813, 3814, 3815, 3816, 3817, 3818, 3819, 3820, 3821, 3822, 3823, 3824, 3825, 3826, 3901, 3902, 3903, 3904, 3905, 3906, 3907, 3908, 3909, 3910, 3911, 3912, 3913, 3914, 3915, 3916, 3917, 3918, 3919, 3920, 3921, 3922, 3923, 3924, 3925, 3926, 4001, 4002, 4003, 4004, 4005, 4006, 4007, 4008, 4009, 4010, 4011, 4012, 4013, 4014, 4015, 4016, 4017, 4101, 4102, 4103, 4104, 4105, 4106, 4107, 4112, 4113, 4114, 4115, 4201, 4202, 4203, 4205, 4206, 4301, 4302, 4303, 4304, 4401, 4402, 4403, 4404, 4405, 4406, 4407, 4408, 4409, 4410, 4411, 4412, 4413, 4414, 4415, 4416, 4417, 4418, 4419, 4420, 4421, 4501, 4502, 4503, 4504, 4601, 4602, 4701, 4702, 4703, 4704, 4705, 4706, 4707, 4801, 4802, 4803, 4804, 4805, 4806, 4807, 4808, 4809, 4810, 4811, 4812, 4813, 4814, 4816, 4817, 4818, 4819, 4820, 4821, 4822, 4823, 4901, 4902, 4903, 4904, 4905, 4906, 4907, 4908, 4909, 4910, 4911, 5001, 5002, 5003, 5004, 5005, 5006, 5007, 5101, 5102, 5103, 5104, 5105, 5106, 5107, 5108, 5109, 5110, 5111, 5112, 5113, 5201, 5202, 5203, 5204, 5205, 5206, 5207, 5208, 5209, 5210, 5211, 5212, 5301, 5302, 5303, 5305, 5306, 5307, 5308, 5309, 5310, 5311, 5401, 5402, 5403, 5404, 5405, 5406, 5407, 5408, 5501, 5502, 5503, 5504, 5505, 5506, 5507, 5508, 5509, 5510, 5511, 5512, 5513, 5514, 5515, 5516, 5601, 5602, 5603, 5604, 5605, 5606, 5607, 5608, 5609, 5701, 5702, 5703, 5704, 5705, 5801, 5802, 5803, 5804, 5805, 5806, 5807, 5808, 5809, 5810, 5811, 5901, 5902, 5903, 5904, 5905, 5906, 5907, 5908, 5909, 5910, 5911, 6001, 6002, 6003, 6004, 6005, 6006, 6101, 6102, 6103, 6104, 6105, 6106, 6107, 6108, 6109, 6110, 6111, 6112, 6113, 6114, 6115, 6116, 6117, 6201, 6202, 6203, 6204, 6205, 6206, 6207, 6208, 6209, 6210, 6211, 6212, 6213, 6214, 6215, 6216, 6217, 6301, 6302, 6303, 6304, 6305, 6306, 6307, 6308, 6309, 6310, 6401, 6402, 6403, 6404, 6405, 6406, 6501, 6502, 6504, 6505, 6506, 6507, 6601, 6602, 6603, 6701, 6702, 6703, 6704, 6801, 6802, 6803, 6804, 6805, 6806, 6807, 6808, 6809, 6810, 6811, 6812, 6813, 6814, 6815, 6901, 6902, 6903, 6904, 6905, 6906, 6907, 6909, 6910, 6911, 6912, 6913, 6914, 7001, 7002, 7003, 7004, 7005, 7006, 7007, 7008, 7009, 7010, 7011, 7013, 7014, 7015, 7016, 7017, 7018, 7019, 7020, 7101, 7102, 7103, 7104, 7105, 7106, 7107, 7108, 7109, 7110, 7111, 7112, 7113, 7114, 7115, 7116, 7117, 7118, 7201, 7202, 7203, 7204, 7205, 7206, 7207, 7208, 7209, 7210, 7211, 7212, 7213, 7214, 7215, 7216, 7217, 7218, 7219, 7220, 7221, 7222, 7223, 7224, 7225, 7226, 7227, 7228, 7229, 7301, 7302, 7303, 7304, 7305, 7306, 7307, 7308, 7309, 7310, 7311, 7312, 7313, 7314, 7315, 7316, 7317, 7318, 7319, 7320, 7321, 7322, 7323, 7324, 7325, 7326, 7401, 7402, 7403, 7404, 7405, 7406, 7407, 7408, 7409, 7410, 7411, 7412, 7413, 7415, 7418, 7419, 7501, 7502, 7503, 7504, 7505, 7506, 7507, 7508, 7601, 7602, 7603, 7604, 7605, 7606, 7607, 7608, 7609, 7610, 7611, 7612, 7613, 7614, 7615, 7616, 7801, 7802, 7804, 7806, 7901, 7902, 7903, 7904, 7905, 7907, 8001, 8002, 8003, 8007, 8101, 8102, 8103, 8104, 8105, 8106, 8107, 8108, 8109, 8110, 8111, 8112, 8113, 8201, 8202, 8203, 8204, 8205, 8206, 8207, 8208, 8209, 8210, 8211, 8212, 8213, 8214, 8215, 8301, 8302, 8303, 8304, 8305, 8306, 8307, 8308, 8309, 8310, 8311, 8401, 8402, 8403, 8404, 8405, 8406, 8407, 8408, 8409, 8410, 8411, 8412, 8413, 8414, 8415, 8416, 8417, 8418, 8419, 8420, 8421, 8422, 8423, 8424, 8425, 8426, 8427, 8428, 8429, 8430, 8431, 8432, 8433, 8434, 8435, 8436, 8437, 8438, 8439, 8440, 8441, 8442, 8443, 8444, 8445, 8446, 8447, 8448, 8449, 8450, 8451, 8452, 8453, 8454, 8455, 8456, 8457, 8458, 8459, 8460, 8461, 8462, 8463, 8464, 8465, 8466, 8467, 8468, 8470, 8471, 8472, 8473, 8474, 8475, 8476, 8477, 8478, 8479, 8480, 8481, 8482, 8483, 8484, 8486, 8487, 8501, 8502, 8503, 8504, 8505, 8506, 8507, 8508, 8509, 8510, 8511, 8512, 8513, 8514, 8515, 8516, 8517, 8518, 8519, 8521, 8522, 8523, 8525, 8526, 8527, 8528, 8529, 8530, 8531, 8532, 8533, 8534, 8535, 8536, 8537, 8538, 8539, 8540, 8541, 8542, 8543, 8544, 8545, 8546, 8547, 8548, 8601, 8602, 8603, 8604, 8605, 8606, 8607, 8608, 8609, 8701, 8702, 8703, 8704, 8705, 8706, 8707, 8708, 8709, 8710, 8711, 8712, 8713, 8714, 8715, 8716, 8801, 8802, 8803, 8804, 8805, 8901, 8902, 8903, 8904, 8905, 8906, 8907, 8908, 9001, 9002, 9003, 9004, 9005, 9006, 9007, 9008, 9010, 9011, 9012, 9013, 9014, 9015, 9016, 9017, 9018, 9019, 9020, 9021, 9022, 9023, 9024, 9025, 9026, 9027, 9028, 9029, 9030, 9031, 9032, 9033, 9101, 9102, 9103, 9104, 9105, 9106, 9107, 9108, 9109, 9110, 9111, 9112, 9113, 9114, 9201, 9202, 9205, 9206, 9207, 9208, 9209, 9301, 9302, 9303, 9304, 9305, 9306, 9307, 9401, 9402, 9403, 9404, 9405, 9406, 9503, 9504, 9505, 9506, 9507, 9508, 9601, 9602, 9603, 9604, 9605, 9606, 9607, 9608, 9609, 9610, 9611, 9612, 9613, 9614, 9615, 9616, 9617, 9618, 9619, 9620, 9701, 9702, 9703, 9704, 9705, 9706, 9801, 9803";
        List<String> numList = Lists.newArrayList(s.split(","));
//        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism","30");
//        System.out.println(numList.toString());
        List<String> allList = Lists.newArrayList();
        numList.forEach(num->{
            String data = num.toString();
            data = data.trim();
            try {
                allList.addAll(jsoupTwo("http://hs.bianmachaxun.com/query/code.php?word=" + (data.length() < 4 ? "0" + data : data)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
//        for (String num : numList) {
//            String data = num.toString();
//            data = data.trim();
//            allList.addAll(jsoupTwo("http://hs.bianmachaxun.com/query/code.php?word=" + (data.length() < 4 ? "0" + data : data)));
//        }
//        List<String> ret = jsoupTwo("111");
        List<Map<String,Object>> listMap = Lists.newArrayList();
        allList.parallelStream().forEach(
                url->{
                    try {
                        Map<String,Object> map = jsoupThree(url);
                        if(map==null)return;
                        listMap.add( map);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
//        Map<String,Object> ret = jsoupThree("111");
        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream("D:\\卡卡学习文件\\1111.xlsx");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        writeExcel(listMap,fileOut);
//        System.out.println(ret);

    }

    /**
     * 把内容写入Excel
     * @param list 传入要写的内容，此处以一个List内容为例，先把要写的内容放到一个list中
     * @param outputStream 把输出流怼到要写入的Excel上，准备往里面写数据
     */
    public static void writeExcel(List<Map<String,Object>> list, FileOutputStream outputStream) {
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
        xssfCell.setCellValue((String) "详情地址"); //设置单元格内容

        xssfSheet.setColumnWidth(xssfCell.getColumnIndex(), 300 * 50);
        xssfCell = xssfRow.createCell(1); //创建单元格
        xssfCell.setCellValue((String) "详情编码"); //设置单元格内容
        xssfSheet.setColumnWidth(xssfCell.getColumnIndex(), 60 * 50);
        xssfCell = xssfRow.createCell(2); //创建单元格
        xssfCell.setCellValue((String) "中文解释"); //设置单元格内容
        xssfSheet.setColumnWidth(xssfCell.getColumnIndex(), 60 * 50);
        xssfCell = xssfRow.createCell(3); //创建单元格
        xssfCell.setCellValue((String) "英文解释"); //设置单元格内容
        xssfSheet.setColumnWidth(xssfCell.getColumnIndex(), 100 * 50);

        //把List里面的数据写到excel中
        for (int i = 1; i <= list.size(); i++) {
            //从第一行开始写入
            xssfRow = xssfSheet.createRow(i);
            //创建每个单元格Cell，即列的数据
            Map<String,Object> sub_list = list.get(i-1);
            xssfCell = xssfRow.createCell(0); //创建单元格
            xssfCell.setCellValue((String) sub_list.get("url")); //设置单元格内容
            xssfCell = xssfRow.createCell(1); //创建单元格
            xssfCell.setCellValue((String) sub_list.get("code")); //设置单元格内容
            xssfCell = xssfRow.createCell(2); //创建单元格
            xssfCell.setCellValue((String) sub_list.get("china")); //设置单元格内容
            xssfCell = xssfRow.createCell(3); //创建单元格
            xssfCell.setCellValue((String) sub_list.get("english")); //设置单元格内容
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

    public static List<Integer> jsoupFour(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).userAgent("Mozilla").get();//模拟火狐浏览器
        } catch (IOException e) {
            e.printStackTrace();
        }
        //这里根据在网页中分析的类选择器来获取电影列表所在的节点
        Elements div = doc.getElementsByClass("layui-colla-content");
        Elements hList = doc.getElementsByTag("h2");
        //拿到全部章节
//        for (Element h : hList) {
//            System.out.println(h.text());
//        }
        Set<Integer> a = new HashSet<>();
        for (Element h : div) {
            //获取Document的所有元素
//            System.out.println("************"+h.getAllElements());
            for(Element data:h.getElementsByClass("layui-colla-item")){
                String d = data.text().replaceAll("\\[子目注释\\]      \\[详情\\] ",";").replaceAll("\\[章注\\]","").replaceAll("\\s*", "");
//                System.out.println("************"+data.text());
                System.out.println("************"+d);
            }

        }
        List<Integer> b = Lists.newArrayList(a);
        Collections.sort(b);
        return b;
    }

    public static Map<String,Object> jsoupThree(String url) throws InterruptedException {
        Thread.sleep(500l);
//        url = "http://hs.bianmachaxun.com/query/detail.php?word=0101210010";
        Document doc = null;
        try {
            doc = Jsoup.connect(url).userAgent("Mozilla").get();//模拟火狐浏览器
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(doc==null)Thread.sleep(10000l);
        if(doc==null)return null;
        Elements div = doc.getElementsByClass("layui-tab-content");
//        System.out.println(doc.getAllElements());
        Elements table = div.select("table");//查找table标签
        Elements tr = table.select("tr");
        Map<String,Object> map = Maps.newHashMap("url",url);
        for(int i=0;i<tr.size();i++){
            Elements nei = tr.get(i).select("td");
            Element ts = nei.get(1);
            switch (i){
                case 0:map.put("code",ts.text());
                case 1:map.put("china",ts.text());
                case 2:map.put("english",ts.text());
                default:break;
            }

        }
//        for(Element tb : tr){
//            System.out.println("********"+tb.getAllElements());




//                System.out.println(i+"::::::"+ts.text());

//        }
        return map;
    }

    public static List<String> jsoupTwo(String url) throws InterruptedException {
        Thread.sleep(1000l);
//        url = "http://hs.bianmachaxun.com/query/code.php?word=0101";
        Document doc = null;
        try {
            doc = Jsoup.connect(url).userAgent("Mozilla").get();//模拟火狐浏览器
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> ret = Lists.newArrayList();

        Elements div = doc.getElementsByAttributeValueContaining("href","query/detail.php?word=");
//        System.out.println(doc.getAllElements());
//        System.out.println(doc.getElementsByAttributeValueContaining("href","/query/detail.php"));
        //获取电影列表
        Elements table = div.select("a");//查找table标签
        for(Element tb : table){
            String videos = tb.getElementsByAttributeValueContaining("href","query/detail.php?word=").get(0).attr("href");
            System.out.println(videos);
            ret.add("http://hs.bianmachaxun.com"+videos);
        }
        return ret;
    }

    public static List<Integer> jsoup(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).userAgent("Mozilla").get();//模拟火狐浏览器
        } catch (IOException e) {
            e.printStackTrace();
        }
        //这里根据在网页中分析的类选择器来获取电影列表所在的节点
        Elements div = doc.getElementsByClass("layui-colla-content");
        Set<Integer> a = new HashSet<>();
        for (Element tb : div) {
            Set<Integer> num = getNumbers(tb.text().replaceAll("\\s*", ""));
            a.addAll(num);
        }
        List<Integer> b = Lists.newArrayList(a);
        Collections.sort(b);
        return b;
    }

    public static Set<Integer> getNumbers(String content) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(content);
        Set<Integer> ret = new HashSet<>();
        while (matcher.find()) {
            if (matcher.group(0).length() == 4) ret.add(Integer.valueOf(matcher.group(0)));
        }
        return ret;
    }
}
