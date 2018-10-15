layui.use(['form', 'layer'], function() {
	form = layui.form;
	layer = parent.layer === undefined ? layui.layer : top.layer;
	
	$.post("/sysRole/getPermission", {
		"sysRoleId": $('.id').val()
	}, function(res) {
		var json = res.data;
		var menu = authtree(json, "");
		$("#LAY-auth-tree-index").html(menu);
		
		$(".parent").click(function() {
			if($(this).hasClass('fa-chevron-down')) {
				$(this).removeClass('fa-chevron-down');
				$(this).addClass('fa-chevron-right');
				$(this).parent().find('ul').eq(0).css('display', 'none');
			} else {
				$(this).removeClass('fa-chevron-right');
				$(this).addClass('fa-chevron-down');
				$(this).parent().find('ul').eq(0).css('display', 'block');
			}
		});

		$(".box-title").click(function() {
			var icon = $(this).find('i').eq(0);
			if($(this).hasClass('layui-form-checked') && icon.hasClass('layui-icon-ok')) {
				$(this).removeClass('layui-form-checked');

				var ul = $(this).parent().find('ul').eq(0);
				if(ul) {
					ul.find('li').find('.box-title').removeClass('layui-form-checked');
				}
				delchecked($(this).parent());
			} else {
				$(this).addClass('layui-form-checked');
				icon.removeClass('layui-icon-add-1')
				icon.addClass('layui-icon-ok');

				var ul = $(this).parent().find('ul');
				$.each(ul, function(index, obj) {
					$(obj).find('li').find('.box-title').addClass('layui-form-checked');
					$(obj).find('li').find('.box-title').find('i').removeClass('layui-icon-add-1').addClass('layui-icon-ok');
				});
				checkedAll($(this).parent());
			}
		});
	});
	
	form.verify({
		name: function(value, item) {
			if(value.length > 16) {
				return "角色名称长度不能大于16位";
			}
		},
		code: function(value, item) {
			if(value.length > 32) {
				return "角色编码长度不能大于32位";
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
							msg = "该角色编码已被使用";
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
		
		var boxs = $("#LAY-auth-tree-index").find('.box-title');
		var sysResourceIds = "";
		$.each(boxs, function(index, obj) {
			var icon = $(obj).find('i').eq(0);
			if($(obj).hasClass('layui-form-checked') && icon.hasClass('layui-icon-ok')) {
				if(index != 0 && sysResourceIds.length) {
					sysResourceIds += ",";
				}
				sysResourceIds += $(obj).attr('lay-filter');
			}
		});
		data.field['sysResourceIds'] = sysResourceIds;
		console.log(data.field);
		
		$.ajax({
			url: '/sysRole',
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
		
		return false;
	});
});

function authtree(tree, menu) {
	$.each(tree, function(index, obj) {
		var checked = "";
		var icon = "";
		if(obj.list.length) {
			var i = 0;
			$.each(obj.list, function(index, item) {
				if(item.checked) {
					i++;
				}
			});

			if(i == 0) {
				icon = "";
			} else if(i < obj.list.length) {
				icon = "layui-icon-add-1";
			} else {
				icon = "layui-icon-ok";
			}
		}

		if(obj.checked) {
			icon = "layui-icon-ok";
		}

		if(obj.checked || icon.length) {
			checked = "layui-form-checked";
		}

		if(obj.list.length) {
			menu += "<li>";
			menu += "<i class='fa fa-chevron-down parent'></i>";
			menu += "<div class='layui-unselect layui-form-checkbox box-title " + checked + "' lay-skin='primary' lay-filter='" + obj.id + "'><span>" + obj.name + "</span><i class='layui-icon " + icon + "'></i></div>";
			menu += "<ul lay-data='children' style='margin-left: 25px;'>";
			menu = authtree(obj.list, menu);
			menu += "</ul>";
		} else {
			menu += "<li style='margin-left: 20px;'>";
			menu += "<div class='layui-unselect layui-form-checkbox box-title " + checked + "' lay-skin='primary' lay-filter='" + obj.id + "'><span>" + obj.name + "</span><i class='layui-icon " + icon + "'></i></div>";
		}
		menu += "</li>";
	});

	return menu;
}

function checkedAll(li) {
	var boxchecked = true;
	$.each(li.siblings(), function(index, item) {
		var box = $(item).find('.box-title');
		if(!box.hasClass('layui-form-checked') || !box.find('i').eq(0).hasClass('layui-icon-ok')) {
			boxchecked = false;
		}
	});
	
	if(boxchecked) {
		var li_box = li.find('.box-title');
		if(!li_box.hasClass('layui-form-checked') || !li_box.find('i').eq(0).hasClass('layui-icon-ok')) {
			boxchecked = false;
		}
		
		if(boxchecked) {
			li.parent().prev().find('i').removeClass('layui-icon-add-1').addClass('layui-icon-ok');
		} else {
			li.parent().prev().find('i').removeClass('layui-icon-ok').addClass('layui-icon-add-1');
		}
	} else {
		li.parent().prev().find('i').removeClass('layui-icon-ok').addClass('layui-icon-add-1');
	}
	li.parent().prev().addClass('layui-form-checked');
	
	if(li.parent().attr('lay-data') == 'children') {
		checkedAll(li.parent().parent());
	}
}

function delchecked(li) {
	var boxchecked = false;
	$.each(li.siblings(), function(index, item) {
		var box = $(item).find('.box-title');
		if(box.hasClass('layui-form-checked') && box.find('i').eq(0).hasClass('layui-icon-ok')) {
			boxchecked = true;
		}
	});

	if(!boxchecked) {
		$.each(li.siblings(), function(index, item) {
			var box = $(item).find('.box-title');
			if(box.hasClass('layui-form-checked')) {
				boxchecked = true;
			}
		});
		
		if(li.find('.box-title').hasClass('layui-form-checked')) {
			boxchecked = true;
		}
		
		if(!boxchecked) {
			li.parent().prev().removeClass('layui-form-checked');
		} else {
			li.parent().prev().find('i').removeClass('layui-icon-ok').addClass('layui-icon-add-1');
			li.parent().prev().addClass('layui-form-checked');
		}
	} else {
		li.parent().prev().find('i').removeClass('layui-icon-ok').addClass('layui-icon-add-1');
		li.parent().prev().addClass('layui-form-checked');
	}

	if(li.parent().attr('lay-data') == 'children') {
		delchecked(li.parent().parent());
	}
}