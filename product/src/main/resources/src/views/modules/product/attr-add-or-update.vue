<template>
  <el-dialog
    :title="!dataForm.attrId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="屬性名" prop="attrName">
      <el-input v-model="dataForm.attrName" placeholder="屬性名"></el-input>
    </el-form-item>
    <el-form-item label="是否需要檢索[0-不需要，1-需要]" prop="searchType">
      <el-input v-model="dataForm.searchType" placeholder="是否需要檢索[0-不需要，1-需要]"></el-input>
    </el-form-item>
    <el-form-item label="屬性圖標" prop="icon">
      <el-input v-model="dataForm.icon" placeholder="屬性圖標"></el-input>
    </el-form-item>
    <el-form-item label="可選值列表[用逗號分隔]" prop="valueSelect">
      <el-input v-model="dataForm.valueSelect" placeholder="可選值列表[用逗號分隔]"></el-input>
    </el-form-item>
    <el-form-item label="屬性類型[0-銷售屬性，1-基本屬性，2-既是銷售屬性又是基本屬性]" prop="attrType">
      <el-input v-model="dataForm.attrType" placeholder="屬性類型[0-銷售屬性，1-基本屬性，2-既是銷售屬性又是基本屬性]"></el-input>
    </el-form-item>
    <el-form-item label="啓用狀態[0 - 禁用，1 - 啓用]" prop="enable">
      <el-input v-model="dataForm.enable" placeholder="啓用狀態[0 - 禁用，1 - 啓用]"></el-input>
    </el-form-item>
    <el-form-item label="所屬分類" prop="catelogId">
      <el-input v-model="dataForm.catelogId" placeholder="所屬分類"></el-input>
    </el-form-item>
    <el-form-item label="快速展示【是否展示在介紹上；0-否 1-是】，在sku中仍然可以調整" prop="showDesc">
      <el-input v-model="dataForm.showDesc" placeholder="快速展示【是否展示在介紹上；0-否 1-是】，在sku中仍然可以調整"></el-input>
    </el-form-item>
    <el-form-item label="單選 多選[0 1]" prop="valueType">
      <el-input v-model="dataForm.valueType" placeholder="單選 多選[0 1]"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          attrId: 0,
          attrName: '',
          searchType: '',
          icon: '',
          valueSelect: '',
          attrType: '',
          enable: '',
          catelogId: '',
          showDesc: '',
          valueType: ''
        },
        dataRule: {
          attrName: [
            { required: true, message: '屬性名不能为空', trigger: 'blur' }
          ],
          searchType: [
            { required: true, message: '是否需要檢索[0-不需要，1-需要]不能为空', trigger: 'blur' }
          ],
          icon: [
            { required: true, message: '屬性圖標不能为空', trigger: 'blur' }
          ],
          valueSelect: [
            { required: true, message: '可選值列表[用逗號分隔]不能为空', trigger: 'blur' }
          ],
          attrType: [
            { required: true, message: '屬性類型[0-銷售屬性，1-基本屬性，2-既是銷售屬性又是基本屬性]不能为空', trigger: 'blur' }
          ],
          enable: [
            { required: true, message: '啓用狀態[0 - 禁用，1 - 啓用]不能为空', trigger: 'blur' }
          ],
          catelogId: [
            { required: true, message: '所屬分類不能为空', trigger: 'blur' }
          ],
          showDesc: [
            { required: true, message: '快速展示【是否展示在介紹上；0-否 1-是】，在sku中仍然可以調整不能为空', trigger: 'blur' }
          ],
          valueType: [
            { required: true, message: '單選 多選[0 1]不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.attrId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.attrId) {
            this.$http({
              url: this.$http.adornUrl(`/product/attr/info/${this.dataForm.attrId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.attrName = data.attr.attrName
                this.dataForm.searchType = data.attr.searchType
                this.dataForm.icon = data.attr.icon
                this.dataForm.valueSelect = data.attr.valueSelect
                this.dataForm.attrType = data.attr.attrType
                this.dataForm.enable = data.attr.enable
                this.dataForm.catelogId = data.attr.catelogId
                this.dataForm.showDesc = data.attr.showDesc
                this.dataForm.valueType = data.attr.valueType
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/product/attr/${!this.dataForm.attrId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'attrId': this.dataForm.attrId || undefined,
                'attrName': this.dataForm.attrName,
                'searchType': this.dataForm.searchType,
                'icon': this.dataForm.icon,
                'valueSelect': this.dataForm.valueSelect,
                'attrType': this.dataForm.attrType,
                'enable': this.dataForm.enable,
                'catelogId': this.dataForm.catelogId,
                'showDesc': this.dataForm.showDesc,
                'valueType': this.dataForm.valueType
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
