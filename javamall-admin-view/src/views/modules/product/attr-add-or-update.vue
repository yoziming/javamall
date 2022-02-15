<template>
  <el-dialog :title="!dataForm.attrId ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible" @closed="dialogClose">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" label-width="120px">
      <!--       @keyup.enter.native="dataFormSubmit()" -->
      <el-form-item label="屬性名" prop="attrName">
        <el-input v-model="dataForm.attrName" placeholder="屬性名"></el-input>
      </el-form-item>
      <el-form-item label="屬性類型" prop="attrType">
        <el-select v-model="dataForm.attrType" placeholder="請選擇">
          <el-option label="規格參數" :value="1"></el-option>
          <el-option label="銷售屬性" :value="0"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="值類型" prop="valueType">
        <el-switch v-model="dataForm.valueType" active-text="允許多個值" inactive-text="只能單個值" active-color="#13ce66" inactive-color="#ff4949" :inactive-value="0" :active-value="1"></el-switch>
      </el-form-item>
      <el-form-item label="可選值" prop="valueSelect">
        <!-- <el-input v-model="dataForm.valueSelect"></el-input> -->
        <el-select v-model="dataForm.valueSelect" multiple filterable allow-create placeholder="請輸入內容"></el-select>
      </el-form-item>
      <el-form-item label="屬性圖標" prop="icon">
        <el-input v-model="dataForm.icon" placeholder="屬性圖標"></el-input>
      </el-form-item>
      <el-form-item label="所屬分類" prop="catalogId">
        <category-cascader :catalogPath.sync="catalogPath"></category-cascader>
      </el-form-item>
      <el-form-item label="所屬分組" prop="attrGroupId" v-if="type == 1">
        <el-select ref="groupSelect" v-model="dataForm.attrGroupId" placeholder="請選擇">
          <el-option v-for="item in attrGroups" :key="item.attrGroupId" :label="item.attrGroupName" :value="item.attrGroupId"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="可檢索" prop="searchType" v-if="type == 1">
        <el-switch v-model="dataForm.searchType" active-color="#13ce66" inactive-color="#ff4949" :active-value="1" :inactive-value="0"></el-switch>
      </el-form-item>
      <el-form-item label="快速展示" prop="showDesc" v-if="type == 1">
        <el-switch v-model="dataForm.showDesc" active-color="#13ce66" inactive-color="#ff4949" :active-value="1" :inactive-value="0"></el-switch>
      </el-form-item>
      <el-form-item label="啓用狀態" prop="enable">
        <el-switch v-model="dataForm.enable" active-color="#13ce66" inactive-color="#ff4949" :active-value="1" :inactive-value="0"></el-switch>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">確定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import CategoryCascader from "../common/category-cascader";
export default {
  data() {
    return {
      visible: false,
      dataForm: {
        attrId: 0,
        attrName: "",
        searchType: 0,
        valueType: 0,
        icon: "",
        valueSelect: "",
        attrType: 1,
        enable: 1,
        catalogId: "",
        attrGroupId: "",
        showDesc: 0,
      },
      catalogPath: [],
      attrGroups: [],
      dataRule: {
        attrName: [
          { required: true, message: "屬性名不能為空", trigger: "blur" },
        ],
        searchType: [
          {
            required: true,
            message: "是否需要檢索不能為空",
            trigger: "blur",
          },
        ],
        valueType: [
          {
            required: true,
            message: "值類型不能為空",
            trigger: "blur",
          },
        ],
        icon: [
          { required: true, message: "屬性圖標不能為空", trigger: "blur" },
        ],
        attrType: [
          {
            required: true,
            message: "屬性類型不能為空",
            trigger: "blur",
          },
        ],
        enable: [
          {
            required: true,
            message: "啓用狀態不能為空",
            trigger: "blur",
          },
        ],
        catalogId: [
          {
            required: true,
            message: "需要選擇正確的三級分類數據",
            trigger: "blur",
          },
        ],
        showDesc: [
          {
            required: true,
            message: "快速展示不能為空",
            trigger: "blur",
          },
        ],
      },
    };
  },
  props: {
    type: {
      type: Number,
      default: 1,
    },
  },
  watch: {
    catalogPath(path) {
      //監聽到路徑變化需要查出這個三級分類的分組信息
      console.log("路徑變了", path);
      this.attrGroups = [];
      this.dataForm.attrGroupId = "";
      this.dataForm.catalogId = path[path.length - 1];
      if (path && path.length == 3) {
        this.$http({
          url: this.$http.adornUrl(
            `/product/attrgroup/list/${path[path.length - 1]}`
          ),
          method: "get",
          params: this.$http.adornParams({ page: 1, limit: 10000000 }),
        }).then(({ data }) => {
          if (data && data.code === 0) {
            this.attrGroups = data.page.list;
          } else {
            this.$message.error(data.msg);
          }
        });
      } else if (path.length == 0) {
        this.dataForm.catalogId = "";
      } else {
        this.$message.error("請選擇正確的分類");
        this.dataForm.catalogId = "";
      }
    },
  },
  components: { CategoryCascader },
  methods: {
    init(id) {
      this.dataForm.attrId = id || 0;
      this.dataForm.attrType = this.type;
      this.visible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].resetFields();
        if (this.dataForm.attrId) {
          this.$http({
            url: this.$http.adornUrl(
              `/product/attr/info/${this.dataForm.attrId}`
            ),
            method: "get",
            params: this.$http.adornParams(),
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.dataForm.attrName = data.attr.attrName;
              this.dataForm.searchType = data.attr.searchType;
              this.dataForm.valueType = data.attr.valueType;
              this.dataForm.icon = data.attr.icon;
              this.dataForm.valueSelect = data.attr.valueSelect.split(";");
              this.dataForm.attrType = data.attr.attrType;
              this.dataForm.enable = data.attr.enable;
              this.dataForm.catalogId = data.attr.catalogId;
              this.dataForm.showDesc = data.attr.showDesc;
              //attrGroupId
              //catalogPath
              this.catalogPath = data.attr.catalogPath;
              this.$nextTick(() => {
                this.dataForm.attrGroupId = data.attr.attrGroupId;
              });
            }
          });
        }
      });
    },
    // 表單提交
    dataFormSubmit() {
      this.$refs["dataForm"].validate((valid) => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl(
              `/product/attr/${!this.dataForm.attrId ? "save" : "update"}`
            ),
            method: "post",
            data: this.$http.adornData({
              attrId: this.dataForm.attrId || undefined,
              attrName: this.dataForm.attrName,
              searchType: this.dataForm.searchType,
              valueType: this.dataForm.valueType,
              icon: this.dataForm.icon,
              valueSelect: this.dataForm.valueSelect.join(";"),
              attrType: this.dataForm.attrType,
              enable: this.dataForm.enable,
              catalogId: this.dataForm.catalogId,
              attrGroupId: this.dataForm.attrGroupId,
              showDesc: this.dataForm.showDesc,
            }),
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.$message({
                message: "操作成功",
                type: "success",
                duration: 1500,
                onClose: () => {
                  this.visible = false;
                  this.$emit("refreshDataList");
                },
              });
            } else {
              this.$message.error(data.msg);
            }
          });
        }
      });
    },
    //dialogClose
    dialogClose() {
      this.catalogPath = [];
    },
  },
};
</script>
