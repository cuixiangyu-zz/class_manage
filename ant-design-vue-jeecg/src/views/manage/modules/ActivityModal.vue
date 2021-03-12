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

        <a-form-item label="活动名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['name', { rules: [{ required: true}] }]" placeholder="请输入活动名称"></a-input>
        </a-form-item>
        <a-form-item label="活动开始时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择活动开始时间" v-decorator="['startTime', { rules: [{ required: true}] }]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="活动结束时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择活动结束时间" v-decorator="['endTime', { rules: [{ required: true}] }]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="班级" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-tree
            v-model="checkedKeys"
            checkable
            :expanded-keys="expandedKeys"
            :auto-expand-parent="autoExpandParent"
            :selected-keys="selectedKeys"
            :tree-data="treeData"
            @expand="onExpand"
            @select="onSelect"
          />
          <a-input style="display: none" v-decorator="['classes', { rules: [{ required: true}] }]" placeholder="请输入班级"></a-input>
        </a-form-item>
        <a-form-item label="介绍" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['introduce']" placeholder="请输入介绍"></a-textarea>
        </a-form-item>
        <a-form-item label="活动图片" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-image-upload isMultiple v-decorator="['pics']"></j-image-upload>
        </a-form-item>

      </a-form>
    </a-spin>
  </j-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JDate from '@/components/jeecg/JDate'  
  import JImageUpload from '@/components/jeecg/JImageUpload'


  export default {
    name: "ActivityModal",
    components: { 
      JDate,
      JImageUpload,
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
          add: "/manage/activity/add",
          edit: "/manage/activity/edit",
          treeData: "/manage/collegeClass/queryTreeData",
        },
        expandedKeys: [],
        autoExpandParent: true,
        checkedKeys: [],
        selectedKeys: [],
        treeData: [],
      }
    },
    watch: {
      checkedKeys(val) {
        console.log('onCheck', val);
      },
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.checkedKeys = []
        this.queryTreeData()
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'name','startTime','endTime','classes','introduce','pics'))
        })
        this.checkedKeys = JSON.parse(record.classes)
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        var classes = '';
        for (let checkedKey of this.checkedKeys) {
          classes += checkedKey + ','
        }
        this.form.setFieldsValue({
          classes: classes
        })
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
        this.form.setFieldsValue(pick(row,'name','startTime','endTime','classes','introduce','pics'))
      },
      onExpand(expandedKeys) {
        console.log('onExpand', expandedKeys);
        // if not set autoExpandParent to false, if children expanded, parent can not collapse.
        // or, you can remove all expanded children keys.
        this.expandedKeys = expandedKeys;
        this.autoExpandParent = false;
      },
      onCheck(checkedKeys) {
        console.log('onCheck', checkedKeys);
        this.checkedKeys = checkedKeys;
      },
      onSelect(selectedKeys, info) {
        console.log('onSelect', info);
        console.log('onSelect', this.checkedKeys);
        this.selectedKeys = selectedKeys;
      },
      queryTreeData () {
        httpAction(this.url.treeData,null,'get').then((res)=>{
          if(res.success){
            console.log(res)
            this.treeData = res.result
          }else{
            that.$message.warning(res.message);
          }
        })
      },
      
    }
  }
</script>