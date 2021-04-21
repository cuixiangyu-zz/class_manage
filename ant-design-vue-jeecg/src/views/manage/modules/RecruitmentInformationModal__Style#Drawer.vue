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

        <a-form-item label="课程" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['subject']" placeholder="请输入课程"></a-input>
        </a-form-item>
        <a-form-item label="年龄" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['age']" placeholder="请输入年龄"></a-input>
        </a-form-item>
        <a-form-item label="学历" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['education']" placeholder="请输入学历"></a-input>
        </a-form-item>
        <a-form-item label="性别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['sex']" placeholder="请输入性别"></a-input>
        </a-form-item>
        <a-form-item label="工龄" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['workingYears']" placeholder="请输入工龄"></a-input>
        </a-form-item>
        <a-form-item label="毕业学校类型:985,211,一本" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['graduateSchoolType']" placeholder="请输入毕业学校类型:985,211,一本"></a-input>
        </a-form-item>
        <a-form-item label="类型:派遣制,外聘制" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['type']" placeholder="请输入类型:派遣制,外聘制"></a-input>
        </a-form-item>
        <a-form-item label="招聘数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['count']" placeholder="请输入招聘数量"></a-input>
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
    name: "RecruitmentInformationModal",
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
          add: "/manage/recruitmentInformation/add",
          edit: "/manage/recruitmentInformation/edit",
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
          this.form.setFieldsValue(pick(this.model,'subject','age','education','sex','workingYears','graduateSchoolType','type','count'))
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
        this.form.setFieldsValue(pick(row,'subject','age','education','sex','workingYears','graduateSchoolType','type','count'))
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