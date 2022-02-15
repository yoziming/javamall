<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="參數名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查詢</el-button>
        <el-button v-if="isAuth('coupon:coupon:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('coupon:coupon:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量刪除</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
      <el-table-column prop="id" header-align="center" align="center" label="id"></el-table-column>
      <el-table-column prop="couponType" header-align="center" align="center" label="優惠卷類型">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.couponType==0">全場贈券</el-tag>
          <el-tag type="info" v-if="scope.row.couponType==1">會員贈券</el-tag>
          <el-tag type="success" v-if="scope.row.couponType==2">購物贈券</el-tag>
          <el-tag type="warning" v-if="scope.row.couponType==3">註冊贈券</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="couponImg" header-align="center" align="center" label="優惠券圖片"></el-table-column>
      <el-table-column prop="couponName" header-align="center" align="center" label="優惠卷名字"></el-table-column>
      <el-table-column prop="num" header-align="center" align="center" label="數量"></el-table-column>
      <el-table-column prop="amount" header-align="center" align="center" label="金額"></el-table-column>
      <el-table-column prop="perLimit" header-align="center" align="center" label="每人限領張數"></el-table-column>
      <el-table-column prop="minPoint" header-align="center" align="center" label="使用門檻"></el-table-column>
      <el-table-column prop="startTime" header-align="center" align="center" label="開始時間"></el-table-column>
      <el-table-column prop="endTime" header-align="center" align="center" label="結束時間"></el-table-column>
      <el-table-column prop="useType" header-align="center" align="center" label="使用類型">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.useType==0">全場通用</el-tag>
          <el-tag type="info" v-if="scope.row.useType==1">指定分類</el-tag>
          <el-tag type="success" v-if="scope.row.useType==2">指定商品</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="note" header-align="center" align="center" label="備註"></el-table-column>
      <el-table-column prop="publishCount" header-align="center" align="center" label="發行數量"></el-table-column>
      <el-table-column prop="useCount" header-align="center" align="center" label="已使用數量"></el-table-column>
      <el-table-column prop="receiveCount" header-align="center" align="center" label="領取數量"></el-table-column>
      <el-table-column label="可以領取的日期">
        <el-table-column prop="enableStartTime" header-align="center" align="center" label="開始日期"></el-table-column>
        <el-table-column prop="enableEndTime" header-align="center" align="center" label="結束日期"></el-table-column>
      </el-table-column>
      <el-table-column prop="code" header-align="center" align="center" label="優惠碼"></el-table-column>
      <el-table-column prop="memberLevel" header-align="center" align="center" label="領取所需等級">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.memberLevel==0">不限等級</el-tag>
          <el-tag type="info" v-else>{{getLevel(scope.row.memberLevel)}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="publish" header-align="center" align="center" label="發佈狀態">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.publish==0">未發佈</el-tag>
          <el-tag type="success" v-else>已發佈</el-tag>
        </template>
      </el-table-column>
      <el-table-column fixed="right" header-align="center" align="center" width="150" label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.id)">刪除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex" :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage" layout="total, sizes, prev, pager, next, jumper"></el-pagination>
    <!-- 彈窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
import AddOrUpdate from "./coupon-add-or-update";
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
      memberLevels: {},
    };
  },
  components: {
    AddOrUpdate,
  },
  activated() {
    this.getDataList();
    this.getMemberLevels();
  },
  methods: {
    getLevel(level) {
      let name = this.memberLevels["level_" + level];
      if (name) {
        return name;
      } else {
        return "";
      }
    },
    getMemberLevels() {
      //獲取所有的會員等級
      this.$http({
        url: this.$http.adornUrl("/member/memberlevel/list"),
        method: "get",
        params: this.$http.adornParams({
          page: 1,
          limit: 500,
        }),
      }).then(({ data }) => {
        data.page.list.forEach((item) => {
          this.memberLevels["level_" + item.id] = item.name;
        });
      });
    },
    // 獲取數據列表
    getDataList() {
      this.dataListLoading = true;
      this.$http({
        url: this.$http.adornUrl("/coupon/coupon/list"),
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
          url: this.$http.adornUrl("/coupon/coupon/delete"),
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
