package com.licyan.operate;

import com.licyan.pojo.Chapter;
import com.licyan.pojo.HttpHeader;
import com.licyan.utils.HttpUtils;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class DownloadOne {

    private String chapterDownloadApi = "https://novel.snssdk.com/api/novel/book/reader/full/v1/";

    public void downloadOne(String url){
//        System.out.println("单本下载");

        //发起请求时指定返回类型
        // httpUtils.getMethod(url);
        try {
            HttpUtils httpUtils = new HttpUtils();
            HttpHeader header = new HttpHeader();
            header.setConnectTimeout(5000);
            header.setReadTimeout(5000);

            HttpURLConnection connection = httpUtils.getMethod(url,header);
            Document document = Jsoup.parse(connection.getInputStream(), "UTF-8", url);
            String allTitle = document.select("h1").first().text();
            Elements divElements = document.select("div.chapter-item");

            List<Chapter> chapters = new ArrayList<>();
            if(!divElements.isEmpty()){

                for (Element divElement : divElements) {
                    //获取标题
                    String text = divElement.text();
                    //获取链接
                    String href = divElement.selectFirst("a").attr("href");
                    href = href.replace("/reader/","");
                    Chapter chapter = new Chapter(text, href);
                    chapters.add(chapter);
                }
            }
            //获取到了所有章节的名称及链接
            String groupId = getGroupId(url);
            if(null != groupId){
                //在当前位置新建一个文件夹
                //获取当前文件路径
                String path = System.getProperty("user.dir");
                //拼接文件夹路径
                path = path + "\\" + allTitle + ".txt";
                File file = new File(path);
                if(!file.exists()){
                    file.createNewFile();
                }

                //下载章节内容
                String content = this.chapterDownload(chapters, groupId);
                //将章节内容写入文件
                //获取文件输出流
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                //写入文件
                fileOutputStream.write(content.getBytes());
                //关闭流
                fileOutputStream.close();

                System.out.println("下载完成");

            }else{
                throw new RuntimeException("获取小说id失败");
            }

        }catch (RuntimeException e) {
            log.info("请求失败,请检查网络连接");
        }catch (Exception e) {
            log.info("请求失败,失败信息为：{}",e.getMessage());
        }

    }

    /**
     * 获取小说本身的id
     * @param url
     * @return
     */
    private String getGroupId(String url) {
        // int lastIndex = url.lastIndexOf("/");
        // if(lastIndex != -1){
        //     return url.substring(lastIndex+1);
        // }else{
        //     System.out.println("获取小说id失败");
        //     return null;
        // }

        // 使用正则表达式匹配最后一个 "/" 和第一个 "?" 之间的内容
        Pattern pattern = Pattern.compile("/(.*?)(?=\\?)");
        Matcher matcher = pattern.matcher(url);

        // 查找匹配结果
        if (matcher.find()) {
            return matcher.group(1);
        }else{
            System.out.println("获取小说id失败");
            return null;
        }

    }

    private String chapterDownload(List<Chapter> chapters,String groupId) {
        String result = "";
        int MaxSize = chapters.size();
        int index = 1;
        //遍历章节列表
        for (Chapter chapter : chapters) {

            //获取章节名称
            String title = chapter.getTitle();
            //获取章节链接
            String href = chapter.getHref();
            //拼接章节链接
            String device_platform = "android";
            String parent_enterfrom = "novel_channel_search.tab.";
            String aid = "2329";
            String platform_id = "1";
            // String group_id = ;
            // String item_id = href ;
            String url = chapterDownloadApi
                    + "?device_platform=" + device_platform
                    + "&parent_enterfrom=" + parent_enterfrom
                    + "&aid=" + aid
                    + "&platform_id=" + platform_id
                    + "&group_id=" + groupId
                    + "&item_id=" + href;
            //发起请求
            try {
                HttpUtils httpUtils = new HttpUtils();
                HttpHeader header = new HttpHeader();
                Map<String, String> stringStringMap = new HashMap<>();
                stringStringMap.put("User-Agent", "user_agent");
                header.setHeaderMap(stringStringMap);

                HttpURLConnection connection = httpUtils.getMethod(url,header);
                Document document = Jsoup.parse(connection.getInputStream(), "UTF-8", url);
                //获取章节内容
                // 定义正则表达式模式
                String patternString = "<article>([\\s\\S]*?)</article>";
                Pattern pattern = Pattern.compile(patternString);
                Matcher matcher = pattern.matcher(document.toString());
                if(matcher.find()){
                    String chapterText = matcher.group(1);
                    chapterText = chapterText.replace("<p>", "  ").replace("</p>", "\n");
                    result = result + title + "\n" + chapterText + "\n" + "\n" + "\n";
                    System.out.print("获取成功：" + title + "               ");
                    System.out.println("下载进度：" + index + "/" + MaxSize);
                    index++;

                }else {
                    throw new RuntimeException("解析章节内容失败");
                }

            }catch (RuntimeException e) {
                log.info("请求失败,请检查网络连接");
            }catch (Exception e) {
                log.info("请求失败,失败信息为：{}",e.getMessage());
            }
        }
        return result;
    }

}
