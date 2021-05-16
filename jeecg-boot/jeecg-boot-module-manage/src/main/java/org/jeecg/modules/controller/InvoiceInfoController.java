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
import org.jeecg.modules.entity.InvoiceInfo;
import org.jeecg.modules.service.IInvoiceInfoService;

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
 * @Description: 发票信息
 * @Author: jeecg-boot
 * @Date:   2021-05-16
 * @Version: V1.0
 */
@Api(tags="发票信息")
@RestController
@RequestMapping("/manage/invoiceInfo")
@Slf4j
public class InvoiceInfoController extends JeecgController<InvoiceInfo, IInvoiceInfoService> {
	@Autowired
	private IInvoiceInfoService invoiceInfoService;
	
	/**
	 * 分页列表查询
	 *
	 * @param invoiceInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "发票信息-分页列表查询")
	@ApiOperation(value="发票信息-分页列表查询", notes="发票信息-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(InvoiceInfo invoiceInfo,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<InvoiceInfo> queryWrapper = QueryGenerator.initQueryWrapper(invoiceInfo, req.getParameterMap());
		Page<InvoiceInfo> page = new Page<InvoiceInfo>(pageNo, pageSize);
		IPage<InvoiceInfo> pageList = invoiceInfoService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param invoiceInfo
	 * @return
	 */
	@AutoLog(value = "发票信息-添加")
	@ApiOperation(value="发票信息-添加", notes="发票信息-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody InvoiceInfo invoiceInfo) {
		invoiceInfoService.save(invoiceInfo);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param invoiceInfo
	 * @return
	 */
	@AutoLog(value = "发票信息-编辑")
	@ApiOperation(value="发票信息-编辑", notes="发票信息-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody InvoiceInfo invoiceInfo) {
		invoiceInfoService.updateById(invoiceInfo);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "发票信息-通过id删除")
	@ApiOperation(value="发票信息-通过id删除", notes="发票信息-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		invoiceInfoService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "发票信息-批量删除")
	@ApiOperation(value="发票信息-批量删除", notes="发票信息-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.invoiceInfoService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "发票信息-通过id查询")
	@ApiOperation(value="发票信息-通过id查询", notes="发票信息-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		InvoiceInfo invoiceInfo = invoiceInfoService.getById(id);
		if(invoiceInfo==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(invoiceInfo);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param invoiceInfo
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, InvoiceInfo invoiceInfo) {
        return super.exportXls(request, invoiceInfo, InvoiceInfo.class, "发票信息");
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
        return super.importExcel(request, response, InvoiceInfo.class);
    }

}
