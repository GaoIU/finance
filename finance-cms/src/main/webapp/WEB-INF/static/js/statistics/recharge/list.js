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
	
	$("table.list").tableresize({
		resizeTable: false
	});
});

var listSearch = new Vue({
	el: '#list_search',
	methods: {
		search() {
			queryList.find();
		}
	}
});

var queryList = new Vue({
	el: '#queryList',
	data: {
		item: {}
	},
	created: function() {
		this.find();
	},
	methods: {
		find() {
			var URL = "/statistics/recharge?" + $('#searchForm').serialize();
			$.get(URL, function(res) {
				queryList.item = res.data.item;
				queryList.pie();
				queryList.bar(res.data.list);
			});
		},
		pie() {
			echarts.init(document.getElementById('pie_chart')).setOption({
				title: {
					text: '充值统计饼状图',
					x: 'center'
				},
				color: ['#05A3E6', '#01D10C', '#A71E32'],
				tooltip: {
					trigger: 'item',
					formatter: "{a} <br/>{b} : {c} ({d}%)"
				},
				legend: {
					type: 'scroll',
			        orient: 'vertical',
			        right: 10,
			        top: 20,
			        bottom: 20,
			        data: []
				},
				series: {
					name: '类型',
					type: 'pie',
			        radius: '55%',
			        center: ['40%', '50%'],
			        data: [{
			        	name: '支付宝',
			        	value: queryList.item.alipay
			        }, {
			        	name: '微信',
			        	value: queryList.item.wechat
			        }, {
			        	name: '银行卡',
			        	value: queryList.item.bankcard
			        }],
			        itemStyle: {
			            emphasis: {
			                shadowBlur: 10,
			                shadowOffsetX: 0,
			                shadowColor: 'rgba(0, 0, 0, 0.5)'
			            }
			        }
				}
			});
		},
		bar(list) {
			var keys = [];
			var vals = [];
			$.each(list, function(index, obj) {
				$.each(obj, function(key, val) {
					keys.push(key);
					vals.push(val);
				});
			});
			
			echarts.init(document.getElementById('bar_chart')).setOption({
				title: {
					text: '最近7日充值统计图',
					x: 'center'
				},
				color: ['#3398DB'],
				tooltip: {
			        trigger: 'axis',
			        axisPointer: {
			            type: 'shadow'
			        }
			    },
			    grid: {
			        left: '3%',
			        right: '4%',
			        bottom: '3%',
			        containLabel: true
			    },
			    xAxis: [{
			    	type: 'category',
			    	data: keys,
			    	axisTick: {
			    		alignWithLabel: true
			    	}
			    }],
			    yAxis: [{
			    	type: 'value'
			    }],
			    series: [{
			    	name: '充值金额',
			    	type: 'bar',
			    	barWidth: '60%',
			    	data: vals
			    }, {
			    	name: '充值金额',
			    	type: 'line',
			    	color: ['#C33834'],
			    	data: vals
			    }]
			});
		}
	}
});