package com.erian.restexample.springmvc.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Rambo Zhu<asybzhu@gmail.com>
 *
 */
public class CommentDetails implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long id;

    private String content;

    private Date createdDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "CommentDetails{" + "id=" + id + ", content=" + content + ", createdDate=" + createdDate + '}';
    }

}
