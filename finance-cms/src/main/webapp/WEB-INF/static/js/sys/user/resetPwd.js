layui.use(['form', 'layer'], function() {
	form = layui.form;
	layer = layui.layer;

	form.verify({
		oldPwd: function(value, item) {
			$.ajax({
				url: '/checkPassword',
				type: 'POST',
				data: {
					"password": value
				},
				dataType: 'JSON',
				async: true,
				success: function(data) {
					if(data.code == 200) {
						if(!data.data) {
							return "密码错误，请重新输入！";
						}
					}
				}
			});
		},
		newPwd: function(value, item) {
			if(value.length < 6 || value.length > 16) {
				return "密码长度只能在6-16位之间";
			}
		},
		confirmPwd: function(value, item) {
			if(!new RegExp($("#oldPwd").val()).test(value)) {
				return "两次输入密码不一致，请重新输入！";
			}
		}
	});
	
	form.on('submit(changePwd)', function() {
		layer.load();
		
	});
});