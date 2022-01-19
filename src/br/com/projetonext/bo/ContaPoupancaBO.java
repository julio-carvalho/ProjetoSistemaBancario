package br.com.projetonext.bo;

import br.com.projetonext.bean.Conta;
import br.com.projetonext.bean.ContaPoupanca;
import br.com.projetonext.utils.BancoDeDados;

public class ContaPoupancaBO {
	
	public void consultarSaldoContaPoupanca(ContaPoupanca cp) {
		String nome = cp.getCliente().getNome();
		String cpf = cp.getCliente().getCpf();
		double valor = cp.getSaldo();
		//TipoCliente tp = cp.getCliente().getTipoCliente();
		
		System.out.println("\n+----------------------------------------------------------------------+");
		System.out.println("|                            CONTA POUPANÇA                            |");
		System.out.println("+----------------------------------------------------------------------+");
		System.out.println(" * Nome: " + nome);
		System.out.println(" * CPF: " + cpf);
		System.out.println(" * Saldo R$: " + valor);
		//System.out.println("Tipo: " + tp);
		System.out.println("+----------------------------------------------------------------------+");
	}
		
	//saque na conta poupança
	public void sacar(ContaPoupanca cp, double valor) {
		double saldo = cp.getSaldo();
		if(saldo >= valor) {
			saldo -= valor;
			cp.setSaldo(saldo);
			System.out.println("\nSaque realizado com sucesso!\nSaldo atual: " + cp.getSaldo());
			BancoDeDados.insereConta(cp.getNumero(), cp);
		} else {
			System.out.println("\nSaldo insuficiente!\nSaldo atual: " + cp.getSaldo());
		}
	}
	
	//deposito na conta poupança
	public void depositar(ContaPoupanca cp, double valor) {
		double saldo = cp.getSaldo();
		saldo += valor;
		cp.setSaldo(saldo);
		
		System.out.println("Depósito realizado com sucesso!\nSaldo atual: " + cp.getSaldo());
		
		BancoDeDados.insereConta(cp.getNumero(), cp);
	}
	
	//transferência
	public void transferir(ContaPoupanca cp, Conta contaDestino, double valor, int opc) {
		double saldoAtual = cp.getSaldo();
		double saldoDestino = contaDestino.getSaldo();
		
		//1 - Transferir para outro tipo de conta
		//2 - Transferir para conta corrente
		if(opc == 1) {
			if(saldoAtual >= valor) {
				saldoAtual -= valor;
				cp.setSaldo(saldoAtual);
				
				saldoDestino += valor;
				contaDestino.setSaldo(saldoDestino);
				System.out.println("Transferência realizada com sucesso!\nSaldo atual: R$" + cp.getSaldo());
				
				BancoDeDados.insereConta(cp.getNumero(), cp);
			} else {
				System.out.println("\nSaldo insuficiente!\nSaldo atual: R$" + cp.getSaldo());
			}
		} else {
			if(saldoAtual >= valor) {
				saldoAtual -= valor;
				//aplica taxa de transferência
				saldoAtual -= 5.60;
				cp.setSaldo(saldoAtual);
				
				saldoDestino += valor;
				contaDestino.setSaldo(saldoDestino);
				System.out.println("Transferência para Conta Corrente realizada com sucesso!");
				System.out.println("Saldo da Conta Poupança: R$" + cp.getSaldo());
				System.out.println("Saldo da Conta Corrente: R$" + contaDestino.getSaldo());
				
				BancoDeDados.insereConta(cp.getNumero(), cp);
			} else {
				System.out.println("\nSaldo insuficiente!\nSaldo atual: R$" + cp.getSaldo());
			}
		}
	}
	
	//aplicar rendimento
	public void aplicarRendimento(ContaPoupanca cp) {
		double saldo = cp.getSaldo() * (1+(cp.getTaxaRendimento()/100));
		cp.setSaldo(saldo);
		System.out.println("Rendimento Aplicado!\nSaldo atual: R$" + cp.getSaldo());
		
		BancoDeDados.insereConta(cp.getNumero(), cp);
	}
}
