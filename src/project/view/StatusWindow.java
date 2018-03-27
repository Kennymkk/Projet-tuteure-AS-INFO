/**
 * 
 */
package project.view;


import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

import project.network.*;

/**
 * @author yoann
 * 
 * Classe gérant la fenêtre de statut de réseau.
 * 
 * Elle créé la fenêtre, récupère les sous-station du réseau, créé leur affichage et celui des éléments
 * qui lui sont connectés. Le réseau se créé en trois temps :
 * 
 *  1. Utiliser le constructeur StatusWindow(Network) pour créer la fenêtre principale
 *  2. Utiliser StatusWindow.createDisplay() pour créer l'affichage du réseau
 *  3. Utiliser StatusWindow.updateDisplay() pour mettre à jour l'affichage des paramètres des éléments du réseau
 * 
 * TODO : Tester un autre type de Layout pour la fenêtre 
 */
public class StatusWindow extends JFrame {

	private Network modelNetwork;
	private ArrayList<StatusWindowSubStationElement> subStations;

	private HashMap<Integer, Node> TESTNetworkElements;

	/**
	 * Constructeur
	 * @param ntw Le réseau à afficher
	 */
	public StatusWindow (Network ntw) {
		super();
		this.modelNetwork = ntw;

		this.subStations = new ArrayList<>();
		buildWindow();


		TESTNetworkElements = new HashMap<>();
	}

	/**
	 * Initialisation de la JFrame StatusWindow
	 */
	private void buildWindow() {

		// init fenêtre
		this.setTitle("Statut Réseau");
		this.setSize(1200,600);
		this.setLocationRelativeTo(null); 
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();

		// Layout principal - 2 colonnes et autant de lignes que nécessaires - espacement de 5px
		panel.setLayout(new GridLayout(0,2,5,5));

		this.setContentPane(panel);
		this.validate();
		this.repaint();		
		this.setVisible(true);
	}


	/**
	 * Créé l'affichage à partir du réseau. N'utiliser qu'une fois, à la création de la fenêtre
	 */
	public void createDisplay() {

		ArrayList<Node> nodes = this.modelNetwork.getNodes();

		for(Node node : nodes) {
			// Sauvegarde node pour test
			TESTNetworkElements.put(node.getId(), node);

			// pour chaque station
			if(node.getClass().equals(SubStation.class)) {
				StatusWindowSubStationElement station = new StatusWindowSubStationElement((SubStation)node);

				// ajout de la sous-station à la collection
				this.subStations.add(station);

				// pour chaque centrale reliée à la station
				for(Line line : ((SubStation)node).getLines()) {
					System.out.println("Plant " + line.getIn().getId());
					station.addElement(new StatusWindowPowerPlant(line.getIn()));
				}

				// pour chaque groupe relié à la station
				for(Group group : ((SubStation)node).getGroups()) {
					System.out.println("Groupe " + group.getId());
					station.addElement(new StatusWindowGroup(group));
				}
				
				// creation de l'affichage sous-station
				station.createDisplay();
				
				// ajout à la fenêtre
				this.getContentPane().add(station.getDisplay());
			}
		}

		// mise à jour de la fenêtre
		this.validate();
		this.repaint();
	}

	/**
	 * Met à jour l'affichage des valeurs de paramètres. Utiliser après createDisplay()
	 */
	public void updateDisplay() {
		for(StatusWindowSubStationElement station : this.subStations) {
			station.updateDisplay();
		}
	}


	/**
	 * Main de test pour StatusWindows
	 * @param args aucun parametre nécessaire
	 * @throws InterruptedException thread.sleep() exception
	 */
	public static void main(String[] args) throws InterruptedException {

		// réseau de test basé sur l'archi en dur de Network
		Network myNetwork = new Network(0,0,0);

		StatusWindow myWindow = new StatusWindow(myNetwork);

		myWindow.createDisplay();
		
		// TEST update data
		Group group7 = null;
		Group group12 = null;
		if(myWindow.TESTNetworkElements.containsKey(7)) {
			group7 = (Group) myWindow.TESTNetworkElements.get(7);
		}
		
		if(myWindow.TESTNetworkElements.containsKey(7)) {
			group12 = (Group) myWindow.TESTNetworkElements.get(12);
		}
		
		while(true) {
			Thread.sleep(2000);
			if(group7 != null)
				group7.setConsumption(0);
			if(group12 != null)
				group12.setConsumption(300000);
			myWindow.updateDisplay();
			
			Thread.sleep(2000);
			if(group7 != null)
				group7.setConsumption(400000);
			if(group12 != null)
				group12.setConsumption(0);
			myWindow.updateDisplay();			
		}
	}

}
