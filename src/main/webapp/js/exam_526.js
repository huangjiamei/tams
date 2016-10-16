// moment.js
// version : 2.1.0
// author : Tim Wood
// license : MIT
// momentjs.com
!function(t){function e(t,e){return function(n){return u(t.call(this,n),e)}}function n(t,e){return function(n){return this.lang().ordinal(t.call(this,n),e)}}function s(){}function i(t){a(this,t)}function r(t){var e=t.years||t.year||t.y||0,n=t.months||t.month||t.M||0,s=t.weeks||t.week||t.w||0,i=t.days||t.day||t.d||0,r=t.hours||t.hour||t.h||0,a=t.minutes||t.minute||t.m||0,o=t.seconds||t.second||t.s||0,u=t.milliseconds||t.millisecond||t.ms||0;this._input=t,this._milliseconds=u+1e3*o+6e4*a+36e5*r,this._days=i+7*s,this._months=n+12*e,this._data={},this._bubble()}function a(t,e){for(var n in e)e.hasOwnProperty(n)&&(t[n]=e[n]);return t}function o(t){return 0>t?Math.ceil(t):Math.floor(t)}function u(t,e){for(var n=t+"";n.length<e;)n="0"+n;return n}function h(t,e,n,s){var i,r,a=e._milliseconds,o=e._days,u=e._months;a&&t._d.setTime(+t._d+a*n),(o||u)&&(i=t.minute(),r=t.hour()),o&&t.date(t.date()+o*n),u&&t.month(t.month()+u*n),a&&!s&&H.updateOffset(t),(o||u)&&(t.minute(i),t.hour(r))}function d(t){return"[object Array]"===Object.prototype.toString.call(t)}function c(t,e){var n,s=Math.min(t.length,e.length),i=Math.abs(t.length-e.length),r=0;for(n=0;s>n;n++)~~t[n]!==~~e[n]&&r++;return r+i}function f(t){return t?ie[t]||t.toLowerCase().replace(/(.)s$/,"$1"):t}function l(t,e){return e.abbr=t,x[t]||(x[t]=new s),x[t].set(e),x[t]}function _(t){if(!t)return H.fn._lang;if(!x[t]&&A)try{require("./lang/"+t)}catch(e){return H.fn._lang}return x[t]}function m(t){return t.match(/\[.*\]/)?t.replace(/^\[|\]$/g,""):t.replace(/\\/g,"")}function y(t){var e,n,s=t.match(E);for(e=0,n=s.length;n>e;e++)s[e]=ue[s[e]]?ue[s[e]]:m(s[e]);return function(i){var r="";for(e=0;n>e;e++)r+=s[e]instanceof Function?s[e].call(i,t):s[e];return r}}function M(t,e){function n(e){return t.lang().longDateFormat(e)||e}for(var s=5;s--&&N.test(e);)e=e.replace(N,n);return re[e]||(re[e]=y(e)),re[e](t)}function g(t,e){switch(t){case"DDDD":return V;case"YYYY":return X;case"YYYYY":return $;case"S":case"SS":case"SSS":case"DDD":return I;case"MMM":case"MMMM":case"dd":case"ddd":case"dddd":return R;case"a":case"A":return _(e._l)._meridiemParse;case"X":return B;case"Z":case"ZZ":return j;case"T":return q;case"MM":case"DD":case"YY":case"HH":case"hh":case"mm":case"ss":case"M":case"D":case"d":case"H":case"h":case"m":case"s":return J;default:return new RegExp(t.replace("\\",""))}}function p(t){var e=(j.exec(t)||[])[0],n=(e+"").match(ee)||["-",0,0],s=+(60*n[1])+~~n[2];return"+"===n[0]?-s:s}function D(t,e,n){var s,i=n._a;switch(t){case"M":case"MM":i[1]=null==e?0:~~e-1;break;case"MMM":case"MMMM":s=_(n._l).monthsParse(e),null!=s?i[1]=s:n._isValid=!1;break;case"D":case"DD":case"DDD":case"DDDD":null!=e&&(i[2]=~~e);break;case"YY":i[0]=~~e+(~~e>68?1900:2e3);break;case"YYYY":case"YYYYY":i[0]=~~e;break;case"a":case"A":n._isPm=_(n._l).isPM(e);break;case"H":case"HH":case"h":case"hh":i[3]=~~e;break;case"m":case"mm":i[4]=~~e;break;case"s":case"ss":i[5]=~~e;break;case"S":case"SS":case"SSS":i[6]=~~(1e3*("0."+e));break;case"X":n._d=new Date(1e3*parseFloat(e));break;case"Z":case"ZZ":n._useUTC=!0,n._tzm=p(e)}null==e&&(n._isValid=!1)}function Y(t){var e,n,s=[];if(!t._d){for(e=0;7>e;e++)t._a[e]=s[e]=null==t._a[e]?2===e?1:0:t._a[e];s[3]+=~~((t._tzm||0)/60),s[4]+=~~((t._tzm||0)%60),n=new Date(0),t._useUTC?(n.setUTCFullYear(s[0],s[1],s[2]),n.setUTCHours(s[3],s[4],s[5],s[6])):(n.setFullYear(s[0],s[1],s[2]),n.setHours(s[3],s[4],s[5],s[6])),t._d=n}}function w(t){var e,n,s=t._f.match(E),i=t._i;for(t._a=[],e=0;e<s.length;e++)n=(g(s[e],t).exec(i)||[])[0],n&&(i=i.slice(i.indexOf(n)+n.length)),ue[s[e]]&&D(s[e],n,t);i&&(t._il=i),t._isPm&&t._a[3]<12&&(t._a[3]+=12),t._isPm===!1&&12===t._a[3]&&(t._a[3]=0),Y(t)}function k(t){var e,n,s,r,o,u=99;for(r=0;r<t._f.length;r++)e=a({},t),e._f=t._f[r],w(e),n=new i(e),o=c(e._a,n.toArray()),n._il&&(o+=n._il.length),u>o&&(u=o,s=n);a(t,s)}function v(t){var e,n=t._i,s=K.exec(n);if(s){for(t._f="YYYY-MM-DD"+(s[2]||" "),e=0;4>e;e++)if(te[e][1].exec(n)){t._f+=te[e][0];break}j.exec(n)&&(t._f+=" Z"),w(t)}else t._d=new Date(n)}function T(e){var n=e._i,s=G.exec(n);n===t?e._d=new Date:s?e._d=new Date(+s[1]):"string"==typeof n?v(e):d(n)?(e._a=n.slice(0),Y(e)):e._d=n instanceof Date?new Date(+n):new Date(n)}function b(t,e,n,s,i){return i.relativeTime(e||1,!!n,t,s)}function S(t,e,n){var s=W(Math.abs(t)/1e3),i=W(s/60),r=W(i/60),a=W(r/24),o=W(a/365),u=45>s&&["s",s]||1===i&&["m"]||45>i&&["mm",i]||1===r&&["h"]||22>r&&["hh",r]||1===a&&["d"]||25>=a&&["dd",a]||45>=a&&["M"]||345>a&&["MM",W(a/30)]||1===o&&["y"]||["yy",o];return u[2]=e,u[3]=t>0,u[4]=n,b.apply({},u)}function F(t,e,n){var s,i=n-e,r=n-t.day();return r>i&&(r-=7),i-7>r&&(r+=7),s=H(t).add("d",r),{week:Math.ceil(s.dayOfYear()/7),year:s.year()}}function O(t){var e=t._i,n=t._f;return null===e||""===e?null:("string"==typeof e&&(t._i=e=_().preparse(e)),H.isMoment(e)?(t=a({},e),t._d=new Date(+e._d)):n?d(n)?k(t):w(t):T(t),new i(t))}function z(t,e){H.fn[t]=H.fn[t+"s"]=function(t){var n=this._isUTC?"UTC":"";return null!=t?(this._d["set"+n+e](t),H.updateOffset(this),this):this._d["get"+n+e]()}}function C(t){H.duration.fn[t]=function(){return this._data[t]}}function L(t,e){H.duration.fn["as"+t]=function(){return+this/e}}for(var H,P,U="2.1.0",W=Math.round,x={},A="undefined"!=typeof module&&module.exports,G=/^\/?Date\((\-?\d+)/i,Z=/(\-)?(\d*)?\.?(\d+)\:(\d+)\:(\d+)\.?(\d{3})?/,E=/(\[[^\[]*\])|(\\)?(Mo|MM?M?M?|Do|DDDo|DD?D?D?|ddd?d?|do?|w[o|w]?|W[o|W]?|YYYYY|YYYY|YY|gg(ggg?)?|GG(GGG?)?|e|E|a|A|hh?|HH?|mm?|ss?|SS?S?|X|zz?|ZZ?|.)/g,N=/(\[[^\[]*\])|(\\)?(LT|LL?L?L?|l{1,4})/g,J=/\d\d?/,I=/\d{1,3}/,V=/\d{3}/,X=/\d{1,4}/,$=/[+\-]?\d{1,6}/,R=/[0-9]*['a-z\u00A0-\u05FF\u0700-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+|[\u0600-\u06FF\/]+(\s*?[\u0600-\u06FF]+){1,2}/i,j=/Z|[\+\-]\d\d:?\d\d/i,q=/T/i,B=/[\+\-]?\d+(\.\d{1,3})?/,K=/^\s*\d{4}-\d\d-\d\d((T| )(\d\d(:\d\d(:\d\d(\.\d\d?\d?)?)?)?)?([\+\-]\d\d:?\d\d)?)?/,Q="YYYY-MM-DDTHH:mm:ssZ",te=[["HH:mm:ss.S",/(T| )\d\d:\d\d:\d\d\.\d{1,3}/],["HH:mm:ss",/(T| )\d\d:\d\d:\d\d/],["HH:mm",/(T| )\d\d:\d\d/],["HH",/(T| )\d\d/]],ee=/([\+\-]|\d\d)/gi,ne="Date|Hours|Minutes|Seconds|Milliseconds".split("|"),se={Milliseconds:1,Seconds:1e3,Minutes:6e4,Hours:36e5,Days:864e5,Months:2592e6,Years:31536e6},ie={ms:"millisecond",s:"second",m:"minute",h:"hour",d:"day",w:"week",M:"month",y:"year"},re={},ae="DDD w W M D d".split(" "),oe="M D H h m s w W".split(" "),ue={M:function(){return this.month()+1},MMM:function(t){return this.lang().monthsShort(this,t)},MMMM:function(t){return this.lang().months(this,t)},D:function(){return this.date()},DDD:function(){return this.dayOfYear()},d:function(){return this.day()},dd:function(t){return this.lang().weekdaysMin(this,t)},ddd:function(t){return this.lang().weekdaysShort(this,t)},dddd:function(t){return this.lang().weekdays(this,t)},w:function(){return this.week()},W:function(){return this.isoWeek()},YY:function(){return u(this.year()%100,2)},YYYY:function(){return u(this.year(),4)},YYYYY:function(){return u(this.year(),5)},gg:function(){return u(this.weekYear()%100,2)},gggg:function(){return this.weekYear()},ggggg:function(){return u(this.weekYear(),5)},GG:function(){return u(this.isoWeekYear()%100,2)},GGGG:function(){return this.isoWeekYear()},GGGGG:function(){return u(this.isoWeekYear(),5)},e:function(){return this.weekday()},E:function(){return this.isoWeekday()},a:function(){return this.lang().meridiem(this.hours(),this.minutes(),!0)},A:function(){return this.lang().meridiem(this.hours(),this.minutes(),!1)},H:function(){return this.hours()},h:function(){return this.hours()%12||12},m:function(){return this.minutes()},s:function(){return this.seconds()},S:function(){return~~(this.milliseconds()/100)},SS:function(){return u(~~(this.milliseconds()/10),2)},SSS:function(){return u(this.milliseconds(),3)},Z:function(){var t=-this.zone(),e="+";return 0>t&&(t=-t,e="-"),e+u(~~(t/60),2)+":"+u(~~t%60,2)},ZZ:function(){var t=-this.zone(),e="+";return 0>t&&(t=-t,e="-"),e+u(~~(10*t/6),4)},z:function(){return this.zoneAbbr()},zz:function(){return this.zoneName()},X:function(){return this.unix()}};ae.length;)P=ae.pop(),ue[P+"o"]=n(ue[P],P);for(;oe.length;)P=oe.pop(),ue[P+P]=e(ue[P],2);for(ue.DDDD=e(ue.DDD,3),s.prototype={set:function(t){var e,n;for(n in t)e=t[n],"function"==typeof e?this[n]=e:this["_"+n]=e},_months:"January_February_March_April_May_June_July_August_September_October_November_December".split("_"),months:function(t){return this._months[t.month()]},_monthsShort:"Jan_Feb_Mar_Apr_May_Jun_Jul_Aug_Sep_Oct_Nov_Dec".split("_"),monthsShort:function(t){return this._monthsShort[t.month()]},monthsParse:function(t){var e,n,s;for(this._monthsParse||(this._monthsParse=[]),e=0;12>e;e++)if(this._monthsParse[e]||(n=H([2e3,e]),s="^"+this.months(n,"")+"|^"+this.monthsShort(n,""),this._monthsParse[e]=new RegExp(s.replace(".",""),"i")),this._monthsParse[e].test(t))return e},_weekdays:"Sunday_Monday_Tuesday_Wednesday_Thursday_Friday_Saturday".split("_"),weekdays:function(t){return this._weekdays[t.day()]},_weekdaysShort:"Sun_Mon_Tue_Wed_Thu_Fri_Sat".split("_"),weekdaysShort:function(t){return this._weekdaysShort[t.day()]},_weekdaysMin:"Su_Mo_Tu_We_Th_Fr_Sa".split("_"),weekdaysMin:function(t){return this._weekdaysMin[t.day()]},weekdaysParse:function(t){var e,n,s;for(this._weekdaysParse||(this._weekdaysParse=[]),e=0;7>e;e++)if(this._weekdaysParse[e]||(n=H([2e3,1]).day(e),s="^"+this.weekdays(n,"")+"|^"+this.weekdaysShort(n,"")+"|^"+this.weekdaysMin(n,""),this._weekdaysParse[e]=new RegExp(s.replace(".",""),"i")),this._weekdaysParse[e].test(t))return e},_longDateFormat:{LT:"h:mm A",L:"MM/DD/YYYY",LL:"MMMM D YYYY",LLL:"MMMM D YYYY LT",LLLL:"dddd, MMMM D YYYY LT"},longDateFormat:function(t){var e=this._longDateFormat[t];return!e&&this._longDateFormat[t.toUpperCase()]&&(e=this._longDateFormat[t.toUpperCase()].replace(/MMMM|MM|DD|dddd/g,function(t){return t.slice(1)}),this._longDateFormat[t]=e),e},isPM:function(t){return"p"===(t+"").toLowerCase()[0]},_meridiemParse:/[ap]\.?m?\.?/i,meridiem:function(t,e,n){return t>11?n?"pm":"PM":n?"am":"AM"},_calendar:{sameDay:"[Today at] LT",nextDay:"[Tomorrow at] LT",nextWeek:"dddd [at] LT",lastDay:"[Yesterday at] LT",lastWeek:"[Last] dddd [at] LT",sameElse:"L"},calendar:function(t,e){var n=this._calendar[t];return"function"==typeof n?n.apply(e):n},_relativeTime:{future:"in %s",past:"%s ago",s:"a few seconds",m:"a minute",mm:"%d minutes",h:"an hour",hh:"%d hours",d:"a day",dd:"%d days",M:"a month",MM:"%d months",y:"a year",yy:"%d years"},relativeTime:function(t,e,n,s){var i=this._relativeTime[n];return"function"==typeof i?i(t,e,n,s):i.replace(/%d/i,t)},pastFuture:function(t,e){var n=this._relativeTime[t>0?"future":"past"];return"function"==typeof n?n(e):n.replace(/%s/i,e)},ordinal:function(t){return this._ordinal.replace("%d",t)},_ordinal:"%d",preparse:function(t){return t},postformat:function(t){return t},week:function(t){return F(t,this._week.dow,this._week.doy).week},_week:{dow:0,doy:6}},H=function(t,e,n){return O({_i:t,_f:e,_l:n,_isUTC:!1})},H.utc=function(t,e,n){return O({_useUTC:!0,_isUTC:!0,_l:n,_i:t,_f:e})},H.unix=function(t){return H(1e3*t)},H.duration=function(t,e){var n,s,i=H.isDuration(t),a="number"==typeof t,o=i?t._input:a?{}:t,u=Z.exec(t);return a?e?o[e]=t:o.milliseconds=t:u&&(n="-"===u[1]?-1:1,o={y:0,d:~~u[2]*n,h:~~u[3]*n,m:~~u[4]*n,s:~~u[5]*n,ms:~~u[6]*n}),s=new r(o),i&&t.hasOwnProperty("_lang")&&(s._lang=t._lang),s},H.version=U,H.defaultFormat=Q,H.updateOffset=function(){},H.lang=function(t,e){return t?(e?l(t,e):x[t]||_(t),H.duration.fn._lang=H.fn._lang=_(t),void 0):H.fn._lang._abbr},H.langData=function(t){return t&&t._lang&&t._lang._abbr&&(t=t._lang._abbr),_(t)},H.isMoment=function(t){return t instanceof i},H.isDuration=function(t){return t instanceof r},H.fn=i.prototype={clone:function(){return H(this)},valueOf:function(){return+this._d+6e4*(this._offset||0)},unix:function(){return Math.floor(+this/1e3)},toString:function(){return this.format("ddd MMM DD YYYY HH:mm:ss [GMT]ZZ")},toDate:function(){return this._offset?new Date(+this):this._d},toISOString:function(){return M(H(this).utc(),"YYYY-MM-DD[T]HH:mm:ss.SSS[Z]")},toArray:function(){var t=this;return[t.year(),t.month(),t.date(),t.hours(),t.minutes(),t.seconds(),t.milliseconds()]},isValid:function(){return null==this._isValid&&(this._isValid=this._a?!c(this._a,(this._isUTC?H.utc(this._a):H(this._a)).toArray()):!isNaN(this._d.getTime())),!!this._isValid},utc:function(){return this.zone(0)},local:function(){return this.zone(0),this._isUTC=!1,this},format:function(t){var e=M(this,t||H.defaultFormat);return this.lang().postformat(e)},add:function(t,e){var n;return n="string"==typeof t?H.duration(+e,t):H.duration(t,e),h(this,n,1),this},subtract:function(t,e){var n;return n="string"==typeof t?H.duration(+e,t):H.duration(t,e),h(this,n,-1),this},diff:function(t,e,n){var s,i,r=this._isUTC?H(t).zone(this._offset||0):H(t).local(),a=6e4*(this.zone()-r.zone());return e=f(e),"year"===e||"month"===e?(s=432e5*(this.daysInMonth()+r.daysInMonth()),i=12*(this.year()-r.year())+(this.month()-r.month()),i+=(this-H(this).startOf("month")-(r-H(r).startOf("month")))/s,i-=6e4*(this.zone()-H(this).startOf("month").zone()-(r.zone()-H(r).startOf("month").zone()))/s,"year"===e&&(i/=12)):(s=this-r,i="second"===e?s/1e3:"minute"===e?s/6e4:"hour"===e?s/36e5:"day"===e?(s-a)/864e5:"week"===e?(s-a)/6048e5:s),n?i:o(i)},from:function(t,e){return H.duration(this.diff(t)).lang(this.lang()._abbr).humanize(!e)},fromNow:function(t){return this.from(H(),t)},calendar:function(){var t=this.diff(H().startOf("day"),"days",!0),e=-6>t?"sameElse":-1>t?"lastWeek":0>t?"lastDay":1>t?"sameDay":2>t?"nextDay":7>t?"nextWeek":"sameElse";return this.format(this.lang().calendar(e,this))},isLeapYear:function(){var t=this.year();return 0===t%4&&0!==t%100||0===t%400},isDST:function(){return this.zone()<this.clone().month(0).zone()||this.zone()<this.clone().month(5).zone()},day:function(t){var e=this._isUTC?this._d.getUTCDay():this._d.getDay();return null!=t?"string"==typeof t&&(t=this.lang().weekdaysParse(t),"number"!=typeof t)?this:this.add({d:t-e}):e},month:function(t){var e,n=this._isUTC?"UTC":"";return null!=t?"string"==typeof t&&(t=this.lang().monthsParse(t),"number"!=typeof t)?this:(e=this.date(),this.date(1),this._d["set"+n+"Month"](t),this.date(Math.min(e,this.daysInMonth())),H.updateOffset(this),this):this._d["get"+n+"Month"]()},startOf:function(t){switch(t=f(t)){case"year":this.month(0);case"month":this.date(1);case"week":case"day":this.hours(0);case"hour":this.minutes(0);case"minute":this.seconds(0);case"second":this.milliseconds(0)}return"week"===t&&this.weekday(0),this},endOf:function(t){return this.startOf(t).add(t,1).subtract("ms",1)},isAfter:function(t,e){return e="undefined"!=typeof e?e:"millisecond",+this.clone().startOf(e)>+H(t).startOf(e)},isBefore:function(t,e){return e="undefined"!=typeof e?e:"millisecond",+this.clone().startOf(e)<+H(t).startOf(e)},isSame:function(t,e){return e="undefined"!=typeof e?e:"millisecond",+this.clone().startOf(e)===+H(t).startOf(e)},min:function(t){return t=H.apply(null,arguments),this>t?this:t},max:function(t){return t=H.apply(null,arguments),t>this?this:t},zone:function(t){var e=this._offset||0;return null==t?this._isUTC?e:this._d.getTimezoneOffset():("string"==typeof t&&(t=p(t)),Math.abs(t)<16&&(t=60*t),this._offset=t,this._isUTC=!0,e!==t&&h(this,H.duration(e-t,"m"),1,!0),this)},zoneAbbr:function(){return this._isUTC?"UTC":""},zoneName:function(){return this._isUTC?"Coordinated Universal Time":""},daysInMonth:function(){return H.utc([this.year(),this.month()+1,0]).date()},dayOfYear:function(t){var e=W((H(this).startOf("day")-H(this).startOf("year"))/864e5)+1;return null==t?e:this.add("d",t-e)},weekYear:function(t){var e=F(this,this.lang()._week.dow,this.lang()._week.doy).year;return null==t?e:this.add("y",t-e)},isoWeekYear:function(t){var e=F(this,1,4).year;return null==t?e:this.add("y",t-e)},week:function(t){var e=this.lang().week(this);return null==t?e:this.add("d",7*(t-e))},isoWeek:function(t){var e=F(this,1,4).week;return null==t?e:this.add("d",7*(t-e))},weekday:function(t){var e=(this._d.getDay()+7-this.lang()._week.dow)%7;return null==t?e:this.add("d",t-e)},isoWeekday:function(t){return null==t?this.day()||7:this.day(this.day()%7?t:t-7)},lang:function(e){return e===t?this._lang:(this._lang=_(e),this)}},P=0;P<ne.length;P++)z(ne[P].toLowerCase().replace(/s$/,""),ne[P]);z("year","FullYear"),H.fn.days=H.fn.day,H.fn.months=H.fn.month,H.fn.weeks=H.fn.week,H.fn.isoWeeks=H.fn.isoWeek,H.fn.toJSON=H.fn.toISOString,H.duration.fn=r.prototype={_bubble:function(){var t,e,n,s,i=this._milliseconds,r=this._days,a=this._months,u=this._data;u.milliseconds=i%1e3,t=o(i/1e3),u.seconds=t%60,e=o(t/60),u.minutes=e%60,n=o(e/60),u.hours=n%24,r+=o(n/24),u.days=r%30,a+=o(r/30),u.months=a%12,s=o(a/12),u.years=s},weeks:function(){return o(this.days()/7)},valueOf:function(){return this._milliseconds+864e5*this._days+2592e6*(this._months%12)+31536e6*~~(this._months/12)},humanize:function(t){var e=+this,n=S(e,!t,this.lang());return t&&(n=this.lang().pastFuture(e,n)),this.lang().postformat(n)},add:function(t,e){var n=H.duration(t,e);return this._milliseconds+=n._milliseconds,this._days+=n._days,this._months+=n._months,this._bubble(),this},subtract:function(t,e){var n=H.duration(t,e);return this._milliseconds-=n._milliseconds,this._days-=n._days,this._months-=n._months,this._bubble(),this},get:function(t){return t=f(t),this[t.toLowerCase()+"s"]()},as:function(t){return t=f(t),this["as"+t.charAt(0).toUpperCase()+t.slice(1)+"s"]()},lang:H.fn.lang};for(P in se)se.hasOwnProperty(P)&&(L(P,se[P]),C(P.toLowerCase()));L("Weeks",6048e5),H.duration.fn.asMonths=function(){return(+this-31536e6*this.years())/2592e6+12*this.years()},H.lang("en",{ordinal:function(t){var e=t%10,n=1===~~(t%100/10)?"th":1===e?"st":2===e?"nd":3===e?"rd":"th";return t+n}}),A&&(module.exports=H),"undefined"==typeof ender&&(this.moment=H),"function"==typeof define&&define.amd&&define("moment",[],function(){return H})}.call(this);

/**
 * @version: 1.2
 * @author: Dan Grossman http://www.dangrossman.info/
 * @date: 2013-07-25
 * @copyright: Copyright (c) 2012-2013 Dan Grossman. All rights reserved.
 * @license: Licensed under Apache License v2.0. See
 *           http://www.apache.org/licenses/LICENSE-2.0
 * @website: http://www.improvely.com/
 */
!function ($) {

    var DateRangePicker = function (element, options, cb) {
        var hasOptions = typeof options == 'object';
        var localeObject;

        // option defaults

        this.startDate = moment().startOf('day');
        this.endDate = moment().startOf('day');
        this.minDate = false;
        this.maxDate = false;
        this.dateLimit = false;

        this.showDropdowns = false;
        this.showWeekNumbers = false;
        this.timePicker = false;
        this.timePickerIncrement = 30;
        this.timePicker12Hour = true;
        this.ranges = {};
        this.opens = 'right';

        this.buttonClasses = ['btn', 'btn-small'];
        this.applyClass = 'btn-success';
        this.cancelClass = 'btn-default';

        this.format = 'MM/DD/YYYY';
        this.separator = ' - ';

        this.locale = {
            applyLabel: 'Apply',
            cancelLabel: 'Cancel',
            fromLabel: 'From',
            toLabel: 'To',
            weekLabel: 'W',
            customRangeLabel: 'Custom Range',
            daysOfWeek: moment()._lang._weekdaysMin.slice(),
            monthNames: moment()._lang._monthsShort.slice(),
            firstDay: 0
        };

        this.cb = function () { };

        // by default, the daterangepicker element is placed at the bottom of
		// HTML body
        this.parentEl = 'body';

        // element that triggered the date range picker
        this.element = $(element);

        if (this.element.hasClass('pull-right'))
            this.opens = 'left';

        if (this.element.is('input')) {
            this.element.on({
                click: $.proxy(this.show, this),
                focus: $.proxy(this.show, this)
            });
        } else {
            this.element.on('click', $.proxy(this.show, this));
        }

        localeObject = this.locale;

        if (hasOptions) {
            if (typeof options.locale == 'object') {
                $.each(localeObject, function (property, value) {
                    localeObject[property] = options.locale[property] || value;
                });
            }

            if (options.applyClass) {
                this.applyClass = options.applyClass;
            }

            if (options.cancelClass) {
                this.cancelClass = options.cancelClass;
            }
        }

        var DRPTemplate = '<div class="daterangepicker dropdown-menu">' +
                '<div class="calendar left"></div>' +
                '<div class="calendar right"></div>' +
                '<div class="ranges">' +
                  '<div class="range_inputs">' +
                    '<div class="daterangepicker_start_input" style="float: left">' +
                      '<label for="daterangepicker_start">' + this.locale.fromLabel + '</label>' +
                      '<input class="input-mini" type="text" name="daterangepicker_start" value="" disabled="disabled" />' +
                    '</div>' +
                    '<div class="daterangepicker_end_input" style="float: left; padding-left: 11px">' +
                      '<label for="daterangepicker_end">' + this.locale.toLabel + '</label>' +
                      '<input class="input-mini" type="text" name="daterangepicker_end" value="" disabled="disabled" />' +
                    '</div>' +
                    '<button class="' + this.applyClass + ' applyBtn" disabled="disabled">' + this.locale.applyLabel + '</button>&nbsp;' +
                    '<button class="' + this.cancelClass + ' cancelBtn">' + this.locale.cancelLabel + '</button>' +
                  '</div>' +
                '</div>' +
              '</div>';

        this.parentEl = (hasOptions && options.parentEl && $(options.parentEl)) || $(this.parentEl);
        // the date range picker
        this.container = $(DRPTemplate).appendTo(this.parentEl);

        if (hasOptions) {

            if (typeof options.format == 'string')
                this.format = options.format;

            if (typeof options.separator == 'string')
                this.separator = options.separator;

            if (typeof options.startDate == 'string')
                this.startDate = moment(options.startDate, this.format);

            if (typeof options.endDate == 'string')
                this.endDate = moment(options.endDate, this.format);

            if (typeof options.minDate == 'string')
                this.minDate = moment(options.minDate, this.format);

            if (typeof options.maxDate == 'string')
                this.maxDate = moment(options.maxDate, this.format);

            if (typeof options.startDate == 'object')
                this.startDate = moment(options.startDate);

            if (typeof options.endDate == 'object')
                this.endDate = moment(options.endDate);

            if (typeof options.minDate == 'object')
                this.minDate = moment(options.minDate);

            if (typeof options.maxDate == 'object')
                this.maxDate = moment(options.maxDate);

            if (typeof options.ranges == 'object') {
                for (var range in options.ranges) {

                    var start = moment(options.ranges[range][0]);
                    var end = moment(options.ranges[range][1]);

                    // If we have a min/max date set, bound this range
                    // to it, but only if it would otherwise fall
                    // outside of the min/max.
                    if (this.minDate && start.isBefore(this.minDate))
                        start = moment(this.minDate);

                    if (this.maxDate && end.isAfter(this.maxDate))
                        end = moment(this.maxDate);

                    // If the end of the range is before the minimum (if min is
					// set) OR
                    // the start of the range is after the max (also if set)
					// don't display this
                    // range option.
                    if ((this.minDate && end.isBefore(this.minDate)) || (this.maxDate && start.isAfter(this.maxDate))) {
                        continue;
                    }

                    this.ranges[range] = [start, end];
                }

                var list = '<ul>';
                for (var range in this.ranges) {
                    list += '<li>' + range + '</li>';
                }
                list += '<li>' + this.locale.customRangeLabel + '</li>';
                list += '</ul>';
                this.container.find('.ranges').prepend(list);
            }

            if (typeof options.dateLimit == 'object')
                this.dateLimit = options.dateLimit;

            // update day names order to firstDay
            if (typeof options.locale == 'object') {

                if (typeof options.locale.daysOfWeek == 'object') {

                    // Create a copy of daysOfWeek to avoid modification of
					// original
                    // options object for reusability in multiple
					// daterangepicker instances
                    this.locale.daysOfWeek = options.locale.daysOfWeek.slice();
                }

                if (typeof options.locale.firstDay == 'number') {
                    this.locale.firstDay = options.locale.firstDay;
                    var iterator = options.locale.firstDay;
                    while (iterator > 0) {
                        this.locale.daysOfWeek.push(this.locale.daysOfWeek.shift());
                        iterator--;
                    }
                }
            }

            if (typeof options.opens == 'string')
                this.opens = options.opens;

            if (typeof options.showWeekNumbers == 'boolean') {
                this.showWeekNumbers = options.showWeekNumbers;
            }

            if (typeof options.buttonClasses == 'string') {
                this.buttonClasses = [options.buttonClasses];
            }

            if (typeof options.buttonClasses == 'object') {
                this.buttonClasses = options.buttonClasses;
            }

            if (typeof options.showDropdowns == 'boolean') {
                this.showDropdowns = options.showDropdowns;
            }

            if (typeof options.timePicker == 'boolean') {
                this.timePicker = options.timePicker;
            }

            if (typeof options.timePickerIncrement == 'number') {
                this.timePickerIncrement = options.timePickerIncrement;
            }

            if (typeof options.timePicker12Hour == 'boolean') {
                this.timePicker12Hour = options.timePicker12Hour;
            }

        }

        if (!this.timePicker) {
            this.startDate = this.startDate.startOf('day');
            this.endDate = this.endDate.startOf('day');
        }

        // apply CSS classes to buttons
        var c = this.container;
        $.each(this.buttonClasses, function (idx, val) {
            c.find('button').addClass(val);
        });

        if (this.opens == 'right') {
            // swap calendar positions
            var left = this.container.find('.calendar.left');
            var right = this.container.find('.calendar.right');
            left.removeClass('left').addClass('right');
            right.removeClass('right').addClass('left');
        }

        if (typeof options == 'undefined' || typeof options.ranges == 'undefined') {
            this.container.find('.calendar').show();
            this.move();
        }

        if (typeof cb == 'function')
            this.cb = cb;

        this.container.addClass('opens' + this.opens);

        // try parse date if in text input
        if (!hasOptions || (typeof options.startDate == 'undefined' && typeof options.endDate == 'undefined')) {
            if ($(this.element).is('input[type=text]')) {
                var val = $(this.element).val();
                var split = val.split(this.separator);
                var start, end;
                if (split.length == 2) {
                    start = moment(split[0], this.format);
                    end = moment(split[1], this.format);
                }
                if (start != null && end != null) {
                    this.startDate = start;
                    this.endDate = end;
                }
            }
        }

        // state
        this.oldStartDate = this.startDate.clone();
        this.oldEndDate = this.endDate.clone();

        this.leftCalendar = {
            month: moment([this.startDate.year(), this.startDate.month(), 1, this.startDate.hour(), this.startDate.minute()]),
            calendar: []
        };

        this.rightCalendar = {
            month: moment([this.endDate.year(), this.endDate.month(), 1, this.endDate.hour(), this.endDate.minute()]),
            calendar: []
        };

        // event listeners
        this.container.on('mousedown', $.proxy(this.mousedown, this));

        this.container.find('.calendar')
            .on('click', '.prev', $.proxy(this.clickPrev, this))
            .on('click', '.next', $.proxy(this.clickNext, this))
            .on('click', 'td.available', $.proxy(this.clickDate, this))
            .on('mouseenter', 'td.available', $.proxy(this.enterDate, this))
            .on('mouseleave', 'td.available', $.proxy(this.updateFormInputs, this))
            .on('change', 'select.yearselect', $.proxy(this.updateMonthYear, this))
            .on('change', 'select.monthselect', $.proxy(this.updateMonthYear, this))
            .on('change', 'select.hourselect,select.minuteselect,select.ampmselect', $.proxy(this.updateTime, this));

        this.container.find('.ranges')
            .on('click', 'button.applyBtn', $.proxy(this.clickApply, this))
            .on('click', 'button.cancelBtn', $.proxy(this.clickCancel, this))
            .on('click', '.daterangepicker_start_input,.daterangepicker_end_input', $.proxy(this.showCalendars, this))
            .on('click', 'li', $.proxy(this.clickRange, this))
            .on('mouseenter', 'li', $.proxy(this.enterRange, this))
            .on('mouseleave', 'li', $.proxy(this.updateFormInputs, this));

        this.element.on('keyup', $.proxy(this.updateFromControl, this));

        this.updateView();
        this.updateCalendars();

    };

    DateRangePicker.prototype = {

        constructor: DateRangePicker,

        mousedown: function (e) {
            e.stopPropagation();
        },

        updateView: function () {
            this.leftCalendar.month.month(this.startDate.month()).year(this.startDate.year());
            this.rightCalendar.month.month(this.endDate.month()).year(this.endDate.year());
            this.updateFormInputs();
        },

        updateFormInputs: function () {
            this.container.find('input[name=daterangepicker_start]').val(this.startDate.format(this.format));
            this.container.find('input[name=daterangepicker_end]').val(this.endDate.format(this.format));

            if (this.startDate.isBefore(this.endDate)) {
                this.container.find('button.applyBtn').removeAttr('disabled');
            } else {
                this.container.find('button.applyBtn').attr('disabled', 'disabled');
            }
        },

        updateFromControl: function () {
            if (!this.element.is('input')) return;
            if (!this.element.val().length) return;

            var dateString = this.element.val().split(this.separator);
            var start = moment(dateString[0], this.format);
            var end = moment(dateString[1], this.format);

            if (start == null || end == null) return;
            if (end.isBefore(start)) return;

            this.oldStartDate = this.startDate.clone();
            this.oldEndDate = this.endDate.clone();

            this.startDate = start;
            this.endDate = end;

            if (!this.startDate.isSame(this.oldStartDate) || !this.endDate.isSame(this.oldEndDate))
                this.notify();

            this.updateCalendars();
        },

        notify: function () {
            this.updateView();
            this.cb(this.startDate, this.endDate);
        },

        move: function () {
            var parentOffset = {
                top: this.parentEl.offset().top - (this.parentEl.is('body') ? 0 : this.parentEl.scrollTop()),
                left: this.parentEl.offset().left - (this.parentEl.is('body') ? 0 : this.parentEl.scrollLeft())
            };
            if (this.opens == 'left') {
                this.container.css({
                    top: this.element.offset().top + this.element.outerHeight() - parentOffset.top,
                    right: $(window).width() - this.element.offset().left - this.element.outerWidth() - parentOffset.left,
                    left: 'auto'
                });
                if (this.container.offset().left < 0) {
                    this.container.css({
                        right: 'auto',
                        left: 9
                    });
                }
            } else {
                this.container.css({
                    top: this.element.offset().top + this.element.outerHeight() - parentOffset.top,
                    left: this.element.offset().left - parentOffset.left,
                    right: 'auto'
                });
                if (this.container.offset().left + this.container.outerWidth() > $(window).width()) {
                    this.container.css({
                        left: 'auto',
                        right: 0
                    });
                }
            }
        },

        show: function (e) {
            this.container.show();
            this.move();

            if (e) {
                e.stopPropagation();
                e.preventDefault();
            }

            $(document).on('mousedown', $.proxy(this.hide, this));
            this.element.trigger('shown', {target: e.target, picker: this});
        },

        hide: function (e) {
            this.container.hide();

            if (!this.startDate.isSame(this.oldStartDate) || !this.endDate.isSame(this.oldEndDate))
                this.notify();

            this.oldStartDate = this.startDate.clone();
            this.oldEndDate = this.endDate.clone();

            $(document).off('mousedown', this.hide);
            this.element.trigger('hidden', { picker: this });
        },

        enterRange: function (e) {
            var label = e.target.innerHTML;
            if (label == this.locale.customRangeLabel) {
                this.updateView();
            } else {
                var dates = this.ranges[label];
                this.container.find('input[name=daterangepicker_start]').val(dates[0].format(this.format));
                this.container.find('input[name=daterangepicker_end]').val(dates[1].format(this.format));
            }
        },

        showCalendars: function() {
            this.container.find('.calendar').show();
            this.move();
        },

        updateInputText: function() {
            if (this.element.is('input'))
                this.element.val(this.startDate.format(this.format) + this.separator + this.endDate.format(this.format));
        },

        clickRange: function (e) {
            var label = e.target.innerHTML;
            if (label == this.locale.customRangeLabel) {
                this.showCalendars();
            } else {
                var dates = this.ranges[label];

                this.startDate = dates[0];
                this.endDate = dates[1];

                if (!this.timePicker) {
                    this.startDate.startOf('day');
                    this.endDate.startOf('day');
                }

                this.leftCalendar.month.month(this.startDate.month()).year(this.startDate.year()).hour(this.startDate.hour()).minute(this.startDate.minute());
                this.rightCalendar.month.month(this.endDate.month()).year(this.endDate.year()).hour(this.endDate.hour()).minute(this.endDate.minute());
                this.updateCalendars();

                this.updateInputText();

                this.container.find('.calendar').hide();
                this.hide();
            }
        },

        clickPrev: function (e) {
            var cal = $(e.target).parents('.calendar');
            if (cal.hasClass('left')) {
                this.leftCalendar.month.subtract('month', 1);
            } else {
                this.rightCalendar.month.subtract('month', 1);
            }
            this.updateCalendars();
        },

        clickNext: function (e) {
            var cal = $(e.target).parents('.calendar');
            if (cal.hasClass('left')) {
                this.leftCalendar.month.add('month', 1);
            } else {
                this.rightCalendar.month.add('month', 1);
            }
            this.updateCalendars();
        },

        enterDate: function (e) {

            var title = $(e.target).attr('data-title');
            var row = title.substr(1, 1);
            var col = title.substr(3, 1);
            var cal = $(e.target).parents('.calendar');

            if (cal.hasClass('left')) {
                this.container.find('input[name=daterangepicker_start]').val(this.leftCalendar.calendar[row][col].format(this.format));
            } else {
                this.container.find('input[name=daterangepicker_end]').val(this.rightCalendar.calendar[row][col].format(this.format));
            }

        },

        clickDate: function (e) {
            var title = $(e.target).attr('data-title');
            var row = title.substr(1, 1);
            var col = title.substr(3, 1);
            var cal = $(e.target).parents('.calendar');

            if (cal.hasClass('left')) {
                var startDate = this.leftCalendar.calendar[row][col];
                var endDate = this.endDate;
                if (typeof this.dateLimit == 'object') {
                    var maxDate = moment(startDate).add(this.dateLimit).startOf('day');
                    if (endDate.isAfter(maxDate)) {
                        endDate = maxDate;
                    }
                }
            } else {
                var startDate = this.startDate;
                var endDate = this.rightCalendar.calendar[row][col];
                if (typeof this.dateLimit == 'object') {
                    var minDate = moment(endDate).subtract(this.dateLimit).startOf('day');
                    if (startDate.isBefore(minDate)) {
                        startDate = minDate;
                    }
                }
            }

            cal.find('td').removeClass('active');

            if (startDate.isSame(endDate) || startDate.isBefore(endDate)) {
                $(e.target).addClass('active');
                this.startDate = startDate;
                this.endDate = endDate;
            } else if (startDate.isAfter(endDate)) {
                $(e.target).addClass('active');
                this.startDate = startDate;
                this.endDate = moment(startDate).add('day', 1).startOf('day');
            }

            this.leftCalendar.month.month(this.startDate.month()).year(this.startDate.year());
            this.rightCalendar.month.month(this.endDate.month()).year(this.endDate.year());
            this.updateCalendars();
        },

        clickApply: function (e) {
            this.updateInputText();
            this.hide();
        },

        clickCancel: function (e) {
            this.startDate = this.oldStartDate;
            this.endDate = this.oldEndDate;
            this.updateView();
            this.updateCalendars();
            this.hide();
        },

        updateMonthYear: function (e) {

            var isLeft = $(e.target).closest('.calendar').hasClass('left');
            var cal = this.container.find('.calendar.left');
            if (!isLeft)
                cal = this.container.find('.calendar.right');

            // Month must be Number for new moment versions
            var month = parseInt(cal.find('.monthselect').val(), 10);
            var year = cal.find('.yearselect').val();

            if (isLeft) {
                this.leftCalendar.month.month(month).year(year);
            } else {
                this.rightCalendar.month.month(month).year(year);
            }

            this.updateCalendars();

        },

        updateTime: function(e) {

            var isLeft = $(e.target).closest('.calendar').hasClass('left');
            var cal = this.container.find('.calendar.left');
            if (!isLeft)
                cal = this.container.find('.calendar.right');

            var hour = parseInt(cal.find('.hourselect').val());
            var minute = parseInt(cal.find('.minuteselect').val());

            if (this.timePicker12Hour) {
                var ampm = cal.find('.ampmselect').val();
                if (ampm == 'PM' && hour < 12)
                    hour += 12;
                if (ampm == 'AM' && hour == 12)
                    hour = 0;
            }

            if (isLeft) {
                var start = this.startDate.clone();
                start.hour(hour);
                start.minute(minute);
                this.startDate = start;
                this.leftCalendar.month.hour(hour).minute(minute);
            } else {
                var end = this.endDate.clone();
                end.hour(hour);
                end.minute(minute);
                this.endDate = end;
                this.rightCalendar.month.hour(hour).minute(minute);
            }

            this.updateCalendars();
			this.updateFormInputs();

        },

        updateCalendars: function () {
            this.leftCalendar.calendar = this.buildCalendar(this.leftCalendar.month.month(), this.leftCalendar.month.year(), this.leftCalendar.month.hour(), this.leftCalendar.month.minute(), 'left');
            this.rightCalendar.calendar = this.buildCalendar(this.rightCalendar.month.month(), this.rightCalendar.month.year(), this.rightCalendar.month.hour(), this.rightCalendar.month.minute(), 'right');
            this.container.find('.calendar.left').html(this.renderCalendar(this.leftCalendar.calendar, this.startDate, this.minDate, this.maxDate));
            this.container.find('.calendar.right').html(this.renderCalendar(this.rightCalendar.calendar, this.endDate, this.startDate, this.maxDate));

            this.container.find('.ranges li').removeClass('active');
            var customRange = true;
            var i = 0;
            for (var range in this.ranges) {
                if (this.timePicker) {
                    if (this.startDate.isSame(this.ranges[range][0]) && this.endDate.isSame(this.ranges[range][1])) {
                        customRange = false;
                        this.container.find('.ranges li:eq(' + i + ')').addClass('active');
                    }
                } else {
                    // ignore times when comparing dates if time picker is not
					// enabled
                    if (this.startDate.format('YYYY-MM-DD') == this.ranges[range][0].format('YYYY-MM-DD') && this.endDate.format('YYYY-MM-DD') == this.ranges[range][1].format('YYYY-MM-DD')) {
                        customRange = false;
                        this.container.find('.ranges li:eq(' + i + ')').addClass('active');
                    }
                }
                i++;
            }
            if (customRange)
                this.container.find('.ranges li:last').addClass('active');
        },

        buildCalendar: function (month, year, hour, minute, side) {

            var firstDay = moment([year, month, 1]);
            var lastMonth = moment(firstDay).subtract('month', 1).month();
            var lastYear = moment(firstDay).subtract('month', 1).year();

            var daysInLastMonth = moment([lastYear, lastMonth]).daysInMonth();

            var dayOfWeek = firstDay.day();

            // initialize a 6 rows x 7 columns array for the calendar
            var calendar = [];
            for (var i = 0; i < 6; i++) {
                calendar[i] = [];
            }

            // populate the calendar with date objects
            var startDay = daysInLastMonth - dayOfWeek + this.locale.firstDay + 1;
            if (startDay > daysInLastMonth)
                startDay -= 7;

            if (dayOfWeek == this.locale.firstDay)
                startDay = daysInLastMonth - 6;

            var curDate = moment([lastYear, lastMonth, startDay, 12, minute]);
            for (var i = 0, col = 0, row = 0; i < 42; i++, col++, curDate = moment(curDate).add('hour', 24)) {
                if (i > 0 && col % 7 == 0) {
                    col = 0;
                    row++;
                }
                calendar[row][col] = curDate.clone().hour(hour);
                curDate.hour(12);
            }

            return calendar;

        },

        renderDropdowns: function (selected, minDate, maxDate) {
            var currentMonth = selected.month();
            var monthHtml = '<select class="monthselect">';
            var inMinYear = false;
            var inMaxYear = false;

            for (var m = 0; m < 12; m++) {
                if ((!inMinYear || m >= minDate.month()) && (!inMaxYear || m <= maxDate.month())) {
                    monthHtml += "<option value='" + m + "'" +
                        (m === currentMonth ? " selected='selected'" : "") +
                        ">" + this.locale.monthNames[m] + "</option>";
                }
            }
            monthHtml += "</select>";

            var currentYear = selected.year();
            var maxYear = (maxDate && maxDate.year()) || (currentYear + 5);
            var minYear = (minDate && minDate.year()) || (currentYear - 50);
            var yearHtml = '<select class="yearselect">';

            for (var y = minYear; y <= maxYear; y++) {
                yearHtml += '<option value="' + y + '"' +
                    (y === currentYear ? ' selected="selected"' : '') +
                    '>' + y + '</option>';
            }

            yearHtml += '</select>';

            return monthHtml + yearHtml;
        },

        renderCalendar: function (calendar, selected, minDate, maxDate) {

            var html = '<div class="calendar-date">';
            html += '<table class="table-condensed">';
            html += '<thead>';
            html += '<tr>';

            // add empty cell for week number
            if (this.showWeekNumbers)
                html += '<th></th>';

            if (!minDate || minDate.isBefore(calendar[1][1])) {
                html += '<th class="prev available"><i class="icon-arrow-left glyphicon glyphicon-arrow-left"></i></th>';
            } else {
                html += '<th></th>';
            }

            var dateHtml = this.locale.monthNames[calendar[1][1].month()] + calendar[1][1].format(" YYYY");

            if (this.showDropdowns) {
                dateHtml = this.renderDropdowns(calendar[1][1], minDate, maxDate);
            }

            html += '<th colspan="5" style="width: auto">' + dateHtml + '</th>';
            if (!maxDate || maxDate.isAfter(calendar[1][1])) {
                html += '<th class="next available"><i class="icon-arrow-right glyphicon glyphicon-arrow-right"></i></th>';
            } else {
                html += '<th></th>';
            }

            html += '</tr>';
            html += '<tr>';

            // add week number label
            if (this.showWeekNumbers)
                html += '<th class="week">' + this.locale.weekLabel + '</th>';

            $.each(this.locale.daysOfWeek, function (index, dayOfWeek) {
                html += '<th>' + dayOfWeek + '</th>';
            });

            html += '</tr>';
            html += '</thead>';
            html += '<tbody>';

            for (var row = 0; row < 6; row++) {
                html += '<tr>';

                // add week number
                if (this.showWeekNumbers)
                    html += '<td class="week">' + calendar[row][0].week() + '</td>';

                for (var col = 0; col < 7; col++) {
                    var cname = 'available ';
                    cname += (calendar[row][col].month() == calendar[1][1].month()) ? '' : 'off';

                    if ((minDate && calendar[row][col].isBefore(minDate)) || (maxDate && calendar[row][col].isAfter(maxDate))) {
                        cname = ' off disabled ';
                    } else if (calendar[row][col].format('YYYY-MM-DD') == selected.format('YYYY-MM-DD')) {
                        cname += ' active ';
                        if (calendar[row][col].format('YYYY-MM-DD') == this.startDate.format('YYYY-MM-DD')) {
                            cname += ' start-date ';
                        }
                        if (calendar[row][col].format('YYYY-MM-DD') == this.endDate.format('YYYY-MM-DD')) {
                            cname += ' end-date ';
                        }
                    } else if (calendar[row][col] >= this.startDate && calendar[row][col] <= this.endDate) {
                        cname += ' in-range ';
                        if (calendar[row][col].isSame(this.startDate)) { cname += ' start-date '; }
                        if (calendar[row][col].isSame(this.endDate)) { cname += ' end-date '; }
                    }

                    var title = 'r' + row + 'c' + col;
                    html += '<td class="' + cname.replace(/\s+/g, ' ').replace(/^\s?(.*?)\s?$/, '$1') + '" data-title="' + title + '">' + calendar[row][col].date() + '</td>';
                }
                html += '</tr>';
            }

            html += '</tbody>';
            html += '</table>';
            html += '</div>';

            if (this.timePicker) {

                html += '<div class="calendar-time">';
                html += '<select class="hourselect">';
                var start = 0;
                var end = 23;
                var selected_hour = selected.hour();
                if (this.timePicker12Hour) {
                    start = 1;
                    end = 12;
                    if (selected_hour >= 12)
                        selected_hour -= 12;
                    if (selected_hour == 0)
                        selected_hour = 12;
                }

                for (var i = start; i <= end; i++) {
                    if (i == selected_hour) {
                        html += '<option value="' + i + '" selected="selected">' + i + '</option>';
                    } else {
                        html += '<option value="' + i + '">' + i + '</option>';
                    }
                }

                html += '</select> : ';

                html += '<select class="minuteselect">';

                for (var i = 0; i < 60; i += this.timePickerIncrement) {
                    var num = i;
                    if (num < 10)
                        num = '0' + num;
                    if (i == selected.minute()) {
                        html += '<option value="' + i + '" selected="selected">' + num + '</option>';
                    } else {
                        html += '<option value="' + i + '">' + num + '</option>';
                    }
                }

                html += '</select> ';

                if (this.timePicker12Hour) {
                    html += '<select class="ampmselect">';
                    if (selected.hour() >= 12) {
                        html += '<option value="AM">AM</option><option value="PM" selected="selected">PM</option>';
                    } else {
                        html += '<option value="AM" selected="selected">AM</option><option value="PM">PM</option>';
                    }
                    html += '</select>';
                }

                html += '</div>';

            }

            return html;

        }

    };

    $.fn.daterangepicker = function (options, cb) {
        this.each(function () {
            var el = $(this);
            if (!el.data('daterangepicker'))
                el.data('daterangepicker', new DateRangePicker(el, options, cb));
        });
        return this;
    };

}(window.jQuery);

jQuery(function($){
	$.datepicker.regional['zh-CN'] = {
		closeText: '关闭',
		prevText: '&#x3C;上月',
		nextText: '下月&#x3E;',
		currentText: '今天',
		monthNames: ['一月','二月','三月','四月','五月','六月',
		'七月','八月','九月','十月','十一月','十二月'],
		monthNamesShort: ['一月','二月','三月','四月','五月','六月',
		'七月','八月','九月','十月','十一月','十二月'],
		dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],
		dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],
		dayNamesMin: ['日','一','二','三','四','五','六'],
		weekHeader: '周',
		dateFormat: 'yy-mm-dd',
		firstDay: 1,
		isRTL: false,
		showMonthAfterYear: true,
		yearSuffix: '年'};
	$.datepicker.setDefaults($.datepicker.regional['zh-CN']);
});

/*
 * (function(){
 * 
 * var oLanguage={ "oAria": { "sSortAscending": ": 升序排列", "sSortDescending": ":
 * 降序排列" }, "oPaginate": { "sFirst": "&laquo;", "sLast": "&raquo;", "sNext":
 * "&rsaquo;", "sPrevious": "&lsaquo;" }, "sEmptyTable": "没有相关记录", "sInfo": "
 * _START_ - _END_/_TOTAL_", "sInfoEmpty": "0-0/0 ", "sInfoFiltered": "",
 * "sInfoPostFix": "", "sDecimal": "", "sInfoThousands": "", "sLengthMenu":
 * "每页显示_MENU_", "sLoadingRecords": "正在载入...", "sProcessing": "正在载入...",
 * "sSearch": "", "sSearchPlaceholder": "", "sUrl": "", "sZeroRecords": "没有相关记录" }
 * 
 * jQuery.fn.dataTable.defaults.oLanguage=oLanguage; jQuery.extend(
 * jQuery.fn.dataTable.defaults, { "bSort": false } ); console.log("over");
 * })();
 */
(function(){
	
    var oLanguage={
        "oAria": {
            "sSortAscending": ": 升序排列",
            "sSortDescending": ": 降序排列"
        },
        "oPaginate": {
            "sFirst": "&laquo;",
            "sLast": "&raquo;",
            "sNext": "&rsaquo;",
            "sPrevious": "&lsaquo;"
        },
        "sEmptyTable": "没有相关记录",
        "sInfo": " _START_ - _END_/_TOTAL_",
        "sInfoEmpty": "0-0/0 ",
        "sInfoFiltered": "",
        "sInfoPostFix": "",
        "sDecimal": "",
        "sThousands": "",
        "sLengthMenu": "每页显示_MENU_",
        "sLoadingRecords": "正在载入...",
        "sProcessing": "正在载入...",
        "sSearch": "",
        "sSearchPlaceholder": "",
        "sUrl": "",
        "sZeroRecords": "没有相关记录"


    }
 
    jQuery.fn.dataTable.defaults.oLanguage=oLanguage;
    jQuery.extend( jQuery.fn.dataTable.defaults, {
        "bSort": false,
        "scrollX": true
    } );
    console.log("over");
})();
/*
 * jQuery.extend( jQuery.fn.dataTable.defaults, { "searching": false,
 * "ordering": false } );
 */

function readyCalendar(){
	/*
	 * initialize the calendar
	 * -----------------------------------------------------------------
	 */
   // Date for the calendar events (dummy data)
   var date = new Date();
   var d = date.getDate(),
           m = date.getMonth(),
           y = date.getFullYear();
   jQuery('#Calendar').fullCalendar({
       header: {
           left: 'prev,next today',
           center: 'title',
           right: 'month,agendaWeek,agendaDay'
       },
       buttonText: {// This is to add icons to the visible buttons
           prev: "<span class='icon-angle-left'></span>",
           next: "<span class='icon-angle-right'></span>",
           today: '今天',
           month: '月',
           week: '周',
           day: '日',
       },
       
       monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
       monthNamesShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
       dayNames: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
       dayNamesShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
       today: ["今天"],
       firstDay: 1,
       allDayText: '全天',
       // Random default events
       events: [
           {
               title: '四六级报名',
               start: new Date(2016, 2, 12),
               end: new Date(2016, 2, 19),
               backgroundColor: "#00c0ef", // Info (aqua)
               borderColor: "#00c0ef" // Info (aqua)
           },
           {
               title: '清明假期',
               start: new Date(2016, 3, 2),
               end: new Date(2016, 3, 4),
               backgroundColor: "#00a65a", // Success (green)
               borderColor: "#00a65a", // Success (green)
           },
           {
               title: '五一假期',
               start: new Date(2016, 3, 30),
               end: new Date(2016, 4, 2),
               backgroundColor: "#00a65a", // Success (green)
               borderColor: "#00a65a", // Success (green)
           },
           {
               title: '端午假期',
               start: new Date(2016, 5, 9),
               end: new Date(2016, 5, 11),
               backgroundColor: "#00a65a", // Success (green)
               borderColor: "#00a65a", // Success (green)
           },
           {
               title: '英语四级考试',
               start: new Date(2016, 5, 18, 9, 0),
               end: new Date(2016, 5, 18, 11, 20),
               allDay: false,
               backgroundColor: "#f56954", // red
               borderColor: "#f56954" // red
           },
           {
               title: '英语六级考试',
               start: new Date(2016, 5, 18, 15, 0),
               end: new Date(2016, 5, 18, 17, 25),
               allDay: false,
               backgroundColor: "#f56954", // red
               borderColor: "#f56954", // red
           },
           {
               title: '考试周',
               start: new Date(2016, 5, 27),
               end: new Date(2016, 6, 10),
               backgroundColor: "#f56954", // red
               borderColor: "#f56954", // red
           },
           {
               title: '中秋节',
               start: new Date(2016, 8, 15),
               end: new Date(2016, 8, 17),
               backgroundColor: "#00a65a", // Success (green)
               borderColor: "#00a65a", //  Success (green)
           },
           {
               title: '国庆节',
               start: new Date(2016, 9, 1),
               end: new Date(2016, 9, 7),
               backgroundColor: "#00a65a", // Success (green)
               borderColor: "#00a65a", //  Success (green)
           },
           {
               title: '四六级报名',
               start: new Date(2016, 8, 20),
               end: new Date(2016, 8, 25),
               backgroundColor: "#00c0ef", // Info (aqua)
               borderColor: "#00c0ef" // Info (aqua)
           },
           {
               title: '英语四级考试',
               start: new Date(2016, 11, 17, 9, 0),
               end: new Date(2016, 11, 17, 11, 20),
               allDay: false,
               backgroundColor: "#f56954", // red
               borderColor: "#f56954" // red
           },
           {
               title: '英语六级考试',
               start: new Date(2016, 11, 17, 15, 0),
               end: new Date(2016, 11, 17, 17, 25),
               allDay: false,
               backgroundColor: "#f56954", // red
               borderColor: "#f56954", // red
           },
           
       ],
       editable: false,
       droppable: false, // this allows things to be dropped onto the
							// calendar !!!
       drop: function(date, allDay) { // this function is called when
										// something is dropped

           // retrieve the dropped element's stored Event Object
           var originalEventObject = jQuery(this).data('eventObject');

           // we need to copy it, so that multiple events don't have a
			// reference to the same object
           var copiedEventObject = jQuery.extend({}, originalEventObject);

           // assign it the date that was reported
           copiedEventObject.start = date;
           copiedEventObject.allDay = allDay;
           copiedEventObject.backgroundColor = jQuery(this).css("background-color");
           copiedEventObject.borderColor = jQuery(this).css("border-color");

           // render the event on the calendar
           // the last `true` argument determines if the event "sticks"
			// (http://arshaw.com/fullcalendar/docs/event_rendering/renderEvent/)
           jQuery('#Calendar').fullCalendar('renderEvent', copiedEventObject, true);

           // is the "remove after drop" checkbox checked?
           if (jQuery('#drop-remove').is(':checked')) {
               // if so, remove the element from the "Draggable Events" list
               jQuery(this).remove();
           }

       }
   });
}

function readyView(){
	/*
	 * window.confirm = function(str){ return true ; }
	 */

	window.confirm = function(str){
		return true ;
	}

}

function readyCetApply(){
	// getLanguageTypes();
	
	jQuery('.CETinfoList select').change(function(){
		  jQuery('#submitButton').removeClass("disabled");
		  jQuery('#payButton').addClass("disabled");
	});
}
function getLanguageTypes(){
	var type = jQuery('.languageType > select').val();
	alert(type);
	jQuery.getJSON('/exam/portal/?methodToCall=getLanguageTypes', {
		ajax : 'true'
	}, function(data) {
		var html = '';
		var len = data.length;
		for ( var i = 0; i < len; i++) {
			if (data[i]=="大学英语") {
				html += '<option selected="selected" value="' + data[i] + '">'
					+ data[i] + '</option>';
			} else{
				html += '<option value="' + data[i] + '">'
					+ data[i] + '</option>';
			};
			
		}
		html += '</option>';
		jQuery('.languageType select').html(html);
	});
}

function getLanguageLevelByLanguageType(){
	jQuery.getJSON('/exam/portal/?methodToCall=getLanguageLevelByLanguageType', {
		languageType : encodeURI(jQuery('.languageType > select').val(),"utf-8"),
		ajax : 'true'
	}, function(data) {
		var html = '';
		var len = data.length;
		for ( var i = 0; i < len; i++) {
			html += '<option value="' + data[i] + '">'
					+ data[i]+ '</option>';
		}
		html += '</option>';

		jQuery('.languageLevel select').html(html);
	});
}

function checkImage(){
	var obj_file = document.getElementById("photoUploadFile_control");  
    var i=obj_file.value.lastIndexOf('.');
    var len=obj_file.value.length;
    var extEndName=obj_file.value.substring(i+1,len);
    var extName="JPG,JPEG";
	var maxsize = 200*1024;// 2M
    jQuery(".warnDialog").dialog(
    				{
    					modal: true,
    					autoOpen: false,
    					title: "警告：",
    					dialogClass: "no-close error_warn",
    					resizable: false,
    					draggable: false,
    					height:170,
    					width:400,
    					zIndex:1100,
  						buttons: [
    						{
      							text: "确定",
      							icons: {
        						primary: "ui-icon-heart"
      							},
      							click: function() {
        							jQuery( this ).dialog( "close" );
      							}
    						}
  						],
    					show: {
    						 effect: "blind",
    						 duration: 100
    						  },
    					hide: {
    						 effect: "blind",
    						 duration: 100
    						 }
    				}
    		);
    
    try{  
    	if(obj_file.value==""){  
            return false; 
        }  
    	else if(extName.indexOf(extEndName.toUpperCase())==-1){
    		jQuery( "#errWarnDialog_type" ).dialog( "open" );
    		/* showDialog('errWarnDialog_type'); */
			return false;
  		}
        
        var filesize = obj_file.files[0].size;  
        if(filesize>maxsize){  
        	jQuery( "#errWarnDialog_size" ).dialog( "open" );
        	/* showDialog('errWarnDialog_size'); */
            return false;
        } 
        return true;     
    }catch(e){  
        alert(e);  
    }  
}
function readyInvigilatorPresetTable(){
	var table =  jQuery('#InvigilatorPresetTable table').DataTable();	
	
	var searchHiddenList = jQuery('#InvigilatorPresetContainer > div > input');
	jQuery('#InvigilatorPresetTable table thead').append( '<tr><tr>' );
	
	var selectDeptHtml = document.getElementsByName("InvigilatordepartmentId")[0].innerHTML;//学院
	var selectCampusHtml = document.getElementsByName("InvigilatorcampusId")[0].innerHTML;//校区
	var selectLevelHtml = document.getElementsByName("InvigilatorexamLevelId")[0].innerHTML;//级别
	
	var htmlArr = new Array();
	htmlArr[0] = selectDeptHtml;
	htmlArr[1] = selectCampusHtml;
	htmlArr[2] = selectLevelHtml;
	
	jQuery('#InvigilatorPresetTable table thead tr:eq(0) th').each( function ( i ) {
		var thwidth = this.style.width;
		jQuery('#InvigilatorPresetTable table thead tr:eq(1)').append( '<th></th>' );
		var column = table.column( i );
		if(i > -1 && i < 3){
        	var select = jQuery('<select style="font-weight:normal;height:24px;" id="'+jQuery(searchHiddenList[i]).attr("name")+'">'+htmlArr[i]+'</select>')
			.appendTo( jQuery( '#InvigilatorPresetTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
			.on( 'change', function () {
				var val = jQuery("#"+jQuery(searchHiddenList[i]).attr("name")).find("option:selected").val();
				var obj = document.getElementsByName(jQuery(searchHiddenList[i]).attr("name"))[0];
				obj.value = val;
				console.info(table.page());
				if(table.page() > 0) {
					   table.page(0).draw(false);
				}
				jQuery("#InvigilatorPresetButton").click();
			} );
        	select.find("option[value="+jQuery(searchHiddenList[i]).val()+"]").attr("selected",true);
		}
		else if(i==3){
			var txt = jQuery('<input type="text" id="'+jQuery(searchHiddenList[i]).attr("name")+'" value="'+jQuery(searchHiddenList[i]).val()+'" style="font-weight:normal;width:80px;">')
			.appendTo( jQuery( '#InvigilatorPresetTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
			.on( {keyup: function () {
							var val = jQuery("#"+jQuery(searchHiddenList[i]).attr("name")).val().replace(/(^[\n\s]*)|([\n\s]*$)/g, "");
							var obj = document.getElementsByName(jQuery(searchHiddenList[i]).attr("name"))[0];
							obj.value = val;
						} ,
				  keydown: function(e){
					  		var key = e.which;
					  		if(key == 13 && document.activeElement.id == jQuery(searchHiddenList[i]).attr("name")){
					  			e.preventDefault();
					  			if(table.page() > 0) {
									   table.page(0).draw(false);
									}
					  			jQuery("#InvigilatorPresetButton").click();
					  		}
				  		}
				}); 
		}	
//		else if(i==1){
//			var select = jQuery('<select style="font-weight:normal;height:24px;" id="'+jQuery(searchHiddenList[i-2]).attr("name")+'">'
//					+'<option value selected ="selected"></option>'
//					+'<option value="1">A区</option>'
//					+'<option value="2">B区</option>'
//					+'<option value="4">D区</option>'
//					+'</select>')
//			.appendTo( jQuery( '#InvigilatorPresetTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
//			.on( 'change', function () {
//				var val = jQuery("#"+jQuery(searchHiddenList[i-2]).attr("name")).find("option:selected").val();
//				var obj = document.getElementsByName(jQuery(searchHiddenList[i-2]).attr("name"))[0];
//				obj.value = val;
//				if(table.page() > 0) {
//				   table.page(0).draw(false);
//				}
//	  				jQuery("#InvigilatorPresetButton").click();
//			} );
//        	select.find("option[value="+jQuery(searchHiddenList[i-2]).val()+"]").attr("selected",true);
//		}
//		else if(i==2){
//			var select = jQuery('<select style="font-weight:normal;height:24px;" id="'+jQuery(searchHiddenList[i-2]).attr("name")+'">'
//					+'<option value selected ="selected"></option>'
//					+'<option value="1">四级</option>'
//					+'<option value="2">六级</option>'
//					+'</select>')
//			.appendTo( jQuery( '#InvigilatorPresetTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
//			.on( 'change', function () {
//				var val = jQuery("#"+jQuery(searchHiddenList[i-2]).attr("name")).find("option:selected").val();
//				var obj = document.getElementsByName(jQuery(searchHiddenList[i-2]).attr("name"))[0];
//				obj.value = val;
//				if(table.page() > 0) {
//				   table.page(0).draw(false);
//				}
//	  				jQuery("#InvigilatorPresetButton").click();
//			} );
//        	select.find("option[value="+jQuery(searchHiddenList[i-2]).val()+"]").attr("selected",true);
//		}
	})
    /*
	 * jQuery("#InvigilatorPresetTable table .disabledInput >
	 * input").attr("disabled", "disabled");
	 */
}
function test(){
	jQuery("#InvigilatorPresetTable table .disabledInput > input").attr("disabled", "disabled");
}
function enabledEdit(id){
	jQuery("#"+id).parents("td").prev().find("input").removeAttr("disabled");
	jQuery("#"+id).css("display","none");
	jQuery("#"+id).next().css("display","block");
}
function saveEdit(id){
	jQuery("#"+id).parents("td").prev().find("input").attr("disabled","disabled");
	jQuery("#"+id).css("display","none");
	jQuery("#"+id).prev().css("display","block");
}

/*
 * function readySearchInvigilatorTable(){ var table =
 * jQuery('#SearchInvigilatorTable table').DataTable();
 * jQuery('#SearchInvigilatorTable table thead').append('<tr></tr>');
 * jQuery('#SearchInvigilatorTable table thead tr:eq(0) th').each( function ( i ) {
 * var thwidth = this.width; jQuery('#SearchInvigilatorTable table thead
 * tr:eq(1)').append( '<th></th>' ); var column = table.column( i );
 * if(i==1){ var select = jQuery('<select
 * style="font-weight:normal;width:130px;height:24px;"><option value=""></option></select>')
 * .appendTo( jQuery( '#SearchInvigilatorTable table thead tr:eq(1)
 * th:eq('+i+')' ) ) .on( 'change', function () {
 * javascript:document.getElementById('searchone').value=jQuery(this).find("option:selected").text().replace(/(^[\n\s]*)|([\n\s]*$)/g,
 * ""); var val =
 * jQuery(this).find("option:selected").text().replace(/(^[\n\s]*)|([\n\s]*$)/g,
 * ""); column.search(val).draw(); } ); var txt = jQuery('<input id="searchone"
 * type="text"
 * style="font-weight:normal;border:none;width:110px;height:22px;margin:1px 20px
 * 0px -129px;position:absolute;">') .appendTo( jQuery( '#SearchInvigilatorTable
 * table thead tr:eq(1) th:eq('+i+')' ) ).on( 'keyup', function () { var val =
 * jQuery(this).val().replace(/(^[\n\s]*)|([\n\s]*$)/g, ""); column.search( val ? '<span(.*)>(.*)'+val+'(.*)<\/(.*)span>' :
 * '', true, false).draw(); } ); var arr = new Array();
 * column.data().unique().sort().each( function ( d, j ) { var subs =
 * d.substring(d.indexOf('span'),d.lastIndexOf('span')); var start =
 * subs.indexOf('>'); var resultD = subs.substring(start+1,subs.length-2);
 * 
 * if(arr.indexOf(resultD)==-1){ arr.push(resultD); } } ); arr.sort(compare);
 * for(var i=0;i<arr.length;i++){ select.append( "<option
 * value='"+arr[i]+"'>"+arr[i]+"</option>" ) } } if(i==2 || i==4){ var txt =
 * jQuery('<input type="text" style="font-weight:normal">').appendTo( jQuery(
 * '#SearchInvigilatorTable table thead tr:eq(1) th:eq('+i+')' ).empty() ).on(
 * 'keyup', function () { var val =
 * jQuery(this).val().replace(/(^[\n\s]*)|([\n\s]*$)/g, ""); column.search( val ? '<span(.*)>(.*)'+val+'(.*)<\/(.*)span>' :
 * '', true, false).draw(); } ); var arr = new Array();
 * column.data().unique().sort().each( function ( d, j ) { var subs =
 * d.substring(d.indexOf('span'),d.lastIndexOf('span')); var start =
 * subs.indexOf('>'); var resultD = subs.substring(start+1,subs.length-2);
 * 
 * if(arr.indexOf(resultD)==-1){ arr.push(resultD); } } ); arr.sort(compare); }
 * if(i==3){ var select = jQuery('<select
 * style="font-weight:normal;height:24px;"><option value=""></option></select>')
 * .appendTo( jQuery( '#SearchInvigilatorTable table thead tr:eq(1)
 * th:eq('+i+')' ).empty() ) .on( 'change', function () { var val =
 * jQuery(this).find("option:selected").text().replace(/(^[\n\s]*)|([\n\s]*$)/g,
 * ""); column.search(val).draw(); } ); var arr = new Array();
 * column.data().unique().sort().each( function ( d, j ) { var subs =
 * d.substring(d.indexOf('span'),d.lastIndexOf('span')); var start =
 * subs.indexOf('>'); var resultD = subs.substring(start+1,subs.length-2);
 * 
 * if(arr.indexOf(resultD)==-1){ arr.push(resultD); } } ); arr.sort(compare);
 * for(var i=0;i<arr.length;i++){ select.append( "<option
 * value='"+arr[i]+"'>"+arr[i]+"</option>" ) } } } ); }
 */

function readyInvigilatorManagementTable(){

	var table =jQuery('#InvigilatorManagementTable table').DataTable();	
	
	var searchHiddenList = jQuery('#invigilatorManagementSearchContainer > div > input');
	jQuery('#InvigilatorManagementTable table thead').append( '<tr><tr>' );
	var selectGenderHtml = document.getElementsByName("invigilatorGender")[0].innerHTML;
	var selectBelongDeptHtml = document.getElementsByName("departmentId")[0].innerHTML;
	var selectDeptHtml;
	var userDeptId = document.querySelector("#userDeptId > p").innerHTML;
	if(userDeptId.trim() == "114819126"){
		selectDeptHtml = selectBelongDeptHtml;
	}
	else{
		selectDeptHtml = '<option selected="selected" value="'+userDeptId+'">'+document.querySelector("#userDeptName > p").innerHTML+'</option>';
		document.getElementsByName("searchdepartmentId")[0].value = userDeptId;
	}
	var selectCampusHtml = document.getElementsByName("campusId")[0].innerHTML;
	var selectExamLevelHtml = document.getElementsByName("examLevelId")[0].innerHTML;
	var htmlArr = new Array();
	htmlArr[3] = selectGenderHtml;
	htmlArr[6] = selectDeptHtml;
	htmlArr[7] = selectBelongDeptHtml;
	htmlArr[8] = selectCampusHtml;
	htmlArr[9] = selectExamLevelHtml;
	jQuery('#InvigilatorManagementTable table thead tr:eq(0) th').each( function ( i ) {
		var thwidth = this.style.width;
		jQuery('#InvigilatorManagementTable table thead tr:eq(1)').append( '<th></th>' );
		var column = table.column( i );
		if(i==2 || i==4  || i==5){
			var txt = jQuery('<input type="text" id="'+jQuery(searchHiddenList[i-2]).attr("name")+'" value="'+jQuery(searchHiddenList[i-2]).val()+'" style="font-weight:normal;width:80px;">')
			.appendTo( jQuery( '#InvigilatorManagementTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
			.on( {keyup: function () {
							var val = jQuery("#"+jQuery(searchHiddenList[i-2]).attr("name")).val().replace(/(^[\n\s]*)|([\n\s]*$)/g, "");
							var obj = document.getElementsByName(jQuery(searchHiddenList[i-2]).attr("name"))[0];
							obj.value = val;
						} ,
				  keydown: function(e){
					  		var key = e.which;
					  		if(key == 13 && document.activeElement.id == jQuery(searchHiddenList[i-2]).attr("name")){
					  			e.preventDefault();
					  			if(table.page() > 0) {
									   table.page(0).draw(false);
									}
					  			jQuery("#columnSearchButton").click();
					  		}
				  		}
				}); 
		}	
		else if(i==3 || ( i > 5 && i < 10)){
        	var select = jQuery('<select style="font-weight:normal;height:24px;" id="'+jQuery(searchHiddenList[i-2]).attr("name")+'">'+htmlArr[i]+'</select>')
			.appendTo( jQuery( '#InvigilatorManagementTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
			.on( 'change', function () {
				var val = jQuery("#"+jQuery(searchHiddenList[i-2]).attr("name")).find("option:selected").val();
				var obj = document.getElementsByName(jQuery(searchHiddenList[i-2]).attr("name"))[0];
				obj.value = val;
				console.info(table.page());
				if(table.page() > 0) {
					   table.page(0).draw(false);
				}
				jQuery("#columnSearchButton").click();
			} );
        	select.find("option[value="+jQuery(searchHiddenList[i-2]).val()+"]").attr("selected",true);
		}
		else if(i==10){
			var select = jQuery('<select style="font-weight:normal;height:24px;" id="'+jQuery(searchHiddenList[i-2]).attr("name")+'">'
					+'<option value selected ="selected"></option>'
					+'<option value="0">已申请</option>'
					+'<option value="1">已批准</option>'
					+'<option value="2">未批准</option>'
					+'</select>')
			.appendTo( jQuery( '#InvigilatorManagementTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
			.on( 'change', function () {
				var val = jQuery("#"+jQuery(searchHiddenList[i-2]).attr("name")).find("option:selected").val();
				var obj = document.getElementsByName(jQuery(searchHiddenList[i-2]).attr("name"))[0];
				obj.value = val;
				if(table.page() > 0) {
				   table.page(0).draw(false);
				}
	  				jQuery("#columnSearchButton").click();
			} );
        	select.find("option[value="+jQuery(searchHiddenList[i-2]).val()+"]").attr("selected",true);
		}
	})
	
}



/*
 * function readyInvigilatorManagementTable(){ var table
 * =jQuery('#InvigilatorManagementTable table').DataTable();
 * jQuery('#InvigilatorManagementTable table thead').append('<tr></tr>');
 * jQuery('#InvigilatorManagementTable table thead tr:eq(0) th').each( function (
 * i ) { var thwidth = this.width; jQuery('#InvigilatorManagementTable table
 * thead tr:eq(1)').append( '<th></th>' ); var column = table.column( i );
 * if(i==2 || i==4 || i==5){ var txt = jQuery('<input type="text"
 * style="font-weight:normal;width:115px;">').appendTo( jQuery(
 * '#InvigilatorManagementTable table thead tr:eq(1) th:eq('+i+')' ).empty()
 * ).on( 'keyup', function () { var val =
 * jQuery(this).val().replace(/(^[\n\s]*)|([\n\s]*$)/g, ""); column.search( val ? '<span(.*)>(.*)'+val+'(.*)<\/(.*)span>' :
 * '', true, false).draw(); localStorage.clear(); } ); var arr = new Array();
 * column.data().unique().sort().each( function ( d, j ) { var subs =
 * d.substring(d.indexOf('span'),d.lastIndexOf('span')); var start =
 * subs.indexOf('>'); var resultD = subs.substring(start+1,subs.length-2);
 * 
 * if(arr.indexOf(resultD)==-1){ arr.push(resultD); } } ); arr.sort(compare); }
 * if(i==3){ var select = jQuery('<select
 * style="font-weight:normal;height:24px;"><option value=""></option></select>')
 * .appendTo( jQuery( '#InvigilatorManagementTable table thead tr:eq(1)
 * th:eq('+i+')' ).empty() ) .on( 'change', function () { var val =
 * jQuery(this).find("option:selected").text().replace(/(^[\n\s]*)|([\n\s]*$)/g,
 * ""); column.search(val).draw(); localStorage.clear(); } ); var arr = new
 * Array(); column.data().unique().sort().each( function ( d, j ) { var subs =
 * d.substring(d.indexOf('span'),d.lastIndexOf('span')); var start =
 * subs.indexOf('>'); var resultD = subs.substring(start+1,subs.length-2);
 * 
 * if(arr.indexOf(resultD)==-1){ arr.push(resultD); } } ); arr.sort(compare);
 * for(var i=0;i<arr.length;i++){ select.append( "<option
 * value='"+arr[i]+"'>"+arr[i]+"</option>" ) } } if(i==6){ var select =
 * jQuery('<select style="font-weight:normal;width:130px;height:24px;"><option
 * value=""></option></select>') .appendTo( jQuery(
 * '#InvigilatorManagementTable table thead tr:eq(1) th:eq('+i+')' ) ) .on(
 * 'change', function () {
 * javascript:document.getElementById('managersix').value=jQuery(this).find("option:selected").text().replace(/(^[\n\s]*)|([\n\s]*$)/g,
 * ""); var val =
 * jQuery(this).find("option:selected").text().replace(/(^[\n\s]*)|([\n\s]*$)/g,
 * ""); column.search(val).draw(); localStorage.clear(); } ); var txt = jQuery('<input
 * id="managersix" type="text"
 * style="font-weight:normal;border:none;width:110px;height:22px;margin:1px 20px
 * 0px -129px;position:absolute;">') .appendTo( jQuery(
 * '#InvigilatorManagementTable table thead tr:eq(1) th:eq('+i+')' ) ).on(
 * 'keyup', function () { var val =
 * jQuery(this).val().replace(/(^[\n\s]*)|([\n\s]*$)/g, ""); column.search( val ? '<span(.*)>(.*)'+val+'(.*)<\/(.*)span>' :
 * '', true, false).draw(); localStorage.clear(); } ); var arr = new Array();
 * column.data().unique().sort().each( function ( d, j ) { var subs =
 * d.substring(d.indexOf('span'),d.lastIndexOf('span')); var start =
 * subs.indexOf('>'); var resultD = subs.substring(start+1,subs.length-2);
 * 
 * if(arr.indexOf(resultD)==-1){ arr.push(resultD); } } ); arr.sort(compare);
 * for(var i=0;i<arr.length;i++){ select.append( "<option
 * value='"+arr[i]+"'>"+arr[i]+"</option>" ) } } if(i==7){ var select =
 * jQuery('<select style="font-weight:normal;width:130px;height:24px;"><option
 * value=""></option></select>') .appendTo( jQuery(
 * '#InvigilatorManagementTable table thead tr:eq(1) th:eq('+i+')' ) ) .on(
 * 'change', function () {
 * javascript:document.getElementById('managerseven').value=jQuery(this).find("option:selected").text().replace(/(^[\n\s]*)|([\n\s]*$)/g,
 * ""); var val =
 * jQuery(this).find("option:selected").text().replace(/(^[\n\s]*)|([\n\s]*$)/g,
 * ""); column.search(val).draw(); localStorage.clear(); } ); var txt = jQuery('<input
 * id="managerseven" type="text"
 * style="font-weight:normal;border:none;width:110px;height:22px;margin:1px 20px
 * 0px -129px;position:absolute;">') .appendTo( jQuery(
 * '#InvigilatorManagementTable table thead tr:eq(1) th:eq('+i+')' ) ).on(
 * 'keyup', function () { var val =
 * jQuery(this).val().replace(/(^[\n\s]*)|([\n\s]*$)/g, ""); column.search( val ? '<span(.*)>(.*)'+val+'(.*)<\/(.*)span>' :
 * '', true, false).draw(); localStorage.clear(); } ); var arr = new Array();
 * column.data().unique().sort().each( function ( d, j ) { var subs =
 * d.substring(d.indexOf('span'),d.lastIndexOf('span')); var start =
 * subs.indexOf('>'); var resultD = subs.substring(start+1,subs.length-2);
 * 
 * if(arr.indexOf(resultD)==-1){ arr.push(resultD); } } ); arr.sort(compare);
 * for(var i=0;i<arr.length;i++){ select.append( "<option
 * value='"+arr[i]+"'>"+arr[i]+"</option>" ) } } if(i>7 && i<11){ var select =
 * jQuery('<select style="font-weight:normal;height:24px;"><option value=""></option></select>')
 * .appendTo( jQuery( '#InvigilatorManagementTable table thead tr:eq(1)
 * th:eq('+i+')' ).empty() ) .on( 'change', function () { var val =
 * jQuery(this).find("option:selected").text().replace(/(^[\n\s]*)|([\n\s]*$)/g,
 * ""); column.search(val).draw(); localStorage.clear(); } ); var arr = new
 * Array(); column.data().unique().sort().each( function ( d, j ) { var subs =
 * d.substring(d.indexOf('span'),d.lastIndexOf('span')); var start =
 * subs.indexOf('>'); var resultD = subs.substring(start+1,subs.length-2);
 * 
 * if(arr.indexOf(resultD)==-1){ arr.push(resultD); } } ); arr.sort(compare);
 * for(var i=0;i<arr.length;i++){ select.append( "<option
 * value='"+arr[i]+"'>"+arr[i]+"</option>" ) } } } ); }
 */

function readyUserRoleManagerTable(){
//	var table =jQuery('#UserRoleManagerTable table').DataTable({ordering:false,oLanguage:{sEmptyTable:"没有相关记录",
//        sInfo: " _START_ - _END_/_TOTAL_",
//        sInfoEmpty: "0-0/0 ",
//        sInfoFiltered: "",
//        sInfoPostFix: "",
//        sDecimal: "",
//        sInfoThousands: ",",
//        sLengthMenu: "每页显示_MENU_",
//        sLoadingRecords: "正在载入...",
//        sProcessing: "正在载入...",
//        sSearch: "",
//        sSearchPlaceholder: "",
//        sUrl: "",
//        sZeroRecords: "没有相关记录",
//        oPaginate: {
//            sFirst: "&laquo;",
//            sLast: "&raquo;",
//            sNext: "&rsaquo;",
//            sPrevious: "&lsaquo;"
//                    }
//                },
//        bSort:false,
//        sPaginationType:"full_numbers",
//        sDom: '<"top">rt<"bottom"ilp><"clear">'
//    });	
	var table =jQuery('#UserRoleManagerTable table').DataTable();	
	var searchHiddenList = jQuery('#UserRoleManagermentContainer > div > input');
	jQuery('#UserRoleManagerTable table thead').append( '<tr><tr>' );
	var selectDeptHtml=document.getElementsByName("URMsearchDepartmentId")[0].innerHTML;//学院	
	var htmlArr = new Array();
	htmlArr[1] = selectDeptHtml;
	jQuery('#UserRoleManagerTable table thead tr:eq(0) th').each( function ( i ) {
		var thwidth = this.style.width;
		jQuery('#UserRoleManagerTable table thead tr:eq(1)').append( '<th></th>' );
		var column = table.column( i );
		if(i==1){
        	var select = jQuery('<select style="font-weight:normal;height:24px;" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'">'+htmlArr[i]+'</select>')
			.appendTo( jQuery( '#UserRoleManagerTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
			.on( 'change', function () {
				var val = jQuery("#"+jQuery(searchHiddenList[i-1]).attr("name")).find("option:selected").val();
				var obj = document.getElementsByName(jQuery(searchHiddenList[i-1]).attr("name"))[0];
				obj.value = val;
				console.info(table.page());
				if(table.page() > 0) {
					   table.page(0).draw(false);
				}
				jQuery("#userRoleButton").click();
			} );
        	select.find("option[value="+jQuery(searchHiddenList[i-1]).val()+"]").attr("selected",true);
		}	
		else if(i==2 || i==4 || i==5){
			var txt = jQuery('<input type="text" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'" value="'+jQuery(searchHiddenList[i-1]).val()+'" style="font-weight:normal;width:80px;">')
			.appendTo( jQuery( '#UserRoleManagerTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
			.on( {keyup: function () {
							var val = jQuery("#"+jQuery(searchHiddenList[i-1]).attr("name")).val().replace(/(^[\n\s]*)|([\n\s]*$)/g, "");
							var obj = document.getElementsByName(jQuery(searchHiddenList[i-1]).attr("name"))[0];
							obj.value = val;
						} ,
				  keydown: function(e){
					  		var key = e.which;
					  		if(key == 13 && document.activeElement.id == jQuery(searchHiddenList[i-1]).attr("name")){
					  			e.preventDefault();
					  			if(table.page() > 0) {
									   table.page(0).draw(false);
									}
					  			jQuery("#userRoleButton").click();
					  		}
				  		}
				}); 
		}
		else if(i==3){
			var select = jQuery('<select style="font-weight:normal;height:24px;" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'">'
					+'<option value selected ="selected"></option>'
					+'<option value="2">女</option>'
					+'<option value="1">男</option>'
					+'</select>')
			.appendTo( jQuery( '#UserRoleManagerTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
			.on( 'change', function () {
				var val = jQuery("#"+jQuery(searchHiddenList[i-1]).attr("name")).find("option:selected").val();
				var obj = document.getElementsByName(jQuery(searchHiddenList[i-1]).attr("name"))[0];
				obj.value = val;
				if(table.page() > 0) {
				   table.page(0).draw(false);
				}
	  				jQuery("#userRoleButton").click();
			} );
        	select.find("option[value="+jQuery(searchHiddenList[i-1]).val()+"]").attr("selected",true);
		}
	})
	
}

function readyExamRoomTable(){
//	var table =  jQuery('#ExamRoomTable table').DataTable({ordering:false,oLanguage:{sEmptyTable:"没有相关记录",
//        sInfo: " _START_ - _END_/_TOTAL_",
//        sInfoEmpty: "0-0/0 ",
//        sInfoFiltered: "",
//        sInfoPostFix: "",
//        sDecimal: "",
//        sInfoThousands: ",",
//        sLengthMenu: "每页显示_MENU_",
//        sLoadingRecords: "正在载入...",
//        sProcessing: "正在载入...",
//        sSearch: "",
//        sSearchPlaceholder: "",
//        sUrl: "",
//        sZeroRecords: "没有相关记录",
//        oPaginate: {
//            sFirst: "&laquo;",
//            sLast: "&raquo;",
//            sNext: "&rsaquo;",
//            sPrevious: "&lsaquo;"
//                    }
//                },
//        bSort:false,
//        sPaginationType:"full_numbers",
//        sDom: '<"top">rt<"bottom"ilp><"clear">'
//    });	 
	var table =jQuery('#ExamRoomTable table').DataTable();	
	var searchHiddenList = jQuery('#ExmTimetableContainer > div > input');
	jQuery('#ExamRoomTable table thead').append( '<tr><tr>' );
	
	var selectLanguageTypeIdHtml=document.getElementsByName("TTExmlanguageType")[0].innerHTML;//语言类型
	var selectLanguageLevelIdHtml=document.getElementsByName("TTExmlanguageLevel")[0].innerHTML;//语言级别
	var selectCampusIdHtml=document.getElementsByName("TTExmcampusId")[0].innerHTML;//校区
	var selectRoomStrHtml=document.getElementsByName("TTExmroomStr")[0].innerHTML;//考场位置
	
	var htmlArr = new Array();
	htmlArr[1] = selectLanguageTypeIdHtml;
	htmlArr[2] = selectLanguageLevelIdHtml;
	htmlArr[3] = selectCampusIdHtml;
	htmlArr[11] = selectRoomStrHtml;
	
	jQuery('#ExamRoomTable table thead tr:eq(0) th').each( function ( i ) {
		var thwidth = this.style.width;
		jQuery('#ExamRoomTable table thead tr:eq(1)').append( '<th></th>' );
		var column = table.column( i );
		if(i==4 || i==6 || i==7 || i==9 || i==12){
			var txt = jQuery('<input type="text" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'" value="'+jQuery(searchHiddenList[i-1]).val()+'" style="font-weight:normal;width:80px;">')
			.appendTo( jQuery( '#ExamRoomTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
			.on( {keyup: function () {
							var val = jQuery("#"+jQuery(searchHiddenList[i-1]).attr("name")).val().replace(/(^[\n\s]*)|([\n\s]*$)/g, "");
							var obj = document.getElementsByName(jQuery(searchHiddenList[i-1]).attr("name"))[0];
							obj.value = val;
						} ,
				  keydown: function(e){
					  		var key = e.which;
					  		if(key == 13 && document.activeElement.id == jQuery(searchHiddenList[i-1]).attr("name")){
					  			e.preventDefault();
					  			if(table.page() > 0) {
									   table.page(0).draw(false);
									}
					  			jQuery("#TimetableButton").click();
					  		}
				  		}
				}); 
		}
		else if(i==5 || i==8){
			var select = jQuery('<select style="font-weight:normal;height:24px;" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'">'
					+'<option value selected ="selected"></option>'
					+'<option value="2">女</option>'
					+'<option value="1">男</option>'
					+'</select>')
			.appendTo( jQuery( '#ExamRoomTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
			.on( 'change', function () {
				var val = jQuery("#"+jQuery(searchHiddenList[i-1]).attr("name")).find("option:selected").val();
				var obj = document.getElementsByName(jQuery(searchHiddenList[i-1]).attr("name"))[0];
				obj.value = val;
				if(table.page() > 0) {
				   table.page(0).draw(false);
				}
	  				jQuery("#TimetableButton").click();
			} );
        	select.find("option[value="+jQuery(searchHiddenList[i-1]).val()+"]").attr("selected",true);
		}
		else if(i > 0 && i < 4){
        	var select = jQuery('<select style="font-weight:normal;height:24px;" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'">'+htmlArr[i]+'</select>')
			.appendTo( jQuery( '#ExamRoomTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
			.on( 'change', function () {
				var val = jQuery("#"+jQuery(searchHiddenList[i-1]).attr("name")).find("option:selected").val();
				var obj = document.getElementsByName(jQuery(searchHiddenList[i-1]).attr("name"))[0];
				obj.value = val;
				console.info(table.page());
				if(table.page() > 0) {
					   table.page(0).draw(false);
				}
				jQuery("#TimetableButton").click();
			} );
        	select.find("option[value="+jQuery(searchHiddenList[i-1]).val()+"]").attr("selected",true);
		}	
		if(i==10){
			var txt = jQuery('<input type="text" style="width:60px;" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'" value="'+jQuery(searchHiddenList[i-1]).val()+'" style="font-weight:normal;width:80px;">')
			.appendTo( jQuery( '#ExamRoomTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
			.on( {keyup: function () {
							var val = jQuery("#"+jQuery(searchHiddenList[i-1]).attr("name")).val().replace(/(^[\n\s]*)|([\n\s]*$)/g, "");
							var obj = document.getElementsByName(jQuery(searchHiddenList[i-1]).attr("name"))[0];
							obj.value = val;
						} ,
				  keydown: function(e){
					  		var key = e.which;
					  		if(key == 13 && document.activeElement.id == jQuery(searchHiddenList[i-1]).attr("name")){
					  			e.preventDefault();
					  			if(table.page() > 0) {
									   table.page(0).draw(false);
									}
					  			jQuery("#TimetableButton").click();
					  		}
				  		}
				}); 
		}	
		else if(i==11){
        	var select = jQuery('<select style="font-weight:normal;height:24px;width:120px;" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'">'+htmlArr[i]+'</select>')
			.appendTo( jQuery( '#ExamRoomTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
			.on( 'change', function () {
				var val = jQuery("#"+jQuery(searchHiddenList[i-1]).attr("name")).find("option:selected").val();
				var obj = document.getElementsByName(jQuery(searchHiddenList[i-1]).attr("name"))[0];
				obj.value = val;
				console.info(table.page());
				if(table.page() > 0) {
					   table.page(0).draw(false);
				}
				jQuery("#TimetableButton").click();
			} );
        	select.find("option[value="+jQuery(searchHiddenList[i-1]).val()+"]").attr("selected",true);
		}	
	})
}

function compare(val1,val2){
	return val1.localeCompare(val2);
}
/*
 * jQuery(function($){ var state = window.getComputedStyle(
 * document.querySelector('#PortalNavigation .sidebar-collapse'), ':before'
 * ).getPropertyValue('content'); if(state==="smallScreen"){ alert("small");
 * jQuery('#PortalNavigation').addClass('sidebar-collapsed');
 * jQuery('#PortalNavigation .sidebar-collapse').css("display","none") }
 * 
 * });
 */

/*
 * function toggleDisabled(){
 * 
 * var inputObj = jQuery("#ApplicantPhonenumberField > input");
 * if(!inputObj.attr("disabled") || inputObj.attr("disabled")==""){
 * inputObj.attr("disabled", "disabled") } else{
 * inputObj.removeAttr("disabled"); } }
 */


function readyApplicantPhonenumberField(){
	var editButton = jQuery("#editButton"); 
	var saveButton = jQuery("#saveButton"); 
	var inputObj = jQuery("#ApplicantPhonenumberField > input"); 
	if(inputObj.val()!=""){
		inputObj.attr("disabled", "disabled");
		editButton.css("display","block");
		saveButton.css("display","none");
	}
	else{
		saveButton.css("display","block");
		editButton.css("display","none");
	}
}

function enabled(){
	var editButton = jQuery("#editButton"); 
	var saveButton = jQuery("#saveButton"); 
	var inputObj = jQuery("#ApplicantPhonenumberField > input"); 
	if(inputObj.attr("disabled") || inputObj.attr("disabled")=="disabled"){
		inputObj.removeAttr("disabled"); 
		editButton.css("display","none");
		saveButton.css("display","block");
	}
}
function disabled(){
	var editButton = jQuery("#editButton"); 
	var saveButton = jQuery("#saveButton"); 
	jQuery("#ApplicantPhonenumberField > input").attr("disabled", "disabled");
	editButton.css("display","block");
	saveButton.css("display","none");
}

function validatePhoneNum(){
        var cellPhone=document.getElementById("ApplicantPhonenumberField_control");
        var RegCellPhone = /^([0-9]{11})?$/;
        var cellPhoneValue = cellPhone.value.trim();
        if(cellPhoneValue==''){
        	alert("请填写手机号！");
        }
        else{
        	var falg=cellPhoneValue.search(RegCellPhone);
            if (falg==-1){
              alert("手机号不合法！");
            }
            else{
            	return true;
            }
        }     
}

function invigilatorApplicationFormValidate(){
	var cet4 = document.getElementById("umdbnen_control").value;
	var cet6 = document.getElementById("umdbnfi_control").value;
	console.log(cet4+"6:"+cet6);
	var cellPhone=document.getElementById("ApplicantPhonenumberField_control");
    var RegCellPhone = /^([0-9]{11})?$/;
    var cellPhoneValue = cellPhone.value.trim();
    if(cellPhoneValue==''){
    	alert("请填写手机号！");
    }
    else if(cet4=="" && cet6==""){
    	alert("请选择考场校区！");
    }
    else{
    	var falg=cellPhoneValue.search(RegCellPhone);
        if (falg==-1){
          alert("手机号不合法！");
        }
        else{
        	return true;
        }
    }    
}

function invigilatorManagementFormValidate(){
	var invigilatorName = document.getElementsByName("invigilatorName")[0].value.trim();
	var invigilatorIdnumber = document.getElementsByName("invigilatorIdnumber")[0].value.trim();
	var departmentId = document.getElementsByName("departmentId")[0];
	var campusId = document.getElementsByName("campusId")[0].value.trim();
	var examLevelId = document.getElementsByName("examLevelId")[0].value.trim();
	var invigilatorPhonenumber = document.getElementsByName("invigilatorPhonenumber")[0].value.trim();

    var RegCellPhone = /^([0-9]{11})?$/;

    if(invigilatorName==='' || invigilatorIdnumber===''  || campusId==='' || examLevelId==='' ||invigilatorPhonenumber==='' ){
    	return false;
    }
    else if(departmentId){
    	if(departmentId.value.trim()===''){
    		return false;
    	}
    }
    var falg=invigilatorPhonenumber.search(RegCellPhone);
	if (falg==-1){
	   return false;
	}
	 else{
	   return true;
	}
	      
}

function deleteCookie(){
	localStorage.clear();
}

function readyExmLogTable(){
	/* jQuery('#examLogTable table').dataTable().fnDestroy(); */
//	jQuery('#examLogTable table').DataTable(
//	{
//		bRetrieve: true,
//		oLanguage:{sEmptyTable:"没有相关记录",
//			sInfo: " _START_ - _END_/_TOTAL_",
//			sInfoEmpty: "0-0/0 ",
//			sInfoFiltered: "",
//			sInfoPostFix: "",
//			sDecimal: "",
//			sThousands: "",
//			sLengthMenu: "每页显示_MENU_",
//			sLoadingRecords: "正在载入...",
//			sProcessing: "正在载入...",
//			sSearch: "",
//			sSearchPlaceholder: "",
//			sUrl: "",
//			sZeroRecords: "没有相关记录",
//			oPaginate: {
//				sFirst: "&laquo;",
//				sLast: "&raquo;",
//				sNext: "&rsaquo;",
//				sPrevious: "&lsaquo;"
//            }
//        },
//        bSort:false,
//        bRetrieve: true,
//        sPaginationType:"full_numbers",
//        sDom: '<"top">rt<"bottom"ilp><"clear">'
//    });	 
}
function readyCetDepartmentTable(){
	var table =jQuery('#CetDepartmentTable table').DataTable({ordering:false,oLanguage:{sEmptyTable:"没有相关记录",
        sInfo: " _START_ - _END_/_TOTAL_",
        sInfoEmpty: "0-0/0 ",
        sInfoFiltered: "",
        sInfoPostFix: "",
        sDecimal: "",
        sThousands: "",
        sLengthMenu: "每页显示_MENU_",
        sLoadingRecords: "正在载入...",
        sProcessing: "正在载入...",
        sSearch: "",
        sSearchPlaceholder: "",
        sUrl: "",
        sZeroRecords: "没有相关记录",
        oPaginate: {
            sFirst: "&laquo;",
            sLast: "&raquo;",
            sNext: "&rsaquo;",
            sPrevious: "&lsaquo;"
                    }
                },
        bSort:false,
        sPaginationType:"full_numbers",
        sDom: '<"top">rt<"bottom"ilp><"clear">'
    });	
    
}

function readyCetProgramTable(){
	var table =jQuery('#CetProgramTable table').DataTable({ordering:false,oLanguage:{sEmptyTable:"没有相关记录",
        sInfo: " _START_ - _END_/_TOTAL_",
        sInfoEmpty: "0-0/0 ",
        sInfoFiltered: "",
        sInfoPostFix: "",
        sDecimal: "",
        sThousands: "",
        sLengthMenu: "每页显示_MENU_",
        sLoadingRecords: "正在载入...",
        sProcessing: "正在载入...",
        sSearch: "",
        sSearchPlaceholder: "",
        sUrl: "",
        sZeroRecords: "没有相关记录",
        oPaginate: {
            sFirst: "&laquo;",
            sLast: "&raquo;",
            sNext: "&rsaquo;",
            sPrevious: "&lsaquo;"
                    }
                },
        bSort:false,
        sPaginationType:"full_numbers",
        sDom: '<"top">rt<"bottom"ilp><"clear">'
    });	
    
}
function readyPrintRoomPageTable(){
//	var table =jQuery('#PrintRoomPageTable table').DataTable({ordering:false,oLanguage:{sEmptyTable:"没有相关记录",
//        sInfo: " _START_ - _END_/_TOTAL_",
//        sInfoEmpty: "0-0/0 ",
//        sInfoFiltered: "",
//        sInfoPostFix: "",   
//        sDecimal: "",
//        sThousands: "",
//        sLengthMenu: "每页显示_MENU_",
//        sLoadingRecords: "正在载入...",
//        sProcessing: "正在载入...",
//        sSearch: "",
//        sSearchPlaceholder: "",
//        sUrl: "",
//        sZeroRecords: "没有相关记录",
//        oPaginate: {
//            sFirst: "&laquo;",
//            sLast: "&raquo;",
//            sNext: "&rsaquo;",
//            sPrevious: "&lsaquo;"
//                    }
//                },
//        bSort:false,
//        sPaginationType:"full_numbers",
//        sDom: '<"top">rt<"bottom"ilp><"clear">'
//    });	
	var table =jQuery('#PrintRoomPageTable table').DataTable();	
	var searchHiddenList = jQuery('#PrintRoomContainer > div > input');
	jQuery('#PrintRoomPageTable table thead').append( '<tr><tr>' );
	
	var selectLanguageTypeIdHtml=document.getElementsByName("ERPsearchLanguageTypeId")[0].innerHTML;//语言类型
	var selectLanguageLevelIdHtml=document.getElementsByName("ERPsearchLanguageLevelId")[0].innerHTML;//语言级别
	var selectCampusIdHtml=document.getElementsByName("ERPsearchCampusId")[0].innerHTML;//校区
	var selectRoomStrHtml=document.getElementsByName("ERPsearchRoomStr")[0].innerHTML;//考场位置
	
	var htmlArr = new Array();
	htmlArr[1] = selectLanguageTypeIdHtml;
	htmlArr[2] = selectLanguageLevelIdHtml;
	htmlArr[3] = selectCampusIdHtml;
	htmlArr[11] = selectRoomStrHtml;
	table.page(0).draw( 'page' );
	jQuery('#PrintRoomPageTable table thead tr:eq(0) th').each( function ( i ) {
		var thwidth = this.style.width;
		jQuery('#PrintRoomPageTable table thead tr:eq(1)').append( '<th></th>' );
		var column = table.column( i );
		if(i==4 || i==6 || i==7 || i==9|| i==12){
			var txt = jQuery('<input type="text" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'" value="'+jQuery(searchHiddenList[i-1]).val()+'" style="font-weight:normal;width:80px;">')
			.appendTo( jQuery( '#PrintRoomPageTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
			.on( {keyup: function () {
							var val = jQuery("#"+jQuery(searchHiddenList[i-1]).attr("name")).val().replace(/(^[\n\s]*)|([\n\s]*$)/g, "");
							var obj = document.getElementsByName(jQuery(searchHiddenList[i-1]).attr("name"))[0];
							obj.value = val;
						} ,
				  keydown: function(e){
					  		var key = e.which;
					  		if(key == 13 && document.activeElement.id == jQuery(searchHiddenList[i-1]).attr("name")){
					  			e.preventDefault();
					  			//table.page(0).draw( 'page' );
					  		 	if(table.page() > 0) {
									    table.page(0).draw(false);
									 }
					  			jQuery("#printRoomButton").click();
					  		}
				  		}
				}); 
		}
		else if(i==5 || i==8){
			var select = jQuery('<select style="font-weight:normal;height:24px;" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'">'
					+'<option value selected ="selected"></option>'
					+'<option value="2">女</option>'
					+'<option value="1">男</option>'
					+'</select>')
			.appendTo( jQuery( '#PrintRoomPageTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
			.on( 'change', function () {
				var val = jQuery("#"+jQuery(searchHiddenList[i-1]).attr("name")).find("option:selected").val();
				var obj = document.getElementsByName(jQuery(searchHiddenList[i-1]).attr("name"))[0];
				obj.value = val;
				//table.page(0).draw( 'page' );
				 if(table.page() > 0) {
				    table.page(0).draw(false);
				 }
	  				jQuery("#printRoomButton").click();
			} );
        	select.find("option[value="+jQuery(searchHiddenList[i-1]).val()+"]").attr("selected",true);
		}
		else if(i > 0 && i < 4){
        	var select = jQuery('<select style="font-weight:normal;height:24px;" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'">'+htmlArr[i]+'</select>')
			.appendTo( jQuery( '#PrintRoomPageTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
			.on( 'change', function () {
				var val = jQuery("#"+jQuery(searchHiddenList[i-1]).attr("name")).find("option:selected").val();
				var obj = document.getElementsByName(jQuery(searchHiddenList[i-1]).attr("name"))[0];
				obj.value = val;
				//table.page(0).draw( 'page' );
				console.info(table.page());
				 if(table.page() > 0) {
				 	   table.page(0).draw(false);
				 }
				jQuery("#printRoomButton").click();
			} );
        	select.find("option[value="+jQuery(searchHiddenList[i-1]).val()+"]").attr("selected",true);
		}	
		if(i==10){
			var txt = jQuery('<input type="text" style="width:60px;" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'" value="'+jQuery(searchHiddenList[i-1]).val()+'" style="font-weight:normal;width:80px;">')
			.appendTo( jQuery( '#PrintRoomPageTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
			.on( {keyup: function () {
							var val = jQuery("#"+jQuery(searchHiddenList[i-1]).attr("name")).val().replace(/(^[\n\s]*)|([\n\s]*$)/g, "");
							var obj = document.getElementsByName(jQuery(searchHiddenList[i-1]).attr("name"))[0];
							obj.value = val;
						} ,
				  keydown: function(e){
					  		var key = e.which;
					  		if(key == 13 && document.activeElement.id == jQuery(searchHiddenList[i-1]).attr("name")){
					  			e.preventDefault();
					  			//table.page(0).draw( 'page' );
					  		 	if(table.page() > 0) {
									    table.page(0).draw(false);
									 }
					  			jQuery("#printRoomButton").click();
					  		}
				  		}
				}); 
		}	
		else if(i==11){
        	var select = jQuery('<select style="font-weight:normal;height:24px;width:120px;" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'">'+htmlArr[i]+'</select>')
			.appendTo( jQuery( '#PrintRoomPageTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
			.on( 'change', function () {
				var val = jQuery("#"+jQuery(searchHiddenList[i-1]).attr("name")).find("option:selected").val();
				var obj = document.getElementsByName(jQuery(searchHiddenList[i-1]).attr("name"))[0];
				obj.value = val;
				//table.page(0).draw( 'page' );
				console.info(table.page());
				 if(table.page() > 0) {
				 	   table.page(0).draw(false);
				 }
				jQuery("#printRoomButton").click();
			} );
        	select.find("option[value="+jQuery(searchHiddenList[i-1]).val()+"]").attr("selected",true);
		}	
	})
    
}
function readyMessageTable(){
//	var table =jQuery('#MessageTable table').DataTable({ordering:false,oLanguage:{sEmptyTable:"没有相关记录",
//        sInfo: " _START_ - _END_/_TOTAL_",
//        sInfoEmpty: "0-0/0 ",
//        sInfoFiltered: "",
//        sInfoPostFix: "",
//        sDecimal: "",
//        sThousands: "",
//        sLengthMenu: "每页显示_MENU_",
//        sLoadingRecords: "正在载入...",
//        sProcessing: "正在载入...",
//        sSearch: "",
//        sSearchPlaceholder: "",
//        sUrl: "",
//        sZeroRecords: "没有相关记录",
//        oPaginate: {
//            sFirst: "&laquo;",
//            sLast: "&raquo;",
//            sNext: "&rsaquo;",
//            sPrevious: "&lsaquo;"
//                    }
//                },
//        bSort:false,
//        sPaginationType:"full_numbers",
//        sDom: '<"top">rt<"bottom"ilp><"clear">'
//    });	
	var table =jQuery('#MessageTable table').DataTable();	
	var searchHiddenList = jQuery('#MessageContainer > div > input');
	jQuery('#MessageTable table thead').append( '<tr><tr>' );
	
	var selectDeptHtml=document.getElementsByName("MPdepartmentIds")[0].innerHTML;//管理学院
	var selectbelongDeptHtml=document.getElementsByName("MPdepartmentIds")[0].innerHTML;//所属学院
	
	var htmlArr = new Array();
	htmlArr[6] = selectDeptHtml;
	htmlArr[7] = selectbelongDeptHtml;
	
	jQuery('#MessageTable table thead tr:eq(0) th').each( function ( i ) {
		var thwidth = this.style.width;
		jQuery('#MessageTable table thead tr:eq(1)').append( '<th></th>' );
		var column = table.column( i );
		if(i==2 || i==4 || i==5){
			var txt = jQuery('<input type="text" id="'+jQuery(searchHiddenList[i-2]).attr("name")+'" value="'+jQuery(searchHiddenList[i-2]).val()+'" style="font-weight:normal;width:80px;">')
			.appendTo( jQuery( '#MessageTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
			.on( {keyup: function () {
							var val = jQuery("#"+jQuery(searchHiddenList[i-2]).attr("name")).val().replace(/(^[\n\s]*)|([\n\s]*$)/g, "");
							var obj = document.getElementsByName(jQuery(searchHiddenList[i-2]).attr("name"))[0];
							obj.value = val;
						} ,
				  keydown: function(e){
					  		var key = e.which;
					  		if(key == 13 && document.activeElement.id == jQuery(searchHiddenList[i-2]).attr("name")){
					  			e.preventDefault();
					  		 	if(table.page() > 0) {
									    table.page(0).draw(false);
									 }
					  			jQuery("#messagePageButton").click();
					  		}
				  		}
				}); 
		}
		else if(i==3){
			var select = jQuery('<select style="font-weight:normal;height:24px;" id="'+jQuery(searchHiddenList[i-2]).attr("name")+'">'
					+'<option value selected ="selected"></option>'
					+'<option value="2">女</option>'
					+'<option value="1">男</option>'
					+'</select>')
			.appendTo( jQuery( '#MessageTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
			.on( 'change', function () {
				var val = jQuery("#"+jQuery(searchHiddenList[i-2]).attr("name")).find("option:selected").val();
				var obj = document.getElementsByName(jQuery(searchHiddenList[i-2]).attr("name"))[0];
				obj.value = val;
				 if(table.page() > 0) {
				    table.page(0).draw(false);
				 }
	  				jQuery("#messagePageButton").click();
			} );
        	select.find("option[value="+jQuery(searchHiddenList[i-2]).val()+"]").attr("selected",true);
		}
		else if( i > 5 && i < 8){
        	var select = jQuery('<select style="font-weight:normal;height:24px;" id="'+jQuery(searchHiddenList[i-2]).attr("name")+'">'+htmlArr[i]+'</select>')
			.appendTo( jQuery( '#MessageTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
			.on( 'change', function () {
				var val = jQuery("#"+jQuery(searchHiddenList[i-2]).attr("name")).find("option:selected").val();
				var obj = document.getElementsByName(jQuery(searchHiddenList[i-2]).attr("name"))[0];
				obj.value = val;
				console.info(table.page());
				 if(table.page() > 0) {
				 	   table.page(0).draw(false);
				 }
				jQuery("#messagePageButton").click();
			} );
        	select.find("option[value="+jQuery(searchHiddenList[i-2]).val()+"]").attr("selected",true);
		}	
		else if(i==8){
			var select = jQuery('<select style="font-weight:normal;height:24px;" id="'+jQuery(searchHiddenList[i-2]).attr("name")+'">'
					+'<option value selected ="selected"></option>'
					+'<option value="1">A区</option>'
					+'<option value="2">B区</option>'
					+'<option value="4">D区</option>'
					+'</select>')
			.appendTo( jQuery( '#MessageTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
			.on( 'change', function () {
				var val = jQuery("#"+jQuery(searchHiddenList[i-2]).attr("name")).find("option:selected").val();
				var obj = document.getElementsByName(jQuery(searchHiddenList[i-2]).attr("name"))[0];
				obj.value = val;
				 if(table.page() > 0) {
				    table.page(0).draw(false);
				 }
	  				jQuery("#messagePageButton").click();
			} );
        	select.find("option[value="+jQuery(searchHiddenList[i-2]).val()+"]").attr("selected",true);
		}
		else if(i==9){
			var select = jQuery('<select style="font-weight:normal;height:24px;" id="'+jQuery(searchHiddenList[i-2]).attr("name")+'">'
					+'<option value selected ="selected"></option>'
					+'<option value="1">四级</option>'
					+'<option value="2">六级</option>'
					+'</select>')
			.appendTo( jQuery( '#MessageTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
			.on( 'change', function () {
				var val = jQuery("#"+jQuery(searchHiddenList[i-2]).attr("name")).find("option:selected").val();
				var obj = document.getElementsByName(jQuery(searchHiddenList[i-2]).attr("name"))[0];
				obj.value = val;
				 if(table.page() > 0) {
				    table.page(0).draw(false);
				 }
	  				jQuery("#messagePageButton").click();
			} );
        	select.find("option[value="+jQuery(searchHiddenList[i-2]).val()+"]").attr("selected",true);
		}
		else if(i==10){
			var select = jQuery('<select style="font-weight:normal;height:24px;" id="'+jQuery(searchHiddenList[i-2]).attr("name")+'">'
					+'<option value selected ="selected"></option>'
					+'<option value="0">已申请</option>'
					+'<option value="1">已批准</option>'
					+'<option value="2">未批准</option>'
					+'</select>')
			.appendTo( jQuery( '#MessageTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
			.on( 'change', function () {
				var val = jQuery("#"+jQuery(searchHiddenList[i-2]).attr("name")).find("option:selected").val();
				var obj = document.getElementsByName(jQuery(searchHiddenList[i-2]).attr("name"))[0];
				obj.value = val;
				 if(table.page() > 0) {
				    table.page(0).draw(false);
				 }
	  				jQuery("#messagePageButton").click();
			} );
        	select.find("option[value="+jQuery(searchHiddenList[i-2]).val()+"]").attr("selected",true);
		}
	})
}

function readyExmLogTable(){
	var table =jQuery('#ExmLogTable table').DataTable({ordering:false,oLanguage:{sEmptyTable:"没有相关记录",
        sInfo: " _START_ - _END_/_TOTAL_",
        sInfoEmpty: "0-0/0 ",
        sInfoFiltered: "",
        sInfoPostFix: "",
        sDecimal: "",
        sThousands: "",
        sLengthMenu: "每页显示_MENU_",
        sLoadingRecords: "正在载入...",
        sProcessing: "正在载入...",
        sSearch: "",
        sSearchPlaceholder: "",
        sUrl: "",
        sZeroRecords: "没有相关记录",
        oPaginate: {
            sFirst: "&laquo;",
            sLast: "&raquo;",
            sNext: "&rsaquo;",
            sPrevious: "&lsaquo;"
                    }
                },
        aoColumnDefs: [  { "sWidth": "18%", "aTargets": [0] },
                         { "sWidth": "13%", "aTargets": [1] },
                         { "sWidth": "18%", "aTargets": [2] },
                         { "sWidth": "13%", "aTargets": [3] },
                         { "sWidth": "17%", "aTargets": [4] },
                         { "sWidth": "10%", "aTargets": [5] }],
        bSort:false,
        sPaginationType:"full_numbers",
        sDom: '<"top">rt<"bottom"ilp><"clear">'
    });	
    
}

function readySignCheckTable(){
//	var table =jQuery('#SignCheckTable table').DataTable({ordering:false,oLanguage:{sEmptyTable:"没有相关记录",
//        sInfo: " _START_ - _END_/_TOTAL_",
//        sInfoEmpty: "0-0/0 ",
//        sInfoFiltered: "",
//        sInfoPostFix: "",
//        sDecimal: "",
//        sThousands: "",
//        sLengthMenu: "每页显示_MENU_",
//        sLoadingRecords: "正在载入...",
//        sProcessing: "正在载入...",
//        sSearch: "",
//        sSearchPlaceholder: "",
//        sUrl: "",
//        sZeroRecords: "没有相关记录",
//        oPaginate: {
//            sFirst: "&laquo;",
//            sLast: "&raquo;",
//            sNext: "&rsaquo;",
//            sPrevious: "&lsaquo;"
//                    }
//                },
//       bSort:false,
//       bRetrieve:true,
//        sPaginationType:"full_numbers",
//        sDom: '<"top">rt<"bottom"ilp><"clear">'
//   });	
	var table =jQuery('#SignCheckTable table').DataTable();	
	var searchHiddenList = jQuery('#SignCheckContainer > div > input');
	jQuery('#SignCheckTable table thead').append( '<tr><tr>' );
	
	var selectDeptHtml=document.getElementsByName("MPdepartmentIds")[0].innerHTML;//学院
	var selectbelongDeptHtml=document.getElementsByName("MPdepartmentIds")[0].innerHTML;//所属学院
	
	var htmlArr = new Array();
	htmlArr[5] = selectDeptHtml;
	htmlArr[6] = selectbelongDeptHtml;
	jQuery('#SignCheckTable table thead tr:eq(0) th').each( function ( i ) {
		var thwidth = this.style.width;
		jQuery('#SignCheckTable table thead tr:eq(1)').append( '<th></th>' );
		var column = table.column( i );
		if(i==1 || i==3 || i==4){
			var txt = jQuery('<input type="text" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'" value="'+jQuery(searchHiddenList[i-1]).val()+'" style="font-weight:normal;width:80px;">')
			.appendTo( jQuery( '#SignCheckTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
			.on( {keyup: function () {
							var val = jQuery("#"+jQuery(searchHiddenList[i-1]).attr("name")).val().replace(/(^[\n\s]*)|([\n\s]*$)/g, "");
							var obj = document.getElementsByName(jQuery(searchHiddenList[i-1]).attr("name"))[0];
							obj.value = val;
						} ,
				  keydown: function(e){
					  		var key = e.which;
					  		if(key == 13 && document.activeElement.id == jQuery(searchHiddenList[i-1]).attr("name")){
					  			e.preventDefault();
					  			if(table.page() > 0) {
									   table.page(0).draw(false);
									}
					  			jQuery("#signCheckButton").click();
					  		}
				  		}
				}); 
		}
		else if(i==2){
			var select = jQuery('<select style="font-weight:normal;height:24px;" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'">'
					+'<option value selected ="selected"></option>'
					+'<option value="2">女</option>'
					+'<option value="1">男</option>'
					+'</select>')
			.appendTo( jQuery( '#SignCheckTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
			.on( 'change', function () {
				var val = jQuery("#"+jQuery(searchHiddenList[i-1]).attr("name")).find("option:selected").val();
				var obj = document.getElementsByName(jQuery(searchHiddenList[i-1]).attr("name"))[0];
				obj.value = val;
				if(table.page() > 0) {
				   table.page(0).draw(false);
				}
	  				jQuery("#signCheckButton").click();
			} );
        	select.find("option[value="+jQuery(searchHiddenList[i-1]).val()+"]").attr("selected",true);
		}
		else if(i==7){
			var select = jQuery('<select style="font-weight:normal;height:24px;" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'">'
					+'<option value selected ="selected"></option>'
					+'<option value="1">A区</option>'
					+'<option value="2">B区</option>'
                    +'<option value="4">D区</option>'
					+'</select>')
			.appendTo( jQuery( '#SignCheckTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
			.on( 'change', function () {
				var val = jQuery("#"+jQuery(searchHiddenList[i-1]).attr("name")).find("option:selected").val();
				var obj = document.getElementsByName(jQuery(searchHiddenList[i-1]).attr("name"))[0];
				obj.value = val;
				if(table.page() > 0) {
				   table.page(0).draw(false);
				}
	  				jQuery("#signCheckButton").click();
			} );
       	select.find("option[value="+jQuery(searchHiddenList[i-1]).val()+"]").attr("selected",true);
		}
		else if( i > 4 && i < 7){
        	var select = jQuery('<select style="font-weight:normal;height:24px;" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'">'+htmlArr[i]+'</select>')
			.appendTo( jQuery( '#SignCheckTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
			.on( 'change', function () {
				var val = jQuery("#"+jQuery(searchHiddenList[i-1]).attr("name")).find("option:selected").val();
				var obj = document.getElementsByName(jQuery(searchHiddenList[i-1]).attr("name"))[0];
				obj.value = val;
				console.info(table.page());
				if(table.page() > 0) {
					   table.page(0).draw(false);
				}
				jQuery("#signCheckButton").click();
			} );
        	select.find("option[value="+jQuery(searchHiddenList[i-1]).val()+"]").attr("selected",true);
		}	
		else if(i==8){
			var select = jQuery('<select style="font-weight:normal;height:24px;" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'">'
					+'<option value selected ="selected"></option>'
					+'<option value="1">四级</option>'
					+'<option value="2">六级</option>'
					+'</select>')
			.appendTo( jQuery( '#SignCheckTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
			.on( 'change', function () {
				var val = jQuery("#"+jQuery(searchHiddenList[i-1]).attr("name")).find("option:selected").val();
				var obj = document.getElementsByName(jQuery(searchHiddenList[i-1]).attr("name"))[0];
				obj.value = val;
				if(table.page() > 0) {
				   table.page(0).draw(false);
				}
	  				jQuery("#signCheckButton").click();
			} );
       	select.find("option[value="+jQuery(searchHiddenList[i-1]).val()+"]").attr("selected",true);
		}
		else if(i==9){
			var select = jQuery('<select style="font-weight:normal;height:24px;" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'">'
					+'<option value selected ="selected"></option>'
					+'<option value="1">已签到</option>'
					+'<option value="0">未签到</option>'
					+'</select>')
			.appendTo( jQuery( '#SignCheckTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
			.on( 'change', function () {
				var val = jQuery("#"+jQuery(searchHiddenList[i-1]).attr("name")).find("option:selected").val();
				var obj = document.getElementsByName(jQuery(searchHiddenList[i-1]).attr("name"))[0];
				obj.value = val;
				if(table.page() > 0) {
				   table.page(0).draw(false);
				}
	  				jQuery("#signCheckButton").click();
			} );
       	select.find("option[value="+jQuery(searchHiddenList[i-1]).val()+"]").attr("selected",true);
		}
	})
}

function clickButton(){
	jQuery("#exmStudentButton").click();
}

function readyExmStudentTable(){
//	var table =jQuery('#ExmStudentTable table').DataTable({ordering:false,oLanguage:{sEmptyTable:"没有相关记录",
//        sInfo: " _START_ - _END_/_TOTAL_",
//        sInfoEmpty: "0-0/0 ",
//        sInfoFiltered: "",
//        sInfoPostFix: "",
//        sDecimal: "",
//        sThousands: "",
//        sLengthMenu: "每页显示_MENU_",
//        sLoadingRecords: "正在载入...",
//        sProcessing: "正在载入...",
//        sSearch: "",
//        sSearchPlaceholder: "",
//        sUrl: "",
//        sZeroRecords: "没有相关记录",
//        oPaginate: {
//            sFirst: "&laquo;",
//            sLast: "&raquo;",
//            sNext: "&rsaquo;",
//            sPrevious: "&lsaquo;"
//                    }
//                },
//       bSort:false,
//       bRetrieve:true,
//       orderClasses:false,
//       sPaginationType:"full_numbers",
//       sDom: '<"top">rt<"bottom"ilp><"clear">'
//   });	
	
	var table =jQuery('#ExmStudentTable table').DataTable();	
	var searchHiddenList = jQuery('#ExmStudentContainer > div > input');
	jQuery('#ExmStudentTable table thead').append( '<tr><tr>' );
	
	var selectBelongDeptHtml=document.getElementsByName("ESPsearchDepartmentId")[0].innerHTML;//学院
	var selectDeptHtml;
	var userDeptId = document.querySelector("#userDeptId > p").innerHTML;
	if(userDeptId.trim() == "114819126"){
		selectDeptHtml = selectBelongDeptHtml;		
		document.getElementsByName("ESPsearchDepartmentId")[0].value = document.getElementsByName("exmStudentDepartmentId")[0].value;
	}
	else{
		selectDeptHtml = '<option selected="selected" value="'+userDeptId+'">'+document.querySelector("#userDeptName > p").innerHTML+'</option>';
		document.getElementsByName("exmStudentDepartmentId")[0].value = userDeptId;
		document.getElementsByName("ESPsearchDepartmentId")[0].value = userDeptId.trim();
		window.onload=clickButton;
	}
	var selectProgramHtml = document.getElementsByName("ESPsearchProgramId")[0].innerHTML;//专业
	var selectGradeHtml = document.getElementsByName("ESPsearchGradeId")[0].innerHTML;//年级
	var selectCampusHtml = document.getElementsByName("ESPsearchCampusId")[0].innerHTML;//校区
	
	var htmlArr = new Array();
	htmlArr[5] = selectDeptHtml;
	htmlArr[6] = selectProgramHtml;
	htmlArr[7] = selectGradeHtml;
	jQuery('#ExmStudentTable table thead tr:eq(0) th').each( function ( i ) {
		var thwidth = this.style.width;
		jQuery('#ExmStudentTable table thead tr:eq(1)').append( '<th></th>' );
		var column = table.column( i );
		if(( i > 0 && i < 4) || i==8){
			var txt = jQuery('<input type="text" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'" value="'+jQuery(searchHiddenList[i-1]).val()+'" style="font-weight:normal;width:80px;">')
			.appendTo( jQuery( '#ExmStudentTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
			.on( {keyup: function () {
							var val = jQuery("#"+jQuery(searchHiddenList[i-1]).attr("name")).val().replace(/(^[\n\s]*)|([\n\s]*$)/g, "");
							var obj = document.getElementsByName(jQuery(searchHiddenList[i-1]).attr("name"))[0];
							obj.value = val;
						} ,
				  keydown: function(e){
					  		var key = e.which;
					  		if(key == 13 && document.activeElement.id == jQuery(searchHiddenList[i-1]).attr("name")){
					  			e.preventDefault();
					  			if(table.page() > 0) {
									   table.page(0).draw(false);
									}
					  			jQuery("#exmStudentButton").click();
					  		}
				  		}
				}); 
		}
		else if(i==4){
			var select = jQuery('<select style="font-weight:normal;height:24px;" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'">'
					+'<option value selected ="selected"></option>'
					+'<option value="F">女</option>'
					+'<option value="M">男</option>'
					+'</select>')
			.appendTo( jQuery( '#ExmStudentTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
			.on( 'change', function () {
				var val = jQuery("#"+jQuery(searchHiddenList[i-1]).attr("name")).find("option:selected").val();
				var obj = document.getElementsByName(jQuery(searchHiddenList[i-1]).attr("name"))[0];
				obj.value = val;
				if(table.page() > 0) {
				   table.page(0).draw(false);
				}
	  				jQuery("#exmStudentButton").click();
			} );
        	select.find("option[value="+jQuery(searchHiddenList[i-1]).val()+"]").attr("selected",true);
		}
		else if( i > 4 && i < 8){
        	var select = jQuery('<select style="font-weight:normal;height:24px;" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'">'+htmlArr[i]+'</select>')
			.appendTo( jQuery( '#ExmStudentTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
			.on( 'change', function () {
				var val = jQuery("#"+jQuery(searchHiddenList[i-1]).attr("name")).find("option:selected").val();
				var obj = document.getElementsByName(jQuery(searchHiddenList[i-1]).attr("name"))[0];
				obj.value = val;
				console.info(table.page());
				if(table.page() > 0) {
					   table.page(0).draw(false);
				}
				jQuery("#exmStudentButton").click();
			} );
        	select.find("option[value="+jQuery(searchHiddenList[i-1]).val()+"]").attr("selected",true);
		}	
		else if(i==9||i==10){
			var select = jQuery('<select style="font-weight:normal;height:24px;" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'">'
					+'<option value selected ="selected"></option>'
					+'<option value="Y">是</option>'
					+'<option value="N">否</option>'
					+'</select>')
			.appendTo( jQuery( '#ExmStudentTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
			.on( 'change', function () {
				var val = jQuery("#"+jQuery(searchHiddenList[i-1]).attr("name")).find("option:selected").val();
				var obj = document.getElementsByName(jQuery(searchHiddenList[i-1]).attr("name"))[0];
				obj.value = val;
				if(table.page() > 0) {
				   table.page(0).draw(false);
				}
	  				jQuery("#exmStudentButton").click();
			} );
        	select.find("option[value="+jQuery(searchHiddenList[i-1]).val()+"]").attr("selected",true);
		}
	})
	
}

function readyApplicantPageTable(){
	var table =jQuery('#ApplicantInfoPageTable table').DataTable();	
	var searchHiddenList = jQuery('#ApplicantInfoContainer > div > input');
	jQuery('#ApplicantInfoPageTable table thead').append( '<tr><tr>' );
	
	var selectGradeHtml=document.getElementsByName("AIPsearchGradeId")[0].innerHTML;//年级
	var selectBelongDeptHtml=document.getElementsByName("AIPsearchDepartmentId")[0].innerHTML;//学院
	var selectCampusHtml=document.getElementsByName("AIPsearchCampusId")[0].innerHTML;//校区
	var selectLtypeHtml=document.getElementsByName("applicantLanguage")[0].innerHTML;//类型
	var selectLevelHtml=document.getElementsByName("applicantLevelId")[0].innerHTML;//级别
	
	var selectDeptHtml;
	var userDeptId = document.querySelector("#userDeptId > p").innerHTML;
	if(userDeptId.trim() == "114819126"){
		selectDeptHtml = selectBelongDeptHtml;		
		document.getElementsByName("AIPsearchDepartmentId")[0].value = document.getElementsByName("applicantdDept")[0].value;
	}else{
		selectDeptHtml = '<option selected="selected" value="'+userDeptId+'">'+document.querySelector("#userDeptName > p").innerHTML+'</option>';
		document.getElementsByName("applicantdDept")[0].value = userDeptId;
	}
	
	var htmlArr = new Array();
	htmlArr[6] = selectGradeHtml;
	htmlArr[7] = selectDeptHtml;
	htmlArr[8] = selectCampusHtml;
	htmlArr[11] = selectLtypeHtml;
	htmlArr[12] = selectLevelHtml;
	
	jQuery('#ApplicantInfoPageTable table thead tr:eq(0) th').each( function ( i ) {
		var thwidth = this.style.width;
		jQuery('#ApplicantInfoPageTable table thead tr:eq(1)').append( '<th></th>' );
		var column = table.column( i );
		if(i==1 || i==5){
			var txt = jQuery('<input type="text" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'" value="'+jQuery(searchHiddenList[i-1]).val()+'" style="font-weight:normal;width:45px;">')
			.appendTo( jQuery( '#ApplicantInfoPageTable table thead tr:eq(1) th:eq('+i+')' ).empty())
			.on( {keyup: function () {
							var val = jQuery("#"+jQuery(searchHiddenList[i-1]).attr("name")).val().replace(/(^[\n\s]*)|([\n\s]*$)/g, "");
							var obj = document.getElementsByName(jQuery(searchHiddenList[i-1]).attr("name"))[0];
							obj.value = val;
						} ,
				  keydown: function(e){
					  		var key = e.which;
					  		if(key == 13 && document.activeElement.id == jQuery(searchHiddenList[i-1]).attr("name")){
					  			e.preventDefault();
					  			table.page(0).draw(false);
					  		 	if(table.page() > 0) {
									    table.page(0).draw(false);
									 }
					  			jQuery("#ApplicantPageButton").click();
					  		}
				  		}
				}); 
		}
		else if(i==10||(i>14 && i<17)){
			var txt = jQuery('<input type="text" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'" value="'+jQuery(searchHiddenList[i-1]).val()+'" style="font-weight:normal;width:80px;">')
			.appendTo( jQuery( '#ApplicantInfoPageTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
			.on( {keyup: function () {
							var val = jQuery("#"+jQuery(searchHiddenList[i-1]).attr("name")).val().replace(/(^[\n\s]*)|([\n\s]*$)/g, "");
							var obj = document.getElementsByName(jQuery(searchHiddenList[i-1]).attr("name"))[0];
							obj.value = val;
						} ,
				  keydown: function(e){
					  		var key = e.which;
					  		if(key == 13 && document.activeElement.id == jQuery(searchHiddenList[i-1]).attr("name")){
					  			e.preventDefault();
					  			table.page(0).draw(false);
					  		 	if(table.page() > 0) {
									    table.page(0).draw(false);
									 }
					  			jQuery("#ApplicantPageButton").click();
					  		}
				  		}
				}); 
		}
		else if(i==3 || i==4 || i==9 || i==13){//
			var txt = jQuery('<input type="text" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'" value="'+jQuery(searchHiddenList[i-1]).val()+'" style="font-weight:normal;width:25px;">')
			.appendTo( jQuery( '#ApplicantInfoPageTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
			.on( {keyup: function () {
							var val = jQuery("#"+jQuery(searchHiddenList[i-1]).attr("name")).val().replace(/(^[\n\s]*)|([\n\s]*$)/g, "");
							var obj = document.getElementsByName(jQuery(searchHiddenList[i-1]).attr("name"))[0];
							obj.value = val;
						} ,
				  keydown: function(e){
					  		var key = e.which;
					  		if(key == 13 && document.activeElement.id == jQuery(searchHiddenList[i-1]).attr("name")){
					  			e.preventDefault();
					  			table.page(0).draw(false);
					  		 	if(table.page() > 0) {
									    table.page(0).draw(false);
									 }
					  			jQuery("#ApplicantPageButton").click();
					  		}
				  		}
				}); 
		}
		else if(i==2){
			var select = jQuery('<select style="font-weight:normal;height:24px;width:30px;" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'">'
					+'<option value selected ="selected"></option>'
					+'<option value="F">女</option>'
					+'<option value="M">男</option>'
					+'</select>')
			.appendTo( jQuery( '#ApplicantInfoPageTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
			.on( 'change', function () {
				var val = jQuery("#"+jQuery(searchHiddenList[i-1]).attr("name")).find("option:selected").val();
				var obj = document.getElementsByName(jQuery(searchHiddenList[i-1]).attr("name"))[0];
				obj.value = val;
				table.page(0).draw(false);
				 if(table.page() > 0) {
				    table.page(0).draw(false);
				 }
	  				jQuery("#ApplicantPageButton").click();
			} );
        	select.find("option[value="+jQuery(searchHiddenList[i-1]).val()+"]").attr("selected",true);
		}
		else if(i==6||i==8||i==12){
        	var select = jQuery('<select style="font-weight:normal;height:24px;width:30px;" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'">'+htmlArr[i]+'</select>')
			.appendTo( jQuery( '#ApplicantInfoPageTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
			.on( 'change', function () {
				var val = jQuery("#"+jQuery(searchHiddenList[i-1]).attr("name")).find("option:selected").val();
				var obj = document.getElementsByName(jQuery(searchHiddenList[i-1]).attr("name"))[0];
				obj.value = val;
				console.info(table.page());
				table.page(0).draw(false);
				 if(table.page() > 0) {
				 	   table.page(0).draw(false);
				 }
				jQuery("#ApplicantPageButton").click();
			} );
        	select.find("option[value="+jQuery(searchHiddenList[i-1]).val()+"]").attr("selected",true);
		}	
		else if(i==11){
        	var select = jQuery('<select style="font-weight:normal;height:24px;width:50px;" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'">'+htmlArr[i]+'</select>')
			.appendTo( jQuery( '#ApplicantInfoPageTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
			.on( 'change', function () {
				var val = jQuery("#"+jQuery(searchHiddenList[i-1]).attr("name")).find("option:selected").val();
				var obj = document.getElementsByName(jQuery(searchHiddenList[i-1]).attr("name"))[0];
				obj.value = val;
				console.info(table.page());
				table.page(0).draw(false);
				 if(table.page() > 0) {
				 	   table.page(0).draw(false);
				 }
				jQuery("#ApplicantPageButton").click();
			} );
        	select.find("option[value="+jQuery(searchHiddenList[i-1]).val()+"]").attr("selected",true);
		}	
		else if(i==7){
        	var select = jQuery('<select style="font-weight:normal;height:24px;width:80px;" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'">'+htmlArr[i]+'</select>')
			.appendTo( jQuery( '#ApplicantInfoPageTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
			.on( 'change', function () {
				var val = jQuery("#"+jQuery(searchHiddenList[i-1]).attr("name")).find("option:selected").val();
				var obj = document.getElementsByName(jQuery(searchHiddenList[i-1]).attr("name"))[0];
				obj.value = val;
				console.info(table.page());
				table.page(0).draw(false);
				 if(table.page() > 0) {
				 	   table.page(0).draw(false);
				 }
				jQuery("#ApplicantPageButton").click();
			} );
        	select.find("option[value="+jQuery(searchHiddenList[i-1]).val()+"]").attr("selected",true);
		}	
		else if(i==14){
			var select = jQuery('<select style="font-weight:normal;height:24px;width:30px;" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'">'
					+'<option value selected ="selected"></option>'
					+'<option value="0">否</option>'
					+'<option value="1">是</option>'
					+'</select>')
			.appendTo( jQuery( '#ApplicantInfoPageTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
			.on( 'change', function () {
				var val = jQuery("#"+jQuery(searchHiddenList[i-1]).attr("name")).find("option:selected").val();
				var obj = document.getElementsByName(jQuery(searchHiddenList[i-1]).attr("name"))[0];
				obj.value = val;
				if(table.page() > 0) {
				   table.page(0).draw(false);
				}
	  				jQuery("#ApplicantPageButton").click();
			} );
       	select.find("option[value="+jQuery(searchHiddenList[i-1]).val()+"]").attr("selected",true);
		}
	})
	
 }

function readyCollegeInvigilatorDataTable(){
	var table =jQuery('#CollegeInvigilatorDataTable table').DataTable();	
	var searchHiddenList = jQuery('#CollegeInContainer > div > input');
	jQuery('#CollegeInvigilatorDataTable table thead').append( '<tr><tr>' );
	
	var selectLanguageHtml=document.getElementsByName("collegeInLanguageType")[0].innerHTML;//类型
	var selectLevelHtml = document.getElementsByName("collegeInexamLevelId")[0].innerHTML;//级别
	var selectCampusHtml = document.getElementsByName("collegeIncampusId")[0].innerHTML;//校区
	var selectRoomStrHtml = document.getElementsByName("collegeInroomStr")[0].innerHTML;//考场位置
	
	var htmlArr = new Array();
	htmlArr[1] = selectLanguageHtml;
	htmlArr[2] = selectLevelHtml;
	htmlArr[3] = selectCampusHtml;
	htmlArr[13] = selectRoomStrHtml;
	
	jQuery('#CollegeInvigilatorDataTable table thead tr:eq(0) th').each( function ( i ) {
		var thwidth = this.style.width;
		jQuery('#CollegeInvigilatorDataTable table thead tr:eq(1)').append( '<th></th>' );
		var column = table.column( i );
		
		if(i > 0 && i < 4){
        	var select = jQuery('<select style="font-weight:normal;height:24px;" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'">'+htmlArr[i]+'</select>')
			.appendTo( jQuery( '#CollegeInvigilatorDataTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
			.on( 'change', function () {
				var val = jQuery("#"+jQuery(searchHiddenList[i-1]).attr("name")).find("option:selected").val();
				var obj = document.getElementsByName(jQuery(searchHiddenList[i-1]).attr("name"))[0];
				obj.value = val;
				console.info(table.page());
				if(table.page() > 0) {
					   table.page(0).draw(false);
				}
				jQuery("#CollegeInPageButton").click();
			} );
        	select.find("option[value="+jQuery(searchHiddenList[i-1]).val()+"]").attr("selected",true);
		}	
		else if(i==4 || (i>5 && i<9)|| (i>9 && i<13)){
			var txt = jQuery('<input type="text" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'" value="'+jQuery(searchHiddenList[i-1]).val()+'" style="font-weight:normal;">')
			.appendTo( jQuery( '#CollegeInvigilatorDataTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
			.on( {keyup: function () {
							var val = jQuery("#"+jQuery(searchHiddenList[i-1]).attr("name")).val().replace(/(^[\n\s]*)|([\n\s]*$)/g, "");
							var obj = document.getElementsByName(jQuery(searchHiddenList[i-1]).attr("name"))[0];
							obj.value = val;
						} ,
				  keydown: function(e){
					  		var key = e.which;
					  		if(key == 13 && document.activeElement.id == jQuery(searchHiddenList[i-1]).attr("name")){
					  			e.preventDefault();
					  			if(table.page() > 0) {
									   table.page(0).draw(false);
									}
					  			jQuery("#CollegeInPageButton").click();
					  		}
				  		}
				}); 
		}
//		else if(i==12){
//			var txt = jQuery('<input type="text" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'" value="'+jQuery(searchHiddenList[i-1]).val()+'" style="font-weight:normal;">')
//			.appendTo( jQuery( '#CollegeInvigilatorDataTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
//			.on( {keyup: function () {
//							var val = jQuery("#"+jQuery(searchHiddenList[i-1]).attr("name")).val().replace(/(^[\n\s]*)|([\n\s]*$)/g, "");
//							var obj = document.getElementsByName(jQuery(searchHiddenList[i-1]).attr("name"))[0];
//							obj.value = val;
//						} ,
//				  keydown: function(e){
//					  		var key = e.which;
//					  		if(key == 13 && document.activeElement.id == jQuery(searchHiddenList[i-1]).attr("name")){
//					  			e.preventDefault();
//					  			if(table.page() > 0) {
//									   table.page(0).draw(false);
//									}
//					  			jQuery("#CollegeInPageButton").click();
//					  		}
//				  		}
//				}); 
//		}
		else if(i==5 || i==9){
			var select = jQuery('<select style="font-weight:normal;height:24px;" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'">'
					+'<option value selected ="selected"></option>'
					+'<option value="2">女</option>'
					+'<option value="1">男</option>'
					+'</select>')
			.appendTo( jQuery( '#CollegeInvigilatorDataTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
			.on( 'change', function () {
				var val = jQuery("#"+jQuery(searchHiddenList[i-1]).attr("name")).find("option:selected").val();
				var obj = document.getElementsByName(jQuery(searchHiddenList[i-1]).attr("name"))[0];
				obj.value = val;  
				if(table.page() > 0) {
				   table.page(0).draw(false);
				}
	  				jQuery("#CollegeInPageButton").click();
			} );
        	select.find("option[value="+jQuery(searchHiddenList[i-1]).val()+"]").attr("selected",true);
		}
//		else if(i==13){
//        	var select = jQuery('<select style="font-weight:normal;height:24px;" id="'+jQuery(searchHiddenList[i-1]).attr("name")+'">'+htmlArr[i]+'</select>')
//			.appendTo( jQuery( '#CollegeInvigilatorDataTable table thead tr:eq(1) th:eq('+i+')' ).empty() )
//			.on( 'change', function () {
//				var val = jQuery("#"+jQuery(searchHiddenList[i-1]).attr("name")).find("option:selected").val();
//				var obj = document.getElementsByName(jQuery(searchHiddenList[i-1]).attr("name"))[0];
//				obj.value = val;
//				console.info(table.page());
//				if(table.page() > 0) {
//					   table.page(0).draw(false);
//				}
//				jQuery("#CollegeInPageButton").click();
//			} );
//        	select.find("option[value="+jQuery(searchHiddenList[i-1]).val()+"]").attr("selected",true);
//		}	
	})
}

jQuery(".sidebar-collapse").click(function(){ 
	 if(document.getElementById('homeID').innerText=="主页"){
	 	document.getElementById('homeID').innerHTML='<span class="icon-home5"></span>';	
     }
      else{
     	document.getElementById('homeID').innerHTML='<span class="icon-home5"></span>主页'; }  
	 
	 if(jQuery("#signID").length>0){
	      if(document.getElementById('signID').innerText=="四六级报名"){
		 	document.getElementById('signID').innerHTML='<span class="icon-check"></span>';	
	     }
      else{
     	document.getElementById('signID').innerHTML='<span class="icon-check"></span>四六级报名';  } 
     	  }
	 
	 if(jQuery("#sinfoID").length>0){
	 if(document.getElementById('sinfoID').innerText=="学籍信息管理"){
		 	document.getElementById('sinfoID').innerHTML='<span class="icon-asterisk"></span>';	
	     }
      else{
     	document.getElementById('sinfoID').innerHTML='<span class="icon-asterisk"></span>学籍信息管理';  } 
     	} 
	 
	 if(jQuery("#tinfoID").length>0){
	 if(document.getElementById('tinfoID').innerText=="考生信息管理"){
		 	document.getElementById('tinfoID').innerHTML='<span class="icon-users"></span>';	
	     }
	   else{
	  	document.getElementById('tinfoID').innerHTML='<span class="icon-users"></span>考生信息管理';  } 
	  	}
	
//	if(jQuery("#tarrangeID").length>0){
//	  if(document.getElementById('tarrangeID').innerText=="监考员老师编排"){
//			 	document.getElementById('tarrangeID').innerHTML='<span class="icon-th-list"></span>';	
//		     }
//	   else{
//	  	document.getElementById('tarrangeID').innerHTML='<span class="icon-th-list"></span>监考员老师编排';  }  
//	  }
	
	if(jQuery("#printID").length>0){
	  if(document.getElementById('printID').innerText=="报表打印"){
		 	document.getElementById('printID').innerHTML='<span class="icon-print"></span>';	
	     }
	   else{
	  	document.getElementById('printID').innerHTML='<span class="icon-print"></span>报表打印';  }
	  	}
	
	if(jQuery("#messageID").length>0){
	  if(document.getElementById('messageID').innerText=="发送信息"){
			 	document.getElementById('messageID').innerHTML='<span class="icon-envelope"></span>';	
		     }
	   else{
	  	document.getElementById('messageID').innerHTML='<span class="icon-envelope"></span>发送信息';  }
	  	}
	
//	if(jQuery("#tquotaID").length>0){
//	  if(document.getElementById('tquotaID').innerText=="监考员老师名额设置"){
//			 	document.getElementById('tquotaID').innerHTML='<span class="icon-list-alt"></span>';	
//		     }
//	   else{
//	  	document.getElementById('tquotaID').innerHTML='<span class="icon-list-alt"></span>监考员老师名额设置';  }
//	  	}
	  
//	  if(jQuery("#InvigilatorManagementLink").length>0){
//		   if(document.getElementById('InvigilatorManagementLink').innerText=="监考员老师管理"){
//			 	document.getElementById('InvigilatorManagementLink').innerHTML='<span class="icon-user"></span>';	
//		     }
//		  else{
//				document.getElementById('InvigilatorManagementLink').innerHTML='<span class="icon-user"></span>监考员老师管理';  
//				}
//				}
	  
	  if(jQuery("#applyID").length>0){
	  	if(document.getElementById('applyID').innerText=="申请监考"){
		 	document.getElementById('applyID').innerHTML='<span class="icon-check"></span>';	
	     }
	  else{
			document.getElementById('applyID').innerHTML='<span class="icon-check"></span>申请监考';  }
	  }
//	  
//	  if(jQuery("#alltestteacherID").length>0){
//	  	if(document.getElementById('alltestteacherID').innerText=="二级单位监考员一览表"){
//		 	document.getElementById('alltestteacherID').innerHTML='<span class="icon-table"></span>';	
//	     }
//	  else{
//			document.getElementById('alltestteacherID').innerHTML='<span class="icon-table"></span>二级单位监考员一览表'; 
//			}
//	  }	  
})

//jQuery("#messagePageButton").click(function(){ 
//	jQuery('#MessageTable table').DataTable().page(0).draw( 'page' );
//})


function SearchInvigilatorTableIndex(){
  var searchInvigilatorTable = jQuery("#SearchInvigilatorTable table").DataTable();
  if(searchInvigilatorTable.page() > 0) {
    searchInvigilatorTable.page(0).draw(false);
    }
}

function changeNextToCetApplyPageBtnLabel(){
  var btn = jQuery("#nextToCetApplyPageBtn");
  if(btn.html() == '提交'){
    btn.html("下一步");
  }else{
      btn.html("提交");
  }
  
}


function readyCurrentStudentEmailField(){
	var editButton = jQuery("#editEmailButton"); 
	var saveButton = jQuery("#saveEmailButton"); 
	var inputObj = jQuery("#currentStudentEmailField > input"); 
	if(inputObj.val()!=""){
		inputObj.attr("disabled", "disabled");
		editButton.css("display","block");
		saveButton.css("display","none");
	}
	else{
		saveButton.css("display","block");
		editButton.css("display","none");
	}
}

function enabledEmail(){
	var editButton = jQuery("#editEmailButton"); 
	var saveButton = jQuery("#saveEmailButton"); 
	var inputObj = jQuery("#currentStudentEmailField > input"); 
	if(inputObj.attr("disabled") || inputObj.attr("disabled")=="disabled"){
		inputObj.removeAttr("disabled"); 
		editButton.css("display","none");
		saveButton.css("display","block");
	}
}
function disabledEmail(){
	var editButton = jQuery("#editEmailButton"); 
	var saveButton = jQuery("#saveEmailButton"); 
	jQuery("#currentStudentEmailField > input").attr("disabled", "disabled");
	editButton.css("display","block");
	saveButton.css("display","none");
}

function validateEmail(){
        var email=document.getElementById("currentStudentEmailField_control");
        var RegEmail = /^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/;
        var emailValue = email.value.trim();
        if(emailValue==''){
        	alert("请填写邮件地址！");
        }
        else{
        	var falg=emailValue.search(RegEmail);
            if (falg==-1){
              alert("邮件地址不合法！");
            }
            else{
            	return true;
            }
        }     
}