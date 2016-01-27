/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
crypt = {
    encodeParams:function(_d,miArgs,preventCache,arrUrl){
        var pSplit='';
        if (arrUrl.length>1) pSplit=arrUrl[1];
        if (sd.operator.isCryptParameter){
            var plaintext = pSplit+'&'+_d.objectToQuery(_d.mixin.apply(null, miArgs));
            if (plaintext!=null && plaintext!=""){
                var keystring = sd.operator.keyString;
                var ivstring = sd.operator.ivString;
                var md5String = MD5.getDigest(keystring),
                keybytes = cryptoHelpers.toNumbers(md5String),
                iv =cryptoHelpers.convertStringToByteArray(ivstring),
                keysize, key = cryptoHelpers.toHex(keybytes),
                bytesToEncrypt, mode, result,
                decrypted, recoveredText, stopwatch;
                stopwatch = new Stopwatch();
                stopwatch.start();
                keysize = slowAES.aes.keySize.SIZE_128;
                bytesToEncrypt = cryptoHelpers.convertStringToByteArray(plaintext);
                mode = slowAES.modeOfOperation.CBC;
                result = slowAES.encrypt(bytesToEncrypt, mode, keybytes, keysize, iv);
                return "vt="+cryptoHelpers.toHex(result.cipher)+"&dojo.preventCache="+new Date().valueOf();
            }
            else return"";
        }else {
            if(preventCache){
                miArgs.push({
                    "dojo.preventCache": new Date().valueOf()
                });
            }
            return pSplit+'&'+_d.objectToQuery(_d.mixin.apply(null, miArgs));
        }
    },
    encode:function(keystring,ivstring,plaintext){

        if (sd.operator.isCryptParameter){
            if (plaintext!=null && plaintext!=""){
                var md5String = MD5.getDigest(keystring),
                keybytes = cryptoHelpers.toNumbers(md5String),
                iv =cryptoHelpers.convertStringToByteArray(ivstring),
                keysize, key = cryptoHelpers.toHex(keybytes),
                bytesToEncrypt, mode, result,
                decrypted, recoveredText, stopwatch;
                stopwatch = new Stopwatch();
                stopwatch.start();
                keysize = slowAES.aes.keySize.SIZE_128;
                bytesToEncrypt = cryptoHelpers.convertStringToByteArray(plaintext);
                mode = slowAES.modeOfOperation.CBC;
                result = slowAES.encrypt(bytesToEncrypt, mode, keybytes, keysize, iv);
                return "vt="+cryptoHelpers.toHex(result.cipher)+"&dojo.preventCache="+new Date().valueOf();
            }
            else return"";
        }else return plaintext;
    }
};
