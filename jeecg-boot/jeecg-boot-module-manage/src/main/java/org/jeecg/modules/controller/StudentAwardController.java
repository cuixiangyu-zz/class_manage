package org.jeecg.modules.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.modules.entity.StudentAward;
import org.jeecg.modules.service.IStudentAwardService;
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
 * @Description: 获奖学生信息
 * @Author: jeecg-boot
 * @Date:   2021-01-06
 * @Version: V1.0
 */
@Api(tags="获奖学生信息")
@RestController
@RequestMapping("/manage/studentAward")
@Slf4j
public class StudentAwardController extends JeecgController<StudentAward, IStudentAwardService> {
	@Autowired
	private IStudentAwardService studentAwardService;
	
	/**
	 * 分页列表查询
	 *
	 * @param studentAward
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "获奖学生信息-分页列表查询")
	@ApiOperation(value="获奖学生信息-分页列表查询", notes="获奖学生信息-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(StudentAward studentAward,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<StudentAward> queryWrapper = QueryGenerator.initQueryWrapper(studentAward, req.getParameterMap());
		Page<StudentAward> page = new Page<StudentAward>(pageNo, pageSize);
		IPage<StudentAward> pageList = studentAwardService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param studentAward
	 * @return
	 */
	@AutoLog(value = "获奖学生信息-添加")
	@ApiOperation(value="获奖学生信息-添加", notes="获奖学生信息-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody StudentAward studentAward) {
		studentAwardService.save(studentAward);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param studentAward
	 * @return
	 */
	@AutoLog(value = "获奖学生信息-编辑")
	@ApiOperation(value="获奖学生信息-编辑", notes="获奖学生信息-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody StudentAward studentAward) {
		studentAwardService.updateById(studentAward);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "获奖学生信息-通过id删除")
	@ApiOperation(value="获奖学生信息-通过id删除", notes="获奖学生信息-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		studentAwardService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "获奖学生信息-批量删除")
	@ApiOperation(value="获奖学生信息-批量删除", notes="获奖学生信息-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.studentAwardService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "获奖学生信息-通过id查询")
	@ApiOperation(value="获奖学生信息-通过id查询", notes="获奖学生信息-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		StudentAward studentAward = studentAwardService.getById(id);
		if(studentAward==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(studentAward);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param studentAward
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, StudentAward studentAward) {
        return super.exportXls(request, studentAward, StudentAward.class, "获奖学生信息");
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
        return super.importExcel(request, response, StudentAward.class);
    }

}
