package com.qimiaochong.entity;

import org.apache.ibatis.type.Alias;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

@Alias("Topic")
public class Topic implements Serializable {

    @Id
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private Integer likeCount;
    private Long labelId;
    private Date createdAt;
    private Date banAt;
    private Date deletedAt;
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Long getLabelId() {
        return labelId;
    }

    public void setLabelId(Long labelId) {
        this.labelId = labelId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getBanAt() {
        return banAt;
    }

    public void setBanAt(Date banAt) {
        this.banAt = banAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
