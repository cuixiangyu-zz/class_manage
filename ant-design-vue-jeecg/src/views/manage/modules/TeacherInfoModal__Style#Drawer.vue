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

        <a-form-item label="基础信息表id" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['baseInfoId']" placeholder="请输入基础信息表id"></a-input>
        </a-form-item>
        <a-form-item label="试讲结果" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['trialLecture']" placeholder="请输入试讲结果"></a-input>
        </a-form-item>
        <a-form-item label="月工资" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['wages']" placeholder="请输入月工资"></a-input>
        </a-form-item>
        <a-form-item label="评价结果" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['evaluation']" placeholder="请输入评价结果"></a-input>
        </a-form-item>
        <a-form-item label="上传文件:协议书,学历证明" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-upload v-decorator="['files']" :trigger-change="true"></j-upload>
        </a-form-item>
        <a-form-item label="任教科目" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['subject']" placeholder="请输入任教科目"></a-input>
        </a-form-item>
        <a-form-item label="学历" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['education']" placeholder="请输入学历"></a-input>
        </a-form-item>
        <a-form-item label="工龄" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['workingYears']" placeholder="请输入工龄"></a-input>
        </a-form-item>
        <a-form-item label="毕业学校类型:985,211,一本" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['graduateSchoolType']" placeholder="请输入毕业学校类型:985,211,一本"></a-input>
        </a-form-item>
        <a-form-item label="工作状态:已聘任,未聘任" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['workStatus']" placeholder="请输入工作状态:已聘任,未聘任"></a-input>
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
  import JUpload from '@/components/jeecg/JUpload'
  
  export default {
    name: "TeacherInfoModal",
    components: { 
      JUpload,
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
          add: "/manage/teacherInfo/add",
          edit: "/manage/teacherInfo/edit",
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
          this.form.setFieldsValue(pick(this.model,'baseInfoId','trialLecture','wages','evaluation','files','subject','education','workingYears','graduateSchoolType','workStatus'))
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
        this.form.setFieldsValue(pick(row,'baseInfoId','trialLecture','wages','evaluation','files','subject','education','workingYears','graduateSchoolType','workStatus'))
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