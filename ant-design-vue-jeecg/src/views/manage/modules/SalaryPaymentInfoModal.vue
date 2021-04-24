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
        <a-form-item label="教师" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag
            ref="teacherSelect"
            placeholder="请选择教师"
            v-decorator="['tercherId']"
            :triggerChange="true"
            :dictOptions="teacherOptions">
          </j-search-select-tag>
        </a-form-item>
        <a-form-item label="月份" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-month-picker showTime format="YYYY-MM" v-decorator="['month']" placeholder="请输入月份"></a-month-picker>
        </a-form-item>
        <a-form-item label="发薪日" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-date-picker showTime format="YYYY-MM-DD" v-decorator="['payday']" placeholder="请输入发薪日"></a-date-picker>
        </a-form-item>
        <a-form-item label="应发" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="['shouldPay']" placeholder="请输入应发"></a-input-number>
        </a-form-item>
        <a-form-item label="实发" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="['actualPay']" placeholder="请输入实发"></a-input-number>
        </a-form-item>
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['remark']" placeholder="请输入备注"></a-input>
        </a-form-item>

      </a-form>
    </a-spin>
  </j-modal>
</template>

<script>

import {httpAction} from '@/api/manage'
import pick from 'lodash.pick'
import {validateDuplicateValue} from '@/utils/util'
import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
import JDictSelectTag from "@comp/dict/JDictSelectTag";
import moment from 'moment';

export default {
  name: "SalaryPaymentInfoModal",
  components: {
    JSearchSelectTag,
    JDictSelectTag,
  },
  data() {
    return {
      form: this.$form.createForm(this),
      title: "操作",
      width: 800,
      visible: false,
      model: {},
      labelCol: {
        xs: {span: 24},
        sm: {span: 5},
      },
      wrapperCol: {
        xs: {span: 24},
        sm: {span: 16},
      },
      confirmLoading: false,
      validatorRules: {},
      url: {
        add: "/manage/salaryPaymentInfo/add",
        edit: "/manage/salaryPaymentInfo/edit",
      },
      teacherOptions: []
    }
  },
  created() {
    this.getTeacherList();
  },
  methods: {
    add() {
      this.edit({});
    },
    edit(record) {
      this.form.resetFields();
      this.model = Object.assign({}, record);
      this.visible = true;
      this.$nextTick(() => {
        this.form.setFieldsValue(pick(this.model, 'tercherId', 'month', 'payday', 'shouldPay', 'actualPay', 'remark'))
      })
    },
    close() {
      this.$emit('close');
      this.visible = false;
    },
    handleOk() {
      const that = this;
      // 触发表单验证
      this.form.validateFields((err, values) => {
        if (!err) {
          that.confirmLoading = true;
          let httpurl = '';
          let method = '';
          if (!this.model.id) {
            httpurl += this.url.add;
            method = 'post';
          } else {
            httpurl += this.url.edit;
            method = 'put';
          }
          let formData = Object.assign(this.model, values);
          console.log("表单提交数据", formData)
          formData.month = moment(new Date(formData.month)).format('YYYY-MM')
          formData.payday = moment(new Date(formData.payday)).format('YYYY-MM-DD')

          httpAction(httpurl, formData, method).then((res) => {
            if (res.success) {
              that.$message.success(res.message);
              that.$emit('ok');
            } else {
              that.$message.warning(res.message);
            }
          }).finally(() => {
            that.confirmLoading = false;
            that.close();
          })
        }

      })
    },
    handleCancel() {
      this.close()
    },
    popupCallback(row) {
      this.form.setFieldsValue(pick(row, 'tercherId', 'month', 'payday', 'shouldPay', 'actualPay', 'remark'))
    },
    getTeacherList() {
      const that = this;
      var httpurl = '/manage/teacherInfo/getTeacherListForSalary'
      httpAction(httpurl, null, 'get').then((res) => {
        if (res.success) {
          that.teacherOptions = res.result
          //that.$refs.teacherSelect.setCurrentDictOptions(this.teacherOptions);
        } else {
          that.$message.warning(res.message);
        }
      })
    },

  },
  mounted() {
    //this.getTeacherList();
  },
}
</script>