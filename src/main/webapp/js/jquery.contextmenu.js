/**
 * jQuery plugin for Pretty looking right click context menu.
 *
 * Requires popup.js and popup.css to be included in your page. And jQuery, obviously.
 *
 * Usage:
 *
 *   jQuery('.something').contextPopup({
 *     title: 'Some title',
 *     items: [
 *       {label:'My Item', icon:'/some/icon1.png', action:function() { alert('hi'); }},
 *       {label:'Item #2', icon:'/some/icon2.png', action:function() { alert('yo'); }},
 *       null, // divider
 *       {label:'Blahhhh', icon:'/some/icon3.png', action:function() { alert('bye'); }},
 *     ]
 *   });
 *
 * Icon needs to be 16x16. I recommend the Fugue icon set from: http://p.yusukekamiyamane.com/
 *
 * - Joe Walnes, 2011 http://joewalnes.com/
 *   https://github.com/joewalnes/jquery-simple-context-menu
 *
 * MIT License: https://github.com/joewalnes/jquery-simple-context-menu/blob/master/LICENSE.txt
 */
jQuery.fn.contextPopup = function(menuData) {

	// Define default settings
	var settings = {
		contextMenuClass: 'contextMenuPlugin',
		gutterLineClass: 'gutterLine',
		headerClass: 'header',
		seperatorClass: 'divider',
		title: '',
		items: []
	};

	// merge them
	jQuery.extend(settings, menuData);

  // Build popup menu HTML
  function createMenu(e) {
    var menu = jQuery('<ul class="' + settings.contextMenuClass + '"><div class="' + settings.gutterLineClass + '"></div></ul>')
      .appendTo(document.body);
    if (settings.title) {
      jQuery('<li class="' + settings.headerClass + '"></li>').text(settings.title).appendTo(menu);
    }
    settings.items.forEach(function(item) {
      if (item) {
        var rowCode = '<li><a href="#"><span></span></a></li>';
        var row = jQuery(rowCode).appendTo(menu);
        if(item.icon){
          var icon = jQuery('<div>');
          icon.attr('class', item.icon);
          icon.insertBefore(row.find('span'));
        }
        row.find('span').text(item.label);
        if (item.action) {
          row.find('a').click(function(){ item.action(e); });
        }
      } else {
        jQuery('<li class="' + settings.seperatorClass + '"></li>').appendTo(menu);
      }
    });
    menu.find('.' + settings.headerClass ).text(settings.title);
    return menu;
  }

  // On contextmenu event (right click)
  this.bind('contextmenu', function(e) {

    var menu = createMenu(e).show();


    var left = e.pageX + 5, /* nudge to the right, so the pointer is covering the title */
        top = e.pageY;
    if (top + menu.height() >= jQuery(window).height()) {
        top -= menu.height();
    }
    if (left + menu.width() >= jQuery(window).width()) {
        left -= menu.width();
    }

    // Create and show menu
    menu.css({zIndex:1000001, left:left, top:top})
      .bind('contextmenu', function() { return false; });

    // Cover rest of page with invisible div that when clicked will cancel the popup.
    var bg = jQuery('<div></div>')
    // 将height:'100%' 改为height:jQuery(document).height(),保证bg能覆盖整个界面
      .css({left:0, top:0, width:'100%', height:jQuery(document).height(),  position:'absolute', zIndex:1000000})
      .appendTo(document.body)
      .bind('contextmenu click', function() {
        // If click or right click anywhere else on page: remove clean up.
        bg.remove();
        menu.remove();
        return false;
      });

    // When clicking on a link in menu: clean up (in addition to handlers on link already)
    menu.find('a').click(function() {
      bg.remove();
      menu.remove();
    });

    // Cancel event, so real browser popup doesn't appear.
    return false;
  });

  return this;
};

