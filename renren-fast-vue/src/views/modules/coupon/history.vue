<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="參數名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查詢</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
      <el-table-column prop="id" header-align="center" align="center" label="id"></el-table-column>
      <el-table-column prop="couponId" header-align="center" align="center" label="優惠券id"></el-table-column>
      <el-table-column prop="memberId" header-align="center" align="center" label="會員id"></el-table-column>
      <el-table-column prop="memberNickName" header-align="center" align="center" label="會員名字"></el-table-column>
      <el-table-column prop="getType" header-align="center" align="center" label="獲取方式">
        <template slot-scope="scope">
          <el-tag type="primary" v-if="scope.row.getType==0">後台贈送</el-tag>
          <el-tag type="success" v-else>主動領取</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" header-align="center" align="center" label="創建時間"></el-table-column>
      <el-table-column prop="useType" header-align="center" align="center" label="使用狀態">
        <template slot-scope="scope">
          <el-tag type="primary" v-if="scope.row.useType==0">未使用</el-tag>
          <el-tag type="success" v-if="scope.row.useType==1">已使用</el-tag>
          <el-tag type="warning" v-if="scope.row.useType==2">已過期</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="useTime" header-align="center" align="center" label="使用時間"></el-table-column>
      <el-table-column prop="orderId" header-align="center" align="center" label="訂單id"></el-table-column>
      <el-table-column prop="orderSn" header-align="center" align="center" label="訂單號"></el-table-column>
    </el-table>
    <el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex" :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage" layout="total, sizes, prev, pager, next, jumper"></el-pagination>
    <!-- 彈窗, 新增 / 修改 -->
  </div>
</template>

<script>
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
  components: {},
  activated() {
    this.getDataList();
  },
  methods: {
    // 獲取數據列表
    getDataList() {
      this.dataListLoading = true;
      this.$http({
        url: this.$http.adornUrl("/coupon/couponhistory/list"),
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
    deleteHandle(id) {
      var ids = id
        ? [id]
        : this.dataListSelections.map((item) => {
            return item.id;
          });
      this.$confirm(
        `確定對[id=${ids.join(",")}]進行[${id ? "刪除" : "批量刪除"}]操作?`,
        "提示",
        {
          confirmButtonText: "確定",
          cancelButtonText: "取消",
          type: "warning",
        }
      ).then(() => {
        this.$http({
          url: this.$http.adornUrl("/coupon/couponhistory/delete"),
          method: "post",
          data: this.$http.adornData(ids, false),
        }).then(({ data }) => {
          if (data && data.code === 0) {
            this.$message({
              message: "操作成功",
              type: "success",
              duration: 1500,
              onClose: () => {
                this.getDataList();
              },
            });
          } else {
            this.$message.error(data.msg);
          }
        });
      });
    },
  },
};
</script>
