package org.jeecg.modules.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.modules.entity.StudentLeave;
import org.jeecg.modules.service.IStudentLeaveService;
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
 * @Description: 请假
 * @Author: jeecg-boot
 * @Date:   2021-01-06
 * @Version: V1.0
 */
@Api(tags="请假")
@RestController
@RequestMapping("/manage/studentLeave")
@Slf4j
public class StudentLeaveController extends JeecgController<StudentLeave, IStudentLeaveService> {
	@Autowired
	private IStudentLeaveService studentLeaveService;
	
	/**
	 * 分页列表查询
	 *
	 * @param studentLeave
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "请假-分页列表查询")
	@ApiOperation(value="请假-分页列表查询", notes="请假-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(StudentLeave studentLeave,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<StudentLeave> queryWrapper = QueryGenerator.initQueryWrapper(studentLeave, req.getParameterMap());
		Page<StudentLeave> page = new Page<StudentLeave>(pageNo, pageSize);
		IPage<StudentLeave> pageList = studentLeaveService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param studentLeave
	 * @return
	 */
	@AutoLog(value = "请假-添加")
	@ApiOperation(value="请假-添加", notes="请假-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody StudentLeave studentLeave) {
		studentLeaveService.save(studentLeave);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param studentLeave
	 * @return
	 */
	@AutoLog(value = "请假-编辑")
	@ApiOperation(value="请假-编辑", notes="请假-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody StudentLeave studentLeave) {
		studentLeaveService.updateById(studentLeave);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "请假-通过id删除")
	@ApiOperation(value="请假-通过id删除", notes="请假-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		studentLeaveService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "请假-批量删除")
	@ApiOperation(value="请假-批量删除", notes="请假-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.studentLeaveService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "请假-通过id查询")
	@ApiOperation(value="请假-通过id查询", notes="请假-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		StudentLeave studentLeave = studentLeaveService.getById(id);
		if(studentLeave==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(studentLeave);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param studentLeave
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, StudentLeave studentLeave) {
        return super.exportXls(request, studentLeave, StudentLeave.class, "请假");
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
        return super.importExcel(request, response, StudentLeave.class);
    }

}
