<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="秒殺訂單超時關閉時間(分)" prop="flashOrderOvertime">
      <el-input v-model="dataForm.flashOrderOvertime" placeholder="秒殺訂單超時關閉時間(分)"></el-input>
    </el-form-item>
    <el-form-item label="正常訂單超時時間(分)" prop="normalOrderOvertime">
      <el-input v-model="dataForm.normalOrderOvertime" placeholder="正常訂單超時時間(分)"></el-input>
    </el-form-item>
    <el-form-item label="發貨後自動確認收貨時間（天）" prop="confirmOvertime">
      <el-input v-model="dataForm.confirmOvertime" placeholder="發貨後自動確認收貨時間（天）"></el-input>
    </el-form-item>
    <el-form-item label="自動完成交易時間，不能申請退貨（天）" prop="finishOvertime">
      <el-input v-model="dataForm.finishOvertime" placeholder="自動完成交易時間，不能申請退貨（天）"></el-input>
    </el-form-item>
    <el-form-item label="訂單完成後自動好評時間（天）" prop="commentOvertime">
      <el-input v-model="dataForm.commentOvertime" placeholder="訂單完成後自動好評時間（天）"></el-input>
    </el-form-item>
    <el-form-item label="會員等級【0-不限會員等級，全部通用；其他-對應的其他會員等級】" prop="memberLevel">
      <el-input v-model="dataForm.memberLevel" placeholder="會員等級【0-不限會員等級，全部通用；其他-對應的其他會員等級】"></el-input>
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
          flashOrderOvertime: '',
          normalOrderOvertime: '',
          confirmOvertime: '',
          finishOvertime: '',
          commentOvertime: '',
          memberLevel: ''
        },
        dataRule: {
          flashOrderOvertime: [
            { required: true, message: '秒殺訂單超時關閉時間(分)不能为空', trigger: 'blur' }
          ],
          normalOrderOvertime: [
            { required: true, message: '正常訂單超時時間(分)不能为空', trigger: 'blur' }
          ],
          confirmOvertime: [
            { required: true, message: '發貨後自動確認收貨時間（天）不能为空', trigger: 'blur' }
          ],
          finishOvertime: [
            { required: true, message: '自動完成交易時間，不能申請退貨（天）不能为空', trigger: 'blur' }
          ],
          commentOvertime: [
            { required: true, message: '訂單完成後自動好評時間（天）不能为空', trigger: 'blur' }
          ],
          memberLevel: [
            { required: true, message: '會員等級【0-不限會員等級，全部通用；其他-對應的其他會員等級】不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/order/ordersetting/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.flashOrderOvertime = data.orderSetting.flashOrderOvertime
                this.dataForm.normalOrderOvertime = data.orderSetting.normalOrderOvertime
                this.dataForm.confirmOvertime = data.orderSetting.confirmOvertime
                this.dataForm.finishOvertime = data.orderSetting.finishOvertime
                this.dataForm.commentOvertime = data.orderSetting.commentOvertime
                this.dataForm.memberLevel = data.orderSetting.memberLevel
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
              url: this.$http.adornUrl(`/order/ordersetting/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'flashOrderOvertime': this.dataForm.flashOrderOvertime,
                'normalOrderOvertime': this.dataForm.normalOrderOvertime,
                'confirmOvertime': this.dataForm.confirmOvertime,
                'finishOvertime': this.dataForm.finishOvertime,
                'commentOvertime': this.dataForm.commentOvertime,
                'memberLevel': this.dataForm.memberLevel
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
