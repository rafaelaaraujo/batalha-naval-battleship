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

	public void addMessageListener(MessageListener messageListener) throws ErroServidorException, RemoteException;

	public Estado conectar() throws ErroServidorException, RemoteException;

	public void desconectar(Estado estado) throws ErroServidorException, RemoteException;

	public Estado getEstadoJogo() throws ErroServidorException, RemoteException;

	public void setEstadoJogo(Estado estado) throws ErroServidorException, RemoteException;

	public List<Point> getTiros(Estado id) throws ErroServidorException, RemoteException;

	public boolean oponenteConectado() throws ErroServidorException, RemoteException;

	public Jogo getJogo() throws ErroServidorException, RemoteException;

	public Jogador getJogador(Estado estado) throws ErroServidorException, RemoteException;

	public void retiraEstadoAdicionandoNavio(Estado estado) throws ErroServidorException, RemoteException;

	public void alteraPosicaoDoNavio(Estado idJogador, int idNavio, Point posicao) throws ErroServidorException, RemoteException;

	public void alteraOrientacaoNavio(Estado idJogador, int idNavio, OrientacaoNavio orientacao) throws ErroServidorException, RemoteException;

	public void adicionaNavioTabuleiro(Estado idJogador, Navio navio) throws ErroServidorException, RemoteException;

	public List<Navio> getFrota(Estado idJogador) throws ErroServidorException, RemoteException;
	
	public boolean posicioanandoNavio(Estado idJogador) throws ErroServidorException, RemoteException;

	public void atirarNoOponente(Estado idJogadorAtual, int coluna, int linha) throws ErroServidorException, RemoteException;



	
}
