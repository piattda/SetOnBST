import static org.junit.Assert.*;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 * 
 * @author David Piatt & Paul Shortman
 * 
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor for the implementation
     * under test and returns the result.
     * 
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor for the reference
     * implementation and returns the result.
     * 
     * @return the new set
     * @ensures constructorRef = {}
     */
    protected abstract Set<String> constructorRef();

    /**
     * Creates and returns a {@code Set<String>} of the implementation under
     * test type with the given entries.
     * 
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsTest = [entries in args]
     */
    private Set<String> createFromArgsTest(String... args) {
        Set<String> set = this.constructorTest();
        for (String s : args) {
            assert !set.contains(s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Creates and returns a {@code Set<String>} of the reference implementation
     * type with the given entries.
     * 
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsRef = [entries in args]
     */
    private Set<String> createFromArgsRef(String... args) {
        Set<String> set = this.constructorRef();
        for (String s : args) {
            assert !set.contains(s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    // TODO - add test cases for xconstructor, xadd, xremove, xremoveAny, xcontains, and size
    
    //constructor tests:
    @Test
    public void testConstructor(){
        Set<String> set = createFromArgsTest("");
        Set<String> set2 = createFromArgsRef("");
        
        assertEquals(set,set2);
    }
    
    @Test
    public void testConstructorNonEmpty(){
        Set<String> set = createFromArgsTest("hello");
        Set<String> set2 = createFromArgsRef("hello");
        
        assertEquals(set,set2);
    }
    
    @Test
    public void testMultipleElementsInConstructor(){
        Set<String> set = createFromArgsTest("hello","World");
        Set<String> set2 = createFromArgsRef("hello","World");
        
        assertEquals(set,set2);
    }

    
    @Test
    public void testAdd1Vlaue(){
        Set<String> set = createFromArgsTest("hello","World");
        Set<String> set2 = createFromArgsRef("hello","World", "I'm");
        set.add("I'm");
        assertEquals(set,set2);
    }
    
    @Test
    public void testAdd2Values(){
        Set<String> set = createFromArgsTest("hello","World");
        Set<String> set2 = createFromArgsRef("hello","World", "I'm", "David");
        set.add("I'm");
        set.add("David");
        assertEquals(set,set2);
    }
    
    @Test 
    public void testAddToEmptySet(){
        Set<String> set = createFromArgsTest();
        Set<String> set2 = createFromArgsRef("hello","World");
        
        set.add("hello");
        set.add("World");
        
        assertEquals(set,set2);
    }
    
    @Test
    public void testRemove(){
        Set<String> set = createFromArgsTest("hello","World", "I'm", "David");
        Set<String> set2 = createFromArgsRef("hello","World");
        set.remove("I'm");
        set.remove("David");
        assertEquals(set,set2);
    }
    
    
    @Test
    public void testRemoveToEmpty(){
        Set<String> set = createFromArgsTest("hello","World");
        Set<String> set2 = createFromArgsRef();
        
        set.remove("hello");
        set.remove("World");
        
        assertEquals(set,set2);
    }
    
    @Test
    public void testRemoveMultiple(){
        Set<String> set = createFromArgsTest("hello","World", "I'm", "David");
        Set<String> set2 = createFromArgsRef("hello","World");
        set.remove("I'm");
        set.remove("David");
        assertEquals(set,set2);
        //kinda redundant
        
    }
    
    @Test
    public void testRemoveAny(){
        Set<String> set = createFromArgsTest("hello","World", "I'm", "David");
        set.removeAny();
        assertTrue(set.size()==3);
    }
    
    @Test
    public void testRemoveAnyLeaving0(){
        Set<String> set = createFromArgsTest("hello");
        set.removeAny();
        assertTrue(set.size()==0);
    }
    
    @Test
    public void testContainsWithOneItem(){
        Set<String> set = createFromArgsTest("hello","World", "I'm", "David");
        Set<String> set2 = createFromArgsRef("hello","World", "I'm", "David");

        assertEquals(set.contains("hello"), set2.contains("hello"));
    }
    
    @Test
    public void testContainsEmpty(){
        Set<String> set = createFromArgsTest();
        Set<String> set2 = createFromArgsRef();
        
        assertEquals(set.contains("I"),false);
        
        
    }
    
    @Test
    public void testSizeGreaterThanZero(){
        Set<String> set = createFromArgsTest("hello","World", "I'm", "David");
        Set<String> set2 = createFromArgsRef("hello","World", "I'm", "David");
        
        assertEquals(set.size(), set2.size());
        
        
    }
    
    
    @Test
    public void testSizeofZero(){
        Set<String> set = createFromArgsTest();
        Set<String> set2 = createFromArgsRef();
        
        int size = set.size();
        assertEquals(size,0);
    }
    
}
