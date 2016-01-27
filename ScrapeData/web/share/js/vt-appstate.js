/**
 * Author: TienNT
 * Description: Cung cấp các API cho phép xử lý sự kiện next/back của browser
 * Date: 10/07/2011
 * FWVersion: 3.3
 **/

ApplicationState = function(action, areaId){
    this.action = action;
    this.areaId = areaId;

    this.preGo = undefined; //Function
    this.postGo = undefined; //Function
    this.param = undefined; //Object

    this.changeUrl = true; //?
};

dojo.extend(ApplicationState, {
    back: function(){
        this.go();
    },

    forward: function(){
        this.go();
    },
    
    go: function(){
        try{
            if(this.action && this.action.length > 0) {
                if(this.preGo) {
                    this.preGo();
                }

                var arg = {
                    url : this.action,
                    areaId : this.areaId
                };

                if(this.postGo) {
                    arg.onSuccess = this.postGo;
                }

                sd.connector.updateArea(arg);
            }
        } catch(e) {
            alert("vt-appstate.js, go:\n" + e.message);
        }
    }
});