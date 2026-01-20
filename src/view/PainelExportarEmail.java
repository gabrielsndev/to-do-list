package view;

import javax.swing.*;
import java.awt.*;
import controller.command.Command;
import controller.command.ExportarEmailCommand;

public class PainelExportarEmail extends JPanel {

    private JTextField textFieldEmail;
    private JTextField textFieldData;

    public PainelExportarEmail() {
        setLayout(null);
        
        JLabel lblTitulo = new JLabel("Exportar Para O Email");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblTitulo.setBounds(267, 48, 234, 62);
        add(lblTitulo);
        
        JLabel lblData = new JLabel("Digite a data (YYYY-MM-DD):");
        lblData.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblData.setBounds(277, 191, 300, 14);
        add(lblData);

        textFieldData = new JTextField();
        textFieldData.setBounds(277, 228, 224, 31);
        add(textFieldData);
        
        JButton btnEnviar = new JButton("Enviar");
        btnEnviar.setBounds(324, 279, 117, 35);
        add(btnEnviar);
        
        // ação encapsulada no Command
        btnEnviar.addActionListener(e -> {
            
			try {
				Command command = new ExportarEmailCommand(PainelExportarEmail.this, textFieldData);
				command.execute();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });
    }
}