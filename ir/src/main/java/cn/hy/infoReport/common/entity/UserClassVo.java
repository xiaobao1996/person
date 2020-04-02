package cn.hy.infoReport.common.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 用户班级vo
 */
@Setter
@Getter
public class UserClassVo {

    private String userId;
    private List<SysOffice> officeList;

}
