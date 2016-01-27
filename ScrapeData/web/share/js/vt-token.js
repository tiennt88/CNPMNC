token = {
    reloadToken : function() {
        var getArgs = {
            url: "token!reloadToken.do",
            handleAs: "text",
            preventCache: this.urlPreventCache,
            sync: true
        };
        
        
        var getHandler = dojo.xhrGet(getArgs);
        getHandler.addCallback(function(data){
            try{
                dojo.byId("token").innerHTML = data;
            }catch(e){
                console.log(e);
                throw e;
            }
        });
        getHandler.addErrback(function(error){
            throw error;
        });  
        
    },
    getTokenParam:function(){
        var myParam;
        try{
            switch(arguments.length) {
                case 1:
                    myParam = arguments[0];
                    break;
                default:
                    myParam = new Object();
                    break;
            }

            this.reloadToken();
        
            var tokenDiv = document.getElementById("token");
            var els0 = tokenDiv.children[0];
            var els1 = tokenDiv.children[1];
            myParam[els0.name] = els0.value;
            myParam[els1.name] = els1.value;
        }catch(e){}
        return myParam;
    },
    getTokenParamString:function(){
        var myParam;
        try{
            this.isLoaded = false;
            this.reloadToken();
        
            var tokenDiv = document.getElementById("token");
            var els0 = tokenDiv.children[0];
            var els1 = tokenDiv.children[1];
        
            myParam=els0.name+"="+els0.value;
            myParam+="&"+els1.name +"="+ els1.value;
        }catch(e){}
        return myParam;
    }    
}
