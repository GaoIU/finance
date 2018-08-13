$(document).ready(function() {
    $(document).on('click', '.new_tab',
    function(ev) {

        var title = $(this).attr('data-title') ? $(this).attr('data-title') : $(this).text();
        var href = $(this).attr('href');
        var icon = $(this).attr('data-icon') ? $(this).attr('data-icon') : $(this).find('i').attr('data-icon');

        if (parent && parent.Tab) {
            parent.Tab.tabAdd({
                title: title,
                href: href,
                icon: icon
            });
        } else {
            Tab.tabAdd({
                title: title,
                href: href,
                icon: icon
            });
        }
        if (ev && ev.preventDefault) ev.preventDefault();
        else window.event.returnValue = false;
        return false;
    }).on('click', '.javascript',
    function(ev) {
        var callback;

        if (callback = $(this).attr('rel')) {
            if (window[callback]) {
                window[callback].call(this);
            }
        }
        if (ev && ev.preventDefault) ev.preventDefault();
        else window.event.returnValue = false;
        return false;
    });

    $('.tooltip').hover(function() {
        var text = $(this).attr('data-tip-text');
        var type = $(this).attr('data-tip-type') ? $(this).attr('data-tip-type') : 2;
        var bg = $(this).attr('data-tip-bg') ? $(this).attr('data-tip-bg') : '#393D49';
        if (text) {
            layer.tips(text, $(this), {
                tips: [type, bg],
                time: 0
            });
        }
    },
    function() {
        layer.close(layer.tips());
    });
});