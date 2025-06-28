package com.randolph.sisyphus.dto;

import java.io.Serial;
import java.util.*;

/**
 * Response with batch page record to return,
 * usually use in page query
 * <p/>
 * Created by xiaochu.lbj
 * @author <a href = "mailto:randolph_lu@163.com">randolph<a/>
 * @since 5.0.0
 */
public class PageResponse<T> extends Response {
    @Serial
    private static final long serialVersionUID = 1L;

    private int totalCount = 0;

    private int pageIndex = 1;

    private int pageSize = 1;

    private Collection<T> data;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageIndex() {
        return Math.max(pageIndex, 1);
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = Math.max(pageIndex, 1);
    }

    public int getPageSize() {
        return Math.max(pageSize, 1);
    }

    public void setPageSize(int pageSize) {
        this.pageSize = Math.max(pageSize, 1);
    }

    public Collection<T> getData() {
        if (Objects.isNull(data)) {
            return Collections.emptyList();
        }else if (data instanceof List<T>){
            return data;
        }
        return new ArrayList<>(data);
    }

    public void setData(Collection<T> data) {
        this.data = data;
    }

    public static <T> PageResponse<T> fastFailure(String errCode, String errMessage) {
        PageResponse<T> response = new PageResponse<>();
        response.setSuccess(false);
        response.setErrCode(errCode);
        response.setErrMessage(errMessage);
        return response;
    }

    public static <T> PageResponse<T> of (int pageSize, int pageIndex){
        return of(Collections.emptyList(), 0, pageSize, pageIndex);
    }

    public static <T> PageResponse<T> of (Collection<T> data, int totalCount, int pageSize, int pageIndex) {
        PageResponse<T> response = new PageResponse<>();
        response.setSuccess(true);
        response.setData(data);
        response.setPageIndex(pageIndex);
        response.setPageSize(pageSize);
        response.setTotalCount(totalCount);
        return response;
    }
}
