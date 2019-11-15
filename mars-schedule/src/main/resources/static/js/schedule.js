$(function () {
    $("#jqGrid").jqGrid({
        url: 'schedule/list',
        datatype: "json",
        colModel: [
            { label: 'ID', name: 'id', width: 100, key: true, hidden:true },
			{ label: '任务名', name: 'taskName', width: 100},
			{ label: '任务组名', name: 'taskGroup', width: 70},
			{ label: '表达式', name: 'cron', width: 100 },
//			{ label: '执行类名', name: 'className', width: 100 },
//			{ label: '执行方法名', name: 'className', width: 100 },
//			{ label: '执行参数', name: 'params', width: 100 },
			{ label: '状态', name: 'status', width: 60, formatter: function(value, options, row){
                return value === 0 ?
                    '<span class="label label-success">正常</span>' :
                    '<span class="label label-danger">禁用</span>';
            }},
			{ label: '运行状态', name: 'triggerState', width: 60, formatter: function(value, options, row){
			    if(value == "ACQUIRED"){
			        return '<span class="label label-success">正常</span>';
			    }else if(value == "ERROR"){
                    return '<span class="label label-error">错误</span>';
                }else if(value == "BLOCKED"){
                    return '<span class="label label-warning">阻塞</span>';
                }else if(value == "PAUSED"){
                    return '<span class="label label-danger">暂停</span>';
                }else if(value == "WAITING"){
                    return '<span class="label label-info">等待</span>';
                }
            }},
            { label: '下次执行时间', name: 'nextFireTime', width: 100, formatter: function(value, options, row){
                if(value == -1){
                    return "无";
                }else{
                    var date = new Date();
                    date.setTime(value);
                    return moment(date).format("YYYY-MM-DD HH:mm:ss");
                }
            }},
            { label: '上次执行时间', name: 'prevFireTime', width: 100, formatter: function(value, options, row){
                if(value == -1){
                    return "无";
                }else{
                    var date = new Date();
                    date.setTime(value);
                    return moment(date).format("YYYY-MM-DD HH:mm:ss");
                }
            }}
//            { label: '时区', name: 'timeZoneId', width: 100 },
//            { label: '描述', name: 'desc', width: 100 },
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50,100,200],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			taskName: null
		},
		showList: true,
        title: null,
        sysScheduleInfo: {}
	},
	methods: {
		query: function () {
		    vm.showList = true;
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'taskName': vm.q.taskName},
                page:1 
            }).trigger("reloadGrid");
		},
		start: function (event) {
            var id = getSelectedRow();
            if(id == null){
                return ;
            }
            confirm('确定要启动选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "schedule/updateIsStart?id="+id+"&isStart=0",
                    success: function(r){
                        if(r.code == 0){
                            alert('操作成功', function(index){
                                $("#jqGrid").trigger("reloadGrid");
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        stop: function (event) {
            var id = getSelectedRow();
            if(id == null){
                return ;
            }
            confirm('确定要停止选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "schedule/updateIsStart?id="+id+"&isStart=1",
                    success: function(r){
                        if(r.code == 0){
                            alert('操作成功', function(index){
                                $("#jqGrid").trigger("reloadGrid");
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        enable: function (event) {
            var id = getSelectedRow();
            if(id == null){
                return ;
            }
            confirm('确定要启用选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "schedule/updateStatus?id="+id+"&status=0",
                    success: function(r){
                        if(r.code == 0){
                            alert('操作成功', function(index){
                                $("#jqGrid").trigger("reloadGrid");
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        disable: function (event) {
            var id = getSelectedRow();
            if(id == null){
                return ;
            }
            confirm('确定要禁止选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "schedule/updateStatus?id="+id+"&status=1",
                    success: function(r){
                        if(r.code == 0){
                            alert('操作成功', function(index){
                                $("#jqGrid").trigger("reloadGrid");
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
		add: function(){
            vm.showList = false;
            vm.title = "新增";
            vm.sysScheduleInfo = {};
        },
        update: function (event) {
            var id = getSelectedRow();
            if(id == null){
                return ;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
        },
        saveOrUpdate: function (event) {
            var url = vm.sysScheduleInfo.id == null ? "schedule/save" : "schedule/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.sysScheduleInfo),
                success: function(r){
                    if(r.code === 0){
                        alert('操作成功', function(index){
                            vm.reload();
                        });
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        
        getInfo: function(id){
            $.get(baseURL + "schedule/info/"+id, function(r){
                vm.sysScheduleInfo = r.sysScheduleInfo;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{'taskName': vm.q.taskName},
                page:page
            }).trigger("reloadGrid");
        }
	}
});

