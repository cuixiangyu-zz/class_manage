package org.jeecg.modules.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.system.vo.DictModel;
import org.jeecg.modules.entity.CollegeClass;
import org.jeecg.modules.service.ICollegeClassService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;

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
 * @Description: 学院班级表
 * @Author: jeecg-boot
 * @Date:   2020-12-30
 * @Version: V1.0
 */
@Api(tags="学院班级表")
@RestController
@RequestMapping("/manage/collegeClass")
@Slf4j
public class CollegeClassController extends JeecgController<CollegeClass, ICollegeClassService>{
	@Autowired
	private ICollegeClassService collegeClassService;
	
	/**
	 * 分页列表查询
	 *
	 * @param collegeClass
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "学院班级表-分页列表查询")
	@ApiOperation(value="学院班级表-分页列表查询", notes="学院班级表-分页列表查询")
	@GetMapping(value = "/rootList")
	public Result<?> queryPageList(CollegeClass collegeClass,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		String parentId = collegeClass.getPid();
		if (oConvertUtils.isEmpty(parentId)) {
			parentId = "0";
		}
		collegeClass.setPid(null);
		QueryWrapper<CollegeClass> queryWrapper = QueryGenerator.initQueryWrapper(collegeClass, req.getParameterMap());
		// 使用 eq 防止模糊查询
		queryWrapper.eq("pid", parentId);
		Page<CollegeClass> page = new Page<CollegeClass>(pageNo, pageSize);
		IPage<CollegeClass> pageList = collegeClassService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	 /**
      * 获取子数据
      * @param testTree
      * @param req
      * @return
      */
	@AutoLog(value = "学院班级表-获取子数据")
	@ApiOperation(value="学院班级表-获取子数据", notes="学院班级表-获取子数据")
	@GetMapping(value = "/childList")
	public Result<?> queryPageList(CollegeClass collegeClass,HttpServletRequest req) {
		QueryWrapper<CollegeClass> queryWrapper = QueryGenerator.initQueryWrapper(collegeClass, req.getParameterMap());
		List<CollegeClass> list = collegeClassService.list(queryWrapper);
		return Result.ok(list);
	}


	 @AutoLog(value = "学院班级表-获取树形数据")
	 @ApiOperation(value="学院班级表-获取树形数据", notes="学院班级表-获取树形数据")
	 @GetMapping(value = "/queryTreeData")
	 public Result<?> queryTreeData() {
		 return collegeClassService.getCollegeClassTreeData();
	 }
	
	/**
	 *   添加
	 *
	 * @param collegeClass
	 * @return
	 */
	@AutoLog(value = "学院班级表-添加")
	@ApiOperation(value="学院班级表-添加", notes="学院班级表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody CollegeClass collegeClass) {
		return collegeClassService.addCollegeClass(collegeClass);
	}
	
	/**
	 *  编辑
	 *
	 * @param collegeClass
	 * @return
	 */
	@AutoLog(value = "学院班级表-编辑")
	@ApiOperation(value="学院班级表-编辑", notes="学院班级表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody CollegeClass collegeClass) {
		collegeClassService.updateCollegeClass(collegeClass);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "学院班级表-通过id删除")
	@ApiOperation(value="学院班级表-通过id删除", notes="学院班级表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		collegeClassService.deleteCollegeClass(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "学院班级表-批量删除")
	@ApiOperation(value="学院班级表-批量删除", notes="学院班级表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.collegeClassService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "学院班级表-通过id查询")
	@ApiOperation(value="学院班级表-通过id查询", notes="学院班级表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		CollegeClass collegeClass = collegeClassService.getById(id);
		if(collegeClass==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(collegeClass);
	}

	 @AutoLog(value = "学院班级表-通过父code查询")
	 @ApiOperation(value="学院班级表-通过父code查询", notes="学院班级表-通过父code查询")
	 @GetMapping(value = "/getChildOption")
	 public Result<?> getChildOption(@RequestParam(name="code",required=true) String code) {
		 List<DictModel> dictModelList = collegeClassService.getByCode(code);
		 return Result.ok(dictModelList);
	 }

    /**
    * 导出excel
    *
    * @param request
    * @param collegeClass
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CollegeClass collegeClass) {
		return super.exportXls(request, collegeClass, CollegeClass.class, "学院班级表");
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
		return super.importExcel(request, response, CollegeClass.class);
    }

}
