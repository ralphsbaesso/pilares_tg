<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" type="text/css">
  <link rel="stylesheet" href="https://pingendo.com/assets/bootstrap/bootstrap-4.0.0-beta.1.css" type="text/css"> </head>

<body>
<nav class="navbar navbar-expand-md bg-primary navbar-dark fixed-top">
    <div class="container">
      <a class="navbar-brand" href="#">Pilares</a>
      <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbar2SupportedContent" aria-controls="navbar2SupportedContent" aria-expanded="false" aria-label="Toggle navigation"> <span class="navbar-toggler-icon"></span> </button>
      <div class="collapse navbar-collapse text-center justify-content-end" id="navbar2SupportedContent">
        <ul class="navbar-nav">
          <li class="nav-item">
            <a class="nav-link" href="#" id='linkCadastrar'><i class="fa d-inline fa-lg fa-pencil"></i> Cadastrar</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#" id='linkPesquisar'><i class="fa d-inline fa-lg fa-search"></i> Pesquisar</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#" id='linkListar'><i class="fa d-inline fa-lg fa-server"></i> Listar</a>
          </li>
        </ul>
        <a class="btn navbar-btn btn-primary ml-2 text-white"><i class="fa d-inline fa-lg fa-user-circle-o"></i> Entrar</a>
      </div>
    </div>
  </nav>
  <h1 style="height:50px" class='bg-light'> - </h1>
  
  <div class="py-5 bg-light">
    <div class="container">
      <div class="row">
        <div class="col-md-12">
          <h1 class="display-1">Especialidade</h1>
        </div>
      </div>
    </div>
  </div>
  
  <div id='divBody'></div>
  
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4" crossorigin="anonymous"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>
  <script src='js/componentes.js'></script>
  <script src='js/util.js'></script>
  <script src='js/especialidade.js'></script>
  <script src='js/requisicao.js'></script>
</body>

</html>