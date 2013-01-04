/*
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Shows the appHeader and footer, initializes the tweet area, selects appropriate links in nav menus, removes
 * padding from views rendered in lightboxes, and handles tab swap action fire if using large example
 */
jQuery(function () {
    jQuery(".demo-appHeader, .demo-appFooter, .demo-thirdTier").show();
    jQuery(".demo-tweets > div").tweet({
        avatar_size:16,
        count:3,
        username:"kuali",
        loading_text:"Loading tweets..."
    }).bind("loaded", function () {
                jQuery(this).find("a").attr("target", "_blank");
            });
    linkSelection();
    if(jQuery("#renderedInLightBox").length && jQuery("#renderedInLightBox").val() == "true"){
        jQuery(".uif-view").css("padding-top", "0");
    }
    handleTabSwap("select#Demo-LargeExampleDropdown_control");
});

/**
 * Setup call for exhibit tabs, adds a handler for the tabsactivate event to switch the source in the syntaxHighlighter
 * based on tab index
 */
function setupExhibitHandlers() {
    jQuery( "#ComponentLibraryTabGroup_tabs" ).on( "tabsactivate", function( event, ui ) {
        var tabIndex = ui.newTab.index();
        var source = jQuery("#demo-exhibitSource > pre:eq(" + tabIndex + ")");
        jQuery("div.uif-syntaxHighlighter:first > div > pre").replaceWith(jQuery(source)[0].outerHTML);
    } );
}

/**
 * Adds css classes the appropriate links in the header and in the componentLibrary navigation based on user selection
 * so they appear active
 */
function linkSelection() {
    var viewDiv = jQuery("div.uif-view");
    if (jQuery(viewDiv).is(".demo-componentLibView")  || jQuery(viewDiv).is(".demo-componentLibHome")) {
        var viewId = viewDiv.attr("id");
        var link = jQuery("#Uif-Navigation").find("a[href*='" + viewId + "']");
        if (link.length) {
            jQuery(link).css("color", "#222222");
            var accordionLi = jQuery(link).closest("li.uif-accordionTab");
            var index = jQuery(accordionLi).index();
            jQuery(accordionLi).parent().accordion("option", "active", index);
        }

        jQuery("a#Demo-LibraryLink").addClass("active");
    }
    else if (jQuery(viewDiv).is(".demo-sampleAppHomeView")) {
        jQuery("a#Demo-HomeLink").addClass("active");
    }
    else{
        jQuery("a#Demo-DemoLink").addClass("active");
    }
}

/**
 * Activates the tab (hidden in the case where large example is being used) for large examples in order to retain
 * the ability to show the correct source in the syntaxHighlighter without needing additional handlers
 *
 * @param control the large example dropdown control
 */
function handleTabSwap(control){
    var tab = jQuery(control).val();
    if(tab != undefined){
        var tabNum = parseInt(tab);
        jQuery("#ComponentLibraryTabGroup_tabs").tabs( "option", "active", tabNum );
    }
}

