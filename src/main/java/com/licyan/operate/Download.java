package com.licyan.operate;

import com.licyan.exception.MyConnectException;
import com.licyan.exception.MyIOException;
import com.licyan.pojo.AuthorInfo;
import com.licyan.pojo.ChapterInfo;
import com.licyan.pojo.HttpHeader;
import com.licyan.pojo.NovelInfo;
import com.licyan.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class Download {

    /**
     * 章节下载api地址
     */
    private String chapterDownloadApi = "https://novel.snssdk.com/api/novel/book/reader/full/v1/";

    public void downloadOne(String novelUrl) throws MyIOException {

        // 判断url是否符合标准
        checkurl(novelUrl);

        // 解析小说id
        String novelId = getNovelId(novelUrl);

        NovelInfo novelInfo;
        // 获取小说基本信息
        try {
            novelInfo = getNovelInfo(novelUrl);
        } catch (MyIOException e) {
            throw new MyIOException("小说信息解析异常……");
        }

        List<ChapterInfo> chapterInfos = novelInfo.getChapterInfos();

        // String chapterAll = getChapter(chapterInfos, novelId);


        // 小说基本信息
        String novelInfoTxt = novelInfo.getNovelTitle() + "\n\n"
                + novelInfo.getAuthorInfo().getAuthorName() + "\n"
                + novelInfo.getAuthorInfo().getAuthorDesc() + "\n"
                + novelInfo.getNovelDesc() + "\n\n\n\n";

        // 创建小说txt文件，写入内容
        String path = System.getProperty("user.dir");
        File file = new File(path + "\\" + novelInfo.getNovelTitle() + ".txt");
        // 判断文件是否存在，不存在则创建
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new MyIOException("文件创建失败……");
            }
        }

        // 小说信息写入文件
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(novelInfoTxt.getBytes());

            int index = 1;
            // 获取单章文本
            for (ChapterInfo chapterInfo : chapterInfos) {
                String chapterContent = getChapterContent(index, chapterInfos.size(), novelId, chapterInfo);
                fileOutputStream.write(chapterContent.getBytes());
                index++;
            }
            fileOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 提示用户下载成功
        System.out.println("下载成功");

    }

    /**
     * 获取章节内容
     *
     * @return
     */
    public String getChapterContent(int index,
                                    int maxSize,
                                    // String title,
                                    // HttpURLConnection httpURLConnection,
                                    String novelId,
                                    ChapterInfo chapterInfo) throws MyIOException {


        String device_platform = "android";
        String parent_enterfrom = "novel_channel_search.tab.";
        String aid = "2329";
        String platform_id = "1";

        String url = chapterDownloadApi
                + "?device_platform=" + device_platform
                + "&parent_enterfrom=" + parent_enterfrom
                + "&aid=" + aid
                + "&platform_id=" + platform_id
                + "&group_id=" + novelId
                + "&item_id=" + chapterInfo.getChapterId();

        // 发起请求
        HttpUtils httpUtils = new HttpUtils();
        HttpHeader httpHeader = new HttpHeader();
        Map<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("User-Agent", "user_agent");
        httpHeader.setHeaderMap(stringStringMap);

        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = httpUtils.get(url, httpHeader);
        } catch (MyConnectException e) {
            System.out.println("获取失败，失败原因为：" + e.getMessage());
        } catch (ProtocolException e) {
            System.out.println("获取失败");
        }

        String result = "";

        Document document = null;
        try {
            document = Jsoup.parse(httpURLConnection.getInputStream(), "UTF-8", url);


            // 获取章节内容
            // 定义正则表达式模式
            String patternString = "<article>([\\s\\S]*?)</article>";
            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = pattern.matcher(document.toString());
            if (matcher.find()) {
                String chapterText = matcher.group(1);
                chapterText = chapterText.replace("<p>", "  ").replace("</p>", "\n");
                result = result + chapterInfo.getTitle() + "\n" + chapterText + "\n" + "\n" + "\n";
                System.out.print("获取成功：" + chapterInfo.getTitle() + "               ");
                System.out.println("下载进度：" + index + "/" + maxSize);
            } else {
                throw new RuntimeException("解析章节内容失败");
            }

        } catch (IOException e) {
            throw new MyIOException("文件解析异常");
        }

        return result;

    }


    /**
     * 获取小说基本信息
     */
    private NovelInfo getNovelInfo(String novelUrl) throws MyIOException {

        HttpHeader httpHeader = new HttpHeader();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("User-Agent", "user_agent");
        httpHeader.setHeaderMap(stringStringHashMap);

        HttpUtils httpUtils = new HttpUtils();
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = httpUtils.get(novelUrl, null);
        } catch (MyConnectException e) {
            System.out.println("获取失败，失败原因为：" + e.getMessage());
        } catch (ProtocolException e) {
            System.out.println("获取失败");
        }

        Document document;
        // 解析小说信息
        try {
            assert httpURLConnection != null;
            document = Jsoup.parse(httpURLConnection.getInputStream(), "UTF-8", novelUrl);
        } catch (IOException e) {
            throw new MyIOException("小说信息解析异常……");
        }

        // TODO:解析小说信息

        NovelInfo novelInfo = new NovelInfo();
        AuthorInfo authorInfo = new AuthorInfo();

        // Elements divElements = document.select("div.chapter-item");
        // String allTitle = document.select("h1").first().text();
        // 提取小说标题
        String novelTitle = Objects.requireNonNull(document.select("h1").first()).text();
        // 提取作者名称

        // 提取作者座右铭

        // 提取章节信息
        List<ChapterInfo> chapterInfos = new ArrayList<>();

        Elements divElements = document.select("div.chapter-item");
        // System.out.println(!divElements.isEmpty());
        if (!divElements.isEmpty()) {

            for (Element element : divElements) {
                ChapterInfo chapterInfo = new ChapterInfo();
                chapterInfo.setTitle(element.text());

                String chapterTmpId = Objects.requireNonNull(element.selectFirst("a")).attr("href");
                chapterTmpId = chapterTmpId.replace("/reader/", "");

                chapterInfo.setChapterId(chapterTmpId);

                chapterInfos.add(chapterInfo);
            }
        }

        novelInfo.setNovelTitle(novelTitle);
        novelInfo.setAuthorInfo(authorInfo);
        novelInfo.setNovelDesc("小说简介");
        novelInfo.setChapterInfos(chapterInfos);

        return novelInfo;

    }

    /**
     * 解析小说id
     */
    private String getNovelId(String novelUrl) {

        String novelId;

        if (novelUrl.contains("?")) {
            return novelUrl.substring(novelUrl.lastIndexOf("/") + 1, novelUrl.indexOf("?"));
        } else {
            return novelUrl.substring(novelUrl.lastIndexOf("/") + 1);
        }

    }

    /**
     * 判断url是否符合标准
     */
    private void checkurl(String novelUrl) throws MyIOException {

        try {
            new URL(novelUrl);
        } catch (Exception e) {
            System.out.println("输入的网址不符合规范，请重新输入");
            downloadOne(novelUrl);
        }

    }

    /**
     * 批量下载小说方法
     */
    public void downloadBatch() throws MyIOException {
        System.out.println("请将要下载的小说的网址放入urls.txt文件中，每行一个网址");
        // String property = System.getProperty("user.dir");
        File file = new File(System.getProperty("user.dir") + "\\urls.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                // throw new RuntimeException(e);
                throw new MyIOException("文件创建失败……");
            }
        }

        System.out.println("如已操作完成，请输入1开始下载");
        while (true) {

            int next = new Scanner(System.in).nextInt();
            // 等待用户输入1，程序开始读取文件

            if (1 == next) {
                List<String> urls = new ArrayList<>();

                try {
                    // 读取urls.txt文件内容，按照换行符将内容读取为一个集合
                    FileReader fileReader = null;

                    fileReader = new FileReader(file.getPath());

                    BufferedReader bufferedReader = new BufferedReader(fileReader);

                    String urlEach = null;
                    while ((urlEach = bufferedReader.readLine()) != null) {
                        urls.add(urlEach);
                    }
                    bufferedReader.close();
                    ;
                    fileReader.close();

                } catch (IOException e) {
                    System.out.println("文件读取失败……");
                    throw new MyIOException("文件读取失败……");
                }

                for (String url : urls) {

                    try {
                        downloadOne(url);
                    } catch (MyIOException e) {
                        System.out.println("下载失败: " + url);
                    }

                }
                break;

            } else {
                System.out.println("输入错误，请重新输入");

            }
        }


    }

}
