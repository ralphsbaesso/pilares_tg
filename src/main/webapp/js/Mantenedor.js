var retorno;

class Mantendor{

	salvar(mantenedor) {
		
		var json = JSON.stringify(mantenedor);
		
		$.ajax(
				{
					url : "Mantendor",
					type : 'post',
					dataType : 'json',
					data : {
						operacao : "salvar",
						mantenedor : json

					},
					async: false,
					beforeSend : function() {
						// carregando ...
					}
				}).done(
				function(msg) {
					retorno = msg;
					
				}).fail(function(jqXHR, textStatus, msg) {
					retorno = msg;
		});
		
		return retorno;
	}
	
	alterar(mantenedor) {
		
		var json = JSON.stringify(mantenedor);

		$.ajax(
				{
					url : "Mantendor",
					type : 'post',
					dataType : 'json',
					data : {
						operacao : "alterar",
						mantenedor : json

					},
					async: false,

					beforeSend : function() {
						// carregando
					}
				}).done(
				function(msg) {
					retorno = msg;
					
				}).fail(function(jqXHR, textStatus, msg) {
					retorno = msg
		});
		
		return retorno;
	}

	excluir(mantenedor) {
		
		var json = JSON.stringify(mantenedor);

		$.ajax(
				{
					url : "Mantendor",
					type : 'post',
					dataType : 'json',
					data : {
						operacao : "excluir",
						mantenedor : json

					},
					async: false,
					beforeSend : function() {
						// carregando ... 
					}
				}).done(
				function(msg) {
					retorno = msg;
					
				}).fail(function(jqXHR, textStatus, msg) {
					retorno = msg;
		});
		
		return retorno;
	}
			
	listar(mantenedor) {		
		
		var json = JSON.stringify(mantenedor);
		
		$.ajax(
				{
					url : "Mantendor",
					type : 'post',
					dataType : 'json',
					data : {
						operacao : "listar",
						mantenedor : json
					},
					async: false,
					beforeSend : function() {
						$("#sucesso").html(
								"<p align='center'>Carregando...</p>");
					}
				}).done(function(msg) {

					retorno = msg;
					
				}).fail(function(jqXHR, textStatus, msg) {
					retorno = msg;
		});
		
		return retorno;		
	}
}