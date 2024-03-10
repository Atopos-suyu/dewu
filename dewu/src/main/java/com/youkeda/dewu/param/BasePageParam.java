package com.youkeda.dewu.param;

/**
 * @Author songchuanming
 * @DATE 2020/7/15.
 */

 //外部代码可以使用这些方法来获取和修改 BasePageParam 对象的属性值，而不需要直接访问类的属性
public class BasePageParam {
    /**
     * 页数
     */
    private int pagination = 0;

    /**
     * 每页数量
     */
    private int pageSize = 10;

    public int getPagination() {
        return pagination;
    }

    public void setPagination(int pagination) {
        this.pagination = pagination;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

}
