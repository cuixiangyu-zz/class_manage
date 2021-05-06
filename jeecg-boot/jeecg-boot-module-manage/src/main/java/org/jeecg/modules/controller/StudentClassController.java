package org.jeecg.modules.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.PmsUtil;
import org.jeecg.modules.entity.StudentClass;
import org.jeecg.modules.entity.StudentClassFile;
import org.jeecg.modules.entity.TeacherInfo;
import org.jeecg.modules.service.IStudentClassFileService;
import org.jeecg.modules.service.IStudentClassService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.service.ITeacherInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: 课程表
 * @Author: jeecg-boot
 * @Date: 2021-01-06
 * @Version: V1.0
 */
@Api(tags = "课程表")
@RestController
@RequestMapping("/manage/studentClass")
@Slf4j
public class StudentClassController extends JeecgController<StudentClass, IStudentClassService> {
    @Autowired
    private IStudentClassService studentClassService;
    @Autowired
    private ITeacherInfoService teacherInfoService;
    @Autowired
    private ISysBaseAPI sysBaseAPI;
    @Autowired
    private IStudentClassFileService studentClassFileService;

    private final String MSG_TITLE = "新增课程提醒";

    private final String MSG_TEMPLATE_CODE = "teacher_subject";

    /**
     * 分页列表查询
     *
     * @param studentClass
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "课程表-分页列表查询")
    @ApiOperation(value = "课程表-分页列表查询", notes = "课程表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(StudentClass studentClass,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        //如果是教师登录只能查看自己的课程信息
        boolean collegeDirector = teacherInfoService.checkRole(sysUser.getUsername(), "teacher");
        if(collegeDirector){
            studentClass.setTeacher(teacherInfoService.getTeacherByUserId(sysUser.getId()).getId());
        }

        QueryWrapper<StudentClass> queryWrapper = QueryGenerator.initQueryWrapper(studentClass, req.getParameterMap());
        Page<StudentClass> page = new Page<StudentClass>(pageNo, pageSize);
        IPage<StudentClass> pageList = studentClassService.page(page, queryWrapper);
        for (StudentClass record : pageList.getRecords()) {
            record.setTeacherName(teacherInfoService.getNameByTeacherId(record.getTeacher()));
        }
        return Result.ok(pageList);
    }

    /**
     * 添加
     *
     * @param studentClass
     * @return
     */
    @AutoLog(value = "课程表-添加")
    @ApiOperation(value = "课程表-添加", notes = "课程表-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody StudentClass studentClass) {
        studentClassService.save(studentClass);

        String subjectName = studentClassService.getsubjectNameByCode(studentClass.getSubjectName());
        TeacherInfo teacherInfo = teacherInfoService.getById(studentClass.getTeacher());
        LoginUser toUser = sysBaseAPI.getUserById(teacherInfo.getBaseInfoId());
        Map<String, String> map = new HashMap();
        map.put("subject_name", subjectName);
        map.put("teacher_name", toUser.getRealname());
        //调用消息推送保存接口
        LoginUser fromUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        sysBaseAPI.sendSysAnnouncement(fromUser.getUsername(), toUser.getUsername(),MSG_TITLE,map,MSG_TEMPLATE_CODE );

        //添加上传文件
        StudentClassFile studentClassFile = new StudentClassFile();
        studentClassFile.setId(studentClass.getId());
        studentClassFileService.save(studentClassFile);

        return Result.ok("添加成功！");
    }

    /**
     * 编辑
     *
     * @param studentClass
     * @return
     */
    @AutoLog(value = "课程表-编辑")
    @ApiOperation(value = "课程表-编辑", notes = "课程表-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody StudentClass studentClass) {
        studentClassService.updateById(studentClass);
        return Result.ok("编辑成功!");
    }

    /**
     * @param studentName 学生名
     * @param week        周
     * @param req
     * @return 课程表
     */
    @AutoLog(value = "课程表-课程表查询")
    @ApiOperation(value = "课程表-课程表查询", notes = "课程表-课程表查询")
    @GetMapping(value = "/getClasses")
    public Result<?> getClasses(String studentName, String week, String xn, String xq, HttpServletRequest req) {
        List<Map<String, String>> classes = studentClassService.getClasses(studentName, week, xn, xq);
        return Result.ok(classes);
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "课程表-通过id删除")
    @ApiOperation(value = "课程表-通过id删除", notes = "课程表-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        studentClassService.removeById(id);
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "课程表-批量删除")
    @ApiOperation(value = "课程表-批量删除", notes = "课程表-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.studentClassService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "课程表-通过id查询")
    @ApiOperation(value = "课程表-通过id查询", notes = "课程表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        StudentClass studentClass = studentClassService.getById(id);
        if (studentClass == null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(studentClass);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param studentClass
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, StudentClass studentClass) {
        return super.exportXls(request, studentClass, StudentClass.class, "课程表");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, StudentClass.class);
    }

}
