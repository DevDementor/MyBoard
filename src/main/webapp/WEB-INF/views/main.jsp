<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Spring MVC02</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            loadList();
        });

        function loadList() {
            goList();
            $.ajax({
                url: "boardList.do",
                action: "get",
                success: makeList,
                error: function () {
                    alert("boardList.do GET Fail");
                }
            });
        }

        function makeList(data) {

            listHtml = "";
            listHtml += "<table class='table table-bordered table-hover'>";
            listHtml += "<tr>";
            listHtml += "<td>번호</td>";
            listHtml += "<td>제목</td>";
            listHtml += "<td>작성자</td>";
            listHtml += "<td>작성일</td>";
            listHtml += "<td>조회수</td>"
            listHtml += "</tr>"

            $.each(data, function (index, obj){
                listHtml += "<tr>";
                listHtml += "<td>"+obj.idx+"</td>";
                listHtml += "<td id='title_"+obj.idx+"'><a href='javascript:goContent("+obj.idx+")'>"+obj.title+"</td>";
                listHtml += "<td>"+obj.writer+"</td>";
                listHtml += "<td>"+obj.indate+"</td>";
                listHtml += "<td id='count_"+obj.idx+"'>"+obj.count+"</td>";
                listHtml += "</tr>"

                listHtml+="<tr id='c"+obj.idx+"' style='display:none'>";
                listHtml+="<td>내용</td>";
                listHtml+="<td colspan='4'>";
                listHtml+="<textarea id='ta"+obj.idx+"' readonly rows='7' class='form-control'></textarea>";
                listHtml+="<br/>";
                listHtml+="<span id='ub"+obj.idx+"'><button class='btn btn-success btn-sm' onclick='goUpdateForm("+obj.idx+")'>수정화면</button></span>&nbsp;";
                listHtml+="<button class='btn btn-warning btn-sm' onclick='goDelete("+obj.idx+")'>삭제</button>";
                listHtml+="</td>";
                listHtml+="</tr>";
            })
            listHtml += "<tr>";
            listHtml += "<td colspan='5'>";
            listHtml += "<button class='btn btn-primary btn-sm' onclick='goForm()'>글쓰기</button>";
            listHtml += "</td>";
            listHtml += "</tr>";
            listHtml += "</table>";
            $("#view").html(listHtml);
        }

        function goForm() {
            $("#view").css('display', 'none');
            $("#wform").css('display', 'block');
        }

        function goList() {
            $("#view").css('display', 'block');
            $("#wform").css('display', 'none');
        }

        function goInsert() {
            var formData = $("#frm").serialize();
            $.ajax({
                url: "boardInsert.do",
                type: "post",
                data: formData,
                success: loadList,
                error: function () {
                    alert("boardInsert.do Fail");
                }
            });
            $("#fclear").trigger("click");
        }

        function goContent(idx){

            if($("#c"+idx).css("display") == "none") {//내용 열림
                $("#c" + idx).css("display", "table-row");

                //내용 가져오기
                $.ajax({
                    url : '/boardContent.do',
                    type : 'get',
                    data : {"idx" : idx},
                    success : function (data){
                        console.log(data);
                        $("#ta"+idx).text(data.content);
                    },
                    error : function (){
                        alert("Fail to get boardContent.do");
                    }

                });

                //카운트
                $.ajax({
                    url : '/boardCount.do',
                    type : 'get',
                    data : {"idx":idx},
                    success : function (data){
                        $("#count_"+idx).text(data.count);
                    },
                    error : function(){
                        alert("Fail to get boardCount");
                    }
                });
            }else{//내용 닫힘
                $("#c" + idx).css("display", "none");
            }
        }

        function goDelete(idx){
            $.ajax({
                url :"boardDelete.do",
                type :"get",
                data :{"idx":idx},
                success : loadList,
                error : function (){
                    alert("boardDelete.do Fail");
                }
            });
        }
        function goUpdateForm(idx){
            //제목의 태그를 input type으로 변경
            let title = $("#title_"+idx).text();
            let newTitle = "<input type='text' id='newTitle_"+idx+"' class='form-control' value='"+title+"'></input>"
            $("#title_"+idx).html(newTitle);

            //내용 수정 가능하게 readonly = false
            $("#ta"+idx).attr("readonly",false);

            //'수정화면' -> '수정'으로 버튼 변경
            let updateBtn = "<button class='btn btn-success btn-sm' onclick='goUpdate("+idx+")'>수정</button>"
            $("#ub"+idx).html(updateBtn);
        }

        function goUpdate(idx){
            let title = $("#newTitle_"+idx).val();
            let content = $("#ta"+idx).val();

            $.ajax({
                url : "/boardUpdate.do",
                type : "post",
                data  : {"idx":idx, "title" : title, "content" : content},
                success : loadList,
                error  : function(){
                    alert("boardUpdate Fail");
                }
            })
        }
    </script>
</head>
<body>

<div class="container">
    <h2>Spring MVC02</h2>
    <div class="panel panel-default">
        <div class="panel-heading">BOARD</div>
        <div class="panel-body" id="view">Panel Content</div>
        <div class="panel-body" id="wform">
            <form id="frm">
                <table class="table">
                    <tr>
                        <td>제목</td>
                        <td><input type="text" id="title" name="title" class="form-control"/></td>
                    </tr>
                    <tr>
                        <td>내용</td>
                        <td><textarea rows="7" id="content" name="content" class="form-control" ></textarea></td>
                    </tr>
                    <tr>
                        <td>작성자</td>
                        <td><input type="text" id="writer" name="writer" class="form-control"/></td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                            <button type="button" class="btn btn-success btn-sm" onclick="goInsert()">등록</button>
                            <button type="reset" class="btn btn-warning btn-sm" id = "fclear">취소</button>
                            <button type="button" class="btn btn-info btn-sm" onclick="goList()">리스트</button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <div class="panel-footer">인프런_스프1탄_박매일</div>
    </div>
</div>

</body>
</html>