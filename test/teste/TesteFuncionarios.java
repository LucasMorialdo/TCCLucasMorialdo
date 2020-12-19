/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import Model.Funcionarios;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Usuário
 */
public class TesteFuncionarios {

    
    //Teste unitário
    @Test
    public void camposVazios() {
        Funcionarios funcs = new Funcionarios();

        funcs.criarFuncionarios("João", "joao@gmail.com");

        Assert.assertNotNull("Campo não preenchido: ", funcs.getEmail());
        

    }
    
    @Test
    public void dadoTipoDiferente(){
        Funcionarios funcs = new Funcionarios();
        funcs.setNome("Maria");
        // A variável Número deve aceitar apenas números
        funcs.setNumero(123);
        
        Assert.assertFalse("abc".equals(funcs.getNumero()));
    }
    
    @Test
    public void cadastroCampoVazio(){
        Funcionarios cadastro1 = new Funcionarios();
        cadastro1.setNome("Júlia");
        cadastro1.setTipoperfil("Funcionário padrão");
        cadastro1.setCpf("01823912839");
        cadastro1.setTelefone1("90213821038");
        cadastro1.setTelefone2("3913912");
        
        Assert.assertNotNull("Campo não preenchido: ", cadastro1.getEmail());
    }
    
    
    
    

}
