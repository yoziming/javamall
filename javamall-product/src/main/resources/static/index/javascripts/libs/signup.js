$(function(){
    $("#signup_btn").click(function(){
        $.post("http://127.0.0.1/php/signup.php",{
            username:$("#usernameInput").val(),
            password:$("#passwordInput").val()
        },
        function(res){
            res = typeof res == 'string'?JSON.parse(res):res;
            if( res.type === "success"){
                alert("注册成功，稍后我们会为您跳转到登入界面！");
                setTimeout(function(){
                    location.href= "./login.html";
                },1000)
            }
            if(res.type === "error"){
                alert(res.msg);
            }
        },"json");
    })

})