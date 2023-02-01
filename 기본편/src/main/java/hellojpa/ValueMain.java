package hellojpa;

public class ValueMain {

    public static void main(String[] args) {
        int a = 10;
        int b = 10;

        System.out.println("a == b" + (a == b));

        Address address = new Address("city", "adress", "10000");
        Address address2 = new Address("city", "adress", "10000");

        System.out.println("address == address2 = " + (address == address2));
        System.out.println("address equals address2: " + (address.equals(address2)));
    }
}
