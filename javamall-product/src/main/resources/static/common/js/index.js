;(function(){
    var user_ele = document.getElementById("userFather");
  
    var login_btn = document.getElementById("login_btn");

 
    var username = getCookie("username");

    if( username ){
   
        // user_ele.style.display = "flex";
        login_btn.innerHTML= username;
        console.log(username);
    }
})();


    // 滚动条事件
    function isTabShow() {
        if(window.scrollY >= 576) {
            $('.left_tab').css({position: 'fixed',top: 50})
            $('.right_tab').css({position: 'fixed',top: 50})
            $('.to_top').css({display:'block',borderTop: "1px solid #ddd"});
        }else if(window.scrollY < 576) {
            $('.left_tab').css({position: 'absolute',top: 650})
            $('.right_tab').css({position: 'absolute',top: 650})
            $('.to_top').css('display','none');
            
        }  
    }
    isTabShow();

    function isTab() {
        if(window.scrollY > 128) {
            $('.s_hide').hide();
            $('.s_show').show();
            $(".tab .tabItem").css({padding :"0 18px"})
            $('.tab').css({ marginTop: 10})
            $('.tab').parent().siblings().hide();
            $('.tab').parent().parent().css({position: 'fixed',top: 0, zIndex: 999,background: "#fff", borderBottom: "1px solid #ddd",width:"100%"})
        }else if(window.scrollY <= 128) {
            $(".tab .tabItem").css({padding :"0 24px"}).first().css({paddingLeft :0});
            $('.s_hide').show();
            $('.s_show').hide();
            $('.tab').css({ marginTop: 27})
            $('.tab').parent().siblings().show();
            $('.tab').parent().parent().css({position: 'static',top: 0, zIndex: 999,background: "#fff", borderBottom: "1px solid #ddd",width:"100%"})
        }
    }

    $(window).scroll(function(){
        isTabShow();
        isTab();
        items();
    })

    //返回顶部
    $('.to_top').click(function() {
        $('html,body').animate({
            scrollTop: 0
        }, 800)
    })

    //电梯导航
    var t = $(".products")[0].offsetTop - 244;
    var w = $('.pros').outerHeight() + 60;
    var ns, tt;

    $(".left_tab").find("dd").click(function(){
        ns = $(this).index() - 2;
        $(this).find("span").css({color:"#bb3343",fontWeight:700});
        $(this).siblings().find("span").css({color:"#333",fontWeight:400});
        tt = t + w * ns;
        $('html,body').animate({
            scrollTop: tt
        }, 800)
    })
    
    $(".left_tab").find("dd").mouseenter(function(){
        $(this).find("span").css({color:"#bb3343",fontWeight:700});
        $(this).siblings().find("span").css({color:"#333",fontWeight:400});
    })
    $(".left_tab").find("dd").mouseleave(function(){
        $(".left_tab dd").find("span").css({color:"#333",fontWeight:400});
        $(".left_tab dd").eq(ns).find("span").css({color:"#bb3343",fontWeight:700});
    })


    function items() {
        var ws = window.scrollY;
        ns = parseInt( (ws - t) / w) + 1;
        if(ws >= t) {
            $(".left_tab dd").find("span").css({color:"#333",fontWeight:400});
            $(".left_tab dd").eq(ns).find("span").css({color:"#bb3343",fontWeight:700});
        }
        else if(ws < t) {
            $(".left_tab dd").find("span").css({color:"#333",fontWeight:400});
            $(".left_tab dd").eq(0).find("span").css({color:"#bb3343",fontWeight:700});
        }
    }
    items();
   $(".w-icon-close").click(function(){
       $(".m-appDownloadGuide").css("display","none");
   })