<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="" prop="spuId">
      <el-input v-model="dataForm.spuId" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="成長積分" prop="growBounds">
      <el-input v-model="dataForm.growBounds" placeholder="成長積分"></el-input>
    </el-form-item>
    <el-form-item label="購物積分" prop="buyBounds">
      <el-input v-model="dataForm.buyBounds" placeholder="購物積分"></el-input>
    </el-form-item>
    <el-form-item label="優惠生效情況[1111（四個狀態位，從右到左）;0 - 無優惠，成長積分是否贈送;1 - 無優惠，購物積分是否贈送;2 - 有優惠，成長積分是否贈送;3 - 有優惠，購物積分是否贈送【狀態位0：不贈送，1：贈送】]" prop="work">
      <el-input v-model="dataForm.work" placeholder="優惠生效情況[1111（四個狀態位，從右到左）;0 - 無優惠，成長積分是否贈送;1 - 無優惠，購物積分是否贈送;2 - 有優惠，成長積分是否贈送;3 - 有優惠，購物積分是否贈送【狀態位0：不贈送，1：贈送】]"></el-input>
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
          id: 0,
          spuId: '',
          growBounds: '',
          buyBounds: '',
          work: ''
        },
        dataRule: {
          spuId: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          growBounds: [
            { required: true, message: '成長積分不能为空', trigger: 'blur' }
          ],
          buyBounds: [
            { required: true, message: '購物積分不能为空', trigger: 'blur' }
          ],
          work: [
            { required: true, message: '優惠生效情況[1111（四個狀態位，從右到左）;0 - 無優惠，成長積分是否贈送;1 - 無優惠，購物積分是否贈送;2 - 有優惠，成長積分是否贈送;3 - 有優惠，購物積分是否贈送【狀態位0：不贈送，1：贈送】]不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/coupon/spubounds/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.spuId = data.spuBounds.spuId
                this.dataForm.growBounds = data.spuBounds.growBounds
                this.dataForm.buyBounds = data.spuBounds.buyBounds
                this.dataForm.work = data.spuBounds.work
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
              url: this.$http.adornUrl(`/coupon/spubounds/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'spuId': this.dataForm.spuId,
                'growBounds': this.dataForm.growBounds,
                'buyBounds': this.dataForm.buyBounds,
                'work': this.dataForm.work
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
