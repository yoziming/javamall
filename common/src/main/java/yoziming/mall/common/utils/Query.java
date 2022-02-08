/**
 * Copyright (c) 2016-2019 人人開源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版權所有，侵權必究！
 */

package yoziming.mall.common.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;
import yoziming.mall.common.xss.SQLFilter;

import java.util.Map;

/**
 * 查詢參數
 *
 * @author Mark sunlightcs@gmail.com
 */
public class Query<T> {

    public IPage<T> getPage(Map<String, Object> params) {
        return this.getPage(params, null, false);
    }

    public IPage<T> getPage(Map<String, Object> params, String defaultOrderField, boolean isAsc) {
        //分頁參數
        long curPage = 1;
        long limit = 10;

        if (params.get(Constant.PAGE) != null) {
            curPage = Long.parseLong((String) params.get(Constant.PAGE));
        }
        if (params.get(Constant.LIMIT) != null) {
            limit = Long.parseLong((String) params.get(Constant.LIMIT));
        }

        //分頁物件
        Page<T> page = new Page<>(curPage, limit);

        //分頁參數
        params.put(Constant.PAGE, page);

        //排序字段
        //防止SQL注入（因為sidx、order是通過拼接SQL實現排序的，會有SQL注入風險）
        String orderField = SQLFilter.sqlInject((String) params.get(Constant.ORDER_FIELD));
        String order = (String) params.get(Constant.ORDER);

        //前端字段排序
        if (StringUtils.isNotEmpty(orderField) && StringUtils.isNotEmpty(order)) {
            if (Constant.ASC.equalsIgnoreCase(order)) {
                return page.addOrder(OrderItem.asc(orderField));
            } else {
                return page.addOrder(OrderItem.desc(orderField));
            }
        }

        //沒有排序字段，則不排序
        if (StringUtils.isBlank(defaultOrderField)) {
            return page;
        }

        //預設排序
        if (isAsc) {
            page.addOrder(OrderItem.asc(defaultOrderField));
        } else {
            page.addOrder(OrderItem.desc(defaultOrderField));
        }

        return page;
    }
}
