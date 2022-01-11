<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="訂單號（對外業務號）" prop="orderSn">
      <el-input v-model="dataForm.orderSn" placeholder="訂單號（對外業務號）"></el-input>
    </el-form-item>
    <el-form-item label="訂單id" prop="orderId">
      <el-input v-model="dataForm.orderId" placeholder="訂單id"></el-input>
    </el-form-item>
    <el-form-item label="支付寶交易流水號" prop="alipayTradeNo">
      <el-input v-model="dataForm.alipayTradeNo" placeholder="支付寶交易流水號"></el-input>
    </el-form-item>
    <el-form-item label="支付總金額" prop="totalAmount">
      <el-input v-model="dataForm.totalAmount" placeholder="支付總金額"></el-input>
    </el-form-item>
    <el-form-item label="交易內容" prop="subject">
      <el-input v-model="dataForm.subject" placeholder="交易內容"></el-input>
    </el-form-item>
    <el-form-item label="支付狀態" prop="paymentStatus">
      <el-input v-model="dataForm.paymentStatus" placeholder="支付狀態"></el-input>
    </el-form-item>
    <el-form-item label="創建時間" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="創建時間"></el-input>
    </el-form-item>
    <el-form-item label="確認時間" prop="confirmTime">
      <el-input v-model="dataForm.confirmTime" placeholder="確認時間"></el-input>
    </el-form-item>
    <el-form-item label="回調內容" prop="callbackContent">
      <el-input v-model="dataForm.callbackContent" placeholder="回調內容"></el-input>
    </el-form-item>
    <el-form-item label="回調時間" prop="callbackTime">
      <el-input v-model="dataForm.callbackTime" placeholder="回調時間"></el-input>
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
          orderSn: '',
          orderId: '',
          alipayTradeNo: '',
          totalAmount: '',
          subject: '',
          paymentStatus: '',
          createTime: '',
          confirmTime: '',
          callbackContent: '',
          callbackTime: ''
        },
        dataRule: {
          orderSn: [
            { required: true, message: '訂單號（對外業務號）不能为空', trigger: 'blur' }
          ],
          orderId: [
            { required: true, message: '訂單id不能为空', trigger: 'blur' }
          ],
          alipayTradeNo: [
            { required: true, message: '支付寶交易流水號不能为空', trigger: 'blur' }
          ],
          totalAmount: [
            { required: true, message: '支付總金額不能为空', trigger: 'blur' }
          ],
          subject: [
            { required: true, message: '交易內容不能为空', trigger: 'blur' }
          ],
          paymentStatus: [
            { required: true, message: '支付狀態不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '創建時間不能为空', trigger: 'blur' }
          ],
          confirmTime: [
            { required: true, message: '確認時間不能为空', trigger: 'blur' }
          ],
          callbackContent: [
            { required: true, message: '回調內容不能为空', trigger: 'blur' }
          ],
          callbackTime: [
            { required: true, message: '回調時間不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/order/paymentinfo/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.orderSn = data.paymentInfo.orderSn
                this.dataForm.orderId = data.paymentInfo.orderId
                this.dataForm.alipayTradeNo = data.paymentInfo.alipayTradeNo
                this.dataForm.totalAmount = data.paymentInfo.totalAmount
                this.dataForm.subject = data.paymentInfo.subject
                this.dataForm.paymentStatus = data.paymentInfo.paymentStatus
                this.dataForm.createTime = data.paymentInfo.createTime
                this.dataForm.confirmTime = data.paymentInfo.confirmTime
                this.dataForm.callbackContent = data.paymentInfo.callbackContent
                this.dataForm.callbackTime = data.paymentInfo.callbackTime
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
              url: this.$http.adornUrl(`/order/paymentinfo/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'orderSn': this.dataForm.orderSn,
                'orderId': this.dataForm.orderId,
                'alipayTradeNo': this.dataForm.alipayTradeNo,
                'totalAmount': this.dataForm.totalAmount,
                'subject': this.dataForm.subject,
                'paymentStatus': this.dataForm.paymentStatus,
                'createTime': this.dataForm.createTime,
                'confirmTime': this.dataForm.confirmTime,
                'callbackContent': this.dataForm.callbackContent,
                'callbackTime': this.dataForm.callbackTime
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
