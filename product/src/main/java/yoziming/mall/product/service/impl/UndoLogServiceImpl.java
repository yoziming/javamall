package yoziming.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import yoziming.mall.common.utils.PageUtils;
import yoziming.mall.common.utils.Query;
import yoziming.mall.product.dao.UndoLogDao;
import yoziming.mall.product.entity.UndoLogEntity;
import yoziming.mall.product.service.UndoLogService;

import java.util.Map;

@Service("undoLogService")
public class UndoLogServiceImpl extends ServiceImpl<UndoLogDao, UndoLogEntity> implements UndoLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UndoLogEntity> page = this.page(
                new Query<UndoLogEntity>().getPage(params),
                new QueryWrapper<UndoLogEntity>()
        );

        return new PageUtils(page);
    }

}