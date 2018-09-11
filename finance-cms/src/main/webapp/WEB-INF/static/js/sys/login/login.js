layui.use("layer", function() {
	layer = layui.layer;

	// 回车事件
	$(window).keydown(function(event) {
		if(event.keyCode == 13) {
			$("#signIn").trigger("click");
		}
	});

	// 登录
	$("#signIn").on("click", function() {
		var userName = $("#userName").val();
		var password = $("#password").val();

		if(userName.trim().length <= 0) {
			layer.msg("请输入用户名", {
				anim: 6
			});
			return false;
		}
		if(password.length <= 0) {
			layer.msg("请输入密码", {
				anim: 6
			});
			return false;
		}

		var param = {
			"userName": userName,
			"password": password
		};
		
		$.ajax({
			type: "POST",
			url: "/signIn",
			async: true,
			data: JSON.stringify(param),
			dataType: "JSON",
			contentType: "application/json; charset=utf-8",
			beforeSend: function() {
				layer.load();
			},
			success: function(data) {
				layer.closeAll('loading');
				if(data.code == 200) {
					layer.msg(data.msg, {
						time: 1500,
						icon: 1,
						skin: 'msg'
					});
					setTimeout(function() {
						window.parent.location.href = "/" + data.data;
						if(self != top) {
							window.top.location.replace(self.location + data.data);
						}
					}, 2000);
				} else {
					layer.msg(data.msg, {
						time: 1500,
						icon: 5,
						anim: 6,
						skin: 'msg'
					});
				}
			},
			error: function() {
				layer.closeAll('loading');
				layer.msg("登录异常，请联系管理员", {
					time: 1500,
					icon: 5,
					anim: 6,
					skin: 'msg'
				});
			}
		});
	});
});