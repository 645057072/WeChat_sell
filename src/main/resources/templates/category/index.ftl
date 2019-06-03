<html>
    <#include "../common/hander.ftl">
    <body>
        <div id="wrapper" class="toggled">
            <#include "../common/nav.ftl">
            <div id="page-content-wrapper">
                <div class="container">
                    <div class="row clearfix">
                        <div class="col-md-12 column">
                            <form role="form" method="get" action="/sell/seller/category/save">
                                <div class="form-group">
                                    <label>类目名称</label><input name="categoryName" class="form-control"  type="text" value="${(category.categoryName)!""}"/>
                                </div>
                                <div class="form-group">
                                    <label>类目编码</label><input name="categoryType" class="form-control"  type="number" value="${(category.categoryType)!""}"/>
                                </div>
                                </div>
                                     <input hidden name="categoryId"  value="${(category.categoryId)!""}"/>
                                    <button class="btn btn-default" type="submit">提交</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>