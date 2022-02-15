<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.orderSn" placeholder="訂單號" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查詢</el-button>
        <el-button v-if="isAuth('order:paymentinfo:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量刪除</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
      <el-table-column type="selection" header-align="center" align="center" width="50">
      </el-table-column>
      <el-table-column prop="id" header-align="center" align="center" label="id">
      </el-table-column>
      <el-table-column prop="orderSn" header-align="center" align="center" label="訂單號（對外業務號）">
      </el-table-column>
      <el-table-column prop="orderId" header-align="center" align="center" label="訂單id">
      </el-table-column>
      <el-table-column prop="alipayTradeNo" header-align="center" align="center" label="支付寶交易流水號">
      </el-table-column>
      <el-table-column prop="totalAmount" header-align="center" align="center" label="支付總金額">
      </el-table-column>
      <el-table-column prop="subject" header-align="center" align="center" label="交易內容">
      </el-table-column>
      <el-table-column prop="paymentStatus" header-align="center" align="center" label="支付狀態">
      </el-table-column>
      <el-table-column prop="createTime" header-align="center" align="center" label="創建時間">
      </el-table-column>
      <el-table-column prop="confirmTime" header-align="center" align="center" label="確認時間">
      </el-table-column>
      <el-table-column prop="callbackContent" header-align="center" align="center" label="回調內容">
      </el-table-column>
      <el-table-column prop="callbackTime" header-align="center" align="center" label="回調時間">
      </el-table-column>
      <el-table-column fixed="right" header-align="center" align="center" width="150" label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.id)">刪除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex" :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage" layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <!-- 彈窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
import AddOrUpdate from "./paymentinfo-add-or-update";
export default {
  data() {
    return {
      dataForm: {
        orderSn: "",
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
        url: this.$http.adornUrl("/order/paymentinfo/list"),
        method: "get",
        params: this.$http.adornParams({
          page: this.pageIndex,
          limit: this.pageSize,
          orderSn: this.dataForm.orderSn,
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
          url: this.$http.adornUrl("/order/paymentinfo/delete"),
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
