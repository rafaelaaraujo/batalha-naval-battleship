package Server;

import java.awt.Point;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import jogo.Jogador;
import jogo.Jogo;
import enuns.Estado;
import enuns.OrientacaoNavio;
import listeners.MessageListener;
import navios.Navio;

public interface Servidor extends Remote {

	public void addMessageListener(MessageListener messageListener) throws RemoteException;

	public Estado conectar() throws RemoteException;

	public void desconectar(Estado estado) throws  RemoteException;

	public Estado getEstadoJogo() throws  RemoteException;

	public void setEstadoJogo(Estado estado) throws  RemoteException;

	public List<Point> getTiros(Estado id) throws  RemoteException;

	public boolean oponenteConectado() throws  RemoteException;

	public Jogo getJogo() throws  RemoteException;

	public Jogador getJogador(Estado estado) throws  RemoteException;

	public void retiraEstadoAdicionandoNavio(Estado estado) throws  RemoteException;

	public void alteraPosicaoDoNavio(Estado idJogador, int idNavio, Point posicao) throws  RemoteException;

	public void alteraOrientacaoNavio(Estado idJogador, int idNavio, OrientacaoNavio orientacao) throws RemoteException;

	public void adicionaNavioTabuleiro(Estado idJogador, Navio navio) throws  RemoteException;

	public List<Navio> getFrota(Estado idJogador) throws RemoteException;
	
	public boolean posicioanandoNavio(Estado idJogador) throws RemoteException;

	public void atirarNoOponente(Estado idJogadorAtual, int coluna, int linha) throws RemoteException;



	
}
