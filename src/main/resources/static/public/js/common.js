//ajax全局配置
$.ajaxSetup({
    dataType: "json",
    contentType: "application/json",
    cache: false
});

//选择多条记录
function getSelectedRows(){
    var rows = $("#table").bootstrapTable('getSelections');
    if (rows.length==0){
        layer.alert('请选择一条记录')
        return;
    }
    return rows;

}
/*
* 选择一条记录
* */
function getSelectedRow() {
    var rows = $("#table").bootstrapTable('getSelections');
    if (rows.length== 0){
        layer.alert("请选择一条记录")
        return;
    }else if (rows.length>1){
        layer.alert("只能选择一条记录")
        return;
    }
    return rows[0];
}



function hasPermission(permission) {
    if (window.parent.permissions.indexOf(permission) > -1) {
        return true;
    } else {
        return false;
    }
}

function doTask(id, msg, url) {
    var rows = getSelectedRows();
    if (rows == null) {
        return;
    }
    //var id = 'jobId';
    //提示确认框
    layer.confirm('您确定要' + msg + '所选数据吗？', {
        btn: ['确定', '取消'] //可以无限个按钮
    }, function (index, layero) {
        var ids = new Array();
        //遍历所有选择的行数据，取每条数据对应的ID
        $.each(rows, function (i, row) {
            ids[i] = row[id];
        });

        $.ajax({
            type: "POST",
            url: url,
            data: JSON.stringify(ids),
            success: function (r) {
                if (r.code === 0) {
                    layer.alert(msg + '成功');
                    $('#table').bootstrapTable('refresh');
                } else {
                    layer.alert(r.msg);
                }
            },
            error: function () {
                layer.alert('服务器没有返回数据，可能服务器忙，请重试');
            }
        });
    });
}