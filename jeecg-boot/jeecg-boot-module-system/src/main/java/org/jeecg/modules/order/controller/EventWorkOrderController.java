package org.jeecg.modules.order.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.order.entity.EventWorkOrder;
import org.jeecg.modules.order.service.IEventWorkOrderService;

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
 * @Description: 事件工单
 * @Author: jeecg-boot
 * @Date:   2020-07-07
 * @Version: V1.0
 */
@Api(tags="事件工单")
@RestController
@RequestMapping("/order/eventWorkOrder")
@Slf4j
public class EventWorkOrderController extends JeecgController<EventWorkOrder, IEventWorkOrderService> {
	@Autowired
	private IEventWorkOrderService eventWorkOrderService;
	
	/**
	 * 分页列表查询
	 *
	 * @param eventWorkOrder
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "事件工单-分页列表查询")
	@ApiOperation(value="事件工单-分页列表查询", notes="事件工单-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(EventWorkOrder eventWorkOrder,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<EventWorkOrder> queryWrapper = QueryGenerator.initQueryWrapper(eventWorkOrder, req.getParameterMap());
		Page<EventWorkOrder> page = new Page<EventWorkOrder>(pageNo, pageSize);
		IPage<EventWorkOrder> pageList = eventWorkOrderService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param eventWorkOrder
	 * @return
	 */
	@AutoLog(value = "事件工单-添加")
	@ApiOperation(value="事件工单-添加", notes="事件工单-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody EventWorkOrder eventWorkOrder) {
		eventWorkOrderService.save(eventWorkOrder);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param eventWorkOrder
	 * @return
	 */
	@AutoLog(value = "事件工单-编辑")
	@ApiOperation(value="事件工单-编辑", notes="事件工单-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody EventWorkOrder eventWorkOrder) {
		eventWorkOrderService.updateById(eventWorkOrder);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "事件工单-通过id删除")
	@ApiOperation(value="事件工单-通过id删除", notes="事件工单-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		eventWorkOrderService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "事件工单-批量删除")
	@ApiOperation(value="事件工单-批量删除", notes="事件工单-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.eventWorkOrderService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "事件工单-通过id查询")
	@ApiOperation(value="事件工单-通过id查询", notes="事件工单-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		EventWorkOrder eventWorkOrder = eventWorkOrderService.getById(id);
		if(eventWorkOrder==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(eventWorkOrder);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param eventWorkOrder
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, EventWorkOrder eventWorkOrder) {
        return super.exportXls(request, eventWorkOrder, EventWorkOrder.class, "事件工单");
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
        return super.importExcel(request, response, EventWorkOrder.class);
    }

}
