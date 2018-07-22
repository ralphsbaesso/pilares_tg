/**
 * 
 * @returns
 */

$(document).ready(function(){
	
	// OPERAÇÃO
	$("body").on('click', '#operacao', function(){
		
		var operacao = $(this).val().toLowerCase();
		if(operacao == 'salvar'){
			salvar();
		}else if(operacao == 'alterar'){
			alterar();
		}else if(operacao == 'excluir'){
			excluir();
		}else if(operacao == 'listar'){
			listar();
		}else if(operacao == 'limparcampos'){
			
			limparCampos();			
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

		//listar tudo, precisar mandar um objeto vazio
		
		listar.call(this, new Object());
	
	});//linkListar
	
	
	// buttonAlterar
	$('body').on('click', '.buttonAlterar', function(){
		
		var linhaTR = $(this).parent().parent().parent();
		var id = linhaTR.find("td").eq(0).html();
		var codigo = linhaTR.find("td").eq(1).html();
		var descricao = linhaTR.find("td").eq(2).html();
		var detalhamento = linhaTR.find("td").eq(3).html();
		var dataCadastro = linhaTR.find("td").eq(4).html();
		
		centroCusto = new Object();
		
		centroCusto.id = id;
		centroCusto.codigo = codigo;
		centroCusto.descricao = descricao;
		centroCusto.detalhamento = detalhamento;
		centroCusto.dataCadastro = dataCadastro;
		
		$('#divBody').html(montarAlterarExcluir.call(this, centroCusto));
	
	});
	
	// buttonExcluir
	$('body').on('click', '.buttonExcluir', function(){
		
		var linhaTR = $(this).parent().parent().parent();
		var id = linhaTR.find("td").eq(0).html();
		var codigo = linhaTR.find("td").eq(1).html();
		var descricao = linhaTR.find("td").eq(2).html();
		var detalhamento = linhaTR.find("td").eq(3).html();
		var dataCadastro = linhaTR.find("td").eq(4).html();
		
		centroCusto = new Object();
		
		centroCusto.id = id;
		centroCusto.codigo = codigo;
		centroCusto.descricao = descricao;
		centroCusto.detalhamento = detalhamento;
		
		excluir(centroCusto);
	});

/* FUNÇÕES CHAMADAS AJAX */
	
	function listar(centroCusto){
		
		if(centroCusto == null){
			var centroCusto = new Object();
			
			centroCusto.codigo = $("[name = txtCodigo]").val();
			centroCusto.descricao = $("[name = txtDescricao]").val();
			centroCusto.detalhamento = $("[name = txtDetalhamento]").val();
		}
		
		var requisicao = {operacao: 'LISTAR'};
		var json = requisicaoAjax(centroCusto, requisicao, 'CentroCusto');
		
		var centroCusto = json.entidade;
		var especialidades = json.entidades;
		var mensagens = json.mensagens;
		var semafaro = json.semafaro;
		
		var mensagem = "";
    	 
    	 
    	 // verificar respostas
    	 if(semafaro == 'VERDE'){
    		
	    	 var msgRetorno;
	    	 
	    	 if(especialidades.length == 1){
	    		 msgRetorno = montarAlterarExcluir.call(this, especialidades[0]);
	    	 }else{
	    		 
	    		 var tBody = "";
	    		 
	    		 for(var i = 0; i < especialidades.length; i++){
	    			 
	    			 centroCusto = especialidades[i];
	    			 
	    			 tBody += makeTableTBody(
	    					 makeTableTR(
	    							 makeTableTD(centroCusto.id) +
	    							 makeTableTD(centroCusto.codigo) +
	    							 makeTableTD(centroCusto.descricao) +
	    							 makeTableTD(centroCusto.detalhamento) +
	    							 makeTableTD(formatarData(centroCusto.dataCadastro))+
	    							 makeTableTDDoublo(htmlButtonUpdate(centroCusto.id), htmlButtonDelete(centroCusto.id))
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
	    		 
	    		 msgRetorno = table;
	    		 
	    	 }
	    	 
	    	 
	    	 $('#divBody').html('');
	    	 $('#divBody').append(msgRetorno);
	    	 
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
	}

	function salvar(){
		
		var centroCusto = new Object();
		
		centroCusto.descricao = $("[name = txtDescricao]").val();
		centroCusto.detalhamento = $("[name = txtDetalhamento]").val();
		
		var requisicao = {operacao: 'SALVAR'};
		
		var json = requisicaoAjax(centroCusto, requisicao, 'CentroCusto');

		var centroCusto = json.entidade;
		var especialidades = json.entidades;
		var mensagens = json.mensagens;
		var semafaro = json.semafaro;

		var mensagem = "";

		// verificar respostas
		if (semafaro == 'VERDE') {

			$('#divBody').html("");
			$('#divBody').append(
					makeMensagemSucesso("CentroCusto "
							+ centroCusto.descricao
							+ " salvo com sucesso!"));

		} else if (semafaro == 'VERMELHO') {

			if (mensagens != null && mensagens.length > 0) {

				for (var i = 0; i < mensagens.length; i++) {
					mensagem += mensagens[i];
					mensagem += "</br>";
				}
			} else {
				mensagem = "Erro!"
			}

			modalAdvertencia(mensagem);
		} else{
			modalAdvertencia("Erro desconhecido");
		}
	}
	
	
	function excluir(centroCusto){		
		
		if(centroCusto == null){
			var centroCusto = new Object();
			
			centroCusto.id = $("[name = txtId]").val();
			centroCusto.descricao = $("[name = txtDescricao]").val();
			centroCusto.detalhamento = $("[name = txtDetalhamento]").val();
		}
		
		var requisicao = {operacao: 'EXCLUIR'};
		var json = requisicaoAjax(centroCusto, requisicao, 'CentroCusto');
	    	 
		var centroCusto = json.entidade;
		var especialidades = json.entidades;
		var mensagens = json.mensagens;
		var semafaro = json.semafaro;
		
		var mensagem = "";
    	 
    	 // verificar respostas
    	 if(semafaro == 'VERDE'){
    		 
    		 $('#divBody').html("");
    		 $('#divBody').append(makeMensagemSucesso("CentroCusto " + centroCusto.descricao + " excluida com sucesso!"));
    		 
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
	}
	
	function alterar(centroCusto){
		
		if(centroCusto == null){
			var centroCusto = new Object();	
			
			centroCusto.id = $("[name = txtId]").val();
			centroCusto.descricao = $("[name = txtDescricao]").val();
			centroCusto.detalhamento = $("[name = txtDetalhamento]").val();
		}
		
		var requisicao = {operacao: 'ALTERAR'};
		var json = requisicaoAjax(centroCusto, requisicao, 'CentroCusto');
		
		var centroCusto = json.entidade;
		var especialidades = json.entidades;
		var mensagens = json.mensagens;
		var semafaro = json.semafaro;
		
		var mensagem = "";
    	 
    	 // verificar respostas
    	 if(semafaro == 'VERDE'){
    		 
    		 $('#divBody').html("");
    		 $('#divBody').append(makeMensagemSucesso("Centro de Custo " + centroCusto.descricao + " alterada com sucesso!"));
    		 
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
		"<button class='btn btn-outline-warning form-control' id='operacao' value='limparCampos'>Limpar</button>" +
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
		"<input class='form-control' type='text' placeholder='Digite aqui..' id='txtCodigo' name='txtCodigo'> </li>" +
		"<li class='list-group-item form-inline'> <label class='form-control bg-secondary text-white' >Descrição</label>" +
		"<input class='form-control' type='text' placeholder='Digite aqui..'id='txtDetalhamento' name='txtDescricao'> </li>" +
		"<li class='list-group-item form-inline'>" +
		"<button class='btn btn-outline-success form-control btn-sm w-100' value='listar' id='operacao'>Pesquisar</button>" +
		"</li>" +
		"</ul>" +
		"</div>" +
		"</div>" +
		"</div>" +
		"</div>" ;
	}
	
	function montarAlterarExcluir(centroCusto){
		
		return "<div class='py-5'>" +
		"<div class='container'>" +
		"<div class='row'>" +
		"<div class='col-md-8'>" +
		"<h1 class='display-4'>Alterar</h1>" +
		"<input type='hidden' name='txtId' value='" + centroCusto.id + "'>" +
		"<ul class='list-group'>" +
		"<li class='list-group-item form-inline'> <label class='form-control bg-secondary text-white' id='lblCodigo'>Código</label>" +
		"<strong>" +
		"<label class='form-control' name='txtCodigo'>" + centroCusto.codigo + "</label>" +
		"</strong>" +
		"<li class='list-group-item form-inline'> <label class='form-control bg-secondary text-white' name='lblDescricao'>Descrição</label>" +
		"<input class='form-control' type='text' name='txtDescricao' value='" + centroCusto.descricao + "'> </li>" +
		"<li class='list-group-item form-inline'> <label class='form-control bg-secondary text-white' name='lblDetalhamento'>Detalhamento</label>" +
		"<input class='form-control w-75' type='text' name='txtDetalhamento' value='" + centroCusto.detalhamento + "'> </li>" +
		"<li class='list-group-item form-inline'>" +
		"<button class='btn btn-outline-warning form-control' name='operacao' id='operacao' value='alterar' style='width:49%'>Alterar</button>" +
		"<button class='btn btn-outline-danger form-control' name='operacao' id='operacao' value='excluir' style='width:49%'>Excluir</button>" +
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
		
		var centroCusto = arguments;
		
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
		            "<li class='list-group-item'>Código " + centroCusto.codigo + "</li>" +
		            "<li class='list-group-item'>Descrição " + centroCusto.descricao + "</li>" +
		            "<li class='list-group-item'>Detalhamento " + centroCusto.detalhamento + "</li>" +
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
	
	function limparCampos(){
		$("input").val("");
	}
	
}); // (document).ready