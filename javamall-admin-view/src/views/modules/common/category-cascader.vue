<template>
  <!--
使用説明：
1）、引入category-cascader.vue
2）、語法：<category-cascader :catalogPath.sync="catalogPath"></category-cascader>
    解釋：
      catalogPath：指定的值是cascader初始化需要顯示的值，應該和父組件的catalogPath綁定;
          由於有sync修飾符，所以cascader路徑變化以後自動會修改父的catalogPath，這是結合子組件this.$emit("update:catalogPath",v);做的
      -->
  <div>
    <el-cascader filterable clearable placeholder="試試搜索：手機" v-model="paths" :options="categorys" :props="setting"></el-cascader>
  </div>
</template>

<script>
//這裏可以導入其他文件（比如：組件，工具js，第三方插件js，json文件，圖片文件等等）
//例如：import 《組件名稱》 from '《組件路徑》';

export default {
  //import引入的組件需要注入到對象中才能使用
  components: {},
  //接受父組件傳來的值
  props: {
    catalogPath: {
      type: Array,
      default() {
        return [];
      },
    },
  },
  data() {
    //這裏存放數據
    return {
      setting: {
        value: "catId",
        label: "name",
        children: "children",
      },
      categorys: [],
      paths: this.catalogPath,
    };
  },
  watch: {
    catalogPath(v) {
      this.paths = this.catalogPath;
    },
    paths(v) {
      this.$emit("update:catalogPath", v);
      //還可以使用pubsub-js進行傳值
      this.PubSub.publish("catPath", v);
    },
  },
  //方法集合
  methods: {
    getCategorys() {
      this.$http({
        url: this.$http.adornUrl("/product/category/list/tree"),
        method: "get",
      }).then(({ data }) => {
        this.categorys = data.data;
      });
    },
  },
  //生命週期 - 創建完成（可以訪問當前this實例）
  created() {
    this.getCategorys();
  },
};
</script>
<style scoped>
</style>
