package med.voll.api.domain.consulta.validacoes.agendamento;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.paciente.PacienteRepository;

@Component
@RequiredArgsConstructor
public class ValidadorPacienteAtivo implements ValidadorAgendamentoDeConsulta {

    private final PacienteRepository repository;

    @Override
    public void validar(DadosAgendamentoConsulta dados) {
        var pacienteEstaAtivo = repository.findAtivoById(dados.idPaciente());
        if (!pacienteEstaAtivo) {
            throw new ValidacaoException("Consulta não pode ser agendada com paciente excluído");
        }
    }

}
