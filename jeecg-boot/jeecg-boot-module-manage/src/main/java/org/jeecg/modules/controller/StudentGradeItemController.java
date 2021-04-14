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
import org.jeecg.modules.entity.StudentGradeItem;
import org.jeecg.modules.service.IStudentGradeItemService;

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
 * @Description: 成绩子表
 * @Author: jeecg-boot
 * @Date:   2021-04-14
 * @Version: V1.0
 */
@Api(tags="成绩子表")
@RestController
@RequestMapping("/manage/studentGradeItem")
@Slf4j
public class StudentGradeItemController extends JeecgController<StudentGradeItem, IStudentGradeItemService> {
	@Autowired
	private IStudentGradeItemService studentGradeItemService;
	
	/**
	 * 分页列表查询
	 *
	 * @param studentGradeItem
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "成绩子表-分页列表查询")
	@ApiOperation(value="成绩子表-分页列表查询", notes="成绩子表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(StudentGradeItem studentGradeItem,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<StudentGradeItem> queryWrapper = QueryGenerator.initQueryWrapper(studentGradeItem, req.getParameterMap());
		Page<StudentGradeItem> page = new Page<StudentGradeItem>(pageNo, pageSize);
		IPage<StudentGradeItem> pageList = studentGradeItemService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param studentGradeItem
	 * @return
	 */
	@AutoLog(value = "成绩子表-添加")
	@ApiOperation(value="成绩子表-添加", notes="成绩子表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody StudentGradeItem studentGradeItem) {
		studentGradeItemService.save(studentGradeItem);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param studentGradeItem
	 * @return
	 */
	@AutoLog(value = "成绩子表-编辑")
	@ApiOperation(value="成绩子表-编辑", notes="成绩子表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody StudentGradeItem studentGradeItem) {
		studentGradeItemService.updateById(studentGradeItem);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "成绩子表-通过id删除")
	@ApiOperation(value="成绩子表-通过id删除", notes="成绩子表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		studentGradeItemService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "成绩子表-批量删除")
	@ApiOperation(value="成绩子表-批量删除", notes="成绩子表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.studentGradeItemService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "成绩子表-通过id查询")
	@ApiOperation(value="成绩子表-通过id查询", notes="成绩子表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		StudentGradeItem studentGradeItem = studentGradeItemService.getById(id);
		if(studentGradeItem==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(studentGradeItem);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param studentGradeItem
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, StudentGradeItem studentGradeItem) {
        return super.exportXls(request, studentGradeItem, StudentGradeItem.class, "成绩子表");
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
        return super.importExcel(request, response, StudentGradeItem.class);
    }

}
