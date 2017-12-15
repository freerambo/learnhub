$(document).ready(function () {
   
    $("#mBtnSubmit").click(function (event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();
        m_fire_ajax_submit();
    });

});

    function m_fire_ajax_submit() {

        // Get form
        var form = $('#mFileUploadForm')[0];

        var data = new FormData(form);

        //data.append("CustomField", "This is some extra data, testing");

        $("#mBtnSubmit").prop("disabled", true);

        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "/api/upload/multi",
            data: data,
            //http://api.jquery.com/jQuery.ajax/
            //https://developer.mozilla.org/en-US/docs/Web/API/FormData/Using_FormData_Objects
            processData: false, //prevent jQuery from automatically transforming the data into a query string
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function (data) {

                $("#result").text(data);
                console.log("SUCCESS : ", data);
                $("#mBtnSubmit").prop("disabled", false);

            },
            error: function (e) {
                $("#result").text(e.responseText);
                console.log("ERROR : ", e);
                $("#mBtnSubmit").prop("disabled", false);
            }
        });

}