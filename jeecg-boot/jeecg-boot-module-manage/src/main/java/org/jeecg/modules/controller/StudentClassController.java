package org.jeecg.modules.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.modules.entity.StudentClass;
import org.jeecg.modules.service.IStudentClassService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 课程表
 * @Author: jeecg-boot
 * @Date:   2021-01-06
 * @Version: V1.0
 */
@Api(tags="课程表")
@RestController
@RequestMapping("/manage/studentClass")
@Slf4j
public class StudentClassController extends JeecgController<StudentClass, IStudentClassService> {
	@Autowired
	private IStudentClassService studentClassService;
	
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
	@ApiOperation(value="课程表-分页列表查询", notes="课程表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(StudentClass studentClass,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<StudentClass> queryWrapper = QueryGenerator.initQueryWrapper(studentClass, req.getParameterMap());
		Page<StudentClass> page = new Page<StudentClass>(pageNo, pageSize);
		IPage<StudentClass> pageList = studentClassService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param studentClass
	 * @return
	 */
	@AutoLog(value = "课程表-添加")
	@ApiOperation(value="课程表-添加", notes="课程表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody StudentClass studentClass) {
		studentClassService.save(studentClass);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param studentClass
	 * @return
	 */
	@AutoLog(value = "课程表-编辑")
	@ApiOperation(value="课程表-编辑", notes="课程表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody StudentClass studentClass) {
		studentClassService.updateById(studentClass);
		return Result.ok("编辑成功!");
	}

	 /**
	  *
	  * @param studentName	学生名
	  * @param week	周
	  * @param req
	  * @return	课程表
	  */
	 @AutoLog(value = "课程表-课程表查询")
	 @ApiOperation(value="课程表-课程表查询", notes="课程表-课程表查询")
	 @GetMapping(value = "/getClasses")
	 public Result<?> getClasses(String studentName,String week,HttpServletRequest req) {
		 List<Map<String,String>> classes = studentClassService.getClasses(studentName,week);
		 return Result.ok(classes);
	 }
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "课程表-通过id删除")
	@ApiOperation(value="课程表-通过id删除", notes="课程表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		studentClassService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "课程表-批量删除")
	@ApiOperation(value="课程表-批量删除", notes="课程表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
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
	@ApiOperation(value="课程表-通过id查询", notes="课程表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		StudentClass studentClass = studentClassService.getById(id);
		if(studentClass==null) {
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
