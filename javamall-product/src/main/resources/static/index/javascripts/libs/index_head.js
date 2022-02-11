//搜索框动态词条滚动
$(function() {

    (function () {

        var n = 0;
        var Timer = setInterval(searchTime, 3000);

        function searchTime() {
            if (n == 0) {
                $('.search_item div').css({
                    top: 0
                })
            }
            n++;
            var top = -$('.search_item').height() * n;
            $('.search_item div').animate({
                top: top
            }, 500);
            if (n == 3) {
                n = 0;
            }
        }
        $('.search_item').on('mouseenter', function () {
            clearInterval(Timer);
        })
        $('.search_item').on('mouseleave', function () {
            Timer = setInterval(searchTime, 3000);
        })
    })()
})

//滚动条事件

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
    isTab();
})