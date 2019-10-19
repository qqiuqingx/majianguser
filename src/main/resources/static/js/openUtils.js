jQuery.fn.serializeObject = function () {
    var formData = {};
    var formArray = this.serializeArray();
    for(var i = 0, n = formArray.length; i < n; ++i){
        formData[formArray[i].name] = formArray[i].value;
    }
    return formData;
};
