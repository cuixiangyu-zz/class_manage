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
        <a-form-item label="上传课程文件" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-upload v-decorator="['subjectFiles']" :trigger-change="true"></j-upload>
        </a-form-item>
        <a-form-item label="上传教学大纲" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-upload v-decorator="['teachFiles']" :trigger-change="true"></j-upload>
        </a-form-item>
        <a-form-item label="上传校历" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-upload v-decorator="['collegeFiles']" :trigger-change="true"></j-upload>
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
import JUpload from '@/components/jeecg/JUpload'

export default {
  name: "StudentClassFileModal",
  components: {
    JUpload,
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
        get: "/manage/studentClassFile/queryById",
        edit: "/manage/studentClassFile/edit",
      },
      majorOptions: [],
      classOptions: [],
      teacherOptions: [],
    }
  },
  created() {
  },
  methods: {
    add(id) {
      var url = this.url.get + '?id=' + id;
      httpAction(url, '', 'get').then((res) => {
        if (res.success) {
          this.edit(res.result);
        } else {
          that.$message.warning(res.message);
        }
      })
    },
    edit(record) {
      this.form.resetFields();
      this.model = Object.assign({}, record);
      this.visible = true;
      this.$nextTick(() => {
        this.form.setFieldsValue(pick(this.model, 'subjectFiles', 'teachFiles', 'collegeFiles'))
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

          httpurl += this.url.edit;
          method = 'put';

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
      this.form.setFieldsValue(pick(row, 'subjectFiles', 'teachFiles', 'collegeFiles'))
    },
  }
}
</script>