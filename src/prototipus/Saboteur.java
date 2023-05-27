package prototipus;
//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Saboteur.java
//  @ Date : 4/22/2023
//  @ Author : 
//
//




public class Saboteur extends Player implements Updateable{
	public Saboteur(Component position) {
		host = position;
		host.addPlayer(this);
	}
	
	public void makeSlippery() {
		boolean isHostNode = host.getNode();
		if(!isHostNode) {
			host.resetSlipperyCounter();
		}
	}
	public void repair(){}
	public void pickUpPipe(Pipe pipe){}
	public void pickUpPump(){}
	public void placeDownPump(){}
	public void placeDownPipe(){}
}
