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
import org.jeecg.modules.entity.EmploymentAgreement;
import org.jeecg.modules.service.IEmploymentAgreementService;

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
 * @Description: 聘任协议
 * @Author: jeecg-boot
 * @Date:   2021-04-21
 * @Version: V1.0
 */
@Api(tags="聘任协议")
@RestController
@RequestMapping("/manage/employmentAgreement")
@Slf4j
public class EmploymentAgreementController extends JeecgController<EmploymentAgreement, IEmploymentAgreementService> {
	@Autowired
	private IEmploymentAgreementService employmentAgreementService;
	
	/**
	 * 分页列表查询
	 *
	 * @param employmentAgreement
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "聘任协议-分页列表查询")
	@ApiOperation(value="聘任协议-分页列表查询", notes="聘任协议-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(EmploymentAgreement employmentAgreement,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<EmploymentAgreement> queryWrapper = QueryGenerator.initQueryWrapper(employmentAgreement, req.getParameterMap());
		Page<EmploymentAgreement> page = new Page<EmploymentAgreement>(pageNo, pageSize);
		IPage<EmploymentAgreement> pageList = employmentAgreementService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param employmentAgreement
	 * @return
	 */
	@AutoLog(value = "聘任协议-添加")
	@ApiOperation(value="聘任协议-添加", notes="聘任协议-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody EmploymentAgreement employmentAgreement) {
		employmentAgreementService.save(employmentAgreement);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param employmentAgreement
	 * @return
	 */
	@AutoLog(value = "聘任协议-编辑")
	@ApiOperation(value="聘任协议-编辑", notes="聘任协议-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody EmploymentAgreement employmentAgreement) {
		employmentAgreementService.updateById(employmentAgreement);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "聘任协议-通过id删除")
	@ApiOperation(value="聘任协议-通过id删除", notes="聘任协议-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		employmentAgreementService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "聘任协议-批量删除")
	@ApiOperation(value="聘任协议-批量删除", notes="聘任协议-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.employmentAgreementService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "聘任协议-通过id查询")
	@ApiOperation(value="聘任协议-通过id查询", notes="聘任协议-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		EmploymentAgreement employmentAgreement = employmentAgreementService.getById(id);
		if(employmentAgreement==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(employmentAgreement);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param employmentAgreement
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, EmploymentAgreement employmentAgreement) {
        return super.exportXls(request, employmentAgreement, EmploymentAgreement.class, "聘任协议");
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
        return super.importExcel(request, response, EmploymentAgreement.class);
    }

}
