<template>
  <div>
    <base-attr :attrtype="0"></base-attr>
  </div>
</template>

<script>
//這裏可以導入其他文件（比如：組件，工具js，第三方插件js，json文件，圖片文件等等）
//例如：import 《組件名稱》 from '《組件路徑》';
import BaseAttr from "./baseattr";
export default {
  //import引入的組件需要注入到對象中才能使用
  components: { BaseAttr },
  props: {},
  data() {
    //這裏存放數據
    return {};
  },
  //計算屬性 類似於data概念
  computed: {},
  //監控data中的數據變化
  watch: {},
  //方法集合
  methods: {},
  //生命週期 - 創建完成（可以訪問當前this實例）
  created() {},
  //生命週期 - 掛載完成（可以訪問DOM元素）
  mounted() {},
  beforeCreate() {}, //生命週期 - 創建之前
  beforeMount() {}, //生命週期 - 掛載之前
  beforeUpdate() {}, //生命週期 - 更新之前
  updated() {}, //生命週期 - 更新之後
  beforeDestroy() {}, //生命週期 - 銷燬之前
  destroyed() {}, //生命週期 - 銷燬完成
  activated() {}, //如果頁面有keep-alive緩存功能，這個函數會觸發
};
</script>
<style scoped>
</style>