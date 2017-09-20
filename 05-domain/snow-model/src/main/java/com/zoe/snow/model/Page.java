package com.zoe.snow.model;

import com.alibaba.fastjson.annotation.JSONField;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Page
 *
 * @author Dai Wenqing
 * @date 2015/10/4
 */

public class Page {
    /*int getCount();

    void setCount(int count);

    int getPage();

    void setPage(int page);

    int getPageSize();

    void setPageSize(int pageSize);

    int getPages();

    void setPageSize();*/

    //总条数据
    int count;
    //第几页
    int page;
    //每页大小
    @JSONField(name = "page_size")
    int pageSize;
    //共多少页
    int pages;

    /**
     * 获取记录总数。
     *
     * @return 记录总数。
     */
    public int getCount() {
        return count;
    }

    /*public void setCount(int count) {
        this.count = count;
    }*/

    public int getPage() {
        return page;
    }

    /**
     * 设置分页信息。
     *
     * @param count    记录总数。
     * @param pageSize 每页显示记录数。
     * @param page     当前显示页码数。
     */
    public Page setPage(int count, int page, int pageSize) {
        this.count = Math.max(0, count);
        this.pageSize = Math.max(1, pageSize);
        this.pages = this.count / this.pageSize + (this.count % this.pageSize == 0 ? 0 : 1);
        this.pages = Math.max(1, this.pages);
        //this.number = (int) Math.ceil(this.count / this.size);
        this.page = page;
        return this;
    }

    /**
     * 获取每页最大显示记录数。
     *
     * @return 每页最大显示记录数。
     */
    public int getPageSize() {
        return pageSize;
    }

    /*public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }*/

    public int getPages() {
        return pages;
    }

    /*public void setPages(int pages) {
        this.pages = pages;
    }*/
}
