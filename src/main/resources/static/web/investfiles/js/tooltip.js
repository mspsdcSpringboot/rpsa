/**
 * van11y-accessible-simple-tooltip-aria - ES2015 accessible tooltip system, using ARIA
 * @version v3.0.0
 * @link https://van11y.net/accessible-simple-tooltip/
 * @license MIT : https://github.com/nico3333fr/van11y-accessible-simple-tooltip-aria/blob/master/LICENSE
 */
"use strict";
var _extends = Object.assign || function(t) {
        for (var e = 1; e < arguments.length; e++) {
            var i = arguments[e];
            for (var n in i) Object.prototype.hasOwnProperty.call(i, n) && (t[n] = i[n])
        }
        return t
    },
    loadConfig = function() {
        var t = {},
            e = function(e, i) {
                t[e] = i
            },
            i = function(e) {
                return t[e]
            },
            n = function(e) {
                return t[e]
            };
        return {
            set: e,
            get: i,
            remove: n
        }
    },
    DATA_HASH_ID = "data-hashtooltip-id",
    pluginConfig = loadConfig(),
    findById = function(t) {
        var e = arguments.length <= 1 || void 0 === arguments[1] ? "" : arguments[1];
        return "" !== e ? document.querySelector("#" + t + "[" + DATA_HASH_ID + '="' + e + '"]') : document.getElementById(t)
    },
    addClass = function(t, e) {
        t.classList ? t.classList.add(e) : t.className += " " + e
    },
    hasClass = function(t, e) {
        return t.classList ? t.classList.contains(e) : new RegExp("(^| )" + e + "( |$)", "gi").test(t.className)
    },
    searchParentHashId = function(t, e) {
        for (var i = !1, n = t; 1 === n.nodeType && n && i === !1;) n.hasAttribute(e) === !0 ? i = !0 : n = n.parentNode;
        return i === !0 ? n.getAttribute(e) : ""
    },
    wrapItem = function(t, e, i, n) {
        var o = [e, i].filter(Boolean).join("-"),
            a = document.createElement("SPAN");
        return addClass(a, o), a.setAttribute(DATA_HASH_ID, n), t.parentNode.insertBefore(a, t), a.appendChild(t), a
    },
    createTooltip = function(t) {
        var e = [t.className, t.tooltipSimpleRaw].filter(Boolean).join("-"),
            i = t.text;
        if (!i && t.id) {
            var n = findById(t.id);
            n && (i = n.innerHTML)
        }
        return '<span class="' + e + " " + t.jsClass + '" id="' + t.tooltipId + '" ' + t.roleTooltip + " " + t.hiddenAttribute + " " + t.hashIdAttribute + ">" + i + "</span>"
    },
    plugin = function() {
        var t = arguments.length <= 0 || void 0 === arguments[0] ? {} : arguments[0],
            e = _extends({
                TOOLTIP_SIMPLE: "js-simple-tooltip",
                TOOLTIP_SIMPLE_CONTAINER: "simpletooltip_container",
                TOOLTIP_SIMPLE_RAW: "simpletooltip",
                TOOLTIP_SIMPLE_LABEL_ID: "label_simpletooltip_",
                TOOLTIP_DATA_TEXT: "data-simpletooltip-text",
                TOOLTIP_DATA_PREFIX_CLASS: "data-simpletooltip-prefix-class",
                TOOLTIP_DATA_CONTENT_ID: "data-simpletooltip-content-id",
                ATTR_DESCRIBEDBY: "aria-describedby",
                ATTR_HIDDEN: "aria-hidden",
                ATTR_ROLE: "role",
                ROLE: "tooltip"
            }, t),
            i = Math.random().toString(32).slice(2, 12);
        pluginConfig.set(i, e);
        var n = function() {
                var t = arguments.length <= 0 || void 0 === arguments[0] ? document : arguments[0];
                return [].slice.call(t.querySelectorAll("." + e.TOOLTIP_SIMPLE))
            },
            o = function(t) {
                n(t).forEach(function(t) {
                    t.setAttribute(DATA_HASH_ID, i);
                    var n = Math.random().toString(32).slice(2, 12),
                        o = t.hasAttribute(e.TOOLTIP_DATA_TEXT) === !0 ? t.getAttribute(e.TOOLTIP_DATA_TEXT) : "",
                        a = t.hasAttribute(e.TOOLTIP_DATA_PREFIX_CLASS) === !0 ? t.getAttribute(e.TOOLTIP_DATA_PREFIX_CLASS) : "",
                        r = t.hasAttribute(e.TOOLTIP_DATA_CONTENT_ID) === !0 ? t.getAttribute(e.TOOLTIP_DATA_CONTENT_ID) : "";
                    t.setAttribute(e.ATTR_DESCRIBEDBY, e.TOOLTIP_SIMPLE_LABEL_ID + n), wrapItem(t, a, e.TOOLTIP_SIMPLE_CONTAINER, i).insertAdjacentHTML("beforeEnd", createTooltip({
                        text: o,
                        className: a,
                        jsClass: e.TOOLTIP_SIMPLE,
                        id: r,
                        tooltipId: e.TOOLTIP_SIMPLE_LABEL_ID + n,
                        tooltipSimpleRaw: e.TOOLTIP_SIMPLE_RAW,
                        hiddenAttribute: e.ATTR_HIDDEN + '="true"',
                        roleTooltip: e.ATTR_ROLE + '="' + e.ROLE + '"',
                        hashIdAttribute: DATA_HASH_ID + '="' + i + '"'
                    }))
                })
            };
        return {
            attach: o
        }
    },
    main = function() {
        return ["mouseenter", "focus", "mouseleave", "blur", "keydown"].forEach(function(t) {
            document.body.addEventListener(t, function(e) {
                var i = searchParentHashId(e.target, DATA_HASH_ID);
                if ("" !== i) {
                    var n = pluginConfig.get(i);
                    if (hasClass(e.target, n.TOOLTIP_SIMPLE) === !0) {
                        var o = e.target;
                        if ("mouseenter" === t || "focus" === t) {
                            var a = findById(o.getAttribute(n.ATTR_DESCRIBEDBY), i);
                            a && a.setAttribute(n.ATTR_HIDDEN, "false")
                        }
                        if ("mouseleave" === t || "blur" === t || "keydown" === t && 27 === e.keyCode) {
                            var a = findById(o.getAttribute(n.ATTR_DESCRIBEDBY), i);
                            a && a.setAttribute(n.ATTR_HIDDEN, "true")
                        }
                    }
                }
            }, !0)
        }), plugin
    };
window.van11yAccessibleSimpleTooltipAria = main();
var onLoad = function t() {
    var e = window.van11yAccessibleSimpleTooltipAria();
    e.attach(), document.removeEventListener("DOMContentLoaded", t)
};
document.addEventListener("DOMContentLoaded", onLoad);