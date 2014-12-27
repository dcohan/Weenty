(function ($) {
     $(document).ready(function () {
        $('#cssmenu > ul').prepend('<li class=\"mobile\"><a href=\"#\"><span>Menu <i>&#9776;</i></span></a></li>');
        var menu_is_mobile = $('#cssmenu > ul > li.mobile').css('display') !== 'none';
        $('#cssmenu > ul > li').click(function (e) {
            if (!menu_is_mobile) {
                return false;
            }
            openMenu($(this), e);
        });

        $('#cssmenu > ul > li').hover(function (e) {
            if (menu_is_mobile) {
                return false;
            }
            openMenu($(this), e);
        });
    });
})(jQuery);

function openMenu($elem, e) {
    $('#cssmenu li').removeClass('active');
    $elem.addClass('active');

    var checkElement = $elem.find('ul');

    if ((checkElement.is('ul')) && (checkElement.is(':visible'))) {
        // Must be closed
        $elem.closest('li').removeClass('active');
        checkElement.slideUp('normal');
    } else {
        // Must be opened
        $('#cssmenu ul ul:visible').slideUp('normal');
        checkElement.slideDown('normal');
    }

    if ($elem.hasClass('mobile')) {
        e.preventDefault();
        $('#cssmenu').toggleClass('expand');
    }

    return $elem.find('ul').children().length == 0;
}