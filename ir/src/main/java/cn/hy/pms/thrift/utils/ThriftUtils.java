package cn.hy.pms.thrift.utils;

import cn.hy.infoReport.common.entity.*;
import cn.hy.infoReport.common.exception.MessageException;
import cn.hy.infoReport.common.vo.PageInfo;
import cn.hy.infoReport.module.business.vo.UserOfficeVo;
import cn.hy.pms.thrift.*;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.wealoha.thrift.PoolConfig;
import com.wealoha.thrift.ServiceInfo;
import com.wealoha.thrift.ThriftClient;
import com.wealoha.thrift.ThriftClientPool;
import org.apache.thrift.TApplicationException;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * thrift客户端工具类，使用连接池
 */
public class ThriftUtils {
    public static ThriftClientPool<PmsThriftService.Client> pool;


    public static void init(String thriftHost, Integer thriftPort) {
        PoolConfig config = new PoolConfig();
        config.setMinIdle(2);
        config.setMaxTotal(20);
        pool = new ThriftClientPool<>(Collections.singletonList(new ServiceInfo(thriftHost, thriftPort)), e -> new PmsThriftService.Client(new TBinaryProtocol(e)), config);
    }

    /**
     * 根据用户id集合查询用户信息
     * @param userIds
     * @return
     */
    public static List<SysUserThrift> findUserByUserIdList(List<String> userIds) throws TException {
        try (ThriftClient<PmsThriftService.Client> client = pool.getClient()) {
            PmsThriftService.Client pmsThriftService = client.iFace();
            List<SysUserThrift> sutList = pmsThriftService.findUserByUserIdList(userIds);
            client.finish();
            return sutList;
        } catch (TException e) {
            return handleThriftException(e);
        }
    }

    /**
     * 根据用户id查询用户信息
     * @param userId
     * @return
     */
    public static SysUserThrift findUserByUserId(String userId) throws TException {
        try (ThriftClient<PmsThriftService.Client> client = pool.getClient()) {
            PmsThriftService.Client pmsThriftService = client.iFace();
            SysUserThrift sut = pmsThriftService.findUserByUserId(userId);
            client.finish();
            return sut;
        } catch (TException e) {
            return handleThriftException(e);
        }
    }

    private static <T>T handleThriftException(TException e) throws TException {
        if (e instanceof TApplicationException && ((TApplicationException) e).getType() == TApplicationException.MISSING_RESULT) {
            return null;
        } else if (e instanceof ThriftBusinessException) {
            throw new MessageException(((ThriftBusinessException) e).message);
        }  else {
            throw new TException(e);
        }
    }

    /**
     * 根据用户id，应用编码查询用户在那些学校有这些应用
     * @param userId
     * @return
     */
    public static List<SchoolRoleVoThrift> findSchoolByUserIdAndAppCode(String userId, String appCode) throws TException {
        try (ThriftClient<PmsThriftService.Client> client = pool.getClient()) {
            PmsThriftService.Client pmsThriftService = client.iFace();
            List<SchoolRoleVoThrift> srvList = pmsThriftService.findSchoolByUserIdAndAppCode(userId, appCode);
            client.finish();
            return srvList;
        } catch (TException e) {
            return handleThriftException(e);
        }
    }


    /**
     * 根据学校id, LIKE用户真实姓名 查询用户信息
     * @param
     * @return
     */
    public static List<SysUserThrift> findUserBySchoolIdAndRealNameLike(String schoolId, String realName) throws TException {
        try (ThriftClient<PmsThriftService.Client> client = pool.getClient()) {
            PmsThriftService.Client pmsThriftService = client.iFace();
            List<SysUserThrift> sutList = pmsThriftService.findUserBySchoolIdAndRealNameLike(schoolId, realName);
            client.finish();
            return sutList;
        } catch (TException e) {
            return handleThriftException(e);
        }
    }

    /**
     * 根据schoolId查询树型机构信息
     * @param
     * @return
     */
    public static List<SysOfficeThrift> findTreeOfficeBySchooId(String schoolId) throws TException {
        try (ThriftClient<PmsThriftService.Client> client = pool.getClient()) {
            PmsThriftService.Client pmsThriftService = client.iFace();
            List<SysOfficeThrift> sotList = pmsThriftService.findTreeOfficeBySchoolId(schoolId);
            client.finish();
            return sotList;
        } catch (TException e) {
            return handleThriftException(e);
        }
    }


    /**
     * 根据学校id和其他参数（机构id）(后期动态补充)查询所有用户id信息
     * @param schoolId
     * @param officeId
     * @return
     * @throws TException
     */
    public static List<String> findUserIdBySchoolIdAndOtherParams(String schoolId, String officeId) throws TException {
        try (ThriftClient<PmsThriftService.Client> client = pool.getClient()) {
            PmsThriftService.Client pmsThriftService = client.iFace();
            List<String> userIdList = pmsThriftService.findUserIdBySchoolIdAndOtherParams(schoolId, officeId);
            client.finish();
            return userIdList;
        } catch (TException e) {
            return handleThriftException(e);
        }
    }





    /**
     * 根据学校id和其他参数（机构id）(后期动态补充)查询所有用户id信息
     * @param schoolId
     * @return
     * @throws TException
     */
    public static List<String> findUserIdListWithJsonParams(String schoolId, String jsonParams) throws TException {
        try (ThriftClient<PmsThriftService.Client> client = pool.getClient()) {
            PmsThriftService.Client pmsThriftService = client.iFace();
            String result = pmsThriftService.findUserIdListWithJsonParams(schoolId, jsonParams);
            client.finish();
            return JSONObject.parseArray(result, String.class);
        } catch (TException e) {
            return handleThriftException(e);
        }
    }


    /**
     * 根据userId、schoolId、appCode查询userId用户在schoolId学校、appCode应用下的权限信息
     * @param userId
     * @return
     */

    public static List<SysMenuPermissionThrift> findMenuPermByUserIdAndSchoolIdAndAppCode(String userId, String schoolId, String appCode) throws TException {
        try (ThriftClient<PmsThriftService.Client> client = pool.getClient()) {
            PmsThriftService.Client pmsThriftService = client.iFace();
            List<SysMenuPermissionThrift> smpList = pmsThriftService.findMenuPermByUserIdAndSchoolIdAndAppCode(userId, schoolId, appCode);
            client.finish();
            return smpList;
        } catch (TException e) {
            return handleThriftException(e);
        }
    }

    /**
     * 根据schoolId,officeType 查询机构树状集合
     * @param schoolId
     * @param officeType
     * @return
     * @throws TException
     */
    public static List<SysOfficeThrift> findTreeOfficeBySchoolIdAndOfficeType(String schoolId, Byte officeType) throws TException {
        try (ThriftClient<PmsThriftService.Client> client = pool.getClient()) {
            PmsThriftService.Client pmsThriftService = client.iFace();
            List<SysOfficeThrift> sotList = pmsThriftService.findTreeOfficeBySchoolIdAndOfficeType(schoolId, officeType);
            client.finish();
            return sotList;
        } catch (TException e) {
            return handleThriftException(e);
        }
    }

    /**
     * 根据schoolId,家长id 查询其孩子信息
     * @param schoolId
     * @param parentIds
     * @return
     * @throws TException
     */
    public static List<SysUser> findChildBySchoolIdAndParentId(String schoolId, List<String> parentIds) throws TException {
        try (ThriftClient<PmsThriftService.Client> client = pool.getClient()) {
            PmsThriftService.Client pmsThriftService = client.iFace();
            String result = pmsThriftService.findChildBySchoolIdAndUserIds(schoolId, parentIds);
            client.finish();
            return JSONObject.parseObject(result, new TypeReference<List<SysUser>>() {});
        } catch (TException e) {
            return handleThriftException(e);
        }
    }
    /**
     * 根据schoolId,查询学校信息
     * @param schoolId
     * @return
     * @throws TException
     */
    public static SysOffice findSchoolBySchoolId(String schoolId) throws TException {
        try (ThriftClient<PmsThriftService.Client> client = pool.getClient()) {
            PmsThriftService.Client pmsThriftService = client.iFace();
            String result = pmsThriftService.findSchoolBySchoolId(schoolId);
            client.finish();
            return JSONObject.parseObject(result, new TypeReference<SysOffice>() {
            });
        } catch (TException e) {
            return handleThriftException(e);
        }
    }

    /**
     * 根据用户id，学校id查询角色信息
     * @param schoolId
     * @return
     * @throws TException
     */
    public static List<SysRoleThrift> findRoleByUserIdAndSchoolId(String userId, String schoolId) throws TException {
        try (ThriftClient<PmsThriftService.Client> client = pool.getClient()) {
            PmsThriftService.Client pmsThriftService = client.iFace();
            List<SysRoleThrift> roleList = pmsThriftService.findRoleByUserIdAndSchoolId(userId, schoolId);
            client.finish();
            return roleList;
        } catch (TException e) {
            return handleThriftException(e);
        }
    }

    /**
     * 根据schoolId,家长id 查询其孩子信息
     * @param schoolId
     * @return
     * @throws TException
     */
    public static List<SysUser> findNormalUserChildByParentIdAndSchoolId(String userId, String schoolId) throws TException {
        try (ThriftClient<PmsThriftService.Client> client = pool.getClient()) {
            PmsThriftService.Client pmsThriftService = client.iFace();
            String result = pmsThriftService.findNormalUserChildByParentIdAndSchoolId(userId, schoolId);
            client.finish();
            return JSONObject.parseObject(result, new TypeReference<List<SysUser>>() {});
        } catch (TException e) {
            return handleThriftException(e);
        }
    }

    /**
     * 根据schoolId,家长id 查询其孩子信息
     * @param schoolId
     * @return
     * @throws TException
     */
    public static List<SysClassUserDetail> findClassUserDetailBySchoolIdAndUserIdAndOtherParams(String schoolId, String userId, String type) throws TException {
        try (ThriftClient<PmsThriftService.Client> client = pool.getClient()) {
            PmsThriftService.Client pmsThriftService = client.iFace();
            String result = pmsThriftService.findClassUserDetailBySchoolIdAndUserIdAndOtherParams(schoolId, userId, type);
            client.finish();
            return JSONObject.parseObject(result, new TypeReference<List<SysClassUserDetail>>() {});
        } catch (TException e) {
            return handleThriftException(e);
        }
    }

    /**
     * 根据用户查询其所在的班级信息
     * @param schoolId
     * @return
     * @throws TException
     */
    public static List<SysOfficeThrift> findOfficeByUserIdAndSchoolId(String schoolId, String userId) throws TException {
        try (ThriftClient<PmsThriftService.Client> client = pool.getClient()) {
            PmsThriftService.Client pmsThriftService = client.iFace();
            List<SysOfficeThrift> sysOfficeThrifts = pmsThriftService.findOfficeByUserIdAndSchoolId(schoolId, userId);
            client.finish();
            return sysOfficeThrifts;
        } catch (TException e) {
            return handleThriftException(e);
        }
    }
    /**
     * 根据学校id,json参数查询机构信息
     * @param schoolId
     * @return
     * @throws TException
     */
    public static List<SysOffice> findOfficeBySchoolIdAndJsonParams(String schoolId, String jsonParams) throws TException {
        try (ThriftClient<PmsThriftService.Client> client = pool.getClient()) {
            PmsThriftService.Client pmsThriftService = client.iFace();
            String result = pmsThriftService.findOfficeBySchoolIdAndJsonParams(schoolId, jsonParams);
            client.finish();
            return JSONObject.parseObject(result, new TypeReference<List<SysOffice>>() {});
        } catch (TException e) {
            return handleThriftException(e);
        }
    }

    /**
     * 根据schoolId,家长id 查询其孩子信息
     * @param schoolId
     * @return
     * @throws TException
     */
    public static PageInfo<SysUser> findPageUserWithJsonParams(Integer pageNum, Integer pageSize, String schoolId, String jsonParams) throws TException {
        try (ThriftClient<PmsThriftService.Client> client = pool.getClient()) {
            PmsThriftService.Client pmsThriftService = client.iFace();
            String result = pmsThriftService.findPageUserWithJsonParams(pageNum, pageSize, schoolId, jsonParams);
            client.finish();
            return JSONObject.parseObject(result, new TypeReference<PageInfo<SysUser>>() {});
        } catch (TException e) {
            return handleThriftException(e);
        }
    }


    /**
     * 根据学校id获取所有班级以及班级里的学生id
     * @param schoolId
     * @return
     * @throws TException
     */
    public static List<OfficeUserIdsVo> findClassStudentUserIdsBySchoolId(String schoolId) throws TException {
        try (ThriftClient<PmsThriftService.Client> client = pool.getClient()) {
            PmsThriftService.Client pmsThriftService = client.iFace();
            String result = pmsThriftService.findClassStudentUserIdsBySchoolId(schoolId);
            client.finish();
            return JSONObject.parseArray(result, OfficeUserIdsVo.class);
        } catch (TException e) {
            return handleThriftException(e);
        }
    }

    /**
     * 根据用户id集合、其他参数（非必填）查询用户所在机构信息
     * @param schoolId
     * @return
     * @throws TException
     */
    public static List<UserClassVo> findUserOfficeBySchoolIdAndUserIdListAndOtherJsonParams(String schoolId, List<String> userIdList, String jsonStr) throws TException {
        try (ThriftClient<PmsThriftService.Client> client = pool.getClient()) {
            PmsThriftService.Client pmsThriftService = client.iFace();
            String result = pmsThriftService.findUserOfficeBySchoolIdAndUserIdListAndOtherJsonParams(schoolId, userIdList, jsonStr);
            client.finish();
            return JSONObject.parseArray(result, UserClassVo.class);
        } catch (TException e) {
            return handleThriftException(e);
        }
    }

    /**
     * 根据学校id,json参数查询busi_class表信息
     * @param schoolId
     * @return
     * @throws TException
     */
    public static List<BusiClass> findBusiClassBySchoolIdAndJsonParams(String schoolId, String jsonStr) throws TException {
        try (ThriftClient<PmsThriftService.Client> client = pool.getClient()) {
            PmsThriftService.Client pmsThriftService = client.iFace();
            String result = pmsThriftService.findBusiClassBySchoolIdAndJsonParams(schoolId, jsonStr);
            client.finish();
            return JSONObject.parseArray(result, BusiClass.class);
        } catch (TException e) {
            return handleThriftException(e);
        }
    }

  /**
     * 根据学校id,json参数查询busi_class表信息
     * @param schoolId
     * @return
     * @throws TException
     */
    public static List<SysUser> findParentBySchoolIdAndUserId(String schoolId, String userId) throws TException {
        try (ThriftClient<PmsThriftService.Client> client = pool.getClient()) {
            PmsThriftService.Client pmsThriftService = client.iFace();
            String result = pmsThriftService.findParentBySchoolIdAndUserId(schoolId, userId);
            client.finish();
            return JSONObject.parseArray(result, SysUser.class);
        } catch (TException e) {
            return handleThriftException(e);
        }
    }

  /**
     * 根据学校id,json参数获取用户角色信息
     * @param schoolId
     * @return
     * @throws TException
     */
    public static List<SysRole> findRoleBySchoolIdAndJsonParams(String schoolId, String jsonStr) throws TException {
        try (ThriftClient<PmsThriftService.Client> client = pool.getClient()) {
            PmsThriftService.Client pmsThriftService = client.iFace();
            String result = pmsThriftService.findRoleBySchoolIdAndJsonParams(schoolId, jsonStr);
            client.finish();
            return JSONObject.parseArray(result, SysRole.class);
        } catch (TException e) {
            return handleThriftException(e);
        }
    }
  /**
     * 根据学校id,json参数获取用户角色信息
     * @param schoolId
     * @return
     * @throws TException
     */
    public static List<SysRole> findJobTypeBySchoolIdAndJsonParams(String schoolId, String jsonStr) throws TException {
        try (ThriftClient<PmsThriftService.Client> client = pool.getClient()) {
            PmsThriftService.Client pmsThriftService = client.iFace();
            String result = pmsThriftService.findJobTypeBySchoolIdAndJsonParams(schoolId, jsonStr);
            client.finish();
            return JSONObject.parseArray(result, SysRole.class);
        } catch (TException e) {
            return handleThriftException(e);
        }
    }

  /**
     * 根据学校id,json参数获取用户角色信息
     * @param schoolId
     * @return
     * @throws TException
     */
    public static List<UserRoleVo> findUserJobTypeBySchoolIdAndUserIdListAndJsonParams(String schoolId, List<String> userIdList, String jsonStr) throws TException {
        try (ThriftClient<PmsThriftService.Client> client = pool.getClient()) {
            PmsThriftService.Client pmsThriftService = client.iFace();
            String result = pmsThriftService.findUserJobTypeBySchoolIdAndUserIdListAndJsonParams(schoolId, userIdList, jsonStr);
            client.finish();
            return JSONObject.parseArray(result, UserRoleVo.class);
        } catch (TException e) {
            return handleThriftException(e);
        }
    }

  /**
     * 根据学校id,json参数获取场所信息
     * @param schoolId
     * @return
     * @throws TException
     */
    public static List<BusiPlace> findPlaceBySchoolIdAndJsonParams(String schoolId, String jsonStr) throws TException {
        try (ThriftClient<PmsThriftService.Client> client = pool.getClient()) {
            PmsThriftService.Client pmsThriftService = client.iFace();
            String result = pmsThriftService.findPlaceBySchoolIdAndJsonParams(schoolId, jsonStr);
            client.finish();
            return JSONObject.parseArray(result, BusiPlace.class);
        } catch (TException e) {
            return handleThriftException(e);
        }
    }

  /**
     * 根据学校id,json参数查询班级任职信息
     * @param schoolId
     * @return
     * @throws TException
     */
    public static List<SysClassUserDetail> findClassUserDetailBySchoolIdAndJsonParams(String schoolId, String jsonStr) throws TException {
        try (ThriftClient<PmsThriftService.Client> client = pool.getClient()) {
            PmsThriftService.Client pmsThriftService = client.iFace();
            String result = pmsThriftService.findClassUserDetailBySchoolIdAndJsonParams(schoolId, jsonStr);
            client.finish();
            return JSONObject.parseArray(result, SysClassUserDetail.class);
        } catch (TException e) {
            return handleThriftException(e);
        }
    }

  /**
     * 根据学校id,json参数查询班级信息
     * @param schoolId
     * @return
     * @throws TException
     */
    public static List<SysOffice> findClassBySchoolIdAndJsonParams(String schoolId, String jsonStr) throws TException {
        try (ThriftClient<PmsThriftService.Client> client = pool.getClient()) {
            PmsThriftService.Client pmsThriftService = client.iFace();
            String result = pmsThriftService.findClassBySchoolIdAndJsonParams(schoolId, jsonStr);
            client.finish();
            return JSONObject.parseArray(result, SysOffice.class);
        } catch (TException e) {
            return handleThriftException(e);
        }
    }


//    public static List<SysOfficeUserDetail> findUserOfficeBySchoolIdAndUserIdList(String schoolId, List<String> userIdList) throws TException {
//        try (ThriftClient<PmsThriftService.Client> client = pool.getClient()) {
//            PmsThriftService.Client pmsThriftService = client.iFace();
//            String result = pmsThriftService.findUserOfficeBySchoolIdAndUserIdList(schoolId, userIdList);
//            client.finish();
//            return JSONObject.parseArray(result, SysOfficeUserDetail.class);
//        } catch (TException e) {
//            return handleThriftException(e);
//        }
//    }
    /**
     * 根据学校id, userIdList查询用户机构详情
     * @param schoolId
     * @return
     * @throws TException
     */
    public static List<UserOfficeVo>  findUserOfficeBySchoolIdAndUserIdList(String schoolId, List<String> userIdList) throws TException {
        try (ThriftClient<PmsThriftService.Client> client = pool.getClient()) {
            PmsThriftService.Client pmsThriftService = client.iFace();
            Map<String, Object> paramsMap = new HashMap<>(2);
            paramsMap.put("officeType",  2);
            String paramsStr = JSONObject.toJSONString(paramsMap);
            String result = pmsThriftService.findUserOfficeBySchoolIdAndUserIdListAndOtherJsonParams(schoolId, userIdList, paramsStr);
            List<UserOfficeVo> officeVoList = JSONObject.parseArray(result, UserOfficeVo.class);

            client.finish();
            return officeVoList;
        } catch (TException e) {
            return handleThriftException(e);
        }
    }
}
