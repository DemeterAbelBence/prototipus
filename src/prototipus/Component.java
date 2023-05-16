package prototipus;

import java.util.ArrayList;
import java.util.Random;

import org.junit.jupiter.api.Test;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Component.java
//  @ Date : 4/22/2023
//  @ Author : 
//
//




public abstract class Component implements Updateable{
	protected boolean pumpPlaceable;
	protected boolean node;
	protected boolean itemSource;
	protected boolean broken;
	protected boolean leaks;
	protected int leakedWater;
	
	protected int stickyCounter = 0;
	protected int slipperyCounter = 0;
	protected int punctureCounter = 0;	
	protected int capacity=-1;
	protected int waterLevel = 0;
	
	public static final int counterPeriod = 10;
	
	private Component input;
	private Component output;
	private ArrayList<Player> players = new ArrayList<Player>();
	protected ArrayList<Component> neighbours = new ArrayList<Component>();
	
	public Component() {
	}
	
	//absztrakt fuggvenyek:
	public abstract void repaired();
	
	public abstract void punctured();
	
	public abstract void malfunction();
	
	public boolean addWater() {
		if(waterLevel >= capacity)
			return false;
		waterLevel += 1;
		return true;
	}
	
	public void takeWater() {
		waterLevel -= 1;
	}
	
	//publikus fuggvenyek
	public boolean canBeSteppedOn() {
		int playerCount = countPlayers();
		if(node)
			return true;
		if(playerCount == 0)
			return true;
		return false;
	}
	
	public void addNeighbour(Component c) {
		neighbours.add(c);
		if(neighbours.size() == 1) {
			//ha spring lenne
			if(this.node && !this.itemSource && this.capacity == -1){
				output = c;
			}else if(c.input == this) {
				output = c;
			}else
				input = c;
		}
		else if(neighbours.size() == 2) {
			if(output!=null){
				if(this.node && !this.itemSource /*&& this.capacity == -1*/){
					neighbours.remove(c);
					return;
				}
				input = c;
			}else if(this.itemSource){ neighbours.remove(c); 	return;/*ha cistern*/ }
			output = c;
		}
	}
	
	public void removeNeighbour(Component c) {
		if(c == output) {
			output = null;
		}
		if(c == input) 
		{
			if(!node) {//csorol van szo, aminek levesszulk az egyik veget
				input = output;
				output = null;
			}
			else
				input = null;
		}
		neighbours.remove(c);
	}
	
	public void addPlayer(Player p) {
		players.add(p);
	}
	
	public void removePlayer(Player p) {
		players.remove(p);
	}
	
	public int countPlayers() {
		return players.size();
	}
	
	public int countNeighbours() {
		return neighbours.size();
	}
	
	public boolean isFull() {
		return waterLevel == capacity;
	}
	
	public boolean getNode() {
		return node;
	}
	
	public void waterFlows() {
		if(output != null && waterLevel > 0) {
			boolean isOutputFull = output.isFull();
			if(!isOutputFull) {
				if(output.addWater())
					this.takeWater();
			}
		}
	}
	
	public int getSlipperyCounter() {
		return slipperyCounter;
	}
	
	public void resetSlipperyCounter() {
		slipperyCounter = counterPeriod;
	}
	
	public int getStickyCounter() {
		return stickyCounter;
	}
	
	public void resetStickyCounter() {
		stickyCounter = counterPeriod;
	}
	
	public ArrayList<Component> getNeighbours(){
		return neighbours;
	}
	
	public boolean getItemSource() {
		return itemSource;
	}
	
	public void resetPunctureCounter() {
		Random random = new Random();
		punctureCounter = random.nextInt(1, 11);
	}
	
	public void setInput(Component input) {
		if(neighbours.contains(input) && this.output != input)
			this.input = input;
	}
	
	public void setOutput(Component output) {
		if(neighbours.contains(output) && this.input != output)
			this.output = output;
	}
	
	public boolean getPumpPlaceable() {
		return !node;
	}
	
	public String toString() {
		String status = "waterLevel: " + waterLevel + ", slippery: " + (slipperyCounter != 0) 
				+ ", sticky: " + (stickyCounter != 0) + ", broken: " 
				+ broken + ", punctured: " + leaks + System.lineSeparator()
				+ "input: " + PrototypeTest.getComponentTypeAndIndex(input)
				+ System.lineSeparator() + "output: " + PrototypeTest.getComponentTypeAndIndex(output)
				+ System.lineSeparator();
		String playersStatus = "Players standing on this component:" + System.lineSeparator();
		for(int i = 0; i < players.size(); ++i)
			playersStatus += "player" + (i + 1) + ": " + PrototypeTest.getPlayerTypeAndIndex(players.get(i)) + System.lineSeparator();
		String neighboursStatus = "Neighbouring components: " + System.lineSeparator();
		for(int i = 0; i < neighbours.size(); ++i)
			neighboursStatus += "neighbour" + (i + 1) + ": " + PrototypeTest.getComponentTypeAndIndex(neighbours.get(i)) + System.lineSeparator();
		return status + playersStatus + neighboursStatus;
	}
}
