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
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.entity.StudentCheck;
import org.jeecg.modules.service.IStudentCheckService;

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
 * @Description: 考勤表
 * @Author: jeecg-boot
 * @Date:   2021-04-14
 * @Version: V1.0
 */
@Api(tags="考勤表")
@RestController
@RequestMapping("/manage/studentCheck")
@Slf4j
public class StudentCheckController extends JeecgController<StudentCheck, IStudentCheckService> {
	@Autowired
	private IStudentCheckService studentCheckService;
	
	/**
	 * 分页列表查询
	 *
	 * @param studentCheck
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "考勤表-分页列表查询")
	@ApiOperation(value="考勤表-分页列表查询", notes="考勤表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(StudentCheck studentCheck,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<StudentCheck> queryWrapper = QueryGenerator.initQueryWrapper(studentCheck, req.getParameterMap());
		Page<StudentCheck> page = new Page<StudentCheck>(pageNo, pageSize);
		IPage<StudentCheck> pageList = studentCheckService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param studentCheck
	 * @return
	 */
	@AutoLog(value = "考勤表-添加")
	@ApiOperation(value="考勤表-添加", notes="考勤表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody StudentCheck studentCheck) {
		studentCheckService.save(studentCheck);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param studentCheck
	 * @return
	 */
	@AutoLog(value = "考勤表-编辑")
	@ApiOperation(value="考勤表-编辑", notes="考勤表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody StudentCheck studentCheck) {
		studentCheckService.updateById(studentCheck);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "考勤表-通过id删除")
	@ApiOperation(value="考勤表-通过id删除", notes="考勤表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		studentCheckService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "考勤表-批量删除")
	@ApiOperation(value="考勤表-批量删除", notes="考勤表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.studentCheckService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "考勤表-通过id查询")
	@ApiOperation(value="考勤表-通过id查询", notes="考勤表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		StudentCheck studentCheck = studentCheckService.getById(id);
		if(studentCheck==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(studentCheck);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param studentCheck
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, StudentCheck studentCheck) {
        return super.exportXls(request, studentCheck, StudentCheck.class, "考勤表");
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
        return super.importExcel(request, response, StudentCheck.class);
    }

}
