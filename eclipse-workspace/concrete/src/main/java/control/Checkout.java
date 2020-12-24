package control;
import java.util.ArrayList;

import entity.Item;
import entity.PriceRules;

public class Checkout {

	private ArrayList<Item> itemList;
	private PriceRules rules;
	private int iteratorItemA = 0;
	private int iteratorItemB = 0;
	private int iteratorItemC = 0;
	private int iteratorItemD = 0;

	public Checkout (PriceRules priceRules) {
		this.itemList = new ArrayList<Item>();
		rules = priceRules;
	}


	public ArrayList<Item> getItemList() {
		return itemList;
	}

	public void scan (String itemId) {

		Item item;

		if(rules.getPricingRulesTable().containsKey(itemId)) {

			int price = rules.getUnitPrice(itemId);
			item = new Item(itemId, price);
			this.itemList.add(item);
			itemIterator(itemId);

		}else {
			System.out.println("Unknown item. Couldn't finish operation.");
		}

	}
	private void itemIterator(String itemId) {

		if(itemId.equals("A")) {
			this.iteratorItemA++;
		}
		else if (itemId.equals("B")) {
			this.iteratorItemB++;
		}
		else if (itemId.equals("C")){
			this.iteratorItemC++;
		}
		else if(itemId.equals("D")){
			this.iteratorItemD++;
		}
	}

	public int priceList(String itemsIDSequence) {

		newItemList();
		resetIterators();
		
		char[]itemsList = itemsIDSequence.toCharArray();
		for(char id : itemsList) {
			String item = Character.toString(id);
			scan(item);
		}
		return getTotal();
	}

	private int getTotalForPrint () {
		int totalPrice = 0;

		for (Item i : this.itemList ) {			
			totalPrice = totalPrice+ i.getPrice();
		}
		if(checkForPromo(itemList)) {

			if(iteratorItemA>= rules.getPromoQuantity("A")){
				totalPrice -= giveAndPrintDiscount("A", iteratorItemA);	
			}
			if(iteratorItemB>= rules.getPromoQuantity("B")){
				totalPrice -= giveAndPrintDiscount("B", iteratorItemB);	
			}
			if(iteratorItemC>= rules.getPromoQuantity("C")){
				totalPrice -= giveAndPrintDiscount("C", iteratorItemC);	
			}
			if(iteratorItemD>= rules.getPromoQuantity("D")){
				totalPrice -= giveAndPrintDiscount("D", iteratorItemD);	
			}
		}
		return totalPrice;
	}
	
	public int getTotal() {
		int totalPrice = 0;

		for (Item i : this.itemList ) {			
			totalPrice = totalPrice+ i.getPrice();
		}
		if(checkForPromo(itemList)) {

			if(iteratorItemA>= rules.getPromoQuantity("A")){
				totalPrice -= giveDiscount("A", iteratorItemA);	
			}
			if(iteratorItemB>= rules.getPromoQuantity("B")){
				totalPrice -= giveDiscount("B", iteratorItemB);	
			}
			if(iteratorItemC>= rules.getPromoQuantity("C")){
				totalPrice -= giveDiscount("C", iteratorItemC);	
			}
			if(iteratorItemD>= rules.getPromoQuantity("D")){
				totalPrice -= giveDiscount("D", iteratorItemD);	
			}
		}
		return totalPrice;
	}

	private void newItemList() {
		this.itemList.clear();
	}

	private void resetIterators() {
		this.iteratorItemA = 0;
		this.iteratorItemB = 0;
		this.iteratorItemC = 0;
		this.iteratorItemD = 0;
	}

	private boolean checkForPromo(ArrayList<Item> itemList) {
		boolean hasPromo = false;

		for(Item i: itemList) {
			String itemID = i.getId();

			if(rules.getPromoQuantity(itemID) > 1) {
				hasPromo = true;
				break;
			}
		}
		return hasPromo;
	}

	private int giveAndPrintDiscount(String itemID, int itemIterator) {

		int discount;

		int minQuantity = rules.getPromoQuantity(itemID);		
		int specialPrice = rules.getSpecialPrice(itemID);
		int unitPrice = rules.getUnitPrice(itemID);

		if(itemIterator >=minQuantity && minQuantity>1) {

			unitPrice = rules.getUnitPrice(itemID);
			int fullPrice = minQuantity*unitPrice;
			int discountMultiplier = itemIterator/minQuantity;
			discount = (fullPrice - specialPrice)*discountMultiplier;
			System.out.println("You recieved " + discount +" off on item "+ itemID);
		}else {
			discount = 0;
		}

		return discount;
	}
	
	private int giveDiscount(String itemID, int itemIterator) {

		int discount;

		int minQuantity = rules.getPromoQuantity(itemID);		
		int specialPrice = rules.getSpecialPrice(itemID);
		int unitPrice = rules.getUnitPrice(itemID);

		if(itemIterator >=minQuantity && minQuantity>1) {

			unitPrice = rules.getUnitPrice(itemID);
			int fullPrice = minQuantity*unitPrice;
			int discountMultiplier = itemIterator/minQuantity;
			discount = (fullPrice - specialPrice)*discountMultiplier;
		}else {
			discount = 0;
		}

		return discount;
	}

	public void printItemList () {
		if(!itemList.isEmpty()) {
			System.out.println(" ");
			for (Item i : this.itemList){
				i.printItem();
			}
		}else {
			System.out.println("There's no item on this list.");
		}
	}

	public void printTotal() {
		System.out.println("Total: "+getTotalForPrint());
	}

}

