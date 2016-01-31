package tutorialfollower.tutorial2;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.massisframework.massis.model.agents.HighLevelController;
import com.massisframework.massis.model.agents.LowLevelAgent;
import com.massisframework.massis.model.building.SimRoom;
import com.massisframework.massis.model.location.Location;
import com.massisframework.massis.model.managers.movement.ApproachCallback;
import com.massisframework.massis.pathfinding.straightedge.FindPathResult.PathFinderErrorReason;

public class MyHelloHighLevelController extends HighLevelController {

	private static final long serialVersionUID = 1L;
	private Location currentTarget;

	public MyHelloHighLevelController(LowLevelAgent agent,
			Map<String, String> metadata, String resourcesFolder)
	{
		super(agent, metadata, resourcesFolder);
		this.agent.setHighLevelData(this);
	}

	@Override
	public void stop()
	{
		/*
		 * Clean resources, threads...etc
		 */
	}

	@Override
	public void step()
	{

		if (this.currentTarget == null)
		{
			/* 1 */ final SimRoom currentRoom = this.agent.getRoom();
			/* 2 */ this.currentTarget = currentRoom.getRandomLoc();
		}

		final ApproachCallback callback = new ApproachCallback() {
			@Override
			public void onTargetReached(LowLevelAgent agent)
			{
				// Target has been reached.
			}

			@Override
			public void onSucess(LowLevelAgent agent)
			{
				// Everything ok. The agent has moved a little bit.
			}

			@Override
			public void onPathFinderError(PathFinderErrorReason reason)
			{
				// Error!
				Logger.getLogger(MyHelloHighLevelController.class.getName())
						.log(Level.SEVERE,
								"Error when approaching to {0} Reason: {1}",
								new Object[] {
										MyHelloHighLevelController.this.currentTarget,
										reason });
			}
		};

		/* 3 */ this.agent.approachTo(this.currentTarget, callback);
	}
}