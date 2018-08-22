layui.use(['form', 'element', 'layer', 'laydate'], function() {
	layForm = layui.form;
	laydate = layui.laydate;
	
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
});

$(document).ready(function() {
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
			queryList.created();
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
			queryList.created();
		}
	}
});

var queryList = new Vue({
	el: '#queryList',
	data: {
		items: []
	},
	created(): return {
		function() {
			var URL = "/sysUser?current=" + pageShow.current + "&size=" + pageShow.size + "&" + $('#searchForm').serialize();
			console.log(URL);
			axios.get(URL).then(function(res) {
				var json = res.data.data;
				pageShow.current = json.page.current;
				pageShow.size = json.page.size;
				pageShow.total = json.page.total;
				listSearch.condition = json.condition;
				queryList.items = json.page.list;
			});
		}
	}
});