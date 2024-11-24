package med.voll.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import lombok.SneakyThrows;
import med.voll.api.domain.consulta.AgendaDeConsultas;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.DadosDetalhamentoConsulta;
import med.voll.api.domain.medico.Especialidade;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJson;

    @Autowired
    private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsultaJson;

    @MockitoBean
    private AgendaDeConsultas agendaDeConsultas;

    @Test
    @SneakyThrows
    @DisplayName("Deveria devolver 400 quando informações estão inválidas")
    @WithMockUser
    void agendar_cenario1() {
        var response = mockMvc.perform(post("/consultas"))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @SneakyThrows
    @DisplayName("Deveria devolver 200 quando informações estão válidas")
    @WithMockUser
    void agendar_cenario2() {
        var data = LocalDateTime.now().plusHours(1);
        var especialidade = Especialidade.CARDIOLOGIA;
        var dadosDetalhamentoConsulta = new DadosDetalhamentoConsulta(null, 2l, 5l, data);

        when(agendaDeConsultas.agendar(any(DadosAgendamentoConsulta.class))).thenReturn(dadosDetalhamentoConsulta);

        var response = mockMvc.perform(post("/consultas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dadosAgendamentoConsultaJson
                        .write(new DadosAgendamentoConsulta(2l, 5l, data, especialidade))
                        .getJson()))
                .andReturn()
                .getResponse();

        var jsonEsperado = dadosDetalhamentoConsultaJson
                .write(dadosDetalhamentoConsulta)
                .getJson();

        assertThat(response.getContentAsString())
                .isEqualTo(jsonEsperado);

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.OK.value());
    }

}
