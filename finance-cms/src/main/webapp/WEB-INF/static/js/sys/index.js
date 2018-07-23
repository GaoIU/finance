var winWidth = $(window).width();
var heiHeght = $(window).height();

var layer;
$(function() {
	layui.use(['layer'], function() {
		layer = layui.layer;
		layer.config({
			zIndex: 10000
		});
	})
})

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
})

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
})

$('#gloMenu').on('click', 'a', function() {
	//if(!$(this).hasClass('isNav')) return false ;
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
	})
	return false;
})

$('#gloTop').find('.menuBar').click(function() {
	if($('#gloBox').hasClass('menu_close')) {
		$('#gloBox').removeClass('menu_close');
	} else {
		$('#gloBox').addClass('menu_close');
	}
})

$('.skin-down').hover(function() {
	$(this).find('.skin-show').stop(true, true).slideDown(300);
}, function() {
	$(this).find('.skin-show').stop(true, true).slideUp(300);
})

function change_skin() {
	var skin = $(this).attr('data-skin');
	var url = "/run/tool/set_skin.html";
	HKUC.ajax_request.call(this, url, {
		skin: skin
	}, {
		'success': function(msg, data) {
			$('#gloBox').removeClass().addClass(skin);
		},
		'error': function(msg, data) {
			layer.closeAll();
			layer.msg(msg)
		}
	});
}

function simple_clear() {
	var url = $(this).attr('href');
	HKUC.ajax_request.call(this, url, null, {
		'success': function(msg, data) {
			layer.closeAll();
			layer.msg(msg)
		},
		'error': function(msg, data) {
			layer.closeAll();
			layer.msg(msg)
		}
	});
}

function switch_trace() {
	var url = $(this).attr('href');
	HKUC.ajax_request.call(this, url, null, {
		'success': function(msg, data) {
			layer.closeAll();
			layer.msg(msg, {
				time: 1000,
				end: function() {
					window.location.reload();
				}
			});
		},
		'error': function(msg, data) {
			layer.closeAll();
			layer.msg(msg)
		}
	});
}

function get_site_size() {
	var url = $(this).attr('href');
	layer.msg('查询中请稍后...', {
		time: 30 * 60 * 1000,
		shade: [0.01, '#393D49']
	});
	HKUC.ajax_request.call(this, url, null, {
		'success': function(msg, data) {
			layer.closeAll();
			$('#showSiteSize').html(msg)
		},
		'error': function(msg, data) {
			layer.closeAll();
			layer.msg(msg)
		}
	});
}

//resize
$(window).resize(function() {
	winWidth = $(window).width();
	heiHeght = $(window).height();
	$('#gloRght').height(heiHeght - 51);
	$('#gloLeft,#gloSLeft').css('height', (heiHeght - 51) + 'px')
	$('.layui-tab-content').height(heiHeght - 51 - 40);

}).trigger('resize')

//Tab
$(window).resize(function() {
	if(typeof(Tab) != 'undefined') Tab.resize();
})

$('.tab-prev').unbind('click').bind('click', function() {
	var left = $('.layui-tab-title').position().left;
	left = left + 117 < 0 ? left + 117 : 0;
	$('.layui-tab-title').stop(true).animate({
		left: left
	}, 500);
})

$('.tab-next').unbind('click').bind('click', function() {
	var left = $('.layui-tab-title').position().left;
	var boxWid = $('.layui-tab-title').width();
	var liWid = 0;
	$('.layui-tab-title').children('span').remove().end().find('li').each(function() {
		liWid += $(this).outerWidth();
	})
	left = left - 117 > -(liWid - boxWid) ? left - 117 : -(liWid - boxWid);
	if(left > 0) left = 0;
	$('.layui-tab-title').stop(true).animate({
		left: left
	}, 500);
})

function full_screen() {
	var docElm = document.documentElement;
	//W3C
	if(docElm.requestFullscreen) {
		docElm.requestFullscreen();
	}
	//FireFox
	else if(docElm.mozRequestFullScreen) {
		docElm.mozRequestFullScreen();
	}
	//Chrome等
	else if(docElm.webkitRequestFullScreen) {
		docElm.webkitRequestFullScreen();
	}
	//IE11
	else if(docElm.msRequestFullscreen) {
		docElm.msRequestFullscreen();
	}
}