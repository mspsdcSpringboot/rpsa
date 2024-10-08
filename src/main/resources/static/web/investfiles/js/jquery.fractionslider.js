/*
 * jQuery Fraction Slider v0.9.9.2
 * http://fractionslider.jacksbox.de
 *
 * Author: Mario Jäckle
 * eMail: support@jacksbox.de
 *
 * Copyright 2013, jacksbox.design
 * Free to use under the MIT license.
 * http://www.opensource.org/licenses/mit-license.php
 */
(function(e) {
    var t = null,
        n = {
            init: function(n) {
                var i = e.extend({
                    slideTransition: "none",
                    slideTransitionSpeed: 2e3,
                    slideEndAnimation: true,
                    position: "0,0",
                    transitionIn: "left",
                    transitionOut: "left",
                    fullWidth: false,
                    delay: 0,
                    timeout: 2e3,
                    speedIn: 2500,
                    speedOut: 1e3,
                    easeIn: "easeOutExpo",
                    easeOut: "easeOutCubic",
                    controls: true,
                    pager: false,
                    autoChange: false,
                    backgroundAnimation: false,
                    backgroundElement: null,
                    backgroundX: 500,
                    backgroundY: 500,
                    backgroundSpeed: 2500,
                    backgroundEase: "easeOutCubic",
                    responsive: false,
                    dimensions: ""
                }, n);
                return this.each(function() {
                    t = new r(this, i)
                })
            },
            pause: function() {
                t.pause()
            },
            resume: function() {
                t.resume()
            },
            stop: function() {
                t.stop()
            },
            start: function() {
                t.start()
            },
            startNextSlide: function() {
                t.startNextSlide()
            }
        };
    var r = function(t, n) {
        function v() {
            if (n.controls) {
                f.append('<a href="#" class="prev"></a><a href="#" class="next" ></a>');
                f.find(".next").bind("click", function() {
                    return C()
                });
                f.find(".prev").bind("click", function() {
                    return N()
                })
            }
            if (n.fullWidth) {
                f.css({
                    overflow: "visible"
                })
            } else {
                f.css({
                    overflow: "hidden"
                })
            }
            if (n.pager) {
                l = e('<div class="fs-pager-wrapper"></div>');
                f.append(l)
            }
            f.children(".slide").each(function(t) {
                var r = e(this);
                r.children().attr("rel", t).addClass("fs_obj");
                r.children("[data-fixed]").addClass("fs_fixed_obj");
                if (n.pager) {
                    var i = e('<a rel="' + t + '" href="#"></a>').bind("click", function() {
                        return T(this)
                    });
                    l.append(i)
                }
            });
            if (n.pager) {
                l = e(l).children("a")
            }
            if (n.responsive) {
                X()
            }
            if (f.find(".fs_loader").length > 0) {
                f.find(".fs_loader").remove()
            }
            m()
        }

        function m() {
            r.stop = false;
            r.pause = false;
            r.running = true;
            k("slide")
        }

        function g() {
            r.stop = false;
            r.pause = false;
            r.running = true;
            E()
        }

        function y() {
            r.stop = true;
            r.running = false;
            f.find(".slide").stop(true, true);
            f.find(".fs_obj").stop(true, true).removeClass("fs-animation");
            G(s)
        }

        function b() {
            r.pause = true;
            r.running = false;
            f.find(".fs-animation").finish()
        }

        function w() {
            r.stop = false;
            r.pause = false;
            r.running = true;
            if (r.finishedObjs < r.maxObjs) {
                k("obj")
            } else if (r.currentStep < r.maxStep) {
                k("step")
            } else {
                k("slide")
            }
        }

        function E() {
            r.lastSlide = r.currentSlide;
            r.currentSlide += 1;
            r.stop = false;
            r.pause = false;
            r.running = true;
            A()
        }

        function S() {
            r.lastSlide = r.currentSlide;
            r.currentSlide -= 1;
            r.stop = false;
            r.pause = false;
            r.running = true;
            A()
        }

        function x(e) {
            r.lastSlide = r.currentSlide;
            r.currentSlide = e;
            r.stop = false;
            r.pause = false;
            r.running = true;
            A()
        }

        function T(t) {
            var n = e(t).attr("rel");
            if (n != r.currentSlide) {
                y();
                x(n)
            }
            return false
        }

        function N() {
            y();
            S();
            return false
        }

        function C() {
            y();
            E();
            return false
        }

        function k(e) {
            if (!r.pause && !r.stop && r.running) {
                switch (e) {
                    case "slide":
                        L();
                        break;
                    case "step":
                        H();
                        break;
                    case "obj":
                        j();
                        break
                }
            }
        }

        function L() {
            var e = n.timeout;
            if (r.init) {
                r.init = false;
                A(true)
            } else {
                s.push(setTimeout(function() {
                    if (r.maxSlide == 0 && r.running == true) {} else {
                        r.lastSlide = r.currentSlide;
                        r.currentSlide += 1;
                        A()
                    }
                }, e))
            }
        }

        function A(t) {
            e(".active-slide").removeClass("active-slide");
            if (r.currentSlide > r.maxSlide) {
                r.currentSlide = 0
            }
            if (r.currentSlide < 0) {
                r.currentSlide = r.maxSlide
            }
            i.currentSlide = f.children(".slide:eq(" + r.currentSlide + ")").addClass("active-slide");
            if (i.currentSlide.length == 0) {
                r.currentSlide = 0;
                i.currentSlide = f.children(".slide:eq(" + r.currentSlide + ")")
            }
            if (r.lastSlide != null) {
                if (r.lastSlide < 0) {
                    r.lastSlide = r.maxSlide
                }
                i.lastSlide = f.children(".slide:eq(" + r.lastSlide + ")")
            }
            if (t) {
                i.animation = "none"
            } else {
                i.animation = i.currentSlide.attr("data-in");
                if (i.animation == null) {
                    i.animation = n.slideTransition
                }
            }
            if (n.slideEndAnimation && r.lastSlide != null) {
                I()
            } else {
                switch (i.animation) {
                    case "none":
                        O();
                        _();
                        break;
                    case "scrollLeft":
                        O();
                        _();
                        break;
                    case "scrollRight":
                        O();
                        _();
                        break;
                    case "scrollTop":
                        O();
                        _();
                        break;
                    case "scrollBottom":
                        O();
                        _();
                        break;
                    default:
                        O();
                        break
                }
            }
        }

        function O() {
            if (n.backgroundAnimation) {
                W()
            }
            if (n.pager) {
                l.removeClass("active");
                l.eq(r.currentSlide).addClass("active")
            }
            P();
            i.currentSlide.children().hide();
            r.currentStep = 0;
            r.currentObj = 0;
            r.maxObjs = 0;
            r.finishedObjs = 0;
            i.currentSlide.children("[data-fixed]").show();
            q()
        }

        function M(e) {
            if (i.lastSlide != null) {
                i.lastSlide.hide()
            }
            if (e.hasClass("active-slide")) {
                k("step")
            }
        }

        function _() {
            if (i.lastSlide == null) {
                return
            }
            if (i.animation != "none") {
                R()
            }
        }

        function D() {}

        function P() {
            var t = i.currentSlide.children(),
                n = 0;
            t.each(function() {
                var t = parseFloat(e(this).attr("data-step"));
                n = t > n ? t : n
            });
            r.maxStep = n
        }

        function H() {
            var e;
            if (r.currentStep == 0) {
                e = i.currentSlide.children('*:not([data-step]):not([data-fixed]), *[data-step="' + r.currentStep + '"]:not([data-fixed])')
            } else {
                e = i.currentSlide.children('*[data-step="' + r.currentStep + '"]:not([data-fixed])')
            }
            r.maxObjs = e.length;
            o = e;
            if (r.maxObjs > 0) {
                r.currentObj = 0;
                r.finishedObjs = 0;
                k("obj")
            } else {
                B()
            }
        }

        function B() {
            r.currentStep += 1;
            if (r.currentStep > r.maxStep) {
                if (n.autoChange) {
                    r.currentStep = 0;
                    k("slide")
                }
                return
            }
            k("step")
        }

        function j() {
            var t = e(o[r.currentObj]);
            t.addClass("fs-animation");
            var i = t.attr("data-position"),
                s = t.attr("data-in"),
                u = t.attr("data-delay"),
                a = t.attr("data-time"),
                f = t.attr("data-ease-in"),
                l = t.attr("data-special");
            if (i == null) {
                i = n.position.split(",")
            } else {
                i = i.split(",")
            }
            if (s == null) {
                s = n.transitionIn
            }
            if (u == null) {
                u = n.delay
            }
            if (f == null) {
                f = n.easeIn
            }
            U(t, i, s, u, a, f, l);
            r.currentObj += 1;
            if (r.currentObj < r.maxObjs) {
                k("obj")
            } else {
                r.currentObj = 0
            }
        }

        function F(e) {
            e.removeClass("fs-animation");
            if (e.attr("rel") == r.currentSlide) {
                r.finishedObjs += 1;
                if (r.finishedObjs == r.maxObjs) {
                    B()
                }
            }
        }

        function I() {
            var t = i.lastSlide.children(":not([data-fixed])");
            t.each(function() {
                var t = e(this),
                    r = t.position(),
                    i = t.attr("data-out"),
                    s = t.attr("data-ease-out");
                if (i == null) {
                    i = n.transitionOut
                }
                if (s == null) {
                    s = n.easeOut
                }
                z(t, r, i, null, s)
            }).promise().done(function() {
                _();
                O()
            })
        }

        function q() {
            var e = i.currentSlide,
                t = {},
                r = {},
                s = n.slideTransitionSpeed,
                o = i.animation;
            if (n.responsive) {
                unit = "%"
            } else {
                unit = "px"
            }
            switch (o) {
                case "slideLeft":
                    t.left = c + unit;
                    t.top = "0" + unit;
                    t.display = "block";
                    r.left = "0" + unit;
                    r.top = "0" + unit;
                    break;
                case "slideTop":
                    t.left = "0" + unit;
                    t.top = -d + unit;
                    t.display = "block";
                    r.left = "0" + unit;
                    r.top = "0" + unit;
                    break;
                case "slideBottom":
                    t.left = "0" + unit;
                    t.top = d + unit;
                    t.display = "block";
                    r.left = "0" + unit;
                    r.top = "0" + unit;
                    break;
                case "slideRight":
                    t.left = -c + unit;
                    t.top = "0" + unit;
                    t.display = "block";
                    r.left = "0" + unit;
                    r.top = "0" + unit;
                    break;
                case "fade":
                    t.left = "0" + unit;
                    t.top = "0" + unit;
                    t.display = "block";
                    t.opacity = 0;
                    r.opacity = 1;
                    break;
                case "none":
                    t.left = "0" + unit;
                    t.top = "0" + unit;
                    t.display = "block";
                    s = 0;
                    break;
                case "scrollLeft":
                    t.left = c + unit;
                    t.top = "0" + unit;
                    t.display = "block";
                    r.left = "0" + unit;
                    r.top = "0" + unit;
                    break;
                case "scrollTop":
                    t.left = "0" + unit;
                    t.top = -d + unit;
                    t.display = "block";
                    r.left = "0" + unit;
                    r.top = "0" + unit;
                    break;
                case "scrollBottom":
                    t.left = "0" + unit;
                    t.top = d + unit;
                    t.display = "block";
                    r.left = "0" + unit;
                    r.top = "0" + unit;
                    break;
                case "scrollRight":
                    t.left = -c + unit;
                    t.top = "0" + unit;
                    t.display = "block";
                    r.left = "0" + unit;
                    r.top = "0" + unit;
                    break
            }
            e.css(t).animate(r, s, "linear", function() {
                M(e)
            })
        }

        function R() {
            var e = {},
                t = {},
                r = n.slideTransitionSpeed,
                s = null,
                o = i.animation;
            if (n.responsive) {
                s = "%"
            } else {
                s = "px"
            }
            switch (o) {
                case "scrollLeft":
                    t.left = -c + s;
                    t.top = "0" + s;
                    break;
                case "scrollTop":
                    t.left = "0" + s;
                    t.top = d + s;
                    break;
                case "scrollBottom":
                    t.left = "0" + s;
                    t.top = -d + s;
                    break;
                case "scrollRight":
                    t.left = c + s;
                    t.top = "0" + s;
                    break;
                default:
                    r = 0;
                    break
            }
            i.lastSlide.animate(t, r, "linear", function() {
                D()
            })
        }

        function U(t, i, o, u, a, f, l) {
            var h = {},
                v = {},
                m = n.speedIn,
                g = null;
            if (n.responsive) {
                g = "%"
            } else {
                g = "px"
            }
            if (a != null) {
                m = a - u
            }
            h.opacity = 1;
            switch (o) {
                case "left":
                    h.top = i[0];
                    h.left = c;
                    break;
                case "bottomLeft":
                    h.top = d;
                    h.left = c;
                    break;
                case "topLeft":
                    h.top = t.outerHeight() * -1;
                    h.left = c;
                    break;
                case "top":
                    h.top = t.outerHeight() * -1;
                    h.left = i[1];
                    break;
                case "bottom":
                    h.top = d;
                    h.left = i[1];
                    break;
                case "right":
                    h.top = i[0];
                    h.left = -p - t.outerWidth();
                    break;
                case "bottomRight":
                    h.top = d;
                    h.left = -p - t.outerWidth();
                    break;
                case "topRight":
                    h.top = t.outerHeight() * -1;
                    h.left = -p - t.outerWidth();
                    break;
                case "fade":
                    h.top = i[0];
                    h.left = i[1];
                    h.opacity = 0;
                    v.opacity = 1;
                    break;
                case "none":
                    h.top = i[0];
                    h.left = i[1];
                    h.display = "none";
                    m = 0;
                    break
            }
            v.top = i[0];
            v.left = i[1];
            v.left = v.left + g;
            v.top = v.top + g;
            h.left = h.left + g;
            h.top = h.top + g;
            s.push(setTimeout(function() {
                if (l == "cycle" && t.attr("rel") == r.currentSlide) {
                    var i = t.prev();
                    if (i.length > 0) {
                        var s = e(i).attr("data-position").split(",");
                        s = {
                            top: s[0],
                            left: s[1]
                        };
                        var o = e(i).attr("data-out");
                        if (o == null) {
                            o = n.transitionOut
                        }
                        z(i, s, o, m)
                    }
                }
                t.css(h).show().animate(v, m, f, function() {
                    F(t)
                }).addClass("fs_obj_active")
            }, u))
        }

        function z(e, t, r, i, s) {
            var o = {},
                f = {},
                l = null;
            i = n.speedOut;
            if (n.responsive) {
                l = "%"
            } else {
                l = "px"
            }
            var h = e.outerWidth(),
                v = e.outerHeight();
            if (n.responsive) {
                h = K(h, u);
                v = K(v, a)
            }
            switch (r) {
                case "left":
                    f.left = -p - 100 - h;
                    break;
                case "bottomLeft":
                    f.top = d;
                    f.left = -p - 100 - h;
                    break;
                case "topLeft":
                    f.top = -v;
                    f.left = -p - 100 - h;
                    break;
                case "top":
                    f.top = -v;
                    break;
                case "bottom":
                    f.top = d;
                    break;
                case "right":
                    f.left = c;
                    break;
                case "bottomRight":
                    f.top = d;
                    f.left = c;
                    break;
                case "topRight":
                    f.top = -v;
                    f.left = c;
                    break;
                case "fade":
                    o.opacity = 1;
                    f.opacity = 0;
                    break;
                case "hide":
                    f.display = "none";
                    i = 0;
                    break;
                default:
                    f.display = "none";
                    i = 0;
                    break
            }
            if (typeof f.top != "undefined") {
                if (f.top.toString().indexOf("px") > 0) {
                    f.top = f.top.substring(0, f.top.length - 2);
                    if (n.responsive) {
                        f.top = K(f.top, a)
                    }
                }
            }
            if (typeof f.left != "undefined") {
                if (f.left.toString().indexOf("px") > 0) {
                    f.left = f.left.substring(0, f.left.length - 2);
                    if (n.responsive) {
                        f.left = K(f.left, u)
                    }
                }
            }
            f.left = f.left + l;
            f.top = f.top + l;
            e.css(o).animate(f, i, s, function() {
                e.hide()
            }).removeClass("fs_obj_active")
        }

        function W() {
            var t;
            if (n.backgroundElement == null || n.backgroundElement == "") {
                t = f.parent()
            } else {
                t = e(n.backgroundElement)
            }
            var r = t.css("background-position");
            r = r.split(" ");
            var i = n.backgroundX,
                s = n.backgroundY,
                o = Number(r[0].replace(/[px,%]/g, "")) + Number(i),
                u = Number(r[1].replace(/[px,%]/g, "")) + Number(s);
            t.animate({
                backgroundPositionX: o + "px",
                backgroundPositionY: u + "px"
            }, n.backgroundSpeed, n.backgroundEase)
        }

        function X() {
            var t = n.dimensions.split(",");
            u = t["0"];
            a = t["1"];
            var r = f.children(".slide").find("*");
            r.each(function() {
                var t = e(this),
                    n = null,
                    r = null,
                    i = null;
                if (t.attr("data-position") != null) {
                    var s = t.attr("data-position").split(",");
                    n = K(s[1], u);
                    r = K(s[0], a);
                    t.attr("data-position", r + "," + n)
                }
                if (t.attr("width") != null) {
                    i = t.attr("width");
                    n = K(i, u);
                    t.attr("width", n + "%");
                    t.css("width", n + "%")
                } else if (t.css("width") != "0px") {
                    i = t.css("width");
                    if (i.indexOf("px") > 0) {
                        i = i.substring(0, i.length - 2);
                        n = K(i, u);
                        t.css("width", n + "%")
                    }
                } else if (t.prop("tagName").toLowerCase() == "img") {
                    i = t.get(0).width;
                    n = K(i, u);
                    t.css("width", n + "%")
                }
                if (t.attr("height") != null) {
                    i = t.attr("height");
                    r = K(i, a);
                    t.attr("height", r + "%");
                    t.css("height", r + "%")
                } else if (t.css("height") != "0px") {
                    i = t.css("height");
                    if (i.indexOf("px") > 0) {
                        i = i.substring(0, i.length - 2);
                        r = K(i, a);
                        t.css("height", r + "%")
                    }
                } else if (t.prop("tagName").toLowerCase() == "img") {
                    i = t.get(0).height;
                    r = K(i, a);
                    t.css("height", r + "%")
                }
                t.attr("data-fontsize", t.css("font-size"))
            });
            f.css({
                width: "auto",
                height: "auto"
            }).append('<div class="fs-stretcher" style="width:' + u + "px; height:" + a + 'px"></div>');
            V();
            e(window).bind("resize", function() {
                V()
            })
        }

        function V() {
            var e = f.innerWidth(),
                t = f.innerHeight();
            if (e <= u) {
                var i = u / a,
                    s = e / i;
                f.find(".fs-stretcher").css({
                    width: e + "px",
                    height: s + "px"
                })
            }
            var o = f.width();
            p = K((h - o) / 2, u);
            c = 100;
            if (n.fullWidth) {
                c = 100 + p * 2
            }
            d = 100;
            if (r.init == false || e < u) {
                J()
            }
        }

        function J() {
            var t = null,
                n = null,
                r = f.children(".slide").find("*");
            r.each(function() {
                obj = e(this);
                var t = obj.attr("data-fontsize");
                if (t.indexOf("px") > 0) {
                    t = t.substring(0, t.length - 2);
                    n = K(t, a) * (f.find(".fs-stretcher").height() / 100);
                    obj.css("fontSize", n + "px");
                    obj.css("lineHeight", "100%")
                }
            })
        }

        function K(e, t) {
            return e / (t / 100)
        }

        function Q(e) {
            clearTimeout(e)
        }

        function G(t) {
            var n = t.length;
            e.each(t, function(e) {
                clearTimeout(this);
                if (e == n - 1) {
                    t = []
                }
            })
        }
        var r = {
                init: true,
                running: false,
                pause: false,
                stop: false,
                controlsActive: true,
                currentSlide: 0,
                lastSlide: null,
                maxSlide: 0,
                currentStep: 0,
                maxStep: 0,
                currentObj: 0,
                maxObjs: 0,
                finishedObjs: 0
            },
            i = {
                currentSlide: null,
                lastSlide: null,
                animationkey: "none"
            },
            s = [],
            o = null,
            u = null,
            a = null;
        e(t).wrapInner('<div class="fraction-slider" />');
        var f = e(t).find(".fraction-slider"),
            l = null;
        r.maxSlide = f.children(".slide").length - 1;
        var c = f.width(),
            h = e("body").width(),
            p = 0;
        if (n.fullWidth) {
            p = (h - c) / 2;
            c = h
        }
        var d = f.height();
        v();
        this.start = function() {
            m()
        };
        this.startNextSlide = function() {
            g()
        };
        this.stop = function() {
            y()
        };
        this.pause = function() {
            b()
        };
        this.resume = function() {
            w()
        }
    };
    e.fn.fractionSlider = function(t) {
        if (n[t]) {
            return n[t].apply(this, Array.prototype.slice.call(arguments, 1))
        } else if (typeof t == "object" || !t) {
            return n.init.apply(this, arguments)
        } else {
            e.error("Method " + t + " does not exist on jQuery.tooltip")
        }
    };
    var i = {};
    e.each(["Quad", "Cubic", "Quart", "Quint", "Expo"], function(e, t) {
        i[t] = function(t) {
            return Math.pow(t, e + 2)
        }
    });
    e.extend(i, {
        Sine: function(e) {
            return 1 - Math.cos(e * Math.PI / 2)
        },
        Circ: function(e) {
            return 1 - Math.sqrt(1 - e * e)
        },
        Elastic: function(e) {
            return e == 0 || e == 1 ? e : -Math.pow(2, 8 * (e - 1)) * Math.sin(((e - 1) * 80 - 7.5) * Math.PI / 15)
        },
        Back: function(e) {
            return e * e * (3 * e - 2)
        },
        Bounce: function(e) {
            var t, n = 4;
            while (e < ((t = Math.pow(2, --n)) - 1) / 11) {}
            return 1 / Math.pow(4, 3 - n) - 7.5625 * Math.pow((t * 3 - 2) / 22 - e, 2)
        }
    });
    e.each(i, function(t, n) {
        e.easing["easeIn" + t] = n;
        e.easing["easeOut" + t] = function(e) {
            return 1 - n(1 - e)
        };
        e.easing["easeInOut" + t] = function(e) {
            return e < .5 ? n(e * 2) / 2 : 1 - n(e * -2 + 2) / 2
        }
    })
})(jQuery)