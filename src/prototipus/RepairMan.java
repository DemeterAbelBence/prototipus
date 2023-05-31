package prototipus;

import java.util.List;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : RepairMan.java
//  @ Date : 4/22/2023
//  @ Author : 
//
//

/**A játékbeli szerelő funkcióinak megvalósítására. Segítségével lehet áthelyezni a
 *pumpákat illetve a csöveket, illetve ezeket az objektumokat lehet vele megjavítani, ha
 *elromlottak, a játékos funkcióin kívül.*/
public class RepairMan extends Player implements Updateable{
	private boolean hasPump;
	private Pipe pipeInHand;

	/**Az osztály konstruktora.
	 * @param position: a komponensen amin áll a szerelő*/
	public RepairMan(Component position) {
		host = position;
		position.addPlayer(this);
	}

	/**A ciszternánál új cső felvétele, ha ez lehetséges. Ekkor új cső
	 *generálódik a szerelő kezébe.*/
	private void obtainNewPipe() {
		if(pipeInHand == null) {
			Pipe newPipe = new Pipe();
			pipeInHand = newPipe;
			Map.addPipe(newPipe);
			System.out.println("uj cso");
		}
	}
	/**Cső felvétele pumpánál (inkább lekapcsolásnak nevezhető).
	 * @param pipe: A lekapcsolandó cső*/
	private void detachPipeFromNode(Pipe pipe) {
		List<Component> pipeEnds = pipe.getNeighbours();
		Component end1 = pipeEnds.get(0);
		Component end2 = pipeEnds.get(1);
		pipe.removeNeighbour(end1);
		pipe.removeNeighbour(end2);
		
		end1.removeNeighbour(pipe);
		end2.removeNeighbour(pipe);
		pipe.setLeaks(true);
		
		pipeInHand = pipe;
	}

	/**A mező megjavítása, amin a szerelő áll.*/
	public void repair() {
		host.repaired();
	}

	/**Adott cső felvétele.
	 * @param pipe: a felvevendő cső*/
	public void pickUpPipe(Pipe pipe) {
		boolean isHostNode = host.getNode();
		boolean isHostItemSource = host.getItemSource();
		if(isHostNode && !isHostItemSource)
			detachPipeFromNode(pipe);
		else if(isHostNode && isHostItemSource)
			obtainNewPipe();
	}

	/**Pumpa felvétele.*/
	public void pickUpPump() {
		boolean standingOnItemSource = host.getItemSource();
		if(standingOnItemSource) {
			hasPump = true;
		}
	}

	/**Pumpa letétele (csak csövön állva lehetséges!).*/
	public void placeDownPump() {
		if(hasPump) {
			boolean isPumpPlaceableOnHost = host.getPumpPlaceable();
			if(isPumpPlaceableOnHost) {
				List<Component> hostNeighbours = host.getNeighbours();
				Pump newPump = new Pump();
				newPump.addNeighbour(host);
				Component otherEndPump = hostNeighbours.get(0);
				host.removeNeighbour(otherEndPump);
				host.addNeighbour(newPump);
				Pipe newPipe = new Pipe();
				otherEndPump.removeNeighbour(host);
				otherEndPump.addNeighbour(newPipe);
				newPipe.addNeighbour(otherEndPump);
				newPipe.addNeighbour(newPump);
				newPump.addNeighbour(newPipe);

				newPump.input = null; newPump.output =  null;
				newPipe.input = null; newPipe.output = null;

				//PrototypeTest.addPump(newPump);
				//PrototypeTest.addPipe(newPipe);
				Map.addPump(this, newPump);
				Map.addPipe(newPipe);
				hasPump = false;
			}
		}
	}

	/**Cső letétele (csak csomóponton állva lehetséges).*/
	public void placeDownPipe() {
		boolean standingOnNode = host.getNode();
		if(pipeInHand == null) return;
		if(standingOnNode) {
			//ellenőrzi hogy ciszternán állunk-e
			//ellenőrzi hogy ciszternára akarjuk-e lerakni
			//ha mindkettő igaz, nem engedi
			if (host.itemSource && pipeInHand.countNeighbours() == 1 && pipeInHand.getNeighbour(0).itemSource) {
				//return;
			}
			//mint ciszternánál, csak forrásra ellenőriz
			if(host.capacity == -1 && !host.itemSource){
				if(pipeInHand.countNeighbours() == 1 && !pipeInHand.getNeighbour(0).itemSource && pipeInHand.getNeighbour(0).capacity ==-1){
					//return;
				}
			}
			host.addNeighbour(pipeInHand);
			pipeInHand.addNeighbour(host);
		}
		int nrOfNeighboursOfPipeInHand = pipeInHand.countNeighbours();
		if(nrOfNeighboursOfPipeInHand == 2) {
			pipeInHand.setLeaks(false);
			setPipeInHand(null);
		}
	}
	/**Üres függvény, nincsen implementációja.*/
	public void makeSlippery(){}
	/**Beállítja az adott csövet a szerelő kezében lévő csőnek.
	 * @param pipe: a felvevendő cső*/
	public void setPipeInHand(Pipe pipe) {
		pipeInHand = pipe;
	}

	/**Visszaadja a szerelő kezében található csövet.
	 * @return Pipe*/
	public Pipe getPipeInHand() {
		return pipeInHand;
	}
	
	/*public String toString() {
		return super.toString() + ", hasPump: " + hasPump + ", pipeInHand: "
	+ PrototypeTest.getComponentTypeAndIndex(pipeInHand);
	}*/
}
