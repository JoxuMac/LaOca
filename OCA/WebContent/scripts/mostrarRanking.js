/**
 * LA OCA - 2017 - Tecnologias y Sistemas Web 
 * Escuela Superior de Informatica de Ciudad Real
 * 
 * Josue Gutierrez Duran 
 * Sonia Querencia Martin 
 * Enrique Simarro Santamaria
 * Eduardo Fuentes Garcia De Blas
 */

function mostrarRanking(){
	var request = new XMLHttpRequest();	
	request.open("get", "servers/mostrarRanking.jsp");
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	$("#ranking_list").empty();
	request.onreadystatechange=function() {
		if (request.readyState==4) {
			var respuesta=JSON.parse(request.responseText);
			if (respuesta.result==="OK") {
				for(var i in respuesta.ranking.scores){
					var inicio= '<tr>';
					var position = '<td>'+ respuesta.ranking.scores[i].position +'</td>';
					var user = '<td>'+respuesta.ranking.scores[i].user+'</td>';
					var score = '<td>'+respuesta.ranking.scores[i].score+'</td>';
					var fin= '</tr>';
					var total = inicio+position+user+score+fin;
					$("#ranking_list").append(total);
				}
			}else{
				alert("Error, no se pudo cargar la lista de ranking");
			}
		}
	};
	request.send();
}
