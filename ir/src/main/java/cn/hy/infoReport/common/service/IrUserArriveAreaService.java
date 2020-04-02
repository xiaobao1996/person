package cn.hy.infoReport.common.service;

import cn.hy.infoReport.common.entity.IrUserArriveArea;
import cn.hy.infoReport.common.entity.IrUserArriveAreaExample;
import cn.hy.infoReport.common.mapper.IrUserArriveAreaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: JiuZhou
 * @Date: 2020/3/5 12:38
 * @Version 1.0
 */
@Service
public class IrUserArriveAreaService {

    @Autowired
    private IrUserArriveAreaMapper irUserArriveAreaMapper;

    /**
     * 根据id集合查找所对应的省市区
     * @param reportIds
     * @return
     */
    public List<IrUserArriveArea> findByReportIds(List<String> reportIds) {
        IrUserArriveAreaExample irUserArriveAreaExample = new IrUserArriveAreaExample();
        irUserArriveAreaExample.createCriteria().andReportIdIn(reportIds);
        return irUserArriveAreaMapper.selectByExample(irUserArriveAreaExample);
    }
}
