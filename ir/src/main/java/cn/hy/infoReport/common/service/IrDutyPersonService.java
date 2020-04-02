package cn.hy.infoReport.common.service;

import cn.hy.infoReport.common.entity.IrDutyPerson;
import cn.hy.infoReport.common.mapper.IrDutyPersonMapper;
import cn.hy.infoReport.common.params.PageParams;
import cn.hy.infoReport.common.vo.PageInfo;
import cn.hy.infoReport.module.business.vo.IrDutyPersonVo;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;


/**
 * @author hu
 * @version 1.0
 * @date 2020/4/2 11:34
 * @description 值班表Service
 */
@Service
public class IrDutyPersonService extends  BaseService{
    @Resource
    private IrDutyPersonMapper irDutyPersonMapper;


    public PageInfo<IrDutyPersonVo> findPageBy(PageParams pageParams, String schoolId, Date startDate, Date endDate, String name, String mobile) {
        PageHelper.startPage(pageParams);
        PageInfo<IrDutyPersonVo> pageInfo = new PageInfo<>(irDutyPersonMapper.findPersonPageBy(schoolId, startDate, endDate, name, mobile));
        return  pageInfo;
    }
}
