public class Main {

    public static void main(String[] args) {
        AVLTree<Integer, String> tree = new AVLTree<>();
        int size = 0;

        // testing inserts
        tree.insert(25, "John Doe");
        tree.insert(21, "Jane Doe");
        tree.insert(9, "Bob Tester");
        size = tree.getSize();
        if (size != 3) {
            System.out.println("Failed to insert initial users, aborting");
            return;
        }

        // testing get
        System.out.println("Welcome, " + tree.get(25));
        System.out.println("Welcome, " + tree.get(21));
        System.out.println("Welcome, " + tree.get(9));

        // testing in-order traversal
        System.out.println("Current users in system:");
        tree.inOrder((key, value) -> System.out.println(key + ": " + value));

        // testing removal
        System.out.println("Removing user: " + tree.get(21));
        tree.remove(21);
        size = tree.getSize();
        if (size != 2) {
            System.out.println("Failed to remove user");
        }
        System.out.println("Current users in system:");
        tree.inOrder((key, value) -> System.out.println(key + ": " + value));
    }
}
