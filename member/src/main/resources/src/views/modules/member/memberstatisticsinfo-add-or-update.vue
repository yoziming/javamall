<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="會員id" prop="memberId">
      <el-input v-model="dataForm.memberId" placeholder="會員id"></el-input>
    </el-form-item>
    <el-form-item label="累計消費金額" prop="consumeAmount">
      <el-input v-model="dataForm.consumeAmount" placeholder="累計消費金額"></el-input>
    </el-form-item>
    <el-form-item label="累計優惠金額" prop="couponAmount">
      <el-input v-model="dataForm.couponAmount" placeholder="累計優惠金額"></el-input>
    </el-form-item>
    <el-form-item label="訂單數量" prop="orderCount">
      <el-input v-model="dataForm.orderCount" placeholder="訂單數量"></el-input>
    </el-form-item>
    <el-form-item label="優惠券數量" prop="couponCount">
      <el-input v-model="dataForm.couponCount" placeholder="優惠券數量"></el-input>
    </el-form-item>
    <el-form-item label="評價數" prop="commentCount">
      <el-input v-model="dataForm.commentCount" placeholder="評價數"></el-input>
    </el-form-item>
    <el-form-item label="退貨數量" prop="returnOrderCount">
      <el-input v-model="dataForm.returnOrderCount" placeholder="退貨數量"></el-input>
    </el-form-item>
    <el-form-item label="登錄次數" prop="loginCount">
      <el-input v-model="dataForm.loginCount" placeholder="登錄次數"></el-input>
    </el-form-item>
    <el-form-item label="關注數量" prop="attendCount">
      <el-input v-model="dataForm.attendCount" placeholder="關注數量"></el-input>
    </el-form-item>
    <el-form-item label="粉絲數量" prop="fansCount">
      <el-input v-model="dataForm.fansCount" placeholder="粉絲數量"></el-input>
    </el-form-item>
    <el-form-item label="收藏的商品數量" prop="collectProductCount">
      <el-input v-model="dataForm.collectProductCount" placeholder="收藏的商品數量"></el-input>
    </el-form-item>
    <el-form-item label="收藏的專題活動數量" prop="collectSubjectCount">
      <el-input v-model="dataForm.collectSubjectCount" placeholder="收藏的專題活動數量"></el-input>
    </el-form-item>
    <el-form-item label="收藏的評論數量" prop="collectCommentCount">
      <el-input v-model="dataForm.collectCommentCount" placeholder="收藏的評論數量"></el-input>
    </el-form-item>
    <el-form-item label="邀請的朋友數量" prop="inviteFriendCount">
      <el-input v-model="dataForm.inviteFriendCount" placeholder="邀請的朋友數量"></el-input>
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
          memberId: '',
          consumeAmount: '',
          couponAmount: '',
          orderCount: '',
          couponCount: '',
          commentCount: '',
          returnOrderCount: '',
          loginCount: '',
          attendCount: '',
          fansCount: '',
          collectProductCount: '',
          collectSubjectCount: '',
          collectCommentCount: '',
          inviteFriendCount: ''
        },
        dataRule: {
          memberId: [
            { required: true, message: '會員id不能为空', trigger: 'blur' }
          ],
          consumeAmount: [
            { required: true, message: '累計消費金額不能为空', trigger: 'blur' }
          ],
          couponAmount: [
            { required: true, message: '累計優惠金額不能为空', trigger: 'blur' }
          ],
          orderCount: [
            { required: true, message: '訂單數量不能为空', trigger: 'blur' }
          ],
          couponCount: [
            { required: true, message: '優惠券數量不能为空', trigger: 'blur' }
          ],
          commentCount: [
            { required: true, message: '評價數不能为空', trigger: 'blur' }
          ],
          returnOrderCount: [
            { required: true, message: '退貨數量不能为空', trigger: 'blur' }
          ],
          loginCount: [
            { required: true, message: '登錄次數不能为空', trigger: 'blur' }
          ],
          attendCount: [
            { required: true, message: '關注數量不能为空', trigger: 'blur' }
          ],
          fansCount: [
            { required: true, message: '粉絲數量不能为空', trigger: 'blur' }
          ],
          collectProductCount: [
            { required: true, message: '收藏的商品數量不能为空', trigger: 'blur' }
          ],
          collectSubjectCount: [
            { required: true, message: '收藏的專題活動數量不能为空', trigger: 'blur' }
          ],
          collectCommentCount: [
            { required: true, message: '收藏的評論數量不能为空', trigger: 'blur' }
          ],
          inviteFriendCount: [
            { required: true, message: '邀請的朋友數量不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/member/memberstatisticsinfo/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.memberId = data.memberStatisticsInfo.memberId
                this.dataForm.consumeAmount = data.memberStatisticsInfo.consumeAmount
                this.dataForm.couponAmount = data.memberStatisticsInfo.couponAmount
                this.dataForm.orderCount = data.memberStatisticsInfo.orderCount
                this.dataForm.couponCount = data.memberStatisticsInfo.couponCount
                this.dataForm.commentCount = data.memberStatisticsInfo.commentCount
                this.dataForm.returnOrderCount = data.memberStatisticsInfo.returnOrderCount
                this.dataForm.loginCount = data.memberStatisticsInfo.loginCount
                this.dataForm.attendCount = data.memberStatisticsInfo.attendCount
                this.dataForm.fansCount = data.memberStatisticsInfo.fansCount
                this.dataForm.collectProductCount = data.memberStatisticsInfo.collectProductCount
                this.dataForm.collectSubjectCount = data.memberStatisticsInfo.collectSubjectCount
                this.dataForm.collectCommentCount = data.memberStatisticsInfo.collectCommentCount
                this.dataForm.inviteFriendCount = data.memberStatisticsInfo.inviteFriendCount
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
              url: this.$http.adornUrl(`/member/memberstatisticsinfo/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'memberId': this.dataForm.memberId,
                'consumeAmount': this.dataForm.consumeAmount,
                'couponAmount': this.dataForm.couponAmount,
                'orderCount': this.dataForm.orderCount,
                'couponCount': this.dataForm.couponCount,
                'commentCount': this.dataForm.commentCount,
                'returnOrderCount': this.dataForm.returnOrderCount,
                'loginCount': this.dataForm.loginCount,
                'attendCount': this.dataForm.attendCount,
                'fansCount': this.dataForm.fansCount,
                'collectProductCount': this.dataForm.collectProductCount,
                'collectSubjectCount': this.dataForm.collectSubjectCount,
                'collectCommentCount': this.dataForm.collectCommentCount,
                'inviteFriendCount': this.dataForm.inviteFriendCount
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
