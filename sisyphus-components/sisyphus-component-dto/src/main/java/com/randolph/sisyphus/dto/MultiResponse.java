package com.randolph.sisyphus.dto;

import java.io.Serial;
import java.util.*;

/**
 * Response with batch record to return,
 * usually use in conditional query
 * <p/>
 * Created by Danny.Lee
 * @author <a href = "mailto:randolph_lu@163.com">randolph<a/>
 * @since 5.0.0
 */
public class MultiResponse<T> extends Response {
    @Serial
    private static final long serialVersionUID = 1L;

    private Collection<T> data;

    public List<T> getData() {
        if (null == data) {
            return Collections.emptyList();
        }
        if (data instanceof List) {
            return (List<T>) data;
        }
        return new ArrayList<>(data);
    }

    public void setData(Collection<T> data) {
        this.data = data;
    }

    public boolean isEmpty() {
        return data == null || data.isEmpty();
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public static <T> MultiResponse<T> fastFailure(String errCode, String errMessage) {
        MultiResponse<T> response = new MultiResponse<>();
        response.setSuccess(false);
        response.setErrCode(errCode);
        response.setErrMessage(errMessage);
        return response;
    }

    public static <T> MultiResponse<T> of(Collection<T> data) {
        MultiResponse<T> response = new MultiResponse<>();
        response.setSuccess(true);
        response.setData(data);
        return response;
    }
}
