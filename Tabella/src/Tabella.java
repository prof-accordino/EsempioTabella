import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

// Qui non implemento actionlistener perché metterò i metodi a parte dentro la classe
public class Tabella extends JFrame 
{
	private JTable table;
	private DefaultTableModel model;
	
	// Creo i bottoni come attributi visibili da tutta la classe
	JButton addButton = new JButton("Aggiungi riga");
	JButton deleteButton = new JButton("Elimina riga");
	
	public Tabella() 
	{
		// Do il titolo alla finestra
		super("Esempio tabella");
		
		// Creo la tabella con alcune righe
		model = new DefaultTableModel();
		model.addColumn("Nome");
		model.addColumn("Cognome");
		model.addRow(new Object[]{"Andrea", "Accordino"});
		model.addRow(new Object[]{"Lorena", "Gaccione"});
		model.addRow(new Object[]{"Teresa", "Cattaneo"});
		
		// Create the table and set the model
		table = new JTable(model);
		
		// Creo una barra per scorrere la tabella se diventa troppo lunga per la finestra
		JScrollPane scrollPane = new JScrollPane(table);
		
		addButton.addActionListener(this::onAddButtonClicked);		
		deleteButton.addActionListener(this::onDeleteButtonClicked);
		// Disabilito il bottone (se non ci sono righe selezionate sarebbe inutile)
		deleteButton.setEnabled(false);
		
		
		/* 
		 * ListSelectionModel è un interfaccia, che permette la manipolazione
		 * di alcuni aspetti della tabella,qui usiamo il modo di selezione
		 * (singola riga) e ci permette di mettere un listener su degli eventi
		 * in questo caso il cambio di selezione delle righe
		 */
		ListSelectionModel selectionModel = table.getSelectionModel();
		selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		selectionModel.addListSelectionListener(this::onRowSelectionChanged);		
		
		// Creo il pannello generale che conterrà tutti gli oggetti
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(scrollPane, BorderLayout.CENTER);
		
		// Creo un pannello per contenere i bottoni
		JPanel buttonPanel = new JPanel(new FlowLayout());
		// Ci aggiungo i bottoni
		buttonPanel.add(addButton);
		buttonPanel.add(deleteButton);
		// E lo metto dentro il pannello generale
		panel.add(buttonPanel, BorderLayout.SOUTH);
		
		// Aggiungo il pannello generale alla finestra
		setContentPane(panel);
		// Ridimensiono la finestra al suo contenuto
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Metto la finestra al centro dello schermo
		setLocationRelativeTo(null);
		// Rendo visibile la finestra
		setVisible(true);
	}
	
	private void onRowSelectionChanged(ListSelectionEvent e) 
	{
		// Enable/disable the delete button based on whether a row is selected
		boolean hasSelection = !e.getValueIsAdjusting() && !table.getSelectionModel().isSelectionEmpty();
		deleteButton.setEnabled(hasSelection);
	}
	
	private void onAddButtonClicked(ActionEvent e) 
	{
		// Aggiungo una riga
		model.addRow(new Object[]{"", ""});
	}
	
	private void onDeleteButtonClicked(ActionEvent e) 
	{
		/* 
		 * Dalla tabella prendo le righe selezionate
		 * (solo una vista la scelta fatta sopra)
		 * e le elimino
		 */
		int selectedRow = table.getSelectedRow();
		if (selectedRow >= 0) {
			model.removeRow(selectedRow);
		}
	}
	
	public static void main(String[] args) 
	{
		new Tabella();
	}
}
