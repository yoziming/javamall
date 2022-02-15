<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.orderSn" placeholder="請輸入訂單號" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.memberUsername" placeholder="請輸入用户名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-select v-model="dataForm.status" clearable placeholder="訂單狀態">
          <el-option label="待處理" value="0"></el-option>
          <el-option label="退貨中" value="1"></el-option>
          <el-option label="已完成" value="2"></el-option>
          <el-option label="已拒絕" value="3"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查詢</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
      <el-table-column type="selection" header-align="center" align="center" width="50">
      </el-table-column>
      <el-table-column prop="id" header-align="center" align="center" label="id">
      </el-table-column>
      <el-table-column prop="orderId" header-align="center" align="center" label="order_id">
      </el-table-column>
      <el-table-column prop="orderSn" header-align="center" align="center" label="訂單編號">
      </el-table-column>
      <el-table-column prop="createTime" header-align="center" align="center" label="申請時間">
      </el-table-column>
      <el-table-column prop="memberUsername" header-align="center" align="center" label="會員用户名">
      </el-table-column>
      <el-table-column prop="returnAmount" header-align="center" align="center" label="退款金額">
      </el-table-column>
      <el-table-column prop="returnName" header-align="center" align="center" label="退貨人姓名">
      </el-table-column>
      <el-table-column prop="returnPhone" header-align="center" align="center" label="退貨人電話">
      </el-table-column>
      <el-table-column prop="status" header-align="center" align="center" label="申請狀態">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status=== 0" type="warning">待處理</el-tag>
          <el-tag v-if="scope.row.status=== 1" type="info">退貨中</el-tag>
          <el-tag v-if="scope.row.status=== 2" type="success">已完成</el-tag>
          <el-tag v-if="scope.row.status=== 3" type="danger">已拒絕</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="handleTime" header-align="center" align="center" label="處理時間">
      </el-table-column>
      <el-table-column prop="reason" header-align="center" align="center" label="原因">
      </el-table-column>
      <el-table-column prop="description" header-align="center" align="center" label="描述">
      </el-table-column>
      <el-table-column prop="handleNote" header-align="center" align="center" label="處理備註">
      </el-table-column>
      <el-table-column prop="receiveMan" header-align="center" align="center" label="收貨人">
      </el-table-column>
      <el-table-column prop="receivePhone" header-align="center" align="center" label="收貨電話">
      </el-table-column>
      <el-table-column prop="companyAddress" header-align="center" align="center" label="公司收貨地址">
      </el-table-column>
      <el-table-column prop="receiveTime" header-align="center" align="center" label="收貨時間">
      </el-table-column>
      <el-table-column prop="receiveNote" header-align="center" align="center" label="收貨備註">
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
import AddOrUpdate from "./orderreturnapply-add-or-update";
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
        url: this.$http.adornUrl("/order/orderreturnapply/list"),
        method: "get",
        params: this.$http.adornParams({
          page: this.pageIndex,
          limit: this.pageSize,
          orderSn: this.dataForm.orderSn,
          memberUsername: this.dataForm.memberUsername,
          status: this.dataForm.status,
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
          url: this.$http.adornUrl("/order/orderreturnapply/delete"),
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
