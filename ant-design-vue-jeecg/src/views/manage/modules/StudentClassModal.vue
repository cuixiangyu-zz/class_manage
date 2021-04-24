<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    switchFullscreen
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="学院" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag @change="changeMajor" :triggerChange="true" v-decorator="['institute', { rules: [{ required: true}] }]"
                             placeholder="请选择用户名称" dictCode="college_class,name,code,type = 'institute'"/>
          <!--          <j-search-select-tag v-decorator="['institute']" dict="" />-->
        </a-form-item>
        <a-form-item label="专业" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag
            @change="changeClass"
            ref="majorSelect"
            placeholder="请选择专业"
            v-decorator="['major']"
            :triggerChange="true"
            :dictOptions="majorOptions">
          </j-search-select-tag>
        </a-form-item>
        <a-form-item label="班级" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag
            ref="classSelect"
            placeholder="请选择班级"
            v-decorator="['className']"
            :triggerChange="true"
            :dictOptions="classOptions">
          </j-search-select-tag>
          <!--          <j-dict-select-tag :triggerChange="true" v-decorator="['className']" placeholder="请选择用户名称"
                                       dictCode="college_class,name,code,type = 'class'"/>-->
          <!--          <j-search-select-tag v-decorator="['class']" dict="" />-->
        </a-form-item>
        <a-form-item label="周几" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['weekDay', { rules: [{ required: true}] }]" :triggerChange="true" placeholder="请输入周几"
                             dictCode="week_num"/>
<!--          <a-input v-decorator="['weekDay']" placeholder="请输入周几"></a-input>-->
        </a-form-item>
        <a-form-item label="第几节课程" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['section', { rules: [{ required: true}] }]" :triggerChange="true" placeholder="请输入第几节课程"
                             dictCode="sections"/>
<!--          <a-input v-decorator="['section']" placeholder="请输入第几节课程"></a-input>-->
        </a-form-item>
        <a-form-item label="课程名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['subjectName', { rules: [{ required: true}] }]" placeholder="请输入课程名称"></a-input>
        </a-form-item>

        <a-form-item label="老师" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag
            placeholder="请选择审批教师"
            v-decorator="['teacher', { rules: [{ required: true}] }]"
            dict="sys_user,realname,id"
            :async="false">
          </j-search-select-tag>
<!--          <a-input v-decorator="['teacher']" placeholder="请输入老师"></a-input>-->
        </a-form-item>
        <a-form-item label="课程详细周数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['weekSeq']" placeholder="请输入课程详细周数"></a-input>
        </a-form-item>
        <a-form-item label="课程开始周" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-week-picker v-decorator="['weekStart', validatorRules.weekStart]"  placeholder="请输入课程开始周" format="YYYY-ww"/>
<!--          <a-input v-decorator="['weekStart']" placeholder="请输入课程开始周"></a-input>-->
        </a-form-item>
        <a-form-item label="课程结束周" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-week-picker v-decorator="['weekEnd', validatorRules.weekEnd]"  placeholder="请输入课程结束周" format="YYYY-ww" />
<!--          <a-input v-decorator="['weekEnd']" placeholder="请输入课程结束周"></a-input>-->
        </a-form-item>
        <a-form-item label="单双周" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['isSingle', { rules: [{ required: true}] }]" :triggerChange="true" placeholder="请选择"
                             dictCode="is_single"/>
          <!--          <a-input v-decorator="['xn']" placeholder="请输入学年"></a-input>-->
        </a-form-item>
        <a-form-item label="教室" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['location']" placeholder="请输入教室"></a-input>
        </a-form-item>
        <a-form-item label="学年" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['xn', { rules: [{ required: true}] }]" :triggerChange="true" placeholder="请输入学年"
                              dictCode="entrance_year"/>
        </a-form-item>
        <a-form-item label="学期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['xq', { rules: [{ required: true}] }]" :triggerChange="true" placeholder="请输入学期"
                             dictCode="xq"/>
        </a-form-item>
      </a-form>
    </a-spin>
  </j-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
  import JDictSelectTag from "@comp/dict/JDictSelectTag";
  import moment from 'moment';

  export default {
    name: "StudentClassModal",
    components: {
      JSearchSelectTag,
      JDictSelectTag,
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:800,
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
          weekEnd:{rules:[{ required: true, message: '请选择结束时间!'} ,{validator: this.endTimeValidate}]},
          weekStart:{rules:[{required: true, message: '请选择开始时间!'},{validator: this.startTimeValidate}]},
        },
        url: {
          add: "/manage/studentClass/add",
          edit: "/manage/studentClass/edit",
        },
        majorOptions: [],
        classOptions: [],
      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'weekDay','section','subjectName','teacher','weekSeq','weekStart','weekEnd','location','xn','xq', 'institute', 'major', 'className'))
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            let startTime = this.form.getFieldValue("weekStart")
            let endTime = this.form.getFieldValue("weekEnd")
            formData.weekStart = moment(new Date(startTime)).format('YYYY-ww')
            formData.weekEnd = moment(new Date(endTime)).format('YYYY-ww')
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }
         
        })
      },
      handleCancel () {
        this.close()
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'weekDay','section','subjectName','teacher','weekSeq','weekStart','weekEnd','location','xn','xq', 'institute', 'major', 'className'))
      },
      changeMajor(e) {
        this.form.setFieldsValue({major:'',className:''})
        if (e!==undefined&&e!== null && e !== '') {
          var httpurl = '/manage/collegeClass/getChildOption?code=' + e
          httpAction(httpurl, null, 'get').then((res) => {
            if (res.success) {
              this.majorOptions = res.result
              this.$refs.majorSelect.setCurrentDictOptions(this.majorOptions);
              console.log(this.majorOptions)
            } else {
              that.$message.warning(res.message);
            }
          })
        }
      },
      changeClass(e) {
        this.form.setFieldsValue({className:''})
        if (e!==undefined&&e!== null && e !== '') {
          var httpurl = '/manage/collegeClass/getChildOption?code=' + e
          httpAction(httpurl, null, 'get').then((res) => {
            if (res.success) {
              this.classOptions = res.result
              this.$refs.classSelect.setCurrentDictOptions(this.classOptions);
              console.log(this.classOptions)
            } else {
              that.$message.warning(res.message);
            }
          })
        }
      },
      startTimeValidate(rule,value,callback){
        let endTime = this.form.getFieldValue("weekEnd")
        if(!value || !endTime){
          callback()
        }else if(moment(value).isBefore(endTime)){
          callback()
        }else{
          callback("开始时间需小于结束时间")
        }
      },
      endTimeValidate(rule,value,callback){
        let startTime = this.form.getFieldValue("weekStart")
        if(!value || !startTime){
          callback()
        }else if(moment(startTime).isBefore(value)){
          callback()
        }else{
          callback("结束时间需大于开始时间")
        }
      }
    }
  }
</script>