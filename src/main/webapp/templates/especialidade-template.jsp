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

<%@ include file="../especialidade.jsp" %>
  <div class="py-5 bg-light">
    <div class="container">
      <div class="row">
        <div class="col-md-12">
          <h1 class="display-1">Especialidade</h1>
        </div>
      </div>
    </div>
  </div>
  <div class="py-5">
    <div class="container">
      <div class="row">
        <div class="col-md-8">
          <h1 class="display-4">Cadastro</h1>
          <ul class="list-group">
            <li class="list-group-item form-inline"> <label class="form-control bg-secondary text-white" id="txtDescricao" name="txtDescricao">DescriÃ§Ã£o</label>
              <input class="form-control" type="text" placeholder="Digite aqui.."> </li>
            <li class="list-group-item form-inline"> <label class="form-control bg-secondary text-white" id="txtDetalhamento" name="txtDetalhamento">detalhamento</label>
              <input class="form-control" type="text" placeholder="Digite aqui.."> </li>
            <li class="list-group-item form-inline">
              <button class="btn btn-outline-success form-control" id="operacao">Salvar</button>
              <button class="btn btn-outline-warning form-control">Limpar</button>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
  <div class="py-5">
    <div class="container">
      <div class="row">
        <div class="col-md-8">
          <h1 class="display-4">Alterar</h1>
          <ul class="list-group">
            <li class="list-group-item form-inline"> <label class="form-control bg-secondary text-white" id="txtDescricao" name="txtDescricao">DescriÃ§Ã£o</label>
              <input class="form-control" type="text" placeholder="Digite aqui.."> </li>
            <li class="list-group-item form-inline"> <label class="form-control bg-secondary text-white" id="txtDetalhamento" name="txtDetalhamento">detalhamento</label>
              <input class="form-control" type="text" placeholder="Digite aqui.."> </li>
            <li class="list-group-item form-inline">
              <button class="btn btn-outline-warning form-control" name="operacao" style="width:49%">Alerar</button>
              <button class="btn btn-outline-danger form-control" name="operacao" style="width:49%">Excluir</button>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
  <div class="py-5">
    <div class="container">
      <div class="row">
        <div class="col-md-8">
          <h1 class="display-4">Pesquisa</h1>
          <ul class="list-group">
            <li class="list-group-item form-inline"> <label class="form-control bg-secondary text-white" id="txtDescricao" name="txtDescricao">CÃ³digo</label>
              <input class="form-control" type="text" placeholder="Digite aqui.."> </li>
            <li class="list-group-item form-inline"> <label class="form-control bg-secondary text-white" id="txtDescricao" name="txtDescricao">DescriÃ§Ã£o</label>
              <input class="form-control" type="text" placeholder="Digite aqui.."> </li>
            <li class="list-group-item form-inline">
              <button class="btn btn-outline-success form-control btn-sm w-100" id="operacao">Pesquisar</button>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
  <div class="py-5">
    <div class="container">
      <div class="row">
        <div class="col-md-12">
          <h1 class="display-4">Lista</h1>
          <table class="table">
            <thead>
              <tr>
                <th>CÃ³digo</th>
                <th>DescriÃ§Ã£o</th>
                <th>Detalhamento</th>
                <th colspan="2" class="text-center">OpÃ§Ãµes</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>1</td>
                <td>Mark</td>
                <td>Otto</td>
                <td>
                  <button class="btn btn-outline-warning form-control" name="operacao">Alerar</button>
                </td>
                <td>
                  <button class="btn btn-outline-danger form-control" name="operacao">Excluir</button>
                </td>
              </tr>
            </tbody>
            <tfoot>
              <tr>
                <td colspan="5"> <label class="form-control bg-light">Total de Especialidades: XX</label> </td>
              </tr>
            </tfoot>
          </table>
        </div>
      </div>
    </div>
  </div>
  <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4" crossorigin="anonymous"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>
</body>

</html>