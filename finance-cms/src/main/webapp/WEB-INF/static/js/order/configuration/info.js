layui.use(['form', 'layer', 'upload'], function() {
	form = layui.form;
	layer = parent.layer === undefined ? layui.layer : top.layer;
	upload = layui.upload;
	
	var type;
	if($('.id').val()) {
		type = 'PUT';
	} else {
		type = 'POST';
	}
	upload.render({
		elem: '.userFaceBtn',
		url: '/operateConfig',
		method: type,
		auto: false,
		bindAction: '#create',
		accept: 'images',
		acceptMime: 'image/*',
		field: 'file',
		size: 1024,
		headers: {
			'contentType': 'multipart/form-data'
		},
		choose: function(obj) {
			var files = obj.pushFile();
			obj.preview(function(index, file, result) {
				$('#userFace').attr('src', result);
			});
		},
		before: function(obj) {
			this.data = $('#operateConfig').serializeObject();
			layer.load();
		},
		done: function(res, index, upload) {
			layer.closeAll('loading');
			layer.msg(res.msg, {icon: 6});
			$('#userFace').attr('src', res.data);
			setTimeout(function() {
				layer.closeAll("iframe");
				parent.location.reload();
			}, 2000);
		},
		error: function(index, upload) {
			layer.closeAll('loading');
			layer.msg('上传失败', {icon: 5});
		}
	});
	
	form.verify({
		name: function(value, item) {
			if(value.length > 16) {
				return "用户账号长度不能大于16位";
			}
		}
	});
});