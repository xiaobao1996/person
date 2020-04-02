package cn.hy.infoReport.module.business.controller;

import cn.hy.infoReport.common.entity.IrDutyPerson;
import cn.hy.infoReport.common.params.PageParams;
import cn.hy.infoReport.common.service.IrDutyPersonService;
import cn.hy.infoReport.common.vo.PageInfo;
import cn.hy.infoReport.common.vo.Result;
import cn.hy.infoReport.module.business.vo.IrDutyPersonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author hu
 * @version 1.0
 * @date 2020/4/2 13:07
 * @description 值班表Controller
 */
@RestController
@RequestMapping("/busi/duty")
public class IrDutyPersonController {

    @Autowired
    private IrDutyPersonService irDutyPersonService;

    @RequestMapping("/list")
    public Result findDutyPersonPageBy(@RequestParam("schoolToken")String schoolId, PageParams pageParams, Date startDate, Date endDate, String name, String mobile) {
        PageInfo<IrDutyPersonVo> pageInfo = irDutyPersonService.findPageBy(pageParams, schoolId, startDate, endDate, name, mobile);
        return Result.success(pageInfo);
    }
}
