package org.jeecg.modules.service.impl;

import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.entity.*;
import org.jeecg.modules.mapper.StudentCheckMapper;
import org.jeecg.modules.mapper.StudentClassMapper;
import org.jeecg.modules.mapper.TaskResultsMapper;
import org.jeecg.modules.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 作业成绩
 * @Author: jeecg-boot
 * @Date:   2021-04-14
 * @Version: V1.0
 */
@Service
public class TaskResultsServiceImpl extends ServiceImpl<TaskResultsMapper, TaskResults> implements ITaskResultsService {

    @Autowired
    TaskResultsMapper taskResultsMapper;

    @Autowired
    StudentClassMapper studentClassMapper;

    @Autowired
    StudentCheckMapper studentCheckMapper;

    @Autowired
    IGradeRateService gradeRateService;

    @Autowired
    IStudentGradeService studentGradeService;

    @Autowired
    IStudentGradeItemService studentGradeItemService;

    @Autowired
    IStudentClassService studentClassService;

    @Override
    public boolean save(TaskResults entity) {
        TaskResults taskResults = taskResultsMapper.getByTaskAndStudent(entity.getTaskId(), entity.getStudentId());
        if(taskResults!=null){
            entity.setId(taskResults.getId());
            return super.updateById(entity);
        }
        return super.save(entity);
    }

    /**
     * 更新成绩
     * @param taskResults   成绩
     * @return  成功
     */
    @Override
    public Result updateTask(TaskResults taskResults){
        if(taskResults==null|| StringUtils.isEmpty(taskResults.getTaskId())||StringUtils.isEmpty(taskResults.getStudentId())){
            return Result.error("信息不全");
        }
        TaskResults oldResult = taskResultsMapper.getByTaskAndStudent(taskResults.getTaskId(), taskResults.getStudentId());
        if(oldResult==null){
            this.save(taskResults);
        }else{
            oldResult.setResults(taskResults.getResults());
            this.updateById(oldResult);
        }
        return Result.ok();
    }

    @Override
    public List<StudentGradeItem> calculateTask(String studentId, String xq, String xn) {

        List<StudentGradeItem> result = new ArrayList<>();
        //获取课程列表
        List<Map<String,Object>> classList = studentClassMapper.getClassListByStudent(studentId, xq, xn);
        if(classList==null||classList.isEmpty()){
            return null;
        }
        StudentGrade studentGrade = new StudentGrade();
        StudentGrade old = studentGradeService.getByStudentAndXqXn(studentId,xq,xn);
        if(old!=null){
            studentGrade = old;
        }else{
            studentGrade.setStudentId(studentId);
            studentGrade.setXn(xn);
            studentGrade.setXq(xq);

            studentGradeService.save(studentGrade);
        }
        studentGradeItemService.delByStudentGradeId(studentGrade.getId());
        for (Map<String, Object> classMap : classList) {
            String subjectId = classMap.get("id").toString();
            StudentGradeItem studentGradeItem = new StudentGradeItem();
            studentGradeItem.setStudentGradeId(studentGrade.getId());
            studentGradeItem.setSubjectId(subjectId);
            //作业成绩
            Double taskGrade = taskResultsMapper.calculateGrade(studentId,subjectId);
            studentGradeItem.setHomeworkGrade(taskGrade==null?100d:taskGrade);

            //考勤成绩
            Double checkGrade = studentCheckMapper.calculateGrade(studentId,subjectId);
            if(checkGrade==null){
                studentGradeItem.setCheckGrade(100d);
            }else if(checkGrade>=100){
                studentGradeItem.setCheckGrade(0d);
            }else{
                studentGradeItem.setCheckGrade(100-checkGrade);
            }

            //期中成绩
            Double midGrade = taskResultsMapper.getByTaskType(studentId,subjectId,"mid_term");
            studentGradeItem.setMidTermGrade(midGrade==null?0d:midGrade);
            //期末成绩
            Double finalGrade = taskResultsMapper.getByTaskType(studentId,subjectId,"final_term");
            studentGradeItem.setFinalTermGrade(finalGrade==null?0d:finalGrade);

            GradeRate gradeRate = gradeRateService.getBySubjectId(subjectId);

            Double totle = 0d;

            totle = (studentGradeItem.getHomeworkGrade()*gradeRate.getHomeworkRate() + studentGradeItem.getCheckGrade()*gradeRate.getCheckRate()
                    + studentGradeItem.getMidTermGrade()*gradeRate.getMidTermGradeRate() + studentGradeItem.getFinalTermGrade()*gradeRate.getFinalTermGradeRate())/100;
            studentGradeItem.setTotleGrade(totle);
            studentGradeItemService.save(studentGradeItem);
            StudentClass studentClass = studentClassService.getById(studentGradeItem.getSubjectId());
            if(studentClass!=null){
                studentGradeItem.setSubjectName(studentClass.getSubjectName());
            }
            result.add(studentGradeItem);
        }
        return result;
    }

    @Override
    public List<StudentGradeItem> getGradeList(String studentId, String xq, String xn) {

        StudentGrade studentGrade = studentGradeService.getByStudentAndXqXn(studentId, xq, xn);

        if(studentGrade==null){
            return this.calculateTask(studentId, xq, xn);
        }

        List<StudentGradeItem> result = studentGradeItemService.getByStudentGradeId(studentGrade.getId());

        if(result==null||result.isEmpty()){
            return this.calculateTask(studentId, xq, xn);
        }
        for (StudentGradeItem studentGradeItem : result) {
            StudentClass studentClass = studentClassService.getById(studentGradeItem.getSubjectId());
            if(studentClass!=null){
                studentGradeItem.setSubjectName(studentClass.getSubjectName());
            }
        }
        return result;
    }
}
