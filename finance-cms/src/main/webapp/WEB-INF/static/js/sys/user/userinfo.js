layui.use([ 'form', 'layer', 'upload' ], function() {
	form = layui.form;
	layer = layui.layer;
	upload = layui.upload;
	
	upload.render({
		elem: '.userFaceBtn',
		url: '/sysUser/upload',
		method: 'POST',
		data: {
			"sysUserId": "${SYS_USER_SESSION_KEY.id}"
		},
		accept: 'images',
		acceptMime: 'image/*',
		field: 'avatar',
		size: 5120,
		before: function() {
			layer.load();
		},
		done: function(res, index, upload) {
			layer.closeAll('loading');
			layer.msg('上传成功', {icon: 6});
			$('#userFace').attr('src', res.data);
		},
		error: function(index, upload) {
			layer.closeAll('loading');
			layer.msg('上传失败', {icon: 5});
		}
	});
});