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
          <el-option label="待付款" value="0"></el-option>
          <el-option label="待發貨" value="1"></el-option>
          <el-option label="已發貨" value="2"></el-option>
          <el-option label="已完成" value="3"></el-option>
          <el-option label="已關閉" value="4"></el-option>
          <el-option label="售後中" value="5"></el-option>
          <el-option label="售後完成" value="6"></el-option>
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
      <el-table-column prop="memberId" header-align="center" align="center" label="member_id">
      </el-table-column>
      <el-table-column prop="orderSn" header-align="center" align="center" label="訂單號">
      </el-table-column>
      <el-table-column prop="createTime" header-align="center" align="center" label="創建時間">
      </el-table-column>
      <el-table-column prop="memberUsername" header-align="center" align="center" label="用户名">
      </el-table-column>
      <el-table-column prop="totalAmount" header-align="center" align="center" label="訂單總額">
      </el-table-column>
      <el-table-column prop="payAmount" header-align="center" align="center" label="應付總額">
      </el-table-column>
      <el-table-column prop="freightAmount" header-align="center" align="center" label="運費金額">
      </el-table-column>
      <el-table-column prop="status" header-align="center" align="center" label="訂單狀態">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status=== 0" type="info">待付款</el-tag>
          <el-tag v-if="scope.row.status=== 1" type="warning">待發貨</el-tag>
          <el-tag v-if="scope.row.status=== 2" type="success">已發貨</el-tag>
          <el-tag v-if="scope.row.status=== 3" type="success">已完成</el-tag>
          <el-tag v-if="scope.row.status=== 4" type="danger">已關閉</el-tag>
          <el-tag v-if="scope.row.status=== 5" type="warning">正在售後</el-tag>
          <el-tag v-if="scope.row.status=== 6" type="success">售後完成</el-tag>
          　　　　　　　　
        </template>
      </el-table-column>
      <el-table-column prop="deliveryCompany" header-align="center" align="center" label="物流公司(配送方式)">
      </el-table-column>
      <el-table-column prop="deliverySn" header-align="center" align="center" label="物流單號">
      </el-table-column>

      <el-table-column prop="receiverName" header-align="center" align="center" label="收貨人姓名">
      </el-table-column>
      <el-table-column prop="receiverPhone" header-align="center" align="center" label="收貨人電話">
      </el-table-column>
      <el-table-column prop="receiverPostCode" header-align="center" align="center" label="收貨人郵編">
      </el-table-column>
      <el-table-column prop="receiverProvince" header-align="center" align="center" label="省份/直轄市">
      </el-table-column>
      <el-table-column prop="receiverCity" header-align="center" align="center" label="城市">
      </el-table-column>
      <el-table-column prop="receiverRegion" header-align="center" align="center" label="區">
      </el-table-column>
      <el-table-column prop="receiverDetailAddress" header-align="center" align="center" label="詳細地址">
      </el-table-column>
      <el-table-column prop="note" header-align="center" align="center" label="訂單備註">
      </el-table-column>
      <el-table-column prop="confirmStatus" header-align="center" align="center" label="確認收貨狀態[0->未確認；1->已確認]">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.confirmStatus=== 0" type="warning">未確認</el-tag>
          <el-tag v-if="scope.row.confirmStatus=== 1" type="success">已確認</el-tag>
          　　　　
        </template>
      </el-table-column>
      <el-table-column prop="deleteStatus" header-align="center" align="center" label="刪除狀態【0->未刪除；1->已刪除】">
      </el-table-column>
      <el-table-column prop="paymentTime" header-align="center" align="center" label="支付時間">
      </el-table-column>
      <el-table-column prop="deliveryTime" header-align="center" align="center" label="發貨時間">
      </el-table-column>
      <el-table-column prop="receiveTime" header-align="center" align="center" label="確認收貨時間">
      </el-table-column>
      <el-table-column prop="modifyTime" header-align="center" align="center" label="修改時間">
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
import AddOrUpdate from "./order-add-or-update";
export default {
  data() {
    return {
      dataForm: {
        memberUsername: "",
        orderSn: "",
        status: "",
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
    if (this.$route.query.username) {
      this.dataForm.memberUsername = this.$route.query.username;
    }
    this.getDataList();
  },
  methods: {
    // 獲取數據列表
    getDataList() {
      this.dataListLoading = true;
      this.$http({
        url: this.$http.adornUrl("/order/order/list"),
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
          url: this.$http.adornUrl("/order/order/delete"),
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
