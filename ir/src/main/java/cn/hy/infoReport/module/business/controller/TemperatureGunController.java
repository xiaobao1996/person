package cn.hy.infoReport.module.business.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import cn.hy.infoReport.common.constant.ProjectConstant;
import cn.hy.infoReport.common.entity.IrMonitorHistory;
import cn.hy.infoReport.common.entity.PmsClass;
import cn.hy.infoReport.common.entity.PmsStudent;
import cn.hy.infoReport.common.enums.ResultCode;
import cn.hy.infoReport.common.exception.MessageException;
import cn.hy.infoReport.common.params.PageParams;
import cn.hy.infoReport.common.service.IrMonitorHistoryService;
import cn.hy.infoReport.common.service.PmsClassService;
import cn.hy.infoReport.common.service.PmsStudentService;
import cn.hy.infoReport.common.utils.DateUtils;
import cn.hy.infoReport.common.utils.UserUtils;
import cn.hy.infoReport.common.vo.PageInfo;
import cn.hy.infoReport.common.vo.Result;
import cn.hy.infoReport.module.business.vo.TbImportVo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 体温枪数据处理controller
 */
@RequestMapping("/busi/gun")
@RestController
public class TemperatureGunController {

    @Autowired
    private PmsClassService pmsClassService;
    @Autowired
    private PmsStudentService pmsStudentService;
    @Autowired
    private IrMonitorHistoryService irMonitorHistoryService;


    /**
     * 获取体温枪数据分页信息
     * @param pageParams
     * @return
     */
    @RequestMapping("/list")
    public Result list(PageParams pageParams, @RequestParam("schoolToken") String schoolId, Date startDate, Date endDate, String name, String classId) {
        if (startDate != null) {
            startDate = DateUtils.getDayStart(startDate);
        }

        if (endDate != null) {
            endDate = DateUtils.getDayEnd(endDate);
        }
        PageInfo<IrMonitorHistory> historyList = irMonitorHistoryService.findTbPageBy(pageParams, schoolId, startDate, endDate, name, classId);
        return Result.success(historyList);

    }

//    /**
//     * 导入学生体温监控信息
//     * @return
//     */
//    @RequestMapping("/studentMonitorImport")
//    public Result studentMonitorImport(MultipartFile file, @RequestParam("schoolToken") String schoolId) {
//        List<PmsClass> classList = pmsClassService.findBySchoolId(schoolId);
//        if (CollectionUtils.isEmpty(classList)) {
//            return Result.error("缺少班级信息！", null);
//        }
//        //单个学校只能同时进行一次同步任务
//        synchronized ((schoolId + "_health_monitor_import_student").intern()) {
//            ImportParams importParams = new ImportParams();
//            importParams.setHeadRows(1);
//            String errSeparator = "。  ";
//            String opeUserId = UserUtils.getUserId();
//            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
//            try (InputStream inputStream = file.getInputStream()){
//                importParams.setVerifyHandler((IExcelVerifyHandler<TbImportVo>) importVo -> {
//                    importVo.buildData();
//                    if (CollectionUtils.isEmpty(importVo.getTemperatureDetailList())) {
//                        return new ExcelVerifyHandlerResult(true, "");
//                    }
//                    StringBuilder errorMsg = new StringBuilder();
//                    if (StringUtils.isBlank(importVo.getMonitorDateLabel())) {
//                        errorMsg.append("日期不能为空").append(errSeparator);
//                    } else {
//                        try {
//                            Date monitorDate = dateFormat.parse(importVo.getMonitorDateLabel());
//                            importVo.setMonitorDate(monitorDate);
//                        } catch (ParseException e) {
//                            errorMsg.append("日期格式错误，请输入类似2000/03/02格式的字符").append(errSeparator);
//                        }
//
//                        if (importVo.getMonitorDate() != null) {
//                            for (TbImportVo.Item item : importVo.getTemperatureDetailList()) {
//                                if (!importVo.getMonitorDateLabel().equals(dateFormat.format(item.getMonitorDateTime()))) {
//                                    errorMsg.append("体温监测时间").append(item.getMonitorDateTimeStr()).append("不属于").append(importVo.getMonitorDateLabel()).append(errSeparator);
//                                }
//                            }
//                        }
//                    }
//
//                    if (StringUtils.isBlank(importVo.getName())) {
//                        errorMsg.append("学生名称不能为空").append(errSeparator);
//                    }
//
//                    if (StringUtils.isNotBlank(errorMsg)) {
//                        return new ExcelVerifyHandlerResult(false, errorMsg.toString());
//                    }
//
//                    PmsStudent curStudent = null;
//
//                    if (StringUtils.isBlank(importVo.getClassName())) {
//                        errorMsg.append("班级不能为空").append(errSeparator);
//                    } else {
//                        try {
//                            String className = importVo.getClassName();
//                            Integer grade = Integer.parseInt(className.substring(0, 4));
//                            String classCode = className.substring(className.indexOf("(") + 1, className.indexOf(")"));
//
//                            boolean found = false;
//                            for (PmsClass pcTmp : classList) {
//                                if (pcTmp.getGrade().equals(grade) && pcTmp.getClassCode().equals(classCode)) {
//                                    found = true;
//                                    break;
//                                }
//                            }
//
//                            if (!found) {
//                                errorMsg.append("班级").append(importVo.getClassName()).append("不存在").append(errSeparator);
//                            } else {
//                                curStudent = pmsStudentService.findBySchoolIdAndGradeAndClassCodeAndName(schoolId, grade, classCode, importVo.getName());
//                                if (curStudent == null) {
//                                    errorMsg.append("学生不存在").append(errSeparator);
//                                } else {
//                                    importVo.setPmsStudent(curStudent);
//                                }
//                            }
//                        } catch (Exception e) {
//                            errorMsg.append("班级格式错误").append(errSeparator);
//                        }
//                    }
//
//
//                    if (StringUtils.isBlank(errorMsg)) {
//                        try {
//                            irMonitorHistoryService.saveStudentHealthMonitorHistoryByImport(schoolId, opeUserId, importVo);
//                        } catch (Exception e) {
//                            errorMsg.append(e.getMessage()).append(errSeparator);
//                        }
//                    }
//
//
//                    return StringUtils.isBlank(errorMsg) ? new ExcelVerifyHandlerResult(true, "导入成功") : new ExcelVerifyHandlerResult(false, errorMsg.toString());
//                });
//
//                ExcelImportResult<TbImportVo> result = ExcelImportUtil.importExcelMore(
//                        inputStream,
//                        TbImportVo.class, importParams);
//
//
//                if (result.isVerfiyFail()) {
//                    String validateExcelFilePath = ProjectConstant.fileUploadLocation + FileUploadType.IMPORT_EXCEL_VALIDATE.getTypePath() + "/" + schoolId + UserUtils.getUserId() + ".xlsx";
//                    result.getFailWorkbook().write(FileUtils.openOutputStream(new File(validateExcelFilePath)));
//                    return new Result(ResultCode.IMPORT_EXCEL_VALIDATE_ERROR.getCode(), "上传文件内容有误", ProjectConstant.hostName + validateExcelFilePath.replace(ProjectConstant.fileUploadLocation, ProjectConstant.fileVisitPrefix));
//                }
//            } catch (MessageException e) {
//                e.printStackTrace();
//                return Result.error(e.getMessage(), null);
//            } catch (Exception e) {
//                e.printStackTrace();
//                return Result.error("上传文件内容有误", null);
//            }
//        }
//        return Result.success(null);
//    }

}
