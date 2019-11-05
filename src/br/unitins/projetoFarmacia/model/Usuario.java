package br.unitins.projetoFarmacia.model;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class Usuario {

	private Integer idUsuario;

	private String nome;
	@Email
	private String email;
	@Size(min = 6, max = 30, message = "A senha deve conter entre 6 e 30 caracteres")
	private String senha;
	private LocalDate dataAdmissao;
	private Boolean ativo = Boolean.TRUE;
	private Telefone telefone;
	private Endereco endereco;
	private Perfil perfil;

	public Usuario() {
		super();
	}
	
	public Usuario(Integer idUsuario, String nome, String email, String senha,
			LocalDate dataAdmissao, Boolean ativo, Telefone telefone, Endereco endereco) {
		super();
		this.idUsuario = idUsuario;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.dataAdmissao = dataAdmissao;
		this.ativo = ativo;
		this.telefone = telefone;
		this.endereco = endereco;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (idUsuario == null) {
			if (other.idUsuario != null)
				return false;
		} else if (!idUsuario.equals(other.idUsuario))
			return false;
		return true;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public LocalDate getDataAdmissao() {
		return dataAdmissao;
	}

	public void setDataAdmissao(LocalDate dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

}
