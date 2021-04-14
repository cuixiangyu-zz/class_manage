<template>
  <a-drawer
    :title="title"
    :width="width"
    placement="right"
    :closable="false"
    @close="close"
    :visible="visible">
  
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="主表id" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['studentGradeId']" placeholder="请输入主表id"></a-input>
        </a-form-item>
        <a-form-item label="课程id" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['subjectId']" placeholder="请输入课程id"></a-input>
        </a-form-item>
        <a-form-item label="作业成绩" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="['homeworkGrade']" placeholder="请输入作业成绩" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="考勤成绩" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="['checkGrade']" placeholder="请输入考勤成绩" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="期中成绩" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="['midTermGrade']" placeholder="请输入期中成绩" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="期末成绩" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="['finalTermGrade']" placeholder="请输入期末成绩" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="总成绩" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="['totleGrade']" placeholder="请输入总成绩" style="width: 100%"/>
        </a-form-item>
        
      </a-form>
    </a-spin>
    <a-button type="primary" @click="handleOk">确定</a-button>
    <a-button type="primary" @click="handleCancel">取消</a-button>
  </a-drawer>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  
  export default {
    name: "StudentGradeItemModal",
    components: { 
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
        },
        url: {
          add: "/manage/studentGradeItem/add",
          edit: "/manage/studentGradeItem/edit",
        }
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
          this.form.setFieldsValue(pick(this.model,'studentGradeId','subjectId','homeworkGrade','checkGrade','midTermGrade','finalTermGrade','totleGrade'))
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
        this.form.setFieldsValue(pick(row,'studentGradeId','subjectId','homeworkGrade','checkGrade','midTermGrade','finalTermGrade','totleGrade'))
      }
      
    }
  }
</script>

<style lang="less" scoped>
/** Button按钮间距 */
  .ant-btn {
    margin-left: 30px;
    margin-bottom: 30px;
    float: right;
  }
</style>