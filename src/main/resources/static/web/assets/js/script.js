$(document).ready(function() {
//    $('a[target="_blank"]').click(function (event) {
//        event.preventDefault();
//        var yesno = confirm("Are you sure you want to go to an external source?");
//        if (yesno) window.open($(this).attr('href'));
//    });
    $("#lightSlider").lightSlider({
        item: 5,
        autoWidth: false,
        slideMove: 1, // slidemove will be 1 if loop is true
        slideMargin: 230,
 
        addClass: '',
        mode: "slide",
        useCSS: true,
        cssEasing: 'ease', //'cubic-bezier(0.25, 0, 0.25, 1)',//
        easing: 'linear', //'for jquery animation',////
 
        speed: 400, //ms'
        auto: true,
        loop: true,
        slideEndAnimation: true,
        pause: 5000,
 
        keyPress: false,
        controls: true,
        prevHtml: '',
        nextHtml: '',
 
        rtl:false,
        adaptiveHeight:false,
        
 
        vertical:false,
        verticalHeight:200,
        vThumbWidth:60,
 
        thumbItem:10,
        pager: true,
        gallery: false,
        galleryMargin: 5,
        thumbMargin: 5,
        currentPagerPosition: 'middle',
 
        enableTouch:true,
        enableDrag:true,
        freeMove:true,
        swipeThreshold: 40,
 
        responsive : [],
 
        onBeforeStart: function (el) {},
        onSliderLoad: function (el) {},
        onBeforeSlide: function (el) {},
        onAfterSlide: function (el) {},
        onBeforeNextSlide: function (el) {},
        onBeforePrevSlide: function (el) {}
    });
  });

  
function font(s) {
    var style = window.getComputedStyle(document.getElementsByTagName("body")[0], null).getPropertyValue('font-size');
    var fontSize = parseFloat(style);
    if (s == 1 && fontSize < 20) {

        document.getElementsByTagName("body")[0].style.fontSize = (fontSize + 1) + "px";
        //alert(fontSize);
    } else if (s == 2 && fontSize > 8) {
        document.getElementsByTagName("body")[0].style.fontSize = (fontSize - 1) + "px";
    } else if (s == 0) {
        document.getElementsByTagName("body")[0].style.fontSize = "16px";
    }
}
