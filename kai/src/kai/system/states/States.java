package kai.system.states;

import kai.system.system.Kai;

public abstract class States {
	protected Kai kai;
	protected int prevState;

	public void setSystem(Kai kai) {
		this.kai = kai;
	}

	public abstract void next();
}
