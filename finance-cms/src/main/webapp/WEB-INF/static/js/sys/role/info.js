layui.use(['form', 'layer', 'authtree'], function() {
	form = layui.form;
	authtree = layui.authtree;
	layer = parent.layer === undefined ? layui.layer : top.layer;
	
	$.post("/sysRole/getPermission", {
		"sysRoleId": $('.id').val()
	}, function(res) {
		var json = res.data.permission;
		var ids = res.data.ids;
		
		var permission = authtree.listConvert(json, {
			primaryKey: 'id',
			parentKey: 'parentId',
			startPid: '0',
			nameKey: 'name',
			valueKey: 'id',
			checkedKey: ids
		});
		
		console.log(permission);
		
		authtree.render('#LAY-auth-tree-index', permission, {
			inputname: 'sysResourceIds',
			openchecked: true,
			autowidth: true,
			openall: true
		});
	});
	
	form.verify({
		name: function(value, item) {
			if(value.length > 16) {
				return "资源名称长度不能大于16位";
			}
		},
		code: function(value, item) {
			if(value.length > 32) {
				return "资源编码长度不能大于32位";
			}
			
			var msg;
			$.ajax({
				url: '/sysRole/checkCode',
				type: 'POST',
				data: {
					"code": value,
					"sysRoleId": $('.id').val()
				},
				dataType: 'JSON',
				async: false,
				success: function(res) {
					if(res.code == 200) {
						if(res.data) {
							msg = "该资源编码已被使用";
						}
					}
				}
			});
			return msg;
		}
	});
	
	form.on("submit(create)", function(data) {
		var index = top.layer.load();
		var type;
		if(data.field.id) {
			type = 'PUT';
		} else {
			type = 'POST';
		}
		
		$.ajax({
			url: '/sysResource',
			type: type,
			data: JSON.stringify(data.field),
			dataType: 'JSON',
			contentType: 'application/json;charset=UTF-8',
			async: true,
			success: function(res) {
				top.layer.close(index);
				top.layer.msg(res.msg, {
					icon: 6,
					time: 1500
				});
				setTimeout(function() {
					if(res.code == 200) {
						layer.closeAll("iframe");
						parent.location.reload();
					}
				}, 2000);
			},
			error: function() {
				top.layer.close(index);
				layer.msg('操作失败', {
					icon: 5,
					anim: 6
				});
			}
		});
	});
	
	return false;
});
