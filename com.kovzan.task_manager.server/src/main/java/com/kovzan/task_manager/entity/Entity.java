package com.kovzan.task_manager.entity;

public abstract class Entity {

	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		
		Entity entity = (Entity) o;
		
		return id.equals(entity.id);
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
