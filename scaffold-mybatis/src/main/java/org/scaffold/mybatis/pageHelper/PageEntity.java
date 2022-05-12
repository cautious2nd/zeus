package org.scaffold.mybatis.pageHelper;

import org.scaffold.mybatis.generator.XMLReflectionIgnore;

import java.io.Serializable;

/**
 * @author ：zjd
 * @date ：Created By 2022/4/14 17:01
 * @description：${description}
 * @modified By：
 * @version: $version$
 */
public class PageEntity implements Serializable {
    @XMLReflectionIgnore
    private static final long serialVersionUID = 1L;

    /**
     * 页码，从1开始
     */
    @XMLReflectionIgnore
    private int pageNum=1;

    /**
     * 页码，从1开始
     */
    @XMLReflectionIgnore
    private int pageNo=1;
    /**
     * 页面大小
     */
    @XMLReflectionIgnore
    private int pageSize=20;
    /**
     * 分页合理化,null时用默认配置
     */
    @XMLReflectionIgnore
    private Boolean reasonable;
    /**
     * true且pageSize=0时返回全部结果，false时分页,null时用默认配置
     */
    @XMLReflectionIgnore
    private Boolean pageSizeZero;
    /**
     * 排序字段 为null则默认排序
     */
    @XMLReflectionIgnore
    private String orderBy;

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }


    public Boolean getReasonable() {
        return reasonable;
    }

    public void setReasonable(Boolean reasonable) {
        this.reasonable = reasonable;
    }

    public Boolean getPageSizeZero() {
        return pageSizeZero;
    }

    public void setPageSizeZero(Boolean pageSizeZero) {
        this.pageSizeZero = pageSizeZero;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
        this.pageNum=pageNo;
    }
}
