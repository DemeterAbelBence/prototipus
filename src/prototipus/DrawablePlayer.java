package prototipus;

import java.awt.Graphics;
import java.awt.Image;

public class DrawablePlayer extends Drawable{
	Player player;


	public DrawablePlayer(Player player, Vector2 coordinates, Image image){
		super(coordinates, image);
		this.player = player;
	}
	
	@Override
	public void Draw(Graphics g) {
		if(player.host.getNode()){
			Move(Observer.getDrawableOfComponent(player.getHost()).getCoordinates().plus(new Vector2(spriteOffset)));
		}
		else{
			Drawable drawableOfNeighbour1 = Observer.getDrawableOfComponent(player.getHost().getNeighbour(0));
			Drawable drawableOfNeighbour2 = Observer.getDrawableOfComponent(player.getHost().getNeighbour(1));
			Vector2 middle = new Vector2((drawableOfNeighbour1.getX() + drawableOfNeighbour2.getX() ) / 2,(drawableOfNeighbour1.getY() + drawableOfNeighbour2.getY() ) / 2);
			Move(middle);
		}

		g.drawImage(image,getX(),getY(), null);
	}
}
