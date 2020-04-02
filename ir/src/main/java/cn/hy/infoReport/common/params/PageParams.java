package cn.hy.infoReport.common.params;

/**
 * Created by lyw on 2017/12/8.
 * 分页参数
 */
public class PageParams {
    /**
     * 默认页数
     */
    private int pageNum = 1;
    /**
     * 默认每页个数
     */
    private int pageSize = 5;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
