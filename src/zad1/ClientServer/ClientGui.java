package zad1.ClientServer;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientGui extends JFrame {
    JButton accept;
    JTextField txtField;
    JTextArea txtArea;
    ClientGui(){

    }

    public ClientGui(ClientServer cs) {
        this.setLayout(new BorderLayout());
        this.setSize(300,150);
        this.setVisible(true);
        this.add(accept = new JButton("accept"),BorderLayout.SOUTH);
        txtArea = new JTextArea();
        txtArea.setEditable(false);
        this.add(txtArea,BorderLayout.CENTER);
        this.add(txtField = new JTextField("wprowadz slowo, a po przecinku kod jezyka"),BorderLayout.NORTH);

        accept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String data = txtField.getText();
                String slowo = data.split(",")[0];
                String kod = data.split(",")[1];
                if(slowo != null && kod != null) {
                    txtArea.setText(cs.getTransaltion(slowo, kod));
                    txtArea.revalidate();
                    txtArea.repaint();
                }
            }
        });
    }
}
