package com.licyan;

import com.licyan.enums.MainMenuEnum;
import com.licyan.exception.MyIOException;
import com.licyan.operate.Download;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLOutput;
import java.util.Scanner;

@Slf4j
public class FanqieResloving {

    public static void main(String[] args) {
        System.out.println("******   番茄小说下载小工具启动成功   ******");
        System.out.println("******   欢迎使用番茄小说下载小工具   ******");
        // System.out.println("用户操作会记录日志存放在启动器旁边，用户可自行删除/查看");

        while (true) {

            System.out.println(MainMenuEnum.FIRST.getCode() + ". " + MainMenuEnum.FIRST.getOperate());
            System.out.println(MainMenuEnum.SECOND.getCode() + ". " + MainMenuEnum.SECOND.getOperate());
            System.out.println(MainMenuEnum.THIRD.getCode() + ". " + MainMenuEnum.THIRD.getOperate());
            System.out.println(MainMenuEnum.FOURTH.getCode() + ". " + MainMenuEnum.FOURTH.getOperate());
            System.out.println(MainMenuEnum.END.getCode() + ". " + MainMenuEnum.END.getOperate());

            System.out.println();
            System.out.println("请选择要进行的操作，输入序号即可");

            // 接收用户输入的序号
            Scanner scanner = new Scanner(System.in);
            int operateNo = scanner.nextInt();

            if (operateNo == MainMenuEnum.FIRST.getCode()) {

                // 调用单本解析
                Download download = new Download();
                try {

                    System.out.println("请输入要解析的番茄小说地址栏中的内容");
                    System.out.println("例如 https://fanqienovel.com/page/7077516958534470656 的格式");

                    // 接收用户输入的网址，进行解析
                    Scanner scannerOne = new Scanner(System.in);
                    String novelUrl = scanner.next();

                    download.downloadOne(novelUrl);

                } catch (MyIOException e) {
                    System.out.println("小说解析下载异常，异常信息为：" + e.getMessage());
                    throw new RuntimeException(e);
                }

                System.out.println("单本下载完成，请继续选择操作");
                System.out.println();

            } else if (operateNo == MainMenuEnum.SECOND.getCode()) {
                System.out.println("批量解析");

                Download download = new Download();
                try {
                    download.downloadBatch();
                } catch (MyIOException e) {
                    System.out.println("小说解析下载异常，异常信息为：" + e.getMessage());
                    throw new RuntimeException(e);
                }

                System.out.println("批量下载完成，请继续选择操作");
                System.out.println();

            } else if (operateNo == MainMenuEnum.THIRD.getCode()) {

            } else if (operateNo == MainMenuEnum.FOURTH.getCode()) {

            } else if (operateNo == MainMenuEnum.END.getCode()) {
                System.out.println("感谢您的使用，再见");
                // 暂停一秒
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.exit(0);


            }
        }


        // public static void main(String[] args) throws InterruptedException {
        //
        //     Scanner scanner = new Scanner(System.in);
        //
        //     // 程序启动
        //     System.out.println("****** 番茄小说下载小工具启动成功 ******");
        //     System.out.println("请选择要进行的操作，输入序号即可");
        //
        //     while (true) {
        //         // 主菜单
        //         System.out.println(MainMenuEnum.FIRST.getCode() + "." + MainMenuEnum.FIRST.getOperate());
        //         System.out.println(MainMenuEnum.SECOND.getCode() + "." + MainMenuEnum.SECOND.getOperate());
        //         System.out.println(MainMenuEnum.THIRD.getCode() + "." + MainMenuEnum.THIRD.getOperate());
        //         System.out.println(MainMenuEnum.FOURTH.getCode() + "." + MainMenuEnum.FOURTH.getOperate());
        //         System.out.println(MainMenuEnum.END.getCode() + "." + MainMenuEnum.END.getOperate());
        //
        //         System.out.println("请选择操作的序号");
        //         int i = scanner.nextInt();
        //
        //         if (i == MainMenuEnum.FIRST.getCode()) {
        //             while (true) {
        //                 Scanner scanner1 = new Scanner(System.in);
        //                 System.out.println("请输入要解析的小说的网页地址");
        //                 String url = scanner1.nextLine();
        //
        //                 DownloadOne d = new DownloadOne();
        //                 try {
        //                     d.downloadOne(url);
        //                 } catch(RuntimeException runtimeException){
        //                     System.out.println("解析失败，失败原因为：" + runtimeException.getMessage());
        //                     System.out.println("请输入 1 返回上一级");
        //
        //                     int userInput = scanner1.nextInt();
        //                     if (userInput == 1) {
        //                         continue;
        //                     } else {
        //                         System.out.println("输入错误，程序退出");
        //                         System.exit(0);
        //                     }
        //                 }catch (Exception e) {
        //                     e.printStackTrace();
        //                     System.out.println("过程出现异常，异常信息为：" + e.getMessage());
        //                     System.out.println("请输入 1 返回上一级");
        //
        //                     int userInput = scanner1.nextInt();
        //                     if (userInput == 1) {
        //                         continue;
        //                     } else {
        //                         System.out.println("输入错误，程序退出");
        //                         System.exit(0);
        //                     }
        //                 }
        //                 System.out.println("下载完成，请继续选择操作");
        //                 System.out.println(DownloadOneMenuEnum.FIRST.getCode() + "." + DownloadOneMenuEnum.FIRST.getOperate());
        //                 System.out.println(DownloadOneMenuEnum.END.getCode() + "." + DownloadOneMenuEnum.END.getOperate());
        //
        //                 int userInput = scanner1.nextInt();
        //                 if (userInput == DownloadOneMenuEnum.FIRST.getCode()) {
        //                     continue;
        //                 } else if (userInput == DownloadOneMenuEnum.END.getCode()) {
        //                     break;
        //                 }
        //             }
        //         }else if(i == MainMenuEnum.SECOND.getCode()){
        //
        //             // System.out.println("批量解析功能暂未开放，敬请期待");
        //             DownloadBatch downloadBatch = new DownloadBatch();
        //             downloadBatch.downloadBatch();
        //
        //         }else if(i == MainMenuEnum.THIRD.getCode()){
        //             System.out.println("更新小说功能暂未开放，敬请期待");
        //         }else if(i == MainMenuEnum.FOURTH.getCode()){
        //             System.out.println("批量更新小说功能暂未开放，敬请期待");
        //         } else if (i == MainMenuEnum.END.getCode()) {
        //             System.out.println("感谢您的使用，再见");
        //             // 暂停一秒
        //             try {
        //                 Thread.sleep(1000);
        //             } catch (InterruptedException e) {
        //                 e.printStackTrace();
        //             }
        //             System.exit(0);
        //         } else {
        //             System.out.println("无效的选择，请重新输入");
        //         }
        //     }
        // }
    }
}
