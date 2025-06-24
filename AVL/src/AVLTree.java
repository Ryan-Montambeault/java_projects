public class AVLTree<K extends Comparable<K>, V> {

    private static class AVLNode<K, V> {
        K key;
        V value;
        AVLNode<K, V> left, right;
        int height;
        private int size = 0;

        AVLNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.height = 1;
        }
    }

    public interface Visitor<K, V> {
        void visit(K key, V value);
    }

    private AVLNode<K, V> root;
    private int size;

    // ---------- public api methods ----------
    public void insert(K key, V value) {
        root = insertRecursive(root, key, value);
    }

    public int getSize() {
        return size;
    }

    public V get(K key) {
        AVLNode<K, V> node = getRecursive(root, key);
        return (node != null) ? node.value : null;
    }

    public void inOrder(Visitor<K, V> visitor) {
        inOrderRecursive(root, visitor);
    }

    public void remove(K key) {
        root = removeRecursive(root, key);
    }

    public void clear() {
        root = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }


    // ---------- private helper functions ----------
    private AVLNode<K, V> insertRecursive(AVLNode<K, V> node, K key, V value) {
        if (node == null) {
            size++;
            return new AVLNode<>(key, value); // create leaf if node is null
        }

        int cmp = key.compareTo(node.key); // compare keys
        if (cmp < 0) node.left = insertRecursive(node.left, key, value); // traverse left
        else if (cmp > 0) node.right = insertRecursive(node.right, key, value); // traverse right
        else return node; // refuse duplicate

        // update height and rebalance AVL if needed
        updateHeight(node);
        return balance(node);
    }

    private void updateHeight(AVLNode<K, V> node) {
        // get left and right subtree heights
        int leftHeight = (node.left != null) ? node.left.height : 0;
        int rightHeight = (node.right != null) ? node.right.height : 0;
        // update height
        node.height = 1 + (leftHeight > rightHeight ? leftHeight : rightHeight);
    }

    private AVLNode<K, V> balance(AVLNode<K, V> node) {
        // calculate balance factor
        int bf = balanceFactor(node);

        // fix imbalances
        if (bf > 1) { // left-heavy
            if (balanceFactor(node.left) < 0) node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        if (bf < -1) { // right-heavy
            if (balanceFactor(node.right) > 0) node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        // no imbalances left - return original node
        return node;
    }

    private int balanceFactor(AVLNode<K, V> node) {
        // get left and right subtree heights
        int leftHeight = (node.left != null) ? node.left.height : 0;
        int rightHeight = (node.right != null) ? node.right.height : 0;
        // calculate balance factor using left and right heights
        int bf = leftHeight - rightHeight;
        return bf;
    }

    private AVLNode<K, V> rotateLeft(AVLNode<K, V> x) {
        AVLNode<K, V> y = x.right;
        AVLNode<K, V> T2 = y.left;

        // rotation
        y.left = x;
        x.right = T2;

        // update heights
        updateHeight(x);
        updateHeight(y);

        return y; // new root
    }

    private AVLNode<K, V> rotateRight(AVLNode<K, V> y) {
        AVLNode<K, V> x = y.left;
        AVLNode<K, V> T2 = x.right;

        // rotation
        x.right = y;
        y.left = T2;

        // update heights
        updateHeight(y);
        updateHeight(x);

        return x; // new root
    }

    private AVLNode<K, V> getRecursive(AVLNode<K, V> node, K key) {
        if (node == null) return null;

        int cmp = key.compareTo(node.key); // compare keys
        if (cmp < 0) return getRecursive(node.left, key); // traverse left
        else if (cmp > 0) return getRecursive(node.right, key); // traverse right
        else return node; // key found
    }

    private void inOrderRecursive(AVLNode<K, V> node, Visitor<K, V> visitor) {
        if (node == null) return;
        inOrderRecursive(node.left, visitor);
        visitor.visit(node.key, node.value);
        inOrderRecursive(node.right, visitor);
    }

    private AVLNode<K, V> removeRecursive(AVLNode<K, V> node, K key) {
        if (node == null) return null;

        int cmp = key.compareTo(node.key); // compare keys
        if (cmp < 0) node.left = removeRecursive(node.left, key); // traverse left
        else if (cmp > 0) node.right = removeRecursive(node.right, key); // traverse right
        else {
            // found node to delete
            if (node.left == null || node.right == null) {
                size--;
                node = (node.left != null) ? node.left : node.right;
            } else {
                AVLNode<K, V> successor = minValueNode(node.right); // locate successor
                node.key = successor.key; // replace key
                node.value = successor.value; // replace value
                node.right = removeRecursive(node.right, successor.key); // remove successor
            }
        }

        if (node == null) return null;
        updateHeight(node); // update height
        return balance(node); // rebalance AVL if necessary
    }

    private AVLNode<K, V> minValueNode(AVLNode<K, V> node) {
        AVLNode<K, V> current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }
}
