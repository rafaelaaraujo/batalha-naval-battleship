package Server;

import java.awt.Point;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import jogo.Jogador;
import jogo.Jogo;
import enuns.Estado;
import enuns.OrientacaoNavio;
import exception.ErroServidorException;
import listeners.MessageListener;
import navios.Navio;

public interface Servidor extends Remote {

	public void addMessageListener(MessageListener messageListener) throws ErroServidorException;

	public Estado conectar() throws ErroServidorException;

	public void desconectar(Estado estado) throws ErroServidorException;

	public Estado getEstadoJogo() throws ErroServidorException;

	public void setEstadoJogo(Estado estado) throws ErroServidorException;

	public List<Point> getTiros(Estado id) throws ErroServidorException;

	public boolean oponenteConectado() throws ErroServidorException;

	public Jogo getJogo() throws ErroServidorException;

	public Jogador getJogador(Estado estado) throws ErroServidorException;

	public void retiraEstadoAdicionandoNavio(Estado estado) throws ErroServidorException;

	public void alteraPosicaoDoNavio(Estado idJogador, int idNavio, Point posicao) throws ErroServidorException;

	public void alteraOrientacaoNavio(Estado idJogador, int idNavio, OrientacaoNavio orientacao) throws ErroServidorException;

	public void adicionaNavioTabuleiro(Estado idJogador, Navio navio) throws ErroServidorException;

	public void atirarNoOponente(Estado idJogadorAtual, int coluna, int linha) throws ErroServidorException;
	
}
