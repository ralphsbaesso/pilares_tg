/**
 * 
 * @returns
 */
$(document).ready(function(){
	
	
	especialdiade = new Object();
	
	
	// OPERAÇÃO
	$("body").on('click', '#operacao', function(){
		
		var operacao = $('#operacao').val().toLowerCase();
		if(operacao == 'salvar'){
			salvar();
		}else if(operacao == 'alterar'){
			alterar();
		}else if(operacao == 'excluir'){
			excluir();
		}else if(operacao == 'listar'){
			listar();
		}
	});
	
	/**
	 * linkCadastrar - exibe o formulário de cadastro
	 */
	$('#linkCadastrar').on('click', function(){
	
		$("#divBody").html(montarCadastro());
		
		
	});//linkCadastrar
	
	/**
	 * linkPesquisar - exibe formulário para pesquisa
	 */
	$('#linkPesquisar').click(function(){
		
		
		$('#divBody').html(montarPesquisa());
		
	
	});//linkPesquisar
	
	// linkListar - exibe todos os mantenedores
	$('#linkListar').click(function(){
		
		listar();
	
	});//linkListar
	
	
	// buttonAlterar
	$('body').on('click', '.buttonAlterar', function(){
		
		//var id = $(this).attr('id');
		var linhaTR = $(this).parent().parent().parent();
		var id = linhaTR.find("td").eq(0).html();
		var codigo = linhaTR.find("td").eq(1).html();
		var descricao = linhaTR.find("td").eq(2).html();
		var detalhamento = linhaTR.find("td").eq(3).html();
		var dataCadastro = linhaTR.find("td").eq(4).html();
		
		this.especialidade = new Object();
		
		windows.especialidade.id = id;
		windows.especialidade.codigo = codigo;
		windows.especialidade.descricao = descricao;
		windows.especialidade.detalhamento = detalhamento;
		windows.especialidade.dataCadastro = dataCadastro;
		
		$('#divBody').html(montarAlterarExcluir());
		
//		alert(id);
//		alert(codigo);
//		alert(descricao);
//		alert(detalhamento);
//		alert(dataCadastro);
	});
	
	// buttonExcluir
	$('body').on('click', '.buttonExcluir', function(){
		
		var id = $(this).attr('id');
		
		excluir(id);
	});
	
	
//	//botão adicionarEspecialidade
//	$('body').on('click','#addEspecialidade', function(){
//		//alert('ok');
//		var clone = $('#selectEspecialidade').clone();
//		clone.children('button').remove();
//		clone.removeAttr("id");
//		clone.append("&nbsp;<button type='button' class='form-control btn-default deleteEspecialidade'>Remover Especialidade</button>");
//		clone.hide();
//		$('#selectEspecialidade').after(clone);
//		clone.slideDown();
//		
//	});// botão adicionarEspecialidade
	
//	// botão deleteEspecialidade
//	$("body").on("click",".deleteEspecialidade", function(){
//		
//		 var indice = $(".deleteEspecialidade").index(this);
//		
//          $(".deleteEspecialidade").eq(indice).parent().slideUp('fast', function() {  
//               $(this).remove();  
//          })  
//         
//    }); // deleteEspecialidade

/* FUNÇÕES CHAMADAS AJAX */
	
	function listar(){
		
		$.ajax({
	          url : "Especialidade",
	          type : 'post',
	          dataType : 'html',
	          data : {
	               operacao : "listar"
	          },
	          beforeSend : function(){
	               $("#divBody").html("<p align='center'>Carregando...</p>");
	          }
	     })
	     .done(function(msg){
	    	 
	    	var json = JSON.parse(msg);
			var especialidade = json.entidade;
			var especialidades = json.entidades;
			var mensagens = json.mensagens;
			var semafaro = json.semafaro;
			
			var mensagem = "";
	    	 
	    	 
	    	 // verificar respostas
	    	 if(semafaro == 'VERDE'){
	    		
		    	 var tBody = "";
		    	 
		    	 
		    	 for(var i = 0; i < especialidades.length; i++){
		    		 
		    		 especialidade = especialidades[i];
		    		 
		    		 tBody += makeTableTBody(
	 	 					makeTableTR(
	    			 				makeTableTD(especialidade.id) +
	    			 				makeTableTD(especialidade.codigo) +
	    			 				makeTableTD(especialidade.descricao) +
	    			 				makeTableTD(especialidade.detalhamento) +
	    			 				makeTableTD(formatarData(especialidade.dataCadastro))+
    			 					makeTableTDDoublo(htmlButtonUpdate(especialidade.id), htmlButtonDelete(especialidade.id))
	    	 					)		
	    			 		);
		    	 }
		    	 
		    	 var table = makeTable(
		    			 		makeTableTHead(
		    			 			makeTableTR(
		    			 					makeTableTD('ID') +
		    			 					makeTableTD('Código') +
		    			 					makeTableTD('Descrição') +
		    			 					makeTableTD('Detalhamento') +
		    			 					makeTableTD('Data Cadastro') 
		    			 			)
		    			 		) + 
		    	 				tBody +
		    			 		makeTableTFoot("Quantidade de Especialidades cadastradas: " + especialidades.length, especialidades.length)
		    	 );
		    	 
		    	 $('#divBody').html('');
		    	 $('#divBody').append(table)
	    	 }else if(semafaro == 'VERMELHO'){
	    		 
	    		 if(mensagens != null && mensagens.length > 0){
	    			 
	    			 for(var i = 0; i < mensagens.length; i++){
	    				 mensagem += mensagens[i];
	    				 mensagem += "</br>";
	    			 }
	    		 }else{
	    			 mensagem = "Erro!"
	    		 }
	    		 
	    		 modalAdvertencia(mensagem);
	    	 }
	     })
	     .fail(function(jqXHR, textStatus, msg){
	          alert(msg);
	     }); 
	}

	function salvar(){
		
		//var especialidade = new Object();
		
		especialidade.descricao = $("[name = txtDescricao]").val();
		especialidade.detalhamento = $("[name = txtDetalhamento]").val();
		
		var json = JSON.stringify(especialidade);
		
		$.ajax({
	          url : "Especialidade",
	          type : 'post',
	          dataType : 'json',
	          data : {
	               operacao : "salvar",
	               especialidade : json
	               
	          },
	          
	          beforeSend : function(){
	               $("#sucesso").html("<p align='center'>Carregando...</p>");
	          }
	     })
	     .done(function(msg){
	    	 
			var json = JSON.parse(msg);
			var especialidade = json.entidade;
			var especialidades = json.entidades;
			var mensagens = json.mensagens;
			var semafaro = json.semafaro;
			
			var mensagem = "";
	    	 
	    	 // verificar respostas
	    	 if(semafaro == 'VERDE'){
	    		 
	    		 $('#divBody').html("");
	    		 $('#divBody').append(makeMensagemSucesso("Especialidade " + especialidade.descricao + " salvo com sucesso!"));
	    		 
	    	 }else if(semafaro == 'VERMELHO'){
	    		 
	    		 if(mensagens != null && mensagens.length > 0){
	    			 
	    			 for(var i = 0; i < mensagens.length; i++){
	    				 mensagem += mensagens[i];
	    				 mensagem += "</br>";
	    			 }
	    		 }else{
	    			 mensagem = "Erro!"
	    		 }
	    		 
	    		 modalAdvertencia(mensagem);
	    	 }
	     })
	     .fail(function(jqXHR, textStatus, msg){
	          alert(msg + " Erro grave!!!");
	     });
	}
	
	
	function excluir(id){
		
		$.ajax({
	          url : "Especialidade",
	          type : 'post',
	          dataType : 'html',
	          data : {
	              operacao : "excluir",
	              txtId : id
	          },
	          
	          beforeSend : function(){
	               $("#sucesso").html("<p align='center'>Carregando...</p>");
	          }
	     })
	     .done(function(msg){
	    	 
			var json = JSON.parse(msg);
			var especialidade = json.entidade;
			var especialidades = json.entidades;
			var mensagens = json.mensagens;
			var semafaro = json.semafaro;
			
			var mensagem = "";
	    	 
	    	 // verificar respostas
	    	 if(semafaro == 'VERDE'){
	    		 
	    		 $('#divBody').html("");
	    		 $('#divBody').append(makeMensagemSucesso("Especialidade " + especialidade.descricao + " excluida com sucesso!"));
	    		 
	    	 }else if(semafaro == 'VERMELHO'){
	    		 
	    		 if(mensagens != null && mensagens.length > 0){
	    			 
	    			 for(var i = 0; i < mensagens.length; i++){
	    				 mensagem += mensagens[i];
	    				 mensagem += "</br>";
	    			 }
	    		 }else{
	    			 mensagem = "Erro!"
	    		 }
	    		 
	    		 modalAdvertencia(mensagem);
	    	 }
	     })
	     .fail(function(jqXHR, textStatus, msg){
	          alert(msg + " Erro grave!!!");
	     });
		
	}
	
	function alterar(){
		
		//var especialidade = new Object();
		
		this.especialidade.descricao;
		this.especialidade.detalhamento;
		
		var json = JSON.stringify(this.especialidade);
		
		$.ajax({
	          url : "Especialidade",
	          type : 'post',
	          dataType : 'html',
	          data : {
	               operacao : "salvar",
	               especialidade : json
	          },
	          
	          beforeSend : function(){
	               $("#sucesso").html("<p align='center'>Carregando...</p>");
	          }
	     })
	     .done(function(msg){
	    	 
			var json = JSON.parse(msg);
			var especialidade = json.entidade;
			var especialidades = json.entidades;
			var mensagens = json.mensagens;
			var semafaro = json.semafaro;
			
			var mensagem = "";
	    	 
	    	 // verificar respostas
	    	 if(semafaro == 'VERDE'){
	    		 
	    		 $('#divBody').html("");
	    		 $('#divBody').append(makeMensagemSucesso("Especialidade " + especialidade.descricao + " alterada com sucesso!"));
	    		 
	    	 }else if(semafaro == 'VERMELHO'){
	    		 
	    		 if(mensagens != null && mensagens.length > 0){
	    			 
	    			 for(var i = 0; i < mensagens.length; i++){
	    				 mensagem += mensagens[i];
	    				 mensagem += "</br>";
	    			 }
	    		 }else{
	    			 mensagem = "Erro!"
	    		 }
	    		 
	    		 modalAdvertencia(mensagem);
	    	 }
	     })
	     .fail(function(jqXHR, textStatus, msg){
	          alert(msg + " Erro grave!!!");
	     });
	}
	
	
/* FUNÇÕES DE MONTAGEM DE COPONENTES HTML*/
	
	// montar cadastro
	function montarCadastro(){
		return"<div class='py-5'>" +
		"<div class='container'>" +
		"<div class='row'>" +
		"<div class='col-md-8'>" +
		"<h1 class='display-4'>Cadastro</h1>" +
		"<ul class='list-group'>" +
		"<li class='list-group-item form-inline'> <label class='form-control bg-secondary text-white'>Descrição</label>" +
		"<input class='form-control' type='text' placeholder='Digite aqui..' id='txtDescricao' name='txtDescricao'> </li>" +
		"<li class='list-group-item form-inline'> <label class='form-control bg-secondary text-white'>Detalhamento</label>" +
		"<input class='form-control' type='text' placeholder='Digite aqui..' id='txtDetalhamento' name='txtDetalhamento'> </li>" +
		"<li class='list-group-item form-inline'>" +
		"<button class='btn btn-outline-success form-control' id='operacao' value='salvar'>Salvar</button>" +
		"<button class='btn btn-outline-warning form-control'>Limpar</button>" +
		"</li>" +
		"</ul>" +
		"</div>" +
		"</div>" +
		"</div>" +
		"</div>";
	}
	
	// montar pesquisa
	function montarPesquisa(){
		return "<div class='py-5'>" +
		"<div class='container'>" +
		"<div class='row'>" +
		"<div class='col-md-8'>" +
		"<h1 class='display-4'>Pesquisa</h1>" +
		"<ul class='list-group'>" +
		"<li class='list-group-item form-inline'> <label class='form-control bg-secondary text-white'>Código</label>" +
		"<input class='form-control' type='text' placeholder='Digite aqui..' id='txtDescricao' name='txtDescricao'> </li>" +
		"<li class='list-group-item form-inline'> <label class='form-control bg-secondary text-white' >Descrição</label>" +
		"<input class='form-control' type='text' placeholder='Digite aqui..'id='txtDetalhamento' name='txtDescricao'> </li>" +
		"<li class='list-group-item form-inline'>" +
		"<button class='btn btn-outline-success form-control btn-sm w-100' id='operacao'>Pesquisar</button>" +
		"</li>" +
		"</ul>" +
		"</div>" +
		"</div>" +
		"</div>" +
		"</div>" ;
	}
	
	function montarAlterarExcluir(){
		
		return "<div class='py-5'>" +
		"<div class='container'>" +
		"<div class='row'>" +
		"<div class='col-md-8'>" +
		"<h1 class='display-4'>Alterar</h1>" +
		"<ul class='list-group'>" +
		"<li class='list-group-item form-inline'> <label class='form-control bg-secondary text-white' id='txtDescricao' name='txtDescricao'>Código</label>" +
		"<strong>" +
		"<label class='form-control' id='txtDescricao' name='txtDescricao'>" + windows.especialidade.codigo + "</label>" +
		"</strong>" +
		"<li class='list-group-item form-inline'> <label class='form-control bg-secondary text-white' id='txtDescricao' name='txtDescricao'>Descrição</label>" +
		"<input class='form-control' type='text' value='" + windows.especialidade.descricao + "'> </li>" +
		"<li class='list-group-item form-inline'> <label class='form-control bg-secondary text-white' id='txtDetalhamento' name='txtDetalhamento'>Detalhamento</label>" +
		"<input class='form-control w-75' type='text' value='" + windows.especialidade.detalhamento + "'> </li>" +
		"<li class='list-group-item form-inline'>" +
		"<button class='btn btn-outline-warning form-control' name='operacao' id='operacao' value='alterar' style='width:49%'>Alterar</button>" +
		"<button class='btn btn-outline-danger form-control' name='operacao' id='operacao' style='width:49%'>Excluir</button>" +
		"</li>" +
		"</ul>" +
		"</div>" +
		"</div>" +
		"</div>" +
		"</div>";
	}
	
	// montar lista
	function montarLista(){
		
		entidades = aArgs;
		//alert(entidades[0].descricao);
		//alert(entidades[1].descricao);
		var tbody ='<tbody>';
		for(var i = 0; i < entidades.lenght; i++){
			tbody += " <tr id='" + entidades[i].id + "> " +
						"<td>" + entidades[i].descricao + "</td>" + 
						"<td>" + entidades[i].detalhemento + "</td>" +
						"<td>" + entidades[i].codigo + "</td>" + 
						"<td>" + entidades[i].dataCadastro + "</td>" +
						"<td>" + entidades[i].autor + "</td>" + 
						"<td><input id='listar' class='form-control bg-sucesso btn btn-outline-sucesso' type='submit' value='Consultar'></td>" +
					"</tr>";
		}
		
		tbody += "</tbody>";
		
		return "<div class='py-5'>" +
	    "<div class='container'>" +
	      "<div class='row'>" +
	        "<div class='col-md-12'>" +
	          "<h1 class=''>Especialidades</h1>" +
	        "</div>" +
	      "</div>" +
	      "<div class='row'>" +
	        "<div class='col-md-12'>" +
	          "<table class='table'>" +
	            "<thead>" +
	              "<tr>" +
	                "<th>Descrição</th>" +
	                "<th>Detalhamento</th>" +
	                "<th>Código</th>" +
	                "<th>Data Cadastro</th>" +
	                "<th>Autor</th>" +
	                "<th></th>" +
	              "</tr>" +
	            "</thead>" +
	            tbody +
	             "<tfoot>" +
	              "<tr>" +
	                "<td colspan='6'> <label class='form-control'>Total de Especialidades: " + entidades.length + "</label> </td>" +
	              "</tr>" +
	            "</tfoot>" +
	          "</table>" +
	        "</div>" +
	      "</div>" +
	    "</div>" +
	  "</div>" ;
	}
	
	// montar modal advertência
	function modalAdvertencia(mensagem){
		
		$("#divBody").prepend(
		"<div class='modal fade' id='modalAdvertencia' tabindex='-1' role='dialog' aria-labelledby='myModalLabel'>" +
		  "<div class='modal-dialog' role='document'>" +
		    "<div class='modal-content'>" +
		      "<div class='modal-header'>" +
		        "<button type='button' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'>&times;</span></button>" +
		        "<h4 class='modal-title' id='myModalLabel'>Atenção</h4>" +
		      "</div>" +
		      "<div class='modal-body'>" +
		        mensagem +
		      "</div>" +
		      "<div class='modal-footer'>" +
		        "<button type='button' class='btn btn-default' data-dismiss='modal'>Fechar</button>" +
		      "</div>" +
		    "</div>" +
		  "</div>" +
		"</div> " );
		
		// abrir modal
		 $('#modalAdvertencia').modal('show');
		 $('.modal-backdrop').css({
			   'background-color' : '#FFE4C4',
			   'opacity' : 0.4
		 });
		 // fechando o modal
		 $('#modalAdvertencia').on('hide.bs.modal', function(){
			 $('#modalAdvertencia .modal-body').html("");
			 $('#divAdvertencia').html('');
			 // limpando as mensagens
		 });
	}
	
	//montar modal Alterar
	function modalAlterarEspecialidade(){
		
		var especialidade = arguments;
		
		$("#divBody").prepend(
		"<div class='modal fade' id='modalAdvertencia' tabindex='-1' role='dialog' aria-labelledby='myModalLabel'>" +
		  "<div class='modal-dialog' role='document'>" +
		    "<div class='modal-content'>" +
		      "<div class='modal-header'>" +
		        "<button type='button' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'>&times;</span></button>" +
		        "<h4 class='modal-title' id='myModalLabel'>Atenção</h4>" +
		      "</div>" +
		      "<div class='modal-body'>" +
			      "<ul class='list-group'>" +
		            "<li class='list-group-item'>Código " + especialidade.codigo + "</li>" +
		            "<li class='list-group-item'>Descrição " + especialidade.descricao + "</li>" +
		            "<li class='list-group-item'>Detalhamento " + especialidade.detalhamento + "</li>" +
		            "<li class='list-group-item'>Porta ac consectetur</li>" +
		          "</ul>" +
		      "</div>" +
		      "<div class='modal-footer'>" +
		        "<button type='button' class='btn btn-default' data-dismiss='modal'>Fechar</button>" +
		      "</div>" +
		    "</div>" +
		  "</div>" +
		"</div> " );
		
		// abrir modal
		 $('#modalAdvertencia').modal('show');
		 $('.modal-backdrop').css({
			   'background-color' : '#FFE4C4',
			   'opacity' : 0.4
		 });
		 // fechando o modal
		 $('#modalAdvertencia').on('hide.bs.modal', function(){
			 $('#modalAdvertencia .modal-body').html("");
			 $('#divAdvertencia').html('');
			 // limpando as mensagens
		 });
	}
	
}); // (document).ready