<html>
    <#include "../common/hander.ftl">

<body>
<div id="wrapper" class="toggled">

<#--边框sidebar-->
        <#include "../common/nav.ftl">

<#--主题内容content-->
    <div id="page-content-wrapper">
        <div class="container">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <form role="form" method="get" action="/sell/seller/product/save">
                        <div class="form-group">
                            <label>商品名称:</label><input name="productName" class="form-control"  type="text" value="${(productInfo.productName)!""}" />
                        </div>
                        <div class="form-group">
                            <label>商品单价:</label><input name="productPrice" class="form-control"  type="text" value="${(productInfo.productPrice)!""}" />
                        </div>
                        <div class="form-group">
                            <label>商品库存:</label><input name="productStock" class="form-control"  type="number" value="${(productInfo.productStock)!""}" />
                        </div>
                        <div class="form-group">
                            <label>商品描述:</label><input name="productDescription" class="form-control"  type="text" value="${(productInfo.productDescription)!""}" />
                        </div>

                        <div class="form-group">
                            <label>商品图片:</label>
                            <img height="100" width="100" src="${(productInfo.productIcon)!""}" alt="">
                            <input name="productIcon" class="form-control"  type="text" value="${(productInfo.productIcon)!""}" />
                        </div>

                        <div class="form-group">
                            <label>商品类目:</label>
                            <select name="categoryType" class="form-control">
                                <#list productCategoryList as category>
                                    <option value="${category.categoryType}"
                                    <#if (productInfo.categoryType)?? && productInfo.categoryType==category.categoryType>
                                            selected
                                    </#if>
                                    >${category.categoryName}
                                    </option>
                                </#list>
                            </select>
                        </div>
                        <input hidden name="productId"  value="${(productInfo.productId)!''}"/>
                        <button class="btn btn-default" type="submit">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>