$(document).ready(function() {
    $('#loading').hide();
    $('#q').keyup(function(){
      $('#loading').show();
      $.post("keyterms.php", {
        q: $('#q').val()
      }, function(response){
        $('#qResult').fadeOut();
        setTimeout("finishAjax('qResult', '"+escape(response)+"')", 400);
      });
        return false;
    });
});

