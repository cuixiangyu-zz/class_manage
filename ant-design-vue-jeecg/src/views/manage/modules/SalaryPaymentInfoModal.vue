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

        <a-form-item label="教师id" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['tercherId']" placeholder="请输入教师id"></a-input>
        </a-form-item>
        <a-form-item label="月份:yyyy-mm" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['month']" placeholder="请输入月份:yyyy-mm"></a-input>
        </a-form-item>
        <a-form-item label="发薪日:yyyy-mm-dd" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['payday']" placeholder="请输入发薪日:yyyy-mm-dd"></a-input>
        </a-form-item>
        <a-form-item label="应发" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['shouldPay']" placeholder="请输入应发"></a-input>
        </a-form-item>
        <a-form-item label="实发" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['actualPay']" placeholder="请输入实发"></a-input>
        </a-form-item>
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['remark']" placeholder="请输入备注"></a-input>
        </a-form-item>

      </a-form>
    </a-spin>
  </j-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'


  export default {
    name: "SalaryPaymentInfoModal",
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
          add: "/manage/salaryPaymentInfo/add",
          edit: "/manage/salaryPaymentInfo/edit",
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
          this.form.setFieldsValue(pick(this.model,'tercherId','month','payday','shouldPay','actualPay','remark'))
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
        this.form.setFieldsValue(pick(row,'tercherId','month','payday','shouldPay','actualPay','remark'))
      },

      
    }
  }
</script>