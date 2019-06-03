<html>
    <#include "../common/hander.ftl">
<body>
    <div id="wrapper" class="toggled">
<#--边框sidebar-->
        <#include "../common/nav.ftl">
    //详情内容content
    <div id="page-content-wrapper">
        <div class="container">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>订单编码</th>
                            <th>姓名</th>
                            <th>送货地址</th>
                            <th>联系电话</th>
                            <th>订单金额</th>
                            <th>订单状态</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${orderDTO.orderId}</td>
                            <td>${orderDTO.buyerName}</td>
                            <td>${orderDTO.buyerAddress}</td>
                            <td>${orderDTO.buyerPhone}</td>
                            <td>${orderDTO.orderAmount}</td>
                            <td>${orderDTO.orderStatusEnum.message}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="col-md-12 column">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>商品ID</th>
                            <th>商品名称</th>
                            <th>商品数量</th>
                            <th>商品单价</th>
                            <th>商品金额</th>
                        </tr>
                        </thead>
                        <tbody>
                <#list orderDTO.orderDetailList as orderDetail>
                <tr>
                    <td>${orderDetail.productId}</td>
                    <td>${orderDetail.productName}</td>
                    <td>${orderDetail.productQuantity}</td>
                    <td>${orderDetail.productPrice}</td>
                    <td>${orderDetail.productQuantity*orderDetail.productPrice}</td>
                </tr>
                </#list>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="col-md-12 column">
        <#if orderDTO.getOrderStatusEnum().message=="新订单">
            <a href="/sell/seller/order/finish?orderId=${orderDTO.orderId}" class="btn btn-default btn-success" type="button">订单完结</a>
            <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}" class="btn btn-default btn-danger" type="button">取消订单</a>
        </#if>
            </div>
        </div>
    </div>
</div>
</body>
</html>