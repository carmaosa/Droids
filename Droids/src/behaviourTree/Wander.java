package behaviourTree;

import java.util.Random;

import main.Board;
import main.Droid;
import main.Vector2;

public class Wander extends Routine {

	private Board board;

	private static Random random = new Random();

	private MoveTo moveTo;
	private Stand stand;

	private final int standTime = 1;

	public Wander(Board board) {
		super();
		this.board = board;
		reset();
	}

	@Override
	public void start() {
		super.start();
		this.moveTo.start();
	}

	public void reset() {

		double targetX = random.nextInt(board.width);
		double targetY = random.nextInt(board.height);

		Vector2 target = new Vector2(targetX, targetY);
		this.moveTo = new MoveTo(target);
	}

	@Override
	public void act(Droid droid, int delta, Board board) {
		if (!moveTo.isRunning()) {
			if (stand.isRunning()) {
				this.stand.act(droid, delta, board);

				if (stand.isSuccess()) {
					succeed();
				}
			}
			return;
		}

		this.moveTo.act(droid, delta, board);

		if (this.moveTo.isSuccess()) {

			if (stand == null || !stand.isRunning()) {
				stand = new Stand(standTime);
				stand.start();
			}

		} else if (this.moveTo.isFailure()) {
			fail();
		}
	}
}
