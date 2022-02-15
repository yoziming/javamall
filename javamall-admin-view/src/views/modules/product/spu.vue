<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-form :inline="true" :model="dataForm">
          <el-form-item label="分類">
            <category-cascader :catalogPath.sync="catalogPath"></category-cascader>
          </el-form-item>
          <el-form-item label="品牌">
            <brand-select style="width:160px"></brand-select>
          </el-form-item>
          <el-form-item label="狀態">
            <el-select style="width:160px" v-model="dataForm.status" clearable>
              <el-option label="新建" :value="0"></el-option>
              <el-option label="上架" :value="1"></el-option>
              <el-option label="下架" :value="2"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="檢索">
            <el-input style="width:160px" placeholder="spu名稱/id" v-model="dataForm.key" clearable></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="searchSpuInfo">查詢</el-button>
          </el-form-item>
        </el-form>
      </el-col>
      <el-col :span="24">
        <spuinfo :catId="catId"></spuinfo>
      </el-col>
    </el-row>
  </div>
</template>

<script>
//這裏可以導入其他文件（比如：組件，工具js，第三方插件js，json文件，圖片文件等等）
//例如：import 《組件名稱》 from '《組件路徑》';
import CategoryCascader from "../common/category-cascader";
import BrandSelect from "../common/brand-select";
import Spuinfo from "./spuinfo";
export default {
  //import引入的組件需要注入到對象中才能使用
  components: { CategoryCascader, Spuinfo, BrandSelect },
  props: {},
  data() {
    //這裏存放數據
    return {
      catId: 0,
      catalogPath: [],
      dataForm: {
        status: "",
        key: "",
        brandId: 0,
        catalogId: 0,
      },
      catPathSub: null,
      brandIdSub: null,
    };
  },
  //計算屬性 類似於data概念
  computed: {},
  //監控data中的數據變化
  watch: {},
  //方法集合
  methods: {
    searchSpuInfo() {
      console.log("搜索條件", this.dataForm);
      this.PubSub.publish("dataForm", this.dataForm);
    },
  },
  //生命週期 - 創建完成（可以訪問當前this實例）
  created() {},
  //生命週期 - 掛載完成（可以訪問DOM元素）
  mounted() {
    this.catPathSub = PubSub.subscribe("catPath", (msg, val) => {
      this.dataForm.catalogId = val[val.length - 1];
    });
    this.brandIdSub = PubSub.subscribe("brandId", (msg, val) => {
      this.dataForm.brandId = val;
    });
  },
  beforeCreate() {}, //生命週期 - 創建之前
  beforeMount() {}, //生命週期 - 掛載之前
  beforeUpdate() {}, //生命週期 - 更新之前
  updated() {}, //生命週期 - 更新之後
  beforeDestroy() {
    PubSub.unsubscribe(this.catPathSub);
    PubSub.unsubscribe(this.brandIdSub);
  }, //生命週期 - 銷燬之前
  destroyed() {}, //生命週期 - 銷燬完成
  activated() {}, //如果頁面有keep-alive緩存功能，這個函數會觸發
};
</script>
<style scoped>
</style>
