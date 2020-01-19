$(function () {
    $('#table').bootstrapTable({
        url: 'menu/list',
        showRefresh: true,//刷新按钮
        search: true,//模糊搜索
        striped: true,//隔行换色
        pagination: true,//客户端分页
        sidePagination: 'server',//服务器端分页
        toolbar: '#toolbar',//嵌套到表格里面
        columns: [
            {
                field: 'menuId',
                title: '序号',
                formatter: function (value, row, index) {
                    return index + 1;
                }
            },
            {
                checkbox: true,
                // field: ''
            },
            {
                field: 'menuId',
                title: 'ID'
            },
            {
                field: 'name',
                title: '菜单名称'
            },
            {
                field: 'parentName',
                title: '父菜单名称'
            },
            {
                field: 'url',
                title: 'url'
            },
            {
                field: 'icon',
                title: '图标',
                formatter: function (value) {
                    return value == null ? ' ' : '<i class="' + value + '"></i>';
                }
            },
            {
                field: 'type',
                title: '类型',
                formatter: function (value) {
                    if (value == 0) {
                        return '<button class="btn btn-xs btn-danger">目录</button>';
                    } else if (value == 1) {
                        return '<button class="btn btn-xs btn-success">菜单</button>';
                    } else if (value == 2) {
                        return '<button class="btn btn-xs btn-primary">按钮</button>';
                    } else {
                        return ' ';
                    }
                }
            },
            {
                field: 'perms',
                title: '授权标识'
            },
            {
                field: 'orderNum',
                title: '排序'
            },
        ]
    })
});

var vm = new Vue({
    el: '#dtapp',
    data: {
        showList: true,
        title: '',
        menu: {},
    },
    methods: {
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.menu = {type: 1, parentName: null, parentId: 0, orderNum: 0}

            vm.getMenu();
        },
        update: function () {
            var row = getSelectedRow();
            var menuId=row['menuId']
            if(menuId==null){
                return;
            }
            $.get('menu/info/'+menuId,function (r) {
                vm.showList=false;
                vm.menu=r.menu;
                vm.menu.parentName=row.parentName;
            });

            vm.getMenu();
        },
        del: function () {
            var rows = getSelectedRows();
            if (rows == null) {
                return;
            }
            layer.confirm("您确定要删除所选数据吗？", {
                btn: ['确定', '取消']
            }, function () {
                var ids = new Array();
                $.each(rows, function (i, row) {
                    ids[i] = row['menuId']
                });
                $.ajax({
                    type: 'POST',
                    url: 'menu/del',
                    data: JSON.stringify(ids),//把数组转为json
                    success: function (r) {
                        if (r.code == 0) {
                            layer.alert(r.msg);
                            $("#table").bootstrapTable('refresh')
                        } else {
                            layer.alert(r.msg)
                        }
                    },
                    error: function () {
                        layer.alert('服务器没有返回数据，可能服务器忙，请稍后重试')
                    }
                })

            })
        },
        saveOrUpdate: function () {
            /*如果没有menuId,那就是调save，如果有，那就是更新update*/
            var url = vm.menu.menuId == null? 'menu/save':'menu/update';
            $.ajax({
                type:"POST",
                url:url,
                data:JSON.stringify(vm.menu),
                success:function (r) {
                    if (r.code==0){
                        layer.alert(r.msg,function (index) {
                            layer.close(index);
                            vm.reload();
                        })
                    }else {
                        layer.alert(r.msg)
                    }
                }
            })
        },
        reload: function () {
            vm.showList = true;
            /*刷新表格数据*/
            $("#table").bootstrapTable('refresh')
        },
        menuTree: function () {
            layer.open({
                type: 1,
                offset: '50px',//里页面上边缘50px
                skin: 'layui-layer-molv',
                title: '选择菜单',
                area: ['300px', '450px'],//宽300，高50
                shade: 0,//是否有阴影
                shadeColse: false,//开启/关闭 遮罩关闭
                /*closeBtn:0,//不显示关闭按钮*/
                anim: 2,
                btn: ['确定', '取消'],
                content: $('#menuLayer'),
                btn1: function (index) {
                    var treeObj = $.fn.zTree.getZTreeObj("menuTree");
                    var nodes = treeObj.getSelectedNodes();
                    vm.menu.parentName = nodes[0].name;
                    vm.menu.parentId=nodes[0].menuId;
                    layer.close(index);
                }
            })
        },
        getMenu: function () {
            var setting = {
                data: {
                    simpleData: {
                        enable: true,
                        idKey: 'menuId',
                        pIdKey: 'parentId',
                        rootPId: -1
                    },
                    key: {
                        url: 'noUrl'
                    }
                }
            };
            /*var zNodes =[
                { id:1, pId:0, name:"父节点1 - 展开", open:true},
                { id:11, pId:1, name:"父节点11 - 折叠"},
                { id:111, pId:11, name:"叶子节点111"},
                { id:112, pId:11, name:"叶子节点112"},
                { id:113, pId:11, name:"叶子节点113"},
                { id:114, pId:11, name:"叶子节点114"},
                { id:12, pId:1, name:"父节点12 - 折叠"},
                { id:121, pId:12, name:"叶子节点121"},
                { id:122, pId:12, name:"叶子节点122"},
                { id:123, pId:12, name:"叶子节点123"},
                { id:124, pId:12, name:"叶子节点124"},
                { id:13, pId:1, name:"父节点13 - 没有子节点", isParent:true},
                { id:2, pId:0, name:"父节点2 - 折叠"},
                { id:21, pId:2, name:"父节点21 - 展开", open:true},
                { id:211, pId:21, name:"叶子节点211"},
                { id:212, pId:21, name:"叶子节点212"},
                { id:213, pId:21, name:"叶子节点213"},
                { id:214, pId:21, name:"叶子节点214"},
                { id:22, pId:2, name:"父节点22 - 折叠"},
                { id:221, pId:22, name:"叶子节点221"},
                { id:222, pId:22, name:"叶子节点222"},
                { id:223, pId:22, name:"叶子节点223"},
                { id:224, pId:22, name:"叶子节点224"},
                { id:23, pId:2, name:"父节点23 - 折叠"},
                { id:231, pId:23, name:"叶子节点231"},
                { id:232, pId:23, name:"叶子节点232"},
                { id:233, pId:23, name:"叶子节点233"},
                { id:234, pId:23, name:"叶子节点234"},
                { id:3, pId:0, name:"父节点3 - 没有子节点", isParent:true}
            ];*/
            $.get("menu/select", function (r) {
                var treeObj = $.fn.zTree.init($("#menuTree"), setting, r.menuList);
                /*点击修改，在ztree中选中parentId（回显功能）*/
                var node = treeObj.getNodeByParam("menuId", vm.parentId, null);
                treeObj.selectNode(node)
            })

        }
    }
});