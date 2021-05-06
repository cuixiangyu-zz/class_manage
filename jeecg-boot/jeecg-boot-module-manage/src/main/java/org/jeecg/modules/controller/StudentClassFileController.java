package org.jeecg.modules.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.entity.Activity;
import org.jeecg.modules.entity.StudentClassFile;
import org.jeecg.modules.service.IActivityService;
import org.jeecg.modules.service.IStudentClassFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
* @Description: 活动
* @Author: jeecg-boot
* @Date:   2021-01-06
* @Version: V1.0
*/
@Api(tags="活动")
@RestController
@RequestMapping("/manage/studentClassFile")
@Slf4j
public class StudentClassFileController extends JeecgController<StudentClassFile, IStudentClassFileService> {
   @Autowired
   private IStudentClassFileService studentClassFileService;


   /**
    *  编辑
    *
    * @param studentClassFile
    * @return
    */
   @AutoLog(value = "活动-编辑")
   @ApiOperation(value="活动-编辑", notes="活动-编辑")
   @PutMapping(value = "/edit")
   public Result<?> edit(@RequestBody StudentClassFile studentClassFile) {
       studentClassFileService.updateById(studentClassFile);
       return Result.ok("编辑成功!");
   }

   /**
    * 通过id查询
    *
    * @param id
    * @return
    */
   @AutoLog(value = "活动-通过id查询")
   @ApiOperation(value="活动-通过id查询", notes="活动-通过id查询")
   @GetMapping(value = "/queryById")
   public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
       StudentClassFile studentClassFile = studentClassFileService.getById(id);
       if(studentClassFile==null) {
           return Result.error("未找到对应数据");
       }
       return Result.ok(studentClassFile);
   }

   /**
   * 导出excel
   *
   * @param request
   * @param studentClassFile
   */
   @RequestMapping(value = "/exportXls")
   public ModelAndView exportXls(HttpServletRequest request, StudentClassFile studentClassFile) {
       return super.exportXls(request, studentClassFile, StudentClassFile.class, "活动");
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
       return super.importExcel(request, response, StudentClassFile.class);
   }

}
