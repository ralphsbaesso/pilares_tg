var retorno;

class Especialidade{

	salvar(especialidade) {
		
		var json = JSON.stringify(especialidade);
		
		$.ajax(
				{
					url : "Especialidade",
					type : 'post',
					dataType : 'json',
					data : {
						operacao : "salvar",
						especialidade : json

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
	
	alterar(especialidade) {
		
		var json = JSON.stringify(especialidade);

		$.ajax(
				{
					url : "Especialidade",
					type : 'post',
					dataType : 'json',
					data : {
						operacao : "alterar",
						especialidade : json

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

	excluir(especialidade) {
		
		var json = JSON.stringify(especialidade);

		$.ajax(
				{
					url : "Especialidade",
					type : 'post',
					dataType : 'json',
					data : {
						operacao : "excluir",
						especialidade : json

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
			
	listar(especialidade) {		
		
		var json = JSON.stringify(especialidade);
		
		$.ajax(
				{
					url : "Especialidade",
					type : 'post',
					dataType : 'json',
					data : {
						operacao : "listar",
						especialidade : json
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