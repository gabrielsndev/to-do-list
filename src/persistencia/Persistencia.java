package persistencia;

import modelo.CentralDeInformacoes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

public class Persistencia {

	public void salvarCentral(CentralDeInformacoes central, String arquivo) throws Exception{
		XStream xstream = new XStream(new StaxDriver());
		try {
			xstream.alias("CentralDeInformacoes", CentralDeInformacoes.class);
			FileOutputStream nomeDoArquivo = new FileOutputStream(arquivo);
			xstream.toXML(central, nomeDoArquivo);
			
		} catch(Exception e) {
			e = new Exception("Ocorreu um erro");
			throw e;
		}
		
	}
	
	public CentralDeInformacoes recuperarCentral(String arquivo) {
		XStream xstream = new XStream(new StaxDriver());
		try {
			FileInputStream nomeDoArquivo = new FileInputStream(arquivo);
	        return (CentralDeInformacoes) xstream.fromXML(nomeDoArquivo);
			
		} catch(FileNotFoundException  e) {
	           return new CentralDeInformacoes();
	           
		} catch (Exception e) {
			
            return new CentralDeInformacoes();
		}
			
	}
	
}