<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="学院">
              <j-dict-select-tag @change="changeMajor" :triggerChange="true" v-model="queryParam.name"
                                 placeholder="请选择用户名称" dictCode="college_class,name,code,type = 'institute'"/>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="专业">
              <j-search-select-tag
                @change="changeClass"
                ref="majorSelect"
                placeholder="请选择专业"
                v-model="queryParam.studentCode"
                :triggerChange="true"
                :dictOptions="majorOptions">
              </j-search-select-tag>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="班级">
              <j-search-select-tag
                ref="classSelect"
                placeholder="请选择班级"
                v-model="queryParam.className"
                :triggerChange="true"
                :dictOptions="classOptions">
              </j-search-select-tag>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="课程周">
              <a-week-picker v-model="queryParam.week"  placeholder="请输入课程开始周" format="YYYY-ww"/>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="学年">
              <j-dict-select-tag type="list" v-model="queryParam.xn" :triggerChange="true" placeholder="请输入学年"
                                 dictCode="entrance_year"/>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="学期">
              <j-dict-select-tag type="list" v-model="queryParam.xq" :triggerChange="true" placeholder="请输入学期"
                                 dictCode="xq"/>
            </a-form-item>
          </a-col>

          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button type="primary" icon="download" @click="handleExportXls('学生表')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
    </div>

    <!-- table区域-begin -->
    <div>
      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :loading="loading"
        class="j-table-force-nowrap"
        @change="handleTableChange">

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="uploadFile(text)">
            下载
          </a-button>
        </template>

      </a-table>
    </div>
  </a-card>
</template>

<script>

import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import StudentModal from './modules/StudentModal'
import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
import JDictSelectTag from "@comp/dict/JDictSelectTag";
import moment from 'moment';
import {httpAction} from "@api/manage";

export default {
  name: "ClasssesForm",
  mixins:[JeecgListMixin, mixinDevice],
  components: {
    JSearchSelectTag,
    StudentModal,
    JDictSelectTag,
  },
  data () {
    return {
      description: '学生课程表页面',
      // 表头
      columns: [
        {
          title: '时间',
          align: "center",
          dataIndex: 'section'
        },
        {
          title: '星期一',
          align: "center",
          dataIndex: '星期一'
        },
        {
          title: '星期二',
          align: "center",
          dataIndex: '星期二'
        },
        {
          title: '星期三',
          align: "center",
          dataIndex: '星期三'
        },
        {
          title: '星期四',
          align: "center",
          dataIndex: '星期四'
        },
        {
          title: '星期五',
          align: "center",
          dataIndex: '星期五'
        },
        {
          title: '星期六',
          align: "center",
          dataIndex: '星期六'
        },
        {
          title: '星期日',
          align: "center",
          dataIndex: '星期日'
        },
      ],
      url: {
        list: "/manage/studentClass/getClasses",
        delete: "/manage/student/delete",
        deleteBatch: "/manage/student/deleteBatch",
        exportXlsUrl: "/manage/student/exportXls",
        importExcelUrl: "manage/student/importExcel",
      },
      dictOptions:{},
      majorOptions: [],
      classOptions: [],
    }
  },
  computed: {
    importExcelUrl: function(){
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
    },
  },
  methods: {
    initDictConfig(){
    },
    changeMajor(e) {
      this.queryParam.major=''
      this.queryParam.className=''
      if (e!==undefined&&e!== null && e !== '') {
        var httpurl = '/manage/collegeClass/getChildOption?code=' + e
        httpAction(httpurl, null, 'get').then((res) => {
          if (res.success) {
            this.majorOptions = res.result
            this.$refs.majorSelect.setCurrentDictOptions(this.majorOptions);
            console.log(this.majorOptions)
          } else {
            that.$message.warning(res.message);
          }
        })
      }
    },
    changeClass(e) {
      this.queryParam.className=''
      if (e!==undefined&&e!== null && e !== '') {
        var httpurl = '/manage/collegeClass/getChildOption?code=' + e
        httpAction(httpurl, null, 'get').then((res) => {
          if (res.success) {
            this.classOptions = res.result
            this.$refs.classSelect.setCurrentDictOptions(this.classOptions);
            console.log(this.classOptions)
          } else {
            that.$message.warning(res.message);
          }
        })
      }
    },
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>