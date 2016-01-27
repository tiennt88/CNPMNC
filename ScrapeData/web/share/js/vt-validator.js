/**
 * Author: TienNT
 * Description: Cung cấp các API validator
 * Date: 10/07/2011
 * FWVersion: 3.3
 **/

sd.validator = {

    stack : {},
    stackPrefix : "",

    initStack : function( stackId ) {
        this.stack[this.stackPrefix + stackId] = [];
    },

    getStack : function( stackId ) {
        return this.stack[this.stackPrefix + stackId];
    },

    clearStack : function( stackId ) {
        try {
            delete this.stack[this.stackPrefix + stackId];
        }
        catch( e ) {
            this.stack[this.stackPrefix + stackId] = undefined;
        }
    },

    clearStacks : function() {
        try {
            delete this.stack;
        }
        catch( e ) {

        }
        this.stack = {};
    },

    testStack : function( stackId ) {
        var validatorArr = this.getStack( stackId );
        var validator, i;
        if( validatorArr ) {

            for( i = 0; i < validatorArr.length; i++ ) {
                validator = validatorArr[i];
                if( !validator.call() ) {
                    return false;
                }
            }
            return true;
        }
        else {
            alert( "TienNT says: ValidatorStack is not available." );
        }
        return false;
    },

    trim : function( stringToTrim ) {
        return stringToTrim.replace( /^\s+|\s+$/g, "" );
    },

    ltrim : function( stringToTrim ) {
        return stringToTrim.replace( /^\s+/, "" );
    },

    rtrim : function( stringToTrim ) {
        return stringToTrim.replace( /\s+$/, "" );
    },

    isHTMLText : function( html ) {
        var reDoubleTag = /<.+>.*<\/.+>/;
        var reSingleTag = /<.+\/?>?/;
        var res = ( reDoubleTag.test( html ) || reSingleTag.test( html ) );

        return res;
    },

    isValidInput : function( ) {
        var sText = "";
        var bEmptyAllowed = true;
        var bSpaceAllowed = true;
        var re;

        switch( arguments.length ) {
            case 1:
                sText = arguments[0];
                break;
            case 2:
                sText = arguments[0];
                bEmptyAllowed = arguments[1];
                break;
            case 3:
                sText = arguments[0];
                bEmptyAllowed = arguments[1];
                bSpaceAllowed = arguments[2];
                break;
        }

        if( bEmptyAllowed ) {
            if( bSpaceAllowed )
                re = /^[a-zA-Z0-9_\-\s]*$/;
            else
                re = /^[a-zA-Z0-9_\-]*/;
        }
        else {
            if( bSpaceAllowed )
                re = /^[a-zA-Z0-9_\-\s]+$/;
            else
                re = /^[a-zA-Z0-9_\-]+$/;
        }

        return re.test( sText );

    },

    isNaturalNumber : function( sText ) {
        var re = /^[\d]+$/;
        return re.test( sText );
    },

    isIntegerNumber : function( sText ) {
        var re = /^\-?[\d]+$/;
        return re.test( sText );
    },

    isFloatNumber : function( sText ) {
        if( sText.toString( ) == '-0' ) return false;

        var re = /^\-?[\d]+$/;
        if( re.test( sText ) ) return true;
        re = /^\-?[\d]+\.[\d]+$/;
        return re.test( sText );
    },

    isNumberFormat : function( sText, sFormat ) {
        var specCharPattern = /[\D]/;
        var specChar = sFormat.match( specCharPattern );
        var aNum = sFormat.split( specChar );

        var sCmd = "var re = /^";
        for( var iC = 0; iC < aNum.length; iC++ ) {
            if( iC != 0 )
                sCmd += "\\" + specChar;
            sCmd += "[0-9]{" + aNum[iC] + "}";
        }
        sCmd += "$/;";
        eval( sCmd );
        return re.test( sText );
    },

    isEmail : function( sText ) {
        var str = sText;
        if( str == "" ) {
            //alert("Verify the email address format.");
            return false;
        }
        var re = /^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]{2,7}$/;
        if ( !str.match( re ) ) {
            //alert("Verify the email address format.");
            return false;
        } else {
            return true;
        }
    },

    isDateFormat : function( value ) {

        var date=value.substr(0,10);



        // Regular expression used to check if date is in correct format

        var pattern = new RegExp(/^\d{1,2}(\-|\/|\.)\d{1,2}\1\d{4}$/);



        // kiem tra date

        if(date.match(pattern)){

            var date_array = date.split('/');

            var day = date_array[0];



            // Attention! Javascript consider months in the range 0 - 11

            var month = date_array[1] - 1;

            var year = date_array[2];



            // This instruction will create a date object

            source_date = new Date(year,month,day);



            if(year != source_date.getFullYear())

            {

                return false;

            }



            if(month != source_date.getMonth())

            {

                return false;

            }



            if(day != source_date.getDate())

            {

                return false;

            }

        }else {

        return false;

    }



    // kiem tra time

    if(value.length>10){

        var time=value.substr(11);

        if(time.length ==8){

            var hour=time.substr(0,2);

            var minute=time.substr(3,2);

            var second=time.substr(6);



            if(parseInt(hour) > 23 && parseInt(hour) < 0){

                return false;

            }

            if(parseInt(minute) > 60 && parseInt(minute) < 0){

                return false;

            }

            if(parseInt(second) > 60 && parseInt(second) < 0){

                return false;

            }

        }else{

        return false;

    }

    }



    return true;

    },

    isDate : function( value )
    {
        var date=value.substr(0,10);

        // Regular expression used to check if date is in correct format
        var pattern = new RegExp(/^\d{1,2}(\-|\/|\.)\d{1,2}\1\d{4}$/);

        // kiem tra date
        if(date.match(pattern)){
            var date_array = date.split('/');
            var day = date_array[0];

            // Attention! Javascript consider months in the range 0 - 11
            var month = date_array[1] - 1;
            var year = date_array[2];

            // This instruction will create a date object
            source_date = new Date(year,month,day);

            if(year != source_date.getFullYear())
            {
                    return false;
            }

            if(month != source_date.getMonth())
            {
            return false;
            }

            if(day != source_date.getDate())
            {
            return false;
            }
        }else {
            return false;
        }

        // kiem tra time
        if(value.length>10){
            var time=value.substr(11);
            if(time.length ==8){
                var hour=time.substr(0,2);
                var minute=time.substr(3,2);
                var second=time.substr(6);

                if(parseInt(hour) > 23 && parseInt(hour) < 0){
                    return false;
                }
                if(parseInt(minute) > 60 && parseInt(minute) < 0){
                    return false;
                }
                if(parseInt(second) > 60 && parseInt(second) < 0){
                     return false;
                }
            }else{
                return false;
            }
        }

        return true;
    },

    //ham so sanh xem date1 co nho hon date2 ko? date
    //pattern = "dd/MM/yyyy HH:mm:ss"
    compareDates : function( date1,date2 ) {

        var arrayDate1 = date1.split("/");

        var arrayDate2 = date2.split("/");

        var arrayTime1 = date1.split(":");
        var arrayTime2 = date2.split(":");

        var nam1   = parseFloat(arrayDate1[2]);

        var nam2   = parseFloat(arrayDate2[2]);

        var thang1 = parseFloat(arrayDate1[1]);

        var thang2 = parseFloat(arrayDate2[1]);

        var ngay1  = parseFloat(arrayDate1[0]);

        var ngay2  = parseFloat(arrayDate2[0]);


        var h1 = parseFloat(arrayTime1[0]);
        var h2 = parseFloat(arrayTime2[0]);

        var p1 = parseFloat(arrayTime1[1]);
        var p2 = parseFloat(arrayTime2[1]);

        var s1 = parseFloat(arrayTime1[2]);
        var s2 = parseFloat(arrayTime2[2]);


        if(nam1 < nam2){
            return true;
        }
        if(nam1 == nam2){
            if(thang1 < thang2){
                return true;
            }
            if(thang1 == thang2){
                if(ngay1 < ngay2){
                    return true;
                }
                if( ngay1 == ngay2) {
                    if( h1 < h2){
                        return true;
                    }
                    if( p1 < p2){
                        return true;
                    }
                    if( s1 < s2){
                        return true;
                    }
               }
            }
        }
        return false;

    },

    daysBetween : function(date1, date2) {

        var arrayDate1 = date1.split("/");

        var arrayDate2 = date2.split("/");



        var startYear   = parseFloat(arrayDate1[2]);

        var endYear   = parseFloat(arrayDate2[2]);

        var startMonth = parseFloat(arrayDate1[1]);

        var endMonth = parseFloat(arrayDate2[1]);

        var startDay  = parseFloat(arrayDate1[0]);

        var endDay  = parseFloat(arrayDate2[0]);



        var start = new Date(startYear,startMonth-1,startDay);

        var stop  = new Date(endYear,endMonth-1,endDay);

        var diff  = (stop - start) / (1000 * 60 * 60 * 24);

        return diff;

    }

};