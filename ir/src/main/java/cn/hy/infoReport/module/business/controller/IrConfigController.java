package cn.hy.infoReport.module.business.controller;

import cn.hy.infoReport.common.entity.IrConfig;
import cn.hy.infoReport.common.service.IrConfigService;
import cn.hy.infoReport.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 配置controller
 */
@RequestMapping("/busi/config")
@RestController
public class IrConfigController {

    @Autowired
    private IrConfigService irConfigService;



    /**
     * 获取配置详情
     * @return
     */
    @RequestMapping("/detail")
    public Result detail(@RequestParam("schoolToken") String schoolId) {
        IrConfig config = irConfigService.findWithInitBySchoolId(schoolId);
        return Result.success(config);
    }

    /**
     * 保存配置
     * @return
     */
    @RequestMapping("/save")
    public Result save(@RequestParam("schoolToken") String schoolId, IrConfig irConfig) {
        irConfigService.save(schoolId, irConfig);
        return Result.success(null);
    }

}
