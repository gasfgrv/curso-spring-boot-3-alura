package med.voll.api.domain.endereco;

import jakarta.persistence.Embeddable;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    private String logradouro;
    private String bairro;
    private String cep;
    private String numero;
    private String complemento;
    private String cidade;
    private String uf;

    public Endereco(DadosEndereco dados) {
        this.logradouro = dados.logradouro();
        this.bairro = dados.bairro();
        this.cep = dados.cep();
        this.uf = dados.uf();
        this.cidade = dados.cidade();
        this.numero = dados.numero();
        this.complemento = dados.complemento();
    }

    public void atualizarInformacoes(DadosEndereco dados) {
        this.logradouro = Objects.requireNonNullElse(dados.logradouro(), this.logradouro);
        this.bairro = Objects.requireNonNullElse(dados.bairro(), this.bairro);
        this.cep = Objects.requireNonNullElse(dados.cep(), this.cep);
        this.uf = Objects.requireNonNullElse(dados.uf(), this.uf);
        this.cidade = Objects.requireNonNullElse(dados.cidade(), this.cidade);
        this.numero = Objects.requireNonNullElse(dados.numero(), this.numero);
        this.complemento = Objects.requireNonNullElse(dados.complemento(), this.complemento);
    }

}
