package cn.hy.infoReport.common.service;

import cn.hy.infoReport.common.entity.IrDutyPerson;
import cn.hy.infoReport.common.entity.IrDutyPersonExample;
import cn.hy.infoReport.common.entity.PmsStaff;
import cn.hy.infoReport.common.enums.DataStatus;
import cn.hy.infoReport.common.exception.MessageException;
import cn.hy.infoReport.common.mapper.IrDutyPersonMapper;
import cn.hy.infoReport.common.params.PageParams;
import cn.hy.infoReport.common.utils.IdUtils;
import cn.hy.infoReport.common.vo.PageInfo;
import cn.hy.infoReport.module.business.vo.IrDutyPersonVo;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;


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

    @Autowired
    private PmsStaffService pmsStaffService;

    public PageInfo<IrDutyPersonVo> findPageBy(PageParams pageParams, String schoolId, Date startDate, Date endDate, String name, String mobile) {
        PageHelper.startPage(pageParams);
        PageInfo<IrDutyPersonVo> pageInfo = new PageInfo<>(irDutyPersonMapper.findPersonPageBy(schoolId, startDate, endDate, name, mobile));
        return  pageInfo;
    }

    /**
     * 新增值班人员信息
     * @param schoolId
     * @param irDutyPersonVo
     * @param operUserId
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(String schoolId, IrDutyPersonVo irDutyPersonVo, String operUserId) {
        List<PmsStaff> staffList = pmsStaffService.findByUserIdIn(Collections.singletonList(irDutyPersonVo.getUserId()));
        if (CollectionUtils.isEmpty(staffList)) {
            throw new MessageException("当前教职工不存在");
        }
        IrDutyPerson forUpdate = this.findNormalDetailBy(irDutyPersonVo.getId(), null, null, schoolId);
        if (forUpdate == null) {
            throw  new MessageException("当前记录不存在！");
        }
        forUpdate.setUserId(irDutyPersonVo.getUserId());
        forUpdate.setDate(irDutyPersonVo.getDutyDate());
        forUpdate.setUpdateBy(operUserId);
        forUpdate.setUpdateAt(new Date());
        irDutyPersonMapper.updateByPrimaryKey(forUpdate);
    }

    /**
     * 修改值班人员信息
     * @param schoolId
     * @param irDutyPersonVo
     * @param operUserId
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(String schoolId, IrDutyPersonVo irDutyPersonVo, String operUserId) {
        List<PmsStaff> staffList = pmsStaffService.findByUserIdIn(Collections.singletonList(irDutyPersonVo.getUserId()));
        if (CollectionUtils.isEmpty(staffList)) {
            throw new MessageException("当前教职工不存在");
        }
        IrDutyPerson forValid = this.findNormalDetailBy(null, null, irDutyPersonVo.getDutyDate(), schoolId);
        if (forValid != null) {
            throw new MessageException("当前日期已设置值班人员信息！");
        }
        IrDutyPerson forAdd = new IrDutyPerson();
        Date now = new Date();
        forAdd.setId(IdUtils.newId());
        forAdd.setDate(irDutyPersonVo.getDutyDate());
        forAdd.setUserId(irDutyPersonVo.getUserId());
        forAdd.setSchoolId(schoolId);
        forAdd.setCreateAt(now);
        forAdd.setCreateBy(operUserId);
        forAdd.setUpdateAt(now);
        forAdd.setUpdateBy(operUserId);
        forAdd.setStatus(DataStatus.NORMAL.getCode());
        irDutyPersonMapper.insert(forAdd);
    }


    /**
     * 获取值班员详情
     * @param id
     * @param userId
     * @param date
     * @param schoolId
     * @return
     */
    public IrDutyPerson findNormalDetailBy(String id, String userId, Date date, String schoolId) {
        IrDutyPersonExample example = new IrDutyPersonExample();
        IrDutyPersonExample.Criteria criteria = example.createCriteria();
        criteria.andSchoolIdEqualTo(schoolId).andStatusEqualTo(DataStatus.NORMAL.getCode());
        if (StringUtils.isNotBlank(id)) {
            criteria.andIdEqualTo(id);
        }
        if (StringUtils.isNotBlank(userId)) {
            criteria.andUserIdEqualTo(userId);
        }
        if (date != null) {
            criteria.andDateEqualTo(date);
        }

        List<IrDutyPerson> irDutyPeople = irDutyPersonMapper.selectByExample(example);
        return CollectionUtils.isEmpty(irDutyPeople) ? null : irDutyPeople.get(0);
    }

    /**
     * 根据id软删除值班人员信息
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    public void  sotfDeleteById(String id, String operUserId) {
        IrDutyPerson forDelete = this.findNormalDetailBy(id, null, null, null);
        if (forDelete == null) {
            throw new MessageException("当前记录不存在！");
        }
        forDelete.setUpdateBy(operUserId);
        forDelete.setUpdateAt(new Date());
        forDelete.setStatus(DataStatus.DELETE.getCode());
        irDutyPersonMapper.updateByPrimaryKeySelective(forDelete);
    }

}
