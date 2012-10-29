package clueGame;

public class Card {
	private String name;
	private Type type;
	public enum Type {PERSON, WEAPON, ROOM};
	
	public Card(String inputName, String cardType) throws BadConfigFormatException{
		if(cardType.equalsIgnoreCase("person")) {
			this.type = Type.PERSON;
		} else if (cardType.equalsIgnoreCase("weapon")) {
			this.type = Type.WEAPON;
		} else if (cardType.equalsIgnoreCase("room")) {
			this.type = type.ROOM;
		} else {
			throw new BadConfigFormatException("Incorrect type for card");
		}
		this.name = inputName;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Type getType() {
		return type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof Card){
			Card temp = (Card) other;
			if (this.name.equals(temp.name) && this.type.equals(temp.type)){
				return true;
			}
		}
		return false;
	}
}
