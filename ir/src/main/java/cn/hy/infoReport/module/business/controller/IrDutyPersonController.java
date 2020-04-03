package cn.hy.infoReport.module.business.controller;

import cn.hy.infoReport.common.entity.IrDutyPerson;
import cn.hy.infoReport.common.params.PageParams;
import cn.hy.infoReport.common.service.IrDutyPersonService;
import cn.hy.infoReport.common.utils.UserUtils;
import cn.hy.infoReport.common.utils.ValidatorUtils;
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

    /**
     * 分页获取值班人员信息
     * @param schoolId
     * @param pageParams
     * @param startDate
     * @param endDate
     * @param name
     * @param mobile
     * @return
     */
    @RequestMapping("/list")
    public Result findDutyPersonPageBy(@RequestParam("schoolToken")String schoolId, PageParams pageParams, Date startDate, Date endDate, String name, String mobile) {
        PageInfo<IrDutyPersonVo> pageInfo = irDutyPersonService.findPageBy(pageParams, schoolId, startDate, endDate, name, mobile);
        return Result.success(pageInfo);
    }

    /**
     * 保存或更新值班人员信息
     * @param schoolId
     * @param irDutyPersonVo
     * @return
     */
    @RequestMapping("/saveOrUpdate")
    public Result saveOrUpdate(@RequestParam("schoolToken")String schoolId, IrDutyPersonVo irDutyPersonVo) {
        ValidatorUtils.validateEntity(irDutyPersonVo);
        if (irDutyPersonVo.getId() != null) {
            irDutyPersonService.update(schoolId, irDutyPersonVo, UserUtils.getUserId());
        } else {
            irDutyPersonService.save(schoolId, irDutyPersonVo, UserUtils.getUserId());
        }

        return Result.success(null);
    }

    /**
     * 软删除值班员信息
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(@RequestParam String id) {
        irDutyPersonService.sotfDeleteById(id, UserUtils.getUserId());
        return  Result.success(null);
    }

}
