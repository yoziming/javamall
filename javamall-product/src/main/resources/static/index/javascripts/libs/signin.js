$(function(){
    $("#signin_btn").click(function(){
        $.post("http://127.0.0.1/php/signin.php",
        {
            username:$("#usernameInput").val(),
            password:$("#passwordInput").val()
        },
        function(res){
            res = typeof res == 'string' ? JSON.parse(res):res;
            if( res.type === "success"){
                alert("恭喜登入成功，即将为您跳转到首页!");
                  
                setCookie("username" , res.username);
                setTimeout( function(){
                    location.href = "./index.html";
                } , 1000);
            }
            if( res.type === "error" ){
                alert("抱歉登入失败 :" + res.msg + "!" );
            }
        },"json")
    })
})