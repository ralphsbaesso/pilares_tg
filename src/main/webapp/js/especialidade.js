/**
 * ESPECIALIDADE
 */

// objeto especialidade
var Especialidade = function(descricao, detalhamento) {
		this.descricao = descricao;
		this.detalhamento = detalhamento;
}

$(document).ready(function(){
	//alert('ok');
	
	// linkCadastrar - exibe o formulário de cadastro
	$('#linkCadastrar').on('click', function(){
	
		$("#divBody").html(montarCadastro());
		
	});//linkCadastrar
	
	// linkPesquisar - exibe formulário para pesquisa
	$('#linkPesquisar').click(function(){
		
		
		$('#divBody').html(montarPesquisa());
		
	
	});//linkPesquisar
	
	// linkListar - exibe todos os mantenedores
	$('#linkListar').click(function(){
		
		var e1 = new Especialidade('1','2');
		var e2 = new Especialidade('3','4');
		
		var e = new Array();
		e.push(e1);
		e.push(e2);
		//e.push(a);
		//var j = JSON.stringify(e);
		//alert(e.length);
		//$('#divBody').html(montarLista.apply(Array, e));
		//return;
		
		listar();
	
	});//linkListar
	
	
	
	// btnConsultar - exibe o mantenedor selecionado
	$('body').on('click', '.btnConsultar', function(){
	
		index = $('.btnConsultar').index(this);
		id = $('.btnConsultar').eq(index).attr('id');
		cpf = $('[name = txtCpf]').val();
		//alert(cpf);
		$.ajax({
		          url : "Especialidade",
		          type : 'post',
		          dataType : 'html',
		          data : {
		               operacao : "listar",
		               txtId : id,
		               txtCpf : cpf
		          },
		          beforeSend : function(){
		               //$("#sucesso").html("<p align='center'>Carregando...</p>");
		          }
		     })
		     .done(function(msg){
		    	  //alert(msg);
		    	 var obj = JSON.parse(msg);
		    	 
		    	 // verificar respostas
		    	 if(obj.sucesso != null){
		    		 $("#sucesso").html(obj.sucesso);
		    		 $("#advertencia").html(""); // limpar
		    	 }
		    	 if(obj.advertencia != null){
		    		//alert(obj.advertencia);
		    		 $("#modalAdvertencia .modal-body").append(obj.advertencia);
		    		 $('#modalAdvertencia').modal('show');
		    		 $('#modalAdvertencia').on('hide.bs.modal', function(){
		    			 $('#modalAdvertencia .modal-body').html("");
		    			 //alert('fechou');
		    		 });
		    	 }
		     })
		     .fail(function(jqXHR, textStatus, msg){
		          alert(msg);
		     }); 
	
	});//btnPesquisar
	
	
	
	
	// buttonSalvar - salva cadastro de Especialidade
	$('body').on('click', '#buttonSalvar', function(){
		
		$.ajax({
		          url : "Especialidade",
		          type : 'post',
		          dataType : 'html',
		          data : {
		               operacao : "salvar",
		               txtDescricao : $("[name = txtDescricao]").val(),
		               txtDetalhamento : $("[name = txtDetalhamento]").val()
		               
		          },
		          
		          beforeSend : function(){
		               $("#sucesso").html("<p align='center'>Carregando...</p>");
		          }
		     })
		     .done(function(msg){
		    	 //alert(msg);
		    	 //var obj = JSON.parse(msg);
		    	 //alert(obj);
		    	 
		    	 // verificar respostas
		    	 if(msg != null){
		    		 
		    		 msg = makeMensagemAdvertencia(msg);
		    		 $("#divBody").html(msg);
		    		 $("#advertencia").html(""); // limpar
		    	 }
		    	 if(obj.advertencia != null){
		    		 //alert(obj.advertencia);
		    		 $("#modalAdvertencia .modal-body").append(obj.advertencia);
		    		 $('#modalAdvertencia').modal('show');
		    		 //$('#modalAdvertencia').modal({backdrop : 'static'});
		    		 $('.modal-backdrop').css({
		    			   'background-color' : '#FFE4C4',
		    			   'opacity' : 0.4
		    		 });
		    		 
		    		 $('#modalAdvertencia').on('hide.bs.modal', function(){
		    			 $('#modalAdvertencia .modal-body').html("");
		    			 //alert('fechou');
		    		 });
		    	 }
		     })
		     .fail(function(jqXHR, textStatus, msg){
		          alert(msg + " Erro grave!!!");
		     }); 
	});//buttonSalvar
	
	
	// btnAlterar - alterar cadastro do mantenedor
	$('body').on('click', '#btnAlterar', function(){

		var select = $('[name = cbEspecialidadeIds]');
		var especialidades = new Array();
		
		for(var i = 0; i < select.length; i++){
			especialidades[i] = select[i].value;
		}
		
		$.ajax({
		          url : "Especialidade",
		          type : 'post',
		          dataType : 'html',
		          data : {
		               operacao : "alterar",
		               txtId : $('[name = txtId]').val(),
		               txtNome : $("[name = txtNome]").val(),
		               txtCpf : $("[name = txtCpf]").val(),
		               cbSexo : $("[name = cbSexo]").val(),
		               txtEmail : $("[name = txtEmail]").val(),
		               cbEspecialidadeIds : especialidades
		               
		               
		          },
		          beforeSend : function(){
		               //$("#sucesso").html("<p align='center'>Carregando...</p>");
		          }
		     })
		     .done(function(msg){
		    	 //alert(msg);
		    	 var obj = JSON.parse(msg);
		    	 //alert(obj);
		    	 
		    	 // verificar respostas
		    	 if(obj.sucesso != null){
		    		 $("#sucesso").html(obj.sucesso);
		    		 $("#advertencia").html(""); // limpar
		    	 }
		    	 if(obj.advertencia != null){
		    		//alert(obj.advertencia);
		    		 $("#modalAdvertencia .modal-body").append(obj.advertencia);
		    		 $('#modalAdvertencia').modal('show');
		    		 $('#modalAdvertencia').on('hide.bs.modal', function(){
		    			 $('#modalAdvertencia .modal-body').html("");
		    			 //alert('fechou');
		    		 });
		    	 }
		     })
		     .fail(function(jqXHR, textStatus, msg){
		          alert(msg);
		     }); 
	});//btnAlterar
	
	
	
	
	// btnExcluir - excluir mantenedor
	$('body').on('click', '#btnExcluir', function(){
		
		
		$.ajax({
		          url : "Especialidade",
		          type : 'post',
		          dataType : 'html',
		          data : {
		               operacao : "excluir",
		               txtId : $("[name = txtId]").val()
		               
		          },
		          beforeSend : function(){
		               //$("#sucesso").html("<p align='center'>Carregando...</p>");
		          }
		     })
		     .done(function(msg){
		    	 //alert(msg);
		    	 var obj = JSON.parse(msg);
		    	 //alert(obj);
		    	 
		    	 // verificar respostas
		    	 if(obj.sucesso != null){
		    		 $("#sucesso").html(obj.sucesso);
		    		 $("#advertencia").html(""); // limpar
		    	 }
		    	 if(obj.advertencia != null){
		    		//alert(obj.advertencia);
		    		 $("#modalAdvertencia .modal-body").append(obj.advertencia);
		    		 $('#modalAdvertencia').modal('show');
		    		 $('#modalAdvertencia').on('hide.bs.modal', function(){
		    			 $('#modalAdvertencia .modal-body').html("");
		    			 //alert('fechou');
		    		 });
		    	 }
		     })
		     .fail(function(jqXHR, textStatus, msg){
		          alert(msg);
		     }); 
	});//btnExcluir
	
	
	
	
	
	
	//botão adicionarEspecialidade
	$('body').on('click','#addEspecialidade', function(){
		//alert('ok');
		var clone = $('#selectEspecialidade').clone();
		clone.children('button').remove();
		clone.removeAttr("id");
		clone.append("&nbsp;<button type='button' class='form-control btn-default deleteEspecialidade'>Remover Especialidade</button>");
		clone.hide();
		$('#selectEspecialidade').after(clone);
		clone.slideDown();
		
	});// botão adicionarEspecialidade
	
	// botão deleteEspecialidade
	$("body").on("click",".deleteEspecialidade", function(){
		
		 var indice = $(".deleteEspecialidade").index(this);
		
          $(".deleteEspecialidade").eq(indice).parent().slideUp('fast', function() {  
               $(this).remove();  
          })  
         
    }); // deleteEspecialidade

/* FUNÇÕES CHAMADAS AJAX */
	
	function listar(entidade){
		
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
	    	 
	    	 objetos = JSON.parse(msg);
	    	 var o = new Array;
	    	 
	    	 
	    	 for(var i = 0; i < objetos.lista.length; i++){
	    		 o.push(objetos.lista[i]);
	    	 }
	    	 
	    	 msg = makeMensagemAdvertencia(o[5].descricao);
	    	 $('#divBody').html(msg);
	    	 
	    	 var table = makeTable(
	    	 				makeTableTBody(
	    	 					makeTableTR(
	    			 				makeTableTD("teste") + makeTableTD("teste")
	    	 					)		
	    			 		)
	    	 );
	    	 $('#divBody').append(table)
	    	 // verificar respostas
	    	 if(obj.sucesso != null){
	    		 $("#sucesso").html(obj.sucesso);
	    		 $("#advertencia").html(""); // limpar
	    	 }
	    	 if(obj.advertencia != null){
	    		//alert(obj.advertencia);
	    		 $("#modalAdvertencia .modal-body").append(obj.advertencia);
	    		 $('#modalAdvertencia').modal('show');
	    		 $('#modalAdvertencia').on('hide.bs.modal', function(){
	    			 $('#modalAdvertencia .modal-body').html("");
	    			 //alert('fechou');
	    		 });
	    	 }
	     })
	     .fail(function(jqXHR, textStatus, msg){
	          alert(msg);
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
		"<li class='list-group-item form-inline'> <label class='form-control bg-secondary text-white' id='txtDescricao' name='txtDescricao'>Descrição</label>" +
		"<input class='form-control' type='text' placeholder='Digite aqui..'> </li>" +
		"<li class='list-group-item form-inline'> <label class='form-control bg-secondary text-white' id='txtDetalhamento' name='txtDetalhamento'>Detalhamento</label>" +
		"<input class='form-control' type='text' placeholder='Digite aqui..'> </li>" +
		"<li class='list-group-item form-inline'>" +
		"<button class='btn btn-outline-success form-control' id='operacao'>Salvar</button>" +
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
	        "<div class='col-md-12'>" +
	          "<h1 class=''>Procura</h1>" +
	        "</div>" +
	      "</div>" +
	      "<div class='row'>" +
	        "<div class='col-md-8'>" +
	          "<ul class='list-group'>" +
	            "<li class='list-group-item form-inline'>" + 
	            	"<label class='form-control bg-faded'>Códigio</label>" +
		              "<input class='form-control mr-sm-2' name='txtCodigo' type='text' placeholder='Digite aqui..'>" +
		              "<input type='submit' class='btn btn-outline-success my-2 my-sm-0' value='Procurar'>" + 
	              "</li>" +
	          "</ul>" +
	        "</div>" +
	      "</div>" +
	    "</div>" +
	  "</div>" ;
	}
	
	function montarAlterarExcluir(especialidade){
		
		return "<div class='py-5'>" +
		"<div class='container'>" +
		"<div class='row'>" +
		"<div class='col-md-8'>" +
		"<h1 class='display-4'>Alterar</h1>" +
		"<ul class='list-group'>" +
		"<li class='list-group-item form-inline'> <label class='form-control bg-secondary text-white' id='txtDescricao' name='txtDescricao'>Descrição</label>" +
		"<input class='form-control' type='text' placeholder='Digite aqui..'> </li>" +
		"<li class='list-group-item form-inline'> <label class='form-control bg-secondary text-white' id='txtDetalhamento' name='txtDetalhamento'>Detalhamento</label>" +
		"<input class='form-control w-75' type='text' placeholder='Digite aqui..'> </li>" +
		"<li class='list-group-item form-inline'>" +
		"<button class='btn btn-outline-warning form-control' name='operacao' style='width:49%'>Alterar</button>" +
		"<button class='btn btn-outline-danger form-control' name='operacao' style='width:49%'>Excluir</button>" +
		"</li>" +
		"</ul>" +
		"</div>" +
		"</div>" +
		"</div>" +
		"</div>";
	}
	
	// montar lista
	function montarLista(...aArgs){
		
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
		
		$("#divAdvertencia").html(
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
		 // fechando o modal
		 $('#modalAdvertencia').on('hide.bs.modal', function(){
			 $('#modalAdvertencia .modal-body').html("");
			 $('#divAdvertencia').html('');
			 // limpando as mensagens
		 });
	}
	
}); // (document).ready