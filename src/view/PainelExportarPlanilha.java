package view;

import javax.swing.*;
import java.awt.*;
import controller.command.Command;
import controller.command.ExportarPlanilhaCommand;

public class PainelExportarPlanilha extends JPanel {

    private JTextField textFieldMesEspecifico;
    private JTextField textFieldDiaEspecifico; 

    public PainelExportarPlanilha() {
        setLayout(null);
        
    
        JLabel lblTituloDia = new JLabel("Exportação Com Um Dia Especifico");
        lblTituloDia.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTituloDia.setBounds(10, 11, 350, 47);
        add(lblTituloDia);
        
        JLabel lblDia = new JLabel("Digite o Dia Especifico");
        lblDia.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblDia.setBounds(265, 69, 244, 22);
        add(lblDia);
        
        textFieldDiaEspecifico = new JTextField();
        textFieldDiaEspecifico.setBounds(297, 102, 150, 27);
        add(textFieldDiaEspecifico);
        
        JButton btnDia = new JButton("Exportar");
        btnDia.setBounds(307, 140, 125, 33);
        add(btnDia);
        
        JLabel lblTituloMes = new JLabel("Exportação Com Um Mes Especifico");
        lblTituloMes.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTituloMes.setBounds(10, 207, 350, 47);
        add(lblTituloMes);
        
        JLabel lblMes = new JLabel("Digite a Mes Especifico (YYYY-MM)");
        lblMes.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblMes.setBounds(260, 275, 350, 22);
        add(lblMes);
        
        textFieldMesEspecifico = new JTextField();
        textFieldMesEspecifico.setBounds(297, 306, 150, 27);
        add(textFieldMesEspecifico);
        
        JButton btnMes = new JButton("Exportar");
        btnMes.setBounds(307, 356, 125, 33);
        add(btnMes);
        
        // Ação encapsulada no Command
        btnMes.addActionListener(e -> {
            
			try {
			Command command = new ExportarPlanilhaCommand(this,textFieldMesEspecifico);
				
				command.execute();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });
    }
}