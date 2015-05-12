package com.cdmcs.qx2cdjw.inf;

import com.cdmcs.qx2cdjw.bean.ColumnBean;

import java.util.List;

/**��̬������
 * Created by cdmcs on 2015/3/28.
 */
public interface DynamicBind {
    /**
     * ��ʽ�����ݣ���Ҫ�ǰ�ColumnBean����value���ã�������ݰ�
     * @param columns ��
     * @param data ��Ҫ��ʽ��������
     * @return ��ʽ���õ�����
     */
    public List<List<ColumnBean>> process(List<ColumnBean> columns,Object data);

    /**
     * ��ȡ��չ����
     * @param requiredType
     * @param <T>
     * @return
     */
    public <T> T getExtData(java.lang.Class<T> requiredType);
}
