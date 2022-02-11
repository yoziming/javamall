      $(function(){
        var options = {
              url : "./data.json",
              dataType : "json"
        }
        $.ajax( options )
        .done( function(res){
              render(res.data);
        });
        function render( res ){
              var html = "";
              html = res.map(function( item ){
                    return `
                    <div class="goods-item">
                          <div class="wrapper">
                                    <a href="./details.html" data-id="${item.id}"class = "addcart" >
                                          <img src="${ item.src }" data-id="${item.id}" class = "addcart"alt="">
                                    </a>
                                    <div class="price">${ item.price }</div>
                                    <div class="title"> ${ item.title }</div>
                                    <a class = "add-cart" href="javascript:void(0)" data-id="${ item.id }"> 加入购物车</a>
                           </div>
                    </div>
                    `
              }).join("");

              $(".goods-list").html( html );

        }
       
    

        $(".goods-list").on("click" , ".addcart", function(){
          
              inpage($(this).attr("data-id"));
        });
        var shop_list = (function () {
            if (localStorage.getItem("goods")) {

                return JSON.parse(localStorage.getItem("goods"));
            } else {
                return {};
            }
        })();

        function inpage(id) {


            if (id in shop_list) {
                shop_list[id]++;
            } else {
                shop_list[id] = 1;
            }


            localStorage.setItem("goods", JSON.stringify(shop_list));
        }

        
      var shop_list1 = (function(){
              if(localStorage.getItem("cart")){
                    return JSON.parse( localStorage.getItem("cart") );
              }else{
                    return {};
              }
        })();

      $(".goods-list").on("mouseup", ".add-cart", function () {
            addCart($(this).attr("data-id"));
        });
        function addCart( id ){
           
              if( id in shop_list1 ){
                    shop_list1[id] ++ 
              }else{
                    shop_list1[id] = 1;
              }

              localStorage.setItem( "cart" , JSON.stringify(shop_list1) );
        }
  })