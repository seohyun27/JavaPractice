package PetShop;
import java.util.ArrayList;
import java.util.Random;

abstract class Animal{ //동물 클래스
	private String name;
	private int age, price;
	private float weight;
	private String species;
	private int[] genes; // 유전자 정보 배열
    public static int totalAnimal = 0; //전체 동물 수
    
	protected Animal(int age, String name, float weight, int price)
	{
		this.age = age;
		this.name = name;
		this.weight = weight;
		this.price = price;
		totalAnimal++;
		
		//유전자 생성 및 저장
        Random rand = new Random();
        genes = new int[8];
        for(int i = 0; i < 8; i++) {
            genes[i] = rand.nextInt(2); // 0 또는 1을 배열에 저장
        }
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getPrice() {
		return this.price;
	}
	
	protected void setSpecies(String species) {
		this.species = species;
	}
	
	public String getSpecies() {
		return this.species;
	}
	
    public void setGenes(int[] genes) {
        this.genes = genes;
    }
	
    public int[] getGenes() {
        return genes;
    }
	
    public void printGenes() {
        System.out.print(name + "'s genes: ");
        for(int gene : genes) { //for each 문
            System.out.print(gene);
        }
        System.out.println();
    }
	
	public void printTotal(){
		System.out.println("Total animals : " + totalAnimal);
	}
	
	public void walk() {
		System.out.println(name + " walk.");
	}
	
	public abstract void sound();
	
	public abstract void doing();

}


class Dog extends Animal{ //개 클래스
	public Dog(int age, String name, float weight, int price) {
		super(age, name, weight, price);
		this.setSpecies("Dog");
	}
	
	@Override
	public void sound() {
		System.out.println("Bark!");
	}
	public void doing() {
		System.out.println(getName() + " wags its tail.");
	}
}


class Cat extends Animal{ //고양이 클래스
	public Cat(int age, String name, float weight, int price) {
		super(age, name, weight, price);
		this.setSpecies("Cat");
	}
	
	@Override
	public void sound() {
		System.out.println("Meow~");
	}
	public void doing() {
		System.out.println(getName() + " scratch.");
	}
}


class Customer{ //구매자 클래스
	private String name;
	private int money;

	public Customer(String name, int money) {
		this.name = name;
		this.money = money;
	}
	
	public int getMoney() {
		return this.money;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void buyAnimal(Animal animal) {
		if(money < animal.getPrice())
			System.out.println(this.name + " doesn't have enough money.");
		else {
			money -= animal.getPrice();
			System.out.println(name + " bought " + animal.getName() + ".");
			System.out.println(name + "'s balance : " + money);
		}
	}
	
	public void petting(Animal animal) {
		animal.sound();
	}
	
	public void increaseMoney() {
		System.out.println(this.name + " earned 10000.");
		this.money += 10000;
		System.out.println(name + "'s balance : " + money);
	}
}


class Owner { //점주 클래스
	private String name;
	private int money;
	private ArrayList<Animal> animal_List;    //동물들을 저장할 ArrayList
	private final int MAX_ANIMALS = 8;    //최대 보유 가능한 동물 수

	public Owner(String name, int money) {
		this.name = name;
		this.money = money;
		this.animal_List = new ArrayList<>(); //animal_List 초기화
	}

    public void getAnimal(Animal animal) {
        if (animal_List.size() >= MAX_ANIMALS) {
            System.out.println(name + "'s animals are full.");
            return;
        }

        animal_List.add(animal); //애니멀 리스트에 새 동물 추가
        //현재까지의 보유 동물 출력
        System.out.println(name + "'s animals : ");
        for (Animal print_a : animal_List) { //for each 문
            System.out.print(print_a.getName() + "    ");
        }
        System.out.println("\nTotal : " + animal_List.size());
    }
    
    private int[] heredity(Animal animalA, Animal animalB) { 
    	//부모 유전자를 받아 새 동물의 유전자를 생성하는 유전 메소드
        int[] genesA = animalA.getGenes();
        int[] genesB = animalB.getGenes();
        int[] newGenes = new int[8];
        
        // 각 유전자 위치별로 연산 수행
        Random rand = new Random();
        for(int i = 0; i < 8; i++) {
            int probability = rand.nextInt(100); //0-99 사이의 난수를 생성
            
            if(probability < 75) { //0-74라면 OR
                newGenes[i] = genesA[i] | genesB[i];
            }
            else if(probability < 90) { //75-89라면 AND
                newGenes[i] = genesA[i] & genesB[i];
            }
            else { //89-99라면 XOR
                newGenes[i] = genesA[i] ^ genesB[i];
            }
        }
        
        return newGenes; //만들어진 유전자값을 리턴
    }
    
    public void crossbreeding(Animal animalA, Animal animalB, String name) { //교배 메소드
    	if(!animalA.getSpecies().equals(animalB.getSpecies())) { //문자열 비교
    		System.out.println("Crossbreeding between different species is impossible.");
    		return;
    	}
    	    
    	Animal temp = null;
    	
    	if(animalA.getSpecies().equals("Dog"))
    		temp = new Dog(0, name, 1.0f, 5000);
        	
    	
    	if(animalA.getSpecies().equals("Cat"))
    		temp = new Cat(0, name, 1.0f, 5000);

    	getAnimal(temp);
    	//부모의 유전자값을 물려받은 자식 유전자 생성
        int[] newGenes = heredity(animalA, animalB);
        temp.setGenes(newGenes); //기존의 랜덤 유전자 값에 덮어쓰기
    }
    
    public void sellingAnimal(Animal animal, Customer customer) {
        if (!animal_List.contains(animal)) {
            System.out.println("The owner doesn't have a " + animal.getName() + ".");
            return;
        }
        
        if (customer.getMoney() < animal.getPrice()) {
            System.out.println("Customer doesn't have enough money.");
            return;
        }

        money += animal.getPrice();
        customer.buyAnimal(animal);
        animal_List.remove(animal);
        System.out.println("Owner's balance : " + money);
    }
    
    public Animal getAnimalOrder(int order) {
    	//순서를 받아 animal_List에서 해당 순서의 동물을 반환하는 함수
        if (order < 1 || order > animal_List.size()) {
            System.out.println("No animals exist in this order.");
            return null;
        }
        
        return animal_List.get(order-1); //인덱스를 사용해야하므로 order-1
    }
}

public class PetShop {
	public static void main(String[] args) {
		System.out.println("1단계 : 기본 클래스 설계");
		Animal dog1 = new Dog(2, "Coco", 5f, 5000);
		dog1.sound();
		dog1.doing();
		Animal cat1 = new Cat(5, "Navi", 3.5f, 3000);
		cat1.sound();
		cat1.doing();
		
		System.out.println("\n2단계 : 기본 클래스 설계");
		Customer customer1 = new Customer("minji", 10000);
		customer1.buyAnimal(cat1);
		customer1.petting(cat1);

		System.out.println("\n3단계 : 점주 클래스 구현 및 동물 관리");
		Owner me = new Owner("seohyun", 10000);
	    Animal dog2 = new Dog(6, "Leo", 4f, 4000);
	    Animal dog3 = new Dog(6, "Max", 3f, 5000);
	    Animal cat2 = new Cat(7, "Luna", 2.9f, 7000);
	    me.getAnimal(dog2);
	    me.getAnimal(dog3);
	    me.getAnimal(cat2);
	    me.crossbreeding(dog2, cat2, "Momo"); //이종교배
	    me.crossbreeding(dog2, dog3, "Momo");
	    me.sellingAnimal(dog2, customer1);
	    me.sellingAnimal(cat2, customer1); //고객의 잔금 부족
	    customer1.increaseMoney();
	    me.sellingAnimal(cat2, customer1);
	    customer1.petting(dog1);

	    System.out.println("\n4단계 : 상수 및 정적 멤버 활용");
	    dog1.printTotal(); //지금까지 생성된 총 동물 수 출력
	    me.crossbreeding(dog2, dog3, "Tori");
	    me.crossbreeding(dog2, dog3, "Kim");
	    me.crossbreeding(dog2, dog3, "Bori");
	    me.crossbreeding(dog2, dog3, "Toto");
	    me.crossbreeding(dog2, dog3, "Cream");
	    me.crossbreeding(dog2, dog3, "Bella");
	    //"Molly"에서 점주의 총 보유 동물 수가 8마리를 넘음 -> 교배 불가
	    me.crossbreeding(dog2, dog3, "Molly");
	    dog1.printTotal();
	    
	    System.out.println("\n5단계 : 유전자 시스템 구현");
	    dog2.printGenes();
	    dog3.printGenes();
	    Animal dog4 = me.getAnimalOrder(8); //8번 동물 Bella를 반환
	    dog4.printGenes(); //dog2와 dog3를 물려받은 Bella의 유전자
	}
}
