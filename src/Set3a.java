import java.util.Iterator;

import components.binarytree.BinaryTree;
import components.binarytree.BinaryTree1;
import components.set.Set;
import components.set.SetSecondary;

/**
 * {@code Set} represented as a {@code BinaryTree} (maintained as a binary
 * search tree) of elements with implementations of primary methods.
 *
 * @param <T>
 *            type of {@code Set} elements
 * @mathdefinitions <pre>
 * IS_BST(
 *   tree: binary tree of T
 *  ): boolean satisfies
 *  [tree satisfies the binary search tree properties as described in the
 *   slides with the ordering reported by compareTo for T, including that
 *   it has no duplicate labels]
 * </pre>
 * @convention IS_BST($this.tree)
 * @correspondence this = labels($this.tree)
 *
 * 
 * @author David Piatt & Paul Shortman
 */


public class Set3a<T extends Comparable<T>> extends SetSecondary<T> {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Elements included in {@code this}.
     */
    private BinaryTree<T> tree;

    /**
     * Returns whether {@code x} is in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} to be searched
     * @param x
     *            the label to be searched for
     * @return true if t contains x, false otherwise
     * @requires IS_BST(t)
     * @ensures isInTree = (x is in labels(t))
     */
    private static <T extends Comparable<T>> boolean isInTree(BinaryTree<T> t,
            T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";
        boolean isInTree = false;
        
        if(t.height() > 0){
            BinaryTree<T> left = t.newInstance();
            BinaryTree<T> right = t.newInstance();
            T root = t.disassemble(left,right);

            if(root.compareTo(x) == 0){
                isInTree = true;
            }else if(root.compareTo(x)<0){
                isInTree = isInTree(right,x);
            }else{
                isInTree=isInTree(left,x);
            }
            t.assemble(root, left, right);
        }

        return isInTree;
    }

    /**
     * Inserts {@code x} in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} to be searched
     * @param x
     *            the label to be inserted
     * @aliases reference {@code x}
     * @updates t
     * @requires IS_BST(t) and x is not in labels(t)
     * @ensures IS_BST(t) and labels(t) = labels(#t) union {x}
     */
    private static <T extends Comparable<T>> void insertInTree(BinaryTree<T> t,
            T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";
        


        if(t.height() > 0){
            BinaryTree<T> left = t.newInstance();
            BinaryTree<T> right = t.newInstance();
            T root = t.disassemble(left,right);

            if(x.compareTo(root) < 0){
                insertInTree(left, x);

            }else{
                insertInTree(right,x);

            }
            t.assemble(root, left, right);
        }else{
            //if the tree is terminal
            t.assemble(x, t.newInstance(), t.newInstance());
        }

    }

    /**
     * Removes and returns the smallest (left-most) label in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} from which to remove the label
     * @return the smallest label in the given {@code BinaryTree}
     * @updates t
     * @requires IS_BST(t) and |t| > 0
     * @ensures <pre>
     * IS_BST(t)  and  removeSmallest = [the smallest label in #t]  and
     *  labels(t) = labels(#t) \ {removeSmallest}
     * </pre>
     */
    private static <T> T removeSmallest(BinaryTree<T> t) {
        assert t != null : "Violation of: t is not null";

        T smallest;




        BinaryTree<T> left = t.newInstance();
        BinaryTree<T> right = t.newInstance();
        T root = t.disassemble(left,right); 

        if(left.size()>0){

            smallest = removeSmallest(left);
            t.assemble(root, left, right);

        }else {

            smallest = root;

            t.transferFrom(right);
        }

        

        
        
        
        return smallest;
    }

    /**
     * Finds label {@code x} in {@code t}, removes it from {@code t}, and
     * returns it.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} from which to remove label {@code x}
     * @param x
     *            the label to be removed
     * @return the removed label
     * @updates t
     * @requires IS_BST(t) and x is in labels(t)
     * @ensures <pre>
     * IS_BST(t)  and  removeFromTree = x  and
     *  labels(t) = labels(#t) \ {x}
     * </pre>
     */
    private static <T extends Comparable<T>> T removeFromTree(BinaryTree<T> t,
            T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";
        
        /*
         * we have binary tree. if root is what we want we set it equal to removed.
         * Then if right has something in it we remove it from right, set it as the new root and assemble it. 
         * else we just transfer from the left to keep the binary search tree structure. 
         * 
         * If root isn't what we want we see if the root is bigger or smaller than what we want and begin navigating down those
         * trees. 
         * 
         * To work with duplicates we can use .equals instead of compareTo. 
         * 
         * 
         */
        

        T removed = null;

        if(t.root().compareTo(x) == 0){

            BinaryTree<T> left = t.newInstance();

            BinaryTree<T> right = t.newInstance();

            removed = t.disassemble(left,right);

            if(right.size()!=0 && left.size()==0){

                t.transferFrom(right);

                //fixed the and statement that left is not equal to zero. 
                //Not checking for a case. 
            }else if(right.size()==0){

                t.transferFrom(left);
            }else{
                t.assemble(removeSmallest(right), left, right);
            }

        }else{
            BinaryTree<T> left = t.newInstance();
            BinaryTree<T> right = t.newInstance();
            T root = t.disassemble(left, right);
            if(root.compareTo(x)>0){
                removed = removeFromTree(left,x);
            }else{
                removed = removeFromTree(right,x);
            }
            t.assemble(root, left, right);
        }






        
        
        return removed;
    }

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {
        //Just a new binary tree
        //We're not making a binary tree we're making set work on binary tree.
        
        this.tree = new BinaryTree1<T>();

    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Set3a() {

        this.createNewRep();

    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @SuppressWarnings("unchecked")
    @Override
    public final Set<T> newInstance() {
        try {
            return this.getClass().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(
                    "Cannot construct object of type " + this.getClass());
        }
    }

    @Override
    public final void clear() {
        this.createNewRep();
    }

    @Override
    public final void transferFrom(Set<T> source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof Set3a<?> : ""
                + "Violation of: source is of dynamic type Set3<?>";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case: source must be of dynamic type Set3a<?>, and
         * the ? must be T or the call would not have compiled.
         */
        Set3a<T> localSource = (Set3a<T>) source;
        this.tree = localSource.tree;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void add(T x) {
        assert x != null : "Violation of: x is not null";
        assert !this.contains(x) : "Violation of: x is not in this";

        this.insertInTree(this.tree, x);;

    }

    @Override
    public final T remove(T x) {
        assert x != null : "Violation of: x is not null";
        assert this.contains(x) : "Violation of: x is in this";

        
        return removeFromTree(this.tree, x);
    }

    @Override
    public final T removeAny() {
        assert this.size() > 0 : "Violation of: this /= empty_set";

       
        return removeSmallest(this.tree);
    }

    @Override
    public final boolean contains(T x) {
        assert x != null : "Violation of: x is not null";

        
        return isInTree(this.tree, x);
    }

    @Override
    public final int size() {

        
        return this.tree.size();
    }

    @Override
    public final Iterator<T> iterator() {
        return this.tree.iterator();
    }

}
