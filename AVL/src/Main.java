public class Main {

    public static void main(String[] args) {
        AVLTree<Integer, String> tree = new AVLTree<>();
        tree.insert(25, "John Doe");
        tree.insert(21, "Jane Doe");
        tree.insert(9, "Bob Tester");

        System.out.println("Welcome, " + tree.get(25));
        System.out.println("Welcome, " + tree.get(21));
        System.out.println("Welcome, " + tree.get(9));
    }
}
