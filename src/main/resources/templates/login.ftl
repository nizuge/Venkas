<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <title>登录界面</title>
    <link href="/static/layui_login/css/default.css" rel="stylesheet" type="text/css" />
    <!--必要样式-->
    <link href="/static/layui_login/css/styles.css" rel="stylesheet" type="text/css" />
    <link href="/static/layui_login/css/demo.css" rel="stylesheet" type="text/css" />
    <link href="/static/layui_login/css/loaders.css" rel="stylesheet" type="text/css" />
    <p hidden>感谢源码之家提供的前端源码支持 http://www.mycodes.net</p>
</head>
<body>
<div class='login'>
    <div class='login_title'>
        <span>登录</span>
        <span style="color: #fd3714;font-size: 14px;padding-left: 20%">${msg}</span>
    </div>
    <div class='login_fields'>
    <form id="login_form" name="form" action="/login" method="post">
        <div class='login_fields__user'>
            <div class='icon'>
                <img alt="" src='/static/layui_login/img/user_icon_copy.png'>
            </div>
            <input name="username" placeholder='用户名' maxlength="16" type='text' autocomplete="off" value="123456"/>
            <div class='validation'>
                <img alt="" src='/static/layui_login/img/tick.png'>
            </div>
        </div>
        <div class='login_fields__password'>
            <div class='icon'>
                <img alt="" src='/static/layui_login/img/lock_icon_copy.png'>
            </div>
            <input name="password" placeholder='密码' maxlength="16" type='text' autocomplete="off">
            <div class='validation'>
                <img alt="" src='/static/layui_login/img/tick.png'>
            </div>
        </div>
        <div class='login_fields__password'>
            <div class='icon'>
                <img alt="" src='/static/layui_login/img/key.png'>
            </div>
            <input name="code" placeholder='验证码' maxlength="4" type='text' name="ValidateNum" autocomplete="off">
            <div class='validation' style="opacity: 1; right: -5px;top: -3px;">
                <canvas class="J_codeimg" id="myCanvas" onclick="Code();">对不起，您的浏览器不支持canvas，请下载最新版浏览器!</canvas>
            </div>
        </div>
        <security:form-login login-processing-url="/j_spring_security_check" username-parameter="j_username"/>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class='login_fields__submit'>
            <input id="login_in" type='button' value='LOGIN'>
        </div>
    </form>
    </div>
    <div class='success'>
    </div>
    <div class='disclaimer' hidden="hidden">
        <p>感谢源码之家提供的前端源码支持，此处因页面展示固隐藏此链接。不敬之处请原谅，感激之至！</p>
        <p>欢迎登录后台管理系统  更多源码：<a href="http://www.mycodes.net/" target="_blank">源码之家</a></p>
    </div>

</div>
<div class='authent'>
    <div class="loader" style="height: 44px;width: 44px;margin-left: 28px;">
        <div class="loader-inner ball-clip-rotate-multiple">
            <div></div>
            <div></div>
            <div></div>
        </div>
    </div>
    <p>认证中...</p>
</div>
<div class="OverWindows"></div>

<link href="/static/layui_login/layui/css/layui.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="/static/js/jquery.min.js"></script>
<script type="text/javascript" src="/static/layui_login/js/jquery-ui.min.js"></script>
<script type="text/javascript" src='/static/layui_login/js/stopExecutionOnTimeout.js?t=1'></script>
<script type="text/javascript" src="/static/layui_login/layui/layui.js"></script>
<script type="text/javascript" src="/static/layui_login/js/Particleground.js"></script>
<script type="text/javascript" src="/static/layui_login/js/Treatment.js"></script>
<script type="text/javascript" src="/static/layui_login/js/jquery.mockjax.js"></script>
<script type="text/javascript">
    var canGetCookie = 0;//是否支持存储Cookie 0 不支持 1 支持

    var CodeVal = 0;
    Code();
    function Code() {
        if(canGetCookie == 1){
            createCode("AdminCode");
            var AdminCode = getCookieValue("AdminCode");
            showCheck(AdminCode);
        }else{
            showCheck(createCode(""));
        }
    }
    function showCheck(a) {
        CodeVal = a;
        var c = document.getElementById("myCanvas");
        var ctx = c.getContext("2d");
        ctx.clearRect(0, 0, 1000, 1000);
        ctx.font = "80px 'Hiragino Sans GB'";
        ctx.fillStyle = "#E8DFE8";
        ctx.fillText(a, 0, 100);
    }
    $(document).keypress(function (e) {
        // 回车键事件
        if(e.which == 13){
            $('input[type="button"]').click();
        }

    });
    //粒子背景特效
    $('body').particleground({
        dotColor: '#E8DFE8',
        lineColor: '#133b88'
    });
    $('input[name="password"]').focus(function () {
        $(this).attr('type', 'password');
    });
    $('input[type="text"]').focus(function () {
        $(this).prev().animate({ 'opacity': '1' }, 200);
    });
    $('input[type="text"],input[type="password"]').blur(function () {
        $(this).prev().animate({ 'opacity': '.5' }, 200);
    });
    $('input[name="username"],input[name="password"]').keyup(function () {
        var Len = $(this).val().length;
        if (!$(this).val() == '' && Len >= 5) {
            $(this).next().animate({
                'opacity': '1',
                'right': '30'
            }, 200);
        } else {
            $(this).next().animate({
                'opacity': '0',
                'right': '20'
            }, 200);
        }
    });
    var open = 0;
    layui.use('layer', function () {
        //非空验证
        $('#login_in').click(function () {
            var username = $('input[name="username"]').val();
            var password = $('input[name="password"]').val();
            var code = $('input[name="code"]').val();
            if (username == '') {
                alert('请输入您的账号');
                $('input[name="username"]').focus();
            } else if (password == '') {
                alert('请输入密码');
                $('input[name="password"]').focus();
            } else if (code == '') {
                alert('请输入验证码');
                $('input[name="code"]').focus();
            } else if(code.toUpperCase() != CodeVal.toUpperCase()){
                $('input[name="code"]').val("");
                alert("验证码输入错误");
                $('input[name="code"]').focus();
            }else {
                //认证中..
                fullscreen();
                $('.login').addClass('test'); //倾斜特效
                setTimeout(function () {
                    $('.login').addClass('testtwo'); //平移特效
                }, 300);
                setTimeout(function () {
                    $('.authent').show().animate({ right: -320 }, {
                        easing: 'easeOutQuint',
                        duration: 600,
                        queue: false
                    });
                    $('.authent').animate({ opacity: 1 }, {
                        duration: 200,
                        queue: false
                    }).addClass('visible');
                }, 500);

                document.getElementById("login_form").submit();
                /*//登录
                var JsonData = { login: login, password: password, code: code };
                //此处做为ajax内部判断
                var url = 'http://'+window.location.hostname+":"+window.location.port+'/test';

                AjaxPost(url, JsonData,
                        function () {
                            //ajax加载中
                        },
                        function (data) {
                            //ajax返回
                            //认证完成
                            setTimeout(function () {
                                $('.authent').show().animate({ right: 90 }, {
                                    easing: 'easeOutQuint',
                                    duration: 600,
                                    queue: false
                                });
                                $('.authent').animate({ opacity: 0 }, {
                                    duration: 200,
                                    queue: false
                                }).addClass('visible');
                                $('.login').removeClass('testtwo'); //平移特效
                            }, 2000);
                            setTimeout(function () {
                                $('.authent').hide();
                                $('.login').removeClass('test');
                                if (data.Status == 'ok') {
                                    //登录成功
                                    $('.login div').fadeOut(100);
                                    $('.success').fadeIn(1000);
                                    $('.success').html(data.Text);
                                    //跳转操作

                                } else {
                                    AjaxErro(data);
                                }
                            }, 2400);
                        })*/
            }
        })
    })
    var fullscreen = function () {
        elem = document.body;
        if (elem.webkitRequestFullScreen) {
            elem.webkitRequestFullScreen();
        } else if (elem.mozRequestFullScreen) {
            elem.mozRequestFullScreen();
        } else if (elem.requestFullScreen) {
            elem.requestFullscreen();
        } else {
            //浏览器不支持全屏API或已被禁用
        }
    }

</script>

</body>
</html>