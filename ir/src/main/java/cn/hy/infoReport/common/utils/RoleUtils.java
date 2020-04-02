package cn.hy.infoReport.common.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色util
 */
public class RoleUtils {

//    /**
//     * 初始化utils
//     */
//    public static void init(ApplicationContext applicationContext) {
//        sysRoleService = applicationContext.getBean(SysRoleService.class);
//        sysUserRoleDetailService = applicationContext.getBean(SysUserRoleDetailService.class);
//        sysOfficeUserDetailService = applicationContext.getBean(SysOfficeUserDetailService.class);
//        sysOfficeRoleDetailService = applicationContext.getBean(SysOfficeRoleDetailService.class);
//        sysOfficeService = applicationContext.getBean(SysOfficeService.class);
//        sysRoleMenuPermissionDetailService = applicationContext.getBean(SysRoleMenuPermissionDetailService.class);
//        sysMenuPermissionService = applicationContext.getBean(SysMenuPermissionService.class);
//        sysUserService = applicationContext.getBean(SysUserService.class);
//    }
//
//    private static SysRoleService sysRoleService;
//    private static SysUserRoleDetailService sysUserRoleDetailService;
//    private static SysOfficeUserDetailService sysOfficeUserDetailService;
//    private static SysOfficeRoleDetailService sysOfficeRoleDetailService;
//    private static SysOfficeService sysOfficeService;
//    private static SysRoleMenuPermissionDetailService sysRoleMenuPermissionDetailService;
//    private static SysMenuPermissionService sysMenuPermissionService;
//    private static SysUserService sysUserService;
//    /**
//     * 获取某人的角色列表
//     * @return
//     */
//    public static List<SysRole> getUserRoleList(String schoolId, String userId) {
//        //获取某人通过部门绑定的角色
//        List<SysOfficeUserDetail> soudList = sysOfficeUserDetailService.findBySchoolIdAndUserId(schoolId, userId);
//        if (CollectionUtils.isEmpty(soudList)) {
//            //这个人不在这个学校下
//            return null;
//        }
//
//        //角色id集合
//        Set<String> roleIdSet = null;
//
//        Set<String> officeIdSet = soudList.stream().map(SysOfficeUserDetail::getOfficeId).collect(Collectors.toSet());
//        officeIdSet.add(schoolId);
//        List<String> officeIdList = new ArrayList<>(officeIdSet);
//        //插叙状态正常的机构信息
//        List<SysOffice> normalOfficeList = sysOfficeService.findNormalBySchoolIdAndIdIn(schoolId, officeIdList);
//        if (CollectionUtils.isEmpty(normalOfficeList)) {
//            return null;
//        }
//
//        List<String> normalOfficeIdList = normalOfficeList.stream().map(SysOffice::getId).collect(Collectors.toList());
//
//        List<SysOfficeRoleDetail> sordList = sysOfficeRoleDetailService.findBySchoolIdAndOfficeIdIn(schoolId, normalOfficeIdList);
//
//        if (!CollectionUtils.isEmpty(sordList)) {
//            roleIdSet = sordList.stream().map(SysOfficeRoleDetail::getRoleId).collect(Collectors.toSet());
//        }
//
//
//        //获取某人绑定的角色
//        List<SysUserRoleDetail> surdList = sysUserRoleDetailService.findByUserIdAndSchoolId(userId, schoolId);
//        if (!CollectionUtils.isEmpty(surdList)) {
//            if (roleIdSet == null) {
//                roleIdSet = new HashSet<>(16);
//            }
//
//            for (SysUserRoleDetail surdTmp : surdList) {
//                roleIdSet.add(surdTmp.getRoleId());
//            }
//        }
//
//        if (CollectionUtils.isEmpty(roleIdSet)) {
//            return null;
//        }
//
//        return sysRoleService.findNormalBySchoolIdAndIdIn(schoolId, new ArrayList<>(roleIdSet));
//    }
//
//    /**
//     * 根据角色获取该角色下的权限
//     * @param sysRoles
//     * @param schoolId
//     * @return
//     */
//    public static List<SysMenuPermission> getMenuPerissionByRole(List<SysRole> sysRoles,String schoolId) {
//
//        List<SysMenuPermission> sysMenuPermissions = null;
//        if (!CollectionUtils.isEmpty(sysRoles)) {
//            List<String> roleIds = sysRoles.stream().map(SysRole::getId).collect(Collectors.toList());
//            List<SysRoleMenuPermissionDetail> sysRoleMenuPermissionDetails = sysRoleMenuPermissionDetailService.findSysRoleMenuPermissionDetailByRoleIdsAndSchool(roleIds, schoolId);
//            if (CollectionUtils.isEmpty(sysRoleMenuPermissionDetails)) {
//                return null;
//            }
//            List<String> menuPermissionIds = sysRoleMenuPermissionDetails.stream().map(SysRoleMenuPermissionDetail::getMenuPermissionId).collect(Collectors.toList());
//            sysMenuPermissions = sysMenuPermissionService.findNormalByIdIn(menuPermissionIds);
//        }
//        return sysMenuPermissions;
//    }
//
//    /**
//     * 根据权限标识符查找所有拥有该权限的用户id
//     * @param perission
//     * @return
//     */
//    public static List<String> getUserIdByMenuPermission(String perission,String schoolId) {
//        List<String> normalUser = new ArrayList<>();
//        List<String> userIds = new ArrayList<>();
//        //查找该权限标识符的id
//        List<SysMenuPermission> list = sysMenuPermissionService.getIdByPermission(perission);
//        if (!CollectionUtils.isEmpty(list)) {
//            String id = list.get(0).getId();
//            //根据权限标识符查找对应的角色id
//            List<SysRoleMenuPermissionDetail> sysRoleMenuPermissionDetails = sysRoleMenuPermissionDetailService.findRoleIdByMenuPermissionIdAndSchoolId(id,schoolId);
//            if (!CollectionUtils.isEmpty(sysRoleMenuPermissionDetails)) {
//                List<String> roleIds = sysRoleMenuPermissionDetails.stream().map(sysRoleMenuPermissionDetail -> sysRoleMenuPermissionDetail.getRoleId()).collect(Collectors.toList());
//                if (!CollectionUtils.isEmpty(roleIds)) {
//                    //根据角色id查找对应的用户id
//                    List<SysUserRoleDetail> sysUserRoleDetails = sysUserRoleDetailService.findByRoleIdsAndSchoolId(roleIds,schoolId);
//                    if (!CollectionUtils.isEmpty(sysUserRoleDetails)) {
//                        List<String> roleUserIds = sysUserRoleDetails.stream().map(sysUserRoleDetail -> sysUserRoleDetail.getUserId()).collect(Collectors.toList());
//                        //把userId添加到userIds集合中
//                        userIds.addAll(roleUserIds);
//                    }
//                    //根据角色id查询对应的部门id
//                    List<SysOfficeRoleDetail> sysOfficeRoleDetails = sysOfficeRoleDetailService.findByRoleIdsAndSchoolId(roleIds,schoolId);
//                    if (!CollectionUtils.isEmpty(sysOfficeRoleDetails)) {
//                        List<String> officeIds = sysOfficeRoleDetails.stream().map(sysOfficeRoleDetail -> sysOfficeRoleDetail.getOfficeId()).collect(Collectors.toList());
//                        if (!CollectionUtils.isEmpty(officeIds)) {
//                            //根据部门id查找该部门下所有的用户id
//                            List<SysOfficeUserDetail> sysOfficeUserDetails = sysOfficeUserDetailService.findByOfficeIdsAndSchoolId(officeIds, schoolId);
//                            if (!CollectionUtils.isEmpty(sysOfficeRoleDetails)) {
//                                //把userId添加到userIds集合中
//                                List<String> officeUserIds = sysOfficeUserDetails.stream().map(sysOfficeUserDetail -> sysOfficeUserDetail.getUserId()).collect(Collectors.toList());
//                                userIds.addAll(officeUserIds);
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        List<String> ids = userIds.stream().distinct().collect(Collectors.toList());
//        //去掉停用和已经删除的用户,只取状态正常的用户
//        List<SysUser> sysUsers = sysUserService.findNormalByIds(ids);
//        if (!CollectionUtils.isEmpty(sysUsers)) {
//            normalUser= sysUsers.stream().map(sysUser -> sysUser.getId()).collect(Collectors.toList());
//        }
//        return normalUser;
//    }
}






















