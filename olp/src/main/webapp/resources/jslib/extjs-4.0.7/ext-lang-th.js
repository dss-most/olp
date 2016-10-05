/*

This file is part of Ext JS 4

Copyright (c) 2011 Sencha Inc

Contact:  http://www.sencha.com/contact

GNU General Public License Usage
This file may be used under the terms of the GNU General Public License version 3.0 as published by the Free Software Foundation and appearing in the file LICENSE included in the packaging of this file.  Please review the following information to ensure the GNU General Public License version 3.0 requirements will be met: http://www.gnu.org/copyleft/gpl.html.

If you are unsure which license is appropriate for your use, please contact the sales department at http://www.sencha.com/contact.

*/
/**
 * List compiled by KillerNay on the extjs.com forums.
 * Thank you KillerNay!
 *
 * Thailand Translations
 */
Ext.onReady(function() {
if(Ext.Date){
    Ext.Date.monthNames = [
      "มกราคม",
      "กุมภาพันธ์",
      "มีนาคม",
      "เมษายน",
      "พฤษภาคม",
      "มิถุนายน",
      "กรกฏาคม",
      "สิงหาคม",
      "กันยายน",
      "ตุลาคม",
      "พฤศจิกายน",
      "ธันวาคม"
    ];
    
    Ext.Date.shortMonthNames = [
                           "ม.ค.",
                           "ก.พ.",
                           "มี.ค.",
                           "เม.ย.",
                           "พ.ค.",
                           "มิ.ย.",
                           "ก.ค.",
                           "ส.ค.",
                           "ก.ย.",
                           "ต.ค.",
                           "พ.ย.",
                           "ธ.ค."
                         ];

    Ext.Date.getShortMonthName = function(month) {
      return Ext.Date.shortMonthNames[month];
    };

    Ext.Date.monthNumbers = {
      "ม.ค." : 0,
      "ก.พ." : 1,
      "มี.ค." : 2,
      "เม.ย.." : 3,
      "พ.ค." : 4,
      "มิ.ย." : 5,
      "ก.ค." : 6,
      "ส.ค." : 7,
      "ก.ย." : 8,
      "ต.ค." : 9,
      "พ.ย." : 10,
      "ธ.ค." : 11
    };

    Ext.Date.getMonthNumber = function(name) {
      return Ext.Date.monthNumbers[name];
    };

    Ext.Date.dayNames = [
      "จันทร์",
      "อังคาร",
      "พุธ",
      "พฤหัสบดี",
      "ศุกร์",
      "เสาร์",
      "อาทิตย์"
    ];

    Ext.Date.getShortDayName = function(day) {
      return Ext.Date.dayNames[day].substring(0, 3);
    };
}


if(Ext.form.field.Text){
  Ext.apply(Ext.form.field.Text.prototype, {
    minLengthText : "The minimum length for this field is {0}",
    maxLengthText : "The maximum length for this field is {0}",
    blankText     : "This field is required",
    regexText     : "",
    emptyText     : null
  });
}

if(Ext.form.field.Number){
  Ext.apply(Ext.form.field.Number.prototype, {
    minText : "The minimum value for this field is {0}",
    maxText : "The maximum value for this field is {0}",
    nanText : "{0} is not a valid number"
  });
}

if(Ext.form.field.Date){
  Ext.apply(Ext.form.field.Date.prototype, {
    disabledDaysText  : "»ÔŽ",
    disabledDatesText : "»ÔŽ",
    minText           : "The date in this field must be after {0}",
    maxText           : "The date in this field must be before {0}",
    invalidText       : "{0} is not a valid date - it must be in the format {1}",
    format            : "m/d/y",
    altFormats        : "m/d/Y|m-d-y|m-d-Y|m/d|m-d|md|mdy|mdY|d|Y-m-d"
  });
}

if(Ext.form.field.ComboBox){
  Ext.apply(Ext.form.field.ComboBox.prototype, {
    valueNotFoundText : undefined
  });
    Ext.apply(Ext.form.field.ComboBox.prototype.defaultListConfig, {
        loadingText       : "กำลังโหลด..."
    });
}

if(Ext.form.field.VTypes){
  Ext.apply(Ext.form.field.VTypes, {
    emailText    : 'This field should be an e-mail address in the format "user@example.com"',
    urlText      : 'This field should be a URL in the format "http:/'+'/www.example.com"',
    alphaText    : 'This field should only contain letters and _',
    alphanumText : 'This field should only contain letters, numbers and _'
  });
}

if(Ext.form.field.HtmlEditor){
  Ext.apply(Ext.form.field.HtmlEditor.prototype, {
    createLinkText : 'Please enter the URL for the link:',
    buttonTips : {
      bold : {
        title: 'Bold (Ctrl+B)',
        text: 'Make the selected text bold.',
        cls: Ext.baseCSSPrefix + 'html-editor-tip'
      },
      italic : {
        title: 'Italic (Ctrl+I)',
        text: 'Make the selected text italic.',
        cls: Ext.baseCSSPrefix + 'html-editor-tip'
      },
      underline : {
        title: 'Underline (Ctrl+U)',
        text: 'Underline the selected text.',
        cls: Ext.baseCSSPrefix + 'html-editor-tip'
      },
      increasefontsize : {
        title: 'Grow Text',
        text: 'Increase the font size.',
        cls: Ext.baseCSSPrefix + 'html-editor-tip'
      },
      decreasefontsize : {
        title: 'Shrink Text',
        text: 'Decrease the font size.',
        cls: Ext.baseCSSPrefix + 'html-editor-tip'
      },
      backcolor : {
        title: 'Text Highlight Color',
        text: 'Change the background color of the selected text.',
        cls: Ext.baseCSSPrefix + 'html-editor-tip'
      },
      forecolor : {
        title: 'Font Color',
        text: 'Change the color of the selected text.',
        cls: Ext.baseCSSPrefix + 'html-editor-tip'
      },
      justifyleft : {
        title: 'Align Text Left',
        text: 'Align text to the left.',
        cls: Ext.baseCSSPrefix + 'html-editor-tip'
      },
      justifycenter : {
        title: 'Center Text',
        text: 'Center text in the editor.',
        cls: Ext.baseCSSPrefix + 'html-editor-tip'
      },
      justifyright : {
        title: 'Align Text Right',
        text: 'Align text to the right.',
        cls: Ext.baseCSSPrefix + 'html-editor-tip'
      },
      insertunorderedlist : {
        title: 'Bullet List',
        text: 'Start a bulleted list.',
        cls: Ext.baseCSSPrefix + 'html-editor-tip'
      },
      insertorderedlist : {
        title: 'Numbered List',
        text: 'Start a numbered list.',
        cls: Ext.baseCSSPrefix + 'html-editor-tip'
      },
      createlink : {
        title: 'Hyperlink',
        text: 'Make the selected text a hyperlink.',
        cls: Ext.baseCSSPrefix + 'html-editor-tip'
      },
      sourceedit : {
        title: 'Source Edit',
        text: 'Switch to source editing mode.',
        cls: Ext.baseCSSPrefix + 'html-editor-tip'
      }
    }
  });
}

if(Ext.grid.header.Container){
  Ext.apply(Ext.grid.header.Container.prototype, {
    sortAscText  : "Sort Ascending",
    sortDescText : "Sort Descending",
    lockText     : "Lock Column",
    unlockText   : "Unlock Column",
    columnsText  : "Columns"
  });
}

if(Ext.grid.GroupingFeature){
  Ext.apply(Ext.grid.GroupingFeature.prototype, {
    emptyGroupText : '(None)',
    groupByText    : 'Group By This Field',
    showGroupsText : 'Show in Groups'
  });
}

if(Ext.grid.PropertyColumnModel){
  Ext.apply(Ext.grid.PropertyColumnModel.prototype, {
    nameText   : "Name",
    valueText  : "Value",
    dateFormat : "m/j/Y"
  });
}

if(Ext.layout.BorderLayout && Ext.layout.BorderLayout.SplitRegion){
  Ext.apply(Ext.layout.BorderLayout.SplitRegion.prototype, {
    splitTip            : "Drag to resize.",
    collapsibleSplitTip : "Drag to resize. Double click to hide."
  });
}

});
