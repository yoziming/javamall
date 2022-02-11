$(function(){
      
      // 渲染购物车结构 : 
      // 关注localstorage里面的数据
      var shop_list = (function(){
            if(localStorage.getItem("cart")){
                  return JSON.parse( localStorage.getItem("cart") );
            }else{
                  return {};
            }
      })();

      var options = {
            url : "./data.json",
            dataType : "json"
      };

      $.ajax( options )
      .done( function( res ){
            render( res.data.filter( function( item ){
                  for(var attr in shop_list){
                        //遍历购物车列表; 
                        if(item.id == attr){
                              // 如果判定相等那么就意味着当前商品就是购物车里面的商品
                              // - 给商品上面加上数量; 
                              item.count = shop_list[attr];
                              return true;
                        }
                  }
                  return false;
            }));
      });

      function render( data ){
            var html = data.map( function( item ){
                  return ` <div class="goods-item">
                                    <!-- 选择框部分 -->
                                    <div class="check-box">
                                          <input type="checkbox">
                                    </div>
                                    <!-- 商品图片和商品标题 -->
                                    <div class="goods-details">
                                          <!-- 商品图片 -->
                                          <img src="${ item.src }" alt="">
                                          <!-- 商品标题 -->
                                          <p>${ item.title}</p>
                                    </div>
                                    <!-- 价格 -->
                                    <div class="price">
                                          $<span>${ item.price }</span>
                                    </div>
                                    <!-- 商品数量 -->
                                    <div class="count">
                                          <button class="reduce">-</button>
                                          <input class="goods-count" type="text" value="${item.count}">
                                          <button class="add">+</button>
                                    </div>
                                    <!-- 商品价格小计 -->
                                    <div class="item-total-price">
                                          <strong>¥ <span>${ item.count * item.price }</span></strong> 
                                    </div>
                                    <!-- 商品操作部分  -->
                                    <div class="options">
                                          <a class="delete" href="javascript:void(0)">
                                                删除
                                          </a>
                                    </div>
                              </div>`;
            }).join("");
            $(".content").html(html);
            $(".reduce").click( function(){
                  changeGoodsCount( $(this).index(".reduce") , "reduce" )
            });
            $(".add").click(function(){
                  changeGoodsCount( $(this).index(".add") , "add" )
            });
            $(".delete").click( function(){
                  removeGoods( $(this).index(".delete") )
            });    
            // 更改商品数量 
            function changeGoodsCount( index , type ){
                  var $goods_count = $(".goods-count").eq(index);
                  var count = $goods_count.val();
                  // 如果是减，那么我们需要判定count为1的情况; 
                  if( count <= 1 && type === "reduce"){
                        if( confirm("是否要删除当前商品!")){
                              removeGoods( index );
                        }
                        return false;
                  }
                  // 分成reduce减，还有add加; 
                  switch( type ){
                        case "add" : 
                              count ++ ; break;
                        case "reduce" :
                              count -- ; break;
                  }
      
                  $goods_count.val( count );
      
                  var price = $(".price span").eq(index).html().replace(/,/g,"");
                  // - 和商品数量进行计算得到对应的小计总价; 
                  var total = price * count;
                  $(".item-total-price span").eq(index).html(total);
      
                  // 在小计商品计算的时候要得知当前的商品是否被选中， 如果被选中
                  // 我们才去触发商品总价计算功能; 
                  if($(".check-box input[type=checkbox]").eq(index).prop("checked")){
                        // 计算商品总价的封装; 
                        calculatTotalPrice();
                  }
            }
            // 删除商品 
            function removeGoods( index ){
                  $(".goods-item").eq(index).remove();
                  calculatTotalPrice();
            }
            // 做商品价格总计 : 
            // - 找到合适的调用时机 : 
            //   - 商品数量改变的时候需要重新计算总价; 
            //   - 勾选商品的时候需要计算总价; 
      
            // - 商品勾选的事件 : 
      
            $(".check-box input[type=checkbox]").change( function(){
                  // 触发总价计算功能; 
                  // console.log("总价计算");
                  calculatTotalPrice();
            });
            // 计算商品总价; 
            function calculatTotalPrice(){
                  // each : 遍历功能; 
                  var total = 0;
                  var amount = 0;
                  $(".goods-item").each( function(){
                        // this : 每一个元素; 
                        // 找到元素之中的checkbox看看是否被选中; 
                        // 如果被选中再进行计算，否则不进行计算; 
                        // 在这里如果使用return false那么会立即终止掉each功能; 
                        // 所以在这里只是用return 功能; 
                        if(!$(this).find(".check-box input").prop("checked")) return ;
                        // 计算 : 
                        var price = $(this).find(".price span").html().replace(/,/g,"");
                        var count = $(this).find(".goods-count").val();
                        total += price * count;
                        amount ++;
                  });
                  $(".price-show span:eq(1)").html("$" + total);
                  $(".amount-sum em").html(amount);
            }
            
            // 全选逻辑 : 
            // - 如果全选按钮被选中，那么所有按钮都会被选中; 
            // - 如果全选按钮被取消选中，那么所有按钮都会被取消选中; 
            $(".select-all input[type=checkbox]").change( function(){
                  $(".check-box input[type=checkbox]").prop("checked" , $(this).prop("checked"));
                  calculatTotalPrice();
            });
      
            // 删除选中的商品 : 
            $(".operation a:eq(0)").click( function(){
                  $(".goods-item").each( function(){
                        // 判定如果当前商品被选中了，那么就删除当前商品; 
                        if($(this).find(".check-box input").prop("checked")){
                              $(this).remove();
                        }
                  });
                  calculatTotalPrice();
            });
      
            $(".operation a:eq(1)").click( function(){
                  $(".goods-item").remove();
                  calculatTotalPrice();
            });
      

           
      }
      
      
      // DOM对象的选择，DOM对象的事件绑定; 
     
      // - 周末任务 : 解决购物车功能失效问题; 
      // - 整理本周复习脑图; 
      // - 本周代码全部练习一遍; 
      // - 如果你的电脑上现在有火绒， 360 ， 腾讯杀毒等软件，请在本周末卸载; 
      
});