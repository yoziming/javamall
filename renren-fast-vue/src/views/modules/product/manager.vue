<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form :inline="true" :model="dataForm">
        <el-form-item label="分類">
          <category-cascader :catalogPath.sync="catalogPath"></category-cascader>
        </el-form-item>
        <el-form-item label="品牌">
          <brand-select style="width: 160px"></brand-select>
        </el-form-item>
        <el-form-item label="價格">
          <el-input-number style="width: 160px" v-model="dataForm.price.min" :min="0"></el-input-number>-
          <el-input-number style="width: 160px" v-model="dataForm.price.max" :min="0"></el-input-number>
        </el-form-item>
        <el-form-item label="檢索">
          <el-input style="width: 160px" v-model="dataForm.key" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="searchSkuInfo">查詢</el-button>
        </el-form-item>
      </el-form>
    </el-form>
    <el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%" @expand-change="getSkuDetails">
      <el-table-column type="expand">
        <template slot-scope="scope">
          商品標題：{{ scope.row.skuTitle }}
          <br />
          商品副標題：{{ scope.row.skuSubtitle }}
          <br />
          商品描述：{{ scope.row.skuDesc }}
          <br />
          分類ID：{{ scope.row.catalogId }}
          <br />
          SpuID：{{ scope.row.spuId }}
          <br />
          品牌ID：{{ scope.row.brandId }}
          <br />
        </template>
      </el-table-column>
      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
      <el-table-column prop="skuId" header-align="center" align="center" label="skuId"></el-table-column>
      <el-table-column prop="skuName" header-align="center" align="center" label="名稱"></el-table-column>
      <el-table-column prop="skuDefaultImg" header-align="center" align="center" label="默認圖片">
        <template slot-scope="scope">
          <img :src="scope.row.skuDefaultImg" style="width: 80px; height: 80px" />
        </template>
      </el-table-column>
      <el-table-column prop="price" header-align="center" align="center" label="價格"></el-table-column>
      <el-table-column prop="saleCount" header-align="center" align="center" label="銷量"></el-table-column>
      <el-table-column fixed="right" header-align="center" align="center" width="150" label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="previewHandle(scope.row.skuId)">預覽</el-button>
          <el-button type="text" size="small" @click="commentHandle(scope.row.skuId)">評論</el-button>
          <el-dropdown @command="handleCommand(scope.row, $event)" size="small" split-button type="text">
            更多
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="uploadImages">上傳圖片</el-dropdown-item>
              <el-dropdown-item command="seckillSettings">參與秒殺</el-dropdown-item>
              <el-dropdown-item command="reductionSettings">滿減設置</el-dropdown-item>
              <el-dropdown-item command="discountSettings">折扣設置</el-dropdown-item>
              <el-dropdown-item command="memberPriceSettings">會員價格</el-dropdown-item>
              <el-dropdown-item command="stockSettings">庫存管理</el-dropdown-item>
              <el-dropdown-item command="couponSettings">優惠劵</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex" :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage" layout="total, sizes, prev, pager, next, jumper"></el-pagination>
  </div>
</template>

<script>
import CategoryCascader from "../common/category-cascader";
import BrandSelect from "../common/brand-select";
export default {
  data() {
    return {
      catPathSub: null,
      brandIdSub: null,
      dataForm: {
        key: "",
        brandId: 0,
        catalogId: 0,
        price: {
          min: 0,
          max: 0,
        },
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      addOrUpdateVisible: false,
      catalogPath: [],
    };
  },
  components: {
    CategoryCascader,
    BrandSelect,
  },
  activated() {
    this.getDataList();
  },
  methods: {
    getSkuDetails(row, expand) {
      //sku詳情查詢
      console.log("展開某行...", row, expand);
    },
    //處理更多指令
    handleCommand(row, command) {
      console.log("~~~~~", row, command);
      if ("stockSettings" == command) {
        this.$router.push({ path: "/ware-sku", query: { skuId: row.skuId } });
      }
    },
    searchSkuInfo() {
      this.getDataList();
    },
    // 獲取數據列表
    getDataList() {
      this.dataListLoading = true;
      this.$http({
        url: this.$http.adornUrl("/product/skuinfo/list"),
        method: "get",
        params: this.$http.adornParams({
          page: this.pageIndex,
          limit: this.pageSize,
          key: this.dataForm.key,
          catalogId: this.dataForm.catalogId,
          brandId: this.dataForm.brandId,
          min: this.dataForm.price.min,
          max: this.dataForm.price.max,
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
  },
  mounted() {
    this.catPathSub = PubSub.subscribe("catPath", (msg, val) => {
      this.dataForm.catalogId = val[val.length - 1];
    });
    this.brandIdSub = PubSub.subscribe("brandId", (msg, val) => {
      this.dataForm.brandId = val;
    });
  },
  beforeDestroy() {
    PubSub.unsubscribe(this.catPathSub);
    PubSub.unsubscribe(this.brandIdSub);
  }, //生命週期 - 銷燬之前
};
</script>
