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

        <a-form-item label="学生" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag
            @change="changeStudent"
            placeholder="请做出你的选择"
            v-decorator="['studentId', { rules: [{ required: true}] }]"
            dict="student,name,id"
          >
          </j-search-select-tag>
        </a-form-item>
        <a-form-item label="学年" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag @change="changeXn" type="list" v-decorator="['xn']" :triggerChange="true"
                             placeholder="请输入学年"
                             dictCode="entrance_year"/>
        </a-form-item>
        <a-form-item label="学期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag @change="changeXq" type="list" v-decorator="['xq']" :triggerChange="true"
                             placeholder="请输入学期"
                             dictCode="xq"/>
        </a-form-item>
        <a-form-item label="作业" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag
            ref="taskSelect"
            placeholder="请选择作业"
            v-decorator="['taskId',{ rules: [{ required: true}] }]"
            :triggerChange="true"
            :dictOptions="taskOptions">
          </j-search-select-tag>
        </a-form-item>
        <a-form-item label="成绩" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="['results',validatorRules.checkGrade]" placeholder="请输入成绩" style="width: 100%"/>
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


export default {
  name: "TaskResultsModal",
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
      validatorRules: {
        checkGrade: {rules: [{required: true, message: '请输入成绩'}, {validator: this.checkGrade}]},
      },
      url: {
        add: "/manage/taskResults/add",
        edit: "/manage/taskResults/edit",
      },
      taskOptions: []
    }
  },
  created() {
  },
  methods: {
    add() {
      this.edit({});
    },
    edit(record) {
      if (record !== undefined && record !== null) {
        this.initStudent(record);
      }
      this.form.resetFields();
      this.model = Object.assign({}, record);
      this.visible = true;
      this.$nextTick(() => {
        this.form.setFieldsValue(pick(this.model, 'studentId', 'taskId', 'results'))
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
      this.form.setFieldsValue(pick(row, 'studentId', 'taskId', 'results'))
    },
    changeStudent(e) {
      this.taskOptions = []
      this.form.setFieldsValue({taskId: '', results: null})
      var studentId = e
      var xq = this.form.getFieldValue('xq')
      var xn = this.form.getFieldValue('xn')
      if (studentId === undefined || studentId === null || studentId === '') {
        return
      }
      if (xq === undefined || xq === null || xq === '') {
        xq = ''
      }
      if (xn === undefined || xn === null || xn === '') {
        xn = ''
      }
      if (e !== undefined && e !== null && e !== '') {
        var httpurl = '/manage/studentTask/getTaskList?studentId=' + studentId + '&xq=' + xq + '&xn=' + xn
        httpAction(httpurl, null, 'get').then((res) => {
          if (res.success) {
            this.taskOptions = res.result
            this.$refs.taskSelect.setCurrentDictOptions(this.taskOptions);
          } else {
            that.$message.warning(res.message);
          }
        })
      }
    },

    changeXn(e) {
      this.taskOptions = []
      this.form.setFieldsValue({taskId: '', results: null})
      var studentId = this.form.getFieldValue('studentId')
      var xq = this.form.getFieldValue('xq')
      var xn = e
      if (studentId === undefined || studentId === null || studentId === '') {
        return
      }
      if (xq === undefined || xq === null || xq === '') {
        xq = ''
      }
      if (xn === undefined || xn === null || xn === '') {
        xn = ''
      }
      if (e !== undefined && e !== null && e !== '') {
        var httpurl = '/manage/studentTask/getTaskList?studentId=' + studentId + '&xq=' + xq + '&xn=' + xn
        httpAction(httpurl, null, 'get').then((res) => {
          if (res.success) {
            this.taskOptions = res.result
            this.$refs.taskSelect.setCurrentDictOptions(this.taskOptions);
          } else {
            that.$message.warning(res.message);
          }
        })
      }
    },

    changeXq(e) {
      this.taskOptions = []
      this.form.setFieldsValue({taskId: '', results: null})
      var studentId = this.form.getFieldValue('studentId')
      var xq = e
      var xn = this.form.getFieldValue('xn')
      if (studentId === undefined || studentId === null || studentId === '') {
        return
      }
      if (xq === undefined || xq === null || xq === '') {
        xq = ''
      }
      if (xn === undefined || xn === null || xn === '') {
        xn = ''
      }
      if (e !== undefined && e !== null && e !== '') {
        var httpurl = '/manage/studentTask/getTaskList?studentId=' + studentId + '&xq=' + xq + '&xn=' + xn
        httpAction(httpurl, null, 'get').then((res) => {
          if (res.success) {
            this.taskOptions = res.result
            this.$refs.taskSelect.setCurrentDictOptions(this.taskOptions);
          } else {
            that.$message.warning(res.message);
          }
        })
      }
    },
    initStudent(record) {
      this.taskOptions = []
      var studentId = record.studentId
      var xq = ''
      var xn = ''
      var httpurl = '/manage/studentTask/getTaskList?studentId=' + studentId + '&xq=' + xq + '&xn=' + xn
      httpAction(httpurl, null, 'get').then((res) => {
        if (res.success) {
          this.taskOptions = res.result
          this.$refs.taskSelect.setCurrentDictOptions(this.taskOptions);
        } else {
          that.$message.warning(res.message);
        }
      })

    },
    checkGrade(rule, value, callback) {
      let results = this.form.getFieldValue("results")
      if (results > 100 || results < 0) {
        callback("分数在0-100之间!")
      } else {
        callback()
      }
    },
  }
}
</script>