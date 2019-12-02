package com.test.Optional;

import java.util.Optional;

public class NewMan {
	private Optional<Goddess> goddess = Optional.empty();

	public NewMan() {
		
	}

	public NewMan(Optional<Goddess> goddess) {
		super();
		this.goddess = goddess;
	}

	@Override
	public String toString() {
		return "NewMan [godness=" + goddess + "]";
	}

	public Optional<Goddess> getGoddess() {
		return goddess;
	}

	public void setGoddess(Optional<Goddess> goddess) {
		this.goddess = goddess;
	}
}
