package org.jeecg.modules.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.extension.api.R;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.entity.TeacherInfo;
import org.jeecg.modules.service.ITeacherInfoService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 教师信息
 * @Author: jeecg-boot
 * @Date:   2021-04-21
 * @Version: V1.0
 */
@Api(tags="教师信息")
@RestController
@RequestMapping("/manage/teacherInfo")
@Slf4j
public class TeacherInfoController extends JeecgController<TeacherInfo, ITeacherInfoService> {
	@Autowired
	private ITeacherInfoService teacherInfoService;

	
	/**
	 * 分页列表查询
	 *
	 * @param teacherInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "教师信息-分页列表查询")
	@ApiOperation(value="教师信息-分页列表查询", notes="教师信息-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(TeacherInfo teacherInfo,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		if(teacherInfoService.checkIsTeacher(sysUser.getId())){
			teacherInfo.setBaseInfoId(sysUser.getId());
		}
		QueryWrapper<TeacherInfo> queryWrapper = QueryGenerator.initQueryWrapper(teacherInfo, req.getParameterMap());
		Page<TeacherInfo> page = new Page<TeacherInfo>(pageNo, pageSize);
		IPage<TeacherInfo> pageList = teacherInfoService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param teacherInfo
	 * @return
	 */
	@AutoLog(value = "教师信息-添加")
	@ApiOperation(value="教师信息-添加", notes="教师信息-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody TeacherInfo teacherInfo) {
		if(StringUtils.isEmpty(teacherInfo.getWorkStatus())){
			teacherInfo.setWorkStatus("quit");
		}
		if(StringUtils.isEmpty(teacherInfo.getBaseInfoId())){
			String teacherName = teacherInfoService.getTeacherName(teacherInfo.getBaseInfoId());
			teacherInfo.setName(teacherName);
		}
		teacherInfoService.save(teacherInfo);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param teacherInfo
	 * @return
	 */
	@AutoLog(value = "教师信息-编辑")
	@ApiOperation(value="教师信息-编辑", notes="教师信息-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody TeacherInfo teacherInfo) {
		if(!StringUtils.isEmpty(teacherInfo.getBaseInfoId())){
			String teacherName = teacherInfoService.getTeacherName(teacherInfo.getBaseInfoId());
			teacherInfo.setName(teacherName);
		}
		teacherInfoService.updateById(teacherInfo);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "教师信息-通过id删除")
	@ApiOperation(value="教师信息-通过id删除", notes="教师信息-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		teacherInfoService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "教师信息-批量删除")
	@ApiOperation(value="教师信息-批量删除", notes="教师信息-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.teacherInfoService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "教师信息-通过id查询")
	@ApiOperation(value="教师信息-通过id查询", notes="教师信息-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		TeacherInfo teacherInfo = teacherInfoService.getById(id);
		if(teacherInfo==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(teacherInfo);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param teacherInfo
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, TeacherInfo teacherInfo) {
        return super.exportXls(request, teacherInfo, TeacherInfo.class, "教师信息");
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
        return super.importExcel(request, response, TeacherInfo.class);
    }

    @GetMapping("/getTeacherListForSalary")
    public Result<?> getTeacherListForSalary(){
		return teacherInfoService.getTeacherListForSalary();
	}

	 @GetMapping("/getUserList")
	 public Result<?> getUserList(){
		 return teacherInfoService.getUserList();
	 }

	 @GetMapping("/getAddTeacherList")
	 public Result<?> getAddTeacherList(String recruitmentInformationId){
		 return teacherInfoService.getAddTeacherList(recruitmentInformationId);
	 }
}
