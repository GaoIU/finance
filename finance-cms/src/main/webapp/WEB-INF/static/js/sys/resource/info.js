layui.use(['form', 'layer'], function() {
	form = layui.form;
	layer = parent.layer === undefined ? layui.layer : top.layer;
	
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
});