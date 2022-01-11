<template>
  <el-dialog
    :title="!dataForm.catId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="分類名稱" prop="name">
      <el-input v-model="dataForm.name" placeholder="分類名稱"></el-input>
    </el-form-item>
    <el-form-item label="父分類id" prop="parentCid">
      <el-input v-model="dataForm.parentCid" placeholder="父分類id"></el-input>
    </el-form-item>
    <el-form-item label="層級" prop="catLevel">
      <el-input v-model="dataForm.catLevel" placeholder="層級"></el-input>
    </el-form-item>
    <el-form-item label="是否顯示[0-不顯示，1顯示]" prop="showStatus">
      <el-input v-model="dataForm.showStatus" placeholder="是否顯示[0-不顯示，1顯示]"></el-input>
    </el-form-item>
    <el-form-item label="排序" prop="sort">
      <el-input v-model="dataForm.sort" placeholder="排序"></el-input>
    </el-form-item>
    <el-form-item label="圖標地址" prop="icon">
      <el-input v-model="dataForm.icon" placeholder="圖標地址"></el-input>
    </el-form-item>
    <el-form-item label="計量單位" prop="productUnit">
      <el-input v-model="dataForm.productUnit" placeholder="計量單位"></el-input>
    </el-form-item>
    <el-form-item label="商品數量" prop="productCount">
      <el-input v-model="dataForm.productCount" placeholder="商品數量"></el-input>
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
          catId: 0,
          name: '',
          parentCid: '',
          catLevel: '',
          showStatus: '',
          sort: '',
          icon: '',
          productUnit: '',
          productCount: ''
        },
        dataRule: {
          name: [
            { required: true, message: '分類名稱不能为空', trigger: 'blur' }
          ],
          parentCid: [
            { required: true, message: '父分類id不能为空', trigger: 'blur' }
          ],
          catLevel: [
            { required: true, message: '層級不能为空', trigger: 'blur' }
          ],
          showStatus: [
            { required: true, message: '是否顯示[0-不顯示，1顯示]不能为空', trigger: 'blur' }
          ],
          sort: [
            { required: true, message: '排序不能为空', trigger: 'blur' }
          ],
          icon: [
            { required: true, message: '圖標地址不能为空', trigger: 'blur' }
          ],
          productUnit: [
            { required: true, message: '計量單位不能为空', trigger: 'blur' }
          ],
          productCount: [
            { required: true, message: '商品數量不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.catId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.catId) {
            this.$http({
              url: this.$http.adornUrl(`/product/category/info/${this.dataForm.catId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.name = data.category.name
                this.dataForm.parentCid = data.category.parentCid
                this.dataForm.catLevel = data.category.catLevel
                this.dataForm.showStatus = data.category.showStatus
                this.dataForm.sort = data.category.sort
                this.dataForm.icon = data.category.icon
                this.dataForm.productUnit = data.category.productUnit
                this.dataForm.productCount = data.category.productCount
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
              url: this.$http.adornUrl(`/product/category/${!this.dataForm.catId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'catId': this.dataForm.catId || undefined,
                'name': this.dataForm.name,
                'parentCid': this.dataForm.parentCid,
                'catLevel': this.dataForm.catLevel,
                'showStatus': this.dataForm.showStatus,
                'sort': this.dataForm.sort,
                'icon': this.dataForm.icon,
                'productUnit': this.dataForm.productUnit,
                'productCount': this.dataForm.productCount
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
