/**
 * 
 * @returns
 */

$(document).ready(function(){
	
	carregarEspecialidades();
	
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
		}else if(operacao == 'adicionarespecialidade'){
			adicionarEspecialidade();
		}else if(operacao == 'deleteespecialidade'){
			deleteEspecialidade($(this));
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
		
		mantenedor = new Object();
		
		mantenedor.id = id;
		mantenedor.codigo = codigo;
		mantenedor.descricao = descricao;
		mantenedor.detalhamento = detalhamento;
		mantenedor.dataCadastro = dataCadastro;
		
		$('#divBody').html(montarAlterarExcluir.call(this, mantenedor));
	
	});
	
	// buttonExcluir
	$('body').on('click', '.buttonExcluir', function(){
		
		var linhaTR = $(this).parent().parent().parent();
		var id = linhaTR.find("td").eq(0).html();
		var codigo = linhaTR.find("td").eq(1).html();
		var descricao = linhaTR.find("td").eq(2).html();
		var detalhamento = linhaTR.find("td").eq(3).html();
		var dataCadastro = linhaTR.find("td").eq(4).html();
		
		mantenedor = new Object();
		
		mantenedor.id = id;
		mantenedor.codigo = codigo;
		mantenedor.descricao = descricao;
		mantenedor.detalhamento = detalhamento;
		
		excluir(mantenedor);
	});

/* FUNÇÕES CHAMADAS AJAX */
	
	function listar(mantenedor){
		
		if(mantenedor == null){
			var mantenedor = new Object();
			
			mantenedor.codigo = $("[name = txtCodigo]").val();
			mantenedor.descricao = $("[name = txtDescricao]").val();
			mantenedor.detalhamento = $("[name = txtDetalhamento]").val();
		}
		
		var objMantenedor = new Mantenedor();
		var json = objMantenedor.listar(mantenedor);
		
		var mantenedor = json.entidade;
		var mantenedors = json.entidades;
		var mensagens = json.mensagens;
		var semafaro = json.semafaro;
		
		var mensagem = "";
    	 
    	 
    	 // verificar respostas
    	 if(semafaro == 'VERDE'){
    		
	    	 var msgRetorno;
	    	 
	    	 if(mantenedors.length == 1){
	    		 msgRetorno = montarAlterarExcluir.call(this, mantenedors[0]);
	    	 }else{
	    		 
	    		 var tBody = "";
	    		 
	    		 for(var i = 0; i < mantenedors.length; i++){
	    			 
	    			 mantenedor = mantenedors[i];
	    			 
	    			 tBody += makeTableTBody(
	    					 makeTableTR(
	    							 makeTableTD(mantenedor.id) +
	    							 makeTableTD(mantenedor.codigo) +
	    							 makeTableTD(mantenedor.descricao) +
	    							 makeTableTD(mantenedor.detalhamento) +
	    							 makeTableTD(formatarData(mantenedor.dataCadastro))+
	    							 makeTableTDDoublo(htmlButtonUpdate(mantenedor.id), htmlButtonDelete(mantenedor.id))
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
	    				 makeTableTFoot("Quantidade de Especialidades cadastradas: " + mantenedors.length, mantenedors.length)
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
		
		var mantenedor = new Object();
		
		mantenedor.descricao = $("[name = txtDescricao]").val();
		mantenedor.detalhamento = $("[name = txtDetalhamento]").val();
		
		var objMantenedor = new Mantenedor();
		
		var json = objMantenedor.salvar(mantenedor);

		var mantenedor = json.entidade;
		var mantenedors = json.entidades;
		var mensagens = json.mensagens;
		var semafaro = json.semafaro;

		var mensagem = "";

		// verificar respostas
		if (semafaro == 'VERDE') {

			$('#divBody').html("");
			$('#divBody').append(
					makeMensagemSucesso("Mantenedor "
							+ mantenedor.descricao
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
	
	
	function excluir(mantenedor){		
		
		if(mantenedor == null){
			var mantenedor = new Object();
			
			mantenedor.id = $("[name = txtId]").val();
			mantenedor.descricao = $("[name = txtDescricao]").val();
			mantenedor.detalhamento = $("[name = txtDetalhamento]").val();
		}
		
		var objMantenedor = new Mantenedor();
		
		var json = objMantenedor.excluir(mantenedor);
	    	 
		var mantenedor = json.entidade;
		var mantenedors = json.entidades;
		var mensagens = json.mensagens;
		var semafaro = json.semafaro;
		
		var mensagem = "";
    	 
    	 // verificar respostas
    	 if(semafaro == 'VERDE'){
    		 
    		 $('#divBody').html("");
    		 $('#divBody').append(makeMensagemSucesso("Mantenedor " + mantenedor.descricao + " excluida com sucesso!"));
    		 
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
	
	function alterar(mantenedor){
		
		if(mantenedor == null){
			var mantenedor = new Object();	
			
			mantenedor.id = $("[name = txtId]").val();
			mantenedor.descricao = $("[name = txtDescricao]").val();
			mantenedor.detalhamento = $("[name = txtDetalhamento]").val();
		}
		
		var objMantenedor = new Mantenedor();
		var json = objMantenedor.alterar(mantenedor);
		
		var mantenedor = json.entidade;
		var mantenedors = json.entidades;
		var mensagens = json.mensagens;
		var semafaro = json.semafaro;
		
		var mensagem = "";
    	 
    	 // verificar respostas
    	 if(semafaro == 'VERDE'){
    		 
    		 $('#divBody').html("");
    		 $('#divBody').append(makeMensagemSucesso("Mantenedor " + mantenedor.descricao + " alterada com sucesso!"));
    		 
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
		
		return "<div class='py-5'>"
				+ "<div class='container'>"
				+ "<div class='row'>"
				+ "<div class='col-md-8'>"
				+ "<h1 class='display-4'>Cadastro</h1>"
				+ "<ul class='list-group'>"
				+ "<li class='list-group-item form-inline'><label class='form-control bg-secondary text-white' id='txtDescricao' "
				+ "name='txtNome'>Nome</label> <input class='form-control' type='text' placeholder='Digite aqui..'>"
				+ "</li>"
				+ "<li class='list-group-item form-inline'><label class='form-control bg-secondary text-white' id='txtDetalhamento' "
				+ "name='txtCpf'>CPF</label>"
				+ " <input class='form-control' type='text' placeholder='Digite aqui..'>"
				+ "</li>"
				+ "<li class='list-group-item form-inline'>"
				+ "<label class='form-control bg-secondary text-white' id='txtDetalhamento' name='txtCpf'>CPF</label>"
				+ "<select class='form-control' type='text' placeholder='Digite aqui..'>"
				+ "</select>"
				+ "<li class='list-group-item form-inline'>"
				+ "<label class='form-control bg-secondary text-white' id='' name='txtEspecialidadeId'>Especialidade</label>"
				+ "<select	id='selectEspecialidade' class='form-control' type='text' placeholder='Digite aqui..'>"
				+ "</select>"
				+ "&nbsp; <button type='button' class='form-control' id='operacao' value='adicionarEspecialidade'>Adicionar Especialidade</button>"
				+ "</li>"
				+ "<li class='list-group-item form-inline'>"
				+ "<button class='btn btn-outline-success form-control' id='operacao'>Salvar</button>"
				+ "<button class='btn btn-outline-warning form-control'>Limpar</button>"
				+ "</li>" + "</ul>" + "</div>" + "</div>"
				+ "</div>" + "</div>";
	}
	
	// montar pesquisa
	function montarPesquisa(){
		return "<div class='py-5'>" +
				"<div class='container'>" +
				"<div class='row'>" +
				"<div class='col-md-8'>" +
				"<h1 class='display-4'>Pesquisa</h1>" +
				"<ul class='list-group'>" +
				"<li class='list-group-item form-inline'>" +
				"<label class='form-control bg-secondary text-white' id='txtDescricao' name='txtDescricao'>CÃ³digo</label>" +
				"<input class='form-control' type='text' placeholder='Digite aqui..'></li>" +
				"<li class='list-group-item form-inline'>" +
				"<label class='form-control bg-secondary text-white' id='txtDescricao' name='txtDescricao'>DescriÃ§Ã£o</label>" +
				"<input class='form-control' type='text' placeholder='Digite aqui..'>" +
				"</li>" +
				"<li class='list-group-item form-inline'>" +
				"<button class='btn btn-outline-success form-control btn-sm w-100' id='operacao'>Pesquisar</button>" +
				"</li>" +
				"</ul>" +
				"</div>" +
				"</div>" +
				"</div>" +
				"</div>" ;
	}
	
	function montarAlterarExcluir(mantenedor){
		
		return "<div class='py-5'>" +
		"<div class='container'>" +
		"<div class='row'>" +
		"<div class='col-md-8'>" +
		"<h1 class='display-4'>Alterar</h1>" +
		"<input type='hidden' name='txtId' value='" + mantenedor.id + "'>" +
		"<ul class='list-group'>" +
		"<li class='list-group-item form-inline'> <label class='form-control bg-secondary text-white' id='lblCodigo'>Código</label>" +
		"<strong>" +
		"<label class='form-control' name='txtCodigo'>" + especialidade.codigo + "</label>" +
		"</strong>" +
		"<li class='list-group-item form-inline'> <label class='form-control bg-secondary text-white' name='lblDescricao'>Descrição</label>" +
		"<input class='form-control' type='text' name='txtDescricao' value='" + especialidade.descricao + "'> </li>" +
		"<li class='list-group-item form-inline'> <label class='form-control bg-secondary text-white' name='lblDetalhamento'>Detalhamento</label>" +
		"<input class='form-control w-75' type='text' name='txtDetalhamento' value='" + especialidade.detalhamento + "'> </li>" +
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
	
	function limparCampos(){
		$("input").val("");
	}
	
	function adicionarEspecialidade(){
		
		var cloneLI = $('#selectEspecialidade').parent().clone();
		cloneLI.children('button').remove();
		cloneLI.children('select').removeAttr("id");
		cloneLI.append("&nbsp;<button type='button' id='operacao' class='form-control btn-default' value='deleteEspecialidade'>Remover Especialidade</button>");
		cloneLI.hide();
		$('#selectEspecialidade').parent().after(cloneLI);
		cloneLI.slideDown();
	}
	
	function deleteEspecialidade(elemento){
		
		
        elemento.parent().slideUp('fast', function() {  
             elemento.parent().remove();  
        }) 
	}
	
	function carregarEspecialidades(){
		
		var objEspecialidade = new Especialidade();
		var obj = objEspecialidade.listar();
		
		if(obj.semafaro == 'VERDE'){
			
			var options = "<option value=''></option>";
			
			var especialidades = obj.entidades;
			
			especialidades.forEach(function(especialidade){
				
				options += "<option value='" + especialidade.id + "'>" + especialidade.descricao + "</option>";
			});
			
			//localStorage.setItem("r", "ralph");
			alert(localStorage.getItem('r'));
			
		}else if(obj.semafaro == 'VERMELHO'){
			
		}else{
			alert('Erro desconhecido!');
		}
		
		
	}
	
}); // (document).ready