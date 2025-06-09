package persistencia;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Persistencia {

	public void salvarCentral(CentralDeInformacoes central, String arquivo) {
		XStream xstream = new XStream(new DomDriver());
		try {
			xstream.alias("CentralDeInformacoes", CentralDeInformacoes.class);
			FileOutputStream nomeDoArquivo = new FileOutputStream(arquivo);
			xstream.toXML(central, nomeDoArquivo);
			
		}	catch(Exception e) {
			System.out.print("ERRO: "+ e.getMessage());
		}
		
	}
	
	public CentralDeInformacoes recuperarCentral(String arquivo) {
		XStream xstream = new XStream(new DomDriver());
		try {
			FileInputStream nomeDoArquivo = new FileInputStream(arquivo);
	        return (CentralDeInformacoes) xstream.fromXML(nomeDoArquivo);
			
		}catch(FileNotFoundException  e) {
	           return new CentralDeInformacoes();
	           
		} catch (Exception e) {
		    System.out.println("Erro ao recuperar: " + e.getMessage());
            return new CentralDeInformacoes();
		}
			
	}
	
}