package game_utilities;

import java.util.Comparator;

import data.PlayerAction;
public class PlayerActionPriorityComparator implements Comparator<PlayerAction> {

	@Override
	public int compare(PlayerAction o1, PlayerAction o2) {
		return Integer.compare(o1.getPriority(), o2.getPriority());
	}
}
