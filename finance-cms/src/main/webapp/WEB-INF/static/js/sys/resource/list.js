layui.use(['form', 'element', 'layer', 'laydate'], function() {
	layForm = layui.form;
	laydate = layui.laydate;
	layer = layui.layer;
	
	var anim = Math.floor(Math.random() * 6 + 1);
	var color;
	switch(anim) {
		case 1:
			color = '#5EABE1';
			break;
		case 2:
			color = '#00A65A';
			break;
		case 3:
			color = '#FA6086';
			break;
		case 4:
			color = '#FF9A1E';
			break;
		case 5:
			color = '#FA2A00';
			break;
		case 6:
			color = 'molv';
			break;
	}
	
	$('.datepicker').each(function() {
		laydate.render({
			elem: this,
			type: 'date',
			trigger: 'click',
			max: 0,
			theme: color
		});
	});

	$('.datetimepicker').each(function() {
		laydate.render({
			elem: this,
			type: 'datetime',
			trigger: 'click',
			max: 0,
			theme: color
		});
	});
	
	$('#list_search_toggle').on('click', function() {
		var search = $('#list_search');
		if(search.hasClass('hidden')) {
			search.removeClass('hidden');
			$('#list_search_toggle').text('关闭搜索');
		} else {
			search.addClass('hidden');
			$('#list_search_toggle').text('展开搜索');
		}
	});
	
	$('#goPage').keyup(function() {
		var max_page = parseInt($('#goPage').attr('max'));
		var target_page = $('#goPage').val();
		if(!/\d{1,}/.test(target_page)) {
			target_page = 1;
		} else {
			target_page = parseInt(target_page);
		}
		if(target_page > max_page) target_page = max_page;
		$('#goPage').val(target_page);
	});
	
	$("table.list").tableresize({
		resizeTable: false
	});
	
	$('.create').on('click', function() {
		var index = layer.open({
			title: '添加后台资源',
			anim: anim,
			type: 2,
			// area: ['100%', '100%'],
			content: '/sysResource/gotoInfo',
			success: function(index, layero) {
				setTimeout(function() {
					layer.tips('点击此处返回后台资源列表', '.layui-layer-setwin .layui-layer-close', {
						tips: 3
					});
				}, 500);
			}
		});
		window.sessionStorage.setItem("index", index);
		$(window).on('resize', function() {
			alert(123);
			layer.full(window.sessionStorage.getItem("index"));
		});
		/*layer.full(index);
		window.sessionStorage.setItem("index", index);
		$(window).on('resize', function() {
			layer.full(window.sessionStorage.getItem("index"));
		});*/
	});
});

var pageShow = new Vue({
	el: '#pageShow',
	data: {
		current: '1',
		size: '15',
		total: '0',
		pagenumber: '1',
		pagesizes: [{
			name: '10 条/页',
			value: '10'
		}, {
			name: '15 条/页',
			value: '15'
		}, {
			name: '20 条/页',
			value: '20'
		}, {
			name: '25 条/页',
			value: '25'
		}]
	},
	created: function() {
		this.size = this.pagesizes[1].value;
		if(this.total <= this.size) {
			this.pagenumber = 1;
		} else if((this.total % this.size) == 0) {
			this.pagenumber = this.total / this.size;
		} else {
			this.pagenumber = this.total / this.size + 1;
		}
	},
	methods: {
		pageRefresh() {
			queryList.find();
		},
		gotoPage(current, size) {
			this.size = size;
			this.current = current;
			queryList.find();
		}
	}
});

var listSearch = new Vue({
	el: '#list_search',
	data: {
		condition: {}
	},
	methods: {
		search() {
			queryList.find();
		}
	}
});

var queryList = new Vue({
	el: '#queryList',
	data: {
		items: []
	},
	created: function() {
		this.find();
	},
	methods: {
		find() {
			var URL = "/sysResource?current=" + pageShow.current + "&size=" + pageShow.size + "&" + $('#searchForm').serialize();
			axios.get(URL).then(function(res) {
				var json = res.data.data;
				pageShow.current = json.page.current;
				pageShow.size = json.page.size;
				pageShow.total = json.page.total;
				listSearch.condition = json.condition;
				queryList.items = json.page.list;
			});
		},
		oneChoose(e) {
			var divbox = e.currentTarget;
			var checkbox = $(divbox).prev();
	    	if($(divbox).hasClass('layui-form-checked')) {
	    		$(divbox).removeClass('layui-form-checked');
	    		$(checkbox).attr('checked', false);
	    		$('.allChoose').removeClass('layui-form-checked');
	    	} else {
	    		$(divbox).addClass('layui-form-checked');
	    		$(checkbox).attr('checked', true);
	    		var ischecked = true;
	    		$.each($('.oneChoose'), function(index, obj) {
	    			if(!$(obj).hasClass('layui-form-checked')) {
	    				ischecked = false;
	    			}
	    		});
	    		if(ischecked) {
	    			$('.allChoose').addClass('layui-form-checked');
	    		} else {
	    			$('.allChoose').removeClass('layui-form-checked');
	    		}
	    	}
		},
		allChoose(e) {
			var divbox = e.currentTarget;
			var ischecked = $(divbox).hasClass('layui-form-checked');
			
			if(ischecked) {
				$(divbox).removeClass('layui-form-checked');
			} else {
				$(divbox).addClass('layui-form-checked');
			}
			$.each($('.oneChoose'), function(index, obj) {
				var inp = $(obj).prev();
				if(ischecked) {
					$(obj).removeClass('layui-form-checked');
					$(inp).attr('checked', false);
				} else {
					$(obj).addClass('layui-form-checked');
					$(inp).attr('checked', true);
				}
			});
		}
	}
});