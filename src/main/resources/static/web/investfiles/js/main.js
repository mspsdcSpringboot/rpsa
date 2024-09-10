(function($){
    $(document).ready(function(){

        /*----------------------------------------------------*/
        /*	Sticky Header
        /*----------------------------------------------------*/
        (function() {
            $('#logo-bar').scrollToFixed(); // Fixed Navigation Bar
         })();

        /*----------------------------------------------------*/
        /*	Same Height Div's
        /*----------------------------------------------------*/
        if(jQuery.isFunction(jQuery.fn.matchHeight)){
            $('.same-height').matchHeight();
        }

        /*----------------------------------------------------*/
        /*	Fraction Slider
        /*----------------------------------------------------*/
        if(jQuery.isFunction(jQuery.fn.fractionSlider)){
            $(window).load(function(){
                $('.slider').fractionSlider({
                    'fullWidth': 			true,
                    'controls': 			true,
                    'responsive': 			true,
                    'dimensions': 			"1920,450",
                    'timeout' :             5000,
                    'increase': 			true,
                    'pauseOnHover': 		true,
                    'slideEndAnimation': 	false,
                    'autoChange':           true
                });
            });
        }
        /*----------------------------------------------------*/
       
        /*----------------------------------------------------*/
        /*	Owl Carousel
        /*----------------------------------------------------*/
        if(jQuery.isFunction(jQuery.fn.owlCarousel)){

            // Recent Work Slider
            $("#recent-work-slider").owlCarousel({
                navigation : true,
                pagination : false,
                items : 5,
                itemsDesktop:[1199,4],
                itemsTablet : [768, 3],
                itemsDesktopSmall : [992, 3],
                itemsMobile : [480,1],
                navigationText : ["",""]
            });

            // Post News Slider
            $("#post-slider").owlCarousel({
                navigation : true,
                pagination : false,
                items : 4,
                itemsDesktop:[1199,3],
                itemsDesktopSmall:[980,2],
                itemsMobile : [479,1],
                navigationText : ["",""]
            });
        }

        // ============================
        //  = Scroll event function =
        //  ===========================
        var goScrolling = function(elem) {
            var docViewTop = $(window).scrollTop();
            var docViewBottom = docViewTop + $(window).height();
            var elemTop = elem.offset().top;
            var elemBottom = elemTop + elem.height();
            return ((elemBottom <= docViewBottom) && (elemTop >= docViewTop));
        };

       
        /*----------------------------------------------------*/
        /*	Accordians & Toggles
         /*----------------------------------------------------*/

        $('.panel-group').on('shown.bs.collapse', function (e) {
            $(e.target).parent().addClass('active_acc');
        });
        $('.panel-group').on('hidden.bs.collapse', function (e) {
            $(e.target).parent().removeClass('active_acc');
        });

        /*----------------------------------------------------*/
        /*	Popover
        /*----------------------------------------------------*/
        $('[data-toggle="popover"]').popover()
        /* ------------------ End Document ------------------ */

        $("body").tooltip({
            selector: '[data-toggle="tooltip"]'
        });
    });
})(this.jQuery);

$(document).ready(function() {

    /*=================
     *	Contact Form
     * #contact
     ===================*/

    try{
        jQuery('#contact').validate({
            submitHandler: function(form) {
                jQuery('#contact .message').hide();
                var ajaxurl = 'contact.php';
                var data = {
                    action: 'contact_us',
                    datas: jQuery(form).serialize()
                };

                jQuery.ajax({
                    type: 'POST',
                    url: ajaxurl,
                    data: data,
                    success: function(response){
                        jQuery('#contact .message').text(response.error).css({'display' : 'inline-block'});
                    },
                    dataType: 'json'
                });
                return false;
            },
            rules: {
                c_name: {
                    required: true,
                    minlength: 3
                },
                c_mail: {
                    required: true,
                    email: true
                },
                c_subject: {
                    required: true,
                    minlength: 6
                },
                c_message:{
                    required: true,
                    minlength: 20
                }
            }
        });
    }catch(e){

    }

    /*============
     BUTTON UP
     * ===========*/
    var btnUp = $('<div/>', {'class':'btntoTop'});
    btnUp.appendTo('body');
    $(document)
        .on('click', '.btntoTop', function() {
            $('html, body').animate({
                scrollTop: 0
            }, 700);
        });

    $(window)
        .on('scroll', function() {
            if ($(this).scrollTop() > 200)
                $('.btntoTop').addClass('active');
            else
                $('.btntoTop').removeClass('active');
        });
});


/**
 * jQuery Plugin to obtain touch gestures from iPhone, iPod Touch, iPad, and Android mobile phones
 * Common usage: wipe images (left and right to show the previous or next image)
 *
 * @author Andreas Waltl, netCU Internetagentur (http://www.netcu.de)
 */
(function($){$.fn.touchwipe=function(settings){var config={min_move_x:20,min_move_y:20,wipeLeft:function(){},wipeRight:function(){},wipeUp:function(){},wipeDown:function(){},preventDefaultEvents:true};if(settings)$.extend(config,settings);this.each(function(){var startX;var startY;var isMoving=false;function cancelTouch(){this.removeEventListener('touchmove',onTouchMove);startX=null;isMoving=false}function onTouchMove(e){if(config.preventDefaultEvents){e.preventDefault()}if(isMoving){var x=e.touches[0].pageX;var y=e.touches[0].pageY;var dx=startX-x;var dy=startY-y;if(Math.abs(dx)>=config.min_move_x){cancelTouch();if(dx>0){config.wipeLeft()}else{config.wipeRight()}}else if(Math.abs(dy)>=config.min_move_y){cancelTouch();if(dy>0){config.wipeDown()}else{config.wipeUp()}}}}function onTouchStart(e){if(e.touches.length==1){startX=e.touches[0].pageX;startY=e.touches[0].pageY;isMoving=true;this.addEventListener('touchmove',onTouchMove,false)}}if('ontouchstart'in document.documentElement){this.addEventListener('touchstart',onTouchStart,false)}});return this}})(jQuery);



