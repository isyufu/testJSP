<jsp:useBean id="dataUtil" class="comp.web.core.DataUtil"/>
<jsp:useBean id="filter" class="comp.web.gui.PriceListFilter" />
<%request.setCharacterEncoding("UTF-8"); //scope="session"%>
<jsp:setProperty name="filter" property="*"/>
<%@page pageEncoding="UTF-8" language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="java.util.*, comp.web.entity.Product, org.apache.commons.lang3.StringUtils"%>

<fmt:setLocale value="ru" />
<fmt:setBundle basename="text" />

<!DOCTYPE html>
<html>
    <style>
        input:invalid {
            background: hsla(0, 90%, 70%, 1);
        }
    </style>
<script type="text/javascript">
            function validate()
            {
                var a = document.getElementById("cat");
                var b = document.getElementById("prod");
                var c = document.getElementById("priceFrom");
                var d = document.getElementById("priceTo");

                var e = document.getElementById("invalidFilter");

                var valid = true;
                if(a.value.length<=0 && b.value.length<=0 && c.value.length<=0 && d.value.length<=0) {
                    e.hidden = false;
                    valid = false;
                } else {
                    e.hidden = true;
                }
                    return valid;
            };

    </script>
    <head>
        <link rel="stylesheet" href="http://netdna.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
        <link rel="stylesheet" href="http://netdna.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
    </head>
    <body class="col-md-10 col-md-offset-1">

    <div class="page-header">
      <h1><fmt:message key="pricelist.label.pageHeader" /></h1>
    </div>

      <FORM role="form" class="form-inline" NAME="form1" METHOD=POST
            ACTION="PriceList.jsp" onsubmit="return validate();">
        <div class="col-md-2">
          <div class="form-group">
            <label for="cat" ><fmt:message key="pricelist.label.cat" />:</label><br/>
            <input type="text" class="form-control" id="cat" name="cat"
                   value="<%= StringUtils.stripToEmpty(filter.getCat())%>">
          </div>
        </div>
            <div class="col-md-2">
          <div class="form-group">
            <label for="prod" ><fmt:message key="pricelist.label.prod" />:</label><br/>
            <input type="text" class="form-control" id="prod" name="prod"
                   value="<%= StringUtils.stripToEmpty(filter.getProd())%>">
          </div>
        </div>
            <div class="col-md-2">
           <div class="form-group">
             <label for="priceFrom" ><fmt:message key="pricelist.label.priceFrom" />:</label><br/>
             <input type="number" min="1" max="500000" class="form-control"
                    id="priceFrom" name="priceFrom" value="<%= filter.getPriceFrom()%>">
            </div>
        </div>
            <div class="col-md-2">
           <div class="form-group">
             <label for="priceTo" ><fmt:message key="pricelist.label.priceTo" />:</label><br/>
             <input type="number" min="1" max="500000" class="form-control"
                    id="priceTo" name="priceTo" value="<%= filter.getPriceTo()%>">
            </div>
        </div>
        <div class="col-md-4">
          <br/>
          <input type=submit class="btn btn-default" style="margin-top: 5px;"
                 value="<fmt:message key="pricelist.buttom.find" />">
        </div>
      </FORM>


      <div class="col-md-6 " style="margin-top: 20px;">
        <label id="invalidFilter" style="color:red" hidden="true">
            <fmt:message key="pricelist.label.invalidFilter" />
        </label>
        <table id="price-list" class="table table-hover table-bordered" >
          <tr class="info" >
            <td class="col-md-4" ><fmt:message key="pricelist.label.cat" /></td>
            <td class="col-md-4" ><fmt:message key="pricelist.label.prod" /></td>
            <td class="col-md-4" ><fmt:message key="pricelist.label.price" /></td>
          </tr>
            <%
                List<Product> prods = dataUtil.getProds(filter.getCat(),
                    filter.getProd(), filter.getPriceFrom(), filter.getPriceTo());
                for(Product p : prods) {
                    %>
                        <tr>
                            <td><%=p.getCat().getName()%></td>
                            <td><%=p.getName()%></td>
                            <td><%=String.format("%.2f", p.getPrice())%></td>
                        </tr>
                    <%
                }
          %>
        </table>
      </div>

    </body>
</html>