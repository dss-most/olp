Ext.onReady(function(){
	Ext.QuickTips.init();
	
	Ext.define('Activity', {
		extend: 'Ext.data.Model',
		fields: ['ID', 'ACTIVITY_NAME', 'ACTIVITY_CODE, BRANCH_NAME, EXAMPLE_NAME']
	});
	
	Ext.define('Customer', {
		extend : 'Ext.data.Model',
		fields : ['ID', 'CUSTOMER_CODE', 'CUSTOMER_NAME_CANDIDATE']
	});
	
	var activityStore = new Ext.data.Store({
		model : 'Activity',
		id: 'store_activityStore',
		proxy : {
			type : 'ajax',
			url : '/olp/json/listActivity'
		}
	});
	
	var customerStore = new Ext.data.Store({
		model : 'Customer',
		id : 'store_customerStore',
		proxy : {
			type : 'ajax',
			url : '/olp/json/listCustomer'
		}
	});

	var simpleForm = Ext.create('Ext.form.Panel', {
		url : 'printInvoice',
		frame : true,
		title : 'พิมพ์ใบแจ้งหนี้ชำระค่าธรรมเนียม',
		bodyStyle : 'padding:5px 5px 0',
		standardSubmit: true,
		width : 350,
		fieldDefaults : {
			msgTarget : 'side',
			labelWidth : 75
		},
		defaultType : 'textfield',
		defaults : {
			anchor : '100%'
		},

		items : [ {
			fieldLabel : 'ปีงบประมาณ',
			name : 'fiscalYear',
			allowBlank : false,
			listeners: {
				change: function(field, newValue, oldValue, eOpts) {
					var combo = Ext.getCmp('combo_activityCombo');
					combo.reset();
					combo.allQuery= newValue;
				}
			}
		}, {
			fieldLabel : 'กิจกรรม',
			name : 'activityId',
			id: 'combo_activityCombo',
			xtype : 'combo',
			displayField: 'ACTIVITY_CODE',
			//tpl: '<b>{ACTIVITY_CODE}</b> : {ACTIVITY_NAME}',
			queryParam: 'fiscalYear',
			editable: false,
			store : activityStore,
			valueField: 'ID',
			listConfig: {
            	loadingText: 'Searching...',
				emptyText: 'No matching address found...',

                 // Custom rendering template for each item
				getInnerTpl: function() {
                     return '<div> <b>{ACTIVITY_CODE}</b> : {ACTIVITY_NAME} ' +
                     '</div>';
                 }
    		}
		}, {
			fieldLabel : 'รหัสลูกค้า',
			name : 'customerCode',
			id : 'combo_customerCodeCombo',
			xtype : 'combo',
			queryParam: 'customerCode',
			displayField: 'CUSTOMER_CODE',
			store : customerStore,
			valueField : 'ID',
			typeAhead : true,
			minChars : 0,
			queryMode : 'remote',
			listConfig: {
				getInnerTpl : function() {
					return '<div> <b>{CUSTOMER_CODE}</b> : {CUSTOMER_NAME_CANDIDATE} </div>';
				}
			}
			}
		 ],

		buttons : [ {
			text : 'ตกลง',
			id : 'btn_ok',
			listeners : {
				click : function() {
					
					var form = this.up('form');

					
					form.submit();
				}
			}
		}, {
			text : 'ยกเลิก'
		} ]
	});

	simpleForm.render('formCanvas');
	 
});