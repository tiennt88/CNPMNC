/**
 * Author: TienNT
 * Description: Cung cấp các API xử lý quá trình init/load các widget trong các TagHandler
 * Date: 10/07/2011
 * FWVersion: 3.3
 **/

sd.widget.loader = {
    sortableArray: null,
    structure: null,
    structure_noScroll: null,
    structure_scroll: null,
    
    gridFormatDate: function (inDatum) {
        if ((/[0-9]T[0-9]+/).test(inDatum)) {
            var temp = inDatum.split("T");
            var inDatum_1 = temp[0];
            if (inDatum_1.indexOf('-')!=-1) {
                var tmp = inDatum_1.split('-');
                inDatum = tmp[1]+'/'+tmp[2]+'/'+ tmp[0];
                inDatum += ' ' + temp[1];
            }
        //"  var constr = this.constraint['datePattern'];" +
        } else {}
        if (inDatum != null) {
            if (inDatum != " ")return dojo.date.locale.format(new Date(inDatum), this.constraint);
            else return '';
        } else {
            return '';
        }
    },

    cloneArray : function (arr) {
        var arr1 = new Array();
        for (var property in arr) {
            arr1[property] = typeof (arr[property]) == 'object' ? arr[property].clone() : arr[property]
        }
        return arr1;
    },

    loadAjaxTree : function(
        getTopLevelUrl, getChildrenUrl, 
        topLevelUrlForm, topLevelUrlParam, childrenUrlForm, childrenUrlParam,
        rootLabel, checkboxStrict, delayLoading, delayLoadTopLevelTime, delayLoadChildrenTime,
        id, persist, onClick, onNodeChecked, onNodeUnchecked, treeContainer
        ) {
        try {
            if(sd.operator.allowedToExecJS == false) {
                return;
            }

            dojo.require("vt.tree.ajaxTree.dataStore");
            dojo.require("vt.tree.ajaxTree.treeModel");
            dojo.require("vt.tree.ajaxTree.tree");

            //[ Create store
            var store = new vt.tree.ajaxTree.dataStore({
                url : getTopLevelUrl,
                getChildrenUrl : getChildrenUrl
            });

            if(sd.util.isValidS(topLevelUrlForm)) {
                store.vtTopUrlForm = topLevelUrlForm;
            }
            if(sd.util.isValid(topLevelUrlParam)) {
                store.vtTopUrlParam = topLevelUrlParam;
            }
            if(sd.util.isValidS(childrenUrlForm)) {
                store.vtChildrenUrlForm = childrenUrlForm;
            }
            if(sd.util.isValid(childrenUrlParam)) {
                store.vtChildrenUrlParam = childrenUrlParam;
            }
            //] Create store

            //[ Create model
            var model = new vt.tree.ajaxTree.treeModel({
                store : store,
                rootLabel : rootLabel
            });

            if(sd.util.isValid(checkboxStrict)) {
                model.checkboxStrict = checkboxStrict;
            }
            if(sd.util.isValid(delayLoading)) {
                model.vtDelayLoading = delayLoading;
            }
            if(sd.util.isValid(delayLoadTopLevelTime)) {
                model.vtDelayLoadTopLevelTime = delayLoadTopLevelTime;
            }
            if(sd.util.isValid(delayLoadChildrenTime)) {
                model.vtDelayLoadChildrenTime = delayLoadChildrenTime;
            }
            //] Create model

            //[ Create tree
            var tree = new vt.tree.ajaxTree.tree({
                id : id,
                model : model
            });

            if(sd.util.isValid(persist)) {
                tree.persist = persist;
            }
            
            if(sd.util.isValidS(onClick)) {
                tree.onClick = function(item,node,domElement){
                    try {
                        eval(onClick + "(item, node,domElement);");
                    }
                    catch(e){
                        alert("VTTree,onNodeClick:" +  e.message);
                    }
                };
            }
            if(sd.util.isValidS(onNodeChecked)) {
                tree.onNodeChecked = function(item,node){
                    try {
                        eval(onNodeChecked + "(item, node);");
                    }
                    catch(e){
                        alert("VTTree,onNodeChecked:" +  e.message);
                    }
                };
            }
            if(sd.util.isValidS(onNodeUnchecked)) {
                tree.onNodeUnchecked = function(item,node){
                    try {
                        eval(onNodeUnchecked + "(item, node);");
                    }
                    catch(e){
                        alert("VTTree,onNodeUnchecked:" +  e.message);
                    }
                };
            }
            //] Create tree

            //[ Make it alive
            dojo.byId(treeContainer).appendChild(tree.domNode);
        //] Make it alive
        }
        catch(e) {
            alert("Error in sd.widget.loader.loadAjaxTree:\n" + e.message);
        }
    },

    loadTree : function(
        getDataUrl,
        rootLabel, checkboxStrict,
        id, persist, onClick, onNodeChecked, onNodeUnchecked, treeContainer
        ) {
        try {
            if(sd.operator.allowedToExecJS == false) {
                return;
            }

            dojo.require("dojo.data.ItemFileWriteStore");
            dojo.require("vt.tree.checkboxTree.CheckBoxStoreModel");
            dojo.require("vt.tree.checkboxTree.CheckBoxTree");

            //[ Create store
            var store = new dojo.data.ItemFileWriteStore({
                url : getDataUrl
            });
            //] Create store

            //[ Create model
            var model = new vt.tree.checkboxTree.CheckBoxStoreModel({
                store : store,
                rootLabel : rootLabel
            });

            if(sd.util.isValid(checkboxStrict)) {
                model.checkboxStrict = checkboxStrict;
            }
            //] Create model

            //[ Create tree
            var tree = new vt.tree.checkboxTree.CheckBoxTree({
                id : id,
                model : model
            });

            if(sd.util.isValid(persist)) {
                tree.persist = persist;
            }

            if(sd.util.isValidS(onClick)) {
                tree.onClick = function(item,node,domElement){
                    try {
                        eval(onClick + "(item, node,domElement);");
                    }
                    catch(e){
                        alert("VTTree,onNodeClick:" +  e.message);
                    }
                };
            }
            if(sd.util.isValidS(onNodeChecked)) {
                tree.onNodeChecked = function(item,node){
                    try {
                        eval(onNodeChecked + "(item, node);");
                    }
                    catch(e){
                        alert("VTTree,onNodeChecked:" +  e.message);
                    }
                };
            }
            if(sd.util.isValidS(onNodeUnchecked)) {
                tree.onNodeUnchecked = function(item,node){
                    try {
                        eval(onNodeUnchecked + "(item, node);");
                    }
                    catch(e){
                        alert("VTTree,onNodeUnchecked:" +  e.message);
                    }
                };
            }
            //] Create tree

            //[ Make it alive
            dojo.byId(treeContainer).appendChild(tree.domNode);
        //] Make it alive
        }
        catch(e) {
            alert("Error in sd.widget.loader.loadTree:\n" + e.message);
        }
    },
    /**
     * Author: DungDV
     * Description: them ham initGrid, loadGrid, loadColumnGrid, initDataPicker, loadDataPicker
     * thuoc tinh sortableArray, structure, structure_noScroll, structure_scroll cho qua trinh khoi tao va load Grid, DataPicker
     * sortableArray danh cho thiet lap cot nao la cot sort duoc
     * Date: 10/07/2011
     * FWVersion: 3.3
     **/
    initGrid: function (id) {
        try {
            if(sd.operator.allowedToExecJS == false) {
                return;
            }

            dojo.require("vt.dataGrid.cells._base");
            this.structure = [];
            this.structure_noScroll = {
                noscroll: true,
                cells:[]
            };
            this.sortableArray = [];
            this.structure_scroll = [];
            this.structure.push(this.structure_noScroll);
            this.structure.push(this.structure_scroll);
        } catch (e) {
            alert("Error in sd.widget.loader.initGrid:\n" + e.message);
        }
    },

    loadGrid: function (
        id, getDataUrl, style, clientSort, query, rowSelector,
        container, autoWidth, rowsPerPage, serverPaging, selectable,
        pageText, rowCountText, rowPerPageText, noDataMessageText,
        idVTGrid, idDiv
        ) {
        try {
            if(sd.operator.allowedToExecJS == false) {
                return;
            }
            // DungDV for fix bug: khong doi mau item duoc chon tren Select
            if (page.enableGridMouseEvent == null || page.enableGridMouseEvent == undefined) {
                page.enableGridMouseEvent = true;
            }
            dojo.require("vt.dataGrid.vtDataGrid");
            var attrs = {};
            if(sd.util.isValid(style)) {
                attrs.style = style;
            }
            if(sd.util.isValid(clientSort)) {
                attrs.clientSort = clientSort;
            }
            if(sd.util.isValid(rowSelector)) {
                attrs.rowSelector = rowSelector;
            }
            if(sd.util.isValid(query)) {
                attrs.query = query;
            }
            if(sd.util.isValid(query)) {
                attrs.query = query;
            }
            if(sd.util.isValid(rowsPerPage)) {
                attrs.rowsPerPage = rowsPerPage;
                attrs.autoHeight = rowsPerPage;
            }
            if(sd.util.isValid(autoWidth)) {
                attrs.autoWidth = autoWidth;
            }
            if(sd.util.isValid(serverPaging)) {
                attrs.serverPaging = serverPaging;
            }
            if(sd.util.isValid(selectable)) {
                attrs.selectable = selectable;
            } else {
                attrs.selectable = true;
            }
            attrs.noDataMessage = noDataMessageText;
            if(sd.util.isValid(id)) {
                attrs.id = id;
            }
            var gridParam = {};
            gridParam.gridAttr = attrs;
            gridParam.sortableArray = this.sortableArray;
            gridParam.structure = this.structure;
            gridParam.getDataUrl = getDataUrl;
            if(sd.util.isValid(container)) {
                attrs.container = container;
            }
            gridParam.id = idVTGrid;
            var vtGrid = new vt.dataGrid.vtDataGrid(gridParam);
            if(sd.util.isValid(container)) {
                dojo.byId(container).appendChild(vtGrid.domNode);
            } else {
                dojo.byId(idDiv).appendChild(vtGrid.domNode);
            }
            vtGrid.titleRowCount.innerHTML = rowCountText;
            vtGrid.titlePageSelector.innerHTML = pageText;
            vtGrid.titleRpp.innerHTML = rowPerPageText;
            vtGrid.startup();
            try {
                delete this.sortableArray;
                delete this.structure_noScroll;
                delete this.structure_scroll;
                delete this.structure;
            } catch (e) {
                this.sortableArray = undefined;
                this.structure_noScroll = undefined;
                this.structure_scroll = undefined;
                this.structure = undefined;
            }
        } catch (e) {
            alert("Error in sd.widget.loader.loadGrid:\n" + e.message);
        }
    },

    loadColumnGrid: function (
        parenId, editable, field, colName, width, type, arrOption, arrValue, formatter, styles,
        cellStyles, headerStyles, get, value, alwaysEditing, format,
        onclick, setDisabled, setChecked, headerCheckbox, wrapping,
        sortType, noScroll, enableSort
        ) {
        try {
            
            //cuongnx
             var sortable = true;
            if(sd.util.isValid(enableSort)) {
                sortable = enableSort;
            }
           // end cuongnx
            if(sd.operator.allowedToExecJS == false) {
                return;
            }
            var attrs = {};
            if(sd.util.isValid(arrOption)) {
                attrs.options = arrOption;
            }
            if(sd.util.isValid(arrValue)) {
                attrs.values = arrValue;
            }
            if(sd.util.isValid(formatter)) {
                attrs.formatter = formatter;
//                sortable = false;
            }
            if(sd.util.isValid(field)) {
                attrs.field = field;
            }
            if(sd.util.isValid(alwaysEditing)) {
                attrs.alwaysEditing = alwaysEditing;
            }
            if(sd.util.isValid(value)) {
                attrs.value = value;
            }
            if(sd.util.isValid(get)) {
                attrs.get = get;
            }
            if(sd.util.isValid(styles)) {
                attrs.styles = styles;
            }
            if(sd.util.isValid(cellStyles)) {
                attrs.cellStyles = cellStyles;
            }
            if(sd.util.isValid(headerStyles)) {
                attrs.headerStyles = headerStyles;
            }
            if(sd.util.isValid(width)) {
                attrs.width = width;
            }
            if(sd.util.isValid(type)) {
                var typeLow = type.toLowerCase();
                if (typeLow == "checkbox") {
                    attrs.type = dojox.grid.cells.vtBool;
                    attrs.editable = true;
                    attrs.gridId = parenId;
                    if(sd.util.isValid(field)) {
                        attrs.field = "_R_D_";
                    }
                    editable = null;
                    //sortable = false;
                } else if (typeLow == "select") {
                    attrs.type = dojox.grid.cells.vtSelect;
                    attrs.editable = true;
                    editable = null;
                } else if (typeLow == "radio") {
                    attrs.type = dojox.grid.cells.Radio;
                    attrs.editable = true;
                    attrs.gridId = parenId;
                    if(sd.util.isValid(field)) {
                        attrs.field = "_R_D_";
                    }
                    editable = null;
                    //sortable = false;
                } else if (typeLow == "date") {
                    attrs.type = dojox.grid.cells.vtDateTextBox;
                    attrs.formatter = this.gridFormatDate;
                    attrs.constraint = {};
                    attrs.constraint.formatLength = 'full';
                    if(sd.util.isValidS(format)) {
                        attrs.constraint.datePattern = format;
                        if (format.toLowerCase().indexOf("ss") == -1) {
                            attrs.constraint.selector = 'date';
                        }
                    } else {
                        attrs.constraint.datePattern = 'dd/MM/yyyy';
                    }
                } else {
                    attrs.type = type;
                }
            }
            if(sd.util.isValidS(wrapping)) {
                if (wrapping.indexOf("true") != -1) {
                    attrs.wrapping = true;
                }
            }
            if(sd.util.isValidS(onclick)) {
                attrs.onclick = onclick;
            }
            if(sd.util.isValidS(setDisabled)) {
                //                if ((/\(*(,?)*\)/).test(setDisabled)) {
                //                    console.error(setDisabled);
                //                    attrs.setDisabled = "\"" + setDisabled + "\"";
                //                } else {
                //                    attrs.setDisabled = setDisabled;
                //                }
                attrs.setDisabled = setDisabled;
            }
            if(sd.util.isValidS(setChecked)) {
                //                if ((/\(*(,?)*\)/).test(setChecked)) {
                //                    console.error(setChecked);
                //                    attrs.setDisabled = "\"" + setChecked + "\"";
                //                } else {
                //                    attrs.setDisabled = setChecked;
                //                }
                attrs.setChecked = setChecked;
            }
            if(sd.util.isValidS(headerCheckbox)) {
                attrs.headerCheckbox = headerCheckbox;
                //sortable = false;
            }
            if(sd.util.isValid(editable)) {
                attrs.editable = editable;
            }
            if(sd.util.isValid(sortType)) {
                attrs.sortType = sortType;
            }
            attrs.name = colName;
            this.sortableArray.push(sortable);
            if(sd.util.isValid(noScroll) && (noScroll == "true" || noScroll == true)) {
                this.structure_noScroll.cells.push(attrs);
            } else {
                this.structure_scroll.push(attrs);
            }
        } catch (e) {
            alert("Error in sd.widget.loader.loadColumnGrid:\n" + e.message);
        }
    },

    initDataPicker: function (id) {
        try {
            if(sd.operator.allowedToExecJS == false) {
                return;
            }
            dojo.require("vt.dataPicker.dataPicker");
            this.structure = [];
            this.structure_noScroll = {
                noscroll: true,
                cells:[]
            };
            this.sortableArray = [];
            this.structure_scroll = [];
            this.structure.push(this.structure_noScroll);
            this.structure.push(this.structure_scroll);
        } catch (e) {
            alert("Error in sd.widget.loader.initDataPicker:\n" + e.message);
        }
    },

    loadDataPicker: function (
        id, searchParams, searchUrl, bServerSearch, searchNumCol, gridUrl,
        valueField, hiddenFields, useCache, name,
        required, cssStyle, maxLength, btnSearchLabelText, idDiv,
        jsObjectItem
        ) {
        try {
            if(sd.operator.allowedToExecJS == false) {
                return;
            }
            var pickerParams = {};
            pickerParams.id = id;
            pickerParams.name = (sd.util.isValid(name) ? name : "");
            pickerParams.selectedItem = jsObjectItem;
            if (sd.util.isValid(cssStyle)) {
                pickerParams.style = cssStyle;
            }
            pickerParams.searchParams = searchParams;
            if (sd.util.isValid(searchNumCol)) {
                pickerParams.searchNumCol = searchNumCol;
            }
            if (sd.util.isValid(useCache)) {
                pickerParams.useCache = useCache;
            }
            if (sd.util.isValid(bServerSearch)) {
                pickerParams.bServerSearch = bServerSearch;
            }
            if (sd.util.isValid(btnSearchLabelText)) {
                pickerParams.btnSearchLabel = btnSearchLabelText;
            }
            if (sd.util.isValid(searchUrl)) {
                pickerParams.searchUrl = searchUrl;
            }
            pickerParams.gridUrl = gridUrl;
            pickerParams.valueField = valueField;
            if (sd.util.isValid(hiddenFields)) {
                pickerParams.hiddenFields = hiddenFields.split(",");
            }
            pickerParams.gridStructure = this.structure;
            if (sd.util.isValid(required) && (required == "true" || required == true)) {
                pickerParams.vt_required = true;
            }
            var dataPopup = new vt.dataPicker.dataPicker(pickerParams);
            dojo.byId(idDiv).appendChild(dataPopup.domNode);
            if (sd.util.isValid(maxLength)) {
                dojo.byId(id).maxLength = maxLength;
            }
        } catch (e) {
            alert("Error in sd.widget.loader.loadDataPicker:\n" + e.message);
        }
    },

    loadCKEditor : function(/*String*/ id) {
        try {
            if(sd.operator.allowedToExecJS == false) {
                return;
            }

            var locale = sd.util.isValidS(sd.operator.getLocale()) ? sd.operator.getLocale() : "en";
            
            CKEDITOR.replace(id,
            {
                language:
                    locale,
                filebrowserBrowseUrl :
                    sd.operator.path["PlugInFolder"] + '/ckfinder/ckfinder.html',
                filebrowserImageBrowseUrl :
                    sd.operator.path["PlugInFolder"] + '/ckfinder/ckfinder.html?type=Images',
                filebrowserFlashBrowseUrl :
                    sd.operator.path["PlugInFolder"] + '/ckfinder/ckfinder.html?type=Flash&currentFolder=uploadFlash',
                filebrowserUploadUrl :
                    sd.operator.path["context"] + '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files',
                filebrowserImageUploadUrl :
                    sd.operator.path["context"] + '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images',
                filebrowserFlashUploadUrl :
                    sd.operator.path["context"] + '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash'
            });
        } catch(e) {
            alert("Error in sd.widget.loader.loadCKEditor: " + e.message);
        }
    }
};