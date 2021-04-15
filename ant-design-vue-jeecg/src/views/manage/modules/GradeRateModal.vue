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

        <a-form-item label="课程id" v-show="false" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['subjectId']" placeholder="请输入课程id"></a-input>
        </a-form-item>
        <a-form-item label="作业比例" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="['homeworkRate',validatorRules.checkRate]" placeholder="请输入作业比例" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="考勤比例" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="['checkRate',validatorRules.checkRate]" placeholder="请输入考勤比例" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="期中成绩比例" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="['midTermGradeRate',validatorRules.checkRate]" placeholder="请输入期中成绩比例" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="期末成绩比例" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="['finalTermGradeRate',validatorRules.checkRate]" placeholder="请输入期末成绩比例" style="width: 100%"/>
        </a-form-item>

      </a-form>
    </a-spin>
  </j-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import moment from "moment";


  export default {
    name: "GradeRateModal",
    components: { 
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:800,
        visible: false,
        model: {},
        subjectId:'',
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
          checkRate:{rules:[{required: true, message: '请输入比例!'},{validator: this.checkRate}]},
        },
        url: {
          add: "/manage/gradeRate/add",
          edit: "/manage/gradeRate/edit",
        }
      }
    },
    created () {
    },
    methods: {
      add (id) {
        this.subjectId = id
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'subjectId','homeworkRate','checkRate','midTermGradeRate','finalTermGradeRate'))
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
            if(this.subjectId!==null&&this.subjectId!==''){
              formData.subjectId = this.subjectId
            }
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
        this.form.setFieldsValue(pick(row,'subjectId','homeworkRate','checkRate','midTermGradeRate','finalTermGradeRate'))
      },
      checkRate(rule,value,callback){
        let homeworkRate = this.form.getFieldValue("homeworkRate")
        let checkRate = this.form.getFieldValue("checkRate")
        let midTermGradeRate = this.form.getFieldValue("midTermGradeRate")
        let finalTermGradeRate = this.form.getFieldValue("finalTermGradeRate")
        var totle = homeworkRate+checkRate+midTermGradeRate+finalTermGradeRate
        if(totle!==undefined&&totle!==100){
          callback("比例之和必须为100!")
        }else{
          callback()
        }
      },
      
    }
  }
</script>