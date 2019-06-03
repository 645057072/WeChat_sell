<html>
    <#include "../common/hander.ftl">

    <body>
    <div id="wrapper" class="toggled">

        <#--边框sidebar-->
        <#include "../common/nav.ftl">

        <#--主题内容content-->
         <div id="page-content-wrapper">
                <div class="container-fluid">
                    <div class="row clearfix">
                        <div class="col-md-12 column">
                            <table class="table table-bordered table-condensed">
                                <thead>
                                <tr>
                                    <th>订单编号</th>
                                    <th>姓名</th>
                                    <th>手机号码</th>
                                    <th>送货地址</th>
                                    <th>微信号</th>
                                    <th>订单金额</th>
                                    <th>订单状态</th>
                                    <th>支付状态</th>
                                    <th>创建时间</th>
                                    <th colspan="2">操作</th>
                                </tr>
                                </thead>

                                <tbody>
                            <#list orderDTOPage.content as orderDTO>
                            <tr>
                            <td>${orderDTO.orderId}</td>
                            <td>${orderDTO.buyerName}</td>
                            <td>${orderDTO.buyerPhone}</td>
                                <td>${orderDTO.buyerAddress}</td>
                            <td>${orderDTO.buyerOpenid}</td>
                            <td>${orderDTO.orderAmount}</td>
                            <td>${orderDTO.getOrderStatusEnum().message}</td>
                             <td>${orderDTO.getPayStatus().message}</td>
                             <td>${orderDTO.createTime}</td>
                              <td><a href="/sell/seller/order/detail?orderId=${orderDTO.orderId}">详情</a></td>
                             <td> <#if orderDTO.getOrderStatusEnum().message!="已取消">
                            <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}" >取消</a></#if></td>
                             </tr>
                             </#list>
                                </tbody>
                            </table>
                        </div>

                    <#--分页-->
                        <div class="col-md-12 column">
                            <ul class="pagination pull-right">
                    <#if currentPage lte 1>
                        <li class="disabled"><a href="#">上页</a></li>
                    <#else>
                        <li><a href="/sell/seller/order/list?page=${currentPage-1}&size=${size}">上页</a></li>
                    </#if>

                    <#list 1..orderDTOPage.getTotalPages() as index>
                        <#if currentPage==index>
                         <li class="disabled"><a href="#">${index}</a></li>
                        <#else>
                            <li><a href="/sell/seller/order/list?page=${index}&size=${size}">${index}</a></li>
                        </#if>

                    </#list>
                    <#if currentPage gte orderDTOPage.getTotalPages()>
                         <li class="disadbled"><a href="#">下页</a></li>
                    <#else>
                        <li><a href="/sell/seller/order/list?page=${currentPage+1}&size=${size}">下页</a></li>
                    </#if>
                            </ul>
                    </div>
                </div>
              </div>
         </div>
    </div>

<#--弹窗-->
    <div class="modal fade" id="myModal" role="dialog" aria-hidden="true" aria-labelledby="myModalLabel">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button class="close" aria-hidden="true" type="button" data-dismiss="modal">×</button>
                                <h4 class="modal-title" id="myModalLabel">
                                    提示
                                </h4>
                            </div>
                            <div class="modal-body">
                                你有新的订单
                            </div>
                            <div class="modal-footer">
                                <button onclick="javascript: document.getElementById('notice').onpause" class="btn btn-default" type="button" data-dismiss="modal">关闭</button>
                                <button onclick="location.reload()" class="btn btn-primary" type="button">查看详情</button>
                            </div>
                        </div>

                    </div>
    </div>

    <#--播放音乐-->
    <audio id="notice" loop="loop">
        <source src="/sell/mp3/song.mp3" type="audio/mpeg"/>
    </audio>
    <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/twitter-bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script>
        var websocket=null;
        if ('WebSocket' in  window){
            websocket=new WebSocket('ws://hlx.nat100.top/sell/webSocket');
        }else {
            alert("该浏览器不支持websocket！")
        }
        websocket.onopen=function (event) {
            console.log("建立连接")
        }
        websocket.onclose=function (event) {
            console.log("关闭连接")
        }
        websocket.onmessage=function (event) {
            console.log("收到消息："+event.data)
            $('#myModal').modal('show');
            document.getElementById('notice').onplay;
        }
        websocket.onerror =function () {
            alert("通信发送错误")
        }
        websocket.onbeforeunload=function () {
            websocket.close;
        }
    </script>
    </body>
</html>