package sptech.projetojpa1.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import sptech.projetojpa1.dto.agendamento.AgendamentoRequestDTO
import sptech.projetojpa1.dto.agendamento.AgendamentoResponseDTO
import sptech.projetojpa1.service.AgendamentoService

@RestController
@RequestMapping("/api/agendamentos")
class AgendamentoController(private val agendamentoService: AgendamentoService) {

    @Operation(summary = "Cria um novo agendamento")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Agendamento criado com sucesso"),
            ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
        ]
    )
    @PostMapping("/criar")
    fun criarNovoAgendamento(@Valid @RequestBody agendamentoRequestDTO: AgendamentoRequestDTO): ResponseEntity<*> {
        try {
            val agendamentoResponseDTO = agendamentoService.criarAgendamento(agendamentoRequestDTO)
            return ResponseEntity.ok(agendamentoResponseDTO)
        } catch (ex: IllegalArgumentException) {
            return ResponseEntity.badRequest().body(ex.message)
        }
    }

    @Operation(summary = "Lista todos os agendamentos")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Agendamentos listados com sucesso")
        ]
    )
    @GetMapping("/listar")
    fun listarTodosAgendamentos(): ResponseEntity<List<AgendamentoResponseDTO>> {
        val agendamentos = agendamentoService.listarTodosAgendamentos()
        return ResponseEntity.ok(agendamentos)
    }

    @Operation(summary = "Obtém um agendamento pelo ID")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Agendamento encontrado"),
            ApiResponse(responseCode = "404", description = "Agendamento não encontrado")
        ]
    )
    @GetMapping("/buscar/{id}")
    fun obterAgendamentoPorId(@PathVariable id: Int): ResponseEntity<*> {
        val agendamentoResponseDTO = agendamentoService.obterAgendamento(id)
        return ResponseEntity.ok(agendamentoResponseDTO)
    }

    @Operation(summary = "Atualiza um agendamento existente pelo ID")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Agendamento atualizado com sucesso"),
            ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            ApiResponse(responseCode = "404", description = "Agendamento não encontrado")
        ]
    )
    @PutMapping("/atualizar/{id}")
    fun atualizarAgendamentoExistente(
        @PathVariable id: Int,
        @Valid @RequestBody agendamentoRequestDTO: AgendamentoRequestDTO
    ): ResponseEntity<*> {
        val agendamentoResponseDTO = agendamentoService.atualizarAgendamento(id, agendamentoRequestDTO)
        return ResponseEntity.ok(agendamentoResponseDTO)
    }

    // Adicione o método abaixo na classe AgendamentoController

    @Operation(summary = "Atualiza o status de um agendamento pelo ID")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Status do agendamento atualizado com sucesso"),
            ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            ApiResponse(responseCode = "404", description = "Agendamento ou status não encontrado")
        ]
    )
    @PutMapping("/atualizar-status/{id}")
    fun atualizarStatusAgendamento(
        @PathVariable id: Int,
        @RequestParam statusId: Int
    ): ResponseEntity<*> {
        val agendamentoResponseDTO = agendamentoService.atualizarStatusAgendamento(id, statusId)
        return ResponseEntity.ok(agendamentoResponseDTO)
    }


    @Operation(summary = "Exclui um agendamento pelo ID")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Agendamento excluído com sucesso"),
            ApiResponse(responseCode = "404", description = "Agendamento não encontrado")
        ]
    )
    @DeleteMapping("/excluir/{id}")
    fun excluirAgendamentoExistente(@PathVariable id: Int): ResponseEntity<*> {
        agendamentoService.excluirAgendamento(id)
        return ResponseEntity.ok().build<Any>()
    }

    @GetMapping("/agendamentos-realizados")
    fun agendamentosRealizadosUltimoTrimestre(): ResponseEntity<Int> {
        val quantidadeConcluidos = agendamentoService.agendamentosRealizadosTrimestre()
        return ResponseEntity.ok(quantidadeConcluidos)
    }

}
