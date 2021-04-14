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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.entity.GradeRate;
import org.jeecg.modules.service.IGradeRateService;
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
 * @Description: 成绩比例
 * @Author: jeecg-boot
 * @Date:   2021-04-14
 * @Version: V1.0
 */
@Api(tags="成绩比例")
@RestController
@RequestMapping("/manage/gradeRate")
@Slf4j
public class GradeRateController extends JeecgController<GradeRate, IGradeRateService> {
	@Autowired
	private IGradeRateService gradeRateService;
	
	/**
	 * 分页列表查询
	 *
	 * @param gradeRate
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "成绩比例-分页列表查询")
	@ApiOperation(value="成绩比例-分页列表查询", notes="成绩比例-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(GradeRate gradeRate,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<GradeRate> queryWrapper = QueryGenerator.initQueryWrapper(gradeRate, req.getParameterMap());
		Page<GradeRate> page = new Page<GradeRate>(pageNo, pageSize);
		IPage<GradeRate> pageList = gradeRateService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param gradeRate
	 * @return
	 */
	@AutoLog(value = "成绩比例-添加")
	@ApiOperation(value="成绩比例-添加", notes="成绩比例-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody GradeRate gradeRate) {
		gradeRateService.save(gradeRate);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param gradeRate
	 * @return
	 */
	@AutoLog(value = "成绩比例-编辑")
	@ApiOperation(value="成绩比例-编辑", notes="成绩比例-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody GradeRate gradeRate) {
		gradeRateService.updateById(gradeRate);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "成绩比例-通过id删除")
	@ApiOperation(value="成绩比例-通过id删除", notes="成绩比例-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		gradeRateService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "成绩比例-批量删除")
	@ApiOperation(value="成绩比例-批量删除", notes="成绩比例-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.gradeRateService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "成绩比例-通过id查询")
	@ApiOperation(value="成绩比例-通过id查询", notes="成绩比例-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		GradeRate gradeRate = gradeRateService.getById(id);
		if(gradeRate==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(gradeRate);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param gradeRate
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, GradeRate gradeRate) {
        return super.exportXls(request, gradeRate, GradeRate.class, "成绩比例");
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
        return super.importExcel(request, response, GradeRate.class);
    }

}
