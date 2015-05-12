package com.cdmcs.qx2cdjw.inf;

import com.cdmcs.qx2cdjw.bean.ColumnBean;

import java.util.List;

/**动态绑定数据
 * Created by cdmcs on 2015/3/28.
 */
public interface DynamicBind {
    /**
     * 格式化数据，主要是把ColumnBean对象value设置，完成数据绑定
     * @param columns 列
     * @param data 需要格式化的数据
     * @return 格式化好的数据
     */
    public List<List<ColumnBean>> process(List<ColumnBean> columns,Object data);

    /**
     * 获取扩展数据
     * @param requiredType
     * @param <T>
     * @return
     */
    public <T> T getExtData(java.lang.Class<T> requiredType);
}
