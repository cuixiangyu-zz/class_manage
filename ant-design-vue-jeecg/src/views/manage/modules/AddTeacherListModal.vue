<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    switchFullscreen
    @ok="handleCancel"
    @cancel="visible=false"
    cancelText="关闭">
    <a-spin :spinning="confirmLoading">
    <!-- table区域-begin -->
      <div>
        <div style="margin-bottom: 16px">
          <a-button type="primary" :disabled="!hasSelected" :loading="confirmLoading" @click="batchDel">
            Reload
          </a-button>
          <span style="margin-left: 8px">
        <template v-if="hasSelected">
          {{ `Selected ${selectedRowKeys.length} items` }}
        </template>
      </span>
        </div>
        <a-table
          :row-selection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
          :columns="columns"
          :data-source="dataSource"
        />
      </div>
    </a-spin>
  </j-modal>
</template>
<script>

  import '@/assets/less/TableExpand.less'
  import { httpAction } from '@/api/manage'
  import { mixinDevice } from '@/utils/mixin'
  //import { JeecgListMixin } from '@/mixins/JeecgListMixin'

  export default {
    name: "AddTeacherListModal",
    mixins:[mixinDevice],
    components: {
    },
    data () {
      return {
        description: '教师信息管理页面',
        // 表头
        columns: [

          {
            title:'任教科目',
            align:"center",
            dataIndex: 'subject_dictText'
          },
          {
            title:'学历',
            align:"center",
            dataIndex: 'education_dictText'
          },
          {
            title:'工龄',
            align:"center",
            dataIndex: 'workingYears'
          },
          {
            title:'毕业学校类型',
            align:"center",
            dataIndex: 'graduateSchoolType_dictText'
          }
        ],
        url: {
          list: "/manage/teacherInfo/getAddTeacherList",
          delete: "/manage/teacherInfo/delete",
          deleteBatch: "/manage/teacherInfo/deleteBatch",
          exportXlsUrl: "/manage/teacherInfo/exportXls",
          importExcelUrl: "manage/teacherInfo/importExcel",
        },
        dictOptions:{},
        title:"操作",
        width:800,
        visible: false,
        model: {},
        confirmLoading: false,
        selectedRowKeys: [],
        /* table选中records*/
        selectionRows: [],
        dataSource:[]
      }
    },
    created() {
      //this.loadData();
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
      hasSelected() {
        return this.selectedRowKeys.length > 0;
      },
    },
    methods: {
      initDictConfig(){
      },
      start() {
        this.loading = true;
        // ajax request after empty completing
        setTimeout(() => {
          this.loading = false;
          this.selectedRowKeys = [];
        }, 1000);
      },
      onSelectChange(selectedRowKeys) {
        console.log('selectedRowKeys changed: ', selectedRowKeys);
        this.selectedRowKeys = selectedRowKeys;
      },
      batchDel: function () {
        if (this.selectedRowKeys.length <= 0) {
          this.$message.warning('请选择一条记录！');
          return;
        } else {
          console.log(this.selectedRowKeys);
          /*var ids = "";
          for (var a = 0; a < this.selectedRowKeys.length; a++) {
            ids += this.selectedRowKeys[a] + ",";
          }
          var that = this;
          this.$confirm({
            title: "确认删除",
            content: "是否删除选中数据?",
            onOk: function () {
              that.loading = true;
              deleteAction(that.url.deleteBatch, {ids: ids}).then((res) => {
                if (res.success) {
                  that.$message.success(res.message);
                  that.loadData();
                  that.onClearSelected();
                } else {
                  that.$message.warning(res.message);
                }
              }).finally(() => {
                that.loading = false;
              });
            }
          });*/
        }
      },
      loadData(id) {
        if(!this.url.list){
          this.$message.error("请设置url.list属性!")
          return
        }
        this.visible = true
        //加载数据 若传入参数1则加载第一页的内容
        var params = {
          id:id
        };//查询条件
        this.loading = true;
        var url = this.url.list+ '?recruitmentInformationId='+id;
        httpAction(url,params,'get').then((res) => {
          if (res.success) {
            this.dataSource = res.result;
            //this.ipagination.total = res.result.total;
          }
          if(res.code===510){
            this.$message.warning(res.message)
          }
          this.loading = false;
        })
      },
      handleCancel () {
        this.close()
      },
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>