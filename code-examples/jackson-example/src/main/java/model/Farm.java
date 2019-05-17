package model;

public class Farm {
	private final List<Animal> animals;

	@JsonCreator
	public Farm(@JsonProperty("animals") final List<Animal> animals) {
		super();
		this.animals = animals;
	}

	public Farm() {
		super();
		animals = new ArrayList<>();
	}

	public List<Animal> getAnimals() {
		return animals;
	}

	public void addAnimal(final Animal animal) {
		animals.add(animal);
	}

	@Override
	public boolean equals(final Object o) {
		if(this == o) {
			return true;
		}
		if(!(o instanceof Farm)) {
			return false;
		}
		final Farm farm = (Farm) o;
		return Objects.equals(getAnimals(), farm.getAnimals());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getAnimals());
	}
}
