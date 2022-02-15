<template>
  <div class="mod-config">
    <el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
      <el-table-column prop="id" header-align="center" align="center" label="id"></el-table-column>
      <el-table-column prop="spuName" header-align="center" align="center" label="名稱"></el-table-column>
      <el-table-column prop="spuDescription" header-align="center" align="center" label="描述"></el-table-column>
      <el-table-column prop="catalogId" header-align="center" align="center" label="分類"></el-table-column>
      <el-table-column prop="brandId" header-align="center" align="center" label="品牌"></el-table-column>
      <el-table-column prop="weight" header-align="center" align="center" label="重量"></el-table-column>
      <el-table-column prop="publishStatus" header-align="center" align="center" label="上架狀態">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.publishStatus == 0" type="info">新建</el-tag>
          <el-tag v-if="scope.row.publishStatus == 1" type="success">已上架</el-tag>
          <el-tag v-if="scope.row.publishStatus == 2" type="warning">已下架</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" header-align="center" align="center" label="創建時間"></el-table-column>
      <el-table-column prop="updateTime" header-align="center" align="center" label="修改時間"></el-table-column>
      <el-table-column fixed="right" header-align="center" align="left" width="200" label="操作">
        <template slot-scope="scope">
          <el-button v-if="scope.row.publishStatus == 0 || scope.row.publishStatus == 2" type="success" size="small" @click="productUp(scope.row.id)">上架</el-button>
          <el-button v-if="scope.row.publishStatus == 1" type="warning" size="small" @click="productDown(scope.row.id)">下架</el-button>
          <el-button v-if="scope.row.publishStatus == 0 || scope.row.publishStatus == 2" type="text" size="small" @click="deleteSpu(scope.row)">刪除</el-button>
          <el-button v-if="scope.row.publishStatus == 0 || scope.row.publishStatus == 2" type="text" size="small" @click="attrUpdateShow(scope.row)">規格</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex" :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage" layout="total, sizes, prev, pager, next, jumper"></el-pagination>
  </div>
</template>

<script>
export default {
  data() {
    return {
      dataSub: null,
      dataForm: {},
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      addOrUpdateVisible: false,
    };
  },
  props: {
    catId: {
      type: Number,
      default: 0,
    },
  },
  components: {},
  activated() {
    this.getDataList();
  },
  methods: {
    productUp(id) {
      this.$http({
        url: this.$http.adornUrl("/product/spuinfo/" + id + "/up"),
        method: "post",
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
    },

    productDown(id) {
      this.$confirm(`確定下架?`, "提示", {
        confirmButtonText: "確定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl("/product/spuinfo/" + id + "/down"),
          method: "post",
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

    attrUpdateShow(row) {
      console.log(row);
      this.$router.push({
        path: "/product-attrupdate",
        query: { spuId: row.id, catalogId: row.catalogId },
      });
    },
    updateSpuPage(row) {
      console.log(row);
      this.$router.push({
        path: "/product-spuupdate",
        query: { spuId: row.id },
      });
    },
    deleteSpu(row) {
      let spuId = row.id;
      this.$confirm(`確定刪除?`, "提示", {
        confirmButtonText: "確定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl("/product/spuinfo/delete"),
          method: "post",
          data: this.$http.adornData(spuId, false),
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
    // 獲取數據列表
    getDataList() {
      this.dataListLoading = true;
      let param = {};
      Object.assign(param, this.dataForm, {
        page: this.pageIndex,
        limit: this.pageSize,
      });
      this.$http({
        url: this.$http.adornUrl("/product/spuinfo/list"),
        method: "get",
        params: this.$http.adornParams(param),
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
    addOrUpdateHandle(id) {},
  },
  mounted() {
    this.dataSub = PubSub.subscribe("dataForm", (msg, val) => {
      console.log("~~~~~", val);
      this.dataForm = val;
      this.getDataList();
    });
  },
  beforeDestroy() {
    PubSub.unsubscribe(this.dataSub);
  },
};
</script>
