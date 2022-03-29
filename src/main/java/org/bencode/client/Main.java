package org.bencode.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Dictionary;

import static java.lang.System.out;

public class Main {
	
	public static final  char DICIONARY = ("d").charAt(0);
	public static final char LISTARY = ("l").charAt(0);

	public static void main(String... args) throws IOException {
		
		char[] data = leitura("./test.torrent");

		int index = 0;	
		index++;
		bencode(data, index);
		
	}
	public static void bencode(char[] data, int index){
		
			 	
		
		Tamanho tamanho;
		int lengthCampo = 0;
		
		int count = 7;
		for(;count != 0; count--) {
			
			tamanho = ObterNumero(data, index, data.length);

			index += tamanho.comprimentoIdentificador + 1;
			lengthCampo = tamanho.comprimentoValor;
			if(tamanho.tipo == LISTARY) {
				
				out.println(" container Lista [");
				
			}
			
			// Lendo Nome do Campo
			out.println(" Nome:\t"+ ler(data, index, lengthCampo) );
			// Lendo Valor do campo
			index += lengthCampo;
			tamanho = ObterNumero(data, index , data.length);
			//out.println(" Tamanho do Valor: "+tamanho);
			index += tamanho.comprimentoIdentificador + 1  ;
			lengthCampo = tamanho.comprimentoValor;
			
			if(tamanho.tipo == DICIONARY) {
				
				out.println("container { ");
				out.println(" Nome:\t"+ ler(data, index, lengthCampo) );
				index += lengthCampo;
				bencode(data, index);
				
			}else if(tamanho.tipo == LISTARY) {
				
				out.println("container Lista!!!!");
				
			}else out.println(" Valor:\t"+ ler(data, index, lengthCampo) );
			
			index += lengthCampo;
			
/*					if(tamanho.tipo == DICIONARY) {
						bencode(data, index);
					}*/
			
			out.println(" ----------------------------- " );
			
		}
		
		
		
	}
	
	
	
	public static Tamanho ObterNumero(char[] data, int initial, int finaliz) {
		
		String dado = "";
		Tamanho tam = new Tamanho();
		
		
		int i = initial;
		for(;i < finaliz; i++) {
			if( data[i] == ":".charAt(0)) {
				break;
			}else  dado += data[i];
		}
		
		//tam.cumprimentoIdentificador;
		
		tam.comprimentoIdentificador = dado.length();
		
		/* indentificador de numerico i ..... e integer: "i" value "e"*/
		if( dado.charAt(0) == "i".charAt(0)) {
			dado = dado.split("e")[0].length() +"";
			
			tam.comprimentoIdentificador = -1;
			tam.comprimentoValor = Integer.parseInt(dado)+1;
		/* subdicionario e lista container: "l" (or "d") values "e" */
		}else if( dado.charAt(0) == DICIONARY) {
			
			tam.comprimentoValor = Integer.parseInt( dado.substring(1, dado.length()) );
			tam.tipo = DICIONARY;
			tam.comprimentoIdentificador = dado.length() ;
			
		}else if( dado.charAt(0) == LISTARY ) {

			tam.comprimentoValor = Integer.parseInt( dado.substring(2, dado.length()) );
			tam.tipo = LISTARY;
			tam.comprimentoIdentificador = dado.length();

		}else tam.comprimentoValor = Integer.parseInt(dado);

		return tam;
	}
	public static String ler(char[] dados, int initial, int length) {
		String dado = "";
		
		for(int i = 0; i < length ; i++) {
			dado += dados[initial + i];
		}
		
		return dado;
	}
	public static char[] leitura(String path) throws IOException {
		
		char[] data = null;
		
		File torrent = new File(path);
		if( torrent.isFile() ) {
			out.println("Encontrado!!");
			
			FileReader leitor = new FileReader(torrent);
			
			if( !leitor.ready() ) {
				out.println("Arquivo inelegivel!");
				return data;
			}
			
			String codificacao = leitor.getEncoding();
			out.println("Codificação: " + codificacao);
			
			//data = new char[(int)torrent.length()];
			data = new char[255];
			leitor.read(data);
			leitor.close();
			
		}else out.print("Não Encontrado!!");
		
		return data;
	}
	
	/*
	 * Estado Ok par aentender o basico do bencode
	 * public static void main(String... args) throws IOException {
		// Leitura!!!!
		char[] data = leitura("./test.torrent");

		int index = 0;
		if(data[ index ] == DICIONARY) {
		
				index++;	 	
				// pegando numero
				String tamanho = ObterNumero(data, index, data.length);
				//out.println(" Tamanho do Nome: "+tamanho);
				index += tamanho.length() + 1  ;
				int lengthCampo = Integer.parseInt(tamanho);
				// Lendo Nome do Campo
				out.println(" Nome: "+ ler(data, index, lengthCampo) );
				// Lendo Valor do campo
				index += lengthCampo;
				tamanho = ObterNumero(data, index , data.length);
				//out.println(" Tamanho do Valor: "+tamanho);
				index += tamanho.length() + 1 ;
				lengthCampo = Integer.parseInt(tamanho);
				out.println(" Valor: "+ ler(data, index, lengthCampo) );
				
				
		}else if(data[0] == LISTARY) {}
		
		
		
	}
	
	public static String ObterNumero(char[] data, int initial, int finaliz) {
		
		String dado = "";
		
		int i = initial;
		for(;i < finaliz; i++) {
			if( data[i] == ":".charAt(0)) {
				break;
			}else dado += data[i];
		}
				
		return dado;
	}
	public static String ler(char[] dados, int initial, int length) {
		String dado = "";
		
		for(int i = 0; i < length ; i++) {
			dado += dados[initial + i];
		}
		
		return dado;
	}
	public static char[] leitura(String path) throws IOException {
		
		char[] data = null;
		
		File torrent = new File(path);
		if( torrent.isFile() ) {
			out.println("Encontrado!!");
			
			FileReader leitor = new FileReader(torrent);
			
			if( !leitor.ready() ) {
				out.println("Arquivo inelegivel!");
				return data;
			}
			
			String codificacao = leitor.getEncoding();
			out.println("Codificação: " + codificacao);
			
			//data = new char[(int)torrent.length()];
			data = new char[255];
			leitor.read(data);
			leitor.close();
			
		}else out.print("Não Encontrado!!");
		
		return data;
	}*/

}
