<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="優惠卷類型[0->全場贈券；1->會員贈券；2->購物贈券；3->註冊贈券]" prop="couponType">
      <el-input v-model="dataForm.couponType" placeholder="優惠卷類型[0->全場贈券；1->會員贈券；2->購物贈券；3->註冊贈券]"></el-input>
    </el-form-item>
    <el-form-item label="優惠券圖片" prop="couponImg">
      <el-input v-model="dataForm.couponImg" placeholder="優惠券圖片"></el-input>
    </el-form-item>
    <el-form-item label="優惠卷名字" prop="couponName">
      <el-input v-model="dataForm.couponName" placeholder="優惠卷名字"></el-input>
    </el-form-item>
    <el-form-item label="數量" prop="num">
      <el-input v-model="dataForm.num" placeholder="數量"></el-input>
    </el-form-item>
    <el-form-item label="金額" prop="amount">
      <el-input v-model="dataForm.amount" placeholder="金額"></el-input>
    </el-form-item>
    <el-form-item label="每人限領張數" prop="perLimit">
      <el-input v-model="dataForm.perLimit" placeholder="每人限領張數"></el-input>
    </el-form-item>
    <el-form-item label="使用門檻" prop="minPoint">
      <el-input v-model="dataForm.minPoint" placeholder="使用門檻"></el-input>
    </el-form-item>
    <el-form-item label="開始時間" prop="startTime">
      <el-input v-model="dataForm.startTime" placeholder="開始時間"></el-input>
    </el-form-item>
    <el-form-item label="結束時間" prop="endTime">
      <el-input v-model="dataForm.endTime" placeholder="結束時間"></el-input>
    </el-form-item>
    <el-form-item label="使用類型[0->全場通用；1->指定分類；2->指定商品]" prop="useType">
      <el-input v-model="dataForm.useType" placeholder="使用類型[0->全場通用；1->指定分類；2->指定商品]"></el-input>
    </el-form-item>
    <el-form-item label="備註" prop="note">
      <el-input v-model="dataForm.note" placeholder="備註"></el-input>
    </el-form-item>
    <el-form-item label="發行數量" prop="publishCount">
      <el-input v-model="dataForm.publishCount" placeholder="發行數量"></el-input>
    </el-form-item>
    <el-form-item label="已使用數量" prop="useCount">
      <el-input v-model="dataForm.useCount" placeholder="已使用數量"></el-input>
    </el-form-item>
    <el-form-item label="領取數量" prop="receiveCount">
      <el-input v-model="dataForm.receiveCount" placeholder="領取數量"></el-input>
    </el-form-item>
    <el-form-item label="可以領取的開始日期" prop="enableStartTime">
      <el-input v-model="dataForm.enableStartTime" placeholder="可以領取的開始日期"></el-input>
    </el-form-item>
    <el-form-item label="可以領取的結束日期" prop="enableEndTime">
      <el-input v-model="dataForm.enableEndTime" placeholder="可以領取的結束日期"></el-input>
    </el-form-item>
    <el-form-item label="優惠碼" prop="code">
      <el-input v-model="dataForm.code" placeholder="優惠碼"></el-input>
    </el-form-item>
    <el-form-item label="可以領取的會員等級[0->不限等級，其他-對應等級]" prop="memberLevel">
      <el-input v-model="dataForm.memberLevel" placeholder="可以領取的會員等級[0->不限等級，其他-對應等級]"></el-input>
    </el-form-item>
    <el-form-item label="發佈狀態[0-未發佈，1-已發佈]" prop="publish">
      <el-input v-model="dataForm.publish" placeholder="發佈狀態[0-未發佈，1-已發佈]"></el-input>
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
          couponType: '',
          couponImg: '',
          couponName: '',
          num: '',
          amount: '',
          perLimit: '',
          minPoint: '',
          startTime: '',
          endTime: '',
          useType: '',
          note: '',
          publishCount: '',
          useCount: '',
          receiveCount: '',
          enableStartTime: '',
          enableEndTime: '',
          code: '',
          memberLevel: '',
          publish: ''
        },
        dataRule: {
          couponType: [
            { required: true, message: '優惠卷類型[0->全場贈券；1->會員贈券；2->購物贈券；3->註冊贈券]不能为空', trigger: 'blur' }
          ],
          couponImg: [
            { required: true, message: '優惠券圖片不能为空', trigger: 'blur' }
          ],
          couponName: [
            { required: true, message: '優惠卷名字不能为空', trigger: 'blur' }
          ],
          num: [
            { required: true, message: '數量不能为空', trigger: 'blur' }
          ],
          amount: [
            { required: true, message: '金額不能为空', trigger: 'blur' }
          ],
          perLimit: [
            { required: true, message: '每人限領張數不能为空', trigger: 'blur' }
          ],
          minPoint: [
            { required: true, message: '使用門檻不能为空', trigger: 'blur' }
          ],
          startTime: [
            { required: true, message: '開始時間不能为空', trigger: 'blur' }
          ],
          endTime: [
            { required: true, message: '結束時間不能为空', trigger: 'blur' }
          ],
          useType: [
            { required: true, message: '使用類型[0->全場通用；1->指定分類；2->指定商品]不能为空', trigger: 'blur' }
          ],
          note: [
            { required: true, message: '備註不能为空', trigger: 'blur' }
          ],
          publishCount: [
            { required: true, message: '發行數量不能为空', trigger: 'blur' }
          ],
          useCount: [
            { required: true, message: '已使用數量不能为空', trigger: 'blur' }
          ],
          receiveCount: [
            { required: true, message: '領取數量不能为空', trigger: 'blur' }
          ],
          enableStartTime: [
            { required: true, message: '可以領取的開始日期不能为空', trigger: 'blur' }
          ],
          enableEndTime: [
            { required: true, message: '可以領取的結束日期不能为空', trigger: 'blur' }
          ],
          code: [
            { required: true, message: '優惠碼不能为空', trigger: 'blur' }
          ],
          memberLevel: [
            { required: true, message: '可以領取的會員等級[0->不限等級，其他-對應等級]不能为空', trigger: 'blur' }
          ],
          publish: [
            { required: true, message: '發佈狀態[0-未發佈，1-已發佈]不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/coupon/coupon/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.couponType = data.coupon.couponType
                this.dataForm.couponImg = data.coupon.couponImg
                this.dataForm.couponName = data.coupon.couponName
                this.dataForm.num = data.coupon.num
                this.dataForm.amount = data.coupon.amount
                this.dataForm.perLimit = data.coupon.perLimit
                this.dataForm.minPoint = data.coupon.minPoint
                this.dataForm.startTime = data.coupon.startTime
                this.dataForm.endTime = data.coupon.endTime
                this.dataForm.useType = data.coupon.useType
                this.dataForm.note = data.coupon.note
                this.dataForm.publishCount = data.coupon.publishCount
                this.dataForm.useCount = data.coupon.useCount
                this.dataForm.receiveCount = data.coupon.receiveCount
                this.dataForm.enableStartTime = data.coupon.enableStartTime
                this.dataForm.enableEndTime = data.coupon.enableEndTime
                this.dataForm.code = data.coupon.code
                this.dataForm.memberLevel = data.coupon.memberLevel
                this.dataForm.publish = data.coupon.publish
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
              url: this.$http.adornUrl(`/coupon/coupon/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'couponType': this.dataForm.couponType,
                'couponImg': this.dataForm.couponImg,
                'couponName': this.dataForm.couponName,
                'num': this.dataForm.num,
                'amount': this.dataForm.amount,
                'perLimit': this.dataForm.perLimit,
                'minPoint': this.dataForm.minPoint,
                'startTime': this.dataForm.startTime,
                'endTime': this.dataForm.endTime,
                'useType': this.dataForm.useType,
                'note': this.dataForm.note,
                'publishCount': this.dataForm.publishCount,
                'useCount': this.dataForm.useCount,
                'receiveCount': this.dataForm.receiveCount,
                'enableStartTime': this.dataForm.enableStartTime,
                'enableEndTime': this.dataForm.enableEndTime,
                'code': this.dataForm.code,
                'memberLevel': this.dataForm.memberLevel,
                'publish': this.dataForm.publish
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
