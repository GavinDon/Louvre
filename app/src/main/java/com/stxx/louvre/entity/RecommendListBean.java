package com.stxx.louvre.entity;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * description: 由于需要添加头所以此类嵌套RecommendItemBean形成一个多类型布局的整体数据bean
 * Created by liNan on 2018/3/2 14:52
 */

public class RecommendListBean extends SectionEntity<ClassifyBean.RowsBean.ListBean> {
    public RecommendListBean(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public RecommendListBean(ClassifyBean.RowsBean.ListBean listBean) {
        super(listBean);
    }
}
