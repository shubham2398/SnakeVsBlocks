import java.util.ArrayList;
import java.util.Iterator;

import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;

public class Animation extends AnimationTimer {
	Game game;
	public LongValue lastNanoTime;
	public LongValue delay;
	public IntValue flag;
	private double last;
	public long currentNanoTime;
	private int[] layer;
	private boolean genPowerUp;

	public Animation(Game g) {
		layer = new int[5];
		for (int i = 0; i < 5; i++)
			layer[i] = 1;
		last = 0;
		game = g;
		lastNanoTime = new LongValue(System.nanoTime());
		delay = new LongValue(0);
		flag = new IntValue(0);
		genPowerUp = true;
	}
	
	private void displayPowerUp(int powerUpNo, int x_dist)
	{
		if (powerUpNo == 0)
		{
			game.getAllElements().add(game.makeShield(game.getBLOCK_SIZE()*x_dist + x_dist + game.getBLOCK_SIZE()/2 -12, -360 + game.getBLOCK_SIZE()/2 -10));
		}
		else if (powerUpNo == 1)
		{
			game.getAllElements().add(game.makeCoin(game.getBLOCK_SIZE()*x_dist + x_dist + game.getBLOCK_SIZE()/2 -12, -360 + game.getBLOCK_SIZE()/2 -10));
		}
		else if (powerUpNo == 2)
		{
			game.getAllElements().add(game.makeMagnet(game.getBLOCK_SIZE()*x_dist + x_dist + game.getBLOCK_SIZE()/2 -12, -360 + game.getBLOCK_SIZE()/2 -10));
		}
		else if (powerUpNo == 3)
		{
			game.getAllElements().add(game.makeDestroyBlocksPowerUp(game.getBLOCK_SIZE()*x_dist + x_dist + game.getBLOCK_SIZE()/2 -12, -360 + game.getBLOCK_SIZE()/2 -10));
		}
	}

	private void genWalls() {
		int how_many = game.rnd.nextInt(3);
		if (how_many == 1) {
			int l1 = game.rnd.nextInt(4);
			// System.err.println(l1+" "+layer[l1]);
			if (layer[l1] == 1)
				game.getAllElements().add(game.makeWall((game.getBLOCK_SIZE()) * l1 + l1 + game.getBLOCK_SIZE() - 6,
						-360 + game.getBLOCK_SIZE(), 50 + game.rnd.nextInt(40), 0));
			else
				game.getAllElements().add(game.makeWall((game.getBLOCK_SIZE()) * l1 + l1 + game.getBLOCK_SIZE() - 6,
						-360 + game.getBLOCK_SIZE(), 180 + game.rnd.nextInt(91), 0));
		} else if (how_many == 2) {
			int l1 = game.rnd.nextInt(4);
			int l2 = game.rnd.nextInt(4);
			while (l1 == l2)
				l2 = game.rnd.nextInt(4);
			// System.err.println(l1+" "+layer[l1]+" "+l2+" "+layer[l2]);

			if (layer[l1] == 1)
				game.getAllElements().add(game.makeWall((game.getBLOCK_SIZE()) * l1 + l1 + game.getBLOCK_SIZE() - 6,
						-360 + game.getBLOCK_SIZE(), 50 + game.rnd.nextInt(40), 0));
			else
				game.getAllElements().add(game.makeWall((game.getBLOCK_SIZE()) * l1 + l1 + game.getBLOCK_SIZE() - 6,
						-360 + game.getBLOCK_SIZE(), 180 + game.rnd.nextInt(91), 0));

			if (layer[l2] == 1)
				game.getAllElements().add(game.makeWall((game.getBLOCK_SIZE()) * l2 + l2 + game.getBLOCK_SIZE() - 6,
						-360 + game.getBLOCK_SIZE(), 50 + game.rnd.nextInt(40), 0));
			else
				game.getAllElements().add(game.makeWall((game.getBLOCK_SIZE()) * l2 + l2 + game.getBLOCK_SIZE() - 6,
						-360 + game.getBLOCK_SIZE(), 180 + game.rnd.nextInt(91), 0));
		}
	}

	@Override
	public void handle(long currentNanoTime) {
		game.setSpeedToDefault();
		this.currentNanoTime = currentNanoTime;
		double elapsedTime;
		if (flag.value == 1) {
			delay.value = currentNanoTime - lastNanoTime.value;
			flag.value = 0;
			elapsedTime = (currentNanoTime - lastNanoTime.value - delay.value) / 1000000000.0;
		} else {
			elapsedTime = (currentNanoTime - lastNanoTime.value) / 1000000000.0;
		}
		last += elapsedTime;
		game.block_dur = game.block_dur - elapsedTime;
		game.ball_dur = game.ball_dur - elapsedTime;
		// if (game.repeat == 0 && last>=6) {
		// for (int i = 1; i < 5; i++) {
		// game.getAllElements().add(game.makeBlock(0 + (game.getBLOCK_SIZE()) * i + (i
		// + 1), -10, 5 * i + 1));
		// if (i % 2 == 1) {
		//
		// game.getAllElements()
		// .add(game.makeWall((game.getBLOCK_SIZE()) * i + (i + 1) +
		// game.getBLOCK_SIZE() / 2,
		// -10 + game.getBLOCK_SIZE()));
		// }
		// }
		// game.repeat = 3 + game.rnd.nextInt(2);
		// game.block_dur = 9;
		// last=0;
		// }
		if (last >= 1.205) {
			genWalls();

			if (game.repeat == 0) {
				
				genPowerUp = true;
				for (int i = 0; i < 5; i++) {
					layer[i] = 1;
				}
				// game.getAllElements().add(game.makeBlock(1, -360, 6));
				int ch1, ch2;
				ch1 = game.rnd.nextInt(5);
				ch2 = game.rnd.nextInt(5);
				while (ch1 == ch2) {
					ch2 = game.rnd.nextInt(5);
				}

				for (int i = 0; i < 5; i++) {
					if (i != ch1 && i != ch2)
						game.getAllElements().add(game.makeBlock(0 + (game.getBLOCK_SIZE()) * i + (i + 1), -360,
								1 + game.rnd.nextInt(50)));
				}
				game.getAllElements().add(game.makeBlock(0 + (game.getBLOCK_SIZE()) * ch1 + (ch1 + 1), -360,
						1 + game.rnd.nextInt(Math.min(50, game.getSnake().getLength() - 1))));
				game.getAllElements().add(game.makeBlock(0 + (game.getBLOCK_SIZE()) * ch2 + (ch2 + 1), -360,
						1 + game.rnd.nextInt(Math.min(50, game.getSnake().getLength() - 1))));

				game.repeat = 3 + game.rnd.nextInt(2);
				last = 0;
			} else {
				int how_many = 1 + game.rnd.nextInt(2);
				ArrayList<Integer> object_exist = new ArrayList<Integer> (5);
				
				if (how_many == 1) {
					int l1 = game.rnd.nextInt(5);
					int ball_count = game.rnd.nextInt(4);
					
					object_exist.add(l1);
					layer[l1] = 1;
					for (int i = 0; i < 5; i++) {
						if (i != l1)
							layer[i] = 0;
					}
					game.getAllElements().add(
							game.makeBlock(0 + (game.getBLOCK_SIZE()) * l1 + (l1 + 1), -360, 1 + game.rnd.nextInt(50)));
					
					while (ball_count > 0)
					{
						int x_loc = game.rnd.nextInt(5);
						if ( ! object_exist.contains(x_loc) )
						{
							object_exist.add(x_loc);
							game.getAllElements().add( game.makeBallPowerUp(game.getBLOCK_SIZE()*x_loc + x_loc + game.getBLOCK_SIZE()/2 -12, -360 + game.getBLOCK_SIZE()/2 -10, 1 + game.rnd.nextInt(5)) );
							ball_count -= 1;
						}
					}
					
				} else if (how_many == 2) {
					int ball_count = game.rnd.nextInt(3);
					
					int l1 = game.rnd.nextInt(5);
					int l2 = game.rnd.nextInt(5);
					while (l1 == l2) {
						l2 = game.rnd.nextInt(5);
					}
					object_exist.add(l1);
					object_exist.add(l2);
					layer[l1] = 1;
					layer[l2] = 1;
					for (int i = 0; i < 5; i++) {
						if (i != l1 && i != l2)
							layer[i] = 0;
					}

					game.getAllElements().add(
							game.makeBlock(0 + (game.getBLOCK_SIZE()) * l1 + (l1 + 1), -360, 1 + game.rnd.nextInt(50)));
					game.getAllElements().add(
							game.makeBlock(0 + (game.getBLOCK_SIZE()) * l2 + (l2 + 1), -360, 1 + game.rnd.nextInt(50)));
					
					while (ball_count > 0)
					{
						int x_loc = game.rnd.nextInt(5);
						if ( ! object_exist.contains(x_loc) )
						{
							object_exist.add(x_loc);
							game.getAllElements().add( game.makeBallPowerUp(game.getBLOCK_SIZE()*x_loc + x_loc + game.getBLOCK_SIZE()/2 -12, -360 + game.getBLOCK_SIZE()/2 -10, 1 + game.rnd.nextInt(5)) );
							ball_count -= 1;
						}
					}
				}

				last = 0;
				game.repeat -= 1;
				
				if(genPowerUp)
				{
					int genPowerUpNow = game.rnd.nextInt(2);
					if (genPowerUpNow == 1)
					{
						int numOfPowerUps = 1 + game.rnd.nextInt(2);
						int x_dist = game.rnd.nextInt(5);
						while (object_exist.contains(x_dist))
						{
							x_dist = game.rnd.nextInt(5);
						}
						object_exist.add(x_dist);
						int which_powerUp = game.rnd.nextInt(4);
						displayPowerUp(which_powerUp,x_dist);
						if(numOfPowerUps == 2)
						{
							if(object_exist.size()<5)
							{
								genPowerUpNow = game.rnd.nextInt(2);
								if (genPowerUpNow == 1)
								{
									while (object_exist.contains(x_dist))
									{
										x_dist = game.rnd.nextInt(5);
									}
									int secondPowerUp = game.rnd.nextInt(4);
									while (secondPowerUp == which_powerUp)
									{
										secondPowerUp = game.rnd.nextInt(4);
									}
									displayPowerUp(secondPowerUp,x_dist);
									genPowerUp = false;
								}
							}
						}
						else
						{
							genPowerUp = false;
						}
					}
				}
				
			}
		}

//		if (game.ball_dur < 0) {
//			for (int i = 0; i < 1; i++) {
//
//				game.getAllElements().add(game.makeBallPowerUp(100, 10));
//			}
//			game.ball_dur = 3;
//		}

		lastNanoTime.value = currentNanoTime;
		game.getSnake().setVelocity(0, 0);
		if (game.getInput().contains("LEFT")) {
			game.getInput().remove("LEFT");
			game.getSnake().addVelocity(-350, 0);
		}
		if (game.getInput().contains("RIGHT")) {
			game.getInput().remove("RIGHT");
			game.getSnake().addVelocity(350, 0);
		}
		game.getSnake().update(elapsedTime);

		boolean removeAllBlocks = false;

		Iterator<Sprite> allElementsIter = game.getAllElements().iterator();
		while (allElementsIter.hasNext()) {
			removeAllBlocks = false;
			Sprite element = allElementsIter.next();
			if (element.outOfFrame()) {
				allElementsIter.remove();
			}
			//System.out.println("Snake at "+game.getSnake().positionY);
			if (element.intersects(game.getSnake())) {
				//if (element instanceof Wall)
					//System.out.println("interects");
				if (element instanceof Block) {
					Block temp = (Block) element;
					if (game.getSnake().isShieldActive()) {
						temp.destroy();
						allElementsIter.remove();
						game.getScore().value++;
					} else {
						try {
							if (temp.canBeDestroyed(game.getSnake())) {
								allElementsIter.remove();
								//System.out.println("Setting to "+game.getSnakeYPos());
								//game.getSnake().setPositionY(game.getSnakeYPos());
								game.getScore().value++;
								//temp.forceSpriteOnBottom(game.getSnake());
								game.setSpeedToDefault();
							} else {
								game.getScore().value++;
								temp.collide(game.getSnake());
								//temp.forceSpriteOnBottom(game.getSnake());
								game.setSpeed(0);
							}
						} catch (SnakeLengthZeroException e) {
							// TODO Auto-generated catch block
							this.stop();
							game.getStage().close();
						}
					}
				} else if (element instanceof BallPowerUp) {
					BallPowerUp temp = (BallPowerUp) element;
					temp.increaseLength(game.getSnake());
					allElementsIter.remove();
				} else if (element instanceof Shield) {
					Shield temp = (Shield) element;
					temp.giveShield(game.getSnake());
					allElementsIter.remove();
				} else if (element instanceof Coin) {
					Coin temp = (Coin) element;
					temp.addCoins(game.getSnake());
					allElementsIter.remove();
				} else if (element instanceof Magnet) {
					Magnet temp = (Magnet) element;
					temp.activateMagnet(game.getSnake());
					allElementsIter.remove();
				} else if (element instanceof DestroyBlocksPowerUp) {
					removeAllBlocks = true;
					allElementsIter.remove();
					break;
				}
			} else if (element instanceof Block || element instanceof Wall) {
				if (element.leftIntersects(game.getSnake())) {
					//System.out.println("left");
					element.forceSpriteOnLeft(game.getSnake());
				} else if (element.rightIntersects(game.getSnake())) {
					//System.out.println("right");
					element.forceSpriteOnRight(game.getSnake());
				} else if (element.bottomIntersects(game.getSnake())) {
					//System.out.println("Correct");
					element.forceSpriteCorrectly(game.getSnake());
				}
			}
			if (element instanceof Coin) {
				if (game.getSnake().magnetIntersects((Coin) element)) {
					double disX = game.getSnake().positionX - element.positionX;
					double disY = game.getSnake().positionY - element.positionY;
					double factor = Math.sqrt(Math.pow(disX, 2) + Math.pow(disX, 2));
					element.addVelocity(disX * 10 / factor, disY * 10 / factor);
				}
			}
		}
		if (removeAllBlocks) {
			removeAllBlocks = false;
			Iterator<Sprite> allElementsIter2 = game.getAllElements().iterator();
			while (allElementsIter2.hasNext()) {
				Sprite element = allElementsIter2.next();
				if (element instanceof Block) {
					allElementsIter2.remove();
				}
			}
		}

		game.getGc().clearRect(0, 0, game.getScreenCoordinates()[1], game.getScreenCoordinates()[3]);
		game.getSnake().render(game.getGc());

		for (Sprite element : game.getAllElements()) {
			element.setVelocity(0, game.getSpeed());
			element.update(elapsedTime);
			element.render(game.getGc());
		}
		game.getGc().setStroke(Color.WHITE);
		String pointsText = "Score: $" + (game.getScore().value);
		game.getGc().strokeText(pointsText, game.getScreenCoordinates()[1] - 70, game.getScreenCoordinates()[0] + 30);
	}

}
