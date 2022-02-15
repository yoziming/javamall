<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="參數名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查詢</el-button>
        <!-- <el-button v-if="isAuth('member:member:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('member:member:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量刪除</el-button>-->
      </el-form-item>
    </el-form>
    <el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
      <el-table-column prop="id" header-align="center" align="center" label="id"></el-table-column>
      <el-table-column prop="levelId" header-align="center" align="center" label="會員等級"></el-table-column>
      <el-table-column prop="username" header-align="center" align="center" label="用户名"></el-table-column>
      <el-table-column prop="nickname" header-align="center" align="center" label="暱稱"></el-table-column>
      <el-table-column prop="mobile" header-align="center" align="center" label="手機號碼"></el-table-column>
      <el-table-column prop="email" header-align="center" align="center" label="郵箱"></el-table-column>
      <el-table-column prop="header" header-align="center" align="center" label="頭像"></el-table-column>
      <el-table-column prop="gender" header-align="center" align="center" label="性別"></el-table-column>
      <el-table-column prop="birth" header-align="center" align="center" label="生日"></el-table-column>
      <el-table-column prop="city" header-align="center" align="center" label="所在城市"></el-table-column>
      <el-table-column prop="job" header-align="center" align="center" label="職業"></el-table-column>
      <el-table-column prop="sign" header-align="center" align="center" label="個性簽名"></el-table-column>
      <el-table-column prop="sourceType" header-align="center" align="center" label="用户來源"></el-table-column>
      <el-table-column prop="integration" header-align="center" align="center" label="積分"></el-table-column>
      <el-table-column prop="growth" header-align="center" align="center" label="成長值"></el-table-column>
      <el-table-column prop="status" header-align="center" align="center" label="啓用狀態">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.status" active-color="#13ce66" inactive-color="#ff4949" :active-value="1" :inactive-value="0"></el-switch>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" header-align="center" align="center" label="註冊時間"></el-table-column>
      <el-table-column fixed="right" header-align="center" align="center" width="150" label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small">送券</el-button>
          <el-button type="text" size="small">查訂單</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex" :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage" layout="total, sizes, prev, pager, next, jumper"></el-pagination>
    <!-- 彈窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
import AddOrUpdate from "./member-add-or-update";
export default {
  data() {
    return {
      dataForm: {
        key: "",
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      addOrUpdateVisible: false,
    };
  },
  components: {
    AddOrUpdate,
  },
  activated() {
    this.getDataList();
  },
  methods: {
    // 獲取數據列表
    getDataList() {
      this.dataListLoading = true;
      this.$http({
        url: this.$http.adornUrl("/member/member/list"),
        method: "get",
        params: this.$http.adornParams({
          page: this.pageIndex,
          limit: this.pageSize,
          key: this.dataForm.key,
        }),
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.dataList = data.page.list;
          this.totalPage = data.page.totalCount;
        } else {
          this.dataList = [];
          this.totalPage = 0;
        }
        this.dataListLoading = false;
      });
    },
    // 每頁數
    sizeChangeHandle(val) {
      this.pageSize = val;
      this.pageIndex = 1;
      this.getDataList();
    },
    // 當前頁
    currentChangeHandle(val) {
      this.pageIndex = val;
      this.getDataList();
    },
    // 多選
    selectionChangeHandle(val) {
      this.dataListSelections = val;
    },
    // 新增 / 修改
    addOrUpdateHandle(id) {
      this.addOrUpdateVisible = true;
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id);
      });
    },
    // 刪除
    //   deleteHandle (id) {
    //     var ids = id ? [id] : this.dataListSelections.map(item => {
    //       return item.id
    //     })
    //     this.$confirm(`確定對[id=${ids.join(',')}]進行[${id ? '刪除' : '批量刪除'}]操作?`, '提示', {
    //       confirmButtonText: '確定',
    //       cancelButtonText: '取消',
    //       type: 'warning'
    //     }).then(() => {
    //       this.$http({
    //         url: this.$http.adornUrl('/member/member/delete'),
    //         method: 'post',
    //         data: this.$http.adornData(ids, false)
    //       }).then(({data}) => {
    //         if (data && data.code === 0) {
    //           this.$message({
    //             message: '操作成功',
    //             type: 'success',
    //             duration: 1500,
    //             onClose: () => {
    //               this.getDataList()
    //             }
    //           })
    //         } else {
    //           this.$message.error(data.msg)
    //         }
    //       })
    //     })
    //   }
  },
};
</script>
