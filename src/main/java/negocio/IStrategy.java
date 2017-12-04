package negocio;

import controle.ITransportador;

	public interface IStrategy {
		public void processar(ITransportador transportador);
		
}
