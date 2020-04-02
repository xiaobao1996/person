package cn.hy.infoReport.common.service;

import cn.hy.infoReport.common.entity.IrMsgLog;
import cn.hy.infoReport.common.entity.IrMsgLogExample;
import cn.hy.infoReport.common.mapper.IrMsgLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * 消息记录service
 */
@Service
public class IrMsgLogService extends BaseService {

    @Autowired
    private IrMsgLogMapper irMsgLogMapper;

    /**
     * 根据条件查询
     * @param schoolId
     * @param userId
     * @return
     */
    public IrMsgLog findBySchoolIdAndUserIdAndDateAndTimeAreaAndOpeType(String schoolId, String userId, Date date, byte timeArea, byte opeType) {
        IrMsgLogExample example = new IrMsgLogExample();
        example.createCriteria().andSchoolIdEqualTo(schoolId)
                .andUserIdEqualTo(userId)
                .andDateEqualTo(date)
                .andTimeAreaEqualTo(timeArea)
                .andOpeTypeEqualTo(opeType);
        List<IrMsgLog> logList = irMsgLogMapper.selectByExample(example);
        return CollectionUtils.isEmpty(logList) ? null : logList.get(0);
    }
}
