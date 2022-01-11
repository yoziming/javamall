<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="参数名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button v-if="isAuth('order:order:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('order:order:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>
      <el-table-column
        prop="id"
        header-align="center"
        align="center"
        label="id">
      </el-table-column>
      <el-table-column
        prop="memberId"
        header-align="center"
        align="center"
        label="member_id">
      </el-table-column>
      <el-table-column
        prop="orderSn"
        header-align="center"
        align="center"
        label="訂單號">
      </el-table-column>
      <el-table-column
        prop="couponId"
        header-align="center"
        align="center"
        label="使用的優惠券">
      </el-table-column>
      <el-table-column
        prop="createTime"
        header-align="center"
        align="center"
        label="create_time">
      </el-table-column>
      <el-table-column
        prop="memberUsername"
        header-align="center"
        align="center"
        label="用户名">
      </el-table-column>
      <el-table-column
        prop="totalAmount"
        header-align="center"
        align="center"
        label="訂單總額">
      </el-table-column>
      <el-table-column
        prop="payAmount"
        header-align="center"
        align="center"
        label="應付總額">
      </el-table-column>
      <el-table-column
        prop="freightAmount"
        header-align="center"
        align="center"
        label="運費金額">
      </el-table-column>
      <el-table-column
        prop="promotionAmount"
        header-align="center"
        align="center"
        label="促銷優化金額（促銷價、滿減、階梯價）">
      </el-table-column>
      <el-table-column
        prop="integrationAmount"
        header-align="center"
        align="center"
        label="積分抵扣金額">
      </el-table-column>
      <el-table-column
        prop="couponAmount"
        header-align="center"
        align="center"
        label="優惠券抵扣金額">
      </el-table-column>
      <el-table-column
        prop="discountAmount"
        header-align="center"
        align="center"
        label="後台調整訂單使用的折扣金額">
      </el-table-column>
      <el-table-column
        prop="payType"
        header-align="center"
        align="center"
        label="支付方式【1->支付寶；2->微信；3->銀聯； 4->貨到付款；】">
      </el-table-column>
      <el-table-column
        prop="sourceType"
        header-align="center"
        align="center"
        label="訂單來源[0->PC訂單；1->app訂單]">
      </el-table-column>
      <el-table-column
        prop="status"
        header-align="center"
        align="center"
        label="訂單狀態【0->待付款；1->待發貨；2->已發貨；3->已完成；4->已關閉；5->無效訂單】">
      </el-table-column>
      <el-table-column
        prop="deliveryCompany"
        header-align="center"
        align="center"
        label="物流公司(配送方式)">
      </el-table-column>
      <el-table-column
        prop="deliverySn"
        header-align="center"
        align="center"
        label="物流單號">
      </el-table-column>
      <el-table-column
        prop="autoConfirmDay"
        header-align="center"
        align="center"
        label="自動確認時間（天）">
      </el-table-column>
      <el-table-column
        prop="integration"
        header-align="center"
        align="center"
        label="可以獲得的積分">
      </el-table-column>
      <el-table-column
        prop="growth"
        header-align="center"
        align="center"
        label="可以獲得的成長值">
      </el-table-column>
      <el-table-column
        prop="billType"
        header-align="center"
        align="center"
        label="發票類型[0->不開發票；1->電子發票；2->紙質發票]">
      </el-table-column>
      <el-table-column
        prop="billHeader"
        header-align="center"
        align="center"
        label="發票抬頭">
      </el-table-column>
      <el-table-column
        prop="billContent"
        header-align="center"
        align="center"
        label="發票內容">
      </el-table-column>
      <el-table-column
        prop="billReceiverPhone"
        header-align="center"
        align="center"
        label="收票人電話">
      </el-table-column>
      <el-table-column
        prop="billReceiverEmail"
        header-align="center"
        align="center"
        label="收票人郵箱">
      </el-table-column>
      <el-table-column
        prop="receiverName"
        header-align="center"
        align="center"
        label="收貨人姓名">
      </el-table-column>
      <el-table-column
        prop="receiverPhone"
        header-align="center"
        align="center"
        label="收貨人電話">
      </el-table-column>
      <el-table-column
        prop="receiverPostCode"
        header-align="center"
        align="center"
        label="收貨人郵編">
      </el-table-column>
      <el-table-column
        prop="receiverProvince"
        header-align="center"
        align="center"
        label="省份/直轄市">
      </el-table-column>
      <el-table-column
        prop="receiverCity"
        header-align="center"
        align="center"
        label="城市">
      </el-table-column>
      <el-table-column
        prop="receiverRegion"
        header-align="center"
        align="center"
        label="區">
      </el-table-column>
      <el-table-column
        prop="receiverDetailAddress"
        header-align="center"
        align="center"
        label="詳細地址">
      </el-table-column>
      <el-table-column
        prop="note"
        header-align="center"
        align="center"
        label="訂單備註">
      </el-table-column>
      <el-table-column
        prop="confirmStatus"
        header-align="center"
        align="center"
        label="確認收貨狀態[0->未確認；1->已確認]">
      </el-table-column>
      <el-table-column
        prop="deleteStatus"
        header-align="center"
        align="center"
        label="刪除狀態【0->未刪除；1->已刪除】">
      </el-table-column>
      <el-table-column
        prop="useIntegration"
        header-align="center"
        align="center"
        label="下單時使用的積分">
      </el-table-column>
      <el-table-column
        prop="paymentTime"
        header-align="center"
        align="center"
        label="支付時間">
      </el-table-column>
      <el-table-column
        prop="deliveryTime"
        header-align="center"
        align="center"
        label="發貨時間">
      </el-table-column>
      <el-table-column
        prop="receiveTime"
        header-align="center"
        align="center"
        label="確認收貨時間">
      </el-table-column>
      <el-table-column
        prop="commentTime"
        header-align="center"
        align="center"
        label="評價時間">
      </el-table-column>
      <el-table-column
        prop="modifyTime"
        header-align="center"
        align="center"
        label="修改時間">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
  import AddOrUpdate from './order-add-or-update'
  export default {
    data () {
      return {
        dataForm: {
          key: ''
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false
      }
    },
    components: {
      AddOrUpdate
    },
    activated () {
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/order/order/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'key': this.dataForm.key
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataList = data.page.list
            this.totalPage = data.page.totalCount
          } else {
            this.dataList = []
            this.totalPage = 0
          }
          this.dataListLoading = false
        })
      },
      // 每页数
      sizeChangeHandle (val) {
        this.pageSize = val
        this.pageIndex = 1
        this.getDataList()
      },
      // 当前页
      currentChangeHandle (val) {
        this.pageIndex = val
        this.getDataList()
      },
      // 多选
      selectionChangeHandle (val) {
        this.dataListSelections = val
      },
      // 新增 / 修改
      addOrUpdateHandle (id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      // 删除
      deleteHandle (id) {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })
        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/order/order/delete'),
            method: 'post',
            data: this.$http.adornData(ids, false)
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        })
      }
    }
  }
</script>
