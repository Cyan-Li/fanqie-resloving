package com.licyan.pojo;

import lombok.Data;

import java.util.List;

/**
 * 小说信息
 */
@Data
public class NovelInfo {

    /**
     * 小说名称
     */
    private String novelTitle;

    /**
     * 小说简介
     */
    private String novelDesc;

    /**
     * 作者信息
     */
    private AuthorInfo authorInfo;

    /**
     * 章节信息
     */
    private List<ChapterInfo> chapterInfos;

}
