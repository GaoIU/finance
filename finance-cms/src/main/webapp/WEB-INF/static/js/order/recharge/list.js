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
	},
	methods: {
		pageRefresh() {
			queryList.find();
		},
		gotoPage(current, size) {
			this.size = size;
			this.current = current;
			queryList.find();
		},
		setPage(page) {
			this.current = page.current;
			this.size = page.size;
			this.total = page.total;
			if(page.total <= page.size) {
				this.pagenumber = 1;
			} else if((page.total % page.size) == 0) {
				this.pagenumber = page.total / page.size;
			} else {
				this.pagenumber = Math.floor(page.total / page.size) + 1;
			}
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
		},
		exportxls() {
			var URL = "/rechargeOrder?current=" + pageShow.current + "&size=" + pageShow.size + "&" + $('#searchForm').serialize();
			$.get(URL, function(res) {
				var json = res.data;
				if (json.page.list.length) {
					var searchForm = $('#searchForm').serializeObject();
					searchForm['current'] = pageShow.current;
					searchForm['size'] = pageShow.size;
					searchForm['fileName'] = "用户充值订单";
					searchForm['keys'] = "userName, orderNo, amount, type, status, createTime, sysUserName, auditNote, auditTime";
					searchForm['columNames'] = "用户账号, 订单号, 订单金额, 充值方式, 充值状态, 充值时间, 审核人, 审核备注, 审核时间";
					searchForm['replace'] = "type:1-支付宝, 2-微信, 3-银行卡; status:0-审核中, 1-审核通过, 2-审核拒绝";
					
					listSearch.download('/rechargeOrder/export', searchForm);
				} else {
					layer.msg('导出数据不能为空', {
						icon: 5,
						anim: 6
					});
				}
			});
		},
		download(url, data) {
			var tempForm = document.createElement("form");
			tempForm.id = 'download';
			tempForm.method = 'POST';
			tempForm.action = url;
			tempForm.target = '下载';
			tempForm.innerHTML = "";
			for(var i in data) {
				tempForm.innerHTML += "<input type='hidden' name='" + i + "' value='" + data[i] + "' />";
			}
			
			document.body.appendChild(tempForm);
			tempForm.dispatchEvent(new Event("onsubmit"));
			tempForm.submit();
			document.body.removeChild(tempForm);
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
			var URL = "/rechargeOrder?current=" + pageShow.current + "&size=" + pageShow.size + "&" + $('#searchForm').serialize();
			$.get(URL, function(res) {
				var json = res.data;
				pageShow.setPage(json.page);
				listSearch.condition = json.condition;
				queryList.items = json.page.list;
			});
		},
		usable(id, status) {
			var anim = Math.floor(Math.random() * 6 + 1);
			var msg;
			var val;
			if (status == 1) {
				msg = "是否确认通过？";
				val = "审核通过";
			} else {
				msg = "是否确认拒绝？";
				val = "审核拒绝";
			}
			
			layer.confirm(msg, {
				icon: 3,
				anim: anim,
				title: '提示'
			}, function(index) {
				layer.close(index);
				
				layer.prompt({
					anim: anim,
					formType: 2,
					value: val,
					maxlength: 120,
					title: '请输入审核备注'
				}, function(value, index, elem) {
					var param = {"id": id, "status": status, "auditNote": value};
					$.ajax({
						url: '/rechargeOrder',
						type: 'PUT',
						data: JSON.stringify(param),
						dataType: 'JSON',
						contentType: 'application/json;charset=UTF-8',
						async: true,
						success: function(res) {
							layer.closeAll();
							top.layer.msg(res.msg, {
								icon: 6,
								time: 1500
							});
							setTimeout(function() {
								if(res.code == 200) {
									queryList.find();
								}
							}, 2000);
						},
						error: function() {
							layer.closeAll();
							layer.msg('操作失败', {
								icon: 5,
								anim: 6
							});
						}
					});
				});
			});
		}
	}
});