package org.jeecg.modules.order.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.order.entity.IssueWorkOrder;
import org.jeecg.modules.order.service.IIssueWorkOrderService;

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
 * @Description: 发布工单
 * @Author: jeecg-boot
 * @Date:   2020-07-07
 * @Version: V1.0
 */
@Api(tags="发布工单")
@RestController
@RequestMapping("/order/issueWorkOrder")
@Slf4j
public class IssueWorkOrderController extends JeecgController<IssueWorkOrder, IIssueWorkOrderService> {
	@Autowired
	private IIssueWorkOrderService issueWorkOrderService;
	
	/**
	 * 分页列表查询
	 *
	 * @param issueWorkOrder
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "发布工单-分页列表查询")
	@ApiOperation(value="发布工单-分页列表查询", notes="发布工单-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(IssueWorkOrder issueWorkOrder,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<IssueWorkOrder> queryWrapper = QueryGenerator.initQueryWrapper(issueWorkOrder, req.getParameterMap());
		Page<IssueWorkOrder> page = new Page<IssueWorkOrder>(pageNo, pageSize);
		IPage<IssueWorkOrder> pageList = issueWorkOrderService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param issueWorkOrder
	 * @return
	 */
	@AutoLog(value = "发布工单-添加")
	@ApiOperation(value="发布工单-添加", notes="发布工单-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody IssueWorkOrder issueWorkOrder) {
		issueWorkOrderService.save(issueWorkOrder);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param issueWorkOrder
	 * @return
	 */
	@AutoLog(value = "发布工单-编辑")
	@ApiOperation(value="发布工单-编辑", notes="发布工单-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody IssueWorkOrder issueWorkOrder) {
		issueWorkOrderService.updateById(issueWorkOrder);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "发布工单-通过id删除")
	@ApiOperation(value="发布工单-通过id删除", notes="发布工单-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		issueWorkOrderService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "发布工单-批量删除")
	@ApiOperation(value="发布工单-批量删除", notes="发布工单-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.issueWorkOrderService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "发布工单-通过id查询")
	@ApiOperation(value="发布工单-通过id查询", notes="发布工单-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		IssueWorkOrder issueWorkOrder = issueWorkOrderService.getById(id);
		if(issueWorkOrder==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(issueWorkOrder);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param issueWorkOrder
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, IssueWorkOrder issueWorkOrder) {
        return super.exportXls(request, issueWorkOrder, IssueWorkOrder.class, "发布工单");
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
        return super.importExcel(request, response, IssueWorkOrder.class);
    }

}
