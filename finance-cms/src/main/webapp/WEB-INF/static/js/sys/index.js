var Tab;
layui.use(['element', 'tab'], function() {
	var element = layui.element;

	layer.config({
		zIndex: 10000
	});

	Tab = layui.tab({
		elem: '.layui-tab',
		maxSetting: {
			max: 20,
			tipMsg: '最多只能开启20个'
		},
		contextMenu: true,
		autoRefresh: true
	});
})

$('#gloMenu>li').each(function() {
	var childLen = $(this).find('.navC').find('li').length;
	if(childLen) {
		var html = $(this).find('.navT').find('a').html();
		$(this).find('.navT').html('<span>' + html + '</span>');
	}
});

$('#gloMenu').on('click', '.navT', function() {
	var parent = $(this).closest('li');
	var index = parent.index();
	if(parent.find('.navC').find('li').length) {
		if(parent.hasClass('open')) {
			parent.find('.navC').stop(true).slideUp(300, function() {
				parent.removeClass('open')
			});
		} else {
			var openLi = $('#gloMenu').find('li.open');
			openLi.removeClass('open').find('.navC').stop(true).slideUp(300);
			parent.addClass('open').find('.navC').stop(true).slideDown(300);
		}

	}
});

$('#gloMenu').on('click', 'a', function() {
	var href = $(this).attr('href');
	var title = $(this).attr('data-title') || $(this).attr('title');
	if(!title) title = $(this).text();
	var icon = $(this).attr('data-icon') || $(this).find('i.fa').attr('data-icon');

	$('#gloMenu').find('a.current').removeClass('current');
	$(this).addClass('current');

	Tab.tabAdd({
		title: title,
		href: href,
		icon: icon
	});
	return false;
});

$('#gloTop').find('.menuBar').click(function() {
	if($('#gloBox').hasClass('menu_close')) {
		$('#gloBox').removeClass('menu_close');
		$('.navT').find('a').each(function() {
			if($(this).hasClass('tooltip')) {
				$(this).css('display', 'none');
			} else {
				$(this).css('display', 'block');
			}
		});
	} else {
		$('#gloBox').addClass('menu_close');
		$('.navT').find('a').each(function() {
			if($(this).hasClass('tooltip')) {
				$(this).css('display', 'block');
			} else {
				$(this).css('display', 'none');
			}
		});
	}
});

$('#left_bar').find('.site-tree-mobile').click(function() {
	if($('#gloBox').hasClass('menu_close')) {
		$('#_icon').html('&#xe603;');
		$('#gloBox').removeClass('menu_close');
	} else {
		$('#_icon').html('&#xe602;');
		$('#gloBox').addClass('menu_close');
	}
});

$(window).resize(function() {
	initSize();
});

function initSize() {
	var screenWidth = $(window).width(); // 窗口宽度
	if(screenWidth < 992) {
		$('#gloBox').addClass('menu_close');
	}
}

$('.skin-down').hover(function() {
	$(this).find('.skin-show').stop(true, true).slideDown(300);
}, function() {
	$(this).find('.skin-show').stop(true, true).slideUp(300);
});

function change_skin() {
	var skin = $(this).attr('data-skin');
	var color = $(this).css('background-color');
	$('.tooltip').attr('data-tip-bg', color);
	$('.current').css('background-color', color);
	$('#gloBox').removeClass().addClass(skin);
}

// resize
$(window).resize(function() {
	winWidth = $(window).width();
	heiHeght = $(window).height();
	$('#gloRght').height(heiHeght - 51);
	$('#gloLeft,#gloSLeft').css('height', (heiHeght - 51) + 'px');
	$('.layui-tab-content').height(heiHeght - 51 - 40);

}).trigger('resize');

//Tab
$(window).resize(function() {
	if(typeof(Tab) != 'undefined') Tab.resize();
});

$('.tab-prev').unbind('click').bind('click', function() {
	var left = $('.layui-tab-title').position().left;
	left = left + 117 < 0 ? left + 117 : 0;
	$('.layui-tab-title').stop(true).animate({
		left: left
	}, 500);
});

$('.tab-next').unbind('click').bind('click', function() {
	var left = $('.layui-tab-title').position().left;
	var boxWid = $('.layui-tab-title').width();
	var liWid = 0;
	$('.layui-tab-title').children('span').remove().end().find('li').each(function() {
		liWid += $(this).outerWidth();
	});
	left = left - 117 > -(liWid - boxWid) ? left - 117 : -(liWid - boxWid);
	if(left > 0) left = 0;
	$('.layui-tab-title').stop(true).animate({
		left: left
	}, 500);
});

function full_screen() {
	var docElm = document.documentElement;
	// W3C
	if(docElm.requestFullscreen) {
		docElm.requestFullscreen();
	}
	//FireFox
	else if(docElm.mozRequestFullScreen) {
		docElm.mozRequestFullScreen();
	}
	// Chrome等
	else if(docElm.webkitRequestFullScreen) {
		docElm.webkitRequestFullScreen();
	}
	// IE11
	else if(docElm.msRequestFullscreen) {
		docElm.msRequestFullscreen();
	}
}

new Vue({
	el: 'gloMenu',
	data: {html: ''},
	created: function() {
		axios.post('/index').then(function(data) {
			var json = data.data.data;
			var menu = "";
			menu = getMenu(json, menu);
		});
	}
});

function getMenu(data, menu) {
	$.each(data, function(index, obj) {
		menu += "<li class='layui-nav-item '>";
		menu += "<div class='navT'>";
		var url = "javascript:;";
		if(obj.url != null) {
			url = obj.url;
		}
		menu += "<a href='" + url + "' style='display: block;' kit-target data-id='" + obj.id + "'><i data-icon='" + obj.icon + "' class='fa " + obj.icon + " animated' style='color: rgb({rand(50,200)},{rand(50,200)},{rand(50,200)});'></i><cite>" + obj.name + "</cite></a>";
		menu += "<a href='" + url + "' style='display: none;' class='tooltip' data-tip-text='" + obj.name + "' data-tip-bg='#66AFE2' data-title='" + obj.name + "' data-icon=''><i data-icon='" + obj.icon + "' class='fa " + obj.icon + " animated' style='color: rgb({rand(50,200)},{rand(50,200)},{rand(50,200)});'></i><cite>" + obj.name + "</cite></a>";
		menu += "</div>";
		menu += "<div class='navC'>";
		menu += "<ul class='list'>";
		menu += menuChild(obj.childList, menu);
		menu += "</ul>";
		menu += "</div>";
		menu += "</li>";
	});
	
	return menu;
}

function menuChild(childList, menu) {
	if(childList == null || childList.length == 0) {
		return menu;
	}
	
	$.each(childList, function(index, obj) {
		if(obj.childList == null || obj.childList.length == 0) {
			menu += "<li class='b'>";
			var url = "javascript:;";
			if(obj.url != null) {
				url = obj.url;
			}
			menu += "<a href='" + url + "' data-url='" + url + "' data-icon='" + obj.icon + "' data-title='" + obj.name + "' kit-target data-id='" + obj.id + "'><i data-icon='" + obj.icon + "' class='fa fa-angle-right fa-lg animated'></i><cite>" + obj.name + "</cite></a>";
			menu += "</li>";
			menu += "<li class='s'>";
			menu += "<a href='" + url + "' class='tooltip' data-tip-text='" + obj.name + "' data-tip-bg='#66AFE2' data-title='" + obj.name + "' data-icon='" + obj.icon + "'><i class='fa " + obj.icon + "'></i></a>";
			menu += "</li>";
		} else {
			menu += getMenu(obj, menu);
		}
	});
	
	return menu;
}