package org.scaffold.mybatis.pageHelper;

import org.scaffold.mybatis.generator.MongoReflectionIgnore;
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
    @MongoReflectionIgnore
    private static final long serialVersionUID = 1L;

    /**
     * 页码，从1开始
     */
    @XMLReflectionIgnore
    @MongoReflectionIgnore
    private int pageNum = 1;

    /**
     * 页码，从1开始
     */
    @XMLReflectionIgnore
    @MongoReflectionIgnore
    private int pageNo = 1;

    /**
     * 页码，从1开始
     */
    @XMLReflectionIgnore
    @MongoReflectionIgnore
    private int pageCurrent = 1;
    /**
     * 页面大小
     */
    @XMLReflectionIgnore
    @MongoReflectionIgnore
    private int pageSize = -1;
    /**
     * 分页合理化,null时用默认配置
     */
    @XMLReflectionIgnore
    @MongoReflectionIgnore
    private Boolean reasonable;
    /**
     * true且pageSize=0时返回全部结果，false时分页,null时用默认配置
     */
    @XMLReflectionIgnore
    @MongoReflectionIgnore
    private Boolean pageSizeZero;
    /**
     * 排序字段 为null则默认排序
     */
    @XMLReflectionIgnore
    @MongoReflectionIgnore
    private String orderBy;

    @XMLReflectionIgnore
    @MongoReflectionIgnore
    private long total;

    @XMLReflectionIgnore
    @MongoReflectionIgnore
    private long totalRow;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageCurrent() {
        return pageCurrent;
    }

    public void setPageCurrent(int pageCurrent) {
        this.pageCurrent = pageCurrent;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
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

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(long totalRow) {
        this.totalRow = totalRow;
    }
}
