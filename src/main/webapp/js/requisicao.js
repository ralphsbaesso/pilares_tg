/* Requisicao AJAX*/
	function requisicaoAjax(entidade, requisicao, servlet){
		
		var entidadeJson = JSON.stringify(entidade);
		var requisicaoJson = JSON.stringify(requisicao)
		var resposta = "";
	
		$.ajax(
				{
					url : servlet,
					type : 'post',
					async: false,
					dataType : 'json',
					data : {
						requisicao : requisicaoJson,
						entidade : entidadeJson
	
					},
	
					beforeSend : function() {
						$("#sucesso").html(
								"<p align='center'>Carregando...</p>");
					}
				})
				.done(function(msg) {
					retorno = msg;
				}).fail(function(jqXHR, textStatus, msg) {
					retorno = msg
		});
		
		return retorno;
	}
	
	/* Requisicao POST */
	function requisicaoPost(entidade, requisicao, servlet){
		
		var entidadeJson = JSON.stringify(entidade);
		var requisicaoJson = JSON.stringify(requisicao)
		
		var form = document.createElement("form");
		form.setAttribute("method", "post");
		form.setAttribute("action", servlet);
		
		var ent = document.createElement("input");
		ent.setAttribute("name", "entidade");
		ent.setAttribute("value", entidadeJson);
		ent.setAttribute("type", "hidden");
		
		var req = document.createElement("input");
		req.setAttribute("name", "requisicao");
		req.setAttribute("value", requisicaoJson);
		req.setAttribute("type", "hidden");
		
		form.appendChild(ent);
		form.appendChild(req);
		document.body.appendChild(form);
		form.submit();
	}